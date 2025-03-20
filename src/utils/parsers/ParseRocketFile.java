package utils.parsers;

import models.Celestials.Planet;
import models.Rocket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;

import static CONSTANTS.Constants.EARTH_MASS;

public class ParseRocketFile {
    public void parseRocketFile(String fileName, Rocket rocket){
        int nrOfEngines;
        int accelerationPerEngine;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
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
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
