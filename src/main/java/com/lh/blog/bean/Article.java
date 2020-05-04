package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Article")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Article implements Serializable   {

    private static final long serialVersionUID = -5186275850776003868L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(max = 10, message = "标题字数不能超过10位")
    private String title;

    @NotNull
    private String summary;

    @NotNull
    private  String text;

    private String secret;

    private int cid;

    private Date createDate;

    private Date updateDate;

    @Column(name = "status_")
    private int status;

    private String type;

    private int view;

    @Column(name = "like_")
    private int like;

    private int start;

    private int comment;

    private int uid;

    private boolean isSpecialty;

    @Transient
    private Category category;

    @Transient
    private List<Tag> tags;

    @Transient
    private boolean hasLike;

    @Transient
    private boolean hasStart;

    @Transient
    private User user;

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }

    public boolean isHasStart() {
        return hasStart;
    }

    public void setHasStart(boolean hasStart) {
        this.hasStart = hasStart;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    public boolean isSpecialty() {
        return isSpecialty;
    }

    public void setSpecialty(boolean specialty) {
        isSpecialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                cid == article.cid &&
                status == article.status &&
                view == article.view &&
                like == article.like &&
                start == article.start &&
                comment == article.comment &&
                uid == article.uid &&
                isSpecialty == article.isSpecialty &&
                hasLike == article.hasLike &&
                hasStart == article.hasStart &&
                Objects.equals(title, article.title) &&
                Objects.equals(summary, article.summary) &&
                Objects.equals(text, article.text) &&
                Objects.equals(secret, article.secret) &&
                Objects.equals(createDate, article.createDate) &&
                Objects.equals(updateDate, article.updateDate) &&
                Objects.equals(type, article.type) &&
                Objects.equals(category, article.category) &&
                Objects.equals(tags, article.tags) &&
                Objects.equals(user, article.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, summary, text, secret, cid, createDate, updateDate, status, type, view, like, start, comment, uid, isSpecialty, category, tags, hasLike, hasStart, user);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", text='" + text + '\'' +
                ", secret='" + secret + '\'' +
                ", cid=" + cid +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status=" + status +
                ", type='" + type + '\'' +
                ", view=" + view +
                ", like=" + like +
                ", start=" + start +
                ", comment=" + comment +
                ", uid=" + uid +
                ", isSpecialty=" + isSpecialty +
                ", category=" + category +
                ", tags=" + tags +
                ", hasLike=" + hasLike +
                ", hasStart=" + hasStart +
                ", user=" + user +
                '}';
    }
}
