package com.projman.dbaccess;

import java.util.Date;

public class ProjectTime {
    private int ptId;
    private int hours;
    private int memberId;
    private int phaseId;
    private Date workDate;
    private int projectId;

    public ProjectTime(int PtId, int Hours, int MemberId, int PhaseId, Date WorkDate, int ProjectId) {
        ptId = PtId;
        hours = Hours;
        memberId = MemberId;
        phaseId = PhaseId;
        workDate = WorkDate;
        projectId = ProjectId;
    }

    public ProjectTime(int Hours, int MemberId, int PhaseId, Date WorkDate, int ProjectId) {
        hours = Hours;
        memberId = MemberId;
        phaseId = PhaseId;
        workDate = WorkDate;
        projectId = ProjectId;
    }

    public int getPtId() { return ptId; }

    public int getHours() { return hours; }

    public int getMemberId() { return memberId; }

    public int getPhaseId() { return phaseId; }

    public Date getWorkDate() { return workDate; }

    public int getProjectId() { return projectId; }

}
