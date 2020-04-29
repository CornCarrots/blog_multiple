package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "comments")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Comments implements Serializable   {

    private static final long serialVersionUID = -4030254873266665216L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String text;

    private Date createDate;

    private int status;

    private int pid;

    private int uid;

    private int aid;

    @Column(name = "like_")
    private int like;

    @Transient
    private Article article;

    @Transient
    private User user;

    @Transient
    private Comments parent;

    @Transient
    private List<Comments> child;

    @Transient
    private boolean hasLike;

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public List<Comments> getChild() {
        return child;
    }

    public void setChild(List<Comments> child) {
        this.child = child;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comments getParent() {
        return parent;
    }

    public void setParent(Comments parent) {
        this.parent = parent;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return id == comments.id &&
                status == comments.status &&
                pid == comments.pid &&
                uid == comments.uid &&
                aid == comments.aid &&
                like == comments.like &&
                Objects.equals(text, comments.text) &&
                Objects.equals(createDate, comments.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createDate, status, pid, uid, aid, like);
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createDate=" + createDate +
                ", status=" + status +
                ", pid=" + pid +
                ", uid=" + uid +
                ", aid=" + aid +
                ", like=" + like +
                ", is="+hasLike+
                ", parent="+parent+
                '}';
    }
}
