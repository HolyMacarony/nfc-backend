package pictures.taking.washing.web.beans;

import pictures.taking.washing.persistence.entities.PlayerPlaysHike;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class PlayerPlaysHikeBean implements Serializable {
    private PlayerPlaysHike playerPlaysHike;
    private Long playerPlaysHikeId;
//	@EJB private PlayerPlaysHikeDAO playerPlaysHikeDAO;

    public PlayerPlaysHikeBean(PlayerPlaysHike playerPlaysHike) {
        this.playerPlaysHike = playerPlaysHike;
    }

    public PlayerPlaysHikeBean() {
    }

    @PostConstruct
    public void init() {
        //		if (null != hike)
        //		{
        //			return;
        //		}
        //
        //		if (hikeId != null)
        //		{
        //			hike = hikeDAO.find(hikeId);
        //		}
        //
        //		if (hike == null)
        //		{
        //			hike = new Hike();
        //		}
        //		// Copy the data
        //		// addresses.clear();
        //		// for (Address address : user.getAddresses()) {
        //		// addresses.add(new AddressBean(address));
        //		// }
    }

    public void create() {
        prepare();
//		playerPlaysHikeDAO.create(playerPlaysHike);
    }

    public void prepare() {

        // Clear all the lists
        // Add all items (again) to the list

        //
        //		hike.getHikesections().clear();
        //		for (Hikesection hs : hikesections)
        //		{
        //			hike.getHikesections().add(hs.getHikeSection());
        //		}

    }
}