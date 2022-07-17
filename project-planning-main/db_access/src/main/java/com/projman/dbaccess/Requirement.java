package com.projman.dbaccess;

public class Requirement {
    private int reqId;
    private String reqDesc;
    private int reqType;
    private int projId;

    public Requirement(int ReqId, String ReqDesc, int ReqType, int ProjId) {
        reqId = ReqId;
        reqDesc = ReqDesc;
        reqType = ReqType;
        projId = ProjId;
    }

    public Requirement(String ReqDesc, int ReqType, int ProjId) {
        reqDesc = ReqDesc;
        reqType = ReqType;
        projId = ProjId;
    }

    public int getReqId() { return reqId; }

    public String getReqDesc() { return reqDesc; }

    public int getReqType() {return reqType; }

    public int getProjId() { return projId; }

    @Override
    public String toString() {
        return this.reqDesc;
    }
}
