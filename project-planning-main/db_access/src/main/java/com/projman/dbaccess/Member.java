package com.projman.dbaccess;

public class Member {
    private int memberId;
    private String memberName;
    private int projectId;
    private int active;

    public Member(int MemberId, String MemberName, int ProjectId, int Active) {
        memberId = MemberId;
        memberName = MemberName;
        projectId = ProjectId;
        active = Active;
    }

    public Member(String MemberName, int ProjectId, int Active) {
        memberName = MemberName;
        projectId = ProjectId;
        active = Active;
    }

    public Member(int MemberId, int ProjectId, int Active) {
        memberId = MemberId;
        projectId = ProjectId;
        active = Active;
    }

    public int getMemberId() { return memberId; }

    public String getMemberName() {
        return memberName;
    }

    public int getProjectId() { return projectId; }

    public int getActive() { return active; }

    @Override
    public String toString() {
        return this.memberName;
    }

}
