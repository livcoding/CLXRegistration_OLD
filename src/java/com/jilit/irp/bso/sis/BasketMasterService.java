package com.jilit.irp.bso.sis;

import com.jilit.irp.bso.biz.BusinessService;
import com.jilit.irp.iservice.BasketMasterIService;
import com.jilit.irp.persistence.dao.DAOFactory;
import com.jilit.irp.persistence.dto.BasketMaster;
import com.jilit.irp.persistence.dto.BasketMasterId;
import com.jilit.irp.util.JIRPSession;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ankit.kumar
 */
@Service
public class BasketMasterService implements BasketMasterIService {

    @Autowired
    DAOFactory daoFactory;
    @Autowired
    JIRPSession jirpsession;

    @Override
    public void getProgramCode(Model model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getProgramMasterDAO().getProgramCode(instituteid);
            model.addAttribute("prog_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSectionCode(Model model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getSectionMasterDAO().getAllSectionMaster(instituteid);
            model.addAttribute("reg_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getSubjectType(Model model) {
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            List list = (List) daoFactory.getSubjectTypeMasterDAO().getSubjectType(instituteid);
            model.addAttribute("sub_list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List getBasketMaster(HttpServletRequest req, Model model) {
        String prog_id = req.getParameter("prog_code");
        String sec_id = req.getParameter("sec_code");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getBasketMasterDAO().getBasketCode(instituteid, prog_id, sec_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List getSemester(HttpServletRequest req, Model model) {
        String prog_id = req.getParameter("prog_code");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getProgramMaxStyDAO().getSemesterCode(instituteid, prog_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List getSectionCode_NotSave(HttpServletRequest req, Model model) {
        String bas_code = req.getParameter("bas_code");
        String programid = req.getParameter("programid");
        String styno = req.getParameter("styno");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getSectionMasterDAO().getAllSectionMaster_NotSaved(instituteid, bas_code, programid, styno);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List getSectionCode(HttpServletRequest req, Model model) {
        String programid = req.getParameter("programid");
        List list = null;
        try {
            String instituteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
            list = (List) daoFactory.getSectionMasterDAO().getSectionCodeByProg(instituteid, programid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List saveBasketMaster(HttpServletRequest req) {

        String arr[] = req.getParameterValues("sec_code");
        String sec_id = "";
        String total = req.getParameter("total");
        String sub_num_count = req.getParameter("sub_num_count");
        if (sub_num_count != null) {
            sub_num_count = "Y";
        } else {
            sub_num_count = "N";
        }
        BusinessService businessService = new BusinessService(daoFactory, true);
        String export_To = req.getParameter("export_To");
        String max_no = req.getParameter("max_no");
        String min_no = req.getParameter("min_no");
        String deactive = req.getParameter("deactive");
        String instiuteuniqueid = jirpsession.getJsessionInfo().getSelectedinstituteuniqueid();
        String instiuteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String prog_id = req.getParameter("prog_code");
        String sty_num = req.getParameter("sty_num");
        String sub_type_id = req.getParameter("sub_type");
        String basket_id = "";
        List recordsToInsert = new ArrayList();
        //  List seqid = new ArrayList();
        Short sh = 1;
        List err = null;
        try {
            for (int i = 0; i < arr.length; i++) {
                BasketMaster master = new BasketMaster();
                BasketMasterId id = new BasketMasterId();
                sec_id = arr[i];
                basket_id = businessService.generateId("BasketID", instiuteuniqueid, "I", false);
                id.setInstituteid(instiuteid);
                id.setBasketid(basket_id);
                master.setId(id);
                master.setSectionid(sec_id);
                master.setProgramid(req.getParameter("prog_code"));
                master.setOptional(sub_num_count);
                master.setPreferencetype(export_To);
                master.setBasketcode(req.getParameter("basket_code").trim());
                master.setStynumber(Byte.decode(req.getParameter("sty_num").toString()));
                master.setSubjecttypeid(req.getParameter("sub_type"));
                master.setBasketdesc(req.getParameter("bas_desc"));
                Short seqid = daoFactory.getBasketMasterDAO().getSeqId(instiuteid, prog_id, sec_id, Byte.decode(sty_num), sub_type_id);
                seqid++;
                master.setSeqid(seqid);
                if (!total.equals("") && total != null) {
                    master.setTotalsubject(Short.decode(total));
                }
                if (!max_no.equals("") && max_no != null) {
                    master.setMaxsubject(Short.decode(max_no));
                }
                if (!min_no.equals("") && min_no != null) {
                    master.setMinsubject(Short.decode(min_no));
                }
                if (deactive != null) {
                    master.setDeactive("N");
                } else {
                    master.setDeactive("Y");
                }
                err = daoFactory.getBasketMasterDAO().doValidate(master, "normal");
                if (err.size() > 0) {
                    businessService.rollback();
                    return err;
                }
                // daoFactory.getBasketMasterDAO().add(master);
                recordsToInsert.add(master);
                //businessService.insertInIdGenerationControl(master);

            }
            businessService.insertInIdGenerationControl(recordsToInsert);
            err = new ArrayList<String>();
            err.add("Success");
        } catch (Exception e) {
            businessService.rollback();
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        } finally {
            businessService.closeSession();
        }
        return err;
    }

    @Override
    public void editBasketMaster(ModelMap mm, HttpServletRequest request) {
        List<Object[]> list = new ArrayList<Object[]>();
        List<Object[]> list1 = new ArrayList<Object[]>();
        String instiuteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        String ids = request.getParameter("pk");
        String basket_id = ids;
        list = (List<Object[]>) daoFactory.getBasketMasterDAO().getBasketCode_edit(instiuteid, basket_id);
        list1 = (List<Object[]>) daoFactory.getBasketMasterDAO().checkTeachingScheme(instiuteid, basket_id);
        String showLov = "";
        if (list1 != null) {
            if (list1.size() > 0) {
                showLov = "hide";
            } else {
                showLov = "show";
            }
        }
        mm.addAttribute("subtypelov", showLov);
        mm.addAttribute("edit", list);

    }

    @Override
    public List updateBasketMaster(HttpServletRequest req) {
        BasketMaster master = new BasketMaster();
        BasketMasterId id = new BasketMasterId();
        List err = null;
        String bask_id = req.getParameter("bask_id");
        String sec_id = req.getParameter("sec_id");
        String sub_type_id = "";
        if (req.getParameter("sub_type") != null) {
            sub_type_id = req.getParameter("sub_type");
        } else {
            sub_type_id = req.getParameter("sub_type_id");
        }
        String total = req.getParameter("total");
        String sub_num_count = req.getParameter("sub_num_count");
        if (sub_num_count != null) {
            sub_num_count = "Y";
        } else {
            sub_num_count = "N";
        }
        String export_To = req.getParameter("export_To");
        String max_no = req.getParameter("max_no");
        String min_no = req.getParameter("min_no");
        String deactive = req.getParameter("deactive");
        String seq_id = req.getParameter("seq_id");
        String instiuteid = jirpsession.getJsessionInfo().getSelectedinstituteid();
        id.setBasketid(bask_id);
        id.setInstituteid(instiuteid);
        try {
            master = (BasketMaster) daoFactory.getBasketMasterDAO().findByPrimaryKey(id);
            if (master != null) {
                master.setSectionid(sec_id);
                master.setProgramid(req.getParameter("prog_id"));
                master.setOptional(sub_num_count);
                master.setPreferencetype(export_To);
                master.setBasketcode(req.getParameter("basket_code").trim());
                master.setStynumber(Byte.decode(req.getParameter("sem_id").toString()));
                master.setSubjecttypeid(sub_type_id);
                master.setBasketdesc(req.getParameter("bas_desc"));
                if (!total.equals("") && total != null) {
                    master.setTotalsubject(Short.decode(total));
                } else {
                    master.setTotalsubject(null);
                }
                if (!max_no.equals("") && max_no != null) {
                    master.setMaxsubject(Short.decode(max_no));
                } else {
                    master.setMaxsubject(null);
                }
                if (!min_no.equals("") && min_no != null) {
                    master.setMinsubject(Short.decode(min_no));
                } else {
                    master.setMinsubject(null);
                }
                if (!seq_id.equals("") && seq_id != null) {
                    master.setSeqid(Short.decode(seq_id));
                } else {
                    master.setSeqid(null);
                }
                if (deactive != null) {
                    master.setDeactive("N");
                } else {
                    master.setDeactive("Y");
                }
                daoFactory.getBasketMasterDAO().update(master);
                err = new ArrayList<String>();
                err.add("Success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            err = new ArrayList<String>();
            err.add(e.getMessage());
        }
        return err;

    }
}
