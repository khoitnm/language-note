package tnmk.ln.infrastructure.dictionary.oxford.entity;

/**
 * @author khoi.tran on 2/24/17.
 */

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pronunciation {

    private String audioFile;
    @Valid
    private List<String> dialects = null;
    private String phoneticNotation;
    private String phoneticSpelling;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public List<String> getDialects() {
        return dialects;
    }

    public void setDialects(List<String> dialects) {
        this.dialects = dialects;
    }

    public String getPhoneticNotation() {
        return phoneticNotation;
    }

    public void setPhoneticNotation(String phoneticNotation) {
        this.phoneticNotation = phoneticNotation;
    }

    public String getPhoneticSpelling() {
        return phoneticSpelling;
    }

    public void setPhoneticSpelling(String phoneticSpelling) {
        this.phoneticSpelling = phoneticSpelling;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}