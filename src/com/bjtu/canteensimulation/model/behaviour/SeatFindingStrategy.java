package com.bjtu.canteensimulation.model.behaviour;

import com.bjtu.canteensimulation.model.entity.Student;
import com.bjtu.canteensimulation.model.entity.SeatPool;


public interface SeatFindingStrategy
{
    boolean findSeat(Student student, SeatPool seatPool);

}
