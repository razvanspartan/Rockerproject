package com.example.parsers;

import com.example.models.Rocket;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseRocketFile {
    public void parseRocketFile(String fileName, Rocket rocket) throws Exception {
        int nrOfEngines;
        int accelerationPerEngine;
        BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            line = br.readLine();
            String[] lineSplit;
            lineSplit = line.split(" ");
            nrOfEngines = Integer.parseInt(lineSplit[4]);
            line = br.readLine();
            lineSplit = line.split(" ");
            accelerationPerEngine =  Integer.parseInt(lineSplit[3]);
            rocket.setAccelerationOfEachEngine(accelerationPerEngine);
            rocket.setNumberOfEngines(nrOfEngines);
    }
}
