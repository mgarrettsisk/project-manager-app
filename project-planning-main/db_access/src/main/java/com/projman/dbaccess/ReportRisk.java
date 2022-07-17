package com.projman.dbaccess;

public class ReportRisk {

    private String riskDesc;
    private String riskStatusDesc;

    public ReportRisk(String RiskDesc, String RiskStatusDesc) {
        riskDesc = RiskDesc;
        riskStatusDesc = RiskStatusDesc;
    }

    public String getRiskDesc() { return riskDesc; }

    public String getRiskStatusDesc() { return riskStatusDesc; }

    @Override
    public String toString() {
        return this.riskDesc;
    }
}
