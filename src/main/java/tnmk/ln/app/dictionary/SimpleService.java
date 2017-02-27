package tnmk.ln.app.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tnmk.ln.app.dictionary.entity.Simple;

import javax.annotation.PostConstruct;

/**
 * @author khoi.tran on 2/26/17.
 */
@Service
public class SimpleService {
    @Autowired
    SimpleRepository simpleRepository;

    @PostConstruct
    public void init() {
        Simple simple = new Simple();
        simple.setText("simple test");
        simpleRepository.save(simple);
    }
}
