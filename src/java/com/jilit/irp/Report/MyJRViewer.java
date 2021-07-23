/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jilit.irp.Report;

import com.jilit.irp.persistence.dao.DAOFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRImageMapRenderer;
import net.sf.jasperreports.engine.JRPrintAnchorIndex;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintImageAreaHyperlink;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.print.JRPrinterAWT;
import net.sf.jasperreports.engine.util.JRClassLoader;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;
import net.sf.jasperreports.view.JRHyperlinkListener;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRPrintSaveContributor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;


// Referenced classes of package net.sf.jasperreports.view:
//            JRSaveContributor, JRHyperlinkListener

public class MyJRViewer extends JPanel
    implements JRHyperlinkListener,ServletContextAware,ApplicationContextAware
{
     @Autowired
     DAOFactory daoFactory;
      private ServletContext context;
       private ApplicationContext applicationContext;

       public void setServletContext(ServletContext context) {
        this.context = context;
    }
      public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

     
    class PageRenderer extends JLabel
    {

        private static final long serialVersionUID = 10200L;
        private boolean renderImage;
        MyJRViewer viewer;
       

        public void paintComponent(Graphics g)
        {
            if(isRenderImage())
            {
                super.paintComponent(g);
            } else
            {
                viewer.paintPage((Graphics2D)g.create());
            }
        }

        public boolean isRenderImage()
        {
            return renderImage;
        }

        public void setRenderImage(boolean renderImage)
        {
            this.renderImage = renderImage;
        }

        public PageRenderer(MyJRViewer viewer)
        {
            super();
            this.viewer = null;
            this.viewer = viewer;
        }
    }

    protected class ImageMapPanel extends JPanel
        implements MouseListener, MouseMotionListener
    {

        private static final long serialVersionUID = 10200L;
        protected final java.util.List imageAreaHyperlinks;

        public String getToolTipText(MouseEvent event)
        {
            String tooltip = null;
            JRPrintImageAreaHyperlink imageMapArea = getImageMapArea(event);
            if(imageMapArea != null)
            {
                tooltip = getHyperlinkTooltip(imageMapArea.getHyperlink());
            }
            if(tooltip == null)
            {
                tooltip = super.getToolTipText(event);
            }
            return tooltip;
        }

        public void mouseDragged(MouseEvent e)
        {
            pnlLinksMouseDragged(e);
        }

        public void mouseMoved(MouseEvent e)
        {
            JRPrintImageAreaHyperlink imageArea = getImageMapArea(e);
            if(imageArea != null && imageArea.getHyperlink().getHyperlinkType() != 1)
            {
                e.getComponent().setCursor(Cursor.getPredefinedCursor(12));
            } else
            {
                e.getComponent().setCursor(Cursor.getDefaultCursor());
            }
        }

        protected JRPrintImageAreaHyperlink getImageMapArea(MouseEvent e)
        {
            return getImageMapArea((int)((float)e.getX() / realZoom), (int)((float)e.getY() / realZoom));
        }

        protected JRPrintImageAreaHyperlink getImageMapArea(int x, int y)
        {
            JRPrintImageAreaHyperlink image = null;
            if(imageAreaHyperlinks != null)
            {
                ListIterator it = imageAreaHyperlinks.listIterator(imageAreaHyperlinks.size());
                do
                {
                    if(image != null || !it.hasPrevious())
                    {
                        break;
                    }
                    JRPrintImageAreaHyperlink area = (JRPrintImageAreaHyperlink)it.previous();
                    if(area.getArea().containsPoint(x, y))
                    {
                        image = area;
                    }
                } while(true);
            }
            return image;
        }

        public void mouseClicked(MouseEvent e)
        {
              System.err.println("applicationContext Click:"+applicationContext);
            JRPrintImageAreaHyperlink imageMapArea = getImageMapArea(e);
            if(imageMapArea != null)
            {
                hyperlinkClicked(imageMapArea.getHyperlink());
            }
        }

        public void mouseEntered(MouseEvent mouseevent)
        {
        }

        public void mouseExited(MouseEvent mouseevent)
        {
        }

        public void mousePressed(MouseEvent e)
        {
            e.getComponent().setCursor(Cursor.getPredefinedCursor(13));
            pnlLinksMousePressed(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            e.getComponent().setCursor(Cursor.getDefaultCursor());
            pnlLinksMouseReleased(e);
        }

        public ImageMapPanel(Rectangle renderingArea, JRImageMapRenderer imageMap)
        {
            super();
            try
            {
                imageAreaHyperlinks = imageMap.getImageAreaHyperlinks(renderingArea);
            }
            catch(JRException e)
            {
                throw new JRRuntimeException(e);
            }
            addMouseListener(this);
            addMouseMotionListener(this);
        }
    }


    private static final Log log;
    private static final long serialVersionUID = 10200L;
    public static final String VIEWER_RENDER_BUFFER_MAX_SIZE = "net.sf.jasperreports.viewer.render.buffer.max.size";
    protected static final int TYPE_FILE_NAME = 1;
    protected static final int TYPE_INPUT_STREAM = 2;
    protected static final int TYPE_OBJECT = 3;
    public static final int REPORT_RESOLUTION = 72;
    protected float MIN_ZOOM;
    protected float MAX_ZOOM;
    protected int zooms[] = {
        50, 75, 100, 125, 150, 175, 200, 250, 400, 800
    };
    protected int defaultZoomIndex;
    protected int type;
    protected boolean isXML;
    protected String reportFileName;
    protected SimpleFileResolver fileResolver;
    JasperPrint jasperPrint;
    private int pageIndex;
    private boolean pageError;
    protected float zoom;
    private JRGraphics2DExporter exporter;
    private int screenResolution;
    protected float realZoom;
    private DecimalFormat zoomDecimalFormat;
    private ResourceBundle resourceBundle;
    private int downX;
    private int downY;
    private java.util.List hyperlinkListeners;
    private Map linksMap;
    private MouseListener mouseListener = new MouseAdapter() {

        public void mouseClicked(MouseEvent evt)
        {

            hyperlinkClicked(evt);
        }

            {
                //super();
            }
    };
    protected KeyListener keyNavigationListener = new KeyListener() {

        public void keyTyped(KeyEvent keyevent)
        {
        }

        public void keyPressed(KeyEvent evt)
        {
            keyNavigate(evt);
        }

        public void keyReleased(KeyEvent keyevent)
        {
        }


            {
                //super();
            }
    };
    protected java.util.List saveContributors;
    protected File lastFolder;
    protected JRSaveContributor lastSaveContributor;
    protected JToggleButton btnActualSize;
    protected JButton btnFirst;
    protected JToggleButton btnFitPage;
    protected JToggleButton btnFitWidth;
    protected JButton btnLast;
    protected JButton btnNext;
    protected JButton btnPrevious;
    protected JButton btnPrint;
    protected JButton btnReload;
    protected JButton btnSave;
    protected JButton btnZoomIn;
    protected JButton btnZoomOut;
    protected JComboBox cmbZoom;
    private JLabel jLabel1;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JPanel jPanel8;
    private JPanel jPanel9;
    private PageRenderer lblPage;
    protected JLabel lblStatus;
    private JPanel pnlInScroll;
    private JPanel pnlLinks;
    private JPanel pnlMain;
    private JPanel pnlPage;
    protected JPanel pnlSep01;
    protected JPanel pnlSep02;
    protected JPanel pnlSep03;
    protected JPanel pnlStatus;
    private JScrollPane scrollPane;
    protected JPanel tlbToolBar;
    protected JTextField txtGoTo;

    public MyJRViewer(String fileName, boolean isXML)
        throws JRException
    {
        this(fileName, isXML, ((Locale) (null)));
    }

    public MyJRViewer(InputStream is, boolean isXML)
        throws JRException
    {
        this(is, isXML, ((Locale) (null)));
    }

    public MyJRViewer(JasperPrint jrPrint)
    {
        this(jrPrint, ((Locale) (null)));
    }

    public MyJRViewer(String fileName, boolean isXML, Locale locale)
        throws JRException
    {
        this(fileName, isXML, locale, null);
    }

    public MyJRViewer(InputStream is, boolean isXML, Locale locale)
        throws JRException
    {
        this(is, isXML, locale, null);
    }

    public MyJRViewer(JasperPrint jrPrint, Locale locale)
    {
        this(jrPrint, locale, ((ResourceBundle) (null)));
    }

    public MyJRViewer(String fileName, boolean isXML, Locale locale, ResourceBundle resBundle)
        throws JRException
    {
        MIN_ZOOM = 0.5F;
        MAX_ZOOM = 10F;
        defaultZoomIndex = 2;
        type = 1;
        this.isXML = false;
        reportFileName = null;
        fileResolver = null;
        jasperPrint = null;
        pageIndex = 0;
        zoom = 0.0F;
        exporter = null;
        screenResolution = 72;
        realZoom = 0.0F;
        zoomDecimalFormat = new DecimalFormat("#.##");
        resourceBundle = null;
        downX = 0;
        downY = 0;
        hyperlinkListeners = new ArrayList();
        linksMap = new HashMap();
        saveContributors = new ArrayList();
        lastFolder = null;
        lastSaveContributor = null;
        initResources(locale, resBundle);
        setScreenDetails();
        setZooms();
        initComponents();
        loadReport(fileName, isXML);
        cmbZoom.setSelectedIndex(defaultZoomIndex);
        initSaveContributors();
        addHyperlinkListener(this);
    }

    public MyJRViewer(InputStream is, boolean isXML, Locale locale, ResourceBundle resBundle)
        throws JRException
    {
        MIN_ZOOM = 0.5F;
        MAX_ZOOM = 10F;
        defaultZoomIndex = 2;
        type = 1;
        this.isXML = false;
        reportFileName = null;
        fileResolver = null;
        jasperPrint = null;
        pageIndex = 0;
        zoom = 0.0F;
        exporter = null;
        screenResolution = 72;
        realZoom = 0.0F;
        zoomDecimalFormat = new DecimalFormat("#.##");
        resourceBundle = null;
        downX = 0;
        downY = 0;
        hyperlinkListeners = new ArrayList();
        linksMap = new HashMap();
        saveContributors = new ArrayList();
        lastFolder = null;
        lastSaveContributor = null;
        initResources(locale, resBundle);
        setScreenDetails();
        setZooms();
        initComponents();
        loadReport(is, isXML);
        cmbZoom.setSelectedIndex(defaultZoomIndex);
        initSaveContributors();
        addHyperlinkListener(this);
    }

    public MyJRViewer(JasperPrint jrPrint, Locale locale, ResourceBundle resBundle)
    {
        MIN_ZOOM = 0.5F;
        MAX_ZOOM = 10F;
        defaultZoomIndex = 2;
        type = 1;
        isXML = false;
        reportFileName = null;
        fileResolver = null;
        jasperPrint = null;
        pageIndex = 0;
        zoom = 0.0F;
        exporter = null;
        screenResolution = 72;
        realZoom = 0.0F;
        zoomDecimalFormat = new DecimalFormat("#.##");
        resourceBundle = null;
        downX = 0;
        downY = 0;
        hyperlinkListeners = new ArrayList();
        linksMap = new HashMap();
        saveContributors = new ArrayList();
        lastFolder = null;
        lastSaveContributor = null;
        initResources(locale, resBundle);
        setScreenDetails();
        setZooms();
        initComponents();
        loadReport(jrPrint);
        cmbZoom.setSelectedIndex(defaultZoomIndex);
        initSaveContributors();
        addHyperlinkListener(this);
    }

    private void setScreenDetails()
    {
        screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
    }

    public void clear()
    {
        emptyContainer(this);
        jasperPrint = null;
    }

    protected void setZooms()
    {
    }

    public void addSaveContributor(JRSaveContributor contributor)
    {
        saveContributors.add(contributor);
    }

    public void removeSaveContributor(JRSaveContributor contributor)
    {
        saveContributors.remove(contributor);
    }

    public JRSaveContributor[] getSaveContributors()
    {
        return (JRSaveContributor[])(JRSaveContributor[])saveContributors.toArray(new JRSaveContributor[saveContributors.size()]);
    }

    public void setSaveContributors(JRSaveContributor saveContributors[])
    {
        this.saveContributors = new ArrayList();
        if(saveContributors != null)
        {
            this.saveContributors.addAll(Arrays.asList(saveContributors));
        }
    }

    public void addHyperlinkListener(JRHyperlinkListener listener)
    {
        hyperlinkListeners.add(listener);
    }

    public void removeHyperlinkListener(JRHyperlinkListener listener)
    {
        hyperlinkListeners.remove(listener);
    }

    public JRHyperlinkListener[] getHyperlinkListeners()
    {
        return (JRHyperlinkListener[])(JRHyperlinkListener[])hyperlinkListeners.toArray(new JRHyperlinkListener[hyperlinkListeners.size()]);
    }

    protected void initResources(Locale locale, ResourceBundle resBundle)
    {
        if(locale != null)
        {
            setLocale(locale);
        } else
        {
            setLocale(Locale.getDefault());
        }
        if(resBundle == null)
        {
            resourceBundle = ResourceBundle.getBundle("net/sf/jasperreports/view/viewer", getLocale());
        } else
        {
            resourceBundle = resBundle;
        }
    }

    protected String getBundleString(String key)
    {
        return resourceBundle.getString(key);
    }

    protected void initSaveContributors()
    {
        String DEFAULT_CONTRIBUTORS[] = {
            "net.sf.jasperreports.view.save.JRPrintSaveContributor", "net.sf.jasperreports.view.save.JRPdfSaveContributor", "net.sf.jasperreports.view.save.JRRtfSaveContributor", "net.sf.jasperreports.view.save.JROdtSaveContributor", "net.sf.jasperreports.view.save.JRHtmlSaveContributor", "net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor", "net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor", "net.sf.jasperreports.view.save.JRCsvSaveContributor", "net.sf.jasperreports.view.save.JRXmlSaveContributor", "net.sf.jasperreports.view.save.JREmbeddedImagesXmlSaveContributor"
        };
        for(int i = 0; i < DEFAULT_CONTRIBUTORS.length; i++)
        {
            try
            {
                Class saveContribClass = JRClassLoader.loadClassForName(DEFAULT_CONTRIBUTORS[i]);
                Constructor constructor = saveContribClass.getConstructor(new Class[] {
                    java.util.Locale.class, java.util.ResourceBundle.class
                });
                JRSaveContributor saveContrib = (JRSaveContributor)constructor.newInstance(new Object[] {
                    getLocale(), resourceBundle
                });
                saveContributors.add(saveContrib);
            }
            catch(Exception e) { }
        }

    }


    public void invoke(String aClass, String aMethod, Class[] params, Object[] args) {
        try {
            Class c = Class.forName(aClass);           
            Method m = c.getDeclaredMethod(aMethod, params);
            Object i = c.newInstance();
            Object r = m.invoke(i, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoHyperlink(JRPrintHyperlink hyperlink)
    {
        switch(hyperlink.getHyperlinkType())
        {
        case 1: // '\001'
        default:
            break;

        case 2: // '\002'
        {
            if(isOnlyHyperlinkListener())
            {
                System.out.println("Hyperlink reference2 : " + hyperlink.getHyperlinkReference());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
            /* Generate HyperLink Report*/
            try {

            String path = hyperlink.getHyperlinkReference();
                System.err.println("PATH::::::::"+path);
             if(path.split("&")[0].contains("StudentRegistrationSummary")){
            //student Registration Summary  --SUBRATA
            String classname ="com.jilit.irp.bso.report."+path.split("&")[0];
            String methodname = path.split("&")[1];
            String title=path.split("&")[2];           
             String Programid=path.split("&")[3];
             String Branchid=path.split("&")[4];
             byte stynumber=new Byte(path.split("&")[5]);
             String Registrationid =path.split("&")[6];
             String Registrationcode=path.split("&")[7];
             String Branchcode = path.split("&")[8];
             String Programcode=path.split("&")[9];
             String Parameter = path.split("&")[10];

                System.err.println("PATRAMETER:::"+Parameter);
                
            invoke(classname,methodname,new Class[]{String.class,String.class,String.class,byte.class,String.class,String.class,String.class,String.class,String.class},new Object[]{Programid,Branchid,Registrationid,stynumber,title,Registrationcode,Branchcode,Programcode,Parameter});
            // END Student Registration Summary    ---SUBRATA

            //START OF Student Class Attendance VIJAY
             }else if(path.split("&")[0].contains("StudentAttendanceService")){
                    String classname ="com.jilit.irp.bso.sis."+path.split("&")[0];
                    String methodname = path.split("&")[1];
                    String title=path.split("&")[2];
                    String studentid=path.split("&")[3];
                    String fstid=path.split("&")[4];
                    String attendancetype =path.split("&")[5];
                    String enrollmentno = path.split("&")[6];
                    String name = path.split("&")[7];
                    String subjectcode = path.split("&")[8];
                    String subjectcomponentcode = path.split("&")[9];
            invoke(classname,methodname,new Class[]{String.class,String.class,String.class,String.class,String.class,String.class,String.class,String.class},new Object[]{studentid,fstid,attendancetype,title,enrollmentno,name,subjectcode,subjectcomponentcode});

             }
            //END OF Student Class Attendance VIJAY
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
            break;
        }

        case 3: // '\003'
        {
            if(hyperlink.getHyperlinkAnchor() == null)
            {
                break;
            }
            Map anchorIndexes = jasperPrint.getAnchorIndexes();
            JRPrintAnchorIndex anchorIndex = (JRPrintAnchorIndex)anchorIndexes.get(hyperlink.getHyperlinkAnchor());
            if(anchorIndex.getPageIndex() != pageIndex)
            {
                setPageIndex(anchorIndex.getPageIndex());
                refreshPage();
            }
            Container container = pnlInScroll.getParent();
            if(!(container instanceof JViewport))
            {
                break;
            }
            JViewport viewport = (JViewport)container;
            int newX = (int)((float)anchorIndex.getElementAbsoluteX() * realZoom);
            int newY = (int)((float)anchorIndex.getElementAbsoluteY() * realZoom);
            int maxX = pnlInScroll.getWidth() - viewport.getWidth();
            int maxY = pnlInScroll.getHeight() - viewport.getHeight();
            if(newX < 0)
            {
                newX = 0;
            }
            if(newX > maxX)
            {
                newX = maxX;
            }
            if(newY < 0)
            {
                newY = 0;
            }
            if(newY > maxY)
            {
                newY = maxY;
            }
            viewport.setViewPosition(new Point(newX, newY));
            break;
        }

        case 4: // '\004'
        {
            int page = pageIndex + 1;
            if(hyperlink.getHyperlinkPage() != null)
            {
                page = hyperlink.getHyperlinkPage().intValue();
            }
            if(page < 1 || page > jasperPrint.getPages().size() || page == pageIndex + 1)
            {
                break;
            }
            setPageIndex(page - 1);
            refreshPage();
            Container container = pnlInScroll.getParent();
            if(container instanceof JViewport)
            {
                JViewport viewport = (JViewport)container;
                viewport.setViewPosition(new Point(0, 0));
            }
            break;
        }

        case 5: // '\005'
        {
            if(isOnlyHyperlinkListener())
            {
                System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
                System.out.println("Hyperlink anchor    : " + hyperlink.getHyperlinkAnchor());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
            }
            break;
        }

        case 6: // '\006'
        {
            if(isOnlyHyperlinkListener())
            {
                System.out.println("Hyperlink reference : " + hyperlink.getHyperlinkReference());
                System.out.println("Hyperlink page      : " + hyperlink.getHyperlinkPage());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
            }
            break;
        }

        case 7: // '\007'
        {
            if(isOnlyHyperlinkListener())
            {
                System.out.println("Hyperlink of type3 " + hyperlink.getLinkType());
                System.out.println("Implement your own JRHyperlinkListener to manage this type of event.");
            }
            break;
        }
        }
    }

    protected boolean isOnlyHyperlinkListener()
    {
        int listenerCount;
        if(hyperlinkListeners == null)
        {
            listenerCount = 0;
        } else
        {
            listenerCount = hyperlinkListeners.size();
            if(hyperlinkListeners.contains(this))
            {
                listenerCount--;
            }
        }
        return listenerCount == 0;
    }

    private void initComponents()
    {
        tlbToolBar = new JPanel();
        btnSave = new JButton();
        btnPrint = new JButton();
        btnReload = new JButton();
        pnlSep01 = new JPanel();
        btnFirst = new JButton();
        btnPrevious = new JButton();
        btnNext = new JButton();
        btnLast = new JButton();
        txtGoTo = new JTextField();
        pnlSep02 = new JPanel();
        btnActualSize = new JToggleButton();
        btnFitPage = new JToggleButton();
        btnFitWidth = new JToggleButton();
        pnlSep03 = new JPanel();
        btnZoomIn = new JButton();
        btnZoomOut = new JButton();
        cmbZoom = new JComboBox();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for(int i = 0; i < zooms.length; i++)
        {
            model.addElement("" + zooms[i] + "%");
        }

        cmbZoom.setModel(model);
        pnlMain = new JPanel();
        scrollPane = new JScrollPane();
        scrollPane.getHorizontalScrollBar().setUnitIncrement(5);
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        pnlInScroll = new JPanel();
        pnlPage = new JPanel();
        jPanel4 = new JPanel();
        pnlLinks = new JPanel();
        jPanel5 = new JPanel();
        jPanel6 = new JPanel();
        jPanel7 = new JPanel();
        jPanel8 = new JPanel();
        jLabel1 = new JLabel();
        jPanel9 = new JPanel();
        lblPage = new PageRenderer(this);
        pnlStatus = new JPanel();
        lblStatus = new JLabel();
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(450, 150));
        setPreferredSize(new Dimension(450, 150));
        tlbToolBar.setLayout(new FlowLayout(0, 0, 2));
        btnSave.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/save.GIF")));
        btnSave.setToolTipText(getBundleString("save"));
        btnSave.setMargin(new Insets(2, 2, 2, 2));
        btnSave.setMaximumSize(new Dimension(23, 23));
        btnSave.setMinimumSize(new Dimension(23, 23));
        btnSave.setPreferredSize(new Dimension(23, 23));
        btnSave.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnSave.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnSave);
        btnPrint.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/print.GIF")));
        btnPrint.setToolTipText(getBundleString("print"));
        btnPrint.setMargin(new Insets(2, 2, 2, 2));
        btnPrint.setMaximumSize(new Dimension(23, 23));
        btnPrint.setMinimumSize(new Dimension(23, 23));
        btnPrint.setPreferredSize(new Dimension(23, 23));
        btnPrint.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnPrintActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnPrint.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnPrint);
        btnReload.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/reload.GIF")));
        btnReload.setToolTipText(getBundleString("reload"));
        btnReload.setMargin(new Insets(2, 2, 2, 2));
        btnReload.setMaximumSize(new Dimension(23, 23));
        btnReload.setMinimumSize(new Dimension(23, 23));
        btnReload.setPreferredSize(new Dimension(23, 23));
        btnReload.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnReloadActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnReload.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnReload);
        pnlSep01.setMaximumSize(new Dimension(10, 10));
        tlbToolBar.add(pnlSep01);
        btnFirst.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/first.GIF")));
        btnFirst.setToolTipText(getBundleString("first.page"));
        btnFirst.setMargin(new Insets(2, 2, 2, 2));
        btnFirst.setMaximumSize(new Dimension(23, 23));
        btnFirst.setMinimumSize(new Dimension(23, 23));
        btnFirst.setPreferredSize(new Dimension(23, 23));
        btnFirst.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnFirstActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnFirst.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnFirst);
        btnPrevious.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/previous.GIF")));
        btnPrevious.setToolTipText(getBundleString("previous.page"));
        btnPrevious.setMargin(new Insets(2, 2, 2, 2));
        btnPrevious.setMaximumSize(new Dimension(23, 23));
        btnPrevious.setMinimumSize(new Dimension(23, 23));
        btnPrevious.setPreferredSize(new Dimension(23, 23));
        btnPrevious.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnPreviousActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnPrevious.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnPrevious);
        btnNext.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/next.GIF")));
        btnNext.setToolTipText(getBundleString("next.page"));
        btnNext.setMargin(new Insets(2, 2, 2, 2));
        btnNext.setMaximumSize(new Dimension(23, 23));
        btnNext.setMinimumSize(new Dimension(23, 23));
        btnNext.setPreferredSize(new Dimension(23, 23));
        btnNext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnNextActionPerformed(evt);
            }


            {
                //super();
            }
        });
        btnNext.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnNext);
        btnLast.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/last.GIF")));
        btnLast.setToolTipText(getBundleString("last.page"));
        btnLast.setMargin(new Insets(2, 2, 2, 2));
        btnLast.setMaximumSize(new Dimension(23, 23));
        btnLast.setMinimumSize(new Dimension(23, 23));
        btnLast.setPreferredSize(new Dimension(23, 23));
        btnLast.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnLastActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnLast.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnLast);
        txtGoTo.setToolTipText(getBundleString("go.to.page"));
        txtGoTo.setMaximumSize(new Dimension(40, 23));
        txtGoTo.setMinimumSize(new Dimension(40, 23));
        txtGoTo.setPreferredSize(new Dimension(40, 23));
        txtGoTo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                txtGoToActionPerformed(evt);
            }


            {
               // super();
            }
        });
        txtGoTo.addKeyListener(keyNavigationListener);
        tlbToolBar.add(txtGoTo);
        pnlSep02.setMaximumSize(new Dimension(10, 10));
        tlbToolBar.add(pnlSep02);
        btnActualSize.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/actualsize.GIF")));
        btnActualSize.setToolTipText(getBundleString("actual.size"));
        btnActualSize.setMargin(new Insets(2, 2, 2, 2));
        btnActualSize.setMaximumSize(new Dimension(23, 23));
        btnActualSize.setMinimumSize(new Dimension(23, 23));
        btnActualSize.setPreferredSize(new Dimension(23, 23));
        btnActualSize.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnActualSizeActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnActualSize.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnActualSize);
        btnFitPage.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitpage.GIF")));
        btnFitPage.setToolTipText(getBundleString("fit.page"));
        btnFitPage.setMargin(new Insets(2, 2, 2, 2));
        btnFitPage.setMaximumSize(new Dimension(23, 23));
        btnFitPage.setMinimumSize(new Dimension(23, 23));
        btnFitPage.setPreferredSize(new Dimension(23, 23));
        btnFitPage.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnFitPageActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnFitPage.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnFitPage);
        btnFitWidth.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/fitwidth.GIF")));
        btnFitWidth.setToolTipText(getBundleString("fit.width"));
        btnFitWidth.setMargin(new Insets(2, 2, 2, 2));
        btnFitWidth.setMaximumSize(new Dimension(23, 23));
        btnFitWidth.setMinimumSize(new Dimension(23, 23));
        btnFitWidth.setPreferredSize(new Dimension(23, 23));
        btnFitWidth.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnFitWidthActionPerformed(evt);
            }


            {
                //super();
            }
        });
        btnFitWidth.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnFitWidth);
        pnlSep03.setMaximumSize(new Dimension(10, 10));
        tlbToolBar.add(pnlSep03);
        btnZoomIn.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomin.GIF")));
        btnZoomIn.setToolTipText(getBundleString("zoom.in"));
        btnZoomIn.setMargin(new Insets(2, 2, 2, 2));
        btnZoomIn.setMaximumSize(new Dimension(23, 23));
        btnZoomIn.setMinimumSize(new Dimension(23, 23));
        btnZoomIn.setPreferredSize(new Dimension(23, 23));
        btnZoomIn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnZoomInActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnZoomIn.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnZoomIn);
        btnZoomOut.setIcon(new ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/zoomout.GIF")));
        btnZoomOut.setToolTipText(getBundleString("zoom.out"));
        btnZoomOut.setMargin(new Insets(2, 2, 2, 2));
        btnZoomOut.setMaximumSize(new Dimension(23, 23));
        btnZoomOut.setMinimumSize(new Dimension(23, 23));
        btnZoomOut.setPreferredSize(new Dimension(23, 23));
        btnZoomOut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                btnZoomOutActionPerformed(evt);
            }


            {
               // super();
            }
        });
        btnZoomOut.addKeyListener(keyNavigationListener);
        tlbToolBar.add(btnZoomOut);
        cmbZoom.setEditable(true);
        cmbZoom.setToolTipText(getBundleString("zoom.ratio"));
        cmbZoom.setMaximumSize(new Dimension(80, 23));
        cmbZoom.setMinimumSize(new Dimension(80, 23));
        cmbZoom.setPreferredSize(new Dimension(80, 23));
        cmbZoom.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                cmbZoomActionPerformed(evt);
            }


            {
               // super();
            }
        });
        cmbZoom.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent evt)
            {
                cmbZoomItemStateChanged(evt);
            }


            {
               // super();
            }
        });
        cmbZoom.addKeyListener(keyNavigationListener);
        tlbToolBar.add(cmbZoom);
        add(tlbToolBar, "North");
        pnlMain.setLayout(new BorderLayout());
        pnlMain.addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent evt)
            {
                pnlMainComponentResized(evt);
            }


            {
                //super();
            }
        });
        scrollPane.setHorizontalScrollBarPolicy(32);
        scrollPane.setVerticalScrollBarPolicy(22);
        pnlInScroll.setLayout(new GridBagLayout());
        pnlPage.setLayout(new BorderLayout());
        pnlPage.setMinimumSize(new Dimension(100, 100));
        pnlPage.setPreferredSize(new Dimension(100, 100));
        jPanel4.setLayout(new GridBagLayout());
        jPanel4.setMinimumSize(new Dimension(100, 120));
        jPanel4.setPreferredSize(new Dimension(100, 120));
        pnlLinks.setLayout(null);
        pnlLinks.setMinimumSize(new Dimension(5, 5));
        pnlLinks.setPreferredSize(new Dimension(5, 5));
        pnlLinks.setOpaque(false);
        pnlLinks.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt)
            {
                pnlLinksMousePressed(evt);
            }

            public void mouseReleased(MouseEvent evt)
            {
                pnlLinksMouseReleased(evt);
            }


            {
                //super();
            }
        });
        pnlLinks.addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseDragged(MouseEvent evt)
            {
                pnlLinksMouseDragged(evt);
            }


            {
                //super();
            }
        });
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = 1;
        jPanel4.add(pnlLinks, gridBagConstraints);
        jPanel5.setBackground(Color.gray);
        jPanel5.setMinimumSize(new Dimension(5, 5));
        jPanel5.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 3;
        jPanel4.add(jPanel5, gridBagConstraints);
        jPanel6.setMinimumSize(new Dimension(5, 5));
        jPanel6.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jPanel6, gridBagConstraints);
        jPanel7.setBackground(Color.gray);
        jPanel7.setMinimumSize(new Dimension(5, 5));
        jPanel7.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 2;
        jPanel4.add(jPanel7, gridBagConstraints);
        jPanel8.setBackground(Color.gray);
        jPanel8.setMinimumSize(new Dimension(5, 5));
        jPanel8.setPreferredSize(new Dimension(5, 5));
        jLabel1.setText("jLabel1");
        jPanel8.add(jLabel1);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel4.add(jPanel8, gridBagConstraints);
        jPanel9.setMinimumSize(new Dimension(5, 5));
        jPanel9.setPreferredSize(new Dimension(5, 5));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jPanel9, gridBagConstraints);
        lblPage.setBackground(Color.white);
        lblPage.setBorder(new LineBorder(new Color(0, 0, 0)));
        lblPage.setOpaque(true);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = 1;
        gridBagConstraints.weightx = 1.0D;
        gridBagConstraints.weighty = 1.0D;
        jPanel4.add(lblPage, gridBagConstraints);
        pnlPage.add(jPanel4, "Center");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        pnlInScroll.add(pnlPage, gridBagConstraints);
        scrollPane.setViewportView(pnlInScroll);
        pnlMain.add(scrollPane, "Center");
        add(pnlMain, "Center");
        pnlStatus.setLayout(new FlowLayout(1, 0, 0));
        lblStatus.setFont(new Font("Dialog", 1, 10));
        lblStatus.setText("Page i of n");
        pnlStatus.add(lblStatus);
        add(pnlStatus, "South");
        addKeyListener(keyNavigationListener);
    }

    void txtGoToActionPerformed(ActionEvent evt)
    {
        try
        {
            int pageNumber = Integer.parseInt(txtGoTo.getText());
            if(pageNumber != pageIndex + 1 && pageNumber > 0 && pageNumber <= jasperPrint.getPages().size())
            {
                setPageIndex(pageNumber - 1);
                refreshPage();
            }
        }
        catch(NumberFormatException e) { }
    }

    void cmbZoomItemStateChanged(ItemEvent evt)
    {
        btnActualSize.setSelected(false);
        btnFitPage.setSelected(false);
        btnFitWidth.setSelected(false);
    }

    void pnlMainComponentResized(ComponentEvent evt)
    {
        if(btnFitPage.isSelected())
        {
            fitPage();
            btnFitPage.setSelected(true);
        } else
        if(btnFitWidth.isSelected())
        {
            setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getWidth() - 20F) / (float)jasperPrint.getPageWidth());
            btnFitWidth.setSelected(true);
        }
    }

    void btnActualSizeActionPerformed(ActionEvent evt)
    {
        if(btnActualSize.isSelected())
        {
            btnFitPage.setSelected(false);
            btnFitWidth.setSelected(false);
            cmbZoom.setSelectedIndex(-1);
            setZoomRatio(1.0F);
            btnActualSize.setSelected(true);
        }
    }

    void btnFitWidthActionPerformed(ActionEvent evt)
    {
        if(btnFitWidth.isSelected())
        {
            btnActualSize.setSelected(false);
            btnFitPage.setSelected(false);
            cmbZoom.setSelectedIndex(-1);
            setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getWidth() - 20F) / (float)jasperPrint.getPageWidth());
            btnFitWidth.setSelected(true);
        }
    }

    void btnFitPageActionPerformed(ActionEvent evt)
    {
        if(btnFitPage.isSelected())
        {
            btnActualSize.setSelected(false);
            btnFitWidth.setSelected(false);
            cmbZoom.setSelectedIndex(-1);
            fitPage();
            btnFitPage.setSelected(true);
        }
    }

    void btnSaveActionPerformed(ActionEvent evt)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setLocale(getLocale());
        fileChooser.updateUI();
        for(int i = 0; i < saveContributors.size(); i++)
        {
            fileChooser.addChoosableFileFilter((JRSaveContributor)saveContributors.get(i));
        }

        if(saveContributors.contains(lastSaveContributor))
        {
            fileChooser.setFileFilter(lastSaveContributor);
        } else
        if(saveContributors.size() > 0)
        {
            fileChooser.setFileFilter((JRSaveContributor)saveContributors.get(0));
        }
        if(lastFolder != null)
        {
            fileChooser.setCurrentDirectory(lastFolder);
        }
        int retValue = fileChooser.showSaveDialog(this);
        if(retValue == 0)
        {
            javax.swing.filechooser.FileFilter fileFilter = fileChooser.getFileFilter();
            File file = fileChooser.getSelectedFile();
            lastFolder = file.getParentFile();
            JRSaveContributor contributor = null;
            if(fileFilter instanceof JRSaveContributor)
            {
                contributor = (JRSaveContributor)fileFilter;
            } else
            {
                int i = 0;
                do
                {
                    if(contributor != null || i >= saveContributors.size())
                    {
                        break;
                    }
                    contributor = (JRSaveContributor)saveContributors.get(i++);
                    if(!contributor.accept(file))
                    {
                        contributor = null;
                    }
                } while(true);
                if(contributor == null)
                {
                    contributor = new JRPrintSaveContributor(getLocale(), resourceBundle);
            }
            }
            lastSaveContributor = contributor;
            try
            {
                contributor.save(jasperPrint, file);
            }
            catch(JRException e)
            {
                if(log.isErrorEnabled())
                {
                    log.error("Save error.", e);
                }
                JOptionPane.showMessageDialog(this, getBundleString("error.saving"));
            }
        }
    }

    void pnlLinksMouseDragged(MouseEvent evt)
    {
        Container container = pnlInScroll.getParent();
        if(container instanceof JViewport)
        {
            JViewport viewport = (JViewport)container;
            Point point = viewport.getViewPosition();
            int newX = point.x - (evt.getX() - downX);
            int newY = point.y - (evt.getY() - downY);
            int maxX = pnlInScroll.getWidth() - viewport.getWidth();
            int maxY = pnlInScroll.getHeight() - viewport.getHeight();
            if(newX < 0)
            {
                newX = 0;
            }
            if(newX > maxX)
            {
                newX = maxX;
            }
            if(newY < 0)
            {
                newY = 0;
            }
            if(newY > maxY)
            {
                newY = maxY;
            }
            viewport.setViewPosition(new Point(newX, newY));
        }
    }

    void pnlLinksMouseReleased(MouseEvent evt)
    {
        pnlLinks.setCursor(new Cursor(0));
    }

    void pnlLinksMousePressed(MouseEvent evt)
    {
        pnlLinks.setCursor(new Cursor(13));
        downX = evt.getX();
        downY = evt.getY();
    }

    void btnPrintActionPerformed(ActionEvent evt)
    {
        Thread thread = new Thread(new Runnable() {

            public void run()
            {
                try {
                    btnPrint.setEnabled(false);
                    setCursor(Cursor.getPredefinedCursor(3));
                    JasperPrintManager.printReport(jasperPrint, true);
                    setCursor(Cursor.getPredefinedCursor(0));
                    btnPrint.setEnabled(true);
                    // break MISSING_BLOCK_LABEL_148;
                    Exception ex;
                    //ex;
                    if (MyJRViewer.log.isErrorEnabled()) {
                       // MyJRViewer.log.error("Print error.", ex);
                    }
                    JOptionPane.showMessageDialog(MyJRViewer.this, getBundleString("error.printing"));
                    setCursor(Cursor.getPredefinedCursor(0));
                    btnPrint.setEnabled(true);
                    // break MISSING_BLOCK_LABEL_148;
                    Exception exception;
                    // exception;
                    setCursor(Cursor.getPredefinedCursor(0));
                    btnPrint.setEnabled(true);
                    //throw exception;
                } catch (JRException ex) {
                    Logger.getLogger(MyJRViewer.class.getName()).log(Level.SEVERE, null, ex);
                }
                //throw exception;
            }


            {
                //super();
            }
        });
        thread.start();
    }

    void btnLastActionPerformed(ActionEvent evt)
    {
        setPageIndex(jasperPrint.getPages().size() - 1);
        refreshPage();
    }

    void btnNextActionPerformed(ActionEvent evt)
    {
        setPageIndex(pageIndex + 1);
        refreshPage();
    }

    void btnPreviousActionPerformed(ActionEvent evt)
    {
        setPageIndex(pageIndex - 1);
        refreshPage();
    }

    void btnFirstActionPerformed(ActionEvent evt)
    {
        setPageIndex(0);
        refreshPage();
    }

    void btnReloadActionPerformed(ActionEvent evt)
    {
        if(type == 1)
        {
            try
            {
                loadReport(reportFileName, isXML);
            }
            catch(JRException e)
            {
                if(log.isErrorEnabled())
                {
                    log.error("Reload error.", e);
                }
                jasperPrint = null;
                setPageIndex(0);
                refreshPage();
                JOptionPane.showMessageDialog(this, getBundleString("error.loading"));
            }
            forceRefresh();
        }
    }

    protected void forceRefresh()
    {
        zoom = 0.0F;
        realZoom = 0.0F;
        setZoomRatio(1.0F);
    }

    void btnZoomInActionPerformed(ActionEvent evt)
    {
        btnActualSize.setSelected(false);
        btnFitPage.setSelected(false);
        btnFitWidth.setSelected(false);
        int newZoomInt = (int)(100F * getZoomRatio());
        int index = Arrays.binarySearch(zooms, newZoomInt);
        if(index < 0)
        {
            setZoomRatio((float)zooms[-index - 1] / 100F);
        } else
        if(index < cmbZoom.getModel().getSize() - 1)
        {
            setZoomRatio((float)zooms[index + 1] / 100F);
        }
    }

    void btnZoomOutActionPerformed(ActionEvent evt)
    {
        btnActualSize.setSelected(false);
        btnFitPage.setSelected(false);
        btnFitWidth.setSelected(false);
        int newZoomInt = (int)(100F * getZoomRatio());
        int index = Arrays.binarySearch(zooms, newZoomInt);
        if(index > 0)
        {
            setZoomRatio((float)zooms[index - 1] / 100F);
        } else
        if(index < -1)
        {
            setZoomRatio((float)zooms[-index - 2] / 100F);
        }
    }

    void cmbZoomActionPerformed(ActionEvent evt)
    {
        float newZoom = getZoomRatio();
        if(newZoom < MIN_ZOOM)
        {
            newZoom = MIN_ZOOM;
        }
        if(newZoom > MAX_ZOOM)
        {
            newZoom = MAX_ZOOM;
        }
        setZoomRatio(newZoom);
    }

    void hyperlinkClicked(MouseEvent evt)
    {
        JPanel link = (JPanel)evt.getSource();
        JRPrintHyperlink element = (JRPrintHyperlink)linksMap.get(link);
        hyperlinkClicked(element);
    }

    protected void hyperlinkClicked(JRPrintHyperlink hyperlink)
    {
          System.err.println("applicationContext Cli:"+applicationContext);
        try
        {
            JRHyperlinkListener listener = null;
            for(int i = 0; i < hyperlinkListeners.size(); i++)
            {
                listener = (JRHyperlinkListener)hyperlinkListeners.get(i);
                listener.gotoHyperlink(hyperlink);
            }

        }
        catch(JRException e)
        {
            if(log.isErrorEnabled())
            {
                log.error("Hyperlink click error.", e);
            }
            JOptionPane.showMessageDialog(this, getBundleString("error.hyperlink"));
        }
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    private void setPageIndex(int index)
    {
        if(jasperPrint != null && jasperPrint.getPages() != null && jasperPrint.getPages().size() > 0)
        {
            if(index >= 0 && index < jasperPrint.getPages().size())
            {
                pageIndex = index;
                pageError = false;
                btnFirst.setEnabled(pageIndex > 0);
                btnPrevious.setEnabled(pageIndex > 0);
                btnNext.setEnabled(pageIndex < jasperPrint.getPages().size() - 1);
                btnLast.setEnabled(pageIndex < jasperPrint.getPages().size() - 1);
                txtGoTo.setEnabled(btnFirst.isEnabled() || btnLast.isEnabled());
                txtGoTo.setText("" + (pageIndex + 1));
                lblStatus.setText(MessageFormat.format(getBundleString("page"), new Object[] {
                    new Integer(pageIndex + 1), new Integer(jasperPrint.getPages().size())
                }));
            }
        } else
        {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
            txtGoTo.setEnabled(false);
            txtGoTo.setText("");
            lblStatus.setText("");
        }
    }

    protected void loadReport(String fileName, boolean isXmlReport)
        throws JRException
    {
        if(isXmlReport)
        {
            jasperPrint = JRPrintXmlLoader.load(fileName);
        } else
        {
            jasperPrint = (JasperPrint)JRLoader.loadObject(fileName);
        }
        type = 1;
        isXML = isXmlReport;
        reportFileName = fileName;
        fileResolver = new SimpleFileResolver(Arrays.asList(new File[] {
            (new File(fileName)).getParentFile(), new File(".")
        }));
        fileResolver.setResolveAbsolutePath(true);
        btnReload.setEnabled(true);
        setPageIndex(0);
    }

    protected void loadReport(InputStream is, boolean isXmlReport)
        throws JRException
    {
        if(isXmlReport)
        {
            jasperPrint = JRPrintXmlLoader.load(is);
        } else
        {
            jasperPrint = (JasperPrint)JRLoader.loadObject(is);
        }
        type = 2;
        isXML = isXmlReport;
        btnReload.setEnabled(false);
        setPageIndex(0);
    }

    protected void loadReport(JasperPrint jrPrint)
    {
        jasperPrint = jrPrint;
        type = 3;
        isXML = false;
        btnReload.setEnabled(false);
        setPageIndex(0);
    }

    protected void refreshPage()
    {
        if(jasperPrint == null || jasperPrint.getPages() == null || jasperPrint.getPages().size() == 0)
        {
            pnlPage.setVisible(false);
            btnSave.setEnabled(false);
            btnPrint.setEnabled(false);
            btnActualSize.setEnabled(false);
            btnFitPage.setEnabled(false);
            btnFitWidth.setEnabled(false);
            btnZoomIn.setEnabled(false);
            btnZoomOut.setEnabled(false);
            cmbZoom.setEnabled(false);
            if(jasperPrint != null)
            {
                JOptionPane.showMessageDialog(this, getBundleString("no.pages"));
            }
            return;
        }
        pnlPage.setVisible(true);
        btnSave.setEnabled(true);
        btnPrint.setEnabled(true);
        btnActualSize.setEnabled(true);
        btnFitPage.setEnabled(true);
        btnFitWidth.setEnabled(true);
        btnZoomIn.setEnabled(zoom < MAX_ZOOM);
        btnZoomOut.setEnabled(zoom > MIN_ZOOM);
        cmbZoom.setEnabled(true);
        Dimension dim = new Dimension((int)((float)jasperPrint.getPageWidth() * realZoom) + 8, (int)((float)jasperPrint.getPageHeight() * realZoom) + 8);
        pnlPage.setMaximumSize(dim);
        pnlPage.setMinimumSize(dim);
        pnlPage.setPreferredSize(dim);
        long maxImageSize = JRProperties.getLongProperty("net.sf.jasperreports.viewer.render.buffer.max.size");
        boolean renderImage;
        if(maxImageSize <= 0L)
        {
            renderImage = false;
        } else
        {
            long imageSize = JRPrinterAWT.getImageSize(jasperPrint, realZoom);
            renderImage = imageSize <= maxImageSize;
        }
        lblPage.setRenderImage(renderImage);
        if(renderImage)
        {
            setPageImage();
        }
        pnlLinks.removeAll();
        linksMap = new HashMap();
        createHyperlinks();
        if(!renderImage)
        {
            lblPage.setIcon(null);
            pnlMain.validate();
            pnlMain.repaint();
        }
    }

    protected void setPageImage()
    {
        Image image;
        if(pageError)
        {
            image = getPageErrorImage();
        } else
        {
            try
            {
                image = JasperPrintManager.printPageToImage(jasperPrint, pageIndex, realZoom);
            }
            catch(Exception e)
            {
                if(log.isErrorEnabled())
                {
                    log.error("Print page to image error.", e);
                }
                pageError = true;
                image = getPageErrorImage();
                JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("net/sf/jasperreports/view/viewer").getString("error.displaying"));
            }
        }
        ImageIcon imageIcon = new ImageIcon(image);
        lblPage.setIcon(imageIcon);
    }

    protected Image getPageErrorImage()
    {
        Image image = new BufferedImage((int)((float)jasperPrint.getPageWidth() * realZoom) + 1, (int)((float)jasperPrint.getPageHeight() * realZoom) + 1, 1);
        Graphics2D grx = (Graphics2D)image.getGraphics();
        AffineTransform transform = new AffineTransform();
        transform.scale(realZoom, realZoom);
        grx.transform(transform);
        drawPageError(grx);
        return image;
    }

    protected void createHyperlinks()
    {
        java.util.List pages = jasperPrint.getPages();
        JRPrintPage page = (JRPrintPage)pages.get(pageIndex);
        createHyperlinks(page.getElements(), 0, 0);
    }

    protected void createHyperlinks(java.util.List elements, int offsetX, int offsetY)
    {
        if(elements != null && elements.size() > 0)
        {
            Iterator it = elements.iterator();
            do
            {
                if(!it.hasNext())
                {
                    break;
                }
                JRPrintElement element = (JRPrintElement)it.next();
                JRImageMapRenderer imageMap = null;
                if(element instanceof JRPrintImage)
                {
                    net.sf.jasperreports.engine.JRRenderable renderer = ((JRPrintImage)element).getRenderer();
                    if(renderer instanceof JRImageMapRenderer)
                    {
                        imageMap = (JRImageMapRenderer)renderer;
                        if(!imageMap.hasImageAreaHyperlinks())
                        {
                            imageMap = null;
                    }
                }
                }
                boolean hasImageMap = imageMap != null;
                JRPrintHyperlink hyperlink = null;
                if(element instanceof JRPrintHyperlink)
                {
                    hyperlink = (JRPrintHyperlink)element;
                }
                boolean hasHyperlink = !hasImageMap && hyperlink != null && hyperlink.getHyperlinkType() != 1;
                boolean hasTooltip = hyperlink != null && hyperlink.getHyperlinkTooltip() != null;
                if(hasHyperlink || hasImageMap || hasTooltip)
                {
                    JPanel link;
                    if(hasImageMap)
                    {
                        Rectangle renderingArea = new Rectangle(0, 0, element.getWidth(), element.getHeight());
                        link = new ImageMapPanel(renderingArea, imageMap);
                    } else
                    {
                        link = new JPanel();
                        if(hasHyperlink)
                        {
                            link.addMouseListener(mouseListener);
                        }
                    }
                    if(hasHyperlink)
                    {
                        link.setCursor(new Cursor(12));
                    }
                    link.setLocation((int)((float)(element.getX() + offsetX) * realZoom), (int)((float)(element.getY() + offsetY) * realZoom));
                    link.setSize((int)((float)element.getWidth() * realZoom), (int)((float)element.getHeight() * realZoom));
                    link.setOpaque(false);
                    String toolTip = getHyperlinkTooltip(hyperlink);
                    if(toolTip == null && hasImageMap)
                    {
                        toolTip = "";
                    }
                    link.setToolTipText(toolTip);
                    pnlLinks.add(link);
                    linksMap.put(link, element);
                }
                if(element instanceof JRPrintFrame)
                {
                    JRPrintFrame frame = (JRPrintFrame)element;
                    int frameOffsetX = offsetX + frame.getX() + frame.getLineBox().getLeftPadding().intValue();
                    int frameOffsetY = offsetY + frame.getY() + frame.getLineBox().getTopPadding().intValue();
                    createHyperlinks(frame.getElements(), frameOffsetX, frameOffsetY);
                }
            } while(true);
        }
    }

    protected String getHyperlinkTooltip(JRPrintHyperlink hyperlink)
    {
        String toolTip = hyperlink.getHyperlinkTooltip();
        if(toolTip == null)
        {
            toolTip = getFallbackTooltip(hyperlink);
        }
        return toolTip;
    }

    protected String getFallbackTooltip(JRPrintHyperlink hyperlink)
    {
        String toolTip = null;
        switch(hyperlink.getHyperlinkType())
        {
        default:
            break;

        case 2: // '\002'
            toolTip = hyperlink.getHyperlinkReference();
            break;

        case 3: // '\003'
            if(hyperlink.getHyperlinkAnchor() != null)
            {
                toolTip = "#" + hyperlink.getHyperlinkAnchor();
            }
            break;

        case 4: // '\004'
            if(hyperlink.getHyperlinkPage() != null)
            {
                toolTip = "#page " + hyperlink.getHyperlinkPage();
            }
            break;

        case 5: // '\005'
            toolTip = "";
            if(hyperlink.getHyperlinkReference() != null)
            {
                toolTip = toolTip + hyperlink.getHyperlinkReference();
            }
            if(hyperlink.getHyperlinkAnchor() != null)
            {
                toolTip = toolTip + "#" + hyperlink.getHyperlinkAnchor();
            }
            break;

        case 6: // '\006'
            toolTip = "";
            if(hyperlink.getHyperlinkReference() != null)
            {
                toolTip = toolTip + hyperlink.getHyperlinkReference();
            }
            if(hyperlink.getHyperlinkPage() != null)
            {
                toolTip = toolTip + "#page " + hyperlink.getHyperlinkPage();
            }
            break;
        }
        return toolTip;
    }

    private void emptyContainer(Container container)
    {
        Component components[] = container.getComponents();
        if(components != null)
        {
            for(int i = 0; i < components.length; i++)
            {
                if(components[i] instanceof Container)
                {
                    emptyContainer((Container)components[i]);
                }
            }

        }
        components = null;
        container.removeAll();
        container = null;
    }

    private float getZoomRatio()
    {
        float newZoom = zoom;
        try
        {
            newZoom = zoomDecimalFormat.parse(String.valueOf(cmbZoom.getEditor().getItem())).floatValue() / 100F;
        }
        catch(ParseException e) { }
        return newZoom;
    }

    public void setZoomRatio(float newZoom)
    {
        if(newZoom > 0.0F)
        {
            cmbZoom.getEditor().setItem(zoomDecimalFormat.format(newZoom * 100F) + "%");
            if(zoom != newZoom)
            {
                zoom = newZoom;
                realZoom = (zoom * (float)screenResolution) / 72F;
                refreshPage();
            }
        }
    }

    private void setRealZoomRatio(float newZoom)
    {
        if(newZoom > 0.0F && realZoom != newZoom)
        {
            zoom = (newZoom * 72F) / (float)screenResolution;
            realZoom = newZoom;
            cmbZoom.getEditor().setItem(zoomDecimalFormat.format(zoom * 100F) + "%");
            refreshPage();
        }
    }

    public void setFitWidthZoomRatio()
    {
        setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getWidth() - 20F) / (float)jasperPrint.getPageWidth());
    }

    public void setFitPageZoomRatio()
    {
        setRealZoomRatio(((float)pnlInScroll.getVisibleRect().getHeight() - 20F) / (float)jasperPrint.getPageHeight());
    }

    protected JRGraphics2DExporter getGraphics2DExporter()
        throws JRException
    {
        return new JRGraphics2DExporter();
    }

    protected void paintPage(Graphics2D grx)
    {
        if(pageError)
        {
            paintPageError(grx);
            return;
        }
        try
        {
            if(exporter == null)
            {
                exporter = getGraphics2DExporter();
            } else
            {
                exporter.reset();
            }
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, grx.create());
            exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(pageIndex));
            exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, new Float(realZoom));
            exporter.setParameter(JRExporterParameter.OFFSET_X, new Integer(1));
            exporter.setParameter(JRExporterParameter.OFFSET_Y, new Integer(1));
            if(type == 1)
            {
                exporter.setParameter(JRExporterParameter.FILE_RESOLVER, fileResolver);
            }
            exporter.exportReport();
        }
        catch(Exception e)
        {
            if(log.isErrorEnabled())
            {
                log.error("Page paint error.", e);
            }
            pageError = true;
            paintPageError(grx);
            SwingUtilities.invokeLater(new Runnable() {

                public void run()
                {
                    JOptionPane.showMessageDialog(MyJRViewer.this, getBundleString("error.displaying"));
                }


            {
               // super();
            }
            });
        }
    }

    protected void paintPageError(Graphics2D grx)
    {
        AffineTransform origTransform;
        origTransform = grx.getTransform();
        AffineTransform transform = new AffineTransform();
        transform.translate(1.0D, 1.0D);
        transform.scale(realZoom, realZoom);
        grx.transform(transform);
        drawPageError(grx);
        grx.setTransform(origTransform);
       // break MISSING_BLOCK_LABEL_61;
      //  Exception exception;
       // exception;
        grx.setTransform(origTransform);
       // throw exception;
    }

    protected void drawPageError(Graphics grx)
    {
        grx.setColor(Color.white);
        grx.fillRect(0, 0, jasperPrint.getPageWidth() + 1, jasperPrint.getPageHeight() + 1);
    }

    protected void keyNavigate(KeyEvent evt)
    {
        boolean refresh = true;
        switch(evt.getKeyCode())
        {
        case 34: // '"'
        case 40: // '('
            dnNavigate(evt);
            break;

        case 33: // '!'
        case 38: // '&'
            upNavigate(evt);
            break;

        case 36: // '$'
            homeEndNavigate(0);
            break;

        case 35: // '#'
            homeEndNavigate(jasperPrint.getPages().size() - 1);
            break;

        case 37: // '%'
        case 39: // '\''
        default:
            refresh = false;
            break;
        }
        if(refresh)
        {
            refreshPage();
        }
    }

    private void dnNavigate(KeyEvent evt)
    {
        int bottomPosition = scrollPane.getVerticalScrollBar().getValue();
        scrollPane.dispatchEvent(evt);
        if((scrollPane.getViewport().getHeight() > pnlPage.getHeight() || scrollPane.getVerticalScrollBar().getValue() == bottomPosition) && pageIndex < jasperPrint.getPages().size() - 1)
        {
            setPageIndex(pageIndex + 1);
            if(scrollPane.isEnabled())
            {
                scrollPane.getVerticalScrollBar().setValue(0);
            }
        }
    }

    private void upNavigate(KeyEvent evt)
    {
        if((scrollPane.getViewport().getHeight() > pnlPage.getHeight() || scrollPane.getVerticalScrollBar().getValue() == 0) && pageIndex > 0)
        {
            setPageIndex(pageIndex - 1);
            if(scrollPane.isEnabled())
            {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
            }
        } else
        {
            scrollPane.dispatchEvent(evt);
        }
    }

    private void homeEndNavigate(int pageNumber)
    {
        setPageIndex(pageNumber);
        if(scrollPane.isEnabled())
        {
            scrollPane.getVerticalScrollBar().setValue(0);
        }
    }

    private void fitPage()
    {
        float heightRatio = ((float)pnlInScroll.getVisibleRect().getHeight() - 20F) / (float)jasperPrint.getPageHeight();
        float widthRatio = ((float)pnlInScroll.getVisibleRect().getWidth() - 20F) / (float)jasperPrint.getPageWidth();
        setRealZoomRatio(heightRatio >= widthRatio ? widthRatio : heightRatio);
    }

    static
    {
        log = LogFactory.getLog(com.jilit.irp.Report.MyJRViewer.class);
    }

}
