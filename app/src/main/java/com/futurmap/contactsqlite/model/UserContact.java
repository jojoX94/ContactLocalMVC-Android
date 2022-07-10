package com.futurmap.contactsqlite.model;

public class UserContact {
    private int id;
    private String name;
    private String attribution;
    private String phoneNumber;
//    private String image = "";
//    public UserContact(String name, String phoneNumber) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.image = image;
//    }

//    public UserContact(String name, String phoneNumber, String image) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.image = image;
//    }


    public UserContact() {
    }

    public UserContact(int id, String name, String attribution, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.attribution = attribution;
        this.phoneNumber = phoneNumber;
    }

    public UserContact(String name, String attribution, String phoneNumber) {
        this.name = name;
        this.attribution = attribution;
        this.phoneNumber = phoneNumber;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    @Override
    public String toString() {
        return "UserContact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }
}
