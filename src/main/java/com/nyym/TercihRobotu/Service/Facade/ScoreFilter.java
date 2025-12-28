package com.nyym.TercihRobotu.Service.Facade;

import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.departments;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreFilter {

    public List<departments> filterByScore(StudentInfo student, List<departments> programs){

        String scoreType = student.getScoreType().toLowerCase();
        double ranking = student.getRanking();

        List<departments> recommendations =  new ArrayList<>();

        for(departments program : programs){
            if (program.getScore_type().toLowerCase().equals(scoreType)) {
                if (program.getProgram_success_rank() >= ranking){
                    recommendations.add(program);

                }
            }
        }
        return recommendations;
    };
}
