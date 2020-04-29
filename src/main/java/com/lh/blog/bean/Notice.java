package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "notice")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Notice  implements Serializable    {
    private static final long serialVersionUID = -7327622590637795232L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private String text;

    private Date createDate;

    private Date updateDate;

    @Column(name = "status_")
    private int status;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return id == notice.id &&
                status == notice.status &&
                Objects.equals(title, notice.title) &&
                Objects.equals(text, notice.text) &&
                Objects.equals(createDate, notice.createDate) &&
                Objects.equals(updateDate, notice.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, text, createDate, updateDate, status);
    }
}
