/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao.impl;

import com.jilit.irp.persistence.dao.HibernateDAO;
import com.jilit.irp.persistence.dao.StoredProcedureIDAO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

/**
 *
 * @author sunny.singhal
 */
public class StoredProcedureDAO extends HibernateDAO implements StoredProcedureIDAO {

    private static final Log log = LogFactory.getLog(StoredProcedureDAO.class);

    public Collection<?> findAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object findByPrimaryKey(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    CallableStatement cstmt = null;
    static Connection con = null;
    Session session = null;

    @Override
    public String RegistrationPermited(final String pInstID, final String pRegID, final String pStudID, final int stynumber, final String flag) {

        String retVal = null;
        try {

            if (con == null) {
                session = getSession();
                con = session.connection();
            }
            cstmt = con.prepareCall("{?=call clxsis.registrationpermited(?,?,?,?,?)}");
            cstmt.registerOutParameter(1, Types.VARCHAR);
            cstmt.setString(2, pInstID);
            cstmt.setString(3, pRegID);
            cstmt.setString(4, pStudID);
            cstmt.setInt(5, stynumber);
            cstmt.setString(6, flag);
            cstmt.execute();
            retVal = cstmt.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cstmt = null;
            }
            if (con != null) {
                try {
                    con.close();
                    session.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    con = null;
                    session = null;
                }
            }
        }
        return retVal;
    }

    public String getTTApprovalAppCanlData(final String instituteid, final String registrationid, final String departmentid, final String status, final String programid, final String academicyear, final String sty) {
        String retVal = null;
        Double d = Double.valueOf(sty);
        //  System.out.print("inst : "+instituteid+" Reg: "+registrationid+"Dept. : "+departmentid+"Status : "+ status+"Prog. : "+programid+"Acad yr. : "+academicyear);
        try {
            if (con == null) {
                session = getSession();
                con = session.connection();
            }
            cstmt = con.prepareCall("{call CLXSIS.ApproveTT_TeachingLoad(?,?,?,?,?,?,?,?)}");
            cstmt.setString(1, instituteid);
            cstmt.setString(2, registrationid);
            cstmt.setString(3, departmentid);

            cstmt.setString(4, programid);
            cstmt.setString(5, academicyear);
            cstmt.setInt(6, d.intValue());
            cstmt.setString(7, status);
            cstmt.registerOutParameter(8, Types.VARCHAR);
            cstmt.execute();
            retVal = cstmt.getString(8);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cstmt = null;
            }
            if (con != null) {
                try {
                    con.close();
                    session.clear();
                    session.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    con = null;
                    session = null;
                }
            }
        }
        return retVal;
    }
    public String subjectRegDataUploadExcel(String pUserID, String pTaskID, String pProcessingType, String pErrorCode, String pReturnFlag, String pErrorMessage, String pSPFlag, String pSPCode) {
        String retVal = null;
        try {
            if (con == null) {
                session = getSession();
                con = session.connection();
            }
            cstmt = con.prepareCall("{call clxsis.SubjectRegDataUploadExcel(?,?,?,?,?,?,?,?)}");//sis
            cstmt.setString(1, pUserID);
            cstmt.setString(2, pTaskID);
            cstmt.setString(3, pProcessingType);
            cstmt.setString(4, pErrorCode);
            cstmt.setString(5, pReturnFlag);
            cstmt.setString(6, pErrorMessage);
            cstmt.setString(7, "S");//pSPFlag);
            cstmt.setString(8, "ALL");//pSPCode);

            cstmt.registerOutParameter(4, Types.VARCHAR);
            cstmt.registerOutParameter(5, Types.VARCHAR);
            cstmt.registerOutParameter(6, Types.VARCHAR);
            cstmt.execute();
            retVal = cstmt.getString(4) + "@@" + cstmt.getString(5) + "@@" + cstmt.getString(6);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cstmt = null;
            }
            if (con != null) {
                try {
                    con.close();
                    session.clear();
                    session.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    con = null;
                    session = null;
                }
            }
        }
        return retVal;
    }

    public String saveTTAllocationData(final String instituteid, final String registrationid, final String mDay, final String mSTAFFTYPE,
            final String mSTAFFID, final String mFromTime, final String mToTime, final String mSLOTID) {
        String retVal = null;
        try {
            if (con == null) {
                session = getSession();
                con = session.connection();
            }
            //       PROCEDURE PopulateBulkTeachingLoad(mINSTITUTEID Varchar2,mREGISTRATIONID Varchar2,mDay varchar2,mSTAFFTYPE Varchar2,
            //mSTAFFID Varchar2,mFromTime Varchar2,mToTime Varchar2,mSLOTID Varchar2, mRetValue OUT Varchar2)
            cstmt = con.prepareCall("{call clxsis.PopulateBulkTeachingLoad(?,?,?,?,?,?,?,?,?)}");
            cstmt.setString(1, instituteid);
            cstmt.setString(2, registrationid);
            cstmt.setString(3, mDay);
            cstmt.setString(4, mSTAFFTYPE);
            cstmt.setString(5, mSTAFFID);
            cstmt.setString(6, mFromTime);
            cstmt.setString(7, mToTime);
            cstmt.setString(8, mSLOTID);
            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.execute();
            retVal = cstmt.getString(9);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                cstmt = null;
            }
            if (con != null) {
                try {
                    con.close();
                    session.clear();
                    session.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    con = null;
                    session = null;
                }
            }
        }
        return retVal;
    }
//
//  public String SchedularCorrectionTool(String pInstituteId, String  pRegistrationID, Date pFromDate, Date pToDate, String pStaffId, String pProgramId, String  pStyNo, String  pSubjectId,String pStaffType,String pMemberId,String pRemarks)
//    {
//        String retVal = null;
//        try {
//            if (con == null) {
//                con = getSession().connection();
//            }
//            cstmt = con.prepareCall("{call SIS.SchedularCorrectionTool(?,?,?,?,?,?,?,?,?,?,?,?)}");//sis
//            cstmt.setString(1, pInstituteId);
//            cstmt.setString(2, pRegistrationID);
//            cstmt.setDate(3, (java.sql.Date) pFromDate);
//            cstmt.setDate(4, (java.sql.Date) pToDate);
//            cstmt.setString(5, pStaffId);
//            cstmt.setString(6, pProgramId);
//            cstmt.setInt(7, Integer.parseInt(pStyNo));
//            cstmt.setString(8, pSubjectId);
//            cstmt.setString(9, pStaffType);
//            cstmt.setString(10, pMemberId);
//            cstmt.setString(11, pRemarks);
//
//            cstmt.registerOutParameter(12, Types.VARCHAR);
//            cstmt.execute();
//            retVal = cstmt.getString(12);
//       } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (cstmt != null) {
//                try {
//                    cstmt.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                cstmt = null;
//            }
//        }
//        return retVal;
//    }
}
