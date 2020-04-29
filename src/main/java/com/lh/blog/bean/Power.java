package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "power")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Power implements Serializable    {
    private static final long serialVersionUID = -8100926527307480249L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int mid;

    private String title;

    private String text;

    private int score;

    private int exchange;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getExchange() {
        return exchange;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Power power = (Power) o;
        return id == power.id &&
                mid == power.mid &&
                score == power.score &&
                exchange == power.exchange &&
                Objects.equals(title, power.title) &&
                Objects.equals(text, power.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mid, title, text, score, exchange);
    }
}
