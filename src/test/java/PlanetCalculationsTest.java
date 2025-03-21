import Service.ServiceForCalculations;
import models.Celestials.Planet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static CONSTANTS.Constants.EARTH_MASS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetCalculationsTest {
    private ServiceForCalculations service;
    private Planet earth;
    private Planet mars;
    private Planet neptune;
    @BeforeEach
    void setUp() {
        service = new ServiceForCalculations();
        earth = new Planet("Earth", BigDecimal.valueOf(12800), EARTH_MASS, 365, 1);
        mars = new Planet("Mars", BigDecimal.valueOf(5800), EARTH_MASS.multiply(BigDecimal.valueOf(0.11)), 687, 1.52);
        neptune = new Planet("Neptune", BigDecimal.valueOf(48400),  EARTH_MASS.multiply(BigDecimal.valueOf(17)), 60148, 30.06);
    }
    @Test
    void getRadius(){
        BigDecimal expectedRadius =   BigDecimal.valueOf(6400);
        BigDecimal actualRadius = earth.getRadius();
        Assertions.assertEquals(expectedRadius, actualRadius.setScale(0, RoundingMode.HALF_UP));
    }
}
