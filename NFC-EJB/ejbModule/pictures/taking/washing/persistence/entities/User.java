package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import pictures.taking.washing.ejb.constraints.annotations.UniqueEmail;
import pictures.taking.washing.ejb.constraints.annotations.UniqueUsername;
import pictures.taking.washing.ejb.constraints.groupsequences.ExpensiveChecks;
import pictures.taking.washing.persistence.security.PBKDF2WithHmacSHA1;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({@NamedQuery(name = User.QUERY_FINDALL, query = "SELECT u FROM User u"), @NamedQuery(name = User.QUERY_FINDBYUSERNAME, query = "SELECT u FROM User u WHERE u.userName = :userName"), @NamedQuery(name = User.QUERY_FINDBYEMAIL, query = "SELECT u FROM User u WHERE u.email = :email")})
@NamedQuery(name = User.QUERY_FINDPASSWORDBYEMAIL, query = "SELECT u.password FROM User u WHERE u.email = :email")

@Table(name = "user")
//@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@GroupSequence({User.class, ExpensiveChecks.class})
public class User implements Serializable {

    public static final String QUERY_FINDALL = "User.FindAll";
    public static final String QUERY_FINDBYUSERNAME = "User.FindByUsername";
    public static final String QUERY_FINDBYEMAIL = "User.FindByEmail";
    public static final String QUERY_FINDPASSWORDBYEMAIL = "User.FindPasswordByEmail";
    public static final String QUERY_FINDBYBIRTHDAY = "User.FindByBirthday";
    private static final long serialVersionUID = 1L;
    // public static final String JOIN_TABLE_User_Usergroup = "User_Usergroup";
    //
    // public static final String JOIN_TABLE_User_Usergroup_JoinColumn = "user_id";
    //
    // public static final String JOIN_TABLE_User_Usergroup_InverseJoinColumn =
    // "Usergroup_id";

    // @JsonManagedReference(value = "user-rates-statement")
    // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval =
    // true, fetch = FetchType.EAGER)
    // private Set<UserRatesStatement> userRatesStatements = new
    // HashSet<UserRatesStatement>();
    // @JsonManagedReference(value="user-has-usergroup")
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinTable(name = "user_usergroup", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "usergroup_id"))
    private Set<Usergroup> usergroups = new HashSet<>();
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 50)
    private String firstName;
    @Size(min = 1, max = 50)
    private String lastName;
    @NotNull(message = "{error_username_NULL}")
    @Size(min = 3, max = 100, message = "{error_username_SIZE}")
    @UniqueUsername(groups = ExpensiveChecks.class, message = "{error_username_UNIQUE}")
    @Column(unique = true)
    private String userName;

    @NotNull(message = "{error_email_NULL}")
    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "{error_email_VALID}")
    @UniqueEmail(groups = ExpensiveChecks.class, message = "{error_email_UNIQUE}")
    @Column(unique = true)
    private String email;

    @NotNull(message = "{error_password_NULL}")
    @Column(columnDefinition = "VARCHAR(157)")
    private String password;
    @Transient
    private String password_clear;
    @Transient
    @AssertTrue(message = "{error_terms_ACCEPTED}")
    private boolean agreedToAGB;
    // @NotNull
    // @Size(min = 1, max = 100)
    // private String email;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Media> media = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @org.hibernate.annotations.OrderBy(clause = "createdAt DESC")
    private List<Hike> hikes = new ArrayList<>();

    @Version
    @NotNull
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @NotNull
    @Version
    private Timestamp updatedAt;

    public User(String username, String password, String email) {
        this.setUserName(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setAgreedToAGB(true);
    }

    public User() {
    }

    public void addHike(Hike hike) {
        this.hikes.add(hike);
        hike.setUser(this);
    }

    public void removeHike(Hike hike) {
        this.hikes.remove(hike);
        hike.setUser(null);
    }

    public void addMedia(Media media) {
        this.media.add(media);
        media.setUser(this);
    }

    public void removeMedia(Media media) {
        this.media.remove(media);
        media.setUser(null);
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public List<Hike> getHikes() {
        //hikes neu laden?
        return hikes;
    }

    public void setHikes(List<Hike> hikes) {
        this.hikes = hikes;
    }

    public Set<Usergroup> getUsergroups() {
        return usergroups;
    }

    public void setUsergroups(Set<Usergroup> usergroups) {
        this.usergroups = usergroups;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password_clear = password;
            String generatedSecuredPasswordHash = PBKDF2WithHmacSHA1.generateStrongPasswordHash(password);
            this.password = generatedSecuredPasswordHash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // setting PW failed
            e.printStackTrace();
        }

    }

    public String getPassword_clear() {
        return password_clear;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAgreedToAGB() {
        return agreedToAGB;
    }

    public void setAgreedToAGB(boolean agreedToAGB) {
        this.agreedToAGB = agreedToAGB;
    }
    // public void addStatementRating(Statement statement, int rating) {
    // UserRatesStatement userRatesStatement = new UserRatesStatement(this,
    // statement, rating);
    // userRatesStatements.add(userRatesStatement);
    // statement.getUserRatesStatements().add(userRatesStatement);
    // }

    // public void addUsergroup(Usergroup Usergroup) {
    // Usergroups.add(Usergroup);
    // Usergroup.getUsers().add(this);
    // }
    public User cloneUser() {
        User u = new User();
        u.setId(id);
        // u.setAddresses(addresses);
        // u.setBirthday(birthday);
        // u.setCommunications(communications);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setUserName(userName);
        u.setPassword(password);
        // u.setStatements(statements);
        u.setUsergroups(usergroups);
        // u.setRelationship(relationship);
        u.setCreatedAt(createdAt);
        // u.setGender(gender);
        // u.setUpdatedAt(updatedAt);
        // u.setUserRatesStatements(userRatesStatements);
        return u;
    }

    // public void removeStatementRating(Statement statement) {
    // for (Iterator<UserRatesStatement> iterator = userRatesStatements.iterator();
    // iterator.hasNext();) {
    // UserRatesStatement userRatesStatement = iterator.next();
    //
    // if (userRatesStatement.getUser().equals(this) &&
    // userRatesStatement.getStatement().equals(statement)) {
    // iterator.remove();
    // userRatesStatement.getStatement().getUserRatesStatements().remove(userRatesStatement);
    // userRatesStatement.setUser(null);
    // userRatesStatement.setStatement(null);
    // }
    // }
    // }
    @Override
    public String toString() {
        return this.getUserName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (userName == null) {
            return other.userName == null;
        } else {
            return userName.equals(other.userName);
        }
    }


}
