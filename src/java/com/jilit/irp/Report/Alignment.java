/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;
 
/**
 *
 * @author ajay2.kumar
 */
public class Alignment {
private static final long serialVersionUID = 1L;
    public static final Alignment RIGHT= new Alignment((byte)1);
    public static final Alignment LEFT=new Alignment((byte)2);
    public static final Alignment CENTER=new Alignment((byte)3);
    public static final Alignment JUSTIFY=new Alignment((byte)4);
    public static final Alignment BOTTOM=new Alignment((byte)5);
    public static final Alignment MIDDLE=new Alignment((byte)6);
    public static final Alignment TOP=new Alignment((byte)7);
 
    private final byte value;
    public byte getValue() {

                 return value;
	         }

          private Alignment(byte value){

                 this.value = value;

         }

}
