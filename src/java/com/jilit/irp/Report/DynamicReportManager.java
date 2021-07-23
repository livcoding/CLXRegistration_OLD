/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.irp.Report;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
// end of default import
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.DynamicReportOptions;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;


/**
 *
 * @author ajay2.kumar
 */
public abstract class DynamicReportManager {

    public static final String HTML = "HTML";
    public static final String PDF = "PDF";
    public static final String EXCEL = "EXCEL";
    public static final String RTF = "RTF";
    public static final String VIEWER = "VIEWER";
    public static final String PDFFILE = "PDFFILE";

    public abstract void getReport(HttpServletRequest request, HttpServletResponse response);

    public void setResponseType(HttpServletResponse response, String contentType, byte[] outBytes, String headerString) throws IOException {
        response.setContentType(contentType);
        response.setHeader("Content-Disposition", headerString);
        response.setContentLength(outBytes.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(outBytes);
        out.flush();
        out.close();
    }

    public void renderReport(DynamicReportProperty dynamicReportProperty) throws Exception {

        PageLayout pageLayout = (dynamicReportProperty.getPageLayout() == null) ? PageLayout.AUTO : dynamicReportProperty.getPageLayout();
        String path = dynamicReportProperty.getPath();
        List<LinkedHashMap> data = dynamicReportProperty.getData();
        String reportTitle = "".equals(dynamicReportProperty.getReportTitle())||(dynamicReportProperty.getReportTitle() == null) ? "Untitled" : dynamicReportProperty.getReportTitle();
        HashMap<String, String> parameters = dynamicReportProperty.getParameters();
        YesNo printSrNo = ((dynamicReportProperty.getPrintSrNo() == null) ? YesNo.NO : dynamicReportProperty.getPrintSrNo());
        int totWidth = 0;
        String filename = (dynamicReportProperty.getReportName() == null || "".equals(dynamicReportProperty.getReportName())) ? "Report" : dynamicReportProperty.getReportName().replace(" ", "");
        HttpServletResponse response = dynamicReportProperty.getResponse();
        String format = "";
        String exportto = (dynamicReportProperty.getExportTo() == null || "".equals(dynamicReportProperty.getExportTo())) ? "P" : dynamicReportProperty.getExportTo();
        List<DynamicColumnDetail> columnList = dynamicReportProperty.getColumnsList();
        if (columnList == null) {
            columnList = new ArrayList<DynamicColumnDetail>();
        }

        JasperPrint jp = null;

        if (data == null) {
            data = new ArrayList<LinkedHashMap>();
        }

        try {
            if (exportto.equalsIgnoreCase("P")) {
                format = PDF;
            } else if (exportto.contains("H")) {
                format = HTML;
            } else if (exportto.contains("W")) {
                format = RTF;
            } else if (exportto.contains("E")) {
                format = EXCEL;
            }

            String jrxmlPath = "";
              jrxmlPath = path + "/DynamicReport2.jrxml";

            parameters.put("title", reportTitle);
            Style detailStyle = new Style();
            Style headerStyle = new Style();
            headerStyle.setBackgroundColor(new Color(230, 230, 230));
            headerStyle.setBorderBottom(Border.THIN);
            headerStyle.setBorderColor(Color.black);
            headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            headerStyle.setTransparency(Transparency.OPAQUE);
            headerStyle.setBorder(Border.THIN);
            if (parameters.get("fontsize") != null) {
                if (parameters.get("fontsize").equals("small")) {
                    headerStyle.setFont(new Font(7, Font._FONT_ARIAL, false, true, false));
                }
            } else {
                headerStyle.setFont(Font.ARIAL_MEDIUM_BOLD);
            }
            headerStyle.setStretchWithOverflow(true);
            headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
            Style titleStyle = new Style();
            titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
            titleStyle.setFont(Font.ARIAL_BIG_BOLD);
            Style subtitleStyle = new Style();
            Style amountStyle = new Style();
            amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
            Style headStyle = new Style();
            headStyle.setHorizontalAlign(HorizontalAlign.LEFT);
            DynamicReportBuilder drb = new DynamicReportBuilder();
            drb.setTitle("").setSubtitle("").setDetailHeight(12).setMargins(0, 0, 0, 0).setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle).setColumnsPerPage(1);
            Map<String, String> map = new HashMap<String, String>();


            List list = new ArrayList();
            int i = 0;
            if (printSrNo.equals(YesNo.YES)) {
                List<LinkedHashMap> newData = new ArrayList<LinkedHashMap>();
                LinkedHashMap<String, String> m;
                long srno = 1L;
                if(dynamicReportProperty.isLastRowAsTotal()){
                 for (int h = 0; h < data.size(); h++) {
                    m = new LinkedHashMap();
                    if(h<data.size()-1)
                    m.put("srno",srno+"");
                    else
                    m.put("srno", "");
                    m.putAll(data.get(h));
                    newData.add(m);
                    srno++;
                }
                }else
                {
                 for (int h = 0; h < data.size(); h++) {
                    m = new LinkedHashMap();
                    m.put("srno", srno + "");
                    m.putAll(data.get(h));
                    newData.add(m);
                    srno++;
                }

                }
               
                data = new ArrayList<LinkedHashMap>();
                data = newData;
                DynamicColumnDetail srnoCol = new DynamicColumnDetail("Sr No.", 23,DataType.BIGDECIMAL,YesNo.YES, Alignment.RIGHT, Alignment.TOP,"srno");
                columnList.add(0, srnoCol);
            }
            if (data != null && data.size() > 0) {
                map = data.get(0);
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add(entry.getKey());
            }
            DynamicColumnDetail dynamicColumnDetail = null;
            AbstractColumn col1;
            String colTitle = "";
            int widths = 0;
            Style style;
            if (list != null && list.size() > 0) {

                for (int l = 0; l < columnList.size(); l++) {
                    style = new Style();
                    if(parameters.get("fontsize")!= null) {
                        if(parameters.get("fontsize").equals("small")) {
                            style.setFont(new Font(7, Font._FONT_ARIAL, false, true, false));
                        }
                    }else {
                        style.setFont(Font.ARIAL_MEDIUM_BOLD);
                    }
                    style.setVerticalAlign(VerticalAlign.MIDDLE);
                    colTitle = "";
                    widths = 0;
                    style.setHorizontalAlign(HorizontalAlign.LEFT);

                    dynamicColumnDetail = new DynamicColumnDetail();
                    dynamicColumnDetail = columnList.get(l);
                    colTitle = dynamicColumnDetail.getColumnTitle() == null ? "Untitled"+l : dynamicColumnDetail.getColumnTitle().toString();
                    widths = dynamicColumnDetail.getColumnWidth();

                    Alignment p = dynamicColumnDetail.getTextAlignment() == null ? Alignment.LEFT : dynamicColumnDetail.getTextAlignment();
                    if (p == Alignment.CENTER) {
                        style.setHorizontalAlign(HorizontalAlign.CENTER);
                    } else if (p == Alignment.RIGHT) {
                        style.setHorizontalAlign(HorizontalAlign.RIGHT);
                    } else if (p == Alignment.JUSTIFY) {
                        style.setHorizontalAlign(HorizontalAlign.JUSTIFY);
                    } else {
                        style.setHorizontalAlign(HorizontalAlign.LEFT);
                    }
                    if (widths == 0) {
                        widths = 100;
                    }
                     if(dynamicColumnDetail.getTextVericalAlignment()==Alignment.BOTTOM)
                            style.setVerticalAlign(VerticalAlign.BOTTOM);
                                    else if(dynamicColumnDetail.getTextVericalAlignment()==Alignment.TOP)
                                        style.setVerticalAlign(VerticalAlign.TOP);
                                                else if(dynamicColumnDetail.getTextVericalAlignment()==Alignment.JUSTIFY)
                                                    style.setVerticalAlign(VerticalAlign.JUSTIFIED);
                                                            else style.setVerticalAlign(VerticalAlign.MIDDLE);
               if(dynamicReportProperty.isShowDataIntoGridLines()){style.setBorder(Border.THIN); style.setBorderColor(Color.black);}
                    
                    if(l<list.size()){
                        if("srno".equals(list.get(l)))
                        {
                        if(dynamicReportProperty.getSrnoHorizontalAlign()==Alignment.CENTER)
                            style.setHorizontalAlign(HorizontalAlign.CENTER);
                                    else if(dynamicReportProperty.getSrnoHorizontalAlign()==Alignment.LEFT)
                                        style.setHorizontalAlign(HorizontalAlign.LEFT);
                                                else if(dynamicReportProperty.getSrnoHorizontalAlign()==Alignment.JUSTIFY)
                                                    style.setHorizontalAlign(HorizontalAlign.JUSTIFY);
                                                            else style.setHorizontalAlign(HorizontalAlign.RIGHT);
                        
                        if(dynamicReportProperty.getSrnoVerticalAlign()==Alignment.BOTTOM)
                            style.setVerticalAlign(VerticalAlign.BOTTOM);
                                    else if(dynamicReportProperty.getSrnoVerticalAlign()==Alignment.MIDDLE)
                                        style.setVerticalAlign(VerticalAlign.MIDDLE);
                                                else if(dynamicReportProperty.getSrnoVerticalAlign()==Alignment.JUSTIFY)
                                                    style.setVerticalAlign(VerticalAlign.JUSTIFIED);
                                                            else style.setVerticalAlign(VerticalAlign.TOP);
                       
                        }
                      
                         col1 = ColumnBuilder.getInstance().setColumnProperty(dynamicColumnDetail.getColumnName(), String.class.getName()).setTitle(colTitle).setFixedWidth(false).setWidth(widths).setStyle(style).build();

                        col1.setBlankWhenNull(true);
                    }
                    else
                    {
                    col1 = ColumnBuilder.getInstance().setColumnProperty("undefined"+l, String.class.getName()).setTitle(colTitle).setFixedWidth(false).setWidth(widths).setStyle(style).build();
                    col1.setBlankWhenNull(true);
                    }
                     drb.addColumn(col1);

                    totWidth = totWidth + widths;
                }
                if (dynamicReportProperty.isUseFullPageWidth()) {
                    drb.setUseFullPageWidth(true);
                }
                drb.setMargins(0, 0, 0, 0);
                style = new Style();
                style.setBackgroundColor(Color.LIGHT_GRAY);
                style.setFont(Font.ARIAL_MEDIUM_BOLD);
                style.setTextColor(Color.BLACK);
                if (dynamicReportProperty.getGroupDetailsList() != null) {
                    List<DynamicGroupDetail> gList = new ArrayList<DynamicGroupDetail>();
                    gList = dynamicReportProperty.getGroupDetailsList();
                    DynamicGroupDetail dynamicGroupDetail;
                    AbstractColumn cols;
                    GroupBuilder myGB;
                    DJGroup myDJGroup;

                    DJGroupLabel glabel1;
                    String label = "";

                    for (int k = 0; k < gList.size(); k++) {
                        dynamicGroupDetail = new DynamicGroupDetail();
                        dynamicGroupDetail = (DynamicGroupDetail)gList.get(k);

                        cols = ColumnBuilder.getInstance().setColumnProperty(dynamicGroupDetail.getGroupByProperty(), String.class.getName()).setTitle("Ajay").setStyle(style).build();
                        drb.addColumn(cols);

                        myGB = new GroupBuilder();
                        myDJGroup = myGB.setCriteriaColumn((PropertyColumn) cols).setGroupLayout(GroupLayout.VALUE_IN_HEADER).build();
                        if(dynamicGroupDetail.getStartWithNewPage())
                        myDJGroup.setStartInNewPage(true);
                        drb.addGroup(myDJGroup);
                    }// end of Loop`
                }

//            }
             if(dynamicReportProperty.isLastRowAsTotal())
            {
               LinkedHashMap mapTemp=new LinkedHashMap();
               LinkedHashMap mapTemp1=new LinkedHashMap();
               Map<String, String> map1 = new HashMap<String, String>();
               map1=data.get(data.size()-1);
               for (Map.Entry<String, String> entry : map1.entrySet()) {

               //CID 10801 (#1 of 1): Identical code for different branches (IDENTICAL_BRANCHES)(By Girish)
                 //  if(entry.getKey().equals("srno"))
                       mapTemp.put(entry.getKey(), "");
                  // else
                //mapTemp.put(entry.getKey(), "");
              //
                //CID 10801 (#1 of 1): Identical code for different branches (IDENTICAL_BRANCHES) (By Girish)
               }data.add(data.size()-1, mapTemp);
               }

            }// end of if
            else {
                drb.setMargins(0, 0, 0, 0);
                style = new Style();
                style.setBackgroundColor(Color.WHITE);
                style.setFont(Font.ARIAL_MEDIUM_BOLD);
                style.setTextColor(Color.BLACK);
                AbstractColumn cols;
                cols = ColumnBuilder.getInstance().setColumnProperty("temp", String.class.getName()).setTitle("No Data Found").setStyle(style).build();
                drb.addColumn(cols);
            }
          /* Operater has been changed to handle null properly
             Modified by: suryakant sharma 26/92017
           * */
            if (dynamicReportProperty.getSummary() != null && !"".equals(dynamicReportProperty.getSummary())) {
                if (dynamicReportProperty.getSummaryAlignment() == Alignment.LEFT) {
                    parameters.put("summarytextl", dynamicReportProperty.getSummary());
                } else if (dynamicReportProperty.getSummaryAlignment() == Alignment.CENTER) {
                    parameters.put("summarytextc", dynamicReportProperty.getSummary());
                } else if (dynamicReportProperty.getSummaryAlignment() == Alignment.JUSTIFY) {
                    parameters.put("summarytextj", dynamicReportProperty.getSummary());
                } else if (dynamicReportProperty.getSummaryAlignment() == Alignment.RIGHT) {
                    parameters.put("summarytextr", dynamicReportProperty.getSummary());
                } else {
                    parameters.put("summarytextl", dynamicReportProperty.getSummary());
                }

            }
            if(dynamicReportProperty.getListOfParameters()!=null && !"".equals(dynamicReportProperty.getListOfParameters()))
            {
             parameters.put("parameters", dynamicReportProperty.getListOfParameters());
            }
            if(dynamicReportProperty.isPrintBackgroundOnOddRows())
            drb.setPrintBackgroundOnOddRows(true);

            if(dynamicReportProperty.getPageLayout().equals(PageLayout.PAGE_A4_PORTRAIT) ||
               dynamicReportProperty.getPageLayout().equals(PageLayout.PAGE_LEGAL_PORTRAIT) ||
               dynamicReportProperty.getPageLayout().equals(PageLayout.PAGE_LETTER_PORTRAIT) ||
               (dynamicReportProperty.getPageLayout().equals(PageLayout.AUTO) &&
               totWidth<611) ||
               (dynamicReportProperty.getPageLayout().equals(PageLayout.CUSTOM_SIZE) &&
               totWidth<611)
               )
              jrxmlPath = path + "/DynamicReport1.jrxml";
            
            drb.setReportName(filename);
            drb.setTemplateFile(jrxmlPath);
            JRDataSource ds = new JRBeanCollectionDataSource(data);
            DynamicReport dr = drb.build();
            DynamicReportOptions options = dr.getOptions();
            dr.setAllowDetailSplit(true);
            Page page = new Page();
                    //options.getPage();
            if(dynamicReportProperty.getPageLayout()==PageLayout.AUTO)
            {
            page.setWidth(totWidth);
            page.setHeight(totWidth);
            }
            else if(dynamicReportProperty.getPageLayout()==PageLayout.CUSTOM_SIZE)
            {
            page.setWidth(dynamicReportProperty.getPageWidth());
            page.setHeight(dynamicReportProperty.getPageHeight());
            }
            else
             page=getPageLayout1(pageLayout, totWidth);

            options.setPage(page);
            dr.setOptions(options);
            dr.setReportName(filename);

            jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (format.equals(this.HTML)) {
            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "/web/common/JRImage!renderImage.action?image=");
            exporter.exportReport();
        } else if (format.equals(PDF)) {
            byte[] outBytes = Report.exportToPdf(jp, parameters, data);
            setResponseType(response, "application/pdf", outBytes, "inline;filename=\"" + filename + ".pdf\"");
        } else if (format.equals(PDFFILE)) {
            byte[] outBytes = Report.exportToPdf(jp, parameters, data);
            setResponseType(response, "application/pdf", outBytes, "attachment;filename=\"" + filename + ".pdf\"");
        } else if (format.equals(EXCEL)) {
            byte[] outBytes = Report.exportToExcel(jp, parameters, data);
            setResponseType(response, "application/vnd.ms-excel", outBytes, "inline;filename=" + filename + ".xls");
        } else if (format.equals(RTF)) {
            byte[] outBytes = Report.exportToRTF(jp, parameters, data);
            setResponseType(response, "application/rtf", outBytes, "inline;filename=" + filename + ".rtf");
        } else if (format.equals(VIEWER)) {
        }
        System.gc();
    }
    
