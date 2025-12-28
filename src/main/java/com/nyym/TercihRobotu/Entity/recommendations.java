package com.nyym.TercihRobotu.Entity;

import jakarta.persistence.*;

/*
    Bu tablo öneri algoritmasının sonuçlarının tutulduğu kısımdır.
*/

@Entity
@Table(name = "recommendations")
public class recommendations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendations_id")
    private int recommendations_id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_R", referencedColumnName = "student_id")
    private StudentInfo student_R;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departments", referencedColumnName = "yop_code")
    private departments departments;

}
