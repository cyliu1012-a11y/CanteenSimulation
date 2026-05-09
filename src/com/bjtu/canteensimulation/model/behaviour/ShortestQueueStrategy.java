package com.bjtu.canteensimulation.model.behaviour;

import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import java.util.List;

public class ShortestQueueStrategy implements StudentChoiceStrategy
{
    @Override
    public ServiceWindow chooseWindow(Student student, List<ServiceWindow> windows) {
        ServiceWindow best = windows.get(0);
        for (ServiceWindow w : windows) {
            if (w.getQueueSize() < best.getQueueSize()) {
                best = w;
            }
        }
        return best;
    }

}
