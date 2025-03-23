package com.example.models.Celestials;

import com.example.models.Celestials.CelestialUtils.PlanetCalculations;

import java.math.BigDecimal;
import java.util.List;

import static com.example.CONSTANTS.Constants.AU_VALUE;
import static com.example.CONSTANTS.Constants.SECONDS_IN_A_DAY;

public class Planet extends CelestialBodyAbstract{
    PlanetCalculations  planetCalculations = new PlanetCalculations();
    public Planet(String name, BigDecimal diameter, BigDecimal mass, int period, double orbitalRadius) {
        super(name, diameter, mass, period, orbitalRadius);
    }
    public BigDecimal getEscapeVelocity(){
        return planetCalculations.getEscapeVelocity(this.mass, this.diameter);
    }
    public BigDecimal getRadius(){
        return planetCalculations.getRadius(this.diameter);
    }
    public BigDecimal getAngularVelocity(){
        return planetCalculations.getAngularVelocity(BigDecimal.valueOf(this.period).multiply(SECONDS_IN_A_DAY));
    }
    public BigDecimal getAngularPosition(BigDecimal time){
        return planetCalculations.getAngularPosition(BigDecimal.valueOf(this.period), time );
    }
    public List<Double> getCoordinates(BigDecimal time){
        return planetCalculations.getCoordinates(getAngularPosition(time), BigDecimal.valueOf(this.orbitalRadius).multiply(AU_VALUE));
    }
    @Override
    public void displayInfo() {
        System.out.println( "Planet{" +
                "name='" + name + '\'' +
                ", diameter=" + diameter +
                ", mass=" + mass +
                ", period=" + period +
                ", orbitalRadius=" + orbitalRadius +
                "} \n");
    }
}
