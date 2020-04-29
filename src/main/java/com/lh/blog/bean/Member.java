package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "member")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Member  implements Serializable   {
    private static final long serialVersionUID = -2858247578885480493L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int min;

    private int max;

    private String name;

    private int score;

    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id == member.id &&
                min == member.min &&
                max == member.max &&
                score == member.score &&
                Objects.equals(name, member.name) &&
                Objects.equals(icon, member.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, min, max, name, score, icon);
    }
}
