package com.jilit.irp.persistence.dao;

//@Repository
public class DAOFactory {

    private CityMasterIDAO cityMasterDAO;
    private CountryMasterIDAO countryMasterDAO;
    private StateMasterIDAO stateMasterDAO;
    private InstituteMasterIDAO instituteMasterDAO;
    private OtherStaffMasterIDAO otherStaffMasterDAO;
    private DBFilterHandlerIDAO dbFilterHandlerDAO;
    private UtilIDAO utilDAO;
    private InstituteCampusMasterIDAO instituteCampusMasterDAO;
    private IdGenerationControlIDAO idGenerationControlDAO;
    private IdGenerationSetupIDAO idGenerationSetupDAO;
    private DepartmentMasterIDAO departmentMasterDAO;
    private StudentMasterIDAO studentMasterDAO;
    private AcademicYearIDAO academicYearDAO;
    private BranchMasterIDAO branchMasterDAO;
    private ProgramMasterIDAO programMasterDAO;
    private Sct_IrpMlpIDAO sct_IrpMlpDAO;
    private Sct_IrpUsersIDAO sct_IrpUsersDAO;
    private Sct_IrpUserTypeIDAO sct_IrpUserTypeDAO;
    private OtherStaffMasterDetailIDAO otherStaffMasterDetailDAO;
    private Sct_ModuleRightsMasterIDAO sct_ModuleRightsMasterDAO;
    private ProgramTypeIDAO programTypeDAO;
    private ProgramTypeProgramTaggingIDAO programTypeProgramTaggingDAO;
    private V_StaffIDAO v_StaffDAO;
    private LocationMasterIDAO locationMasterDAO;
    private Sct_RoleMasterIDAO sct_RoleMasterDAO;
    private StudentPhotoIDAO studentPhotoDAO;
    private StudentAddressIDAO studentAddressDAO;
    private StudentPhoneIDAO studentPhoneDAO;
    private Sct_KioskMarqueeTextIDAO sct_KioskMarqueeTextDAO;
    private NumberingControlOthersIDAO numberingControlOthersDAO;
    private NumberingSetupOthersIDAO numberingSetupOthersDAO;
    private EmployeeAcademicDutyIDAO employeeAcademicDutyDAO;
    private Sct_UserHierarchyIDAO sct_UserHierarchyDAO;
    private Notify_NotificationAlertMasterIDAO notify_NotificationAlertMasterDAO;
    private Notify_NotificationToIDAO notify_NotificationToDAO;
    private Notification_SmsSetupIDAO notification_SmsSetupDAO;
    private Notify_AttachmentsIDAO notify_AttachmentsDAO;
    private ComplainTicketMasterIDAO complainTicketMasterDAO;
    private ComplainTicketPersonIDAO complainTicketPersonDAO;
    private ComplainTicketReplyIDAO complainTicketReplyDAO;
    private Log_LoginLogInfoIDAO loginLogDAO;
    private Sct_IrpModulesIDAO sct_IrpModulesDAO;
    private EmployeePhotoIDAO employeePhotoDAO;
    private ClientMasterIDAO clientMasterDAO;
    private CommonParameterIDAO parameterDAO;
    private StudentCategoryIDAO studentCategoryDAO;
    private QualificationMasterIDAO qualificationMasterDAO;
    private ProgramEligibilityExamsIDAO programEligibilityExamsDAO;
    private EligibilityExamInfoIDAO eligibilityExamInfoDAO;
    private StudentEligibilityExamInfoIDAO studentEligibilityExamInfoDAO;
    private StudentDocumentMasterIDAO studentDocumentMasterDAO;
    private ProgramMaxStyIDAO programMaxStyDAO;
    private StudentAdmissionCategoryIDAO studentAdmissionCategoryDAO;
    private QualificationBoardMasterIDAO qualificationBoardMasterDAO;
    private DisciplinaryTypeMasterIDAO disciplinaryTypeMasterDAO;
    private DisciplinaryOffenceMasterIDAO disciplinaryOffenceMasterDAO;
    private ProgramMinMaxLimitIDAO programMinMaxLimitDAO;
    private SubjectMasterIDAO subjectMasterDAO;
    private Ex_PatternMasterIDAO ex_PatternMasterDAO;
    private StudentAdmissionSubCategoryIDAO studentAdmissionSubCategoryDAO;
    private RollNumberingSetupIDAO rollNumberingSetupDAO;
    private StudentGroupMasterIDAO studentGroupMasterDAO;
    private StudentQuotaIDAO studentQuotaDAO;
    private StudentGroupCrLimitIDAO studentGroupCrLimitDAO;
    private ProgramWiseSubsectionIDAO programWiseSubsectionDAO;
    private DepartmentBranchTaggingIDAO departmentBranchTaggingDAO;
    private SectionMasterIDAO sectionMasterDAO;
    private ElectiveMasterIDAO electiveMasterDAO;
    private StudentLeaveMasterIDAO studentLeaveMasterDAO;
    private RollNumberingControlIDAO rollNumberingControlDAO;
    private BranchChangeRequestIDAO branchChangeRequestDAO;
    private StudentDisciplinaryActionIDAO studentDisciplinaryActionDAO;
    private RegistrationMasterIDAO registrationMasterDAO;
    private DepartmentSubjectTaggingIDAO departmentSubjectTaggingDAO;
    private StudentActivityDetailIDAO studentActivityDetailDAO;
    private SubjectTypeMasterIDAO subjectTypeMasterDAO;
    private StyTypeIDAO styTypeDAO;
    private PhdSelfcourseRegistrationIDAO phdSelfcourseRegistrationDAO;
    private BasketMasterIDAO basketMasterDAO;
    private ProgramSubjectDetailIDAO programSubjectDetailDAO;
    private ProgramSubjectTaggingIDAO programSubjectTaggingDAO;
    private FacultySubjectTaggingIDAO facultySubjectTaggingDAO;
    private OfferedODSubjectTaggingIDAO offeredODSubjectTaggingDAO;
    private OfferedODSubjectTaggingDetailIDAO offeredODSubjectTaggingDetailDAO;
    private ProgramSchemeIDAO programSchemeDAO;
    private StudentRegistrationIDAO studentRegistrationDAO;
    private BasketMasterDetailIDAO basketMasterDetailDAO;
    private StudentSubjectChoiceMasterIDAO studentSubjectChoiceMasterDAO;
    private V_ProgramSubjectTaggingIDAO v_ProgramSubjectTaggingDAO;
    private StudentResultIDAO studentResultDAO;
    private V_StudentComponentDetailIDAO v_StudentComponentDetailDAO;
    private StudentAttendanceIDAO studentAttendanceDAO;
    private InstituteRegistrationEventsIDAO instituteRegistrationEventsDAO;
    private StudentSubjectChoiceDetailIDAO studentSubjectChoiceDetailDAO;
    private DBDependentIDAO dBDependentDAO;
    private StudentRegistration_infoIDAO studentRegistration_infoDAO;
    private StyDescIDAO styDescDAO;
    private StoredProcedureIDAO storedProcedureDAO;
    private FacultyStudentTaggingIDAO facultyStudentTaggingDAO;
    private UpdateStyNumberIDAO updateStyNumberDAO;
    private DropElectiveSubjectIDAO dropElectiveSubjectDao;
    private Xl_RegSubjectDataIDAO xl_RegSubjectDataDAO;
    private TT_TimeTableIDAO tt_TimeTableDAO;
    private TT_SlotMasterIDAO tt_SlotMasterDAO;
    private SubjectCoordinatorIDAO subjectCoordinatorDAO;
    private PreRequisitForRegistrationIDAO preRequisitForRegistrationDAO;
    private PRStudentSubjectChoiceCountIDAO prStudentSubjectChoiceCountDAO;
    private Sis_RegistrationActivityMasterIDAO sis_RegistrationActivityMasterDAO;
    private Sis_RegistrationActivityRightsIDAO sis_RegistrationActivityRightsDAO;
    private SubjectAreaMasterIDAO subjectAreaMasterDAO;
    private StudentQualificationIDAO studentQualificationDAO;
    private OldvsNewSubjectIDAO oldvsNewSubjectDAO;
    private Setup_GIPCriteriaIDAO setup_GIPCriteriaDAO;
    private TempRollNumberSetupIDAO tempRollNumberSetupDAO;
    private TempRollNumberSetupDetailIDAO tempRollNumberSetupDetailDAO;
    private GuestStudentMasterIDAO guestStudentMasterDAO;
    private GuestStudentAddressIDAO guestStudentAddressDAO;
    private SubjectRegistrationCriteriaIDAO subjectRegistrationCriteriaDAO;
    private SubjectWiseBackPaperFeeIDAO subjectWiseBackPaperFeeDAO;
    private CourseCreditWiseFeeIDAO courseCreditWiseFeeDAO;
    private RegistrationParametersIDAO registrationParametersDAO;
    private RegistrationSubjectGroupIDAO registrationSubjectGroupDAO;
    private FSTwiseCoordinatorIDAO fSTwiseCoordinatorDAO;
    private SummerRegistrationSetupIDAO summerRegistrationSetupDAO;
    private SummerRegistrationSetupDetIDAO summerRegistrationSetupDetDAO;
    private PRFacultyStudentTaggingIDAO prFacultyStudentTaggingDAO;
    private StudentNRSubjectsIDAO studentNRSubjectsDAO;
    private Setup_GIPCriteriaDetailIDAO setup_GIPCriteriaDetailDAO;
    private ExamGradeMasterIDAO examGradeMasterDAO;
    private CoordinatorTypeIDAO coordinatorTypeDAO;
    private TempRollNumberControlIDAO tempRollNumberControlDAO;
    private TT_TimeTableAllocationDetailIDAO tt_TimeTableAllocationDetailDAO;
    private TT_TimeTableAllocationIDAO tt_TimeTableAllocationDAO;
    private TT_MultiFacultyTeachingLoadIDAO tt_MultiFacultyTeachingLoadDAO;
    private ExamGradeMasterDeatilIDAO examGradeMasterDetailDAO;
    private LoadDistributionGrantIDAO loadDistributionGrantDAO;
    private RegistrationInstructionUploadIDAO registrationInstructionUploadDAO;

