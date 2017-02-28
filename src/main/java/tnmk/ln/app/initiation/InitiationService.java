package tnmk.ln.app.initiation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.common.util.ObjectMapperUtil;
import tnmk.ln.app.dictionary.ExpressionService;
import tnmk.ln.app.dictionary.entity.Expression;
import tnmk.ln.infrastructure.security.entity.User;
import tnmk.ln.infrastructure.security.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * @author khoi.tran on 2/26/17.
 */
@Service
public class InitiationService {
    public static Logger LOGGER = LoggerFactory.getLogger(InitiationService.class);

    public static final String ADMIN_USERNAME = "admin";
    @Autowired
    private UserService userService;

    @Autowired
    private ExpressionService expressionService;

    @PostConstruct
    public void initAdminUser() {
        User user = userService.findByUsername("admin");
        if (user == null) {
            //TODO I know it's absolutely not secured.
            user = new User();
            user.setUsername(ADMIN_USERNAME);
            user.setEmail("khoi.tnm@gmail.com");
            user.setPassword("password");
            user.setRoles(Arrays.asList("ADMIN"));
            userService.registerUser(user);
        }
//        this.testUdate();
    }

    public void testUdate() {
        Expression synonym1 = new Expression();
        synonym1.setId(218l);
        synonym1.setText("synonym04");
        Expression synonym2 = new Expression();
        synonym2.setId(216l);
        synonym2.setText("synonym02");

        Expression expression = expressionService.findById(213);
        expression.setSynonyms(Arrays.asList(synonym1, synonym2));
        expression.setOwner(null);
        expressionService.updateExpressionDefinition(expression);

        expression = expressionService.findById(213);
        LOGGER.info(ObjectMapperUtil.toStringMultiLine(expression));
    }
}
