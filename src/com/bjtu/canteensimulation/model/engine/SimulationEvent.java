package com.bjtu.canteensimulation.model.engine;

public abstract class SimulationEvent implements Comparable<SimulationEvent> {

    protected long time;

    public SimulationEvent(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public abstract void process(SimulationEngine engine);

    @Override
    public int compareTo(SimulationEvent o) {
        return Long.compare(this.time, o.time);
    }
}