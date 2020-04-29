package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authorized")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Authorized implements Serializable   {

    private static final long serialVersionUID = -646637553551861199L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int uid;

    private String name;

    @Column(name = "desc_")
    private String desc;

    private String email;

    @Column(name = "status_")
    private int status;

    @Transient
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authorized that = (Authorized) o;
        return id == that.id &&
                uid == that.uid &&
                status == that.status &&
                Objects.equals(name, that.name) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(email, that.email) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, name, desc, email, status, user);
    }

    @Override
    public String toString() {
        return "Authorized{" +
                "id=" + id +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
