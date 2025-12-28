package com.nyym.TercihRobotu.Entity;


import jakarta.persistence.*;

import java.util.List;

//
// student_info tablosu içindir.
//

@Entity
@Table(name = "student_info")
public class StudentInfo extends users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="student_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private User user;

    @Column(name = "exam_score")
    private double  examScore;

    @Column(name = "score_type")
    private String scoreType;

    @Column(name = "ranking")
    private double ranking;

    @Column(name = "interest_areas")
    private String interestAreas;

    @Column(name = "gender")
    private String gender;

    // Bir öğrencinin birden fazla tercihi olabilir
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<preferences> preferences;

    @OneToMany(mappedBy = "student_R", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<recommendations> recommendations;


    public StudentInfo() {
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public double  getExamScore() {
        return examScore;
    }

    public void setExamScore(double  examScore) {
        this.examScore = examScore;
    }

    public double getRanking() {
        return ranking;
    }

    public void setRanking(double ranking) {
        this.ranking = ranking;
    }

    public String getInterestAreas() {
        return interestAreas;
    }

    public void setInterestAreas(String interestAreas) {
        this.interestAreas = interestAreas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", user=" + user +
                ", examScore=" + examScore +
                ", scoreType='" + scoreType + '\'' +
                ", ranking=" + ranking +
                ", interestAreas='" + interestAreas + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
