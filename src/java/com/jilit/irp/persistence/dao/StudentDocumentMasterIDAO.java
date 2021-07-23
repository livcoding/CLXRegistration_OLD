/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.CategoryProgramDocumentTagging;
import com.jilit.irp.persistence.dto.StudentDocumentMaster;
import java.util.List;

/**
 *
 * @author v.kumar
 */
public interface StudentDocumentMasterIDAO extends IDAO {

    public int checkIfChildExist(final String documentid);
//
 //   public List<String> doValidate(final StudentDocumentMaster studentDocumentMaster, final String mode);
//
//    public List getTaggedDocuments(final String categoryid, final String programid, final String instituteid);
//
      public List getUnTaggedDocuments(final String instituteid, final String categoryid, final String programid);
//
    public String getSaveInsertForCategoryProgramDocumentTagging(List<CategoryProgramDocumentTagging> finalDeleteList,List<CategoryProgramDocumentTagging> finalInsertList);
}
