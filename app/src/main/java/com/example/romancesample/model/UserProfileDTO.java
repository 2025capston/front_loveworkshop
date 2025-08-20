package com.example.romancesample.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfileDTO implements Parcelable {
    private String cloneAge;
    private String place;
    private int height;
    private int age;
    private String sex;
    public String setSexualOrientation;
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

    public String getSetting1() { return setSexualOrientation; }
    public void setSetting1(String setting1) { this.setSexualOrientation = setSexualOrientation; }

    public String getSetting2() { return setting2; }
    public void setSetting2(String setting2) { this.setting2 = setting2; }

    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }

    // ---------------- Parcelable 구현 ----------------
    protected UserProfileDTO(Parcel in) {
        cloneAge = in.readString();
        place = in.readString();
        height = in.readInt();
        age = in.readInt();
        sex = in.readString();
        setSexualOrientation = in.readString();
        setting2 = in.readString();
        photoUri = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cloneAge);
        dest.writeString(place);
        dest.writeInt(height);
        dest.writeInt(age);
        dest.writeString(sex);
        dest.writeString(setSexualOrientation);
        dest.writeString(setting2);
        dest.writeString(photoUri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserProfileDTO> CREATOR = new Creator<UserProfileDTO>() {
        @Override
        public UserProfileDTO createFromParcel(Parcel in) {
            return new UserProfileDTO(in);
        }

        @Override
        public UserProfileDTO[] newArray(int size) {
            return new UserProfileDTO[size];
        }
    };


    public void setSexualOrientation(String selectedLuv) {
    }
}
