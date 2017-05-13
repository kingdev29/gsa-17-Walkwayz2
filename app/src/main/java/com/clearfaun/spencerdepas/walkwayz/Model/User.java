package com.clearfaun.spencerdepas.walkwayz.Model;

import com.clearfaun.spencerdepas.walkwayz.Manager.UserManager;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SpencerDepas on 5/7/17.
 */

public class User {

    private  static User user;

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    private int age = 17;
    private String id = "6636gdggdduu9WWE";
    private String email = "spencerdepas@gmail.com";
    private String name = "Spencer";
    private String sex = "Male";
    private String emergencyContact;
    private String password = "talkingplumcake";
    private int height = 88;
    private String image;
    private LatLng location = new LatLng(54.351210, 18.646448);

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LatLng getLocationLatLng() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}