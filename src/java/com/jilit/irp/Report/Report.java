/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;

/**
 *
 * @author jaswinder.singh
 */
public class Report {

    public static String createBasicReport(ReportData reportData) {
        String report = null;
        report = "<?xml version='1.0' encoding='UTF-8'?>\n";
        report = report + " <!DOCTYPE jasperReport PUBLIC '-//JasperReports//DTD Report Design//EN'\n";
        report = report + " 'http://jasperreports.sourceforge.net/dtds/jasperreport.dtd'>\n";

        report = report + " <jasperReport name='BasicReport' orientation='Landscape' pageWidth='1168' pageHeight='1100' columnWidth='1090'\n";
        report = report + " leftMargin='40' rightMargin='38' topMargin='20' bottomMargin='20'>\n";

        report = report + " <reportFont name='Verdana' isDefault='true' fontName='Arial' size='10'\n";
        report = report + " isBold='false' isItalic='false' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica' pdfEncoding='Cp1252' isPdfEmbedded='false' />\n";
        report = report + " <reportFont name='Arial_Bold' isDefault='false' fontName='Arial' size='12'\n";
        report = report + " isBold='true' isItalic='false' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica-Bold' pdfEncoding='Cp1252' isPdfEmbedded='false'/>\n";
        report = report + " <reportFont name='Arial_Italic' isDefault='false' fontName='Arial' size='12'\n";
        report = report + " isBold='false' isItalic='true' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica-Oblique' pdfEncoding='Cp1252' isPdfEmbedded='false' />\n";
        List<FieldAttributes> fieldlist = reportData.getFieldAttributesList();
        for (int i = 0; i < fieldlist.size(); i++) {
            FieldAttributes field = fieldlist.get(i);
            report = report + " <field name='" + field.getFieldname() + "' class='" + field.getClasstype() + "'></field>\n";
        }
        // System.err.println("<<<<  "+report);
        report = report + " <title>\n";
        report = report + " <band height='45'  isSplitAllowed='true'>\n";
        report = report + " <staticText>\n";
        report = report + " <reportElement x='480' y='5' width='394' height='29' key='staticText-1'/>\n";
        report = report + " <box></box>\n";
        report = report + " <textElement>\n";
        report = report + " <font fontName='Lucida Sans' size='14' isBold='true' pdfEncoding ='Cp1254'/>\n";
        report = report + " </textElement>\n";
        report = report + " <text><![CDATA[" + reportData.getReporttitle() + "]]></text>\n";
        report = report + " </staticText>\n";
        report = report + " </band>\n";
        report = report + " </title>\n";

        report = report + " <pageHeader>\n";
        /*report = report + " <band height='20'>\n";
        report = report + " <staticText>\n";
        report = report + " <reportElement x='0' y='5' width='520' height='15'  key='staticText-2'/>\n";
        // report = report + " forecolor='#ffffff' backcolor='#666666'/>\n";
        report = report + " <textElement textAlignment='Left'>\n";
        report = report + " <font/>\n";
        report = report + " </textElement>\n";
        report = report + " <text>\n";
        report = report + " <![CDATA[" + map.get("report.pageHeader") + "]]>\n";
        report = report + " </text>\n";
        report = report + " </staticText>\n";
        report = report + " </band>\n";*/
        report = report + " <band height='45'  isSplitAllowed='true'>\n";
        report = report + " <staticText>\n";
        //report = report + " <reportElement mode='Transparent' x='702' y='34' width='63' height='13' forecolor='#000000' backcolor='#FFFFFF' key='textField-176'/>\n";
        report = report + " <reportElement x='450' y='5' width='394' height='29' forecolor='#000000' backcolor='#FFFFFF' key='staticText-1'/>\n";
        report = report + " <box topBorder='None' topBorderColor='#000000' leftBorder='None' leftBorderColor='#000000' rightBorder='None' rightBorderColor='#FFFFFF' bottomBorder='None' bottomBorderColor='#000000'/>\n";

        /* report = report + "<topPen lineWidth='0.0' lineStyle='Solid' lineColor='#FF0000'/>\n";
        report = report + "<leftPen lineWidth='0.0' lineStyle='Solid' lineColor='#FF0000'/>\n";
        report = report + "<bottomPen lineWidth='0.0' lineColor='#000000'/>\n";
        report = report + "<rightPen lineWidth='0.0' lineStyle='Solid' lineColor='#FF0000'/>\n";
        report = report + " </box>\n";*/

        report = report + " <textElement>\n";
        report = report + " <font fontName='Lucida Sans' size='14' isBold='true' pdfEncoding ='Cp1254'/>\n";
        report = report + " </textElement>\n";
        report = report + " <text><![CDATA[" + reportData.getPageheader() + "]]></text>\n";
        report = report + " </staticText>\n";
        report = report + " </band>\n";
        report = report + " </pageHeader>\n";
//        report = report + " <columnHeader>\n";
//        report = report + " <band height='20'>\n";
//        int x = 0;
//        for (String col : columnList) {
//            report = report + " <staticText>\n";
//           // report = report + " <reportElement mode='Opaque' x='" + x + "' y='5' width='" + map.get(col + ".width") + "' height='15'/>\n";
//            report = report + " <reportElement x='" + x + "' y='5' width='" + map.get(col + ".width") + "' height='15'/>\n";
//         //   report = report + " forecolor='#ffffff' backcolor='#bbbbbb' />\n";
//            report = report + " <textElement textAlignment='Left'>\n";
//            report = report + " <font reportFont='Arial_Bold' />\n";
//           report = report + " </textElement>\n";
//            report = report + " <text>\n";
//            report = report + " <![CDATA[" + map.get(col + ".header") + "]]>\n";
//            report = report + " </text>\n";
//            report = report + " </staticText>\n";
//            x = x + Integer.parseInt((String) map.get(col + ".width"));
//        }
//        report = report + " </band>\n";
//        report = report + " </columnHeader>\n";




        report = report + " <columnHeader>\n";
        report = report + " <band height='15'  isSplitAllowed='true'>\n";
        int x = 0;
        for (FieldAttributes fld : fieldlist) {
            report = report + " <staticText>\n";
            report = report + " <reportElement x='" + x + "' y='2' width='" + fld.getWidth() + "' height='13' key='staticText-" + fld.getFieldname() + "'/>\n";
            report = report + " <box bottomBorder='Thin' topBorder='Thin'></box>\n";
            report = report + " <textElement>\n";
            report = report + " <font fontName='Lucida Sans' pdfFontName='Helvetica-Bold' size='10' isBold='true' pdfEncoding ='Cp1254'/>\n";
            report = report + " </textElement>\n";
            report = report + " <text><![CDATA[" + fld.getHeader() + "]]></text>\n";
            report = report + " </staticText>\n";
            x = x + Integer.parseInt(fld.getWidth());
        }
        report = report + " </band>\n";
        report = report + " </columnHeader>\n";



        report = report + " <detail>\n";
        report = report + " <band height='15'>\n";

        x = 0;
        for (FieldAttributes fld : fieldlist) {
            // System.out.println("//////  " + col + "/////////");
            report = report + " <textField>\n";
            report = report + " <reportElement x='" + x + "' y='2' width='" + fld.getWidth() + "' height='13' />\n";
            //report = report + "<box bottomBorder='Thin'></box>\n";
            report = report + "<box></box>\n";
            report = report + " <textElement textAlignment='" + fld.getAlignment() + "'/>\n";
            report = report + " <textFieldExpression class='" + fld.getClasstype() + "'>\n";
            report = report + " <![CDATA[" + fld.getExpression() + "]]>\n";

            report = report + " </textFieldExpression>\n";
            report = report + " </textField>\n";
            x = x + Integer.parseInt(fld.getWidth());
        //  System.out.println((String) map.get(col + ".width"));
        }

        report = report + " </band>\n";
        report = report + " </detail>\n";

        report = report + " <pageFooter>\n";

        report = report + " <band height='40'>\n";
        report = report + " <textField>\n";
        report = report + " <reportElement x  = '200' y  = '20' width  = '85' height  = '15'/>\n";
        report = report + " <textElement textAlignment  = 'Right'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[ String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " <textField evaluationTime='Report'>\n";
        report = report + " <reportElement x='285' y='20' width='75' height='15' />\n";
        report = report + " <textElement textAlignment='Left'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[\" of \" + String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " </band>\n";
        report = report + " </pageFooter>\n";
        report = report + " </jasperReport>\n";
        System.out.println("//////  \n" + report + "\n/////////");
        return report;
    }

