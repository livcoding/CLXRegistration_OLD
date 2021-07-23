/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shimona.Khandelwal
 */
public class ReportData implements Serializable{
    private String reporttitle;
     private String pageheader;
    private List<FieldAttributes> fieldAttributesList;

    public ReportData(String reporttitle,String pageheader) {
        this.reporttitle = reporttitle;
        this.pageheader = pageheader;
    }

    
    public ReportData(String reporttitle,String pageheader,  List<FieldAttributes> fieldAttributesList) {
        this.reporttitle = reporttitle;
         this.pageheader = pageheader;
        this.fieldAttributesList = fieldAttributesList;
    }

    public List<FieldAttributes> getFieldAttributesList() {
        if(fieldAttributesList ==null){
            fieldAttributesList = new ArrayList<FieldAttributes>();
        }
        return fieldAttributesList;
    }

    public void setFieldAttributesList(List<FieldAttributes> fieldAttributesList) {
        this.fieldAttributesList = fieldAttributesList;
    }

   
    public String getReporttitle() {
        return reporttitle;
    }

    public void setReporttitle(String reporttitle) {
        this.reporttitle = reporttitle;
    }

    public String getPageheader() {
        return pageheader;
    }

    public void setPageheader(String pageheader) {
        this.pageheader = pageheader;
    }

    
    

}
