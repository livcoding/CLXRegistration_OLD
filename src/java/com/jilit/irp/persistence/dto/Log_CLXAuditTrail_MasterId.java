/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.persistence.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author sumitk.singh
 */
public class Log_CLXAuditTrail_MasterId implements Serializable{

     private String logentryid;
     private BigDecimal logid;

    public Log_CLXAuditTrail_MasterId() {
    }

    public String getLogentryid() {
        return logentryid;
    }

    public void setLogentryid(String logentryid) {
        this.logentryid = logentryid;
    }
 
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.logentryid);
        hash = 23 * hash + Objects.hashCode(this.logid);
        return hash;
    }

    public BigDecimal getLogid() {
        return logid;
    }

    public void setLogid(BigDecimal logid) {
        this.logid = logid;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Log_CLXAuditTrail_MasterId other = (Log_CLXAuditTrail_MasterId) obj;
        if (!Objects.equals(this.logentryid, other.logentryid)) {
            return false;
        }
        if (!Objects.equals(this.logid, other.logid)) {
            return false;
        }
        return true;
    }

    
   }
