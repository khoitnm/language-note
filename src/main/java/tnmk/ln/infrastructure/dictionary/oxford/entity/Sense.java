package tnmk.ln.infrastructure.dictionary.oxford.entity;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sense {

    @Valid
    private List<String> definitions = null;
    @Valid
    private List<String> domains = null;
    @Valid
    private List<Example> examples = null;
    private String id;
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<String> definitions) {
        this.definitions = definitions;
    }

    public List<String> getDomains() {
        return domains;
    }

    public void setDomains(List<String> domains) {
        this.domains = domains;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}