    public CourseCreditWiseFeeIDAO getCourseCreditWiseFeeDAO() {
        return courseCreditWiseFeeDAO;
    }

    public void setCourseCreditWiseFeeDAO(CourseCreditWiseFeeIDAO courseCreditWiseFeeDAO) {
        this.courseCreditWiseFeeDAO = courseCreditWiseFeeDAO;
    }

    public SubjectWiseBackPaperFeeIDAO getSubjectWiseBackPaperFeeDAO() {
        return subjectWiseBackPaperFeeDAO;
    }

    public void setSubjectWiseBackPaperFeeDAO(SubjectWiseBackPaperFeeIDAO subjectWiseBackPaperFeeDAO) {
        this.subjectWiseBackPaperFeeDAO = subjectWiseBackPaperFeeDAO;
    }

    public SubjectRegistrationCriteriaIDAO getSubjectRegistrationCriteriaDAO() {
        return subjectRegistrationCriteriaDAO;
    }

    public void setSubjectRegistrationCriteriaDAO(SubjectRegistrationCriteriaIDAO subjectRegistrationCriteriaDAO) {
        this.subjectRegistrationCriteriaDAO = subjectRegistrationCriteriaDAO;
    }

    public TempRollNumberSetupIDAO getTempRollNumberSetupDAO() {
        return tempRollNumberSetupDAO;
    }

    public void setTempRollNumberSetupDAO(TempRollNumberSetupIDAO tempRollNumberSetupDAO) {
        this.tempRollNumberSetupDAO = tempRollNumberSetupDAO;
    }

    public TempRollNumberSetupDetailIDAO getTempRollNumberSetupDetailDAO() {
        return tempRollNumberSetupDetailDAO;
    }

    public void setTempRollNumberSetupDetailDAO(TempRollNumberSetupDetailIDAO tempRollNumberSetupDetailDAO) {
        this.tempRollNumberSetupDetailDAO = tempRollNumberSetupDetailDAO;
    }

    public Setup_GIPCriteriaIDAO getSetup_GIPCriteriaDAO() {
        return setup_GIPCriteriaDAO;
    }

    public void setSetup_GIPCriteriaDAO(Setup_GIPCriteriaIDAO setup_GIPCriteriaDAO) {
        this.setup_GIPCriteriaDAO = setup_GIPCriteriaDAO;
    }

    public OldvsNewSubjectIDAO getOldvsNewSubjectDAO() {
        return oldvsNewSubjectDAO;
    }

    public void setOldvsNewSubjectDAO(OldvsNewSubjectIDAO oldvsNewSubjectDAO) {
        this.oldvsNewSubjectDAO = oldvsNewSubjectDAO;
    }

    public StudentQualificationIDAO getStudentQualificationDAO() {
        return studentQualificationDAO;
    }

    public void setStudentQualificationDAO(StudentQualificationIDAO studentQualificationDAO) {
        this.studentQualificationDAO = studentQualificationDAO;
    }
    private ProgramSchemeAcadyearWiseIDAO programSchemeAcadyearWiseDAO;
    private ProgramSchemeAcadYearDetailIDAO programSchemeAcadYearDetailDAO;

    public SubjectAreaMasterIDAO getSubjectAreaMasterDAO() {
        return subjectAreaMasterDAO;
    }

    public void setSubjectAreaMasterDAO(SubjectAreaMasterIDAO subjectAreaMasterDAO) {
        this.subjectAreaMasterDAO = subjectAreaMasterDAO;
    }

    public Sis_RegistrationActivityRightsIDAO getSis_RegistrationActivityRightsDAO() {
        return sis_RegistrationActivityRightsDAO;
    }

    public void setSis_RegistrationActivityRightsDAO(Sis_RegistrationActivityRightsIDAO sis_RegistrationActivityRightsDAO) {
        this.sis_RegistrationActivityRightsDAO = sis_RegistrationActivityRightsDAO;
    }

    public Sis_RegistrationActivityMasterIDAO getSis_RegistrationActivityMasterDAO() {
        return sis_RegistrationActivityMasterDAO;
    }

    public void setSis_RegistrationActivityMasterDAO(Sis_RegistrationActivityMasterIDAO sis_RegistrationActivityMasterDAO) {
        this.sis_RegistrationActivityMasterDAO = sis_RegistrationActivityMasterDAO;
    }

