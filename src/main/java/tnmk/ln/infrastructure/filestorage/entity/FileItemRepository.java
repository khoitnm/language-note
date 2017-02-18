package tnmk.ln.infrastructure.filestorage.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author khoi.tran on 2/2/17.
 */
@Repository
public interface FileItemRepository extends MongoRepository<FileItem, String> {
}
