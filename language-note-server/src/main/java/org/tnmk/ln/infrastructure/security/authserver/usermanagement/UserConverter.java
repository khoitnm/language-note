package org.tnmk.ln.infrastructure.security.authserver.usermanagement;

import org.springframework.stereotype.Component;
import org.tnmk.ln.infrastructure.basemodel.BaseConverter;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;
@Component
public class UserConverter extends BaseConverter<User, AuthServerUser>{
}
