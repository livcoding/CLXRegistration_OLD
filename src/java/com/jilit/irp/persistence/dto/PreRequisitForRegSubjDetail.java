package com.jilit.irp.persistence.dto;

/**
 *
 * @author ankur.goyal
 */
public class PreRequisitForRegSubjDetail implements java.io.Serializable {

    private PreRequisitForRegSubjDetailId id;
    private SubjectMaster subjectmaster;
    private PreRequisitForRegistration prerequisitforregistration;
    private String deactive;

    public PreRequisitForRegSubjDetail() {
    }

    public PreRequisitForRegSubjDetail(PreRequisitForRegSubjDetail dto) {
        this.id = dto.getId();
    }

    public PreRequisitForRegSubjDetail(PreRequisitForRegSubjDetailId id, SubjectMaster subjectmaster, PreRequisitForRegistration prerequisitforregistration) {
        this.id = id;
        this.subjectmaster = subjectmaster;
        this.prerequisitforregistration = prerequisitforregistration;
    }

    public PreRequisitForRegSubjDetail(PreRequisitForRegSubjDetailId id, SubjectMaster subjectmaster, PreRequisitForRegistration prerequisitforregistration, String deactive) {
        this.id = id;
        this.subjectmaster = subjectmaster;
        this.prerequisitforregistration = prerequisitforregistration;
        this.deactive = deactive;
    }

    public PreRequisitForRegSubjDetailId getId() {
        return id;
    }

    public void setId(PreRequisitForRegSubjDetailId id) {
        this.id = id;
    }

    public SubjectMaster getSubjectmaster() {
        return subjectmaster;
    }

    public void setSubjectmaster(SubjectMaster subjectmaster) {
        this.subjectmaster = subjectmaster;
    }

    public PreRequisitForRegistration getPrerequisitforregistration() {
        return prerequisitforregistration;
    }

    public void setPrerequisitforregistration(PreRequisitForRegistration prerequisitforregistration) {
        this.prerequisitforregistration = prerequisitforregistration;
    }

    public String getDeactive() {
        return deactive;
    }

    public void setDeactive(String deactive) {
        this.deactive = deactive;
    }

//    public String getIdStr() {
//        return id.getInstituteid() + ":" + id.getAcademicyear() + ":" + id.getProgramid() + ":" + id.getBranchid() + ":" + id.getStynumber() + ":" + id.getSubjectid();
//    }
}
