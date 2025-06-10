package ru.nuyaxov.pzviewha.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;
import ru.nuyaxov.pzviewha.entity.Command;
import ru.nuyaxov.pzviewha.history.Console;
import ru.nuyaxov.pzviewha.payload.ConfigInfo;
import ru.nuyaxov.pzviewha.payload.PlayerInfo;
import ru.nuyaxov.pzviewha.payload.SafehouseInfo;
import ru.nuyaxov.pzviewha.rcon.ManagerRCON;
import java.util.ArrayList;
import java.util.List;


//TODO: организовать проверку health-check
@Controller
public class main {

    private final ManagerRCON ManagerRCON;

    public main(ManagerRCON ManagerRCON) {
        this.ManagerRCON = ManagerRCON;
    }

    //TODO: подумать, может вынести  адрес апи и порт в отдельный класс.
    @Value("${map.path}")
    String mapPath;
    
    @Value("${api.address}")
    public String apiAddress;
    
    @Value("${api.port}")
    public String apiPort;



    //TODO: вынести в отдельный класс работу с WebClient и jsonDeserialize()
    public String getApiData(String apiAddress, String apiPort, String endpoint) {
        System.out.printf("Start connect to %s:%s%n", apiAddress, apiPort);
        WebClient webClient = WebClient.create("http://%s:%s".formatted(apiAddress, apiPort));
        String jsonResponse = webClient.get()
                .uri("/%s".formatted(endpoint)) // относительный путь (будет добавлен к базовому URL)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // блокируем выполнение для синхронного получения результата
        return jsonResponse;
    }