    public static String createBasicReport1(ReportData reportData) {
        String report = null;
        report = "<?xml version='1.0' encoding='UTF-8'?>\n";
        report = report + " <!DOCTYPE jasperReport PUBLIC '-//JasperReports//DTD Report Design//EN'\n";
        report = report + " 'http://jasperreports.sourceforge.net/dtds/jasperreport.dtd'>\n";

        report = report + " <jasperReport name='MasterReport' orientation='Landscape' pageWidth='2168' pageHeight='1100' columnWidth='2090'\n";
        report = report + " leftMargin='40' rightMargin='38' topMargin='20' bottomMargin='20'>\n";

        report = report + " <reportFont name='Verdana' isDefault='true' fontName='Arial' size='10'\n";
        report = report + " isBold='false' isItalic='false' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica' pdfEncoding='Cp1252' isPdfEmbedded='false' />\n";
        report = report + " <reportFont name='Arial_Bold' isDefault='false' fontName='Arial' size='12'\n";
        report = report + " isBold='true' isItalic='false' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica-Bold' pdfEncoding='Cp1252' isPdfEmbedded='false'/>\n";
        report = report + " <reportFont name='Arial_Italic' isDefault='false' fontName='Arial' size='12'\n";
        report = report + " isBold='false' isItalic='true' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica-Oblique' pdfEncoding='Cp1252' isPdfEmbedded='false' />\n";

        report = report + "  <style name='Arial_Normal' isDefault='true' fontName='Arial' fontSize='12' isBold='false' isItalic='false' isUnderline='false' isStrikeThrough='false' pdfFontName='Helvetica' pdfEncoding='Cp1252' isPdfEmbedded='false'/>";
        report = report + " <style name='Arial_Bold' isDefault='false' fontName='Arial' fontSize='12' isBold='true' isItalic='false' isUnderline='false' isStrikeThrough='false' pdfFontName='Helvetica-Bold' pdfEncoding='Cp1252' isPdfEmbedded='false'/>";
        report = report + " <style name='Arial_Italic' isDefault='false' fontName='Arial' fontSize='12' isBold='false' isItalic='true' isUnderline='false' isStrikeThrough='false' pdfFontName='Helvetica-Oblique' pdfEncoding='Cp1252' isPdfEmbedded='false'/>";
        List<FieldAttributes> fieldlist = reportData.getFieldAttributesList();
        for (int i = 0; i < fieldlist.size(); i++) {
            FieldAttributes field = fieldlist.get(i);
            report = report + " <field name='" + field.getFieldname() + "' class='" + field.getClasstype() + "'></field>\n";
        }

        report = report + "<group name='Counselling Id' isStartNewColumn='true' isReprintHeaderOnEachPage='true' minHeightToStartNewPage='200'>  ";
        report = report + "<groupExpression><![CDATA[" + fieldlist.get(0).getExpression() + "]]></groupExpression>";
        report = report + "<groupHeader>";
        report = report + "<band height='50'>";

        //        int x = 5;

        // for (FieldAttributes fld : fieldlist) {
        report = report + "<staticText>";
        report = report + "<reportElement x='5' y='5' width='100' height='15'  style='Arial_Bold'/>";
        report = report + " <textElement>\n";
        report = report + " <font isUnderline='true'/>";
        report = report + " </textElement>";
        report = report + " <text><![CDATA[Counselling Id:]]></text>";
        report = report + " </staticText>\n";

        report = report + "<textField>";
        report = report + "<reportElement x='110' y='5' width='100' height='15' isPrintWhenDetailOverflows='true' style='Arial_Bold'/>";
        report = report + " <textFieldExpression class='" + fieldlist.get(0).getClasstype() + "'>\n";
        report = report + " <![CDATA[" + fieldlist.get(0).getExpression() + "]]>\n";

        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        //      x = x + Integer.parseInt(fld.getWidth());
        //  System.out.println((String) map.get(col + ".width"));
        // }
        report = report + " <staticText>";
        report = report + " <reportElement isPrintRepeatedValues='false' x='110' y='5' width='100' height='15' isPrintWhenDetailOverflows='true' style='Arial_Bold'/>";
        report = report + " <text><![CDATA[(continued)]]></text>";
        report = report + " </staticText>";
        report = report + " <line>";
        report = report + " <reportElement x='0' y='20' width='515' height='1' isPrintWhenDetailOverflows='true'/>";
        report = report + " <graphicElement/>";
        report = report + " </line>";
        report = report + " </band>\n";
        report = report + "</groupHeader>";
        report = report + "<groupFooter>";
        report = report + "<band height='5'>";
        report = report + "</band>";
        report = report + "</groupFooter>";
        report = report + "</group>";
        report = report + "<group name='Program Type'  isReprintHeaderOnEachPage='true' >  ";
        report = report + "<groupExpression><![CDATA[" + fieldlist.get(1).getExpression() + "]]></groupExpression>";
        report = report + "<groupHeader>";
        report = report + "<band height='25'>";

        //        int x = 5;

        // for (FieldAttributes fld : fieldlist) {
        report = report + "<staticText>";
        report = report + "<reportElement backcolor='#ffcc99' x='15' y='5' width='70' height='15'  style='Arial_Bold'/>";

        report = report + " <text><![CDATA[Program Type:]]></text>";
        report = report + " </staticText>\n";

        report = report + "<textField>";
        report = report + "<reportElement x='110' y='5' width='70' height='15' isPrintWhenDetailOverflows='true' style='Arial_Bold'/>";
        report = report + " <textFieldExpression class='" + fieldlist.get(1).getClasstype() + "'>\n";
        report = report + " <![CDATA[" + fieldlist.get(1).getExpression() + "]]>\n";

        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        //      x = x + Integer.parseInt(fld.getWidth());
        //  System.out.println((String) map.get(col + ".width"));
        // }
        report = report + " <staticText>";
        report = report + " <reportElement isPrintRepeatedValues='false' x='110' y='5' width='70' height='15' isPrintWhenDetailOverflows='true' style='Arial_Bold'/>";
        report = report + " <text><![CDATA[(continued)]]></text>";
        report = report + " </staticText>";
        report = report + " <line>";
        report = report + " <reportElement x='0' y='20' width='270' height='1' isPrintWhenDetailOverflows='true'/>";
        report = report + " <graphicElement/>";
        report = report + " </line>";
        report = report + " </band>\n";
        report = report + "</groupHeader>";
        report = report + "<groupFooter>";
        report = report + "<band height='5'>";
        report = report + "</band>";
        report = report + "</groupFooter>";
        report = report + "</group>";


        // System.err.println("<<<<  "+report);
        report = report + " <title>\n";
        report = report + " <band height='70'  isSplitAllowed='true'>\n";
        report = report + " <line><reportElement x='0' y='0' width='515' height='1'/>\n";
        report = report + " <graphicElement/>";
        report = report + " </line>";

        report = report + " <staticText>\n";
        report = report + " <reportElement x='0' y='0' width='515' height='30' key='staticText-1'/>\n";
        report = report + " <textElement>\n";
        report = report + " <font size='22'/>\n";
        report = report + " </textElement>\n";
        report = report + " <text><![CDATA[" + reportData.getReporttitle() + "]]></text>\n";
        report = report + " </staticText>\n";
        report = report + " </band>\n";
        report = report + " </title>\n";

        report = report + " <pageHeader>\n";
        report = report + " <band height='21'>";
        report = report + " <rectangle>";
        report = report + " <reportElement x='0' y='5' width='515' height='15' backcolor='#333333'/>";
        report = report + " <graphicElement pen='None'/>";
        report = report + " </rectangle>";
        report = report + " <staticText>";
        report = report + " <reportElement mode='Opaque' x='0' y='5' width='515' height='15' forecolor='#ffffff' backcolor='#333333' style='Arial_Bold'/>";
        report = report + " <text><![CDATA[" + reportData.getPageheader() + "]]></text>";
        report = report + " </staticText>";
        report = report + " <line>";
        report = report + " <reportElement x='0' y='20' width='515' height='1'/>";
        report = report + " <graphicElement/>";
        report = report + " </line>";
        report = report + "</band>";
        report = report + "</pageHeader>";


        report = report + " <columnHeader>\n";
        report = report + " <band height='21'  isSplitAllowed='true'>\n";
        report = report + " <rectangle>";
        report = report + " <reportElement x='0' y='5' width='515' height='15' backcolor='#ffcc99'/>";
        report = report + " <graphicElement pen='None'/>";
        report = report + " </rectangle>";
        int x = 0;
        for (int i = 2; i < fieldlist.size(); i++) {
            FieldAttributes fld = fieldlist.get(i);
            report = report + " <staticText>\n";
            report = report + " <reportElement x='" + x + "' y='2' width='" + fld.getWidth() + "' height='13' key='staticText-" + fld.getFieldname() + "'/>\n";
            report = report + " <box bottomBorder='Thin'></box>\n";
            report = report + " <textElement>\n";
            report = report + " <font/>\n";
            report = report + " </textElement>\n";
            report = report + " <text><![CDATA[" + fld.getHeader() + "]]></text>\n";
            report = report + " </staticText>\n";
            x = x + Integer.parseInt(fld.getWidth());
        }
        report = report + " </band>\n";
        report = report + " </columnHeader>\n";



        report = report + " <detail>\n";
        report = report + " <band height='15'>\n";

        x = 0;
        for (int i = 2; i < fieldlist.size(); i++) {
            FieldAttributes fld = fieldlist.get(i);


            // System.out.println("//////  " + col + "/////////");
            report = report + " <textField>\n";
            report = report + " <reportElement x='" + x + "' y='2' width='" + fld.getWidth() + "' height='13' />\n";

            report = report + " <textElement textAlignment='" + fld.getAlignment() + "'/>\n";
            report = report + " <textFieldExpression class='" + fld.getClasstype() + "'>\n";
            report = report + " <![CDATA[" + fld.getExpression() + "]]>\n";

            report = report + " </textFieldExpression>\n";
            report = report + " </textField>\n";
            x = x + Integer.parseInt(fld.getWidth());
        //  System.out.println((String) map.get(col + ".width"));
        }

        report = report + " </band>\n";
        report = report + " </detail>\n";



        report = report + " <pageFooter>\n";

        report = report + " <band height='40'>\n";
        report = report + " <textField>\n";
        report = report + " <reportElement x  = '200' y  = '20' width  = '85' height  = '15'/>\n";
        report = report + " <textElement textAlignment  = 'Right'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[ String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " <textField evaluationTime='Report'>\n";
        report = report + " <reportElement x='285' y='20' width='75' height='15' />\n";
        report = report + " <textElement textAlignment='Left'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[\" of \" + String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " </band>\n";
        report = report + " </pageFooter>\n";
        report = report + " </jasperReport>\n";
        //  System.out.println("//////  \n" + report + "\n/////////");
        return report;
    }

