package com.jilit.irp.Report;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.Locale;
import javax.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyJasperViewer extends JFrame
{
    private static final Log log;
    private static final long serialVersionUID = 10200L;
    protected MyJRViewer viewer;
    private boolean isExitOnClose;
    private JPanel pnlMain;

    public MyJasperViewer(String sourceFile, boolean isXMLFile)
        throws JRException
    {
        this(sourceFile, isXMLFile, true);
    }

    public MyJasperViewer(InputStream is, boolean isXMLFile)
        throws JRException
    {
        this(is, isXMLFile, true);
    }

    public MyJasperViewer(JasperPrint jasperPrint)
    {
        this(jasperPrint, true);
    }

    public MyJasperViewer(String sourceFile, boolean isXMLFile, boolean isExitOnClose)
        throws JRException
    {
        this(sourceFile, isXMLFile, isExitOnClose, null);
    }

    public MyJasperViewer(InputStream is, boolean isXMLFile, boolean isExitOnClose)
        throws JRException
    {
        this(is, isXMLFile, isExitOnClose, null);
    }

    public MyJasperViewer(JasperPrint jasperPrint, boolean isExitOnClose)
    {
        this(jasperPrint, isExitOnClose, ((Locale) (null)));
    }

    public MyJasperViewer(String sourceFile, boolean isXMLFile, boolean isExitOnClose, Locale locale)
        throws JRException
    {
        viewer = null;
        this.isExitOnClose = true;
        if(locale != null)
        {
            setLocale(locale);
        }
        this.isExitOnClose = isExitOnClose;
        initComponents();
        viewer = new MyJRViewer(sourceFile, isXMLFile, locale);
        pnlMain.add(viewer, "Center");
    }

    public MyJasperViewer(InputStream is, boolean isXMLFile, boolean isExitOnClose, Locale locale)
        throws JRException
    {
        viewer = null;
        this.isExitOnClose = true;
        if(locale != null)
        {
            setLocale(locale);
        }
        this.isExitOnClose = isExitOnClose;
        initComponents();
        viewer = new MyJRViewer(is, isXMLFile, locale);
        pnlMain.add(viewer, "Center");
    }

    public MyJasperViewer(JasperPrint jasperPrint, boolean isExitOnClose, Locale locale)
    {
        viewer = null;
        this.isExitOnClose = true;
        if(locale != null)
        {
            setLocale(locale);
        }
        this.isExitOnClose = isExitOnClose;
        initComponents();
        viewer = new MyJRViewer(jasperPrint, locale);
        pnlMain.add(viewer, "Center");
    }

    private void initComponents()
    {
        pnlMain = new JPanel();
        setTitle("JasperViewer");
        setIconImage((new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/jricon.GIF"))).getImage());
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt)
            {
                exitForm();
            }


            {
                //super();
            }
        });
        pnlMain.setLayout(new BorderLayout());
        getContentPane().add(pnlMain, "Center");
        pack();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenResolution = toolkit.getScreenResolution();
        float zoom = (float)screenResolution / 72F;
        int height = (int)(550F * zoom);
        if((double)height > screenSize.getHeight())
        {
            height = (int)screenSize.getHeight();
        }
        int width = (int)(750F * zoom);
        if((double)width > screenSize.getWidth())
        {
            width = (int)screenSize.getWidth();
        }
        Dimension dimension = new Dimension(width, height);
        setSize(dimension);
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
    }

    void exitForm()
    {
        if(isExitOnClose)
        {
            System.exit(0);
        } else
        {
            setVisible(false);
            viewer.clear();
            viewer = null;
            getContentPane().removeAll();
            dispose();
        }
    }

    public void setZoomRatio(float zoomRatio)
    {
        viewer.setZoomRatio(zoomRatio);
    }

    public void setFitWidthZoomRatio()
    {
        viewer.setFitWidthZoomRatio();
    }

    public void setFitPageZoomRatio()
    {
        viewer.setFitPageZoomRatio();
    }

    public static void main(String args[])
    {
        String fileName = null;
        boolean isXMLFile = false;
        for(int i = 0; i < args.length; i++)
        {
            if(args[i].startsWith("-XML"))
            {
                isXMLFile = true;
                continue;
            }
            if(args[i].startsWith("-F"))
            {
                fileName = args[i].substring(2);
            } else
            {
                fileName = args[i];
            }
        }

        if(fileName == null)
        {
            usage();
            return;
        }
        if(!isXMLFile && fileName.endsWith(".jrpxml"))
        {
            isXMLFile = true;
        }
        try
        {
            viewReport(fileName, isXMLFile);
        }
        catch(JRException e)
        {
            if(log.isErrorEnabled())
            {
                log.error("Error viewing report.", e);
            }
            System.exit(1);
        }
    }

    private static void usage()
    {
        System.out.println("JasperViewer usage:");
        System.out.println("\tjava JasperViewer [-XML] file");
    }

    public static void viewReport(String sourceFile, boolean isXMLFile)
        throws JRException
    {
        viewReport(sourceFile, isXMLFile, true, null);
    }

    public static void viewReport(InputStream is, boolean isXMLFile)
        throws JRException
    {
        viewReport(is, isXMLFile, true, null);
    }

    public static void viewReport(JasperPrint jasperPrint)
    {
        viewReport(jasperPrint, true, ((Locale) (null)));
    }

    public static void viewReport(String sourceFile, boolean isXMLFile, boolean isExitOnClose)
        throws JRException
    {
        viewReport(sourceFile, isXMLFile, isExitOnClose, null);
    }

    public static void viewReport(InputStream is, boolean isXMLFile, boolean isExitOnClose)
        throws JRException
    {
        viewReport(is, isXMLFile, isExitOnClose, null);
    }

    public static void viewReport(JasperPrint jasperPrint, boolean isExitOnClose)
    {  

        viewReport(jasperPrint, isExitOnClose, ((Locale) (null)));
    }

    public static void viewReport(String sourceFile, boolean isXMLFile, boolean isExitOnClose, Locale locale)
        throws JRException
    {
        MyJasperViewer jasperViewer = new MyJasperViewer(sourceFile, isXMLFile, isExitOnClose, locale);
        jasperViewer.setVisible(true);
    }

    public static void viewReport(InputStream is, boolean isXMLFile, boolean isExitOnClose, Locale locale)
        throws JRException
    {
        MyJasperViewer jasperViewer = new MyJasperViewer(is, isXMLFile, isExitOnClose, locale);
        jasperViewer.setVisible(true);
    }

    public static void viewReport(JasperPrint jasperPrint, boolean isExitOnClose, Locale locale)
    {
        MyJasperViewer jasperViewer = new MyJasperViewer(jasperPrint, isExitOnClose, locale);
        jasperViewer.setVisible(true);
    }

    static Class _mthclass$(String x0) throws ClassNotFoundException, Throwable
    {
        return Class.forName(x0);
        //ClassNotFoundException x1;
       // x1;
       // throw (new NoClassDefFoundError()).initCause(x1);
    }

    static
    {
        log = LogFactory.getLog(net.sf.jasperreports.view.JasperViewer.class);
    }
}

