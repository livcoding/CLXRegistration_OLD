/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jilit.bso.xlsexport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

/**
 *
 * @author singh.jaswinder
 */
public class ExcelExport {

    private String inputFile;
    private HSSFWorkbook workbook = null;
    private HSSFSheet sheet = null;

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public ExcelExport() {
    }

    public ExcelExport(String ReportName) {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(ReportName);
    }

    public void setMainTitle(String title, int mtc, int rowHeight) {
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(title);
        cell.setCellStyle(getTitleStyle_Main("ARIAL", 16));
        row.setHeightInPoints((float) rowHeight);
        setMgdRegion(0, 0, 0, mtc);

    }

    public void setAddress(String address, int mtc, int rowHeight) {
        HSSFRow row = sheet.createRow((short) 1);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(address);
        cell.setCellStyle(getTitleStyle_Address("ARIAL", 10));
        row.setHeightInPoints((float) rowHeight);
        setMgdRegion(1, 0, 1, mtc);
    }

    /**
     *
     * @param title
     * @param mtc
     * @param rowHeight default 12.75
     */
    public void setSubTitle(String title, int mtc, int rowHeight) {
        HSSFRow row = sheet.createRow((short) 2);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellValue(title);
        cell.setCellStyle(getTitleStyleForSubTitle("ARIAL", 12));
        row.setHeightInPoints((float) rowHeight);
        setMgdRegion(2, 0, 2, mtc);
    }

