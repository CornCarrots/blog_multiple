package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "focus")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Focus implements Serializable {
    private static final long serialVersionUID = 8070042267446383113L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fromid")
    private int from;

    @Column(name = "toid")
    private int to;

    @Transient
    private User fromUser;

    @Transient
    private User toUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Focus focus = (Focus) o;
        return id == focus.id &&
                from == focus.from &&
                to == focus.to &&
                Objects.equals(fromUser, focus.fromUser) &&
                Objects.equals(toUser, focus.toUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, fromUser, toUser);
    }

    @Override
    public String toString() {
        return "Focus{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                '}';
    }
}
