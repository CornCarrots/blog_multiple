package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_tag")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class UserTag implements Serializable   {
    private static final long serialVersionUID = 7657406688200530903L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int tid;

    private int uid;

    @Transient
    private Tag tag;

    @Transient
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
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
        UserTag userTag = (UserTag) o;
        return id == userTag.id &&
                tid == userTag.tid &&
                uid == userTag.uid &&
                Objects.equals(tag, userTag.tag) &&
                Objects.equals(user, userTag.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tid, uid, tag, user);
    }

    @Override
    public String toString() {
        return "UserTag{" +
                "id=" + id +
                ", tid=" + tid +
                ", uid=" + uid +
                ", tag=" + tag +
                ", user=" + user +
                '}';
    }
}
