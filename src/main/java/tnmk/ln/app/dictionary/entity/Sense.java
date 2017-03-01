package tnmk.ln.app.dictionary.entity;

//import org.neo4j.ogm.annotation.NodeEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.common.infrastructure.data.neo4j.annotation.CascadeRelationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.digitalasset.entity.DigitalAsset;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity(label = "Sense")
public class Sense extends BaseNeo4jEntity {
    public static final String HAS_EXAMPLE = "HAS_EXAMPLE";
    public static final String HAS_PHOTOS = "HAS_PHOTOS";
    public static final String HAS_AUDIOS = "HAS_AUDIOS";
    public static final String HAS_VIDEOS = "HAS_VIDEOS";

    private String explanation;
    private String note;

    @CascadeRelationship
    @Relationship(type = HAS_PHOTOS, direction = Relationship.OUTGOING)
    private List<DigitalAsset> photos;

    @CascadeRelationship
    @Relationship(type = HAS_AUDIOS, direction = Relationship.OUTGOING)
    private List<DigitalAsset> audios;

    @CascadeRelationship
    @Relationship(type = HAS_VIDEOS, direction = Relationship.OUTGOING)
    private List<DigitalAsset> videos;

    @CascadeRelationship
    @Relationship(type = HAS_EXAMPLE, direction = Relationship.OUTGOING)
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
