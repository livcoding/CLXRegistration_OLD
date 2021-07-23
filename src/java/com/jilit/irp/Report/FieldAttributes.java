/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

import java.io.Serializable;

/**
 *
 * @author Shimona.Khandelwal
 */
public class FieldAttributes implements Serializable{
    
    private String fieldname;
    private String classtype;
    private String width;
    private String header;
    private String alignment;
    private String expression;
    

    public FieldAttributes() {
    }

    public FieldAttributes(String fieldname, String classtype, String width, String header, String alignment, String expression) {
        this.fieldname = fieldname;
        this.classtype = classtype;
        this.width = width;
        this.header = header;
        this.alignment = alignment;
        this.expression = expression;
    }



    public FieldAttributes(String fieldname, String width, String header, String alignment, String expression) {
        this.fieldname = fieldname;
        this.width = width;
        this.header = header;
        this.alignment = alignment;
        this.expression = expression;
        this.classtype = "java.lang.String";
    }

    public FieldAttributes(String fieldname, String width, String header, String alignment) {
        this.fieldname = fieldname;
        this.width = width;
        this.header = header;
        this.alignment = alignment;
        this.classtype = "java.lang.String";
        this.expression = "$F{"+fieldname+"}";
    }

      public FieldAttributes(String fieldname, String width, String header) {
        this.fieldname = fieldname;
        this.width = width;
        this.header = header;
        this.alignment = "Left";
        this.classtype = "java.lang.String";
        this.expression = "$F{"+fieldname+"}";
    }
      




    

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getClasstype() {
        return classtype;
    }

    public void setClasstype(String classtype) {
        this.classtype = classtype;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
   

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

     

 
}
