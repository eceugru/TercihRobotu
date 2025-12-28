package com.nyym.TercihRobotu.Service;

import com.nyym.TercihRobotu.Entity.departments;

import java.util.List;

public interface RecommendationService {
    public List<departments> getRecommendationsForStudent (int theStudentd);
}
