package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "role_permission")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class RolePermission  implements Serializable   {
    private static final long serialVersionUID = -5831329927984135959L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int rid;

    private int pid;

    @Transient
    private Role role;

    @Transient
    private Permission permission;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission that = (RolePermission) o;
        return id == that.id &&
                rid == that.rid &&
                pid == that.pid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rid, pid);
    }

    @Override
    public String toString() {
        return "RolePermission{" +
                "id=" + id +
                ", rid=" + rid +
                ", pid=" + pid +
                ", role=" + role +
                ", permission=" + permission +
                '}';
    }
}
