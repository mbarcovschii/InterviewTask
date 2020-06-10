package com.mycompany.app.database;

public class HumanPOJO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String sex;
    private String imageInfo;
    private String workplace;
    private Double hourlyPay;
    private String isCool;
    private String isAwesome;
    private String residence;

    private Boolean containsFullData = true;

    public HumanPOJO(String firstName, String lastName, String email,
                     String sex, String imageInfo, String workplace,
                     Double hourlyPay, String isCool, String isAwesome,
                     String residence) {

        if (firstName == null) {
            containsFullData = false;
        } else {
            this.firstName = firstName;
        }

        if (lastName == null) {
            containsFullData = false;
        } else {
            this.lastName = lastName;
        }

        if (email == null) {
            containsFullData = false;
        } else {
            this.email = email;
        }

        if (sex == null) {
            containsFullData = false;
        } else {
            this.sex = sex;
        }

        if (imageInfo == null) {
            containsFullData = false;
        } else {
            this.imageInfo = imageInfo;
        }

        if (workplace == null) {
            containsFullData = false;
        } else {
            this.workplace = workplace;
        }

        if (hourlyPay == null) {
            containsFullData = false;
        } else {
            this.hourlyPay = hourlyPay;
        }

        if (isCool == null) {
            containsFullData = false;
        } else {
            this.isCool = isCool;
        }

        if (isAwesome == null) {
            containsFullData = false;
        } else {
            this.isAwesome = isAwesome;
        }

        if (residence == null) {
            containsFullData = false;
        } else {
            this.residence = residence;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public Double getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(Double hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    public String getCool() {
        return isCool;
    }

    public void setCool(String cool) {
        isCool = cool;
    }

    public String getAwesome() {
        return isAwesome;
    }

    public void setAwesome(String awesome) {
        isAwesome = awesome;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public boolean containsFullData() {

        return containsFullData;
    }
}
