package tnmk.ln.infrastructure.filestorage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import tnmk.ln.infrastructure.filestorage.entity.FileItem;

/**
 * @author khoi.tran on 2/2/17.
 */
@MongoRepoScanInclude
@Repository
public interface FileItemRepository extends MongoRepository<FileItem, String> {
}
