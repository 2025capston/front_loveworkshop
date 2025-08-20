package com.example.romancesample.model;

public class UserProfileDTO {
    private String cloneAge;
    private String place;
    private int height;
    private int age;
    private String sex;
    private String setting1;
    private String setting2;
    private String photoUri;

    // 기본 생성자
    public UserProfileDTO() {}

    // getter / setter
    public String getCloneAge() { return cloneAge; }
    public void setCloneAge(String cloneAge) { this.cloneAge = cloneAge; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getSetting1() { return setting1; }
    public void setSetting1(String setting1) { this.setting1 = setting1; }

    public String getSetting2() { return setting2; }
    public void setSetting2(String setting2) { this.setting2 = setting2; }

    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }
}