    public PreRequisitForRegistrationIDAO getPreRequisitForRegistrationDAO() {
        return preRequisitForRegistrationDAO;
    }

    public void setPreRequisitForRegistrationDAO(PreRequisitForRegistrationIDAO preRequisitForRegistrationDAO) {
        this.preRequisitForRegistrationDAO = preRequisitForRegistrationDAO;
    }

    public SubjectCoordinatorIDAO getSubjectCoordinatorDAO() {
        return subjectCoordinatorDAO;
    }

    public void setSubjectCoordinatorDAO(SubjectCoordinatorIDAO subjectCoordinatorDAO) {
        this.subjectCoordinatorDAO = subjectCoordinatorDAO;
    }

    public TT_SlotMasterIDAO getTt_SlotMasterDAO() {
        return tt_SlotMasterDAO;
    }

    public void setTt_SlotMasterDAO(TT_SlotMasterIDAO tt_SlotMasterDAO) {
        this.tt_SlotMasterDAO = tt_SlotMasterDAO;
    }

    public TT_TimeTableIDAO getTt_TimeTableDAO() {
        return tt_TimeTableDAO;
    }

    public void setTt_TimeTableDAO(TT_TimeTableIDAO tt_TimeTableDAO) {
        this.tt_TimeTableDAO = tt_TimeTableDAO;
    }

    public Xl_RegSubjectDataIDAO getXl_RegSubjectDataDAO() {
        return xl_RegSubjectDataDAO;
    }

    public void setXl_RegSubjectDataDAO(Xl_RegSubjectDataIDAO xl_RegSubjectDataDAO) {
        this.xl_RegSubjectDataDAO = xl_RegSubjectDataDAO;
    }

    public DropElectiveSubjectIDAO getDropElectiveSubjectDao() {
        return dropElectiveSubjectDao;
    }

    public void setDropElectiveSubjectDao(DropElectiveSubjectIDAO dropElectiveSubjectDao) {
        this.dropElectiveSubjectDao = dropElectiveSubjectDao;
    }

    public UpdateStyNumberIDAO getUpdateStyNumberDAO() {
        return updateStyNumberDAO;
    }

    public void setUpdateStyNumberDAO(UpdateStyNumberIDAO updateStyNumberDAO) {
        this.updateStyNumberDAO = updateStyNumberDAO;
    }

    public StyDescIDAO getStyDescDAO() {
        return styDescDAO;
    }

    public void setStyDescDAO(StyDescIDAO styDescDAO) {
        this.styDescDAO = styDescDAO;
    }

    public StoredProcedureIDAO getStoredProcedureDAO() {
        return storedProcedureDAO;
    }

    public void setStoredProcedureDAO(StoredProcedureIDAO storedProcedureDAO) {
        this.storedProcedureDAO = storedProcedureDAO;
    }

    public StudentRegistration_infoIDAO getStudentRegistration_infoDAO() {
        return studentRegistration_infoDAO;
    }

    public void setStudentRegistration_infoDAO(StudentRegistration_infoIDAO studentRegistration_infoDAO) {
        this.studentRegistration_infoDAO = studentRegistration_infoDAO;
    }

    public DBDependentIDAO getdBDependentDAO() {
        return dBDependentDAO;
    }

    public void setdBDependentDAO(DBDependentIDAO dBDependentDAO) {
        this.dBDependentDAO = dBDependentDAO;
    }

    public StudentSubjectChoiceDetailIDAO getStudentSubjectChoiceDetailDAO() {
        return studentSubjectChoiceDetailDAO;
    }

    public void setStudentSubjectChoiceDetailDAO(StudentSubjectChoiceDetailIDAO studentSubjectChoiceDetailDAO) {
        this.studentSubjectChoiceDetailDAO = studentSubjectChoiceDetailDAO;
    }

    public InstituteRegistrationEventsIDAO getInstituteRegistrationEventsDAO() {
        return instituteRegistrationEventsDAO;
    }

    public void setInstituteRegistrationEventsDAO(InstituteRegistrationEventsIDAO instituteRegistrationEventsDAO) {
        this.instituteRegistrationEventsDAO = instituteRegistrationEventsDAO;
    }

    public StudentAttendanceIDAO getStudentAttendanceDAO() {
        return studentAttendanceDAO;
    }

    public void setStudentAttendanceDAO(StudentAttendanceIDAO studentAttendanceDAO) {
        this.studentAttendanceDAO = studentAttendanceDAO;
    }

    public V_StudentComponentDetailIDAO getV_StudentComponentDetailDAO() {
        return v_StudentComponentDetailDAO;
    }

    public void setV_StudentComponentDetailDAO(V_StudentComponentDetailIDAO v_StudentComponentDetailDAO) {
        this.v_StudentComponentDetailDAO = v_StudentComponentDetailDAO;
    }

    public StudentResultIDAO getStudentResultDAO() {
        return studentResultDAO;
    }

    public void setStudentResultDAO(StudentResultIDAO studentResultDAO) {
        this.studentResultDAO = studentResultDAO;
    }

    public V_ProgramSubjectTaggingIDAO getV_ProgramSubjectTaggingDAO() {
        return v_ProgramSubjectTaggingDAO;
    }

    public void setV_ProgramSubjectTaggingDAO(V_ProgramSubjectTaggingIDAO v_ProgramSubjectTaggingDAO) {
        this.v_ProgramSubjectTaggingDAO = v_ProgramSubjectTaggingDAO;
    }

    public BasketMasterDetailIDAO getBasketMasterDetailDAO() {
        return basketMasterDetailDAO;
    }

    public void setBasketMasterDetailDAO(BasketMasterDetailIDAO basketMasterDetailDAO) {
        this.basketMasterDetailDAO = basketMasterDetailDAO;
    }

    public ProgramSchemeIDAO getProgramSchemeDAO() {
        return programSchemeDAO;
    }

    public void setProgramSchemeDAO(ProgramSchemeIDAO programSchemeDAO) {
        this.programSchemeDAO = programSchemeDAO;
    }

    public OfferedODSubjectTaggingDetailIDAO getOfferedODSubjectTaggingDetailDAO() {
        return offeredODSubjectTaggingDetailDAO;
    }

    public void setOfferedODSubjectTaggingDetailDAO(OfferedODSubjectTaggingDetailIDAO offeredODSubjectTaggingDetailDAO) {
        this.offeredODSubjectTaggingDetailDAO = offeredODSubjectTaggingDetailDAO;
    }

    public OfferedODSubjectTaggingIDAO getOfferedODSubjectTaggingDAO() {
        return offeredODSubjectTaggingDAO;
    }

    public void setOfferedODSubjectTaggingDAO(OfferedODSubjectTaggingIDAO offeredODSubjectTaggingDAO) {
        this.offeredODSubjectTaggingDAO = offeredODSubjectTaggingDAO;
    }

    public FacultySubjectTaggingIDAO getFacultySubjectTaggingDAO() {
        return facultySubjectTaggingDAO;
    }

    public void setFacultySubjectTaggingDAO(FacultySubjectTaggingIDAO facultySubjectTaggingDAO) {
        this.facultySubjectTaggingDAO = facultySubjectTaggingDAO;
    }

    public ProgramSubjectTaggingIDAO getProgramSubjectTaggingDAO() {
        return programSubjectTaggingDAO;
    }

