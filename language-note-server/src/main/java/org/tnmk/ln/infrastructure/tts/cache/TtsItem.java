package org.tnmk.ln.infrastructure.tts.cache;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.tnmk.ln.app.common.entity.BaseMongoEntity;
import org.tnmk.ln.infrastructure.filestorage.entity.FileItem;

/**
 * @author khoi.tran on 2/2/17.
 */
@Document(collection = "TtsItem")
public class TtsItem extends BaseMongoEntity {
    /**
     * Must be trimmed and compare ignore case (don't care about uppercase or lowercase).
     */
    @Indexed
    private String text;
    @Indexed
    private String locale;

    private String source;
    @DBRef
    private FileItem fileItem;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
