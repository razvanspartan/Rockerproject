import Service.ServiceForCalculations;
import models.Celestials.Planet;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;

import static CONSTANTS.Constants.EARTH_MASS;

public class ServiceForCalculationsTest {

    @BeforeEach
    void setUp() {
        ServiceForCalculations service = new ServiceForCalculations();
        Planet earth = new Planet("Earth", BigDecimal.valueOf(12800), EARTH_MASS, 365, 1);
        Planet mars = new Planet("Mars", BigDecimal.valueOf(5800), EARTH_MASS.multiply(BigDecimal.valueOf(0.11)), 687, 1.52);
        Planet neptune = new Planet("Neptune", BigDecimal.valueOf(48400),  EARTH_MASS.multiply(BigDecimal.valueOf(17)), 60148, 30.06);
    }
}
