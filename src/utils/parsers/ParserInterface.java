package utils.parsers;

import models.Celestials.Planet;

import java.util.Map;
import java.math.BigDecimal;

public interface ParserInterface {
        Map<String, Planet> parseFilePlanet(String name);
}