    ///////////////------------------------------------------------------------------/////////////////////////
    public static String createBasicReportWithChart(String[] columnList, Map fieldMap, Map map) {
        String report = null;
        report = "<?xml version='1.0' encoding='UTF-8' ?>\n";
        report = report + " <!DOCTYPE jasperReport PUBLIC '//JasperReports//DTD Report Design//EN' 'http://jasperreports.sourceforge.net/dtds/jasperreport.dtd'>\n";
        report = report + " <jasperReport name='myreport' columnCount='1' printOrder='Vertical' orientation='Portrait' pageWidth='595' pageHeight='842' columnWidth='535' columnSpacing='0' leftMargin='30' rightMargin='30' topMargin='20' bottomMargin='20' whenNoDataType='NoPages' isTitleNewPage='false' isSummaryNewPage='false'>\n";

        Set fset = fieldMap.keySet();
        Iterator it = fset.iterator();
        String fld = null;
        while (it.hasNext()) {
            fld = (String) it.next();
            report = report + " <field name='" + fld + "' class='" + fieldMap.get(fld) + "'></field>\n";
        }
        report = report + " <background>";
        report = report + " <band height='800'>";
        report = report + " <image>";
        report = report + " <reportElement x='100' y='200' width='595' height='500' mode='Transparent'/>";
        report = report + " <imageExpression class='java.lang.String'>";
        report = report + " <![CDATA[\"http://localhost:8080/S2HApp/example/Jaypee.JPG\"]]>";
        report = report + " </imageExpression>";
        report = report + " </image>";
        report = report + " </band>";
        report = report + " </background>";

        report = report + " <title>\n";
        report = report + " <band height='50'  isSplitAllowed='true'>\n";
        report = report + " <staticText>\n";
        report = report + " <reportElement x='" + map.get("report.title.x") + "' y='8' width='294' height='29' key='staticText-1'/>\n";
        report = report + " <box></box>\n";
        report = report + " <textElement>\n";
        report = report + " <font/>\n";
        report = report + " </textElement>\n";
        report = report + " <text><![CDATA[" + map.get("report.title") + "]]></text>\n";
        report = report + " </staticText>\n";
        report = report + " </band>\n";
        report = report + " </title>\n";

        report = report + " <columnHeader>\n";
        report = report + " <band height='50'  isSplitAllowed='true'>\n";
        int x = 0;
        for (String col : columnList) {
            report = report + " <staticText>\n";
            report = report + " <reportElement x='" + x + "' y='8' width='" + map.get(col + ".width") + "' height='29' key='staticText-" + col + "'/>\n";
            report = report + " <box></box>\n";
            report = report + " <textElement>\n";
            report = report + " <font/>\n";
            report = report + " </textElement>\n";
            report = report + " <text><![CDATA[" + map.get(col + ".header") + "]]></text>\n";
            report = report + " </staticText>\n";
            x = x + Integer.parseInt((String) map.get(col + ".width"));
        }
        report = report + " </band>\n";
        report = report + " </columnHeader>\n";
        // report = report + " <pageHeader>\n";
        //  report = report + " <band height='50'  isSplitAllowed='true' >\n";
        //  report = report + " </band>\n";
        //  report = report + " </pageHeader>\n";
        //report = report + " <columnHeader>\n";
        // report = report + " <band height='30'  isSplitAllowed='true' >\n";
        //  int x = 0;
        // for (String col : columnList) {
        // report = report + " <staticText>\n";
        //     report = report + " <reportElement x='" + x + "' y='4' width='" + map.get(col + ".width") + "' height='21'>\n";
        // report = report + " <reportElement x='" + x + "' y='5' width='" + map.get(col + ".width") + "' height='15'/>\n";
        //report = report + " </reportElement>\n";
        //report = report + " <textElement textAlignment='Left'>\n";
        // report = report + " <textElement>\n";
        //  report = report + " <font/>\n";
        // report = report + " </textElement>\n";
        // report = report + " <text>\n";
        //   report = report + " <![CDATA[" + map.get(col + ".header") + "]]>\n";
        //  report = report + " </text>\n";
        //   report = report + " </staticText>\n";
        //  x = x + Integer.parseInt((String) map.get(col + ".width"));
        // }
        // report = report + " </band>\n";
        // report = report + " </columnHeader>\n";
        report = report + " <detail>\n";
        report = report + " <band height='20'  isSplitAllowed='true' >\n";

        x = 0;
        for (String col : columnList) {
            System.out.println("//////  " + col + "/////////");
            report = report + " <textField>\n";
            report = report + " <reportElement x='" + x + "' y='4' width='" + map.get(col + ".width") + "' height='15' />\n";
            report = report + " <textElement textAlignment='" + map.get(col + ".alignment") + "'/>\n";
            report = report + " <textFieldExpression class='" + map.get(col + ".classType") + "'>\n";
            report = report + " <![CDATA[" + map.get(col + ".expression") + "]]>\n";
            report = report + " </textFieldExpression>\n";
            report = report + " </textField>\n";
            x = x + Integer.parseInt((String) map.get(col + ".width"));
            System.out.println((String) map.get(col + ".width"));
        }

        report = report + " </band>\n";
        report = report + " </detail>\n";

        report = report + " <pageFooter>\n";

        report = report + " <band height='40'>\n";
        report = report + " <textField>\n";
        report = report + " <reportElement x  = '200' y  = '20' width  = '85' height  = '15'/>\n";
        report = report + " <textElement textAlignment  = 'Right'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[ String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " <textField evaluationTime='Report'>\n";
        report = report + " <reportElement x='285' y='20' width='75' height='15' />\n";
        report = report + " <textElement textAlignment='Left'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[\" of \" + String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " </band>\n";
        report = report + " </pageFooter>\n";
        report = report + " <summary>";
        report = report + " <band height='142'  isSplitAllowed='true' >";
        report = report + " <pie3DChart>";
        report = report + " <chart  hyperlinkTarget='Self'>";
        report = report + " <reportElement x='106' y='10' width='281' height='125' key='element-1'/>";
        report = report + " <box></box>";
        report = report + " <chartLegend textColor='#000000' backgroundColor='#FFFFFF'>";
        report = report + " </chartLegend>";
        report = report + " </chart>";
        report = report + " <pieDataset>";
        report = report + " <dataset >";
        report = report + " </dataset>";
        report = report + " <keyExpression><![CDATA[" + map.get("chart.keyexpr") + "]]></keyExpression>";
        report = report + " <valueExpression><![CDATA[" + map.get("chart.valueexpr") + "]]></valueExpression>";
        report = report + " <labelExpression><![CDATA[" + map.get("chart.labelexpr") + "]]></labelExpression>";
        report = report + " <sectionHyperlink >";
        report = report + " </sectionHyperlink>";
        report = report + " </pieDataset>";
        report = report + " <pie3DPlot >";
        report = report + " <plot backgroundAlpha='0.5' foregroundAlpha='0.5' />";
        report = report + " </pie3DPlot>";
        report = report + " </pie3DChart>";
        report = report + " </band>";
        report = report + " </summary>";

        report = report + " </jasperReport>\n";
        return report;
    }

