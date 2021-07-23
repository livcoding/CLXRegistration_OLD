package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dto.TempRollNumberSetupId;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ankur.goyal
 */
public interface TempRollNumberSetupIDAO extends IDAO {

    public int checkIfChildExist(final TempRollNumberSetupId id);

    public List getAllTempRollNumberSetup(String instituteid, String academicyear);

    public List getAllEditTempRollNumberSetup(String instituteid, String groupid);
    
    
    public abstract Object findByPrimaryKey1(final Serializable id);
}
