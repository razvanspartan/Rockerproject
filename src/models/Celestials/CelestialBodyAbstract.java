package models.Celestials;

import java.math.BigDecimal;

public abstract class CelestialBodyAbstract {
    String name;
    BigDecimal diameter; //km
    BigDecimal mass; // kg
    int period; // days
    double orbitalRadius; // AU

    public double getOrbitalRadius() {
        return orbitalRadius;
    }

    public int getPeriod() {
        return period;
    }

    public BigDecimal getMass() {
        return mass;
    }

    public BigDecimal getDiameter() {
        return diameter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiameter(BigDecimal diameter) {
        this.diameter = diameter;
    }

    public void setMass(BigDecimal mass) {
        this.mass = mass;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setOrbitalRadius(double orbitalRadius) {
        this.orbitalRadius = orbitalRadius;
    }

    public CelestialBodyAbstract(String name, BigDecimal diameter, BigDecimal mass, int period, double orbitalRadius) {
        this.name = name;
        this.diameter = diameter;
        this.mass = mass;
        this.period = period;
        this.orbitalRadius = orbitalRadius;
    }

    abstract void displayInfo();
}
