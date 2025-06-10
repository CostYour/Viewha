package ru.nuyaxov.pzviewha.rcon;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Manager for using RCON-client.
 */
@Component
public class ManagerRCON {

    /**
     * IP-address of RCON-server of PZ Server. Get from 'application.properties'
     * using @Value annotation {@link org.springframework.beans.factory.annotation}
     */
    @Value("${rcon.address}")
    String ip;

    /**
     * PORT of RCON-server of PZ Server. Get from 'application.properties'
     * using @Value annotation {@link org.springframework.beans.factory.annotation}
     */
    @Value("${rcon.port}")
    String port = "";

    /**
     * Password of RCON-server of PZ Server. Get from 'application.properties'
     * using @Value annotation {@link org.springframework.beans.factory.annotation}
     */
    @Value("${rcon.password}")
    String password = "";


    /**
     * Indicates the server is connected or not
     */
    private boolean isConnected = false;

    /**
     * Indicates connection of the server is authenticated or not
     */
    private boolean isAuthenticated = false;

    //TODO: дописать джавадок
    /**
     *
     * */
    private RCONClient rconClient = new RCONClient();



    /**
     * Connect to RCON-client after Spring Boot is started.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        isConnected = rconClient.connect(ip, port);
        isAuthenticated =  rconClient.auth(password);
    }

    /**
     * Return connection status
     * @return connection is successful or not- {@link Boolean}
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Return connection status of auth
     * @return auth is successful or not- {@link Boolean}
     */
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * Send a command request to connected PZ RCON-server and return response
     * @param command   - console command to send to RCON-server like as "kick", "save" and etc.
     * @return if successful a result of executing command from a server {@link String}, else null
     */
    public String exec(String command) {
        return rconClient.exec(command);
    }

    /**
     * Check command before sending to PZ RCON-server: for commands unsupported by the server
     * and remove char '/' from commands
     *
     * @param command    console command to send to RCON-server like as "kick", "save" etc.
     * @return the processed string of command {@link String}
     */
    public String filter(String command) {
        command = command.replaceAll("/", "");

        if (command.matches("(?i).*(say|whisper|all|yell).*")) {
            return "RCON does not support commands: /say, /whisper, /all, /yell. Use /servermsg 'text' instead.";
        }

        return command;
    }

}