    public void setData(List<String> columnHeader, List<Object[]> columnData) {
        int rownum = 4;
        HSSFRow row = sheet.createRow((short) rownum);
        HSSFCell cell = null;
        int col = 0;
        for (String columnName : columnHeader) {
            cell = row.createCell((short) col);
            cell.setCellValue(columnName);
            cell.setCellStyle(getTitleStyle("ARIAL", 10));
            sheet.autoSizeColumn((short) col);
            col++;
        }
        for (Object obj[] : columnData) {
            rownum++;
            row = sheet.createRow((short) rownum);
            col = 0;
            for (int i = 0; i < obj.length; i++) {
                cell = row.createCell((short) col);
                cell.setCellValue(obj[i].toString());
                cell.setCellStyle(getTitleStyle_Cell("ARIAL", 10));
                try {
                    if (!"".equals(obj[i].toString())) {
                        sheet.autoSizeColumn((short) col);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                col++;
            }

        }
    }

    public void doWrite() throws IOException {
        FileOutputStream file = new FileOutputStream(inputFile);
        workbook.write(file);
        file.flush();
        file.close();
    }

    public HSSFCellStyle getTitleStyle(String fontname, int fontsize) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) fontsize);
        font.setFontName(fontname);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    public HSSFCellStyle getTitleStyleForSubTitle(String fontname, int fontsize) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) fontsize);
        font.setFontName(fontname);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        return cellStyle;
    }

    public void setMgdRegion(int rf, int cf, int rt, int ct) {
        sheet.addMergedRegion(new Region(rf, (short) cf, rt, (short) ct));
    }

    public void setPrintSetUp() {
        HSSFPrintSetup ps = sheet.getPrintSetup();
        sheet.setAutobreaks(true);
        ps.setFitHeight((short) 1);
        ps.setFitWidth((short) 1);
    }

    public HSSFCellStyle getTitleStyle_Cell(String fontname, int fontsize) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) fontsize);
        font.setFontName(fontname);
        cellStyle.setFont(font);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    public HSSFCellStyle getTitleStyle_Address(String fontname, int fontsize) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) fontsize);
        font.setFontName(fontname);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    public HSSFCellStyle getTitleStyle_Main(String fontname, int fontsize) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) fontsize);
        font.setFontName(fontname);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    //To make some cell bold use this method by appending ::Bold with the cell data
    public void setDataMakeSomeBold(List<String> columnHeader, List<Object[]> columnData) {
        int rownum = 4;
        HSSFRow row = sheet.createRow((short) rownum);
        HSSFCell cell = null;
        int col = 0;
        for (String columnName : columnHeader) {
            cell = row.createCell((short) col);
            cell.setCellValue(columnName);
            cell.setCellStyle(getTitleStyle("ARIAL", 10));
            col++;
        }
        for (Object obj[] : columnData) {
            rownum++;
            row = sheet.createRow((short) rownum);
            col = 0;
            for (int i = 0; i < obj.length; i++) {
                cell = row.createCell((short) col);
                String[] dataBold = obj[i].toString().split("::");
                if (dataBold.length == 2) {
                    cell.setCellStyle(getTitleStyleForSubTitle("ARIAL", 10));
                }
                cell.setCellValue(dataBold[0].toString());
                try {
                    if (!"".equals(obj[i].toString())) {
                        sheet.autoSizeColumn((short) col);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                col++;
            }

        }
    }

    public List getSheetInformation(String filename) throws FileNotFoundException, IOException {
        List<Object[]> retlist = new ArrayList<Object[]>();
        FileInputStream fin = new FileInputStream(filename);
        HSSFWorkbook workbook_local = new HSSFWorkbook(fin);
        int sheets = workbook_local.getNumberOfSheets();
        for (int j = 0; j < sheets; j++) {
            HSSFSheet sheet_local = workbook_local.getSheetAt(j);
            String sheet_name = workbook_local.getSheetName(j);
            Iterator<HSSFRow> itr = (Iterator<HSSFRow>) sheet_local.rowIterator();
            int maxrows = 0;
            int maxcols = 0;
            int rowcount = 0;
            int colcount = 0;
            RowLoop:
            while (itr.hasNext()) {
                rowcount += 1;
                colcount = 0;
                HSSFRow row = itr.next();
                //System.out.println("Row Number is "+rowcount);
                if (row != null) {
                    Iterator<HSSFCell> cellitr = (Iterator<HSSFCell>) row.cellIterator();
                    ColumnLoop:
                    while (cellitr.hasNext()) {
                        colcount += 1;
                        HSSFCell cell = cellitr.next();
                        //System.out.println("Columns number is " + colcount + " Column value is " + cell.toString().trim() + "\t");
                        if (cell != null && cell.toString().trim() != null && !"".equals(cell.toString().trim()) && cell.toString().trim().length() >= 7) {
                            if ("end".equalsIgnoreCase(cell.toString().trim().substring(2).substring(0, cell.toString().trim().length() - 4))) {
                                maxrows = rowcount;
                                if (colcount != 0 && colcount != 1) {
                                    maxcols = colcount;
                                    break ColumnLoop;
                                } else {
                                    break RowLoop;
                                }
                            }
                        }
                    }
                }
            }
            retlist.add(new Object[]{sheet_name, 1, maxrows == 0 ? "Please define max row limit for " + sheet_name + "." : maxrows - 1, 1, maxcols == 0 ? "Please define max column limit for " + sheet_name + "." : maxcols - 1});// do not comsider <<end>> value column and row so subtract 1 from each
        }
        return retlist;
    }

    public HSSFSheet writeDataInForm16Template(List<String> columnHeader, List<Object[]> columnData, HSSFSheet sheet) {
        int rownum = 0;
        HSSFRow row = sheet.createRow((short) rownum);
        HSSFCell cell = null;
        int col = 0;
        for (String columnName : columnHeader) {
            cell = row.createCell((short) col);
            cell.setCellValue(columnName);
            //cell.setCellStyle(getTitleStyle("ARIAL", 10));
            sheet.autoSizeColumn((short) col);
            col++;
        }
        for (Object obj[] : columnData) {
            rownum++;
            row = sheet.createRow((short) rownum);
            col = 0;
            for (int i = 0; i < obj.length; i++) {
                cell = row.createCell((short) col);
                cell.setCellValue(obj[i].toString());
                //cell.setCellStyle(getTitleStyle_Cell("ARIAL", 10));
                try {
                    if (!"".equals(obj[i].toString())) {
                        sheet.autoSizeColumn((short) col);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                col++;
            }

        }
        return sheet;
    }
}
