package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "log_")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Log  implements Serializable    {
    private static final long serialVersionUID = -264888902026798630L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int mid;

    private String text;

    private Date createDate;

    @Transient
    private Manager manager;

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return id == log.id &&
                mid == log.mid &&
                Objects.equals(text, log.text) &&
                Objects.equals(createDate, log.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mid, text, createDate);
    }
}