    public void setProgramSubjectTaggingDAO(ProgramSubjectTaggingIDAO programSubjectTaggingDAO) {
        this.programSubjectTaggingDAO = programSubjectTaggingDAO;
    }

    public ProgramSubjectDetailIDAO getProgramSubjectDetailDAO() {
        return programSubjectDetailDAO;
    }

    public void setProgramSubjectDetailDAO(ProgramSubjectDetailIDAO programSubjectDetailDAO) {
        this.programSubjectDetailDAO = programSubjectDetailDAO;
    }

    public BasketMasterIDAO getBasketMasterDAO() {
        return basketMasterDAO;
    }

    public void setBasketMasterDAO(BasketMasterIDAO basketMasterDAO) {
        this.basketMasterDAO = basketMasterDAO;
    }

    public PhdSelfcourseRegistrationIDAO getPhdSelfcourseRegistrationDAO() {
        return phdSelfcourseRegistrationDAO;
    }

    public void setPhdSelfcourseRegistrationDAO(PhdSelfcourseRegistrationIDAO phdSelfcourseRegistrationDAO) {
        this.phdSelfcourseRegistrationDAO = phdSelfcourseRegistrationDAO;
    }

    public StyTypeIDAO getStyTypeDAO() {
        return styTypeDAO;
    }

    public void setStyTypeDAO(StyTypeIDAO styTypeDAO) {
        this.styTypeDAO = styTypeDAO;
    }

    public SubjectTypeMasterIDAO getSubjectTypeMasterDAO() {
        return subjectTypeMasterDAO;
    }

    public void setSubjectTypeMasterDAO(SubjectTypeMasterIDAO subjectTypeMasterDAO) {
        this.subjectTypeMasterDAO = subjectTypeMasterDAO;
    }

    public StudentActivityDetailIDAO getStudentActivityDetailDAO() {
        return studentActivityDetailDAO;
    }

    public void setStudentActivityDetailDAO(StudentActivityDetailIDAO studentActivityDetailDAO) {
        this.studentActivityDetailDAO = studentActivityDetailDAO;
    }

    public DepartmentSubjectTaggingIDAO getDepartmentSubjectTaggingDAO() {
        return departmentSubjectTaggingDAO;
    }

    public void setDepartmentSubjectTaggingDAO(DepartmentSubjectTaggingIDAO departmentSubjectTaggingDAO) {
        this.departmentSubjectTaggingDAO = departmentSubjectTaggingDAO;
    }
    private RollNumberingSetupDetailIDAO rollNumberingSetupDetailDAO;
    private SubjectComponentIDAO subjectComponentDAO;
    private SpecializationMasterIDAO specializationMasterDAO;
    private StudentSpecializationMasterIDAO studentSpecializationMasterDAO;
    private SubjectsForBranchChangeIDAO subjectsForBranchChangeDAO;

    public RegistrationMasterIDAO getRegistrationMasterDAO() {
        return registrationMasterDAO;
    }

    public void setRegistrationMasterDAO(RegistrationMasterIDAO registrationMasterDAO) {
        this.registrationMasterDAO = registrationMasterDAO;
    }

    public StudentDisciplinaryActionIDAO getStudentDisciplinaryActionDAO() {
        return studentDisciplinaryActionDAO;
    }

    public void setStudentDisciplinaryActionDAO(StudentDisciplinaryActionIDAO studentDisciplinaryActionDAO) {
        this.studentDisciplinaryActionDAO = studentDisciplinaryActionDAO;
    }

    public RollNumberingControlIDAO getRollNumberingControlDAO() {
        return rollNumberingControlDAO;
    }

    public void setRollNumberingControlDAO(RollNumberingControlIDAO rollNumberingControlDAO) {
        this.rollNumberingControlDAO = rollNumberingControlDAO;
    }

    private V_ProgrmSecBranchSemIDAO v_ProgrmSecBranchSemDAO;

    public V_ProgrmSecBranchSemIDAO getV_ProgrmSecBranchSemDAO() {
        return v_ProgrmSecBranchSemDAO;
    }

    public void setV_ProgrmSecBranchSemDAO(V_ProgrmSecBranchSemIDAO v_ProgrmSecBranchSemDAO) {
        this.v_ProgrmSecBranchSemDAO = v_ProgrmSecBranchSemDAO;
    }

    public SubjectsForBranchChangeIDAO getSubjectsForBranchChangeDAO() {
        return subjectsForBranchChangeDAO;
    }

    public void setSubjectsForBranchChangeDAO(SubjectsForBranchChangeIDAO subjectsForBranchChangeDAO) {
        this.subjectsForBranchChangeDAO = subjectsForBranchChangeDAO;
    }

    public StudentSpecializationMasterIDAO getStudentSpecializationMasterDAO() {
        return studentSpecializationMasterDAO;
    }

    public void setStudentSpecializationMasterDAO(StudentSpecializationMasterIDAO studentSpecializationMasterDAO) {
        this.studentSpecializationMasterDAO = studentSpecializationMasterDAO;
    }

    public SpecializationMasterIDAO getSpecializationMasterDAO() {
        return specializationMasterDAO;
    }

    public void setSpecializationMasterDAO(SpecializationMasterIDAO specializationMasterDAO) {
        this.specializationMasterDAO = specializationMasterDAO;
    }

    public StudentLeaveMasterIDAO getStudentLeaveMasterDAO() {
        return studentLeaveMasterDAO;
    }

    public void setStudentLeaveMasterDAO(StudentLeaveMasterIDAO studentLeaveMasterDAO) {
        this.studentLeaveMasterDAO = studentLeaveMasterDAO;
    }

    public ElectiveMasterIDAO getElectiveMasterDAO() {
        return electiveMasterDAO;
    }

    public void setElectiveMasterDAO(ElectiveMasterIDAO electiveMasterDAO) {
        this.electiveMasterDAO = electiveMasterDAO;
    }

    public StudentGroupCrLimitIDAO getStudentGroupCrLimitDAO() {
        return studentGroupCrLimitDAO;
    }

    public void setStudentGroupCrLimitDAO(StudentGroupCrLimitIDAO studentGroupCrLimitDAO) {
        this.studentGroupCrLimitDAO = studentGroupCrLimitDAO;
    }

    public DepartmentBranchTaggingIDAO getDepartmentBranchTaggingDAO() {
        return departmentBranchTaggingDAO;
    }

    public void setDepartmentBranchTaggingDAO(DepartmentBranchTaggingIDAO departmentBranchTaggingDAO) {
        this.departmentBranchTaggingDAO = departmentBranchTaggingDAO;
    }

    public RollNumberingSetupDetailIDAO getRollNumberingSetupDetailDAO() {
        return rollNumberingSetupDetailDAO;
    }

    public void setRollNumberingSetupDetailDAO(RollNumberingSetupDetailIDAO rollNumberingSetupDetailDAO) {
        this.rollNumberingSetupDetailDAO = rollNumberingSetupDetailDAO;
    }

    public StudentQuotaIDAO getStudentQuotaDAO() {
        return studentQuotaDAO;
    }

    public void setStudentQuotaDAO(StudentQuotaIDAO studentQuotaDAO) {
        this.studentQuotaDAO = studentQuotaDAO;
    }

    public RollNumberingSetupIDAO getRollNumberingSetupDAO() {
        return rollNumberingSetupDAO;
    }

