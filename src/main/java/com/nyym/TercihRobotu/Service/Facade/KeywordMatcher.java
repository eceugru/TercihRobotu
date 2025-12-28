package com.nyym.TercihRobotu.Service.Facade;

import com.nyym.TercihRobotu.DAO.AppDAO;
import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.departments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class KeywordMatcher {
    @Autowired
    private AppDAO appDAO;
    private final Map<String, List<String>> keywordGroups = Map.ofEntries(
            Map.entry("engineer", List.of("mühendislik", "teknoloji", "robotik", "yazılım", "makine", "inşaat", "otomasyon", "yapay zeka", "endüstri", "oyun oynamak", "veri bilimi", "matematik", "bulmaca", "siber güvenlik")),
            Map.entry("health", List.of("sağlık", "tıp", "doktor", "hemşire", "eczacılık", "diş hekimliği", "fizyoterapi", "beslenme", "veteriner", "hastane", "insan", "hayvan", "biyoloji", "kimya", "doktor dizi")),
            Map.entry("social", List.of("psikoloji", "sosyoloji", "tarih", "siyaset", "felsefe", "uluslararası ilişkiler", "insan bilimleri", "milletvekili", "kaymakam", "vali", "tarih öğretmeni", "siyasetçi", "sosyolog")),
            Map.entry("science", List.of("fen", "fizik", "kimya", "biyoloji", "matematik", "astronomi", "genetik", "jeoloji", "kimya deneyleri", "biyoloji öğretmeni")),
            Map.entry("economics", List.of("işletme", "ekonomi", "finans", "maliye", "pazarlama", "yönetim", "bankacılık", "ticareti", "yönetici", "girişimcilik", "dijital pazarlama", "dropshipping", "e-ticaret", "kripto para", "döviz", "borsa", "marka yönetimi")),
            Map.entry("education", List.of("eğitim", "öğretmenlik", "rehberlik", "okul öncesi", "eğitim bilimleri", "çocuklar", "sınıf öğretmeni", "eğitimkoçluğu")),
            Map.entry("design", List.of("sanat", "tasarım", "mimarlık", "grafik", "müzik", "resim", "sinema", "moda", "müzik aleti", "dijital sanat", "karikatür çizimi", "çizim")),
            Map.entry("humanities", List.of("edebiyat", "dil", "çeviri", "dil öğrenme", "çevirmenlik")),
            Map.entry("law", List.of("hukuk", "adalet", "kanun", "avukat", "insan hakları")),
            Map.entry("agriculture", List.of("tarım", "ziraat", "orman", "gıda", "su ürünleri", "çiftçilik", "bahçe işleri", "bitkiler")),
            Map.entry("communication", List.of("iletişim", "gazetecilik", "medya", "reklam", "halkla ilişkiler", "radyo", "tv", "haber", "spiker", "muhabir"))
    );
    public List<departments> filterByInterests(StudentInfo thestudent, List<departments> programs){
        StudentInfo student = appDAO.getStudentInfoById(thestudent.getId());
        if(student == null){
            System.out.println("Öğrenci bilgileri getirilemedi !");
            return List.of();
        }
        // Interest areas, score type ve ranking bilgilerinin alınması
        String InterestAreas = student.getInterestAreas();
        System.out.println(InterestAreas);
        // tüm programların çekilmesi
        Map<String, Integer> groupMatchCounts = new HashMap<>();
        List<departments> recommendations =  new ArrayList<>();
        //skorlama ve filtreleme
        List<String> interestList = List.of(InterestAreas.toLowerCase().split(", "));
        for (Map.Entry<String, List<String>> group : keywordGroups.entrySet()) {
            int matchCount = 0;
            for (String interest : interestList) {
                if (group.getValue().contains(interest)) {
                    matchCount++;

                }
            }
            groupMatchCounts.put(group.getKey(), matchCount);
        }
        // 2. En çok eşleşen grubu bul
        String bestMatchingGroup = Collections.max(groupMatchCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
        List<String> bestGroupKeywords = keywordGroups.get(bestMatchingGroup);
        System.out.println("DEBUD - "  + bestMatchingGroup);
        List<departments> allPrograms  = appDAO.getAllDepartmentsByKeywords(bestMatchingGroup);
        return allPrograms;
    };
}
