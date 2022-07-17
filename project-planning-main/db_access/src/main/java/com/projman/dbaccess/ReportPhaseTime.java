package com.projman.dbaccess;

import java.util.Date;

public class ReportPhaseTime {
    private String phaseDesc;
    private int hours;
    private Date workDate;

    public ReportPhaseTime(String PhaseDesc, int Hours, Date WorkDate) {
        phaseDesc = PhaseDesc;
        hours = Hours;
        workDate = WorkDate;
    }

    public ReportPhaseTime(String PhaseDesc, int Hours) {
        phaseDesc = PhaseDesc;
        hours = Hours;
    }

    public ReportPhaseTime(Date WorkDate, int Hours) {
        workDate = WorkDate;
        hours = Hours;
    }

    public ReportPhaseTime(int Hours) {
        hours = Hours;
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
