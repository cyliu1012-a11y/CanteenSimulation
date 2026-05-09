package com.bjtu.canteensimulation.engine.core;

public abstract class SimulationEvent
{
    protected final double time;

    public SimulationEvent(double time) {
        this.time = time;
    }

    public abstract void process();

    public double getTime() {
        return time;
    }

}
