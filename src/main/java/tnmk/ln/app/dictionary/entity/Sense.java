package tnmk.ln.app.dictionary.entity;

//import org.neo4j.ogm.annotation.NodeEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.digitalasset.entity.DigitalAsset;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity(label = "Sense")
public class Sense extends BaseNeo4jEntity {
    private String explanation;
    private String note;
    private LexicalType lexicalType;
    private List<DigitalAsset> photos;
    private List<DigitalAsset> audios;
    private List<DigitalAsset> videos;
    private List<Example> examples;

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public LexicalType getLexicalType() {
        return lexicalType;
    }

    public void setLexicalType(LexicalType lexicalType) {
        this.lexicalType = lexicalType;
    }

    public List<DigitalAsset> getPhotos() {
        return photos;
    }

    public void setPhotos(List<DigitalAsset> photos) {
        this.photos = photos;
    }

    public List<DigitalAsset> getAudios() {
        return audios;
    }

    public void setAudios(List<DigitalAsset> audios) {
        this.audios = audios;
    }

    public List<DigitalAsset> getVideos() {
        return videos;
    }

    public void setVideos(List<DigitalAsset> videos) {
        this.videos = videos;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
