package tnmk.ln.infrastructure.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tnmk.common.infrastructure.validator.BeanValidator;
import tnmk.ln.infrastructure.security.neo4j.UserRepository;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 1/28/17.
 */
@Service
public class UserService {
    public static final String USERNAME_ADMIN = "admin";
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
