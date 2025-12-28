package com.nyym.TercihRobotu.Service;

import com.nyym.TercihRobotu.DAO.AppDAO;
import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.departments;
import com.nyym.TercihRobotu.Entity.users;
import com.nyym.TercihRobotu.Service.Facade.KeywordMatcher;
import com.nyym.TercihRobotu.Service.Facade.ScoreFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationFacade implements RecommendationService{
    @Autowired
    private AppDAO appDAO;
    private KeywordMatcher keywordMatcher;
    private ScoreFilter scoreFilter;
    @Autowired
    public RecommendationFacade(AppDAO appDAO, KeywordMatcher keywordMatcher, ScoreFilter scoreFilter) {
        this.appDAO = appDAO;
        this.keywordMatcher = keywordMatcher;
        this.scoreFilter = scoreFilter;
    }
    @Override
    public List<departments> getRecommendationsForStudent(int theStudentd) {
        // 1. Öğrenci bilgilerini al
        StudentInfo student = appDAO.getStudentInfoById(theStudentd);
        if (student == null) {
            System.out.println("Öğrenci bilgileri getirilemedi!");
            return List.of();
        }
        // 2. Tüm programları getir
        List<departments> allPrograms = appDAO.getAllDepartments();
        // 3. İlgi alanlarına göre filtrele (KeywordMatcher)
        List<departments> interestFiltered = keywordMatcher.filterByInterests(student, allPrograms);
        // 4. Puan ve sıralamaya göre filtrele (ScoreFilter)
        List<departments> scoreFiltered = scoreFilter.filterByScore(student, interestFiltered);
        return scoreFiltered;
    }

    public void tercihListesineEkle(users kullanici, departments bolum) {
        bolum.addObserver(kullanici);
        // tercih listesine ekle
    }
}
