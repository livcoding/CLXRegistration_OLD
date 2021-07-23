/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Report;

/** 
 *
 * @author ajay2.kumar
 */
public class DynamicColumnDetail {

    private String columnTitle;
    private int columnWidth;
    private DataType dataType;
    private YesNo printSum;
    private Alignment textAlignment;
    private Alignment textVericalAlignment;
    private String columnName;

    public DynamicColumnDetail() {
    }

    public DynamicColumnDetail(String columnTitle, int columnWidth, Alignment textAlignment, Alignment textVericalAlignment, String columnName) {
        this.columnTitle = columnTitle;
        this.columnWidth = columnWidth;
        this.textAlignment = textAlignment;
        this.textVericalAlignment = textVericalAlignment;
        this.columnName = columnName;
    }

    public DynamicColumnDetail(String columnTitle, int columnWidth, DataType dataType, YesNo printSum, Alignment textAlignment, Alignment textVerticalAlignment, String columnName) {
        this.columnTitle = columnTitle;
        this.columnWidth = columnWidth;
        this.dataType = dataType;
        this.printSum = printSum;
        this.textAlignment = textAlignment;
        this.textVericalAlignment = textVerticalAlignment;
        this.columnName = columnName;
    }

    public DynamicColumnDetail(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public DynamicColumnDetail(String columnTitle, Alignment textAlignment) {
        this.columnTitle = columnTitle;
        this.textAlignment = textAlignment;
    }

    public DynamicColumnDetail(String columnTitle, int columnWidth) {
        this.columnTitle = columnTitle;
        this.columnWidth = columnWidth;
    }

    public DynamicColumnDetail(String columnTitle, int columnWidth, Alignment textAlignment) {
        this.columnTitle = columnTitle;
        this.columnWidth = columnWidth;
        this.textAlignment = textAlignment;
    }

    public DynamicColumnDetail(String columnTitle, int columnWidth, Alignment textAlignment, Alignment textVerticalAlignment) {
        this.columnTitle = columnTitle;
        this.columnWidth = columnWidth;
        this.textAlignment = textAlignment;
        this.textVericalAlignment = textVerticalAlignment;
    }

    public DynamicColumnDetail(String columnTitle, int columnWidth, Alignment textAlignment, String columnName) {
        this.columnTitle = columnTitle;
        this.columnWidth = columnWidth;
        this.textAlignment = textAlignment;
        this.columnName = columnName;
    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public int getColumnWidth() {
        return columnWidth;
    }

    public void setColumnWidth(int columnWidth) {
        this.columnWidth = columnWidth;
    }

    public YesNo getPrintSum() {
        return printSum;
    }

    public void setPrintSum(YesNo printSum) {
        this.printSum = printSum;
    }

    public Alignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(Alignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Alignment getTextVericalAlignment() {
        return textVericalAlignment;
    }

    public void setTextVericalAlignment(Alignment textVericalAlignment) {
        this.textVericalAlignment = textVericalAlignment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
