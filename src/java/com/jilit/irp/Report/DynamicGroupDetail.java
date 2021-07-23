/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

/** 
 *
 * @author ajay2.kumar
 */
public class DynamicGroupDetail {

    private String groupTextLabel;// code show to user like Department :
    private String groupTextProperty;    // display to user
    private String groupSubTextProperty;  //display Text shwing into brases
    private String groupByProperty; // it is define group by departmentid etc
    private Boolean startWithNewPage=false;
//    private Boolean startWithNewPage;

public DynamicGroupDetail(){

}
    public DynamicGroupDetail(String groupByProperty,String groupTextLabel,String groupTextProperty, String groupSubTextProperty)
    {
    this.groupTextLabel=groupTextLabel;
    this.groupTextProperty=groupTextProperty;
    this.groupSubTextProperty=groupSubTextProperty;
    this.groupByProperty=groupByProperty;
  //  this.startWithNewPage=startWithNewPage;
    }
    public DynamicGroupDetail(String groupByProperty,String groupTextLabel,String groupTextProperty)
    {
    this.groupTextLabel=groupTextLabel;
    this.groupTextProperty=groupTextProperty;
    this.groupByProperty=groupByProperty;
 //   this.startWithNewPage=startWithNewPage;
    }
     public DynamicGroupDetail(String groupByProperty,String groupTextLabel,String groupTextProperty,boolean startWithNewPage)
    {
    this.groupTextLabel=groupTextLabel;
    this.groupTextProperty=groupTextProperty;
    this.groupByProperty=groupByProperty;
    this.startWithNewPage=startWithNewPage;
    }
    public String getGroupByProperty() {
        return groupByProperty;
    }

    public void setGroupByProperty(String groupByProperty) {
        this.groupByProperty = groupByProperty;
    }

    public String getGroupSubTextProperty() {
        return groupSubTextProperty;
    }

    public void setGroupSubTextProperty(String groupSubTextProperty) {
        this.groupSubTextProperty = groupSubTextProperty;
    }

    public String getGroupTextLabel() {
        return groupTextLabel;
    }

    public void setGroupTextLabel(String groupTextLabel) {
        this.groupTextLabel = groupTextLabel;
    }

    public String getGroupTextProperty() {
        return groupTextProperty;
    }

    public void setGroupTextProperty(String groupTextProperty) {
        this.groupTextProperty = groupTextProperty;
    }

    public Boolean getStartWithNewPage() {
        return startWithNewPage;
    }

    public void setStartWithNewPage(Boolean startWithNewPage) {
        this.startWithNewPage = startWithNewPage;
    }



}
