/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dao.impl;


import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StudentSubjectFacultyChoiceIDAO;
import com.jilit.irp.persistence.dto.BasketMasterDetail;
import com.jilit.irp.persistence.dto.BasketMasterDetailId;
import com.jilit.irp.persistence.dto.OfferedODSubjectTagging;
import com.jilit.irp.persistence.dto.OfferedODSubjectTaggingDetail;
import com.jilit.irp.persistence.dto.ProgramScheme;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadYearDetail;
import com.jilit.irp.persistence.dto.ProgramSchemeAcadyearWise;
import com.jilit.irp.persistence.dto.ProgramSchemeDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectDetail;
import com.jilit.irp.persistence.dto.ProgramSubjectTagging;
import com.jilit.irp.persistence.dto.StudentRegistration;
import com.jilit.irp.persistence.dto.StudentSubjectChoiceMaster;
import com.jilit.irp.persistence.dto.StudentSubjectFacultyChoices;
import com.jilit.irp.persistence.dto.V_Staff;

import java.io.Serializable;
import java.util.Collection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author subrata.lohar
 */

public class StudentSubjectFacultyChoiceDAO extends HibernateDAO implements StudentSubjectFacultyChoiceIDAO{

    private static final Log log = LogFactory.getLog(StudentSubjectFacultyChoiceDAO.class);

    public Collection<?> findAll() {
        log.info("Retrieving all StudentSubjectFacultyChoices records via Hibernate from the database");
        return this.find("from StudentSubjectFacultyChoices as tname order by seqid asc");
    }

    public void saveOrUpdate(Object record) {
        getHibernateTemplate().saveOrUpdate((StudentSubjectFacultyChoices) record);
    }

