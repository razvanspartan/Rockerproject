package Service;

import models.Celestials.Planet;
import models.Rocket;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static CONSTANTS.Constants.*;

public class ServiceForCalculations {
    public BigDecimal timeToAlign(Planet startingPlanet, Planet destinationPlanet){
        BigDecimal startingPlanetPeriod = BigDecimal.valueOf(startingPlanet.getPeriod());
        BigDecimal destinationPlanetPeriod = BigDecimal.valueOf(destinationPlanet.getPeriod());
        return startingPlanetPeriod.multiply(destinationPlanetPeriod)
                .divide((startingPlanetPeriod.subtract(destinationPlanetPeriod)).abs(), MathContext.DECIMAL128);
    }
    public BigDecimal numberOfAllingmentsByATime(Planet startingPlanet, Planet destinationPlanet,  BigDecimal time){
        return time.multiply(DAYS_IN_A_YEAR).divide(timeToAlign(startingPlanet, destinationPlanet), MathContext.DECIMAL128);
    }
    public BigDecimal closestTimeToAlignAfterSomeTime(Planet startingPlanet, Planet destinationPlanet, BigDecimal time){
        BigDecimal numberOfAlignments = numberOfAllingmentsByATime(startingPlanet, destinationPlanet, time);
        if(numberOfAlignments.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0){
                return time;
        }
        else return time.add(timeToAlign(startingPlanet,destinationPlanet).multiply(numberOfAlignments));
    }
    public BigDecimal convertRadiansToDegrees(BigDecimal radians){
        return radians.multiply(BigDecimal.valueOf(180/Math.PI));
    }
    public String getTimeinDaysMinutesSeconds(BigDecimal seconds){
        BigDecimal[] divisionDay = seconds.divideAndRemainder(SECONDS_IN_A_DAY);
        BigDecimal[] divisionHours = divisionDay[1].divideAndRemainder(SECONDS_IN_A_HOUR);
        BigDecimal[] divisionMinutes = divisionHours[1].divideAndRemainder(SECONDS_IN_A_MINUTE);

        int nrOfDays =  divisionDay[0].intValue();
        int nrOfHours =  divisionHours[0].intValue();
        int nrOfMinutes =  divisionMinutes[0].intValue();
        return String.valueOf(nrOfDays) + " Days" + " " + String.valueOf(nrOfHours) + " Hours" + " " + String.valueOf(nrOfMinutes) + " Minutes" +" "+ String.valueOf(divisionMinutes[1].setScale(0, RoundingMode.HALF_UP)) + " Seconds";
    }
    public BigDecimal getCruisingSpeed(Planet startingPlanet,  Planet destinationPlanet) {
        return startingPlanet.getEscapeVelocity().compareTo(destinationPlanet.getEscapeVelocity()) == 1 ? startingPlanet.getEscapeVelocity() : destinationPlanet.getEscapeVelocity();
    }
    public BigDecimal getCruisingDistance(Planet startingPlanet, Planet destinationPlanet, Rocket rocket) {
        BigDecimal cruisingVelocity = getCruisingSpeed(startingPlanet, destinationPlanet);
        BigDecimal distanceToAccelerateOrDeaccelerate = rocket.getDistanceToReachEscapeVelocity(cruisingVelocity);
        BigDecimal distanceBetweenPlanets = BigDecimal.valueOf(Math.abs(startingPlanet.getOrbitalRadius()-destinationPlanet.getOrbitalRadius())).multiply(AU_VALUE);
        return distanceBetweenPlanets.subtract(distanceToAccelerateOrDeaccelerate.multiply(BigDecimal.valueOf(2)))
                .subtract(startingPlanet.getRadius())
                .subtract(destinationPlanet.getRadius());
    }
    public BigDecimal getJourneyTimeAtCruisingVelocity(Planet startingPlanet, Planet destinationPlanet, Rocket rocket) {
        return getCruisingDistance(startingPlanet, destinationPlanet, rocket).divide(getCruisingSpeed(startingPlanet, destinationPlanet), MathContext.DECIMAL128);
    }
    public BigDecimal getTotalJourneyTime(Planet startingPlanet, Planet destinationPlanet, Rocket rocket) {
        return getJourneyTimeAtCruisingVelocity(startingPlanet, destinationPlanet, rocket)
                .add(rocket.getTimeToReachEscapeVelocity(getCruisingSpeed(startingPlanet, destinationPlanet)).multiply(BigDecimal.valueOf(2)));
    }
    public BigDecimal getDistanceFromPlanetAtAccOrDeacc(Planet targetPlanet, Planet otherPlanet, Rocket rocket){
        return targetPlanet.getRadius().add(rocket.getDistanceToReachEscapeVelocity(getCruisingSpeed(targetPlanet, otherPlanet)));
    }
}
