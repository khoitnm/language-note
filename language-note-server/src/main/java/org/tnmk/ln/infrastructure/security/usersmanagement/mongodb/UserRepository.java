package org.tnmk.ln.infrastructure.security.usersmanagement.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.tnmk.common.infrastructure.data.mongo.repository.MongoRepoScanInclude;
import org.tnmk.ln.infrastructure.security.usersmanagement.mongodb.entity.User;

/**
 * @author khoi.tran on 1/28/17.
 * @deprecated use neo4j, this class is used for migration only.
 */
@Deprecated
@MongoRepoScanInclude
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findOneByUsername(String username);
}
