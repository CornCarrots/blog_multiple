package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
//@Proxy(lazy = false)
public class User implements Serializable  {
    private static final long serialVersionUID = -2167085571235163354L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String nickName;

    private String password;

    private String salt;

    private int sex;

    private Date registerDate;

    private Date loginDate;

    private String mobile;

    private String email;

    private String img;

    private int mid;

    private int score;

    @Column(name = "status_")
    private int status;

    private boolean isSpecialty;

    @Transient
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isSpecialty() {
        return isSpecialty;
    }

    public boolean getIsSpecialty() {
        return isSpecialty;
    }

    public void setSpecialty(boolean specialty) {
        isSpecialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                sex == user.sex &&
                mid == user.mid &&
                score == user.score &&
                status == user.status &&
                isSpecialty == user.isSpecialty &&
                Objects.equals(name, user.name) &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(salt, user.salt) &&
                Objects.equals(registerDate, user.registerDate) &&
                Objects.equals(loginDate, user.loginDate) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(email, user.email) &&
                Objects.equals(img, user.img) &&
                Objects.equals(member, user.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nickName, password, salt, sex, registerDate, loginDate, mobile, email, img, mid, score, status, isSpecialty, member);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", sex=" + sex +
                ", registerDate=" + registerDate +
                ", loginDate=" + loginDate +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", img='" + img + '\'' +
                ", mid=" + mid +
                ", score=" + score +
                ", status=" + status +
                ", isSpecialty=" + isSpecialty +
                ", member=" + member +
                '}';
    }
}
