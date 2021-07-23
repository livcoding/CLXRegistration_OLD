/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.persistence.dao;

import java.util.List;

/**
 *
 * @author Malkeet Singh
 */
public interface CourseCreditWiseFeeIDAO extends IDAO {

    public List creditSRno(String instituteid);

    public List getGridData(String instituteid, String stytypeid);

    public List validData(String instituteid, String stytypeid, double creditfrom, double creditto, short slno, String status);

    public String getCreditWiseBackPaperFeeAmount(String instituteid, String stytypeid, double creditfrom, double creditto);

}