    public Object findByPrimaryKey(Serializable id) {
        return getHibernateTemplate().get(StudentSubjectFacultyChoices.class, id);
    }

//    public float calculateCredits(Object dto){
//         float credits_marks = 0;
//            if(dto instanceof ProgramSubjectTagging){
//                 Iterator itr = ((ProgramSubjectTagging)dto).getProgramsubjectdetails().iterator();//session.createFilter(((ProgramSubjectTagging)dto).getProgramsubjectdetails(), "where coalesce(deactive,'N')='N'").iterate();
//                 credits_marks = 0;
//                 while (itr.hasNext()) {
//                     ProgramSubjectDetail detail = (ProgramSubjectDetail) itr.next();
//                     credits_marks = credits_marks+ (detail.getSubjectcomponent().getClassperweekformula().floatValue()*detail.getTotalccpmarks().floatValue()/100);
//                }
//            }else if(dto instanceof OfferedODSubjectTagging){
//                 Iterator itr = ((OfferedODSubjectTagging)dto).getOfferedodsubjecttaggingdetails().iterator();//session.createFilter(((OfferedODSubjectTagging)dto).getOfferedodsubjecttaggingdetails(), "where coalesce(deactive,'N')='N'").iterate();
//                 credits_marks = 0;
//                 while (itr.hasNext()) {
//                     OfferedODSubjectTaggingDetail detail = (OfferedODSubjectTaggingDetail) itr.next();
//                     credits_marks = credits_marks+ (detail.getSubjectcomponent().getClassperweekformula().floatValue()*detail.getTotalccpmarks().floatValue()/100);
//                }
//            }else if(dto instanceof ProgramScheme){
//                 Iterator itr =(((ProgramScheme)dto).getProgramschemedetails()).iterator();
//                 credits_marks = 0;
//                 while (itr.hasNext()) {
//                     ProgramSchemeDetail detail = (ProgramSchemeDetail) itr.next();
//                     credits_marks = credits_marks+ (detail.getSubjectcomponent().getClassperweekformula().floatValue()*detail.getTotalccpmarks().floatValue()/100);
//                }
//            }else if(dto instanceof ProgramSchemeAcadyearWise){
//                 Iterator itr = (((ProgramSchemeAcadyearWise)dto).getProgramschemeacadyeardetails()).iterator();
//                 credits_marks = 0;
//                 while (itr.hasNext()) {
//                     ProgramSchemeAcadYearDetail detail = (ProgramSchemeAcadYearDetail) itr.next();
//                     credits_marks = credits_marks+ (detail.getSubjectcomponent().getClassperweekformula().floatValue()*detail.getTotalccpmarks().floatValue()/100);
//                }
//            }
//             return credits_marks;
//    }
//
//
//  public void getStudentSubjectChoiceData(final StudentRegistration registration, final List<ASObject> griddata, final List<ASObject> backlogdata) {
//        final List list = getHibernateTemplate().executeFind(new HibernateCallback() {
//
//            public Object doInHibernate(final Session session) throws HibernateException, SQLException {
//                ASObject object = null;
//                try {
//                    HashMap basketid_list = new HashMap();
//                    HashMap basketid_obj = new HashMap();
//                    ProgramSubjectTagging programSubjectTagging = null;
//                    float basketcredits = 0;
//                    float subjectcredits = 0;
//                    float totalmarks = 0;
//
//                    ArrayList subjectslist = new ArrayList<ASObject>();
//
//                    List<StudentSubjectChoiceMaster> studentSubjectChoiceMasterslist = session.createQuery("select distinct p from StudentSubjectChoiceMaster p left join p.studentsubjectchoicedetails d where size(p.studentsubjectchoicedetails)>0 and p.id.instituteid = '" + registration.getId().getInstituteid() + "'and p.id.studentid = '" + registration.getStudentmaster().getStudentid() + "' and p.id.stynumber='" + registration.getStynumber() + "' and coalesce(p.deactive,'N')='N'  and coalesce(p.subjectrunning,'Y')='Y'").setReadOnly(true).list();
//
//                    // List<ProgramSchemeAcadyearWise> acadyrwiselist = session.createQuery("select distinct p from ProgramSchemeAcadyearWise p left join p.programschemeacadyeardetails d where  size(p.programschemeacadyeardetails)>0 and p.id.instituteid = '" + registration.getId().getInstituteid() + "' and p.academicyear ='" + registration.getStudentmaster().getAcadyear() + "' and p.programid ='" + registration.getStudentmaster().getProgramid() + "'and p.sectionid = '" + registration.getStudentmaster().getSectionid() + "' and p.stynumber='" + registration.getStynumber() + "' and coalesce(p.deactive,'N')='N'  and coalesce(d.deactive,'N')='N'").setReadOnly(true).list();
//
//                    basketid_list = new HashMap();
//                    basketid_obj = new HashMap();
//
//                    if (!studentSubjectChoiceMasterslist.isEmpty()) {
//
//
//                        for (int i = 0; i < studentSubjectChoiceMasterslist.size(); i++) {
//                            StudentSubjectChoiceMaster choiceMaster = studentSubjectChoiceMasterslist.get(i);
//
//                            System.err.println("RegistrationId:"+registration.getId().getRegistrationid()
//                                                +"----ACADYEAR:"+registration.getStudentmaster().getAcadyear()
//                                                +"----SECTIONID:"+registration.getStudentmaster().getSectionid()
//                                                +"----PROGRAMID:"+registration.getStudentmaster().getProgramid()
//                                                +"----STYNUMBER:"+registration.getStynumber()
//                                                +"----SUBJECTID:"+choiceMaster.getId().getSubjectid());
//
//                            List<ProgramSubjectTagging> programSubjectTagginglist = session.createQuery("select distinct p from ProgramSubjectTagging p left join p.programsubjectdetails d where size(p.programsubjectdetails)>0 and p.id.instituteid = '" + registration.getId().getInstituteid() + "'" +
//                                    " and p.id.registrationid='" + registration.getId().getRegistrationid() + "' and p.academicyear='" + registration.getStudentmaster().getAcadyear() + "' and p.sectionid='" + registration.getStudentmaster().getSectionid() + "' and p.programid='" + registration.getStudentmaster().getProgramid() + "' and p.stynumber='" + registration.getStynumber() + "' " +
//                                    "and p.subjectid='" + choiceMaster.getId().getSubjectid() + "' and coalesce(p.deactive,'N')='N' and coalesce(d.deactive,'N')='N'").setReadOnly(true).list();
//
//
//                            if (programSubjectTagginglist.size() != 0) {
//                                programSubjectTagging = programSubjectTagginglist.get(0);
//
//
//                                ASObject obj = new ASObject();
//
//                                obj.put("subjectcode", choiceMaster.getSubjectmasterByStudentsubjectchoicemasterFk3().getSubjectcode());
//                                obj.put("subjectid", choiceMaster.getId().getSubjectid());
//                                obj.put("subjectdesc", choiceMaster.getSubjectmasterByStudentsubjectchoicemasterFk3().getSubjectdesc() + "(" + choiceMaster.getSubjectmasterByStudentsubjectchoicemasterFk3().getSubjectcode() + ")");
//
//                                subjectcredits = calculateCredits(programSubjectTagging);
//                                totalmarks = totalmarks+subjectcredits;
//
//                                obj.put("credits_marks", String.valueOf(subjectcredits));
//
//
//  //List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select p from StudentsubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() +"' and p.id.preferenceno='1'").setReadOnly(true).list();
//    //List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"'").setReadOnly(true).list();
//                                //for LTP
//                                List<ProgramSubjectDetail> programSubjectDetaillist = session.createQuery("select distinct p from ProgramSubjectDetail p where  p.id.instituteid = '" + registration.getId().getInstituteid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid() + "'and p.id.programsubjectid='" + programSubjectTagging.getId().getProgramsubjectid() + "' and coalesce(p.deactive,'N')='N'").setReadOnly(true).list();
//
//                              for (int k = 0; k < programSubjectDetaillist.size(); k++) {
//                                ProgramSubjectDetail programSubjectDetail = programSubjectDetaillist.get(k);
//                                  String ltp = programSubjectDetail.getSubjectcomponent().getSubjectcomponentcode();
//                                  System.err.println("LTP::::::::::::::::::::::::::"+ltp);
//                                  if(ltp.contains("L")){
//                                      obj.put("subjectcomponentL", true);
//                                  }
//                                  if(ltp.contains("T")){
//                                      obj.put("subjectcomponentT", true);
//                                  }
//                                  if(ltp.contains("P")){
//                                      obj.put("subjectcomponentP", true);
//                                  }
//                              }
//
//
//                                //For Faculty----
//                                List<V_Staff> employeelist = session.createQuery("select distinct p from V_Staff p where  p.departmentid='" + programSubjectTagging.getDepartmentid() + "' and coalesce(p.deactive,'N')='N'").setReadOnly(true).list();
//                                System.err.println("FACULTY:" + employeelist.size());
//                                List emplList = new ArrayList();
//                                emplList.add(new Option("Select Faculty","",""));
//                                for (int j = 0; j < employeelist.size(); j++) {
//                                    V_Staff v_Staff = employeelist.get(j);
//                                    emplList.add(new Option(v_Staff.getEmployeename(),v_Staff.getEmployeeid(),v_Staff.getStafftype()));
//                                }
//                                int selindex = 0;
//                                // emplList.add(new Option("XXX","XX001"));
//                                // emplList.add(new Option("YYY","YY001"));
//
//                  //new
//                                for (int m = 0; m < programSubjectDetaillist.size(); m++) {
//                                ProgramSubjectDetail programSubjectDetail = programSubjectDetaillist.get(m);
//
//                                String ltp = programSubjectDetail.getSubjectcomponent().getSubjectcomponentcode();
//
//
//
//
//                                if(ltp.contains("L")){
//                                       /* ----  */
//                                        List staffList = new ArrayList();
//                                        StudentSubjectFacultyChoices studentSubjectFacultyChoices = new StudentSubjectFacultyChoices();
//                                        //List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select distinct p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() + "' and p.id.subjectcomponentid='" + programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid()+"' and p.id.preferenceno='1'").setReadOnly(true).list();
//                                        List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select distinct p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() + "' and p.id.subjectcomponentid='" + programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid()+"' order by p.id.preferenceno asc ").setReadOnly(true).list();
//                                        if(subjectFacultylist.size()>0) {
//                                             for (int s = 0; s < subjectFacultylist.size(); s++) {
//                                               studentSubjectFacultyChoices = subjectFacultylist.get(s);
//                                                List<V_Staff> v_list = session.createQuery("select distinct p from V_Staff p where  p.id.employeeid='" + studentSubjectFacultyChoices.getStaffid()+"'").setReadOnly(true).list();
//                                                if(v_list.size()>0){
//                                                V_Staff vs =  v_list.get(0);
//                                                 staffList.add(new Option(vs.getEmployeename(),vs.getEmployeeid(),vs.getStafftype()));
//                                                }else{
//                                                 staffList.add(new Option("","",""));
//                                                }
//                                                }
//                                        }
//
//                                        /* ------- */
//
//                                   List<ASObject> empGridList = new ArrayList<ASObject>();
//
//                             //1
//                                   ASObject empGridObj = new ASObject();
//                                   empGridObj.put("rownum", "1");
//
//                                    if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                   if(emplList.contains(staffList.get(0))){
//                                   for(int a1 =0;a1 < emplList.size();a1++){
//                                    if(emplList.get(a1).equals(staffList.get(0))){
//                                      empGridObj.put("selectedIndex", a1);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(0));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                   empGridObj.put("uid", 0);
//                                   empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                   empGridList.add(empGridObj);
//
//
//                               //2
//                                   empGridObj = new ASObject();
//                                   empGridObj.put("rownum", "2");
//                                   //empGridObj.put("combolist", emplList);
//                                   //empGridObj.put("selectedItem", emplList.get(1));
//                                  if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                   if(emplList.contains(staffList.get(1))){
//                                   for(int a2 =0;a2 < emplList.size();a2++){
//                                    if(emplList.get(a2).equals(staffList.get(1))){
//                                      empGridObj.put("selectedIndex", a2);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(1));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                    empGridObj.put("uid", 1);
//                                   empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                   empGridList.add(empGridObj);
//
//
//                               //3
//                                   empGridObj = new ASObject();
//                                   empGridObj.put("rownum", "3");
//                                   //empGridObj.put("combolist", emplList);
//                                   //empGridObj.put("selectedItem", emplList.get(2));
//                                  if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                   if(emplList.contains(staffList.get(2))){
//                                   for(int a3 =0;a3 < emplList.size();a3++){
//                                    if(emplList.get(a3).equals(staffList.get(2))){
//                                      empGridObj.put("selectedIndex", a3);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(2));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                       empGridObj.put("selectedIndex", selindex);
//                                   }
//                                    empGridObj.put("uid", 2);
//                                   empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                   empGridList.add(empGridObj);
//
//
//
//                                //4
//                                   empGridObj = new ASObject();
//                                   empGridObj.put("rownum", "4");
//                                   //empGridObj.put("combolist", emplList);
//                                   // empGridObj.put("selectedItem", emplList.get(3));
//                                    if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                   if(emplList.contains(staffList.get(3))){
//                                   for(int a4 =0;a4 < emplList.size();a4++){
//                                    if(emplList.get(a4).equals(staffList.get(3))){
//                                      empGridObj.put("selectedIndex", a4);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(3));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                    empGridObj.put("uid", 3);
//                                   empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                   empGridList.add(empGridObj);
//
//
//
//                              //5
//                                   empGridObj = new ASObject();
//                                   empGridObj.put("rownum", "5");
//                                  // empGridObj.put("combolist", emplList);
//                                   //empGridObj.put("selectedItem", emplList.get(4));
//                                   if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                   if(emplList.contains(staffList.get(4))){
//                                   for(int a5 =0;a5 < emplList.size();a5++){
//                                    if(emplList.get(a5).equals(staffList.get(4))){
//                                      empGridObj.put("selectedIndex", a5);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(4));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                    empGridObj.put("uid", 4);
//                                   empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                   empGridList.add(empGridObj);
//
//
//                        obj.put("JASLIST", empGridList);
//                      }
//
//
//                      if(ltp.contains("T")){
//
//                                 /* ----  */
//                                        List staffList = new ArrayList();
//                                        StudentSubjectFacultyChoices studentSubjectFacultyChoices = new StudentSubjectFacultyChoices();
//                                        //List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select distinct p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() + "' and p.id.subjectcomponentid='" + programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid()+"' and p.id.preferenceno='1'").setReadOnly(true).list();
//                                        List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select distinct p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() + "' and p.id.subjectcomponentid='" + programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid()+"' order by p.id.preferenceno asc ").setReadOnly(true).list();
//                                        if(subjectFacultylist.size()>0) {
//                                             for (int s = 0; s < subjectFacultylist.size(); s++) {
//                                               studentSubjectFacultyChoices = subjectFacultylist.get(s);
//                                                List<V_Staff> v_list = session.createQuery("select distinct p from V_Staff p where  p.id.employeeid='" + studentSubjectFacultyChoices.getStaffid()+"'").setReadOnly(true).list();
//                                                 if(v_list.size()>0){
//                                                V_Staff vs =  v_list.get(0);
//                                                 staffList.add(new Option(vs.getEmployeename(),vs.getEmployeeid(),vs.getStafftype()));
//                                                 }else{
//                                                    staffList.add(new Option("","",""));
//                                                 }
//                                                 }
//                                        }
//
//                                        /* ------- */
//
//
//
//                           List<ASObject> empGridList = new ArrayList<ASObject>();
//
//                       //1
//                           ASObject empGridObj = new ASObject();
//                                empGridObj.put("rownum","1");
//                                //empGridObj.put("combolist", emplList);
//                               // empGridObj.put("selectedItem", emplList.get(4));
//                                 if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                if(emplList.contains(staffList.get(0))){
//                                   for(int b1 =0;b1 < emplList.size();b1++){
//                                    if(emplList.get(b1).equals(staffList.get(0))){
//                                      empGridObj.put("selectedIndex", b1);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(0));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                  empGridObj.put("uid", 0);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//                       //2
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","2");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(3));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(1))){
//                                   for(int b2 =0;b2 < emplList.size();b2++){
//                                    if(emplList.get(b2).equals(staffList.get(1))){
//                                      empGridObj.put("selectedIndex", b2);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(1));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                         empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 1);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//                        //3
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","3");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(2));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(2))){
//                                   for(int b3 =0;b3 < emplList.size();b3++){
//                                    if(emplList.get(b3).equals(staffList.get(2))){
//                                      empGridObj.put("selectedIndex", b3);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(2));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                         empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 2);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//
//                       //4
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","4");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(1));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(3))){
//                                   for(int b4 =0;b4 < emplList.size();b4++){
//                                    if(emplList.get(b4).equals(staffList.get(3))){
//                                      empGridObj.put("selectedIndex", b4);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(3));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                         empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 3);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//
//                        //5
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","5");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(0));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(4))){
//                                   for(int b5 =0;b5 < emplList.size();b5++){
//                                    if(emplList.get(b5).equals(staffList.get(4))){
//                                      empGridObj.put("selectedIndex", b5);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(4));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                         empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 4);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//                             obj.put("TUTORIALLIST", empGridList);
//                           }
//
//
//
//                           if(ltp.contains("P")){
//
//                                 /* ----  */
//                                        List staffList = new ArrayList();
//                                        StudentSubjectFacultyChoices studentSubjectFacultyChoices = new StudentSubjectFacultyChoices();
//                                        //List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select distinct p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() + "' and p.id.subjectcomponentid='" + programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid()+"' and p.id.preferenceno='1'").setReadOnly(true).list();
//                                        List<StudentSubjectFacultyChoices> subjectFacultylist = session.createQuery("select distinct p from StudentSubjectFacultyChoices p where  p.id.instituteid = '" + registration.getId().getInstituteid()+"' and p.id.studentid='" + registration.getId().getStudentid() + "' and p.id.subjectid='" + choiceMaster.getId().getSubjectid() +"' and p.id.registrationid='" + programSubjectTagging.getId().getRegistrationid()+"' and p.id.stynumber='" + registration.getStynumber() + "' and p.id.subjectcomponentid='" + programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid()+"' order by p.id.preferenceno asc ").setReadOnly(true).list();
//                                        if(subjectFacultylist.size()>0) {
//                                             for (int s = 0; s < subjectFacultylist.size(); s++) {
//                                               studentSubjectFacultyChoices = subjectFacultylist.get(s);
//                                                List<V_Staff> v_list = session.createQuery("select distinct p from V_Staff p where  p.id.employeeid='" + studentSubjectFacultyChoices.getStaffid()+"'").setReadOnly(true).list();
//                                                 if(v_list.size()>0){
//                                                V_Staff vs =  v_list.get(0);
//                                                 staffList.add(new Option(vs.getEmployeename(),vs.getEmployeeid(),vs.getStafftype()));
//                                                 }else{
//                                                  staffList.add(new Option("","",""));
//
//                                    }
//                                                 }
//                                        }
//
//                                        /* ------- */
//
//                               List<ASObject> empGridList = new ArrayList<ASObject>();
//
//                          //1
//                               ASObject empGridObj = new ASObject();
//                                empGridObj.put("rownum","1");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(2));
//                                 if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                                if(emplList.contains(staffList.get(0))){
//                                   for(int c1 =0;c1 < emplList.size();c1++){
//                                    if(emplList.get(c1).equals(staffList.get(0))){
//                                      empGridObj.put("selectedIndex", c1);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(0));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                  empGridObj.put("uid", 0);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//                          //2
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","2");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(1));
//                               if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(1))){
//                                   for(int c2 =0;c2 < emplList.size();c2++){
//                                    if(emplList.get(c2).equals(staffList.get(1))){
//                                      empGridObj.put("selectedIndex", c2);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(1));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 1);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//
//                         //3
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","3");
//                                //empGridObj.put("combolist", emplList);
//                               // empGridObj.put("selectedItem", emplList.get(0));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(2))){
//                                   for(int c3 =0;c3 < emplList.size();c3++){
//                                    if(emplList.get(c3).equals(staffList.get(2))){
//                                      empGridObj.put("selectedIndex", c3);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(2));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 2);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//                         //4
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","4");
//                                //empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(3));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(3))){
//                                   for(int c4 =0;c4 < emplList.size();c4++){
//                                    if(emplList.get(c4).equals(staffList.get(3))){
//                                      empGridObj.put("selectedIndex", c4);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(3));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 3);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//                         //5
//                                empGridObj = new ASObject();
//                               empGridObj.put("rownum","5");
//                               // empGridObj.put("combolist", emplList);
//                                //empGridObj.put("selectedItem", emplList.get(4));
//                                if (subjectFacultylist.size() > 0 && staffList.size()>0) {
//                               if(emplList.contains(staffList.get(4))){
//                                   for(int c5 =0;c5 < emplList.size();c5++){
//                                    if(emplList.get(c5).equals(staffList.get(4))){
//                                      empGridObj.put("selectedIndex", c5);
//                                    }
//                                   }
//                                   }else{
//                                    empGridObj.put("selectedIndex", selindex);
//                                   }
//
//                                       empGridObj.put("selectedItem", staffList.get(4));
//                                       empGridObj.put("combolist", emplList);
//
//                                   } else {
//                                       empGridObj.put("selectedItem", emplList.get(0));
//                                       empGridObj.put("combolist", emplList);
//                                        empGridObj.put("selectedIndex", selindex);
//                                   }
//                                 empGridObj.put("uid", 4);
//                                empGridObj.put("subjectcomponentid", programSubjectDetail.getSubjectcomponent().getId().getSubjectcomponentid());
//                                empGridList.add(empGridObj);
//
//
//                                obj.put("PRACTICALLIST", empGridList);
//                           }
//
//
//
//                         }
//
//
//
//
//
//                                subjectslist.add(obj);
//
//                                // basketid_list.put(programScheme.getBasketid(), new ArrayList().add("Option1"));
//
//                                basketid_list.put(choiceMaster.getBasketid(), subjectslist);
//
//
//                                if (!basketid_obj.containsKey(choiceMaster.getBasketid())) {
//                                    obj = new ASObject();
//
//                                    BasketMasterDetail detail = (BasketMasterDetail) session.get(BasketMasterDetail.class, new BasketMasterDetailId(registration.getId().getInstituteid(), choiceMaster.getBasketid(), registration.getStudentmaster().getAcadyear(), registration.getStudentmaster().getProgramid(), registration.getStudentmaster().getSectionid(), registration.getStynumber()));
//
//                                    obj.put("subjecttype", detail.getSubjecttypemaster().getSubjecttypedesc() + "(" + detail.getSubjecttypemaster().getSubjecttype() + ")");
//                                    obj.put("subjecttypeid", detail.getSubjecttypeid());
//
//                                    basketid_obj.put(choiceMaster.getBasketid(), obj);
//                                }
//                                ((ASObject) basketid_obj.get(choiceMaster.getBasketid())).put("credits_marks", String.valueOf(basketcredits));
//
//                            }
//                        }
//                    }
//                    Iterator iterator = basketid_obj.keySet().iterator();
//                    while (iterator.hasNext()) {
//                        Object basketid = iterator.next();
//                        ASObject dto = new ASObject();
//                        dto.put("dto", (ASObject) basketid_obj.get(basketid));
//                        dto.put("list", ((List) basketid_list.get(basketid)));
//                        dto.put("totalmarks",totalmarks);
//
//                        griddata.add(dto);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return griddata;
//            }
//        });
//    }
//
//




}

