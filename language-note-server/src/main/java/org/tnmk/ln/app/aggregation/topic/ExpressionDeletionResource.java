package org.tnmk.ln.app.aggregation.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author khoi.tran on 4/1/17.
 */
@RestController
public class ExpressionDeletionResource {

    @Autowired
    private ExpressionDeletionService expressionDeletionService;

//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/expressions/{expressionId}/sense-groups/{senseGroupId}", method = RequestMethod.DELETE)
//    public void deleteSenseGroup(@PathVariable Long expressionId, @PathVariable Long senseGroupId) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        expressionDeletionService.deleteSenseGroupAndRelations(senseGroupId);
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/sense-groups/{senseGroupId}/senses/{senseId}", method = RequestMethod.DELETE)
//    public void deleteSense(@PathVariable Long senseGroupId, @PathVariable Long senseId) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        expressionDeletionService.deleteSenseAndRelations(senseGroupId);
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/senses/{senseId}/examples/{exampleId}", method = RequestMethod.DELETE)
//    public void deleteExample(@PathVariable Long senseId, @PathVariable Long exampleId) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        expressionDeletionService.deleteExampleAndRelations(exampleId);
//    }
//
//    @RequestMapping(value = UriPrefixConstants.API_PREFIX + "/senses/{senseId}/photo/{itemId}/detach", method = RequestMethod.DELETE)
//    public void deletePhotoFromSense(@PathVariable Long senseId, @PathVariable Long itemId) {
//        User user = SecurityContextHelper.validateExistAuthenticatedUser();
//        expressionDeletionService.deletePhotoAndRelations(itemId);
//    }
}
