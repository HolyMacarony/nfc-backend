package pictures.taking.washing.web.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
public class LocaleBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Map<String, Locale> countries;

    static {
        countries = new LinkedHashMap<String, Locale>();
        countries.put("English", Locale.ENGLISH);
        countries.put("Deutsch", Locale.GERMAN);
    }

    private Locale locale;
    private String localeCode;

    public static Map<String, Locale> getCountries() {
        return countries;
    }

    public static void setCountries(Map<String, Locale> countries) {
        LocaleBean.countries = countries;
    }

    @PostConstruct
    public void init() {
        this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        localeCode = this.locale.getLanguage();
    }

    // public void setLanguage(String language)
    // {
    // locale = new Locale(language);
    // FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    // }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLanguage() {
        return locale.getDisplayLanguage();
    }

    public void onLanguageChange() {
        String newLocaleValue = localeCode;

        // loop country map to compare the locale code
        for (Map.Entry<String, Locale> entry : countries.entrySet()) {

            if (entry.getValue().toString().equals(newLocaleValue)) {
                this.locale = entry.getValue();
                FacesContext.getCurrentInstance().getViewRoot().setLocale(entry.getValue());

            }
        }

    }

    public Map<String, Locale> getCountriesInMap() {
        return countries;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

}