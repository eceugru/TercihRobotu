package com.nyym.TercihRobotu.Entity.Factory;

import com.nyym.TercihRobotu.Entity.StudentInfo;
import com.nyym.TercihRobotu.Entity.User;

public class EntityFactory {

    public static Object createEntity(String key) {
        if ("user".equalsIgnoreCase(key)) {
            return new User();
        } else if ("student".equalsIgnoreCase(key)) {
            return new StudentInfo();
        } else {
            throw new IllegalArgumentException("Geçersiz entity anahtarı: " + key);
        }
    }
}
