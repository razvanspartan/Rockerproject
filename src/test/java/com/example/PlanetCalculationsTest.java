package com.example;

import com.example.Service.ServiceForCalculations;
import com.example.models.Celestials.Planet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.example.CONSTANTS.Constants.EARTH_MASS;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetCalculationsTest {
    private ServiceForCalculations service;
    private Planet earth;
    private Planet mars;
    private Planet neptune;
    @BeforeEach
    void setUp() {
        service = new ServiceForCalculations();
        earth = new Planet("Earth", BigDecimal.valueOf(12800* Math.pow(10,3)), EARTH_MASS, 365, 1);
        mars = new Planet("Mars", BigDecimal.valueOf(5800* Math.pow(10,3)), EARTH_MASS.multiply(BigDecimal.valueOf(0.11)), 687, 1.52);
        neptune = new Planet("Neptune", BigDecimal.valueOf(48400* Math.pow(10,3)),  EARTH_MASS.multiply(BigDecimal.valueOf(17)), 60148, 30.06);
    }
    @Test
    void getRadius(){
        BigDecimal expectedRadius =   BigDecimal.valueOf(6400000);
        BigDecimal actualRadius = earth.getRadius();
        Assertions.assertEquals(expectedRadius, actualRadius.setScale(0, RoundingMode.HALF_UP));
    }

    @Test
    void getEscapeVelocity(){
        BigDecimal expectedEscapeVelocityEarth =   BigDecimal.valueOf(11183);
        BigDecimal actualEscapeVelocityEarth = earth.getEscapeVelocity();

        BigDecimal expectedEscapeVelocityNeptune =   BigDecimal.valueOf(23712);
        BigDecimal actualEscapeVelocityNeptune = neptune.getEscapeVelocity();
        Assertions.assertEquals(expectedEscapeVelocityEarth, actualEscapeVelocityEarth);
        Assertions.assertEquals(expectedEscapeVelocityNeptune, actualEscapeVelocityNeptune);
    }
   /* @Test
    void getAngularVelocity(){
        BigDecimal expectedAngularVelocity = BigDecimal.valueOf(1.99);
        BigDecimal actualAngularVelocity = earth.getAngularVelocity().setScale(2, RoundingMode.HALF_UP);
        Assertions.assertEquals(expectedAngularVelocity,actualAngularVelocity);
    }*/

}
