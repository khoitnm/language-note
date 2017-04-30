package tnmk.ln.app.practice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tnmk.common.util.IterableUtil;
import tnmk.ln.app.practice.entity.favourite.PracticeFavourite;
import tnmk.ln.infrastructure.data.neo4j.repository.Neo4jRepository;

/**
 * @author khoi.tran on 2/26/17.
 */
@Repository
public class PracticeFavouriteCustomRepository {
    @Autowired
    Neo4jRepository neo4jRepository;

    public PracticeFavourite findByOwnerIdAndExpressionId(Long userId, String expressionId) {
        String queryString = String.join("",
                "MATCH (p:PracticeFavourite)"
                , " WHERE p.expressionId = {p1}"
                , " MATCH (e)-[:FAVOURITE_EXPRESSION_OWNER]-(u:User)"
                , " WHERE id(u)={p0}"
                , " RETURN p"
        );
        return IterableUtil.getFirst(neo4jRepository.findList(PracticeFavourite.class, queryString, userId, expressionId));
    }
}
