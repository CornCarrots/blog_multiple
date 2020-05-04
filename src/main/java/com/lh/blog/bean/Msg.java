package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "msg")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Msg  implements Serializable    {
    private static final long serialVersionUID = 6337782546299957453L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Size(max = 200)
    private String text;

    private Date createDate;

    @Column(name = "status_")
    private int status;

    @Column(name = "sendid")
    private int sid;

    @Column(name = "receiveid")
    private int rid;

    @Transient
    private User send;

    @Transient
    private User Receive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public User getSend() {
        return send;
    }

    public void setSend(User send) {
        this.send = send;
    }

    public User getReceive() {
        return Receive;
    }

    public void setReceive(User receive) {
        Receive = receive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Msg msg = (Msg) o;
        return id == msg.id &&
                status == msg.status &&
                sid == msg.sid &&
                rid == msg.rid &&
                Objects.equals(text, msg.text) &&
                Objects.equals(createDate, msg.createDate) &&
                Objects.equals(send, msg.send) &&
                Objects.equals(Receive, msg.Receive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createDate, status, sid, rid, send, Receive);
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createDate=" + createDate +
                ", stauts=" + status +
                ", sid=" + sid +
                ", rid=" + rid +
                ", send=" + send +
                ", Receive=" + Receive +
                '}';
    }
}
