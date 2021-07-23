package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.RegistrationInstruction;
import com.jilit.irp.persistence.dto.RegistrationInstructionId;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author priyanka.tyagi
 */
public interface RegistrationInstructionUploadIDAO extends IDAO {

//    public Collection<?> findAll(String instituteid);

     public List getRegistrationInstructionData(String instituteid);
   
}
