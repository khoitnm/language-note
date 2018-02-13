package org.tnmk.ln.infrastructure.dictionary.oxford.entity;

/**
 * @author khoi.tran on 2/24/17.
 */

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OxfordResponse {

    @Valid
    private Metadata metadata;
    @Valid
    private List<OxfordWord> results = null;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<OxfordWord> getResults() {
        return results;
    }

    public void setResults(List<OxfordWord> results) {
        this.results = results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}