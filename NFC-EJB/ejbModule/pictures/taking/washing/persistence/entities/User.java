package pictures.taking.washing.persistence.entities;


import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import pictures.taking.washing.ejb.constraints.annotations.UniqueEmail;
import pictures.taking.washing.ejb.constraints.annotations.UniqueUsername;
import pictures.taking.washing.ejb.constraints.groupsequences.ExpensiveChecks;
import pictures.taking.washing.persistence.enums.SecurityroleEnum;
import pictures.taking.washing.persistence.enums.UsergroupEnum;
import pictures.taking.washing.persistence.security.PBKDF2WithHmacSHA1;


import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.*;
import org.hibernate.annotations.Type;

@Entity
@Schema(description="A user of the system.")
@NamedQueries({
        @NamedQuery(name = User.QUERY_FINDALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = User.QUERY_FINDBYUSERNAME, query = "SELECT u FROM User u WHERE u.userName = :userName"),
        @NamedQuery(name = User.QUERY_FINDPASSWORDBYEMAIL, query = "SELECT u.password FROM User u WHERE u.email = :email"),
        @NamedQuery(name = User.QUERY_FINDRESERVEDMACHINES, query = "SELECT m FROM Machine m WHERE m.lastHoldingStartTime < :endtime AND m.user.id = :userID"),
        @NamedQuery(name = User.QUERY_FINDBYEMAIL, query = "SELECT u FROM User u WHERE u.email = :email")})


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
    public static final String QUERY_FINDRESERVEDMACHINES = "User.FindReservedMachines";

    private static final long serialVersionUID = 1L;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JoinTable(name = "user_usergroup", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "usergroup_id"))
    private Set<Usergroup> usergroups = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Machine> machines = new ArrayList<>();

    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    @NotNull(message = "{error_username_NULL}")
    @Size(min = 3, max = 100, message = "{error_username_SIZE}")
    @UniqueUsername(groups = ExpensiveChecks.class, message = "{error_username_UNIQUE}")
    @Column(unique = true)
    private String userName;

    @NotNull(message = "{error_email_NULL}")
    @Schema(example = "hans@example.com", required = true, description = "")
    @Pattern(regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "{error_email_VALID}")
    @UniqueEmail(groups = ExpensiveChecks.class, message = "{error_email_UNIQUE}")
    @Column(unique = true)
    @JsonProperty("email")
    private String email;

    @NotNull(message = "{error_password_NULL}")
    @Column(columnDefinition = "VARCHAR(157)")
    private String password;
    @Transient
    private String password_clear;



    @Schema(example = "4.5", description = "")
    @JsonProperty("balance")
    private Double balance = null;

    @Schema(example = "123123", description = "")
    @JsonProperty("pinCode")
    private String pinCode = null;


    @Schema(example = "123e4567-e89b-12d3-a456-426655440000", description = "")
    @JsonProperty("cardId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId = null;


    @Version
    @NotNull
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @NotNull
    @Version
    private Timestamp updatedAt;

    public User(String username, String password, String email) {
//        this.setId(UUID.randomUUID());
        this.setUserName(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public User() {
    }

    public Long getId() {
        return id;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setId(Long id) {  this.id = id; }

    public Double getBalance() { return balance; }

    public void setBalance(Double balance) { this.balance = balance; }

    public String getPinCode() {  return pinCode; }

    public void setPinCode(String pinCode) {  this.pinCode = pinCode; }

    public Long getCardId() {  return cardId;}

    public void setCardId(Long cardId) { this.cardId = cardId; }


    public User cloneUser() {
        User u = new User();
        u.setId(id);
        u.setUserName(userName);
        u.setPassword(password);
        u.setUsergroups(usergroups);
        u.setCreatedAt(createdAt);

        return u;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class User {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    cardId: ").append(toIndentedString(cardId)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
        sb.append("    pinCode: ").append(toIndentedString(pinCode)).append("\n");
        sb.append("    Usergroups: ").append(toIndentedString(usergroups)).append("\n");
        sb.append("}");
        return sb.toString();
    }
    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, cardId, balance, pinCode);
    }

    public boolean isAdmin(){
        for (Usergroup group : this.getUsergroups()) {
            if(group.getName().equals(UsergroupEnum.admins.name())) {
                   return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(cardId, user.cardId) &&
                Objects.equals(balance, user.balance) &&
                Objects.equals(pinCode, user.pinCode);
    }


}