    public static byte[] exportToPdf(String reportXml, HashMap params, List data) {
        byte[] outBytes = null;
        try {
            ByteArrayInputStream arrin = new ByteArrayInputStream(reportXml.getBytes());
            JasperDesign jd = JRXmlLoader.load(arrin);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
            outBytes = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return outBytes;
    }

    public static byte[] exportToPdf(FileInputStream reportXml, HashMap params, List data) {
        byte[] outBytes = null;
        try {            
            JasperDesign jd = JRXmlLoader.load(reportXml);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
//               JasperPrint jp = JasperFillManager.fillReport("d:/AssetAdditionStatusReport.jasper", params, new JRBeanCollectionDataSource(data));
            outBytes = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return outBytes;
    }
// public static byte[] exportToPdf1(JasperPrint jp, HashMap params, List data) {
//        byte[] outBytes = null;
//        try {
////            JasperDesign jd = JRXmlLoader.load(reportXml);
////            JasperReport jr = JasperCompileManager.compileReport(jd);
////            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
////               JasperPrint jp = JasperFillManager.fillReport("d:/AssetAdditionStatusReport.jasper", params, new JRBeanCollectionDataSource(data));
//            outBytes = JasperExportManager.exportReportToPdf(jp);
//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
//        return outBytes;
//    }
      public static void exportToPdfFile(FileInputStream reportXml, HashMap params, List data) {      
        try {            
            JasperDesign jd = JRXmlLoader.load(reportXml);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
            JasperExportManager.exportReportToPdfFile(jp, "c:\\xyz.pdf");
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] exportToExcel(FileInputStream reportXml, HashMap params, List data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JasperDesign jd = JRXmlLoader.load(reportXml);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            // exporterXLS.setParameter(JRXlsExporterParameter.HYPERLINK_PRODUCER_FACTORY,);
            exporterXLS.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }       
        return baos.toByteArray();

    }

    public static byte[] exportToRTF(FileInputStream reportXml, HashMap params, List data) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JasperDesign jd = JRXmlLoader.load(reportXml);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
            JRRtfExporter exporterRTF = new JRRtfExporter();
            exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporterRTF.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }        
        return baos.toByteArray();
    }
   
