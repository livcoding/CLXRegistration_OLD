/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

/**
 *
 * @author ajay2.kumar
 */
public class PageLayout {

    private static final long serialVersionUID = 1L;
    public static final PageLayout AUTO = new PageLayout((byte) 1); 
    public static final PageLayout CUSTOM_SIZE  = new PageLayout((byte) 11);
    public static final PageLayout PAGE_A4_AUTO = new PageLayout((byte) 2);
    public static final PageLayout PAGE_A4_LANDSCAPE = new PageLayout((byte) 8);
    public static final PageLayout PAGE_A4_PORTRAIT = new PageLayout((byte) 3);
    public static final PageLayout PAGE_Legal_AUTO = new PageLayout((byte) 4);
    public static final PageLayout PAGE_Legal_LANDSCAPE = new PageLayout((byte) 9);
    public static final PageLayout PAGE_LEGAL_PORTRAIT = new PageLayout((byte) 5);
    public static final PageLayout PAGE_LETTER_AUTO = new PageLayout((byte) 6);
    public static final PageLayout PAGE_LETTER_LANDSCAPE = new PageLayout((byte) 10);
    public static final PageLayout PAGE_LETTER_PORTRAIT = new PageLayout((byte) 7);
    

     private final byte value;
    public byte getValue() {

                 return value;
	         }

          private PageLayout(byte value){

                 this.value = value;

         }
}
