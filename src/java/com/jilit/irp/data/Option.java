/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ankit.kumar
 */
public class Option implements Serializable{
    private int index;
    private String label;
    private String data;
    private String type;
    private String checked="0";
    private List children;
    public Option(String label, String data) {
        this.label = label;
        this.data = data;
    }

    public Option(int index, String label, String data) {
        this.index = index;
        this.label = label;
        this.data = data;
    }

    public Option(String label, String data, List list) {
        this.label = label;
        this.data = data;
        this.children = list;
    }

     public Option(String label, String data, String type) {
        this.label = label;
        this.data = data;
        this.type = type;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    
   


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

     public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Option other = (Option) obj;
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
            return false;
        }
        if ((this.data == null) ? (other.data != null) : !this.data.equals(other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.label != null ? this.label.hashCode() : 0);
        hash = 61 * hash + (this.data != null ? this.data.hashCode() : 0);
        return hash;
    }

}
