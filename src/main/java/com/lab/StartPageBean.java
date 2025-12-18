package com.lab;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@RequestScoped
public class StartPageBean {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public String getCurrentDateTime() {
        return LocalDateTime.now().format(FORMATTER);
    }

    public String navigateToMain() {
        return "main";
    }
}

