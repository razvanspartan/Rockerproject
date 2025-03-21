package com.example.parsers;

import com.example.models.Celestials.Planet;

import java.util.Map;

public interface ParserInterface {
        Map<String, Planet> parseFilePlanet(String name);
}
