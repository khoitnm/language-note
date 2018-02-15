package org.tnmk.ln.infrastructure.security.authserver.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tnmk.common.infrastructure.validator.BeanValidator;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.UserRepository;
import org.tnmk.ln.infrastructure.security.usersmanagement.neo4j.entity.User;

@Service
public class AuthServerUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BeanValidator beanValidator;

    @Autowired
    private UserConverter userConverter;

    public AuthServerUser registerUser(AuthServerUser authServerUser) {
        User user = userConverter.toEntity(authServerUser);
        beanValidator.validate(user);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        return userConverter.toModel(user);
    }

    public AuthServerUser findByUsername(String username) {
        User userEntity = userRepository.findOneByUsername(username);
        return userConverter.toModel(userEntity);
    }

    public AuthServerUser findById(Long id) {
        User userEntity = userRepository.findOne(id);
        return userConverter.toModel(userEntity);
    }
}
