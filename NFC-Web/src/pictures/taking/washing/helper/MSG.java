package pictures.taking.washing.helper;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MSG {
    public static String getValue(String bundleName, String key, Object params[], Locale locale) {
//        ResourceBundle.clearCache();
        String text;
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);

        try {
            text = bundle.getString(key);
        } catch (MissingResourceException e) {
            text = "?? key " + key + " not found ??";
        }

        if (params != null) {
            MessageFormat mf = new MessageFormat(text, locale);
            text = mf.format(params, new StringBuffer(), null).toString();
        }

        return text;
    }
}
