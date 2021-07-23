package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.ExamGradeMasterDetail;
import com.jilit.irp.persistence.dto.ExamGradeMasterDetailId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author priyanka.tyagi
 */
public interface ExamGradeMasterDeatilIDAO extends IDAO {

    public Collection<?> findAll(String instituteid);

   
}
