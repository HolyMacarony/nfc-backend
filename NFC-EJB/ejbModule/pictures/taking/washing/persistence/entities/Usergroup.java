package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity implementation class for Entity: Usergroup
 */
@Entity
@NamedQueries({@NamedQuery(name = Usergroup.QUERY_GETALL, query = "SELECT u FROM Usergroup u"), @NamedQuery(name = Usergroup.QUERY_GETUsergroupBYNAME, query = "SELECT u FROM Usergroup u WHERE u.name = :name")})
@Table(name = "usergroup", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Usergroup implements Serializable {

    public static final String QUERY_GETALL = "Usergroup.GetAll";
    public static final String QUERY_GETUsergroupBYNAME = "Usergroup.GetUsergroupByName";
    private static final long serialVersionUID = 1L;
    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    //	@JsonBackReference(value = "user-has-usergroup")
    @ManyToMany(mappedBy = "usergroups", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> users = new HashSet<>();
//    @JsonManagedReference(value = "usergroup-has-securityrole")
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "usergroup_securityrole", joinColumns = @JoinColumn(name = "usergroup_id"), inverseJoinColumns = @JoinColumn(name = "securityrole_id"))
    private Set<Securityrole> securityroles = new HashSet<>();

    public Usergroup() {
    }

    public Usergroup(String name) {
        this.name = name;
    }

    public Usergroup(String name, Set<Securityrole> Securityroles) {
        this(name);
        this.setSecurityroles(Securityroles);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Securityrole> getSecurityroles() {
        return securityroles;
    }

    public void setSecurityroles(Set<Securityrole> Securityroles) {
        this.securityroles = Securityroles;
    }

    @Override
    @Transient
    public String toString() {
        return name;
    }
}
