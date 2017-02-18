package tnmk.common.infrastructure.projectinfo;

import java.io.Serializable;

public class ProjectInfoProperties implements Serializable {
    private String[] profiles;
    private String projectVersion;
    private String projectBuildTime;
    private String gitBuildNumber;
    private String gitRevision;
    private String gitTag;

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }

    public String getProjectBuildTime() {
        return projectBuildTime;
    }

    public void setProjectBuildTime(String projectBuildTime) {
        this.projectBuildTime = projectBuildTime;
    }

    public String getGitBuildNumber() {
        return gitBuildNumber;
    }

    public void setGitBuildNumber(String gitBuildNumber) {
        this.gitBuildNumber = gitBuildNumber;
    }

    public String getGitRevision() {
        return gitRevision;
    }

    public void setGitRevision(String gitRevision) {
        this.gitRevision = gitRevision;
    }

    public String[] getProfiles() {
        return profiles;
    }

    public void setProfiles(String[] profiles) {
        this.profiles = profiles;
    }

    public String getGitTag() {
        return gitTag;
    }

    public void setGitTag(String gitTag) {
        this.gitTag = gitTag;
    }
}

