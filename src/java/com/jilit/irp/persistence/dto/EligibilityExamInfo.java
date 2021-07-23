package com.jilit.irp.persistence.dto;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author deepak.gupta
 */
public class EligibilityExamInfo  implements java.io.Serializable {


     private EligibilityExamInfoId id;
     private String eeexamcode;
     private String eeexamname;
     private Short seqid;
     private String entryby;
     private Date entrydate;
     private Set eligibilityexaminfodetails = new HashSet(0);
     private Set programeligibilityexamses = new HashSet(0);

    public EligibilityExamInfo() {
    }

    public EligibilityExamInfo(EligibilityExamInfo master) {
          this.id = id;
           this.eeexamcode = master.eeexamcode;
           this.eeexamname = master.eeexamname;
           this.seqid = master.seqid;
           this.entryby = master.entryby;
           this.entrydate = master.entrydate;
    }


    public EligibilityExamInfo(EligibilityExamInfoId id, String eeexamname) {
        this.id = id;
        this.eeexamname = eeexamname;
    }
    public EligibilityExamInfo(EligibilityExamInfoId id, String eeexamcode, String eeexamname, Short seqid, String entryby, Date entrydate, Set eligibilityexaminfodetails, Set programeligibilityexamses) {
       this.id = id;
       this.eeexamcode = eeexamcode;
       this.eeexamname = eeexamname;
       this.seqid = seqid;
       this.entryby = entryby;
       this.entrydate = entrydate;
       this.eligibilityexaminfodetails = eligibilityexaminfodetails;
       this.programeligibilityexamses = programeligibilityexamses;
    }

    public EligibilityExamInfoId getId() {
        return this.id;
    }

    public void setId(EligibilityExamInfoId id) {
        this.id = id;
    }
    public String getEeexamcode() {
        return this.eeexamcode;
    }

    public void setEeexamcode(String eeexamcode) {
        this.eeexamcode = eeexamcode;
    }
    public String getEeexamname() {
        return this.eeexamname;
    }

    public void setEeexamname(String eeexamname) {
        this.eeexamname = eeexamname;
    }
    public Short getSeqid() {
        return this.seqid;
    }

    public void setSeqid(Short seqid) {
        this.seqid = seqid;
    }
    public String getEntryby() {
        return this.entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }
    public Date getEntrydate() {
        return this.entrydate;
    }

    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }
    public Set getEligibilityexaminfodetails() {
        return this.eligibilityexaminfodetails;
    }

    public void setEligibilityexaminfodetails(Set eligibilityexaminfodetails) {
        this.eligibilityexaminfodetails = eligibilityexaminfodetails;
    }
    public Set getProgrameligibilityexamses() {
        return this.programeligibilityexamses;
    }

    public void setProgrameligibilityexamses(Set programeligibilityexamses) {
        this.programeligibilityexamses = programeligibilityexamses;
    }
      public String getIdStr() {
        return (id.getInstituteid() + "::" + id.getEeexamid());
    }

}


