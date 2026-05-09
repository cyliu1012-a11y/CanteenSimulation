package com.bjtu.canteensimulation.model.behaviour;

import com.bjtu.canteensimulation.model.behaviour.StudentChoiceStrategy;
import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import java.util.List;

public class UtilityBasedStrategy implements StudentChoiceStrategy {
    @Override
    public ServiceWindow chooseWindow(Student student, List<ServiceWindow> windows) {
        double maxUtility = -1;
        ServiceWindow best = windows.get(0);

        for (ServiceWindow w : windows) {
            double utility = calculate(student, w);
            if (utility > maxUtility) {
                maxUtility = utility;
                best = w;
            }
        }
        return best;
    }

    private double calculate(Student s, ServiceWindow w) {
        double like = s.getPreference().getTypeScore(w.getType());
        double queue = 1.0 / (1 + w.getQueueSize() * s.getPreference().getQueueSensitivity());
        return like * queue;
    }

}
