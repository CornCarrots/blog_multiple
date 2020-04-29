package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Category implements Serializable   {

    private static final long serialVersionUID = -8280610094025350750L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    private int pid;

    private String icon;

    @Column(name = "status_")
    private int status;

    private int uid;

    @Transient
    private Category parent;

    @Transient
    private User user;

    @Transient
    @JsonIgnoreProperties("parent")
    private List<Category> child;

    public List<Category> getChild() {
        return child;
    }

    public void setChild(List<Category> child) {
        this.child = child;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        Category category = (Category) o;
        return id == category.id &&
                pid == category.pid &&
                status == category.status &&
                uid == category.uid &&
                Objects.equals(name, category.name) &&
                Objects.equals(icon, category.icon) &&
                Objects.equals(parent, category.parent) &&
                Objects.equals(user, category.user) &&
                Objects.equals(child, category.child);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pid, icon, status, uid, parent, user, child);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", icon='" + icon + '\'' +
                ", status=" + status +
                ", uid=" + uid +
                ", parent=" + parent +
                ", user=" + user +
                ", child=" + child +
                '}';
    }
}
