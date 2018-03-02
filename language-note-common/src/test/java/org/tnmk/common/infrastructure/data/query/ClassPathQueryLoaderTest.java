package org.tnmk.common.infrastructure.data.query;

import org.junit.Assert;
import org.junit.Test;

public class ClassPathQueryLoaderTest {
    private ClassPathQueryLoader classPathQueryLoader = new ClassPathQueryLoader();
    @Test
    public void loadQuery(){
        String query = ClassPathQueryLoader.loadQuery("/testdata/query/test-query.cql",1,"2");
        Assert.assertEquals("MATCH (e:Expression)--(u:User) WHERE id(u)=1 AND e.id=2 RETURN e",query.trim());
    }
}
