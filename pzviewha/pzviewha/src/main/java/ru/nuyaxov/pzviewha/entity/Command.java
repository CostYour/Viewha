package ru.nuyaxov.pzviewha.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Command {
    public String txt;
    public Date date;

    public Command(String txt) {
        this.txt = txt;
        this.date = new Date();
    }

    public Command() {
        this.txt = "empty command";
        this.date = new Date();
    }

    // Геттер
    public String getTxt() {
        return txt;
    }

    // Сеттер
    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
