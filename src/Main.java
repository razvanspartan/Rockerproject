import Service.ServiceForCalculations;
import models.Celestials.Planet;
import models.Rocket;
import utils.parsers.ParsePlanetFile;
import utils.parsers.ParseRocketFile;
import utils.parsers.ParserInterface;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

import static CONSTANTS.Constants.DAYS_IN_A_YEAR;

public class Main {
    public static void main(String[] args) {
        Map<String, Planet> planetData = new HashMap<String, Planet>();
        List<Planet> planets = new ArrayList<Planet>();

        ServiceForCalculations service= new ServiceForCalculations();
        Rocket rocket = new Rocket(0,0);
        ParsePlanetFile parserForPlanets = new ParsePlanetFile();
        ParseRocketFile parserForRocket = new ParseRocketFile();
        parserForPlanets.parseFilePlanet("/home/razvanspartan/IdeaProjects/Rocketproject/src/data/input/Planetary_Data.txt", planetData);
        parserForPlanets.parseFileSolarSystem("/home/razvanspartan/IdeaProjects/Rocketproject/src/data/input/Solar_System_Data.txt", planetData, planets);
        parserForRocket.parseRocketFile("/home/razvanspartan/IdeaProjects/Rocketproject/src/data/input/Rocket_Data.txt",  rocket);

        System.out.println("Escape velocities for each planet: ");
        planetData.forEach((planetName, planet) -> System.out.println(planetName + " " + planet.getEscapeVelocity().divide(BigDecimal.valueOf(1000), MathContext.DECIMAL128).setScale(1,  RoundingMode.DOWN)+" km/s"));
        rocket.displayInfo();
        System.out.println("Time to reach escape veloccity from earth with our rocket: " + rocket.getTimeToReachEscapeVelocity(planetData.get("Earth").getEscapeVelocity())+ " seconds");
        System.out.println("Distance from earth to the point where we reached escape velocity with our rocket: " + rocket.getDistanceToReachEscapeVelocity(planetData.get("Earth").getEscapeVelocity()).setScale(1, RoundingMode.HALF_UP)+ " m");
        Planet earth = planetData.get("Earth");
        Planet mars = planetData.get("Mars");

        BigDecimal cruisingSpeed = service.getCruisingSpeed(earth, mars);
        System.out.println("\n Cruising Speed: " + cruisingSpeed + " m/s");

        BigDecimal cruisingDistance = service.getCruisingDistance(earth, mars, rocket);
        System.out.println("Cruising Distance: " + cruisingDistance + " meters");

        BigDecimal journeyTimeAtCruising = service.getJourneyTimeAtCruisingVelocity(earth, mars, rocket);
        System.out.println("Journey Time at Cruising Velocity: " + journeyTimeAtCruising + " seconds");

        BigDecimal totalJourneyTime = service.getTotalJourneyTime(earth, mars, rocket);
        System.out.println("Total Journey Time: " + totalJourneyTime + " seconds");

        System.out.println("Total Journey Time (formatted): " + service.getTimeinDaysMinutesSeconds(totalJourneyTime));

        BigDecimal distanceFromEarthAtAcc = service.getDistanceFromPlanetAtAccOrDeacc(earth, mars, rocket);
        System.out.println("Distance from Earth at acceleration: " + distanceFromEarthAtAcc + " meters");

        BigDecimal distanceFromMarsAtDeacc = service.getDistanceFromPlanetAtAccOrDeacc(mars, earth, rocket);
        System.out.println("Distance from Mars at deceleration: " + distanceFromMarsAtDeacc + " meters");

        System.out.println(service.convertRadiansToDegrees(earth.getAngularPosition(BigDecimal.valueOf(100).multiply(DAYS_IN_A_YEAR))).setScale(2,RoundingMode.HALF_UP));
        System.out.println(service.closestTimeToAlignAfterSomeTime(earth,mars,BigDecimal.valueOf(100)).divide(DAYS_IN_A_YEAR, MathContext.DECIMAL128).setScale(2,RoundingMode.HALF_UP));

        planets.forEach(planet -> System.out.println(planet.getName()));
        System.out.println(service.collisionCheckerSolarSystemNotMoving(planetData.get("Earth"), planetData.get("Uranus"), service.closestTimeToAlignAfterSomeTime(planetData.get("Earth"), planetData.get("Uranus"), BigDecimal.valueOf(100).multiply(DAYS_IN_A_YEAR)), planets));
        System.out.println("STOPPP!!! \n \n");
        System.out.println(service.collisionCheckerSolarSystemNotMoving(planetData.get("Earth"), planetData.get("Uranus"), BigDecimal.valueOf(2), planets));

    }// /home/razvanspartan/IdeaProjects/Rocketproject/src/data/input/Planetary_Data.txt
   // "/home/razvanspartan/IdeaProjects/Rocketproject/src/data/input/Solar_System_Data.txt"
}