package org.tnmk.ln.infrastructure.dictionary.oxford.entity;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

public class Example {

    private String text;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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