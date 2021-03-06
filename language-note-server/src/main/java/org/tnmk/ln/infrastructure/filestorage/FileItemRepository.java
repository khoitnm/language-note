package org.tnmk.ln.infrastructure.filestorage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import org.tnmk.ln.infrastructure.filestorage.entity.FileItem;

/**
 * @author khoi.tran on 2/2/17.
 */
@MongoRepoScanInclude
@Repository
public interface FileItemRepository extends MongoRepository<FileItem, String> {
    FileItem findOneByNameAndMimeTypeAndFileSize(String name, String mimeType, Long fileSize);
}
