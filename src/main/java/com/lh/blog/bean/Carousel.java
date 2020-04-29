package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "carousel")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Carousel implements Serializable   {

    private static final long serialVersionUID = 4185964482937305509L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "status_")
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        Carousel carousel = (Carousel) o;
        return id == carousel.id &&
                status == carousel.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }
}
