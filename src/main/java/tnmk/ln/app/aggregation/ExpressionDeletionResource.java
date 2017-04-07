package tnmk.ln.app.aggregation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tnmk.ln.app.common.entity.UriPrefixConstants;
import tnmk.ln.infrastructure.security.helper.SecurityContextHelper;
import tnmk.ln.infrastructure.security.neo4j.entity.User;

/**
 * @author khoi.tran on 4/1/17.
 */
@RestController
public class ExpressionDeletionResource {

    @Autowired
    private ExpressionDeletionService expressionDeletionService;

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expressions/{expressionId}/sense-groups/{senseGroupId}", method = RequestMethod.DELETE)
    public void deleteSenseGroup(@PathVariable Long expressionId, @PathVariable Long senseGroupId) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        expressionDeletionService.deleteSenseGroupAndRelations(senseGroupId);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/sense-groups/{senseGroupId}/senses/{senseId}", method = RequestMethod.DELETE)
    public void deleteSense(@PathVariable Long senseGroupId, @PathVariable Long senseId) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        expressionDeletionService.deleteSenseAndRelations(senseGroupId);
    }

    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/senses/{senseId}/examples/{exampleId}", method = RequestMethod.DELETE)
    public void deleteExample(@PathVariable Long senseId, @PathVariable Long exampleId) {
        User user = SecurityContextHelper.validateExistAuthenticatedUser();
        expressionDeletionService.deleteExampleAndRelations(exampleId);
    }
}
