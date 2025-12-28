package com.nyym.TercihRobotu.Entity;

import jakarta.persistence.*;
/*
    Bu entity öğrencilerin tercihlerinin tutulduğu tablodur.
 */
@Entity
@Table(name = "preferences")
public class preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preferences_id")
    private int preferences_id;

    @ManyToOne
    @JoinColumn(name = "yop_code", referencedColumnName = "yop_code")
    private departments department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private StudentInfo student;



    @Column(name = "preference_order")
    private int preference_order;

    public int getPreferences_id() {
        return preferences_id;
    }

    public void setPreferences_id(int preferences_id) {
        this.preferences_id = preferences_id;
    }

    public departments getDepartment() {
        return department;
    }

    public void setDepartment(departments department) {
        this.department = department;
    }

    public StudentInfo getStudent() {
        return student;
    }

    public void setStudent(StudentInfo student) {
        this.student = student;
    }

    public int getPreference_order() {
        return preference_order;
    }

    public void setPreference_order(int preference_order) {
        this.preference_order = preference_order;
    }
}
