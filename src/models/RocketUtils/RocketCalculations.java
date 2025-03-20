package models.RocketUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class RocketCalculations {
    public BigDecimal timeToReachEscapeVelocity(BigDecimal velocity, int accelerationPerEngine, int numberOfEngines){
        BigDecimal numerator = BigDecimal.valueOf(accelerationPerEngine).multiply(BigDecimal.valueOf(numberOfEngines));
        return velocity.divide(numerator, MathContext.DECIMAL128);
    }
    public BigDecimal distanceToReachEscapeVelocity(BigDecimal velocity, int accelerationPerEngine, int numberOfEngines){
        BigDecimal numerator =  BigDecimal.valueOf(accelerationPerEngine)
                .multiply(BigDecimal.valueOf(numberOfEngines))
                .multiply(BigDecimal.valueOf(Math.pow(timeToReachEscapeVelocity(velocity, accelerationPerEngine, numberOfEngines).setScale(0, RoundingMode.DOWN).doubleValue(), 2)));
        return numerator.divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
    }
}
