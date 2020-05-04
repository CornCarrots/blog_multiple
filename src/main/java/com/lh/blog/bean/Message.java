package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Message  implements Serializable    {
    private static final long serialVersionUID = 1923141252098989493L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int uid;

    private Date createDate;

    @Size(max = 200)
    private String text;

    private String reply;

    private Date replyDate;

    private int status;

    private String type;

    @Transient
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        Message message = (Message) o;
        return id == message.id &&
                uid == message.uid &&
                status == message.status &&
                Objects.equals(createDate, message.createDate) &&
                Objects.equals(text, message.text) &&
                Objects.equals(reply, message.reply) &&
                Objects.equals(replyDate, message.replyDate) &&
                Objects.equals(type, message.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uid, createDate, text, reply, replyDate, status, type);
    }
}
