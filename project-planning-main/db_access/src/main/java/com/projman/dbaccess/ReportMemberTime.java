package com.projman.dbaccess;

import java.util.Date;

public class ReportMemberTime {
    private String memberName;
    private String phaseDesc;
    private int hours;
    private Date workDate;

    public ReportMemberTime(String MemberName, String PhaseDesc, int Hours, Date WorkDate) {
        memberName = MemberName;
        phaseDesc = PhaseDesc;
        hours = Hours;
        workDate = WorkDate;
    }

    public ReportMemberTime(String MemberName, String PhaseDesc, int Hours) {
        memberName = MemberName;
        phaseDesc = PhaseDesc;
        hours = Hours;
    }

    public ReportMemberTime(String MemberName, Date WorkDate, int Hours) {
        memberName = MemberName;
        workDate = WorkDate;
        hours = Hours;
    }

    public ReportMemberTime(String MemberName, int Hours) {
        memberName = MemberName;
        hours = Hours;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getPhaseDesc() {
        return phaseDesc;
    }

    public int getHours() {
        return hours;
    }

    public Date getWorkDate() {
        return workDate;
    }
}
