package com.projman.dbaccess;

public class Phase {

    private int phaseId;
    private String phaseDesc;

    public Phase(int PhaseId, String PhaseDesc) {
        phaseId = PhaseId;
        phaseDesc = PhaseDesc;
    }

    public Phase(String PhaseDesc) {
        phaseDesc = PhaseDesc;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public String getPhaseDesc() {
        return phaseDesc;
    }

    @Override
    public String toString() {
        return this.phaseDesc;
    }
}


