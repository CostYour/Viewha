package ru.nuyaxov.pzviewha.config;

import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ExternalConfigPropertySource extends PropertySource<Map<String, Object>> {

    public ExternalConfigPropertySource() throws IOException {
        super("externalConfig", loadProperties());
    }

    private static Map<String, Object> loadProperties() throws IOException {
        Map<String, Object> properties = new HashMap<>();

        // Получаем путь к директории, где находится JAR-файл
        String jarDir = getJarDirectory();
        Path settingsDir = Paths.get(jarDir, "settings");

        // Проверяем существование папки settings
        if (settingsDir.toFile().exists() && settingsDir.toFile().isDirectory()) {
            // Ищем файлы конфигурации (например, application.properties или application.yml)
            Path propertiesFile = settingsDir.resolve("application.properties");

            if (propertiesFile.toFile().exists()) {
                System.out.println("Ищем путь до файла конфигурации...");
                Resource resource = new PathMatchingResourcePatternResolver()
                        .getResource("file:" + propertiesFile.toString());
                MapPropertySource source = new ResourcePropertySource(resource);
                properties.putAll(source.getSource());
                System.out.println("Найдена новая конфигурация");
            } else {
                System.out.println("Ничего не нашлось, тогда настройки по умолчанию");
            }

        }

        return properties;
    }

    private static String getJarDirectory() {
        try {
            String jarPath = new File(ExternalConfigPropertySource.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getAbsolutePath();
            return new File(jarPath).getParent();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public Object getProperty(String name) {
        return this.source.get(name);
    }
}