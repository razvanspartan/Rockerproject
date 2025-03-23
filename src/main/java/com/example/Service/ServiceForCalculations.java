package com.example.Service;

import com.example.models.Celestials.Planet;
import com.example.models.Rocket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.CONSTANTS.Constants.*;

public class ServiceForCalculations {

    public boolean willCollide(Planet startingPlanet, Planet currentPlanet, Planet destinationPlanet, BigDecimal time){
        List<Double> startingPlanetCoords = startingPlanet.getCoordinates(time);
        List<Double> destinationPlanetCoords = destinationPlanet.getCoordinates(time);
        List<Double> currentPlanetCoords = currentPlanet.getCoordinates(time);
            double distance = Math.abs((destinationPlanetCoords.get(1) -  startingPlanetCoords.get(1)) * currentPlanetCoords.get(0)
            - (destinationPlanetCoords.get(0) -  startingPlanetCoords.get(0)) *  currentPlanetCoords.get(1)
                    + startingPlanetCoords.get(1) * destinationPlanetCoords.get(0) - destinationPlanetCoords.get(1)*startingPlanetCoords.get(0));
            double denominator = Math.sqrt(Math.pow(destinationPlanetCoords.get(1) - startingPlanetCoords.get(1), 2) + Math.pow(destinationPlanetCoords.get(0) - startingPlanetCoords.get(0), 2));
        return distance/denominator <= currentPlanet.getRadius().doubleValue();
    }
    public boolean collisionCheckerSolarSystemNotMoving(Planet startingPlanet, Planet destinationPlanet,BigDecimal time, List<Planet> planetList){
        if(planetList.indexOf(startingPlanet) > planetList.indexOf(destinationPlanet)){
            collisionCheckerSolarSystemNotMoving(destinationPlanet, startingPlanet, time, planetList);
        }
        for(int i = planetList.indexOf(startingPlanet)+1; i < planetList.indexOf(destinationPlanet); i++){
            System.out.println(planetList.get(i).getName() + " " + willCollide(startingPlanet, planetList.get(i), destinationPlanet, time));
            if(willCollide(startingPlanet, planetList.get(i), destinationPlanet, time)){
                return false;
            }
        }
        return true;
    }
    public boolean collisionCheckerSolarSystemMoving (Planet startingPlanet, Planet destinationPlanet,BigDecimal time, List<Planet> planetList, Rocket rocket){
        if(planetList.indexOf(startingPlanet) > planetList.indexOf(destinationPlanet)){
            return collisionCheckerSolarSystemNotMoving(destinationPlanet, startingPlanet, time, planetList);
        }
        for(int i = planetList.indexOf(startingPlanet)+1; i < planetList.indexOf(destinationPlanet); i++){
            System.out.println(planetList.get(i).getName() + " " + willCollide(startingPlanet, planetList.get(i), destinationPlanet, time));
            if(willCollide(startingPlanet, planetList.get(i), destinationPlanet,
                    (getJourneyTimeAtCruisingVelocity(startingPlanet, destinationPlanet, rocket, getCruisingSpeed(startingPlanet,destinationPlanet)).add(rocket.getTimeToReachEscapeVelocity(getCruisingSpeed(startingPlanet, destinationPlanet))
                            .divide(SECONDS_IN_A_DAY, MathContext.DECIMAL128)
                            .setScale(2, RoundingMode.HALF_UP))))){
                return false;
            }
        }
        return true;

    }
    public BigDecimal timeToAlign(Planet startingPlanet, Planet destinationPlanet){
        BigDecimal startingPlanetPeriod = BigDecimal.valueOf(startingPlanet.getPeriod());
        BigDecimal destinationPlanetPeriod = BigDecimal.valueOf(destinationPlanet.getPeriod());
        return startingPlanetPeriod.multiply(destinationPlanetPeriod)
                .divide((startingPlanetPeriod.subtract(destinationPlanetPeriod)).abs(), MathContext.DECIMAL128).divide(DAYS_IN_A_YEAR, MathContext.DECIMAL128);
    }