    public static String createBasicReportSeatMaster(ReportData reportData) {
        String report = null;
        report = "<?xml version='1.0' encoding='UTF-8'?>\n";
        report = report + " <!DOCTYPE jasperReport PUBLIC '-//JasperReports//DTD Report Design//EN'\n";
        report = report + " 'http://jasperreports.sourceforge.net/dtds/jasperreport.dtd'>\n";

        report = report + " <jasperReport name='BasicReport' orientation='Landscape' pageWidth='1168' pageHeight='1100' columnWidth='1090'\n";
        report = report + " leftMargin='40' rightMargin='38' topMargin='20' bottomMargin='20'>\n";

        report = report + " <reportFont name='Verdana' isDefault='true' fontName='Arial' size='10'\n";
        report = report + " isBold='false' isItalic='false' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica' pdfEncoding='Cp1252' isPdfEmbedded='false' />\n";
        report = report + " <reportFont name='Arial_Bold' isDefault='false' fontName='Arial' size='12'\n";
        report = report + " isBold='true' isItalic='false' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica-Bold' pdfEncoding='Cp1252' isPdfEmbedded='false'/>\n";
        report = report + " <reportFont name='Arial_Italic' isDefault='false' fontName='Arial' size='12'\n";
        report = report + " isBold='false' isItalic='true' isUnderline='false' isStrikeThrough='false'\n";
        report = report + " pdfFontName='Helvetica-Oblique' pdfEncoding='Cp1252' isPdfEmbedded='false' />\n";
        List<FieldAttributes> fieldlist = reportData.getFieldAttributesList();
        for (int i = 0; i < fieldlist.size(); i++) {
            FieldAttributes field = fieldlist.get(i);
            report = report + " <field name='" + field.getFieldname() + "' class='" + field.getClasstype() + "'></field>\n";
        }


        report = report + " <title>\n";
        report = report + " <band height='45'  isSplitAllowed='true'>\n";

        report = report + " <line><reportElement x='0' y='0' width='515' height='1'/>\n";
        report = report + " <graphicElement/>";
        report = report + " </line>";

        report = report + " <staticText>\n";
        report = report + " <reportElement x='10' y='5' width='594' height='29' key='staticText-1'/>\n";
        report = report + " <box></box>\n";
        report = report + " <textElement>\n";
        report = report + " <font fontName='Lucida Sans' size='14' isBold='true' pdfEncoding ='Cp1254'/>\n";
        report = report + " </textElement>\n";
        report = report + " <text><![CDATA[" + reportData.getReporttitle() + "]]></text>\n";
        report = report + " </staticText>\n";
        report = report + " </band>\n";
        report = report + " </title>\n";



        /* Original */
        report = report + " <pageHeader>\n";
        report = report + " <band height='21' isSplitAllowed='true'>";


        /*Date
        report = report + " <textField isStretchWithOverflow='false' pattern='dd/MM/yyyy HH.mm.ss' isBlankWhenNull='false' evaluationTime='Report' hyperlinkType='None' hyperlinkTarget='Self' >";
        report = report + "<reportElement x='0' y='10'	width='100'	height='8'	key='textField'/>";
        report = report + "<box></box>";
        report = report + "<textElement>";
        report = report + "<font/>";
        report = report + "</textElement>";
        report = report + "<textFieldExpression   class='java.util.Date'><![CDATA[new java.util.Date()]]></textFieldExpression>";
        report = report + "</textField>";
        END Date*/



        report = report + " <rectangle>";
        report = report + " <reportElement x='0' y='5' width='532' height='15' backcolor='#333333'/>";
        report = report + " <graphicElement pen='None'/>";
        report = report + " </rectangle>";
        report = report + " <staticText>";
        report = report + " <reportElement mode='Transparent' x='0' y='5' width='532' height='15' forecolor='#ffffff' backcolor='#333333' positionType='FixRelativeToTop'/>";
        report = report + "<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>";
        report = report + "<font fontName='Arial' size='10' isBold='true' isItalic='false' isUnderline='false' isStrikeThrough='false' />";
        report = report + "</textElement>";
        //report = report + " <box topBorder='None' topBorderColor='#fff000' leftBorder='None' leftBorderColor='#fff000' rightBorder='None' rightBorderColor='#fff000' bottomBorder='None' bottomBorderColor='#fff000'/>";
        report = report + " <text><![CDATA[" + reportData.getPageheader() + "]]></text>";
        report = report + " </staticText>";
        report = report + " <line>";
        report = report + " <reportElement x='0' y='20' width='532' height='1'/>";
        report = report + " <graphicElement/>";
        report = report + " </line>";
        report = report + "</band>";
        report = report + "</pageHeader>";

        /* End Original */


        report = report + " <columnHeader>\n";
        report = report + " <band height='21'  isSplitAllowed='true'>\n";
        report = report + " <rectangle>";
        report = report + " <reportElement x='0' y='4' width='532' height='15' backcolor='#ffcc99'/>";
        report = report + " <graphicElement pen='None'/>";
        report = report + " </rectangle>";




        /*report = report + " <columnHeader>\n";
        report = report + " <band height='15'  isSplitAllowed='true'>\n";*/
        int x = 0;
        for (FieldAttributes fld : fieldlist) {
            report = report + " <staticText>\n";
            report = report + " <reportElement x='" + x + "' y='2' width='" + fld.getWidth() + "' height='13' key='staticText-" + fld.getFieldname() + "'/>\n";
            // report = report + "<textElement textAlignment='Center' verticalAlignment='Middle' rotation='None' lineSpacing='Single'>";
            // report = report + "<font fontName='Arial' size='10' isBold='true' isItalic='false' isUnderline='false' isStrikeThrough='false' />";
            // report = report + "</textElement>";
            // report = report + " <box bottomBorder='Thin' topBorder='Thin'></box>\n";
            report = report + " <textElement>\n";
            report = report + " <font fontName='Lucida Sans' pdfFontName='Helvetica-Bold' size='10' isBold='true' pdfEncoding ='Cp1254'/>\n";
            report = report + " </textElement>\n";
            report = report + " <text><![CDATA[" + fld.getHeader() + "]]></text>\n";
            report = report + " </staticText>\n";
            x = x + Integer.parseInt(fld.getWidth());
        }
        report = report + " </band>\n";
        report = report + " </columnHeader>\n";



        report = report + " <detail>\n";
        report = report + " <band height='15'>\n";

        x = 0;
        for (FieldAttributes fld : fieldlist) {
            // System.out.println("//////  " + col + "/////////");
            report = report + " <textField>\n";
            report = report + " <reportElement x='" + x + "' y='2' width='" + fld.getWidth() + "' height='13' />\n";
            //report = report + "<box bottomBorder='Thin'></box>\n";
            report = report + "<box></box>\n";
            report = report + " <textElement textAlignment='" + fld.getAlignment() + "'/>\n";
            report = report + " <textFieldExpression class='" + fld.getClasstype() + "'>\n";
            report = report + " <![CDATA[" + fld.getExpression() + "]]>\n";

            report = report + " </textFieldExpression>\n";
            report = report + " </textField>\n";
            x = x + Integer.parseInt(fld.getWidth());
        //  System.out.println((String) map.get(col + ".width"));
        }

        report = report + " </band>\n";
        report = report + " </detail>\n";

        report = report + " <pageFooter>\n";

        report = report + " <band height='40'>\n";
        report = report + " <textField>\n";
        report = report + " <reportElement x  = '200' y  = '20' width  = '85' height  = '15'/>\n";
        report = report + " <textElement textAlignment  = 'Right'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[ String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " <textField evaluationTime='Report'>\n";
        report = report + " <reportElement x='285' y='20' width='75' height='15' />\n";
        report = report + " <textElement textAlignment='Left'/>\n";
        report = report + " <textFieldExpression class='java.lang.String'>\n";
        report = report + " <![CDATA[\" of \" + String.valueOf($V{PAGE_NUMBER})]]>\n";
        report = report + " </textFieldExpression>\n";
        report = report + " </textField>\n";
        report = report + " </band>\n";
        report = report + " </pageFooter>\n";
        report = report + " </jasperReport>\n";
        System.out.println("//////  \n" + report + "\n/////////");
        return report;
    }
     public static byte[] exportToPdfJasper(String reportXml, HashMap params, List data) {
        byte[] outBytes = null;
        try {
              JasperPrint jp = JasperFillManager.fillReport(reportXml, params, new JRBeanCollectionDataSource(data));
            outBytes = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return outBytes;

    }
      public static byte[] exportToExcelJasper(String reportJasper, HashMap params, List data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JasperPrint jp = JasperFillManager.fillReport(reportJasper, params, new JRBeanCollectionDataSource(data));
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            // exporterXLS.setParameter(JRXlsExporterParameter.HYPERLINK_PRODUCER_FACTORY,);
            exporterXLS.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return baos.toByteArray();

    }
      public static byte[] exportToRTFJasper(String reportXml, HashMap params, List data) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            JasperPrint jp = JasperFillManager.fillReport(reportXml, params, new JRBeanCollectionDataSource(data));
            JRRtfExporter exporterRTF = new JRRtfExporter();
            exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporterRTF.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return baos.toByteArray();
    }
public static byte[] exportToPdf(JasperPrint jp, HashMap params, List data) {
        byte[] outBytes = null;
        try {
           outBytes = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return outBytes;
    }
  public static byte[] exportToExcel(JasperPrint jp, HashMap params, List data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
//            JasperDesign jd = JRXmlLoader.load(reportXml);
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            // exporterXLS.setParameter(JRXlsExporterParameter.HYPERLINK_PRODUCER_FACTORY,);
            exporterXLS.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return baos.toByteArray();

    }
   public static byte[] exportToRTF(JasperPrint jp, HashMap params, List data) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
//            JasperDesign jd = JRXmlLoader.load(reportXml);
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            JasperPrint jp = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(data));
            JRRtfExporter exporterRTF = new JRRtfExporter();
            exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporterRTF.exportReport();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return baos.toByteArray();
    }
}