    public void setRollNumberingSetupDAO(RollNumberingSetupIDAO rollNumberingSetupDAO) {
        this.rollNumberingSetupDAO = rollNumberingSetupDAO;
    }

    public StudentAdmissionSubCategoryIDAO getStudentAdmissionSubCategoryDAO() {
        return studentAdmissionSubCategoryDAO;
    }

    public void setStudentAdmissionSubCategoryDAO(StudentAdmissionSubCategoryIDAO studentAdmissionSubCategoryDAO) {
        this.studentAdmissionSubCategoryDAO = studentAdmissionSubCategoryDAO;
    }

    public ProgramMaxStyIDAO getProgramMaxStyDAO() {
        return programMaxStyDAO;
    }

    public StudentGroupMasterIDAO getStudentGroupMasterDAO() {
        return studentGroupMasterDAO;
    }

    public void setStudentGroupMasterDAO(StudentGroupMasterIDAO studentGroupMasterDAO) {
        this.studentGroupMasterDAO = studentGroupMasterDAO;
    }

    public void setProgramMaxStyDAO(ProgramMaxStyIDAO programMaxStyDAO) {
        this.programMaxStyDAO = programMaxStyDAO;
    }

    public ProgramMinMaxLimitIDAO getProgramMinMaxLimitDAO() {
        return programMinMaxLimitDAO;
    }

    public void setProgramMinMaxLimitDAO(ProgramMinMaxLimitIDAO programMinMaxLimitDAO) {
        this.programMinMaxLimitDAO = programMinMaxLimitDAO;
    }

    public StudentDocumentMasterIDAO getStudentDocumentMasterDAO() {
        return studentDocumentMasterDAO;
    }

    public void setStudentDocumentMasterDAO(StudentDocumentMasterIDAO studentDocumentMasterDAO) {
        this.studentDocumentMasterDAO = studentDocumentMasterDAO;
    }

    public QualificationBoardMasterIDAO getQualificationBoardMasterDAO() {
        return qualificationBoardMasterDAO;
    }

    public void setQualificationBoardMasterDAO(QualificationBoardMasterIDAO qualificationBoardMasterDAO) {
        this.qualificationBoardMasterDAO = qualificationBoardMasterDAO;
    }

    public StudentEligibilityExamInfoIDAO getStudentEligibilityExamInfoDAO() {
        return studentEligibilityExamInfoDAO;
    }

    public void setStudentEligibilityExamInfoDAO(StudentEligibilityExamInfoIDAO studentEligibilityExamInfoDAO) {
        this.studentEligibilityExamInfoDAO = studentEligibilityExamInfoDAO;
    }

    public EligibilityExamInfoIDAO getEligibilityExamInfoDAO() {
        return eligibilityExamInfoDAO;
    }

    public void setEligibilityExamInfoDAO(EligibilityExamInfoIDAO eligibilityExamInfoDAO) {
        this.eligibilityExamInfoDAO = eligibilityExamInfoDAO;
    }

    public ProgramEligibilityExamsIDAO getProgramEligibilityExamsDAO() {
        return programEligibilityExamsDAO;
    }

    public void setProgramEligibilityExamsDAO(ProgramEligibilityExamsIDAO programEligibilityExamsDAO) {
        this.programEligibilityExamsDAO = programEligibilityExamsDAO;
    }

    public QualificationMasterIDAO getQualificationMasterDAO() {
        return qualificationMasterDAO;
    }

    public void setQualificationMasterDAO(QualificationMasterIDAO qualificationMasterDAO) {
        this.qualificationMasterDAO = qualificationMasterDAO;
    }

    public StudentCategoryIDAO getStudentCategoryDAO() {
        return studentCategoryDAO;
    }

    public void setStudentCategoryDAO(StudentCategoryIDAO StudentCategoryDAO) {
        this.studentCategoryDAO = StudentCategoryDAO;
    }

    public CommonParameterIDAO getParameterDAO() {
        return parameterDAO;
    }

    public void setParameterDAO(CommonParameterIDAO parameterDAO) {
        this.parameterDAO = parameterDAO;
    }

    public ClientMasterIDAO getClientMasterDAO() {
        return clientMasterDAO;
    }

    public void setClientMasterDAO(ClientMasterIDAO clientMasterDAO) {
        this.clientMasterDAO = clientMasterDAO;
    }

    public EmployeePhotoIDAO getEmployeePhotoDAO() {
        return employeePhotoDAO;
    }

    public void setEmployeePhotoDAO(EmployeePhotoIDAO employeePhotoDAO) {
        this.employeePhotoDAO = employeePhotoDAO;
    }

    public InstituteCampusMasterIDAO getInstituteCampusMasterDAO() {
        return instituteCampusMasterDAO;
    }

    public void setInstituteCampusMasterDAO(InstituteCampusMasterIDAO instituteCampusMasterDAO) {
        this.instituteCampusMasterDAO = instituteCampusMasterDAO;
    }

    public CityMasterIDAO getCityMasterDAO() {
        return cityMasterDAO;
    }

    public void setCityMasterDAO(CityMasterIDAO cityMasterDAO) {
        this.cityMasterDAO = cityMasterDAO;
    }

    public CountryMasterIDAO getCountryMasterDAO() {
        return countryMasterDAO;
    }

    public void setCountryMasterDAO(CountryMasterIDAO countryMasterDAO) {
        this.countryMasterDAO = countryMasterDAO;
    }

    public StateMasterIDAO getStateMasterDAO() {
        return stateMasterDAO;
    }

    public void setStateMasterDAO(StateMasterIDAO stateMasterDAO) {
        this.stateMasterDAO = stateMasterDAO;
    }

    public InstituteMasterIDAO getInstituteMasterDAO() {
        return instituteMasterDAO;
    }

    public void setInstituteMasterDAO(InstituteMasterIDAO instituteMasterDAO) {
        this.instituteMasterDAO = instituteMasterDAO;
    }

    public OtherStaffMasterIDAO getOtherStaffMasterDAO() {
        return otherStaffMasterDAO;
    }

    public void setOtherStaffMasterDAO(OtherStaffMasterIDAO otherStaffMasterDAO) {
        this.otherStaffMasterDAO = otherStaffMasterDAO;
    }

    public DBFilterHandlerIDAO getDbFilterHandlerDAO() {
        return dbFilterHandlerDAO;
    }

    public void setDbFilterHandlerDAO(DBFilterHandlerIDAO dbFilterHandlerDAO) {
        this.dbFilterHandlerDAO = dbFilterHandlerDAO;
    }

    public UtilIDAO getUtilDAO() {
        return utilDAO;
    }

    public void setUtilDAO(UtilIDAO utilDAO) {
        this.utilDAO = utilDAO;
    }

    public IdGenerationControlIDAO getIdGenerationControlDAO() {
        return idGenerationControlDAO;
    }

    public void setIdGenerationControlDAO(IdGenerationControlIDAO idGenerationControlDAO) {
        this.idGenerationControlDAO = idGenerationControlDAO;
    }

    public IdGenerationSetupIDAO getIdGenerationSetupDAO() {
        return idGenerationSetupDAO;
    }

    public void setIdGenerationSetupDAO(IdGenerationSetupIDAO idGenerationSetupDAO) {
        this.idGenerationSetupDAO = idGenerationSetupDAO;
    }

