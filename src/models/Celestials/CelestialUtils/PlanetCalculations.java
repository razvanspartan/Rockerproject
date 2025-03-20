package models.Celestials.CelestialUtils;

import models.Celestials.Planet;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static CONSTANTS.Constants.GRAVITATIONAL_CONSTANT;
import static CONSTANTS.Constants.VALUE_OF_PI;
import static java.lang.Math.sqrt;

public class PlanetCalculations {
    public BigDecimal getEscapeVelocity(BigDecimal mass, BigDecimal diameter) {
        BigDecimal radius = diameter.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
        BigDecimal numerator = GRAVITATIONAL_CONSTANT.multiply(mass).multiply(BigDecimal.valueOf(2));
        BigDecimal escapeVelo = numerator.divide(radius, MathContext.DECIMAL128);
        escapeVelo = new BigDecimal(Math.sqrt(escapeVelo.doubleValue()));
        return escapeVelo.setScale(0, RoundingMode.HALF_UP);
    }
    public BigDecimal getAngularVelocity(BigDecimal period) {
        return VALUE_OF_PI.multiply(BigDecimal.valueOf(2))
                .divide(period, MathContext.DECIMAL128);
    }
    public BigDecimal getRadius(BigDecimal diameter) {
        return  diameter.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    }
    public BigDecimal getAngularPosition(BigDecimal period, BigDecimal time){
        return getAngularVelocity(period).multiply(time);
    }
    //public BigDecimal getAngularWidth(Big)
}
