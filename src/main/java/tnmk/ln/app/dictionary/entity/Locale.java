package tnmk.ln.app.dictionary.entity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.Cleanable;

/**
 * @author khoi.tran on 3/10/17.
 */
//@NodeEntity(label = "Locale")
@Document(collection = "Locale")
public class Locale extends BaseMongoEntity implements Cleanable {
    public static final String LANGUAGE_EN = "en";
    public static final Locale EN_EN = new Locale(LANGUAGE_EN, "en");
    public static final Locale EN_US = new Locale(LANGUAGE_EN, "us");
    public static final Locale DEFAULT = EN_EN;

    private String language;
    private String country;

    public Locale(String language, String country) {
        this.language = language;
        this.country = country;
    }

    public Locale() {
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
