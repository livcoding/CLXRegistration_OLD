/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

/**
 *
 * @author ajay2.kumar
 */
public class YesNo {

     private static final long serialVersionUID = 1L;
public static final YesNo YES =new YesNo((byte)1);
public static final YesNo NO = new YesNo((byte)2);
 private final byte value;
     public byte getValue() {

                 return value;
	         }

          private YesNo(byte value){

                 this.value = value;

         }

}
