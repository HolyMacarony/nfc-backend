package pictures.taking.washing.persistence.entities.Events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeScanEvent extends Hikeevent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 250)
    private String codescan_text;
    @NotNull
    @Size(max = 250)
    private int codescan_points;
    private boolean codescan_gencodenotcustom;
    private boolean codescan_scanmandatory;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Hikeevent hikeevent;

    public CodeScanEvent() {
        this.clazz = this.getClass();

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodescan_text() {
        return codescan_text;
    }

    public void setCodescan_text(String text) {
        this.codescan_text = text;
    }

    public int getCodescan_points() {
        return codescan_points;
    }

    public void setCodescan_points(int points) {
        this.codescan_points = points;
    }

    public boolean isCodescan_gencodenotcustom() {
        return codescan_gencodenotcustom;
    }

    public void setCodescan_gencodenotcustom(boolean gencodenotcustom) {
        this.codescan_gencodenotcustom = gencodenotcustom;
    }

    public boolean isCodescan_scanmandatory() {
        return codescan_scanmandatory;
    }

    public void setCodescan_scanmandatory(boolean scanmandatory) {
        this.codescan_scanmandatory = scanmandatory;
    }

    public Hikeevent getHikeevent() {
        return hikeevent;
    }

    public void setHikeevent(Hikeevent hikeevent) {
        this.hikeevent = hikeevent;
    }

    @Override
    public String getEditFormName() {
        return null;
    }
}
