package com.example.models.Celestials.CelestialUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.example.CONSTANTS.Constants.GRAVITATIONAL_CONSTANT;
import static com.example.CONSTANTS.Constants.VALUE_OF_PI;

public class PlanetCalculations {
    public BigDecimal getEscapeVelocity(BigDecimal mass, BigDecimal diameter) {
        BigDecimal radius = getRadius(diameter);
        BigDecimal numerator = GRAVITATIONAL_CONSTANT.multiply(mass).multiply(BigDecimal.valueOf(2));
        BigDecimal escapeVelo = numerator.divide(radius, MathContext.DECIMAL128);
        escapeVelo = BigDecimal.valueOf(Math.sqrt(escapeVelo.doubleValue()));
        return escapeVelo.setScale(0, RoundingMode.HALF_UP);
    }
    public BigDecimal getAngularVelocity(BigDecimal period) {
        return VALUE_OF_PI.multiply(BigDecimal.valueOf(2))
                .divide(period, MathContext.DECIMAL128);
    }
    public BigDecimal getRadius(BigDecimal diameter) {
        return  diameter.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    }

    //getangular position returns position in rads
    public BigDecimal getAngularPosition(BigDecimal period, BigDecimal time){
        BigDecimal angularVelocity = getAngularVelocity(period);
        BigDecimal angularPos = angularVelocity.multiply(time).setScale(15, RoundingMode.HALF_UP);
        angularPos = angularPos.remainder(BigDecimal.valueOf(Math.PI * 2));
        return angularPos;
    }
    public List<Double> getCoordinates(BigDecimal angularPosition, BigDecimal orbitalRadius){
        List<Double> coordinates = new ArrayList<>();
        BigDecimal x = BigDecimal.valueOf(Math.cos(angularPosition.doubleValue()))
                .multiply(orbitalRadius)
                .setScale(3, RoundingMode.HALF_UP);
        BigDecimal y = BigDecimal.valueOf(Math.sin(angularPosition.doubleValue()))
                .multiply(orbitalRadius)
                .setScale(3, RoundingMode.HALF_UP);
        coordinates.add(x.doubleValue());
        coordinates.add(y.doubleValue());
        return coordinates;
    }

}
