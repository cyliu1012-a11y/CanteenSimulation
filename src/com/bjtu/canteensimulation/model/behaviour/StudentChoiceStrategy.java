package com.bjtu.canteensimulation.model.behaviour;

import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.ServiceWindow;
import java.util.List;


public interface StudentChoiceStrategy
{
    ServiceWindow chooseWindow(Student student, List<ServiceWindow> windows);


}