    public DepartmentMasterIDAO getDepartmentMasterDAO() {
        return departmentMasterDAO;
    }

    public void setDepartmentMasterDAO(DepartmentMasterIDAO departmentMasterDAO) {
        this.departmentMasterDAO = departmentMasterDAO;
    }

    public StudentMasterIDAO getStudentMasterDAO() {
        return studentMasterDAO;
    }

    public void setStudentMasterDAO(StudentMasterIDAO studentMasterDAO) {
        this.studentMasterDAO = studentMasterDAO;
    }

    public AcademicYearIDAO getAcademicYearDAO() {
        return academicYearDAO;
    }

    public void setAcademicYearDAO(AcademicYearIDAO academicYearDAO) {
        this.academicYearDAO = academicYearDAO;
    }

    public BranchMasterIDAO getBranchMasterDAO() {
        return branchMasterDAO;
    }

    public void setBranchMasterDAO(BranchMasterIDAO branchMasterDAO) {
        this.branchMasterDAO = branchMasterDAO;
    }

    public ProgramMasterIDAO getProgramMasterDAO() {
        return programMasterDAO;
    }

    public void setProgramMasterDAO(ProgramMasterIDAO programMasterDAO) {
        this.programMasterDAO = programMasterDAO;
    }

    public Sct_IrpMlpIDAO getSct_IrpMlpDAO() {
        return sct_IrpMlpDAO;
    }

    public void setSct_IrpMlpDAO(Sct_IrpMlpIDAO sct_IrpMlpDAO) {
        this.sct_IrpMlpDAO = sct_IrpMlpDAO;
    }

    public Sct_IrpUsersIDAO getSct_IrpUsersDAO() {
        return sct_IrpUsersDAO;
    }

    public void setSct_IrpUsersDAO(Sct_IrpUsersIDAO sct_IrpUsersDAO) {
        this.sct_IrpUsersDAO = sct_IrpUsersDAO;
    }

    public Sct_IrpUserTypeIDAO getSct_IrpUserTypeDAO() {
        return sct_IrpUserTypeDAO;
    }

    public void setSct_IrpUserTypeDAO(Sct_IrpUserTypeIDAO sct_IrpUserTypeDAO) {
        this.sct_IrpUserTypeDAO = sct_IrpUserTypeDAO;
    }
    public OtherStaffMasterDetailIDAO getOtherStaffMasterDetailDAO() {
        return otherStaffMasterDetailDAO;
    }

    public void setOtherStaffMasterDetailDAO(OtherStaffMasterDetailIDAO otherStaffMasterDetailDAO) {
        this.otherStaffMasterDetailDAO = otherStaffMasterDetailDAO;
    }

    public Sct_ModuleRightsMasterIDAO getSct_ModuleRightsMasterDAO() {
        return sct_ModuleRightsMasterDAO;
    }

    public void setSct_ModuleRightsMasterDAO(Sct_ModuleRightsMasterIDAO sct_ModuleRightsMasterDAO) {
        this.sct_ModuleRightsMasterDAO = sct_ModuleRightsMasterDAO;
    }

    public ProgramTypeIDAO getProgramTypeDAO() {
        return programTypeDAO;
    }

    public void setProgramTypeDAO(ProgramTypeIDAO programTypeDAO) {
        this.programTypeDAO = programTypeDAO;
    }

    public ProgramTypeProgramTaggingIDAO getProgramTypeProgramTaggingDAO() {
        return programTypeProgramTaggingDAO;
    }

    public void setProgramTypeProgramTaggingDAO(ProgramTypeProgramTaggingIDAO programTypeProgramTaggingDAO) {
        this.programTypeProgramTaggingDAO = programTypeProgramTaggingDAO;
    }

    public V_StaffIDAO getV_StaffDAO() {
        return v_StaffDAO;
    }

    public void setV_StaffDAO(V_StaffIDAO v_StaffDAO) {
        this.v_StaffDAO = v_StaffDAO;
    }

    public LocationMasterIDAO getLocationMasterDAO() {
        return locationMasterDAO;
    }

    public void setLocationMasterDAO(LocationMasterIDAO locationMasterDAO) {
        this.locationMasterDAO = locationMasterDAO;
    }

    public Sct_RoleMasterIDAO getSct_RoleMasterDAO() {
        return sct_RoleMasterDAO;
    }

    public void setSct_RoleMasterDAO(Sct_RoleMasterIDAO sct_RoleMasterDAO) {
        this.sct_RoleMasterDAO = sct_RoleMasterDAO;
    }

    public StudentPhotoIDAO getStudentPhotoDAO() {
        return studentPhotoDAO;
    }

    public void setStudentPhotoDAO(StudentPhotoIDAO studentPhotoDAO) {
        this.studentPhotoDAO = studentPhotoDAO;
    }

    public StudentAddressIDAO getStudentAddressDAO() {
        return studentAddressDAO;
    }

    public void setStudentAddressDAO(StudentAddressIDAO studentAddressDAO) {
        this.studentAddressDAO = studentAddressDAO;
    }

    public StudentPhoneIDAO getStudentPhoneDAO() {
        return studentPhoneDAO;
    }

    public void setStudentPhoneDAO(StudentPhoneIDAO studentPhoneDAO) {
        this.studentPhoneDAO = studentPhoneDAO;
    }

    public Sct_KioskMarqueeTextIDAO getSct_KioskMarqueeTextDAO() {
        return sct_KioskMarqueeTextDAO;
    }

    public void setSct_KioskMarqueeTextDAO(Sct_KioskMarqueeTextIDAO sct_KioskMarqueeTextDAO) {
        this.sct_KioskMarqueeTextDAO = sct_KioskMarqueeTextDAO;
    }

    public NumberingControlOthersIDAO getNumberingControlOthersDAO() {
        return numberingControlOthersDAO;
    }

    public void setNumberingControlOthersDAO(NumberingControlOthersIDAO numberingControlOthersDAO) {
        this.numberingControlOthersDAO = numberingControlOthersDAO;
    }

    public NumberingSetupOthersIDAO getNumberingSetupOthersDAO() {
        return numberingSetupOthersDAO;
    }

    public void setNumberingSetupOthersDAO(NumberingSetupOthersIDAO numberingSetupOthersDAO) {
        this.numberingSetupOthersDAO = numberingSetupOthersDAO;
    }

    public EmployeeAcademicDutyIDAO getEmployeeAcademicDutyDAO() {
        return employeeAcademicDutyDAO;
    }

    public void setEmployeeAcademicDutyDAO(EmployeeAcademicDutyIDAO employeeAcademicDutyDAO) {
        this.employeeAcademicDutyDAO = employeeAcademicDutyDAO;
    }

    public Sct_UserHierarchyIDAO getSct_UserHierarchyDAO() {
        return sct_UserHierarchyDAO;
    }

    public void setSct_UserHierarchyDAO(Sct_UserHierarchyIDAO sct_UserHierarchyDAO) {
        this.sct_UserHierarchyDAO = sct_UserHierarchyDAO;
    }

    public Notify_NotificationAlertMasterIDAO getNotify_NotificationAlertMasterDAO() {
        return notify_NotificationAlertMasterDAO;
    }

    public void setNotify_NotificationAlertMasterDAO(Notify_NotificationAlertMasterIDAO notify_NotificationAlertMasterDAO) {
        this.notify_NotificationAlertMasterDAO = notify_NotificationAlertMasterDAO;
    }

