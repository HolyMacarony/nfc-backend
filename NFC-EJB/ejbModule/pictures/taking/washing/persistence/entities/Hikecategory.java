package pictures.taking.washing.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Hikecategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(max = 250)
    private String name;
    @ManyToMany(mappedBy = "hikecategories")
    private Set<Hike> hikes = new HashSet<>();

    public Set<Hike> getHikes() {
        return hikes;
    }

    public void setHikes(Set<Hike> hikes) {
        this.hikes = hikes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