    public Page getPageLayout1(PageLayout pgLayout,long totWidth)
    {
    Page page=new Page();
    if(pgLayout.equals(PageLayout.PAGE_A4_AUTO)){
           if (totWidth > 590) {
            page=Page.Page_A4_Landscape();
                } else {
                 page=Page.Page_A4_Portrait();
                }
    }
    else if(pgLayout.equals(PageLayout.PAGE_A4_LANDSCAPE)){
    page=Page.Page_A4_Landscape();
    }
    else if(pgLayout.equals(PageLayout.PAGE_A4_PORTRAIT)){
    page=Page.Page_A4_Portrait();
    }
    else if(pgLayout.equals(PageLayout.PAGE_LEGAL_PORTRAIT)){
    page=Page.Page_Legal_Portrait();
    }
    else if(pgLayout.equals(PageLayout.PAGE_LETTER_AUTO)){

         if (totWidth > 610) {
            page=Page.Page_Letter_Landscape();
                } else {
                 page=Page.Page_Letter_Portrait();
                }
        }
    else if(pgLayout.equals(PageLayout.PAGE_LETTER_LANDSCAPE)){
    page=Page.Page_Letter_Landscape();
    }
    else if(pgLayout.equals(PageLayout.PAGE_LETTER_PORTRAIT)){
    page=Page.Page_Letter_Portrait();
    }

    else if(pgLayout.equals(PageLayout.PAGE_Legal_AUTO)){
     if (totWidth > 605) {
            page=Page.Page_Legal_Landscape();
                } else {
                 page=Page.Page_Legal_Portrait();
                }
       }
    else if(pgLayout.equals(PageLayout.PAGE_Legal_LANDSCAPE)){
    page=Page.Page_Legal_Landscape();
    }
    else{
         page=Page.Page_A4_Landscape();
    }
    return page;
    }
}
