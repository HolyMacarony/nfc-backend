package pictures.taking.washing.web.beans;

import pictures.taking.washing.ejb.interfaces.MediaDAO;
import pictures.taking.washing.persistence.entities.Media;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class MediaBean implements Serializable {

    private Media media;
    private Long mediaId;

    @EJB
    private MediaDAO mediaDAO;

    public MediaBean(Media media) {
        this.media = media;
    }

    public MediaBean() {
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void init() {

        if (media != null) {
            return;
        }
        if (mediaId != null) {
            media = mediaDAO.find(mediaId);
        }
        if (media == null) {
            media = new Media();
        }
    }
}
