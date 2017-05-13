package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.Cleanable;

import java.util.Arrays;
import java.util.List;

/**
 * @author khoi.tran on 3/10/17.
 */
//@NodeEntity(label = "Locale")
@Document(collection = "Locale")
public class Locale extends BaseMongoEntity implements Cleanable {
    public static final String CODE_EN = "en";
    public static final Locale EN_EN = new Locale(CODE_EN, "en");
    public static final Locale EN_US = new Locale(CODE_EN, "us");
    private static final List<Locale> SUPPORTED_LOCALES = Arrays.asList(EN_EN, EN_US);
    public static final Locale DEFAULT = EN_EN;
    public static final String DEFAULT_STRING = "en-en";

    private String language;
    private String country;

    public Locale(String language, String country) {
        this.language = language;
        this.country = country;
    }

    public Locale() {
        //For JSON converter
    }

    public String toString() {
        return String.format("%s-%s", this.language, this.country);
    }

    public static Locale fromString(String localeString) {
        String normLocaleString = StringUtils.normalizeSpace(localeString.toLowerCase());
        String[] parts = normLocaleString.split("-");
        String language = parts[0];
        String country = language;
        if (parts.length > 1) {
            country = parts[1];
        }
        Locale result = new Locale(language, country);
        for (Locale supportedLocale : SUPPORTED_LOCALES) {
            if (supportedLocale.language.equals(language) && supportedLocale.country.equals(country)) {
                result = supportedLocale;
                break;
            }
        }
        return result;
    }

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.language);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
