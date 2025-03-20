package utils.parsers;

import models.Celestials.Planet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static CONSTANTS.Constants.EARTH_MASS;

public class ParsePlanetFile {
    final String planetRegex = "^[A-Za-z0-9\\-]+: diameter = ([0-9,]+) km, mass = (([0-9.]+) Earths|[0-9]+ \\* 10\\^[0-9]+ kg)$";

    final boolean validateEntry(String line) {
        Pattern pattern = Pattern.compile(planetRegex);
        Matcher matcher = pattern.matcher(line);
        return matcher.matches();
    };
    public void  parseFilePlanet(String fileName, Map<String, Planet> planets){
            try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
                String line;

                while((line=br.readLine())!=null) {
                    if (!validateEntry(line)) {
                        throw new Exception("Planet file contains invalid entry, please modify the file to contain valid entries of the type [PlanetName]: diameter = [number] km, mass = [number] Earths");
                    }
                    String[] fields = line.split(" ");
                    fields[0] = fields[0].replace(":", ""); //Remove :
                    String planetName = fields[0];
                    BigDecimal diameterinkm = BigDecimal.valueOf(Integer.parseInt(fields[3].replace(",", "")));

                    BigDecimal mass;
                    if (planetName.equals("Earth")) {
                        mass = EARTH_MASS;
                    } else {
                        mass = EARTH_MASS.multiply(BigDecimal.valueOf(Double.parseDouble(fields[7])));
                    }
                    Planet newPlanet = new Planet(planetName, diameterinkm.multiply(BigDecimal.valueOf(Math.pow(10,3))), mass, 0, 0);
                    planets.put(planetName, newPlanet);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }
    public void parseFileSolarSystem(String fileName, Map<String, Planet> planets){
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;

            while((line=br.readLine())!=null) {
                String[] fields = line.split(" ");
                fields[0] = fields[0].replace(":", ""); //Remove :
                String planetName = fields[0];
                int period = Integer.parseInt(fields[3].replace(",", ""));
                double orbitalRadius = Double.parseDouble(fields[8]);
                Planet currentPlanet = planets.get(planetName);
                currentPlanet.setPeriod(period);
                currentPlanet.setOrbitalRadius(orbitalRadius);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
