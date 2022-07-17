package com.projman.dbaccess;

public class Project {
    private int projectId;
    private String projectName;
    private String projectDesc;
    private String owner;

    public Project(int ProjectId, String ProjectName, String ProjectDesc, String Owner) {
        projectId = ProjectId;
        projectName = ProjectName;
        projectDesc = ProjectDesc;
        owner = Owner;
    }

    public Project(String ProjectName, String ProjectDesc, String Owner) {
        projectName = ProjectName;
        projectDesc = ProjectDesc;
        owner = Owner;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return this.projectName;
    }
}



