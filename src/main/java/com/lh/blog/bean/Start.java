package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "start")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Start  implements Serializable    {
    private static final long serialVersionUID = -6020153096686992490L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int uid;

    private int aid;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Start start = (Start) o;
        return id == start.id &&
                uid == start.uid &&
                aid == start.aid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, aid);
    }
}
