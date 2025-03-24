package com.example;

import com.example.Service.ServiceForCalculations;
import com.example.models.Celestials.Planet;
import com.example.models.Rocket;
import com.example.parsers.ParsePlanetFile;
import com.example.parsers.ParseRocketFile;
import com.example.view.App;
import javafx.application.Application;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

import static com.example.CONSTANTS.Constants.DAYS_IN_A_YEAR;

public class Main {
    public static void main(String[] args) throws Exception {
        Application.launch(App.class, args);
    }

}