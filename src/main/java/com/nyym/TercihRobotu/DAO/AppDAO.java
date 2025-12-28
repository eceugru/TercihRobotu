package com.nyym.TercihRobotu.DAO;

import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.User;
import com.nyym.TercihRobotu.Entity.departments;
import com.nyym.TercihRobotu.Entity.preferences;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface AppDAO {

    void save(User theUser);

    void save(StudentInfo theStudentInfo);

    void save(preferences thePreference);


    User findUserByEmail(String username);

    StudentInfo findStudentInfoByUser(User user);

    void update(StudentInfo studentInfo);

    StudentInfo getStudentInfoById(int theStudentId);

    List<departments> getAllDepartments();

    List<departments> getAllDepartmentsByKeywords(String theKeyword);

    departments findDepartmentByYopCode(int theYopCode);

    StudentInfo findStudentInfoByUsername(String username);

    List<preferences> findPreferencesByStudentOrderByOrder(StudentInfo student);

    int findMaxPreferenceOrder(StudentInfo student);

    boolean deletePreference(StudentInfo student, departments program);

    // AppDAO interface'ine ekle
    preferences findPreferenceByStudentAndDepartment(StudentInfo student, departments department);
    void update(preferences preference);
}
