package models;

import models.RocketUtils.RocketCalculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rocket {
    int numberOfEngines;
    int accelerationOfEachEngine;// m/s^2
    RocketCalculations  rocketCalculations = new RocketCalculations();

    public Rocket(int accelerationOfEachEngine, int numberOfEngines) {
        this.accelerationOfEachEngine = accelerationOfEachEngine;
        this.numberOfEngines = numberOfEngines;
    }
    public BigDecimal getTimeToReachEscapeVelocity(BigDecimal velocity) {
        return rocketCalculations.timeToReachEscapeVelocity(velocity, this.accelerationOfEachEngine, this.numberOfEngines);
    }
    public BigDecimal getDistanceToReachEscapeVelocity(BigDecimal velocity) {
        return rocketCalculations.distanceToReachEscapeVelocity(velocity, this.accelerationOfEachEngine, this.numberOfEngines);
    }
    public int getNumberOfEngines() {
        return numberOfEngines;
    }

    public int getAccelerationOfEachEngine() {
        return accelerationOfEachEngine;
    }

    public void setNumberOfEngines(int numberOfEngines) {
        this.numberOfEngines = numberOfEngines;
    }

    public void setAccelerationOfEachEngine(int accelerationOfEachEngine) {
        this.accelerationOfEachEngine = accelerationOfEachEngine;
    }

    public void displayInfo() {
        System.out.println( "Rocket{" +
                "numberOfEngines=" + numberOfEngines +
                ", accelerationOfEachEngine=" + accelerationOfEachEngine +
                "}\n");
    }
}