    public BigDecimal numberOfAllingmentsByATime(Planet startingPlanet, Planet destinationPlanet,  BigDecimal time){
        return time.divide(timeToAlign(startingPlanet, destinationPlanet), MathContext.DECIMAL128);
    }
    public BigDecimal closestTimeToAlignAfterSomeTime(Planet startingPlanet, Planet destinationPlanet, BigDecimal time) {
        BigDecimal timeToAlign = timeToAlign(startingPlanet, destinationPlanet);

        if (timeToAlign.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("timeToAlign cannot be zero.");
        }

        BigDecimal numberOfAlignments = numberOfAllingmentsByATime(startingPlanet, destinationPlanet, time);
        BigDecimal lastAlignmentTime = timeToAlign.multiply(numberOfAlignments.setScale(0, RoundingMode.FLOOR));
        BigDecimal nextAlignmentTime = lastAlignmentTime.add(timeToAlign);

        if (nextAlignmentTime.compareTo(time.add(BigDecimal.TEN)) > 0) {
            return BigDecimal.valueOf(-1);
        } else {
            return nextAlignmentTime;
        }
    }
    public BigDecimal convertRadiansToDegrees(BigDecimal radians){
        return radians.multiply(BigDecimal.valueOf(180/Math.PI));
    }
    public String toStringGetTime(List<Integer> values){
        return String.valueOf(values.get(0)) + " Days" + " " + String.valueOf(values.get(1)) + " Hours" + " " + String.valueOf(values.get(2)) + " Minutes" +" "+ values.get(3) + " Seconds";
    }
    public List<Integer> getTimeinDaysMinutesSeconds(BigDecimal seconds){
        BigDecimal[] divisionDay = seconds.divideAndRemainder(SECONDS_IN_A_DAY);
        BigDecimal[] divisionHours = divisionDay[1].divideAndRemainder(SECONDS_IN_A_HOUR);
        BigDecimal[] divisionMinutes = divisionHours[1].divideAndRemainder(SECONDS_IN_A_MINUTE);

        int nrOfDays =  divisionDay[0].intValue();
        int nrOfHours =  divisionHours[0].intValue();
        int nrOfMinutes =  divisionMinutes[0].intValue();
        List<Integer> timeinDays = new ArrayList<>();
        timeinDays.add(nrOfDays);
        timeinDays.add(nrOfHours);
        timeinDays.add(nrOfMinutes);
        timeinDays.add(divisionMinutes[1].setScale(0, RoundingMode.HALF_UP).toBigInteger().intValue());
        return timeinDays;
    }
    public BigDecimal getCruisingSpeed(Planet startingPlanet,  Planet destinationPlanet) {
        return startingPlanet.getEscapeVelocity().compareTo(destinationPlanet.getEscapeVelocity()) == 1 ? startingPlanet.getEscapeVelocity() : destinationPlanet.getEscapeVelocity();
    }
    //curising Velocity is an optional parameter
    public BigDecimal getCruisingDistance(Planet startingPlanet, Planet destinationPlanet, Rocket rocket, BigDecimal cruisingVelocity) {
        if(cruisingVelocity == null) {
            cruisingVelocity = getCruisingSpeed(startingPlanet, destinationPlanet);
        }
        BigDecimal distanceToAccelerateOrDeaccelerate = rocket.getDistanceToReachEscapeVelocity(cruisingVelocity);
        BigDecimal distanceBetweenPlanets = BigDecimal.valueOf(Math.abs(startingPlanet.getOrbitalRadius()-destinationPlanet.getOrbitalRadius())).multiply(AU_VALUE);
        return distanceBetweenPlanets.subtract(distanceToAccelerateOrDeaccelerate.multiply(BigDecimal.valueOf(2)))
                .subtract(startingPlanet.getRadius())
                .subtract(destinationPlanet.getRadius());
    }
    public BigDecimal getJourneyTimeAtCruisingVelocity(Planet startingPlanet, Planet destinationPlanet, Rocket rocket, BigDecimal cruisingVelocity) {
        return getCruisingDistance(startingPlanet, destinationPlanet, rocket, cruisingVelocity).divide(getCruisingSpeed(startingPlanet, destinationPlanet), MathContext.DECIMAL128);
    }
    public BigDecimal getTotalJourneyTime(Planet startingPlanet, Planet destinationPlanet, Rocket rocket, BigDecimal cruisingVelocity) {
        return getJourneyTimeAtCruisingVelocity(startingPlanet, destinationPlanet, rocket, cruisingVelocity)
                .add(rocket.getTimeToReachEscapeVelocity(getCruisingSpeed(startingPlanet, destinationPlanet)).multiply(BigDecimal.valueOf(2)));
    }
    public BigDecimal getDistanceFromPlanetAtAccOrDeacc(Planet targetPlanet, Planet otherPlanet, Rocket rocket){
        return targetPlanet.getRadius().add(rocket.getDistanceToReachEscapeVelocity(getCruisingSpeed(targetPlanet, otherPlanet)));
    }
}
