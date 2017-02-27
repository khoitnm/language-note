package tnmk.ln.app.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.dictionary.entity.Expression;

import java.util.Arrays;

/**
 * @author khoi.tran on 2/27/17.
 */
@Service
public class ExampleService {
    @Autowired
    private ExampleRepository exampleRepository;

    //    @PostConstruct
    public void test() {
        Example example = new Example();
        Example.Child child = new Example.Child();
        child.setText("child");
        example.setChild(child);

        Expression expression = new Expression();
        expression.setText("expression");
        expression.setFamily(Arrays.asList(new Expression()));

        example.setText("example test");
        example.setExpression(expression);
        exampleRepository.save(example);

        exampleRepository.findAll();
    }
}
