/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

import ar.com.fdvs.dj.domain.constants.Page;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author ajay2.kumar
 */
public class DynamicReportProperty {

    private  YesNo printSrNo;
    private  PageLayout pageLayout;
    private  String reportTitle;
    private  String path;
    private  String reportName;
    private  HashMap parameters;
    private  List<LinkedHashMap> data;
    private  String exportTo;
    private  HttpServletResponse response;
    private String listOfParameters; // list of parameters
    private List<DynamicColumnDetail> columnsList;
    private List<DynamicGroupDetail> groupDetailsList;
    private boolean columnWithAutoTrue;
    private String summary;
    private Alignment summaryAlignment;
    private Page page;
    private int  pageWidth;
    private int  pageHeight;
    private boolean LastRowAsTotal;
    private boolean useFullPageWidth;
    private boolean showDataIntoGridLines;
    private boolean printBackgroundOnOddRows;
    private Alignment srnoHorizontalAlign;
    private Alignment srnoVerticalAlign;
    public String getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(String listOfParameters) {
        this.listOfParameters = listOfParameters;
    }

    public List getData() {
        return data;
    }

    public void setData(List<LinkedHashMap> data) {
        this.data = data;
    }

    public String getExportTo() {
        return exportTo;
    }

    public void setExportTo(String exportTo) {
        this.exportTo = exportTo;
    }

    public HashMap getParameters() {
        return parameters;
    }

    public void setParameters(HashMap parameters) {
        this.parameters = parameters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

      public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    public PageLayout getPageLayout() {
        return pageLayout;
    }

    public void setPageLayout(PageLayout pageLayout) {
        this.pageLayout = pageLayout;
    }

    public YesNo getPrintSrNo() {
        return printSrNo;
    }

    public void setPrintSrNo(YesNo printSrNo) {
        this.printSrNo = printSrNo;
    }

    public List<DynamicColumnDetail> getColumnsList() {
        return columnsList;
    }

    public void setColumnsList(List<DynamicColumnDetail> columnsList) {
        this.columnsList = columnsList;
    }

    public List<DynamicGroupDetail> getGroupDetailsList() {
        return groupDetailsList;
    }

    public void setGroupDetailsList(List<DynamicGroupDetail> groupDetailsList) {
        this.groupDetailsList = groupDetailsList;
    }

    public boolean getColumnWithAutoTrue() {
        return columnWithAutoTrue;
    }

    public void setColumnWithAutoTrue() {
        this.columnWithAutoTrue = true;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Alignment getSummaryAlignment() {
        return summaryAlignment;
    }

    public void setSummaryAlignment(Alignment summaryAlignment) {
        this.summaryAlignment = summaryAlignment;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public boolean isLastRowAsTotal() {
        return LastRowAsTotal;
    }

    public void setLastRowAsTotal(boolean LastRowAsTotal) {
        this.LastRowAsTotal = LastRowAsTotal;
    }

    public boolean isColumnWithAutoTrue() {
        return columnWithAutoTrue;
    }

    public void setColumnWithAutoTrue(boolean columnWithAutoTrue) {
        this.columnWithAutoTrue = columnWithAutoTrue;
    }

    public boolean isUseFullPageWidth() {
        return useFullPageWidth;
    }

    public void setUseFullPageWidth(boolean useFullPageWidth) {
        this.useFullPageWidth = useFullPageWidth;
    }

    public boolean isShowDataIntoGridLines() {
        return showDataIntoGridLines;
    }

    public void setShowDataIntoGridLines(boolean showDataIntoGridLines) {
        this.showDataIntoGridLines = showDataIntoGridLines;
    }

    public boolean isPrintBackgroundOnOddRows() {
        return printBackgroundOnOddRows;
    }

    public void setPrintBackgroundOnOddRows(boolean printBackgroundOnOddRows) {
        this.printBackgroundOnOddRows = printBackgroundOnOddRows;
    }

    public Alignment getSrnoHorizontalAlign() {
        return srnoHorizontalAlign;
    }

    public void setSrnoHorizontalAlign(Alignment srnoHorizontalAlign) {
        this.srnoHorizontalAlign = srnoHorizontalAlign;
    }

    public Alignment getSrnoVerticalAlign() {
        return srnoVerticalAlign;
    }

    public void setSrnoVerticalAlign(Alignment srnoVerticalAlign) {
        this.srnoVerticalAlign = srnoVerticalAlign;
    }

}
