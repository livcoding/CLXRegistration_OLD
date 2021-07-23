/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

/**
 *
 * @author subrata.lohar
 */

import net.sf.jasperreports.engine .*;
import net.sf.jasperreports.engine.export .*;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import java.util.List;
import java.io. *;
//import com.log.Logger;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.util.JRLoader;



public class MyJasperExportManager {

public MyJasperExportManager () {

}

public void exportReportToRTF (JasperPrint jasperPrint, OutputStream out) {

    JRRtfExporter rtfExporter = new JRRtfExporter ();

    rtfExporter.setParameter (JRExporterParameter.JASPER_PRINT, jasperPrint);

    rtfExporter.setParameter (JRExporterParameter.OUTPUT_STREAM, out);

    try {
        rtfExporter.exportReport ();       
    } catch (JRException e) {
        e.printStackTrace ();      

    }
}
}


