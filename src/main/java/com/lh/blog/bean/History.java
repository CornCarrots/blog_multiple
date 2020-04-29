package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "history")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class History implements Serializable {
    private static final long serialVersionUID = 782736219462450896L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int uid;

    private int aid;

    private Date createDate;

    @Transient
    private Article article;

    @Transient
    private User user;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return id == history.id &&
                uid == history.uid &&
                aid == history.aid &&
                Objects.equals(createDate, history.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, aid, createDate);
    }
}
