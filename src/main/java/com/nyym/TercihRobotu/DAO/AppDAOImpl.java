package com.nyym.TercihRobotu.DAO;

import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.User;
import com.nyym.TercihRobotu.Entity.departments;
import com.nyym.TercihRobotu.Entity.preferences;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    // Singleton tasarım kalıbı
    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        entityManager.persist(theUser);
    }

    @Override
    @Transactional
    public void save(StudentInfo theStudentInfo) {
        entityManager.persist(theStudentInfo);
    }

    @Override
    @Transactional
    public void save(preferences thePreference) {
        entityManager.persist(thePreference);
    }

    @Override
    public User findUserByEmail(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery("select u from User u where u.email=:data", User.class);
            query.setParameter("data", username);
            User user = query.getSingleResult();
            return user;
        }catch (NoResultException e) {
            return null;
        }


    }


    @Override
    public StudentInfo findStudentInfoByUser(User user) {
        try {
            TypedQuery<StudentInfo> query = entityManager.createQuery(
                    "SELECT s FROM StudentInfo s WHERE s.user = :user", StudentInfo.class);
            query.setParameter("user", user);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void update(StudentInfo studentInfo) {
        entityManager.merge(studentInfo);
    }


    // id'ye göre student_info tablosundan öğrencinin bilgilerinin çekilmesi
    @Override
    public StudentInfo getStudentInfoById(int theStudentId) {
        try{
            TypedQuery<StudentInfo> query = entityManager.createQuery(
                    "select s from StudentInfo s where s.id = :studentId", StudentInfo.class
            );
            query.setParameter("studentId", theStudentId);
            StudentInfo studentInfo = query.getSingleResult();
            return studentInfo;
        }catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<departments> getAllDepartments() {
        try{
            TypedQuery<departments>  query = entityManager.createQuery("select d from departments d", departments.class);
            List<departments> departments = query.getResultList();
            return departments;
        }catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<departments> getAllDepartmentsByKeywords(String theKeyword) {
        try{
            TypedQuery<departments>  query = entityManager.createQuery("select d from departments d where d.keywords =:theKeywords", departments.class);
            query.setParameter("theKeywords", theKeyword);

            System.out.println("Debug - theKeywords: " +  theKeyword);
            System.out.println("-------------------------------------------");
            List<departments> departments = query.getResultList();
            for (departments department : departments) {
                System.out.println(department.getUniversity_name());
            }
            System.out.println("DEBUG - departments" + departments);
            System.out.println("---------------------------------------------");

            return departments;
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public departments findDepartmentByYopCode(int theYopCode) {
        TypedQuery<departments>  query = entityManager.createQuery("select d from departments d where d.yop_code =:theYopCode", departments.class);
        query.setParameter("theYopCode", theYopCode);
        departments department =  query.getSingleResult();

        return department;
    }

    @Override
    public StudentInfo findStudentInfoByUsername(String username) {
        try {
            TypedQuery<StudentInfo> query = entityManager.createQuery(
                    "SELECT s FROM StudentInfo s WHERE s.user.email = :username", StudentInfo.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<preferences> findPreferencesByStudentOrderByOrder(StudentInfo student) {
        return entityManager.createQuery(
                        "SELECT p FROM preferences p WHERE p.student = :student ORDER BY p.preference_order",
                        preferences.class)
                .setParameter("student", student)
                .getResultList();
    }

    @Override
    public int findMaxPreferenceOrder(StudentInfo student) {
        Integer maxOrder = entityManager.createQuery(
                        "SELECT MAX(p.preference_order) FROM preferences p WHERE p.student = :student",
                        Integer.class)
                .setParameter("student", student)
                .getSingleResult();
        return maxOrder != null ? maxOrder : 0;
    }

    @Override
    @Transactional
    public boolean deletePreference(StudentInfo student, departments program) {
        try {
            Query query = entityManager.createQuery(
                    "DELETE FROM preferences p WHERE p.student = :student AND p.department = :program");
            query.setParameter("student", student);
            query.setParameter("program", program);
            int result = query.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public preferences findPreferenceByStudentAndDepartment(StudentInfo student, departments department) {
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<preferences> cq = cb.createQuery(preferences.class);
        Root<preferences> root = cq.from(preferences.class);

        cq.select(root).where(
                cb.and(
                        cb.equal(root.get("student"), student),
                        cb.equal(root.get("department"), department)
                )
        );

        try {
            return session.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void update(preferences preference) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(preference); // Güncelleme için merge kullanılır.
    }


}
