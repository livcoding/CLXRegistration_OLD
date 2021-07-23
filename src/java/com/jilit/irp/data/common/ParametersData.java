/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.data.common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author singh.jaswinder
 */
public class ParametersData {
    String parameterId;
    String parameter;
    String parameterValue;
    String entryfield;
    String datatype;
    String parameterValueDescription;
    String width;
    String minRange;
    String maxRange;

    public String getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(String maxRange) {
        this.maxRange = maxRange;
    }

    public String getMinRange() {
        return minRange;
    }

    public void setMinRange(String minRange) {
        this.minRange = minRange;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getParameterValueDescription() {
        return parameterValueDescription;
    }

    public void setParameterValueDescription(String parameterValueDescription) {
        this.parameterValueDescription = parameterValueDescription;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
   
    public String getEntryfield() {
        return entryfield;
    }

    public void setEntryfield(String entryfield) {
        this.entryfield = entryfield;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameterId() {
        return parameterId;
    }

    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }      

}
