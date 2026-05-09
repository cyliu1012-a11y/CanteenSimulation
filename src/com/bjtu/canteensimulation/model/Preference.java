package com.bjtu.canteensimulation.model;

import java.util.Map;

public class Preference
{
    private final Map<String, Double> typeScores;
    private final double queueSensitivity;
    private final double priceSensitivity;

    public Preference(Map<String, Double> typeScores, double queueSensitivity, double priceSensitivity) {
        this.typeScores = typeScores;
        this.queueSensitivity = queueSensitivity;
        this.priceSensitivity = priceSensitivity;}
    public double getTypeScore(String type) {
        return typeScores.getOrDefault(type, 0.0);
    }

    public double getQueueSensitivity() { return queueSensitivity; }
    public double getPriceSensitivity() { return priceSensitivity; }

}
