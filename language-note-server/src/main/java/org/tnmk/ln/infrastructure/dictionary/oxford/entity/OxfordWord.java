package org.tnmk.ln.infrastructure.dictionary.oxford.entity;

/**
 * @author khoi.tran on 2/24/17.
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.tnmk.ln.app.common.entity.BaseMongoEntity;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "OxfordWord")
public class OxfordWord extends BaseMongoEntity {
    @Field("oxford_word_id")
    @JsonProperty("id")//In order to compatible with OxfordAPI
    private String oxfordWordId;
    private String language;
    @Valid
    private List<LexicalEntry> lexicalEntries = null;
    private String type;
    private String word;

    @Indexed
    private String fromRequest;

    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<LexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getOxfordWordId() {
        return oxfordWordId;
    }

    public void setOxfordWordId(String oxfordWordId) {
        this.oxfordWordId = oxfordWordId;
    }

    public String getFromRequest() {
        return fromRequest;
    }

    public void setFromRequest(String fromRequest) {
        this.fromRequest = fromRequest;
    }
}
