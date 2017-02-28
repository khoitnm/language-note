package tnmk.ln.infrastructure.security.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import tnmk.ln.infrastructure.security.entity.User;

/**
 * @author khoi.tran on 1/28/17.
 */
@MongoRepoScanInclude
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findOneByUsername(String username);
}
