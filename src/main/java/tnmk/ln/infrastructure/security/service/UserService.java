package tnmk.ln.infrastructure.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tnmk.common.infrastructure.validator.BeanValidator;
import tnmk.ln.infrastructure.security.entity.User;
import tnmk.ln.infrastructure.security.repository.UserRepository;

/**
 * @author khoi.tran on 1/28/17.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BeanValidator beanValidator;

    public User registerUser(User user) {
        beanValidator.validate(user);
//        String encodedPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }
}
