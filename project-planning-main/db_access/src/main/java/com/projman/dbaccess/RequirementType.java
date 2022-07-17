package com.projman.dbaccess;

public class RequirementType {
    int reqType;
    String reqTypeDesc;

    public RequirementType(int ReqType, String ReqTypeDesc) {
        reqType = ReqType;
        reqTypeDesc = ReqTypeDesc;
    }

    public RequirementType(String ReqTypeDesc) {
        reqTypeDesc = ReqTypeDesc;
    }

    public int getReqType() { return reqType; }

    public String getReqTypeDesc() { return reqTypeDesc; }

    @Override
    public String toString() {
        return this.reqTypeDesc;
    }
}
