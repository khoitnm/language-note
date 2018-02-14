package org.tnmk.ln.infrastructure.security.usersmanagement;

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
public class UserService {
    public static final String USERNAME_ADMIN = "superuser";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BeanValidator beanValidator;

    public User registerUser(User user) {
        beanValidator.validate(user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        User userEntity = userRepository.findOneByUsername(username);
        return userEntity;
    }
}
