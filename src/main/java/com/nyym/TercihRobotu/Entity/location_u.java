package com.nyym.TercihRobotu.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/*

    Bu entity departmanların konum bilgisinin tutulduğu kısım

*/

@Entity
@Table(name = "location_u")
public class location_u {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int location_id;


    @Column(name = "city_name")
    private String city_name;

    @Column(name = "district_name")
    private String district_name;

    @ManyToMany(mappedBy = "locations",  cascade = CascadeType.ALL)
    private List<departments> departments = new ArrayList<>();


    public List<departments> getDepartments() {
        return departments;
    }

    public void setDepartments(List<departments> departments) {
        this.departments = departments;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}

