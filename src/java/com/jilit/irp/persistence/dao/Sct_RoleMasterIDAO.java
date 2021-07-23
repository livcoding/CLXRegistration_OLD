/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import com.jilit.irp.persistence.dao.IDAO;
import com.jilit.irp.persistence.dto.Sct_RoleMaster;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author akshya.gaur
 */
public interface Sct_RoleMasterIDAO extends IDAO {

    public Collection<?> checkIfRoleNameExists(final String roleName);

    public int checkIfChildExist(final String roleid);

    public List<String> doValidate(final Sct_RoleMaster dto, final String mode);

    public List getCounsellingRoleList(String roleid);
    public Collection<?> getAssignedRoles();
    public List getModuleCode();
    public List getAssignedRole(String roleid);
    public List getRemainingRoles(String roleid, String moduleid);
    public Object findByPrimaryKey1(Serializable id);
    public boolean saveObjlist(List objList);
    public List getRoleCode();
}
