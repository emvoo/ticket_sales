package models;

import java.io.Serializable;

/**
 *
 * @author Marcin Wisniewski
 */
public class Agent implements Serializable {
    private int id;
    private String name;
    private String location;
    private String phone;
    private boolean active;
    
    // constructor
    public Agent(int id, String name, String location, String phone, boolean active)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.active = active;
    }
    
    // getters and setters
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }
    
    // changes Agent to active/inactives
    public void setActive(boolean active) {
        this.active = active;
    }
}
