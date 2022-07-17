package com.projman.dbaccess;

public class Risk {
    private int riskId;
    private String riskDesc;
    private int riskStatusId;
    private int projectId;

    public Risk(int RiskId, String RiskDesc, int RiskStatusId, int ProjectId) {
        riskId = RiskId;
        riskDesc = RiskDesc;
        riskStatusId = RiskStatusId;
        projectId = ProjectId;
    }

    public Risk(String RiskDesc, int RiskStatusId, int ProjectId) {
        riskDesc = RiskDesc;
        riskStatusId = RiskStatusId;
        projectId = ProjectId;
    }

    public Risk(int RiskId, int RiskStatusId, int ProjectId) {
        riskId = RiskId;
        riskStatusId = RiskStatusId;
        projectId = ProjectId;
    }

    public int getRiskId() { return riskId; }

    public String getRiskDesc() { return riskDesc; }

    public int getRiskStatusId() { return riskStatusId; }

    public int getProjectId() { return projectId; }

    @Override
    public String toString() {
        return this.riskDesc;
    }
}
