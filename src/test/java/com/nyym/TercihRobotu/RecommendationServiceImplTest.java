package com.nyym.TercihRobotu;

import com.nyym.TercihRobotu.DAO.AppDAO;
import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.departments;
import com.nyym.TercihRobotu.Service.Facade.KeywordMatcher;
import com.nyym.TercihRobotu.Service.Facade.ScoreFilter;
import com.nyym.TercihRobotu.Service.RecommendationFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceImplTest{

    @Mock
    private AppDAO appDAO; // Taklit veritabanı

    @Mock
    private KeywordMatcher keywordMatcher;

    @Mock
    private ScoreFilter scoreFilter;

    @InjectMocks
    private RecommendationFacade recommendationService; // Test edilecek sınıf

    @Test
    void whenStudentNotFound_thenReturnEmptyList() {
        // 1. Hazırlık
        when(appDAO.getStudentInfoById(anyInt())).thenReturn(null);

        // 2. Testi çalıştır
        List<departments> result = recommendationService.getRecommendationsForStudent(1);

        // 3. Doğrulama
        assertTrue(result.isEmpty());
    }


    @Test
    void forEngineeringStudent_shouldReturnEngineeringPrograms() {
        // 1. Test verileri hazırla
        StudentInfo student = new StudentInfo();
        student.setInterestAreas("yazılım, robotik");
        student.setScoreType("SAY");
        student.setRanking(50000);

        departments engineeringProgram = new departments();
        engineeringProgram.setProgram_name("Bilgisayar Mühendisliği");
        engineeringProgram.setScore_type("SAY");
        engineeringProgram.setProgram_success_rank(60000);

        // 2. Mock davranışları
        when(appDAO.getStudentInfoById(1)).thenReturn(student);
        when(appDAO.getAllDepartments()).thenReturn(List.of(engineeringProgram));
        when(keywordMatcher.filterByInterests(student, List.of(engineeringProgram)))
                .thenReturn(List.of(engineeringProgram));
        when(scoreFilter.filterByScore(student, List.of(engineeringProgram)))
                .thenReturn(List.of(engineeringProgram));


        // 3. Testi çalıştır
        List<departments> result = recommendationService.getRecommendationsForStudent(1);

        // 4. Doğrulamalar
        assertFalse(result.isEmpty());
        assertEquals("Bilgisayar Mühendisliği", result.get(0).getProgram_name());
    }

}
