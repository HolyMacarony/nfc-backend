package pictures.taking.washing.ejb.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class BaseUserData implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;

    private String email;
    private Long hikeCount;
    private Date createdAt;

    public BaseUserData(Long id, String username, String email, Date createdAt, Long hikeCount) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.hikeCount = hikeCount;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public BaseUserData() {
//    }

    public Long getHikeCount() {
        return hikeCount;
    }

    public void setHikeCount(Long hikeCount) {
        this.hikeCount = hikeCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
