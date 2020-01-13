package pictures.taking.washing.customComponents;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@ViewScoped
@FacesConverter("GPSPointConverter")
public class GPSPointConverter implements Converter, Serializable {

    protected static final String LATITUDE_PATTERN = "^(\\+|-)?(?:90(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-8][0-9])(?:(?:\\.[0-9]{1,6})?))$";
    protected static final String LONGITUDE_PATTERN = "^(\\+|-)?(?:180(?:(?:\\.0{1,6})?)|(?:[0-9]|[1-9][0-9]|1[0-7][0-9])(?:(?:\\.[0-9]{1,6})?))$";

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert comitted and comma seperated GPS coordinates into an POINT Object if valid.
        if (value == null) {
            return null;
        }
        String[] latLong = value.split(",[ ]*");
        if (latLong.length != 2) {
            FacesMessage msg =
                    new FacesMessage("POINT Conversion error.",
                            "lat,long durch Komma trennen");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ConverterException(msg);
        }

//        DecimalFormat df = new DecimalFormat("#.######");
//        df.setRoundingMode(RoundingMode.UP);
        Point p1 = null;

        try {
            boolean resultLAT = String.valueOf(round(Double.valueOf(latLong[0]), 6)).matches(LATITUDE_PATTERN);
            boolean resultLNG = String.valueOf(round(Double.valueOf(latLong[1]), 6)).matches(LONGITUDE_PATTERN);
            if (resultLAT && resultLNG) {
                GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
                p1 = gf.createPoint(new Coordinate(round(Double.valueOf(latLong[0]), 6), round(Double.valueOf(latLong[1]), 6)));
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,"invalid values!", "lat: " + latLong[0] + "\nlng: " + latLong[1]));
        }

        return p1;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert here Hikesection object to String value for use in HTML.
        if (!(value instanceof Point) || value == null) {
            return null;
        }
        return String.valueOf(round(((Point) value).getX(), 6) + ", " + round(((Point) value).getY(), 6));
    }
}
