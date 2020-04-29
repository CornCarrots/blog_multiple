package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "manager")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Manager implements Serializable  {
    private static final long serialVersionUID = 5518756406952100461L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private String password;

    private String salt;

    private String nickName;

    private int sex;

    private String mobile;

    private String email;

    private Date createDate;

    @Transient
    private List<Role> roles;

    @Column(name = "status_")
    private int status;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return id == manager.id &&
                sex == manager.sex &&
                status == manager.status &&
                Objects.equals(name, manager.name) &&
                Objects.equals(password, manager.password) &&
                Objects.equals(salt, manager.salt) &&
                Objects.equals(nickName, manager.nickName) &&
                Objects.equals(mobile, manager.mobile) &&
                Objects.equals(email, manager.email) &&
                Objects.equals(createDate, manager.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, salt, nickName, sex, mobile, email, createDate, status);
    }
}