    //TODO: может оно может и просто объекты обрабатывать..., а не только ArrayList
    /**
     * Deserialize JSON String to Object. Use to deserialize JSON-string into ArrayList's of {@link PlayerInfo}, {@link SafehouseInfo} and {@link ConfigInfo}
     * @param json JSON String represents ArrayList<>
     * @param clazz class represents {@link PlayerInfo}, {@link SafehouseInfo} and {@link ConfigInfo}
     * @return ArrayList<clazz> of some objects, class of them is given as second argument
     *  */
    public <T> ArrayList<T> jsonDeserialize(String json, Class<T> clazz) {

        ObjectMapper objectMapper = new ObjectMapper();
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, clazz);
        try {
            return objectMapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO: стоит сразу всю JSON строку обрабатывать, а не все '\n' в ней?
    //TODO: посыл дока верный, но переделать. Сказать что она делает, а уже потом почему, а не как сейчас
    /**
     * 'descriptions' variables in {@link ConfigInfo} contain '/n' substrings and not supported on HTML pages.
     * And function is replace all '\n' substrings with 'System.getProperty("line.separator")' substrings
     *
     * @param configArray some ArrayList with {@link ConfigInfo}, variable 'description' of that must be processed
     * @return same ArrayList<ConfigInfo>, but all '/n' in var description replaces with value 'System.getProperty("line.separator")'
     * */
    public ArrayList<ConfigInfo> processingNChars(ArrayList<ConfigInfo> configArray) {
        for (int i = 0; i < configArray.size(); i++) {
            String configDescription = configArray.get(i).getConfigDescription();
            System.out.println("Настрйока: %s".formatted(configArray.get(i).getConfigName()));
            if (configDescription != null) {
                if (configDescription.contains("\\n")) {
                    String newConfigDescription = configDescription.replaceAll("\\\\n", System.getProperty("line.separator"));
                    configArray.get(i).setConfigDescription(newConfigDescription);
                }
            }
        }
        return configArray;
    }


    //TODO: я пока убрал предварительную проверку доступности. Подумать, надо ли оно
    @GetMapping("/index")
    public String getPageIndex(Model model) {

        /*TODO: может получать уже все одним запросом...*/

        String jsonResponseSandboxSettings = getApiData(apiAddress, apiPort, "server_sandbox");
        String jsonResponseServerSettings = getApiData(apiAddress, apiPort, "server_settings");
        String jsonResponsePlayers = getApiData(apiAddress, apiPort, "server_players");

        ArrayList<ConfigInfo> sandboxSettingsList = jsonDeserialize(jsonResponseSandboxSettings, ConfigInfo.class);
        ArrayList<ConfigInfo> serverSettingsList = jsonDeserialize(jsonResponseServerSettings, ConfigInfo.class);
        ArrayList<PlayerInfo> playersList = jsonDeserialize(jsonResponsePlayers, PlayerInfo.class);

        sandboxSettingsList = processingNChars(sandboxSettingsList);
        serverSettingsList = processingNChars(serverSettingsList);


        //Ищем в настройках значение максимума игроков для того, чтобы добавить отдельно в атрибут
        for (ConfigInfo configInfo : serverSettingsList) {
            if (configInfo.getConfigName().toLowerCase().contains("maxplayers")) {
                model.addAttribute("maxplayers", configInfo.getConfigValue());
            }
        }


        model.addAttribute("sandboxSettings", sandboxSettingsList);
        model.addAttribute("serverSettings", serverSettingsList);
        model.addAttribute("serverPlayers", playersList);
        model.addAttribute("playersNum", String.valueOf(playersList.size()));

        return "index.html";
    }

    @GetMapping("/console")
    public String getPageConsole(Model model) {
        if (!model.containsAttribute("command")) {
            model.addAttribute("command", new Command());
        }
        model.addAttribute("console", Console.getInstance().getHistory());

        return "console";
    }

    @PostMapping("/console")
    public String sendCommand(@ModelAttribute Command command, Model model) {
        if (ManagerRCON.isConnected() & ManagerRCON.isAuthenticated()) {
            //TODO: добавить обработку '/servermsg' и нескольких строк (обрамлять все предложение в кавычки)

            String filteredCommandText = ManagerRCON.filter(command.getTxt());
            if (filteredCommandText.contains("RCON does not support commands")) {
                command.setTxt("");
            }
            command.setTxt(filteredCommandText);
            String responce = this.ManagerRCON.exec(command.getTxt());

            //TODO: лог запрос-ответ

            Console.getInstance().addRecord(command);
            Console.getInstance().addRecord(new Command(responce));

            model.addAttribute("console", Console.getInstance().getHistory());
        } else {
            model.addAttribute("console", Console.getInstance().getHistory());
            Console.getInstance().addRecord(new Command("There has no connection to RCON"));
        }

        return "console";
    }


    @GetMapping("/about")
    public String getPageAbout(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("string", "String");
        modelAndView.setViewName("about");
        return "about.html";
    }

    //TODO: понять как работает MODEL
    //TODO: понять возвращается страница
    /**
     * Endpoint for /map. If it called, it send requests 'server_players' and 'server_houses' to API
     * for get JSON responces and deserialaze them into ArrayLists of {@link PlayerInfo} and {@link SafehouseInfo}
     * then providing data to the template 'map.html'
     *
     * @param model Model that used to...
     * @return template that...
     * */
    //TODO: выглядит ужасно (улучшил, но надо сделать лучше)
    @GetMapping("/map")
    public String getPageMapTest(Model model) {
        //атрибут для того чтобы OpenSeaDragon мог получать данные об игроках и сам их парсить
        String jsonResponsePlayers = getApiData(apiAddress, apiPort, "server_players");
        String jsonResponseSafeHouses = getApiData(apiAddress, apiPort, "server_houses");

        ArrayList<PlayerInfo> listPlayers = jsonDeserialize(jsonResponsePlayers, PlayerInfo.class);
        ArrayList<SafehouseInfo> listHouses = jsonDeserialize(jsonResponseSafeHouses, SafehouseInfo.class);

        model.addAttribute("listPlayers", listPlayers);
        model.addAttribute("listHouses", listHouses);


        /*передаем сюда путь к карте */
        model.addAttribute("mapPath", mapPath);
        System.out.println("<--- mapPath: " + mapPath + " --->");

        return "map.html";
    }
}