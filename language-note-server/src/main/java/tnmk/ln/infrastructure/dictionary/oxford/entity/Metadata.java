package tnmk.ln.infrastructure.dictionary.oxford.entity;

/**
 * @author khoi.tran on 2/24/17.
 */

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class Metadata {

    private String provider;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}