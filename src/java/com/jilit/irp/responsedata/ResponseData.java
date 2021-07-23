/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.responsedata;

import org.springframework.ui.ModelMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sourabh.kanojiya
 */
public class ResponseData {

    private Object dto;
    private List<?> list;
    private Boolean booleanval;
    private String datavalue;
    private ModelMap addldata;
    private String message;
    private String status;
    private Map<?,?> map;

    public ResponseData() {
    }

    public ResponseData(Object dto, String message, String status) {
        this.dto = dto;
        this.message = message;
        this.status = status;
    }

    public ResponseData(List<?> list, String message, String status) {
        this.list = list;
        this.message = message;
        this.status = status;
    }

    public ResponseData(Boolean booleanval, String message, String status) {
        this.booleanval = booleanval;
        this.message = message;
        this.status = status;
    }

    public ResponseData(String datavalue, String message, String status) {
        this.datavalue = datavalue;
        this.message = message;
        this.status = status;
    }

    public ResponseData(ModelMap addldata, String message, String status) {
        this.addldata = addldata;
        this.message = message;
        this.status = status;
    }

    public ResponseData(String message, String status, Map<?, ?> map) {
        this.message = message;
        this.status = status;
        this.map = map;
    }

    public ModelMap getAddldata() {
        return addldata;
    }

    public void setAddldata(ModelMap addldata) {
        this.addldata = addldata;
    }

    public Boolean getBooleanval() {
        return booleanval;
    }

    public void setBooleanval(Boolean booleanval) {
        this.booleanval = booleanval;
    }

    public Object getDto() {
        return dto;
    }

    public void setDto(Object dto) {
        this.dto = dto;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(String datavalue) {
        this.datavalue = datavalue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<?, ?> getMap() {
        return map;
    }

    public void setMap(Map<?, ?> map) {
        this.map = map;
    }
    
}
