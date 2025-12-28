package com.nyym.TercihRobotu.Entity;
import com.nyym.TercihRobotu.Service.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class users implements Observer {
    private static final Logger log = LoggerFactory.getLogger(users.class);
    public int id;
    public String email;

    @Override
    public void update(departments department) {
        log.info("{} kullanıcısına bildirim: {} bölümünün bilgileri değişti!", id, department.getYop_code());
    }

    @Override
    public String toString() {
        return "users{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
