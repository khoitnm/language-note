package tnmk.ln.app.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tnmk.ln.app.topic.entity.Category;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author khoi.tran on 3/5/17.
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryAndOwnerRepository categoryAndOwnerRepository;

    @Autowired
    private Neo4jRepository neo4jRepository;

    @Transactional
    public Category saveIfNecessaryByTextAndOwner(User user, Category category) {
        //TODO use merge query of neo4j to improve performance?
        Category result;
        category.setOwner(user);
        if (category.getId() != null) {
            Category existingCategory = categoryRepository.findOne(category.getId());
            if (existingCategory == null) {
                existingCategory = categoryAndOwnerRepository.findOneByTextAndOwner(category.getText(), user.getId());
                if (existingCategory == null) {
                    result = categoryRepository.save(category);
                } else {
                    result = existingCategory;
                }
            } else {
                result = categoryRepository.save(category);
            }
        } else {
            Category existingCategory = categoryAndOwnerRepository.findOneByTextAndOwner(category.getText(), user.getId());
            if (existingCategory == null) {
                category.setOwner(user);
                result = categoryRepository.save(category);
            } else {
                result = existingCategory;
            }
        }
        return result;
    }

    //TODO can improve performance (using batch or Merge query?)
    @Transactional
    public Set<Category> saveIfNecessaryByTextAndOwner(User user, Set<Category> categories) {
        Set<Category> result = new HashSet<>();
        if (categories != null) {
            for (Category category : categories) {
                Category savedCategory = saveIfNecessaryByTextAndOwner(user, category);
                result.add(savedCategory);
            }
        }
        return result;
    }
}
