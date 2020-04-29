package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "link")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Link  implements Serializable    {
    private static final long serialVersionUID = -8609886929790927030L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String url;

    private String name;

    private String email;

    @Column(name = "desc_")
    private String describe;

    private Date createDate;

    @Column(name = "status_")
    private  int status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return id == link.id &&
                status == link.status &&
                Objects.equals(url, link.url) &&
                Objects.equals(name, link.name) &&
                Objects.equals(email, link.email) &&
                Objects.equals(describe, link.describe) &&
                Objects.equals(createDate, link.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, name, email, describe, createDate, status);
    }
}
