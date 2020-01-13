package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity implementation class for Entity: Securityrole
 */
@Entity
@NamedQueries({@NamedQuery(name = Securityrole.QUERY_GETALL, query = "SELECT s FROM Securityrole s"), @NamedQuery(name = Securityrole.QUERY_GETSecurityroleBYNAME, query = "SELECT s FROM Securityrole s WHERE s.name = :name")})
@Table(name = "securityrole", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Securityrole implements Serializable {

    public static final String QUERY_GETALL = "Securityrole.GetAll";
    public static final String QUERY_GETSecurityroleBYNAME = "Securityrole.GetSecurityroleByName";
    private static final long serialVersionUID = 1L;
    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String displayName = "";
    @ManyToMany(mappedBy = "securityroles", fetch = FetchType.LAZY)
    private Set<Usergroup> usergroups = new HashSet<>();

    public Securityrole(String name) {
        this.name = name;
    }

    public Securityrole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<Usergroup> getUsergroups() {
        return usergroups;
    }

    @JsonBackReference(value = "usergroup-has-securityrole")
    public void setUsergroups(Set<Usergroup> usergroups) {
        this.usergroups = usergroups;
    }

    @Override
    @Transient
    public String toString() {
        return "{Name:" + displayName + ";Security Role:" + name + "}";
    }
}
