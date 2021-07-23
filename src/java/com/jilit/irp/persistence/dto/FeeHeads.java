package com.jilit.irp.persistence.dto;
// Generated Apr 21, 2011 3:02:39 PM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FeeHeads implements java.io.Serializable {

    private FeeHeadsId id;
    private InstituteMaster institutemaster;
    private String companyid;
    private String glid;
    private String feehead;
    private String feeheaddesc;
    private String refundable;
    private String feetype;
    private Short repseqid;
    private Short collseqid;
    private String deactive;
    private String fineapplicable;
    private String finecompanyid;
    private String fineglid;
    private String receibleglid;

    public FeeHeads() {
    }

    public FeeHeads(FeeHeadsId id, String feehead, String feeheaddesc) {
        this.id = id;
        this.feehead = feehead;
        this.feeheaddesc = feeheaddesc;
    }

    public FeeHeads(FeeHeadsId id, String feehead) {
        this.id = id;
        this.feehead = feehead;
    }

    public FeeHeads(FeeHeads fhead) {
        this.id = fhead.getId();
        this.companyid = fhead.getCompanyid();
        this.glid = fhead.getGlid();
        this.feehead = fhead.getFeehead();
        this.feeheaddesc = fhead.getFeeheaddesc();
        this.refundable = fhead.getRefundable();
        this.fineapplicable = fhead.getFineapplicable();
        this.feetype = fhead.getFeetype();
        this.repseqid = fhead.getRepseqid();
        this.collseqid = fhead.getCollseqid();
        this.deactive = fhead.getDeactive();
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public FeeHeadsId getId() {
        return this.id;
    }

    public void setId(FeeHeadsId id) {
        this.id = id;
    }

    public InstituteMaster getInstitutemaster() {
        return this.institutemaster;
    }

    public void setInstitutemaster(InstituteMaster institutemaster) {
        this.institutemaster = institutemaster;
    }

    public String getFeehead() {
        return this.feehead;
    }

    public void setFeehead(String feehead) {
        this.feehead = feehead;
    }

    public String getFeeheaddesc() {
        return this.feeheaddesc;
    }

    public void setFeeheaddesc(String feeheaddesc) {
        this.feeheaddesc = feeheaddesc;
    }

    public String getRefundable() {
        return this.refundable;
    }

    public void setRefundable(String refundable) {
        this.refundable = refundable;
    }

    public String getFeetype() {
        return this.feetype;
    }

    public void setFeetype(String feetype) {
        this.feetype = feetype;
    }

    public Short getRepseqid() {
        return this.repseqid;
    }

    public void setRepseqid(Short repseqid) {
        this.repseqid = repseqid;
    }

    public Short getCollseqid() {
        return this.collseqid;
    }

    public void setCollseqid(Short collseqid) {
        this.collseqid = collseqid;
    }

    public String getDeactive() {
        return this.deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

    public String getGlid() {
        return glid;
    }

    public void setGlid(String glid) {
        this.glid = glid;
    }

    public String getIdStr() {
        return (id.getInstituteid() + "::" + id.getFeeheadid());
    }

    public String getFineapplicable() {
        return fineapplicable;
    }

    public void setFineapplicable(String fineapplicable) {
        this.fineapplicable = fineapplicable;
    }

    public String getFinecompanyid() {
        return finecompanyid;
    }

    public void setFinecompanyid(String finecompanyid) {
        this.finecompanyid = finecompanyid;
    }

    public String getFineglid() {
        return fineglid;
    }

    public void setFineglid(String fineglid) {
        this.fineglid = fineglid;
    }

    public String getReceibleglid() {
        return receibleglid;
    }

    public void setReceibleglid(String receibleglid) {
        this.receibleglid = receibleglid;
    }

    @Override
    public String toString() {
        return "FeeHeads{" + "id=" + id + ", institutemaster=" + institutemaster + ", companyid=" + companyid + ", glid=" + glid + ", feehead=" + feehead + ", feeheaddesc=" + feeheaddesc + ", refundable=" + refundable + ", feetype=" + feetype + ", repseqid=" + repseqid + ", collseqid=" + collseqid + ", deactive=" + deactive + ", fineapplicable=" + fineapplicable + ", finecompanyid=" + finecompanyid + ", fineglid=" + fineglid + ", receibleglid=" + receibleglid + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.institutemaster);
        hash = 53 * hash + Objects.hashCode(this.companyid);
        hash = 53 * hash + Objects.hashCode(this.glid);
        hash = 53 * hash + Objects.hashCode(this.feehead);
        hash = 53 * hash + Objects.hashCode(this.feeheaddesc);
        hash = 53 * hash + Objects.hashCode(this.refundable);
        hash = 53 * hash + Objects.hashCode(this.feetype);
        hash = 53 * hash + Objects.hashCode(this.repseqid);
        hash = 53 * hash + Objects.hashCode(this.collseqid);
        hash = 53 * hash + Objects.hashCode(this.deactive);
        hash = 53 * hash + Objects.hashCode(this.fineapplicable);
        hash = 53 * hash + Objects.hashCode(this.finecompanyid);
        hash = 53 * hash + Objects.hashCode(this.fineglid);
        hash = 53 * hash + Objects.hashCode(this.receibleglid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FeeHeads other = (FeeHeads) obj;
        if (!Objects.equals(this.companyid, other.companyid)) {
            return false;
        }
        if (!Objects.equals(this.glid, other.glid)) {
            return false;
        }
        if (!Objects.equals(this.feehead, other.feehead)) {
            return false;
        }
        if (!Objects.equals(this.feeheaddesc, other.feeheaddesc)) {
            return false;
        }
        if (!Objects.equals(this.refundable, other.refundable)) {
            return false;
        }
        if (!Objects.equals(this.feetype, other.feetype)) {
            return false;
        }
        if (!Objects.equals(this.deactive, other.deactive)) {
            return false;
        }
        if (!Objects.equals(this.fineapplicable, other.fineapplicable)) {
            return false;
        }
        if (!Objects.equals(this.finecompanyid, other.finecompanyid)) {
            return false;
        }
        if (!Objects.equals(this.fineglid, other.fineglid)) {
            return false;
        }
        if (!Objects.equals(this.receibleglid, other.receibleglid)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.institutemaster, other.institutemaster)) {
            return false;
        }
        if (!Objects.equals(this.repseqid, other.repseqid)) {
            return false;
        }
        if (!Objects.equals(this.collseqid, other.collseqid)) {
            return false;
        }
        return true;
    }

}
