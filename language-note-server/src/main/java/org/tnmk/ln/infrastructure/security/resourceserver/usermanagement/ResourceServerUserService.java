package org.tnmk.ln.infrastructure.security.resourceserver.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.UserRepository;
import org.tnmk.common.infrastructure.validator.BeanValidator;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

/**
 * @author khoi.tran on 1/28/17.
 * In this project, this service is shared on both authenticationServer and resourceServer. However, in other projects, we may need separated services for each server.
 */
@Service
public class ResourceServerUserService {
    public static final String USERNAME_ADMIN = "superuser";
    @Autowired
    private UserRepository userRepository;

    /**
     * Because we use ResourceServer and AuthServer in the same project, so this method is exactly the same as {@link org.tnmk.ln.infrastructure.security.authserver.usermanagement.AuthServerUserService#findByUsername(String)}
     * However, they could be different when we split them into different server.
     *
     * @param username
     * @return
     */
    public User findByUsername(String username) {
        User userEntity = userRepository.findOneByUsername(username);
        return userEntity;
    }

    public User findById(Long id) {
        User userEntity = userRepository.findOne(id);
        return userEntity;
    }
}
