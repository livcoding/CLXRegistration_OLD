package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberSetupDetail implements java.io.Serializable {

    private TempRollNumberSetupDetailId id;
    private TempRollNumberSetup temprollnumbersetup;
    private ProgramType programtype;
    private BranchMaster branchmaster;
    private Academicyear academicyear;
    private ProgramMaster programmaster;

    public TempRollNumberSetupDetail() {

    }

    public TempRollNumberSetupDetail(TempRollNumberSetupDetail dto) {
        this.id = dto.getId();
    }

    public TempRollNumberSetupDetail(TempRollNumberSetupDetailId id, TempRollNumberSetup temprollnumbersetup, BranchMaster branchmaster) {
        this.id = id;
        this.temprollnumbersetup = temprollnumbersetup;
        this.branchmaster = branchmaster;
    }

    public TempRollNumberSetupDetail(TempRollNumberSetupDetailId id, TempRollNumberSetup temprollnumbersetup, ProgramType programtype, BranchMaster branchmaster, Academicyear academicyear, ProgramMaster programmaster) {
        this.id = id;
        this.temprollnumbersetup = temprollnumbersetup;
        this.programtype = programtype;
        this.branchmaster = branchmaster;
        this.academicyear = academicyear;
        this.programmaster = programmaster;
    }

    public ProgramType getProgramtype() {
        return programtype;
    }

    public void setProgramtype(ProgramType programtype) {
        this.programtype = programtype;
    }

    public Academicyear getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(Academicyear academicyear) {
        this.academicyear = academicyear;
    }

    public ProgramMaster getProgrammaster() {
        return programmaster;
    }

    public void setProgrammaster(ProgramMaster programmaster) {
        this.programmaster = programmaster;
    }

    public TempRollNumberSetupDetailId getId() {
        return id;
    }

    public void setId(TempRollNumberSetupDetailId id) {
        this.id = id;
    }

    public TempRollNumberSetup getTemprollnumbersetup() {
        return temprollnumbersetup;
    }

    public void setTemprollnumbersetup(TempRollNumberSetup temprollnumbersetup) {
        this.temprollnumbersetup = temprollnumbersetup;
    }

    public BranchMaster getBranchmaster() {
        return branchmaster;
    }

    public void setBranchmaster(BranchMaster branchmaster) {
        this.branchmaster = branchmaster;
    }

}
