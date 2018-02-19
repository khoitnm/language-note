package org.tnmk.common.infrastructure.projectinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Khoi on 5/22/2016.
 */
@RestController
public class ProjectInfoResource {
    private final ProjectInfoProperties projectInfoProperties;

    @Autowired
    public ProjectInfoResource(ProjectInfoProperties projectInfoProperties) {this.projectInfoProperties = projectInfoProperties;}

    @RequestMapping(value = "/project-info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectInfoProperties getProjectInfoProperties() {
        return this.projectInfoProperties;
    }

}
