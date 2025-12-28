package com.nyym.TercihRobotu.Entity;

import com.nyym.TercihRobotu.Service.Observer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*
    Bu entity tercih edilebilir bütün seçenklerin tutulduğu kısım
*/

@Entity
@Table(name = "departments")
public class departments implements Observable {

    @Id
    @Column(name = "yop_code")
    private int yop_code;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "department_locations",
            joinColumns = @JoinColumn(name = "yop_code"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private List<location_u> locations = new ArrayList<>();

    // One-to-Many ilişki (Departments → Preferences)
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<preferences> preferences;

    //@OneToMany(mappedBy = "departments", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<recommendations> recommendations;

    @Column(name = "universite_name")
    private String university_name;

    @Column(name = "faculty_name")
    private String faculty_name;

    @Column(name = "program_name")
    private String program_name;

    @Column(name = "program_language")
    private String program_language;

    @Column(name = "universite_type")
    private String university_type;

    @Column(name = "program_duraction")
    private int program_duraction;

    @Column(name = "program_base_score")
    private double program_base_score;

    @Column(name = "program_success_rank")
    private double program_success_rank;

    @Column(name = "program_quota")
    private int program_quota;

    @Column(name = "program_website")
    private String program_website;

    @Column(name = "keywords")
    private String keywords;

    @Column(name = "score_type")
    private String score_type;


    // getter/setter


    public List<location_u> getLocations() {
        return locations;
    }

    public void setLocations(List<location_u> locations) {
        this.locations = locations;
    }

    public int getYop_code() {
        return yop_code;
    }

    public void setYop_code(int yop_code) {
        this.yop_code = yop_code;
    }

    public List<preferences> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<preferences> preferences) {
        this.preferences = preferences;
    }

    /*public List<recommendations> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<recommendations> recommendations) {
        this.recommendations = recommendations;
    }*/

    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getProgram_name() {
        return program_name;
    }

    public void setProgram_name(String program_name) {
        this.program_name = program_name;
    }

    public String getProgram_language() {
        return program_language;
    }

    public void setProgram_language(String program_language) {
        this.program_language = program_language;
    }

    public String getUniversity_type() {
        return university_type;
    }

    public void setUniversity_type(String university_type) {
        this.university_type = university_type;
    }

    public int getProgram_duraction() {
        return program_duraction;
    }

    public void setProgram_duraction(int program_duraction) {
        this.program_duraction = program_duraction;
    }

    public double getProgram_base_score() {
        return program_base_score;
    }

    public void setProgram_base_score(double program_base_score) {
        this.program_base_score = program_base_score;
    }

    public double getProgram_success_rank() {
        return program_success_rank;
    }

    public void setProgram_success_rank(double program_success_rank) {
        this.program_success_rank = program_success_rank;
    }

    public int getProgram_quota() {
        return program_quota;
    }

    public void setProgram_quota(int program_quota) {
        this.program_quota = program_quota;
    }

    public String getProgram_website() {
        return program_website;
    }

    public void setProgram_website(String program_website) {
        this.program_website = program_website;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getScore_type() {
        return score_type;
    }

    public void setScore_type(String score_type) {
        this.score_type = score_type;
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
}
