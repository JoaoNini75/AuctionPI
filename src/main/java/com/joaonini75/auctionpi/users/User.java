package com.joaonini75.auctionpi.users;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity(name = "userTable")
@Table(name = "userTable")
public class User {

    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name, nickname, email, password, photoId;
    private LocalDate dob; // date of birth
    @Transient
    private int age;

    public User() {

    }

    public User(String name, String nickname, String email, String password, String photoId, LocalDate dob, int age) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.photoId = photoId;
        this.dob = dob;
        this.age = age;
    }

    public User(Long id, String name, String nickname, String email, String password, String photoId, LocalDate dob, int age) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.photoId = photoId;
        this.dob = dob;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", photoId='" + photoId + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public void setAge(int age) {
        this.age = age;
    }
}
