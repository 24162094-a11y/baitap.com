package baitap.com.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int cateid;

    @Column(name = "name")
    private String catename;

    @Column(name = "icons")
    private String icon;

    @Column(name = "createdDate")
    private Long createdDate;

    public Category() {
    }

    public int getId() {
        return cateid;
    }

    public void setId(int id) {
        this.cateid = id;
    }

    public String getName() {
        return catename;
    }

    public void setName(String name) {
        this.catename = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }
}