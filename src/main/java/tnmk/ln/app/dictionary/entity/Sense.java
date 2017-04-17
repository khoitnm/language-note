package tnmk.ln.app.dictionary.entity;

//import org.neo4j.ogm.annotation.NodeEntity;

import org.apache.commons.lang3.StringUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import tnmk.ln.app.common.entity.BaseMongoEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.common.entity.Cleanable;
import tnmk.ln.app.digitalasset.entity.DigitalAsset;
import tnmk.ln.infrastructure.data.neo4j.annotation.CascadeRelationship;
import tnmk.ln.infrastructure.data.neo4j.annotation.DetailLoading;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
//@NodeEntity(label = "Sense")
@Document(collection = "Sense")
public class Sense extends BaseMongoEntity implements Cleanable {
    public static final String HAS_EXAMPLE = "HAS_EXAMPLE";
    public static final String HAS_PHOTOS = "HAS_PHOTOS";
    public static final String HAS_AUDIOS = "HAS_AUDIOS";
    public static final String HAS_VIDEOS = "HAS_VIDEOS";
    private static final String SENSE_HAS_MAIN_PHOTO = "SENSE_HAS_MAIN_PHOTO";

    private String explanation;
    private String shortExplanation;
    private String detailExplanation;
    /**
     * This is an example which helps you to easily link some famous events/characters/movies... to the expression.
     * Examples of links are shown in this video: https://www.youtube.com/watch?v=jZrWc1j-NLA
     */
    private String memorizedLink;

    @DetailLoading
    @Relationship(type = SENSE_HAS_MAIN_PHOTO, direction = Relationship.OUTGOING)
    private DigitalAsset mainPhoto;

    @DetailLoading
    @Relationship(type = HAS_PHOTOS, direction = Relationship.OUTGOING)
    private List<DigitalAsset> photos;

    @DetailLoading
    @Relationship(type = HAS_AUDIOS, direction = Relationship.OUTGOING)
    private List<DigitalAsset> audios;

    @DetailLoading
    @Relationship(type = HAS_VIDEOS, direction = Relationship.OUTGOING)
    private List<DigitalAsset> videos;

    @DetailLoading
    @Relationship(type = HAS_EXAMPLE, direction = Relationship.OUTGOING)
    private List<Example> examples;

    @Transient
    @Override
    public boolean isEmpty() {
        return StringUtils.isBlank(this.explanation);
    }
    @Override
    public String toString() {
        return String.format("Sense{%s, %s}", super.getId(), explanation);
    }

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

    public String getMemorizedLink() {
        return memorizedLink;
    }

    public void setMemorizedLink(String memorizedLink) {
        this.memorizedLink = memorizedLink;
    }

    public String getDetailExplanation() {
        return detailExplanation;
    }

    public void setDetailExplanation(String detailExplanation) {
        this.detailExplanation = detailExplanation;
    }

    public String getShortExplanation() {
        return shortExplanation;
    }

    public void setShortExplanation(String shortExplanation) {
        this.shortExplanation = shortExplanation;
    }

    public DigitalAsset getMainPhoto() {
        if (mainPhoto == null && photos != null && photos.size() > 0) {
            mainPhoto = photos.get(0);
        }
        return mainPhoto;
    }

    public void setMainPhoto(DigitalAsset mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
}
