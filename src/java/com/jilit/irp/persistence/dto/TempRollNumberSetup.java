package com.jilit.irp.persistence.dto;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ankur.goyal
 */
public class TempRollNumberSetup implements java.io.Serializable {

    private TempRollNumberSetupId id;
    private Short codelength;
    private String prefix;
    private String suffix;
    private String institutecode;
    private String academicyear;
    private String programtype;
    private String programcode;
    private String branchcode;
    private String mm;
    private String yy;
    private Byte runningno;
    private String seqid;
    private Set<TempRollNumberControl> temprollnumbercontrol = new HashSet<TempRollNumberControl>(0);
    private Set<TempRollNumberSetupDetail> temprollnumbersetupdetail = new HashSet<TempRollNumberSetupDetail>(0);

    public TempRollNumberSetup() {

    }

    public TempRollNumberSetup(TempRollNumberSetup dto) {
        this.id = dto.getId();
        this.codelength = dto.getCodelength();
        this.prefix = dto.getPrefix();
        this.suffix = dto.getSuffix();
        this.institutecode = dto.getInstitutecode();
        this.academicyear = dto.getAcademicyear();
        this.programtype = dto.getProgramtype();
        this.programcode = dto.getProgramcode();
        this.branchcode = dto.getBranchcode();
        this.mm = dto.getMm();
        this.yy = dto.getYy();
        this.runningno = dto.getRunningno();
        this.seqid = dto.getSeqid();
    }

    public TempRollNumberSetupId getId() {
        return id;
    }

    public void setId(TempRollNumberSetupId id) {
        this.id = id;
    }

    public Short getCodelength() {
        return codelength;
    }

    public void setCodelength(Short codelength) {
        this.codelength = codelength;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getInstitutecode() {
        return institutecode;
    }

    public void setInstitutecode(String institutecode) {
        this.institutecode = institutecode;
    }

    public String getAcademicyear() {
        return academicyear;
    }

    public void setAcademicyear(String academicyear) {
        this.academicyear = academicyear;
    }

    public String getProgramtype() {
        return programtype;
    }

    public void setProgramtype(String programtype) {
        this.programtype = programtype;
    }

    public String getProgramcode() {
        return programcode;
    }

    public void setProgramcode(String programcode) {
        this.programcode = programcode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    public Byte getRunningno() {
        return runningno;
    }

    public void setRunningno(Byte runningno) {
        this.runningno = runningno;
    }

    public String getSeqid() {
        return seqid;
    }

    public void setSeqid(String seqid) {
        this.seqid = seqid;
    }

    public Set<TempRollNumberControl> getTemprollnumbercontrol() {
        return temprollnumbercontrol;
    }

    public void setTemprollnumbercontrol(Set<TempRollNumberControl> temprollnumbercontrol) {
        this.temprollnumbercontrol = temprollnumbercontrol;
    }

    public Set<TempRollNumberSetupDetail> getTemprollnumbersetupdetail() {
        return temprollnumbersetupdetail;
    }

    public void setTemprollnumbersetupdetail(Set<TempRollNumberSetupDetail> temprollnumbersetupdetail) {
        this.temprollnumbersetupdetail = temprollnumbersetupdetail;
    }

}
