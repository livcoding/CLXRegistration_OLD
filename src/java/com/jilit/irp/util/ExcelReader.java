package com.jilit.irp.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author deepak.gupta
 */
public class ExcelReader {

    @SuppressWarnings("unchecked")
    public List displayFromExcel(String xlsPath) {
        InputStream inputStream = null;
        List l = new ArrayList();
        try {
            inputStream = new FileInputStream(xlsPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        POIFSFileSystem fileSystem = null;
        try {
            fileSystem = new POIFSFileSystem(inputStream);
            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workBook.getSheetAt(0);

            int index = 0;
            Map aso = new HashMap();
            Iterator<HSSFRow> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = rows.next();
                aso = new HashMap();
                index = 0;
                Iterator<HSSFCell> cells = row.cellIterator();

                while (cells.hasNext()) {
                    HSSFCell cell = cells.next();
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: {
                            System.out.println("Numeric value: " + cell.getNumericCellValue());
                            aso.put("C" + index++, cell.getNumericCellValue());
                            break;
                        }
                        case HSSFCell.CELL_TYPE_STRING: {
                            HSSFRichTextString richTextString = cell.getRichStringCellValue();
                            aso.put("C" + index++, richTextString.getString());
                            break;
                        }
                        default: {
                            aso.put("C" + index++, "Type not supported.");
                            break;
                        }
                    }
                }
                l.add(aso);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;
    }

    public static List<Map> getExcelColumnData(String xlsPath) {
        InputStream inputStream = null;
        POIFSFileSystem fileSystem = null;
        List<Map> retList = new ArrayList<Map>();
        Map map;
        try {
            inputStream = new FileInputStream(xlsPath);
            fileSystem = new POIFSFileSystem(inputStream);
            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workBook.getSheetAt(0);
            Iterator<HSSFRow> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = rows.next();
                Iterator<HSSFCell> cells = row.cellIterator();
                map = new HashMap();
                while (cells.hasNext()) {
                    HSSFCell cell = cells.next();
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: {
                            map.put(cell.getCellNum(), cell.getNumericCellValue());
                            break;
                        }
                        case HSSFCell.CELL_TYPE_STRING: {
                            HSSFRichTextString richTextString = cell.getRichStringCellValue();
                            map.put(cell.getCellNum(), richTextString.getString());
                            break;
                        }
                        default: {
                            map.put(cell.getCellNum(), "Type not supported.");
                            break;
                        }
                    }
                }
                retList.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retList;

    }

    public static List getExcelColumnData(String xlsPath, List cloumn, Map<String, String> map) {
        InputStream inputStream = null;
        List l = new ArrayList();
        List tnameGrid = new ArrayList();
        List saveList = new ArrayList();
        List retList = new ArrayList();
        try {
            inputStream = new FileInputStream(xlsPath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in the specified path.");
            e.printStackTrace();
        }
        POIFSFileSystem fileSystem = null;
        try {
            fileSystem = new POIFSFileSystem(inputStream);
            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workBook.getSheetAt(0);

            int index = 0;
            Map aso = new HashMap();
            Map asobj = new HashMap();
            Map gridListaso = new HashMap();
            Iterator<HSSFRow> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                HSSFRow row = rows.next();
                aso = new HashMap();
                asobj = new HashMap();
                gridListaso = new HashMap();
                index = 0;
                Iterator<HSSFCell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = cells.next();
                    if (cloumn.contains(Integer.valueOf(cell.getCellNum()))) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_NUMERIC: {
                                aso.put("C" + cell.getCellNum(), cell.getNumericCellValue());
                                String tname = map.get(cell.getCellNum() + "");
                                aso.put("tname" + cell.getCellNum(), tname);
                                asobj.put(tname, cell.getNumericCellValue());
                                gridListaso.put("C" + cell.getCellNum(), cell.getNumericCellValue());
                                break;
                            }
                            case HSSFCell.CELL_TYPE_STRING: {
                                HSSFRichTextString richTextString = cell.getRichStringCellValue();
                                aso.put("C" + cell.getCellNum(), richTextString.getString());
                                String tname = map.get(cell.getCellNum() + "");
                                aso.put("tname" + cell.getCellNum(), tname);
                                asobj.put(tname, richTextString.getString());
                                gridListaso.put("C" + cell.getCellNum(), richTextString.getString());
                                break;
                            }
                            default: {
                                aso.put("C" + cell.getCellNum(), "Type not supported.");
                                String tname = map.get(cell.getCellNum() + "");
                                aso.put("tname" + cell.getCellNum(), tname);
                                asobj.put(tname, "Type not supported.");
                                gridListaso.put("C" + cell.getCellNum(), "Type not supported.");
                                break;
                            }
                        }
                    }
                }
                l.add(gridListaso);
                tnameGrid.add(aso);
                saveList.add(asobj);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        retList.add(l);
        retList.add(tnameGrid);
        retList.add(saveList);
        return retList;
    }

    public static List getHeaderDataList(String xlsPath) {
        List retList = new ArrayList();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(xlsPath);
        } catch (FileNotFoundException e) {
            System.out.println("File not found in the specified path.");
            e.printStackTrace();
        }
        POIFSFileSystem fileSystem = null;
        try {
            fileSystem = new POIFSFileSystem(inputStream);
            HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workBook.getSheetAt(0);
            int index = 0;
            Iterator<HSSFRow> rows = sheet.rowIterator();
            while (rows.hasNext() && index == 0) {
                index++;
                HSSFRow row = rows.next();
                Iterator<HSSFCell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    HSSFCell cell = cells.next();
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC: {
                            retList.add(cell.getCellNum() + "::" + cell.getNumericCellValue());
                            break;
                        }
                        case HSSFCell.CELL_TYPE_STRING: {
                            HSSFRichTextString richTextString = cell.getRichStringCellValue();
                            retList.add(cell.getCellNum() + "::" + richTextString.getString().toUpperCase());
                            break;
                        }
                        default: {
                            retList.add(cell.getCellNum() + "::");
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retList;
    }

    /**
     * The main executable method to test displayFromExcel method.
     *
     * @param args
     */
    public static void main(String[] args) {
        ExcelReader poiExample = new ExcelReader();
        String xlsPath = "c://test1.xls";
        List l = (List) poiExample.displayFromExcel(xlsPath);
        System.err.println("List Size" + l.size());
    }
}
