package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "like_")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Like implements Serializable   {
    private static final long serialVersionUID = 8925120447470386505L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int uid;

    private int acid;

    private String type;

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

    public int getAcid() {
        return acid;
    }

    public void setAcid(int acid) {
        this.acid = acid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return id == like.id &&
                uid == like.uid &&
                acid == like.acid &&
                Objects.equals(type, like.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, acid, type);
    }
}
