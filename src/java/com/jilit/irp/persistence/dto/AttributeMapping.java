package com.jilit.irp.persistence.dto;

/**
 *
 * @author jaswinder.singh
 */
public class AttributeMapping implements java.io.Serializable {

    private int uniqid;
    private String groupname;
    private String attributename;
    private String mappingtable;
    private String attributedescription;
    private String editable;

    public AttributeMapping() {
    }

    public AttributeMapping(AttributeMapping dto) {
        this.uniqid = dto.uniqid;
        this.groupname = dto.groupname;
        this.attributename = dto.attributename;
        this.mappingtable = dto.mappingtable;
        this.attributedescription = dto.attributedescription;
        this.editable = dto.editable;
    }

    public String getAttributedescription() {
        return attributedescription;
    }

    public void setAttributedescription(String attributedescription) {
        this.attributedescription = attributedescription;
    }

    public String getAttributename() {
        return attributename;
    }

    public void setAttributename(String attributename) {
        this.attributename = attributename;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getMappingtable() {
        return mappingtable;
    }

    public void setMappingtable(String mappingtable) {
        this.mappingtable = mappingtable;
    }

    public int getUniqid() {
        return uniqid;
    }

    public void setUniqid(int uniqid) {
        this.uniqid = uniqid;
    }
}


