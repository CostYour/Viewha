package ru.nuyaxov.pzviewha.history;


import ru.nuyaxov.pzviewha.entity.Command;

import java.util.ArrayList;

/**
 * Class responsible for storing the history of requests to RCON-server
 * and responses from RCON-server. Not stored history of commands from launch to launch
 */
public class Console {
    //TODO: разобраться с инстансом
    private static final Console INSTANCE = new Console();

    /**
     * Initialising of Console class
     * */
    private Console() {
        System.out.println("Init Console");
    }

    /**
     * Variable that store history of sent commands and results of commands executes
     * */
    private ArrayList<Command> history = new ArrayList<Command>();

    public static Console getInstance() {
        return INSTANCE;
    }

    /**
     * Add command object {@link Command} to Console history {@link Console}
     * @param record   command for adding to Console history
     * */
    public void addRecord(Command record) {
        this.history.add(record);
    }

    /**
     * Return all sended commands to RCON server and all responces from RCON server
     * for the entire duration of the program.
     *
     * @return {@link ArrayList<Command>} object
     */
    public ArrayList<Command> getHistory() {
        return history;
    }
}