package com.jilit.irp.iservice;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author deepak.gupta
 */
public interface OfferSubjectTaggingIService {

    public List getOfferSubjectTaggingListData(HttpServletRequest request);

    public void addOfferSubjectTagging(ModelMap model);

    public void getRegistrationList(Model model);

    public List addNewOfferSubjectTagging(HttpServletRequest request);

    public List getOldSubjectCode(HttpServletRequest request);

    public List getSubjectComponent(HttpServletRequest request);

    public List getFacultyList(HttpServletRequest request);

    public List getAllSubjectComponent();

    public ModelMap getAllOfferSubjectTaggingData(ModelMap mm, HttpServletRequest request);

    public List updateOfferSubjectTagging(HttpServletRequest request);

    public List deleteOfferSubjectTagging(HttpServletRequest request);

}
