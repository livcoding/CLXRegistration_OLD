/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.StudentPhoto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface StudentPhotoIDAO extends IDAO {

    public List getStudentPhotoData(final String instituteid, final String studentid);
//
//   public ArrayList getStudentPhotoEnrollmentWiseData(final String instituteid, final String from,final String to);
//
//   public String insertAndUpdateStudentPhoto(final List<StudentPhoto> insertSPList);
//
//   public StudentPhoto getPhoto(String studentid);

    public String getStudentIdRank(String rank);

    public List getStudentPhotoSign(String rank);

    public String getStudentId(String enroll);
    
    public List getStudentPhotoForRegSlip(String studentids);

    public Object findByPrimaryKeyForBulkPhoto(Serializable id);

    public List getGuestStudentPhotoSign(String data);
}
