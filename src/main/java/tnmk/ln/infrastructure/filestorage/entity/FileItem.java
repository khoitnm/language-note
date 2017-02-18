package tnmk.ln.infrastructure.filestorage.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Base64Utils;
import tnmk.ln.app.common.entity.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the binary content of {@link Document}.
 * With current relationship, one Document metadata can point to many DocumentDownload binary (in theory), it's not good.
 */
@Document(collection = "FileItem")
public class FileItem extends BaseEntity {
    private String name;
    private String mimeType;
    private String base64Content;

    private Map<String, Object> properties = new HashMap<>();

    @Transient
    public byte[] getBytesContent() {
        return Base64Utils.decodeFromString(base64Content);
    }

    @Transient
    public void setBytesContent(byte[] bytesData) {
        this.base64Content = Base64Utils.encodeToString(bytesData);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
