package tnmk.ln.app.digitalasset.entity;

import org.hibernate.validator.constraints.NotBlank;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import tnmk.common.infrastructure.data.neo4j.annotation.CascadeRelationship;
import tnmk.ln.app.common.entity.BaseNeo4jEntity;
import tnmk.ln.app.social.entity.Like;

import java.util.List;

/**
 * This is a wrapper of {@link tnmk.ln.infrastructure.filestorage.entity.FileItem}.
 * It can contain other social information: likes, comments...
 * //FUTURE If we want to handle many sizes of an image, store those image variations here.
 *
 * @author khoi.tran on 2/28/17.
 */
@NodeEntity(label = "DigitalAsset")
public class DigitalAsset extends BaseNeo4jEntity {
    public static final String LIKE = "LIKE";

    @NotBlank
    private String fileItemId;
    private long likesCount;

    @CascadeRelationship
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
}
