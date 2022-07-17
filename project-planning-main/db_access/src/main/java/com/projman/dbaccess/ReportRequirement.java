package com.projman.dbaccess;

public class ReportRequirement {
    private String reqDesc;
    private String reqType;

    public ReportRequirement(String ReqDesc, String ReqType) {
        reqDesc = ReqDesc;
        reqType = ReqType;
    }

    public String getReqDesc() { return reqDesc; }

    public String getReqType() { return reqType; }

    @Override
    public String toString() {
        return this.reqDesc;
    }
}
