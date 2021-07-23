/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

/** 
 *
 * @author ajay2.kumar
 */
public class DataType {
    public static final DataType BIGDECIMAL = new DataType((byte)1);
    public static final DataType INTEGER = new DataType((byte)2);
    public static final DataType LONG = new DataType((byte)3);
    public static final DataType FLOAT = new DataType((byte)4);
    public static final DataType DOUBLE = new DataType((byte)5);

    private final byte value;
    public byte getValue() {

                 return value;
	         }
          private DataType(byte value){
                 this.value = value;
         }
}
