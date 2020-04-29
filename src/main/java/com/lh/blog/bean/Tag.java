package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tag")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Tag implements Serializable     {
    private static final long serialVersionUID = 1398605882838341237L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(name = "status_")
    private int status;

    private int count;

    private int uid;

    @Transient
    private User user;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
        Tag tag = (Tag) o;
        return id == tag.id &&
                status == tag.status &&
                count == tag.count &&
                uid == tag.uid &&
                Objects.equals(name, tag.name) &&
                Objects.equals(user, tag.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, count, uid, user);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", uid=" + uid +
                ", user=" + user +
                '}';
    }
}
