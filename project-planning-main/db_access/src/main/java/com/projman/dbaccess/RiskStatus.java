package com.projman.dbaccess;

public class RiskStatus {
    private int riskStatusId;
    private String riskStatusDesc;

    public RiskStatus(int RiskStatusId, String RiskStatusDesc) {
        riskStatusId = RiskStatusId;
        riskStatusDesc = RiskStatusDesc;
    }

    public RiskStatus(String RiskStatusDesc) {
        riskStatusDesc = RiskStatusDesc;
    }

    public int getRiskStatusId() { return riskStatusId; }

    public String getRiskStatusDesc() { return riskStatusDesc; }

    @Override
    public String toString() {
        return this.riskStatusDesc;
    }
}