    public Notify_NotificationToIDAO getNotify_NotificationToDAO() {
        return notify_NotificationToDAO;
    }

    public void setNotify_NotificationToDAO(Notify_NotificationToIDAO notify_NotificationToDAO) {
        this.notify_NotificationToDAO = notify_NotificationToDAO;
    }

    public Notification_SmsSetupIDAO getNotification_SmsSetupDAO() {
        return notification_SmsSetupDAO;
    }

    public void setNotification_SmsSetupDAO(Notification_SmsSetupIDAO notification_SmsSetupDAO) {
        this.notification_SmsSetupDAO = notification_SmsSetupDAO;
    }

    public Notify_AttachmentsIDAO getNotify_AttachmentsDAO() {
        return notify_AttachmentsDAO;
    }

    public void setNotify_AttachmentsDAO(Notify_AttachmentsIDAO notify_AttachmentsDAO) {
        this.notify_AttachmentsDAO = notify_AttachmentsDAO;
    }

    public ComplainTicketMasterIDAO getComplainTicketMasterDAO() {
        return complainTicketMasterDAO;
    }

    public void setComplainTicketMasterDAO(ComplainTicketMasterIDAO complainTicketMasterDAO) {
        this.complainTicketMasterDAO = complainTicketMasterDAO;
    }

    public ComplainTicketPersonIDAO getComplainTicketPersonDAO() {
        return complainTicketPersonDAO;
    }

    public void setComplainTicketPersonDAO(ComplainTicketPersonIDAO complainTicketPersonDAO) {
        this.complainTicketPersonDAO = complainTicketPersonDAO;
    }

    public ComplainTicketReplyIDAO getComplainTicketReplyDAO() {
        return complainTicketReplyDAO;
    }

    public void setComplainTicketReplyDAO(ComplainTicketReplyIDAO complainTicketReplyDAO) {
        this.complainTicketReplyDAO = complainTicketReplyDAO;
    }

    public Log_LoginLogInfoIDAO getLoginLogDAO() {
        return loginLogDAO;
    }

    public void setLoginLogDAO(Log_LoginLogInfoIDAO loginLogDAO) {
        this.loginLogDAO = loginLogDAO;
    }

    public Sct_IrpModulesIDAO getSct_IrpModulesDAO() {
        return sct_IrpModulesDAO;
    }

    public void setSct_IrpModulesDAO(Sct_IrpModulesIDAO sct_IrpModulesDAO) {
        this.sct_IrpModulesDAO = sct_IrpModulesDAO;
    }

    public StudentAdmissionCategoryIDAO getStudentAdmissionCategoryDAO() {
        return studentAdmissionCategoryDAO;
    }

    public void setStudentAdmissionCategoryDAO(StudentAdmissionCategoryIDAO studentAdmissionCategoryDAO) {
        this.studentAdmissionCategoryDAO = studentAdmissionCategoryDAO;
    }

    public DisciplinaryTypeMasterIDAO getDisciplinaryTypeMasterDAO() {
        return disciplinaryTypeMasterDAO;
    }

    public void setDisciplinaryTypeMasterDAO(DisciplinaryTypeMasterIDAO disciplinaryTypeMasterDAO) {
        this.disciplinaryTypeMasterDAO = disciplinaryTypeMasterDAO;
    }

    public DisciplinaryOffenceMasterIDAO getDisciplinaryOffenceMasterDAO() {
        return disciplinaryOffenceMasterDAO;
    }

    public void setDisciplinaryOffenceMasterDAO(DisciplinaryOffenceMasterIDAO disciplinaryOffenceMasterDAO) {
        this.disciplinaryOffenceMasterDAO = disciplinaryOffenceMasterDAO;
    }

    public SubjectMasterIDAO getSubjectMasterDAO() {
        return subjectMasterDAO;
    }

    public void setSubjectMasterDAO(SubjectMasterIDAO subjectMasterDAO) {
        this.subjectMasterDAO = subjectMasterDAO;
    }

    public Ex_PatternMasterIDAO getEx_PatternMasterDAO() {
        return ex_PatternMasterDAO;
    }

    public void setEx_PatternMasterDAO(Ex_PatternMasterIDAO ex_PatternMasterDAO) {
        this.ex_PatternMasterDAO = ex_PatternMasterDAO;
    }

    public SubjectComponentIDAO getSubjectComponentDAO() {
        return subjectComponentDAO;
    }

    public void setSubjectComponentDAO(SubjectComponentIDAO subjectComponentDAO) {
        this.subjectComponentDAO = subjectComponentDAO;
    }

    public ProgramWiseSubsectionIDAO getProgramWiseSubsectionDAO() {
        return programWiseSubsectionDAO;
    }

    public void setProgramWiseSubsectionDAO(ProgramWiseSubsectionIDAO programWiseSubsectionDAO) {
        this.programWiseSubsectionDAO = programWiseSubsectionDAO;
    }

    public SectionMasterIDAO getSectionMasterDAO() {
        return sectionMasterDAO;
    }

    public void setSectionMasterDAO(SectionMasterIDAO sectionMasterDAO) {
        this.sectionMasterDAO = sectionMasterDAO;
    }

    public BranchChangeRequestIDAO getBranchChangeRequestDAO() {
        return branchChangeRequestDAO;
    }

    public StudentRegistrationIDAO getStudentRegistrationDAO() {
        return studentRegistrationDAO;
    }

    public void setStudentRegistrationDAO(StudentRegistrationIDAO studentRegistrationDAO) {
        this.studentRegistrationDAO = studentRegistrationDAO;
    }

    public void setBranchChangeRequestDAO(BranchChangeRequestIDAO branchChangeRequestDAO) {
        this.branchChangeRequestDAO = branchChangeRequestDAO;
    }

    public StudentSubjectChoiceMasterIDAO getStudentSubjectChoiceMasterDAO() {
        return studentSubjectChoiceMasterDAO;
    }

    public void setStudentSubjectChoiceMasterDAO(StudentSubjectChoiceMasterIDAO studentSubjectChoiceMasterDAO) {
        this.studentSubjectChoiceMasterDAO = studentSubjectChoiceMasterDAO;
    }

    public FacultyStudentTaggingIDAO getFacultyStudentTaggingDAO() {
        return facultyStudentTaggingDAO;
    }

    public void setFacultyStudentTaggingDAO(FacultyStudentTaggingIDAO facultyStudentTaggingDAO) {
        this.facultyStudentTaggingDAO = facultyStudentTaggingDAO;
    }

    public PRStudentSubjectChoiceCountIDAO getPrStudentSubjectChoiceCountDAO() {
        return prStudentSubjectChoiceCountDAO;
    }

    public void setPrStudentSubjectChoiceCountDAO(PRStudentSubjectChoiceCountIDAO prStudentSubjectChoiceCountDAO) {
        this.prStudentSubjectChoiceCountDAO = prStudentSubjectChoiceCountDAO;
    }

    public ProgramSchemeAcadyearWiseIDAO getProgramSchemeAcadyearWiseDAO() {
        return programSchemeAcadyearWiseDAO;
    }

    public void setProgramSchemeAcadyearWiseDAO(ProgramSchemeAcadyearWiseIDAO programSchemeAcadyearWiseDAO) {
        this.programSchemeAcadyearWiseDAO = programSchemeAcadyearWiseDAO;
    }

