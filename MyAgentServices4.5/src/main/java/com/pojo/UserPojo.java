package com.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserPojo implements Cloneable, Serializable {
    private int id;
    private String name;

    private String email;

    private List<UserObjective> objectives= new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TestPojo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public List<UserObjective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<UserObjective> objectives) {
        this.objectives = objectives;
    }
}
