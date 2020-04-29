package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "manager_role")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class ManagerRole  implements Serializable    {
    private static final long serialVersionUID = -993465988950492121L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int mid;

    private int rid;

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

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManagerRole that = (ManagerRole) o;
        return id == that.id &&
                mid == that.mid &&
                rid == that.rid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mid, rid);
    }
}
