package com.lh.blog.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tag_article")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class TagArticle  implements Serializable   {
    private static final long serialVersionUID = -2825164791525724134L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int tid;

    private int aid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
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
        TagArticle that = (TagArticle) o;
        return id == that.id &&
                tid == that.tid &&
                aid == that.aid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tid, aid);
    }
}