    public ProgramSchemeAcadYearDetailIDAO getProgramSchemeAcadYearDetailDAO() {
        return programSchemeAcadYearDetailDAO;
    }

    public void setProgramSchemeAcadYearDetailDAO(ProgramSchemeAcadYearDetailIDAO programSchemeAcadYearDetailDAO) {
        this.programSchemeAcadYearDetailDAO = programSchemeAcadYearDetailDAO;
    }

    public GuestStudentMasterIDAO getGuestStudentMasterDAO() {
        return guestStudentMasterDAO;
    }

    public void setGuestStudentMasterDAO(GuestStudentMasterIDAO guestStudentMasterDAO) {
        this.guestStudentMasterDAO = guestStudentMasterDAO;
    }

    public GuestStudentAddressIDAO getGuestStudentAddressDAO() {
        return guestStudentAddressDAO;
    }

    public void setGuestStudentAddressDAO(GuestStudentAddressIDAO guestStudentAddressDAO) {
        this.guestStudentAddressDAO = guestStudentAddressDAO;
    }

    public RegistrationParametersIDAO getRegistrationParametersDAO() {
        return registrationParametersDAO;
    }

    public void setRegistrationParametersDAO(RegistrationParametersIDAO registrationParametersDAO) {
        this.registrationParametersDAO = registrationParametersDAO;
    }

    public RegistrationSubjectGroupIDAO getRegistrationSubjectGroupDAO() {
        return registrationSubjectGroupDAO;
    }

    public void setRegistrationSubjectGroupDAO(RegistrationSubjectGroupIDAO registrationSubjectGroupDAO) {
        this.registrationSubjectGroupDAO = registrationSubjectGroupDAO;
    }

    public FSTwiseCoordinatorIDAO getfSTwiseCoordinatorDAO() {
        return fSTwiseCoordinatorDAO;
    }

    public void setfSTwiseCoordinatorDAO(FSTwiseCoordinatorIDAO fSTwiseCoordinatorDAO) {
        this.fSTwiseCoordinatorDAO = fSTwiseCoordinatorDAO;
    }

    public SummerRegistrationSetupIDAO getSummerRegistrationSetupDAO() {
        return summerRegistrationSetupDAO;
    }

    public void setSummerRegistrationSetupDAO(SummerRegistrationSetupIDAO summerRegistrationSetupDAO) {
        this.summerRegistrationSetupDAO = summerRegistrationSetupDAO;
    }

    public SummerRegistrationSetupDetIDAO getSummerRegistrationSetupDetDAO() {
        return summerRegistrationSetupDetDAO;
    }

    public void setSummerRegistrationSetupDetDAO(SummerRegistrationSetupDetIDAO summerRegistrationSetupDetDAO) {
        this.summerRegistrationSetupDetDAO = summerRegistrationSetupDetDAO;
    }

    public PRFacultyStudentTaggingIDAO getPrFacultyStudentTaggingDAO() {
        return prFacultyStudentTaggingDAO;
    }

    public void setPrFacultyStudentTaggingDAO(PRFacultyStudentTaggingIDAO prFacultyStudentTaggingDAO) {
        this.prFacultyStudentTaggingDAO = prFacultyStudentTaggingDAO;
    }

    public StudentNRSubjectsIDAO getStudentNRSubjectsDAO() {
        return studentNRSubjectsDAO;
    }

    public void setStudentNRSubjectsDAO(StudentNRSubjectsIDAO studentNRSubjectsDAO) {
        this.studentNRSubjectsDAO = studentNRSubjectsDAO;
    }

    public Setup_GIPCriteriaDetailIDAO getSetup_GIPCriteriaDetailDAO() {
        return setup_GIPCriteriaDetailDAO;
    }

    public void setSetup_GIPCriteriaDetailDAO(Setup_GIPCriteriaDetailIDAO setup_GIPCriteriaDetailDAO) {
        this.setup_GIPCriteriaDetailDAO = setup_GIPCriteriaDetailDAO;
    }
    public ExamGradeMasterIDAO getExamGradeMasterDAO() {
        return examGradeMasterDAO;
    }

    public void setExamGradeMasterDAO(ExamGradeMasterIDAO examGradeMasterDAO) {
        this.examGradeMasterDAO = examGradeMasterDAO;
    }

    public CoordinatorTypeIDAO getCoordinatorTypeDAO() {
        return coordinatorTypeDAO;
    }

    public void setCoordinatorTypeDAO(CoordinatorTypeIDAO coordinatorTypeDAO) {
        this.coordinatorTypeDAO = coordinatorTypeDAO;
    }

    public TempRollNumberControlIDAO getTempRollNumberControlDAO() {
        return tempRollNumberControlDAO;
    }

    public void setTempRollNumberControlDAO(TempRollNumberControlIDAO tempRollNumberControlDAO) {
        this.tempRollNumberControlDAO = tempRollNumberControlDAO;
    }

    public TT_TimeTableAllocationDetailIDAO getTt_TimeTableAllocationDetailDAO() {
        return tt_TimeTableAllocationDetailDAO;
    }

    public void setTt_TimeTableAllocationDetailDAO(TT_TimeTableAllocationDetailIDAO tt_TimeTableAllocationDetailDAO) {
        this.tt_TimeTableAllocationDetailDAO = tt_TimeTableAllocationDetailDAO;
    }

    public TT_TimeTableAllocationIDAO getTt_TimeTableAllocationDAO() {
        return tt_TimeTableAllocationDAO;
    }

    public void setTt_TimeTableAllocationDAO(TT_TimeTableAllocationIDAO tt_TimeTableAllocationDAO) {
        this.tt_TimeTableAllocationDAO = tt_TimeTableAllocationDAO;
    }

    public TT_MultiFacultyTeachingLoadIDAO getTt_MultiFacultyTeachingLoadDAO() {
        return tt_MultiFacultyTeachingLoadDAO;
    }

    public void setTt_MultiFacultyTeachingLoadDAO(TT_MultiFacultyTeachingLoadIDAO tt_MultiFacultyTeachingLoadDAO) {
        this.tt_MultiFacultyTeachingLoadDAO = tt_MultiFacultyTeachingLoadDAO;
    }

    public ExamGradeMasterDeatilIDAO getExamGradeMasterDetailDAO() {
        return examGradeMasterDetailDAO;
    }

    public void setExamGradeMasterDetailDAO(ExamGradeMasterDeatilIDAO examGradeMasterDetailDAO) {
        this.examGradeMasterDetailDAO = examGradeMasterDetailDAO;
    }

    public LoadDistributionGrantIDAO getLoadDistributionGrantDAO() {
        return loadDistributionGrantDAO;
    }

    public void setLoadDistributionGrantDAO(LoadDistributionGrantIDAO loadDistributionGrantDAO) {
        this.loadDistributionGrantDAO = loadDistributionGrantDAO;
    }

    public RegistrationInstructionUploadIDAO getRegistrationInstructionUploadDAO() {
        return registrationInstructionUploadDAO;
    }

    public void setRegistrationInstructionUploadDAO(RegistrationInstructionUploadIDAO registrationInstructionUploadDAO) {
        this.registrationInstructionUploadDAO = registrationInstructionUploadDAO;
    }

    

}
