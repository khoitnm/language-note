package tnmk.ln.app.dictionary.entity;

//import org.neo4j.ogm.annotation.NodeEntity;

import org.neo4j.ogm.annotation.NodeEntity;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.infrastructure.filestorage.entity.FileItem;

import java.util.List;

/**
 * @author khoi.tran on 2/18/17.
 */
@NodeEntity
public class Sense extends BaseNeo4jEntity {
    private String explanation;
    private LexicalType lexicalType;
    private List<FileItem> photos;
    private List<FileItem> audios;
    private List<FileItem> videos;
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

    public List<FileItem> getPhotos() {
        return photos;
    }

    public void setPhotos(List<FileItem> photos) {
        this.photos = photos;
    }

    public List<FileItem> getAudios() {
        return audios;
    }

    public void setAudios(List<FileItem> audios) {
        this.audios = audios;
    }

    public List<FileItem> getVideos() {
        return videos;
    }

    public void setVideos(List<FileItem> videos) {
        this.videos = videos;
    }

    public LexicalType getLexicalType() {
        return lexicalType;
    }

    public void setLexicalType(LexicalType lexicalType) {
        this.lexicalType = lexicalType;
    }
}
