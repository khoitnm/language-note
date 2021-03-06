package org.tnmk.ln.app.digitalasset.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tnmk.ln.app.common.entity.BaseMongoEntity;
import org.tnmk.ln.infrastructure.filestorage.entity.FileItem;
import org.tnmk.ln.app.social.entity.Like;

import java.util.List;

/**
 * This is a wrapper of {@link FileItem}.
 * It can contain other social information: likes, comments...
 * //FUTURE If we want to handle many sizes of an image, store those image variations here.
 *
 * @author khoi.tran on 2/28/17.
 */
//@NodeEntity(label = "DigitalAsset")
@Document(collection = "DigitalAsset")
public class DigitalAsset extends BaseMongoEntity {
    public static final String LIKE = "LIKE";

    @NotBlank
    private String fileItemId;
    private String externalUrl;
    private long likesCount;

    @Relationship(type = LIKE, direction = Relationship.INCOMING)
    private List<Like> likes;

    public String getFileItemId() {
        return fileItemId;
    }

    public void setFileItemId(String fileItemId) {
        this.fileItemId = fileItemId;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }
}
