package tnmk.ln.infrastructure.dictionary.oxford.entity;

/**
 * @author khoi.tran on 2/24/17.
 */

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sentence {

    @Valid
    private List<String> regions = null;
    @Valid
    private List<String> senseIds = null;
    private String text;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getSenseIds() {
        return senseIds;
    }

    public void setSenseIds(List<String> senseIds) {
        this.senseIds = senseIds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
