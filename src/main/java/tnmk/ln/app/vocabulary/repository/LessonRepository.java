package tnmk.ln.app.vocabulary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tnmk.common.infrastructure.repositoriesfilter.MongoRepoScanInclude;
import tnmk.ln.app.vocabulary.entity.Lesson;

import java.util.List;

@MongoRepoScanInclude
@Repository
public interface LessonRepository extends MongoRepository<Lesson, String> {
    @Query(value = "{}", fields = "{'_id':1,'name':1,'bookId':1,'createdDateTime':1}")
    List<Lesson> findAllIntroductions();

    Lesson findOneByName(String name);
}
