package org.tnmk.ln.infrastructure.dictionary.oxford.entity;

/**
 * @author khoi.tran on 2/24/17.
 */

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entry {

    @Valid
    private List<String> etymologies = null;
    @Valid
    private List<GrammaticalFeature> grammaticalFeatures = null;
    @Valid
    private List<Sense> senses = null;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<String> getEtymologies() {
        return etymologies;
    }

    public void setEtymologies(List<String> etymologies) {
        this.etymologies = etymologies;
    }

    public List<GrammaticalFeature> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public void setGrammaticalFeatures(List<GrammaticalFeature> grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
    }

    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}