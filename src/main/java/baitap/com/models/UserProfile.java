package baitap.com.models;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String username;
    private String email;
    private String password;
    private int roleid;
    private String phone;
    private Long createdDate;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "avatar")
    private String avatar;

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}