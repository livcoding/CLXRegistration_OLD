<%-- 
    Document   : addDropSubjects
    Created on : 15 Jan, 2019, 10:43:55 AM
    Author     : Ashutosh1.kumar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<div class="row" id="dashboard">
    <style >

        .table-wrapper-scroll-y2 { height: 140px;  overflow: scroll;   width: 600px; }
        #subjectCode_chosen{text-align:left;}
        #auditsubjectcompl_chosen{text-align:left;}
        #auditsubjectcompt_chosen{text-align:left;}
        #auditsubjectcompp_chosen{text-align:left;}

    </style> 
    <div class="col-md-12">
        <div class="box" style="box-shadow: 0 8px 17px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);">
            <div class="box-header with-border bg-navy">
                <h3 class="box-title header_name" id="box-title"></h3>
                <div class="box-tools pull-right">                  
                    <button onclick="javascript:blank();" class="btn btn-box-tool"><i class="fa fa-close fa-2x" style="color:white"></i></button>
                </div>
            </div>
            <div class="box-body" >
                <form method="POST" id="ajaxform" class="form-horizontal">
                    <div class="row col-lg-12 form-group" id="divContainer" >
                        <div class="row col-lg-12 form-group"  style="margin-bottom:30px" >
                            <div class="row col-lg-4 col-lg-offset-2" >
                                <label class="radio-inline"><input type="radio" name="students" value="individualStudent" checked="true" onchange="ShowHide();"/> Individual Student <br> </label>
                            </div>
                            <div class="row col-lg-4 col-lg-offset-1" >
                                <label class="radio-inline">  <input type="radio" name="students" value="programAcadWise" onchange="ShowHide1();"/> Program/Academic Year Wise Student <br></label>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" id="individualStudent" >
                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Institute:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="multiinstituteid2" multiple="true" name="multiinstituteid2" tabindex="1" data-validation="required"data-live-search="true" onchange="getSemesterCode(2);">               
                                            <c:forEach items="${institutelist}" var="institutelist">
                                                <c:if test="${maininstituteid==institutelist[0]}">
                                                    <option value="${institutelist[0]}" selected="true"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                                </c:if>
                                                <c:if test="${maininstituteid!=institutelist[0]}">
                                                    <option value="${institutelist[0]}"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="semCode2" name="semCode2" data-validation="required"data-live-search="true" tabindex="2" onchange="getAcademicYear()">               
                                            <option value="">Select</option>
                                            <c:forEach items="${regisList}" var="regisList">
                                                <option value="${regisList[0]}~@~${regisList[1]}"><c:out value="${regisList[1]} -- ${regisList[2]}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-6 form-group" >
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Enrollment No.:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <input type="text" id="enrollmentNo1" name="enrollmentNo1"  autocomplete="off"  maxlength="20" tabindex="3" onblur="getStudentInfo(1);" onkeyup="this.value = this.value.toUpperCase();"  class="form-control" data-validation="required"/>
                                    </div>  
                                    <select class="form-control" id="enrollmentNo11" name="enrollmentNo11" data-live-search="true" tabindex="" onchange="getStudentDetail()" style="display:none;" >
                                    </select>
                                </div> 
                            </div>   
                            <input type="hidden" name="programcode1" id="programcode1">
                            <input type="hidden" name="academicyear1" id="academicyear1">
                            <input type="hidden" name="selectedstudentid" id="selectedstudentid">
                            <input type="hidden" name="modeparameter" id="modeparameter">
                        </div>
                        <div class="row col-lg-12 form-group" id="allStudent" style="display:none;">
                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Institute:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="multiinstituteid" multiple="true" name="multiinstituteid" data-validation="required"data-live-search="true" onchange="getSemesterCode();">               
                                            <c:forEach items="${institutelist}" var="institutelist">
                                                <c:if test="${maininstituteid==institutelist[0]}">
                                                    <option value="${institutelist[0]}" selected="true"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                                </c:if>
                                                <c:if test="${maininstituteid!=institutelist[0]}">
                                                    <option value="${institutelist[0]}"><c:out value="${institutelist[1]} -- ${institutelist[2]}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Semester Code:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="semCode" name="semCode" data-validation="required" data-live-search="true" onchange="getAcademicYear()">               
                                            <option value="">Select</option>
                                            <c:forEach items="${regisList}" var="regisList">
                                                <option value="${regisList[0]}~@~${regisList[1]}"><c:out value="${regisList[1]} -- ${regisList[2]}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-6 form-group" >
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Academic Year:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="acadyear" name="acadyear" data-live-search="true" onchange="getProgram()">
                                            <option value="">Select</option>
                                        </select>
                                    </div>
                                </div> 
                            </div>

                            <div class="row col-lg-12 form-group" >
                                <div class="row col-lg-6 form-group">
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Program:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="programCode" name="programCode" data-live-search="true" onchange="getStudent(0)">
                                            <option value="">Select</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="row col-lg-6 form-group" >
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Enrollment No.:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                    <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6">
                                        <select class="form-control" id="enrollmentNo" name="enrollmentNo" data-live-search="true" onchange="getStudentDetail()">
                                            <option value="">Select</option>
                                        </select>
                                    </div>
                                </div> 
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group">
                            <div class="row col-lg-5 form-group">
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Student Name:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="stuName" name="stuName" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div> 
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Father's Name:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="fathername" name="fathername" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div> 
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Program(Branch):
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="program" name="program" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Batch:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="batchSubBatch" name="batchSubBatch" class="form-control has-help-txt" readonly="true"/>
                                        <input type="hidden" id="batchSubBatch1" name="batchSubBatch1" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div> 
                            </div>  
                            <div class="row col-lg-5 form-group">
                                <div class="row col-lg-12 form-group">
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Academic Year:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="academic" name="academic" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div> 
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">STY Number :
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="styNumber" name="styNumber" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Max Credits:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="totalCredit" name="totalCredit" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-md-4 control-label">Credit Taken:
                                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                    <div class="col-md-8">
                                        <input type="text" id="creditTaken" name="creditTaken" class="form-control has-help-txt" readonly="true"/>
                                    </div>
                                </div> 
                            </div> 
                            <div class="row col-lg-2 form-group" style="margin-right: 15px;">
                                <div class="row form-group" >                                     
                                    <a href="javascript:loadData();" style="width:100%" class="btn btn-success btn-lg fa fa-search" tabindex="4"> Load Subjects</a>
                                </div>
                                <div class="row form-group" data-toggle="tooltip" id="hint" > 
                                    <button type="button" id="addreg" style="width:100%" onclick="coreSubjectData()" class="btn btn-info"  disabled="true" > Add Regular / Back Subjects</button>
                                </div>
                                <div class="row form-group" > 
                                    <button type="button" id="addaudit" style="width:100%" onclick="auditSubjectData()" class="btn btn-info" disabled="true">Add Audit Subject</button>
                                </div>
                                <div class="row form-group" > 
                                    <button type="button" id="noduesbtn" style="width:100%;display:none;" onclick="showNoDuesModal()" class="btn btn-warning">No Dues Status</button>
                                </div>
                            </div> 
                            <div id="activitymodal" class="modal fade" role="dialog">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header" style="background-color:#001f3f;color:white;position:sticky;top:0px;z-index:1">
                                            <button type="button" class="close" data-dismiss="modal" style="opacity:1;color:white;margin-right:160px">&times;</button>
                                            <h4 class="modal-title"><b>Student No Dues Status...</b></h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="container">
                                                <div id="test" class="row col-lg-12 form-group table-wrapper-scroll-y2" style="height:300px;width:750px;border:1px solid lightgray;margin-top:-15px">
                                                    <table class="row col-lg-12 form-group box table table-hover table-condensed table-bordered" cellspacing="0" style="width:750px">
                                                        <thead id="header">
                                                            <tr class="" style="background-color:#008080;color:white">                                               
                                                                <th width="20px;" style="position:sticky;top:0px;background-color:#008080 ">Activity / No Dues Required</th>
                                                                <th width="20px;" style="position:sticky;top:0px;background-color:#008080 ">Approval Status</th>               
                                                                <th width="20px;" style="position:sticky;top:0px;background-color:#008080 ">remarks</th>            
                                                            </tr>
                                                        </thead>  
                                                        <tbody id="studentactivity">
                                                        </tbody>
                                                    </table>                                                          
                                                </div>  
                                            </div>  
                                        </div>
                                    </div>
                                </div>
                            </div> 
                        </div>
                    </div>

                    <input type="hidden"   name="countval" id="countval" value=""/>
                    <input type="hidden"   name="countvalGIP" id="countvalGIP" value=""/>
                    <input type="hidden"   name="countval1" id="countval1" value=""/>
                    <input type="hidden"   name="countvalforregconfirm" id="countvalforregconfirm" value=""/>
                    <input type="hidden"   name="studentid" id="studentid" value=""/>
                    <input type="hidden"   name="programid" id="programid" value=""/>
                    <input type="hidden"   name="branchid" id="branchid" value=""/>
                    <input type="hidden"   name="sectionid" id="sectionid" value=""/>
                    <input type="hidden"   name="subsectionid" id="subsectionid" value=""/>
                    <input type="hidden"   name="groupid" id="groupid" value=""/>                        
                    <input type="hidden"   name="enroll" id="enroll" value=""/>                        
                    <input type="hidden"   name="registrationid" id="registrationid" value=""/>                        
                    <input type="hidden"   name="semCode1" id="semCode1" value=""/>                        
                    <input type="hidden"   name="countBasket" id="countBasket" value=""/>                        
                    <input type="hidden"   name="countBasketElective" id="countBasketElective" value=""/>                        
                    <input type="hidden"   name="countBasketGIP" id="countBasketGIP" value=""/>                        
                    <input type="hidden"   name="subjectid" id="subjectid" value=""/>                        
                    <input type="hidden"   name="minCredit" id="minCredit" value=""/>                        
                    <input type="hidden"   name="userType" id="userType" value=""/>                        
                    <input type="hidden"   name="allowforfeepay" id="allowforfeepay" value=""/>                        
                    <input type="hidden"   name="feespaid" id="feespaid" value=""/>                        
                    <input type="hidden"   name="docverification" id="docverification" value=""/>                        
                    <input type="hidden"   name="nodues" id="nodues" value=""/>                        
                    <input type="hidden"   name="quotaid" id="quotaid" value=""/>                        
                    <input type="hidden"   name="stytypeid" id="stytypeid" value=""/>                        
                    <input type="hidden"   name="criditSum" id="criditSum" value=""/>                        
                    <input type="hidden"   name="leclistforelectiveswap" id="leclistforelectiveswap" value=""/>                        
                    <input type="hidden"   name="tutlistforelectiveswap" id="tutlistforelectiveswap" value=""/>                        
                    <input type="hidden"   name="praclistforelectiveswap" id="praclistforelectiveswap" value=""/>                        
                    <input type="hidden"   name="regconfflag" id="regconfflag" value=""/>                        
                    <input type="hidden"   name="regconfdate" id="regconfdate" value=""/>                                             
                    <input type="hidden"   name="departmentid" id="departmentid" value=""/>                                            
                    <input type="hidden"   name="facsubjectid" id="facsubjectid" value=""/>                                            
                    <input type="hidden"   name="basketid" id="basketid" value=""/>         
                    <input type="hidden" id="section" name="section" class="form-control has-help-txt" readonly="true"/>
                    <input type="hidden" id="subsection" name="subsection" class="form-control has-help-txt" readonly="true"/>                    
                    <input type="hidden" id="classDateflag" name="classDateflag" value="${classDate}" class="form-control has-help-txt" readonly="true"/>
                    <input type="hidden" id="remainingCredit" name="remainingCredit" class="form-control has-help-txt" readonly="true"/>                    
                    <input type="hidden" id="studentinstituteid" name="studentinstituteid" class="form-control has-help-txt" readonly="true"/>                    
                    <input type="hidden" id="studentregistrationid" name="studentregistrationid" class="form-control has-help-txt" readonly="true"/>                                          
                    <input type="hidden"   name="showbatches" id="showbatches" value=""/>                          

                    <div class="col-lg-12">
                        <div class="row col-lg-12 form-group" > 
                            <!--<div id="test" class="table-wrapper-scroll-y">-->
                            <table  class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                <thead id="header">
                                    <tr class="" style="background-color:#008080;color:white">                                                                
                                        <th width="5px;">Drop</th>
                                        <th width="15px;" >Subject Code - Description</th>               
                                        <th width="10px;" >Subject Type</th>               
                                        <th width="15px;" >Subject Batch Detail</th> 
                                        <!--<th width="15px;" >Group</th>-->  
                                        <th width="20px;" >STY No.</th>               
                                        <th width="10px;" >Credit</th>              
                                    </tr>
                                </thead>  
                                <tbody id="dropStudent">
                                </tbody>
                            </table>                                                          
                            <!--</div>-->                       
                        </div>
                    </div> 

                    <div class="col-lg-12">              
                        <div style="margin-top: 10px;margin-left: 10px; display: none" class="form-group pull-left">
                            <label style="text-transform: capitalize; margin-top: -17px;">Registration Confirmation:</label>
                            <input type="checkbox" id="chkbox" name="chkbox" onclick="change()" value="Y" checked="true"/>  
                        </div>
                        <div style="margin-top: 10px;margin-right: 15px;display:none" class="form-group pull-right" id="printbutton">
                            <a onclick="getReport()" class="btn btn-success btn-sm btn-flat">Print Registration Slip</a>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" style="display:none;">
                        <div class="row col-lg-3 form-group" style="text-align:center">
                            <a onclick="regConfermation()" class="btn btn-success btn-sm">Confirm Registration</a>  
                        </div>
                        <div class="row col-lg-6 form-group">
                            <label style="text-transform: capitalize;display:none" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Reg Confirmation Date:</label>
                            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                                <div class='input-group date' id='datetimepicker4'>
                                    <input type='hidden' class="form-control" id="regConfirm_date" name="regConfirm_date" />
                                    <span class="input-group-addon" style="display:none;">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

            </div>    

            <!----------------------- Model Window Open --------------------->   
            <div id="myModal" class="modal fade" role="dialog" style="width: 1000px;margin-top:-350px;border:2px solid lightgray">
                <div class="modal-dialog modal-lg" style="width: 1000px;">
                    <div class="modal-content">
                        <div class="modal-header" style="background-color:#001f3f;color:white;position:sticky;top:0px;z-index:1">
                            <button type="button" class="close" data-dismiss="modal" style="opacity:1;color:white;margin-right:20px">&times;</button>
                            <h4 class="modal-title" id="heading"><b>Add Regular / BackLog Subjects</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <!--                                <div class="row" style="text-align:right">
                                                                    <div class="row col-lg-6 form-group" style="height: 25px;margin-right: 15px;"> 
                                                                        <div class="row col-lg-12 form-group">
                                                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Semester Code:
                                                                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                                                <input type="text" id="regCode1" name="regCode1" class="form-control has-help-txt" readonly="true"/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row" style="text-align:right">
                                                                    <div class="row col-lg-4 form-group" >
                                                                        <label style="text-transform: capitalize;" class="col-md-5 col-sm-12 control-label">Name:
                                                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-7">
                                                                            <input type="text" id="name1" name="name1" class="form-control has-help-txt" readonly="true"/>
                                                                        </div>
                                                                    </div> 
                                                                    <div class="row col-lg-4 form-group">
                                                                        <label style="text-transform: capitalize;" class="col-md-5 col-sm-12 control-label">Enroll. No.:
                                                                            <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-7">
                                                                            <input type="text" id="regNo1" name="regNo1" class="form-control has-help-txt" readonly="true"/>
                                                                        </div>
                                                                    </div> 
                                                                    <div class="row col-lg-4 form-group" >
                                                                        <label style="text-transform: capitalize;" class="col-md-5 col-sm-12 control-label">STY No.:
                                                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-7">
                                                                            <input type="text" id="styNo1" name="styNo1" class="form-control has-help-txt" readonly="true"/>
                                                                        </div>
                                                                    </div>
                                                                </div>-->
                                <div class="row" style="text-align:right">
                                    <div class="row col-lg-4 form-group" >
                                        <label style="text-transform: capitalize;" class="col-md-5 col-sm-12 control-label">Total Credits:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-7">
                                            <input type="text" id="totalCredit1" name="totalCredit1" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div> 
                                    <div class="row col-lg-4 form-group" >
                                        <label style="text-transform: capitalize;" class="col-md-5 control-label">Credit Taken:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-7">
                                            <input type="text" id="creditTaken1" name="creditTaken1" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div> 
                                    <div class="row col-lg-4 form-group" >
                                        <label style="text-transform: capitalize;" class="col-md-5 col-sm-12 control-label">Remaining Credits:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-7">
                                            <input type="text" id="remainingCredit1" name="remainingCredit1" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group" style="overflow-x:auto;overflow-y:auto;height:400px"> 
                                    <!--<div id="test" class="table-wrapper-scroll-y" style="height:250px" >--> 
                                    <table  class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                        <thead id="header">
                                            <tr class="" style="background-color:#008080;color:white">                                                               
                                                <th width="5px;">Add</th>
                                                <th width="10px;" >Subject Code - Desc</th>               
                                                <th width="10px;" >Subject Type</th>               
                                                <th style="width:200px" >Subject Batch Detail</th>               
                                                <!--<th width="10px;" >Group</th>-->               
                                                <th width="10px;" >STY No.</th>               
                                                <th width="10px;" >Credit</th>        
                                                    <c:if test="${classDate=='Y'}">   
                                                    <th width="10px;" >Class Start Date</th>      
                                                    </c:if>                    
                                                <th width="10px;" >Switch Backlog Subject</th>              
                                            </tr>
                                        </thead>  
                                        <tfoot>
                                            <tr id="filterrow" class="no-print">                                                           
                                            </tr>
                                        </tfoot>
                                        <tbody id="AddSubject">
                                        </tbody>
                                    </table>                                                          
                                    <!--</div>-->                       
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div style="margin-right: 15px;" class="form-group pull-right" >
                                <a href="javascript:saveData();" class="btn btn-success btn-md" style="width:100px">Add Subject</a>  
                                <button type="button" class="btn btn-warning btn-md" data-dismiss="modal" style="width:100px">Back</button>                                          
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
            <!--///////////////////Model window End///////////////////-->

            <!----------------------- Model Window Open --------------------->   
            <div id="myModal1" class="modal fade" role="dialog" style="width: 1000px;">
                <div class="modal-dialog modal-lg" style="width: 1000px;">
                    <div class="modal-content">
                        <div class="modal-header" style="background-color:#001f3f;color:white">
                            <button type="button" class="close" data-dismiss="modal" style="opacity:1;color:white">&times;</button>
                            <h4 class="modal-title"><b>Add GIP Subject</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row col-lg-12 form-group">
                                    <div class="row col-lg-6 form-group" style="height: 25px;margin-right: 15px;"> 
                                        <div class="row col-lg-12 form-group">
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Semester Code:
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="regCode2" name="regCode2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row col-lg-12 form-group">
                                    <div class="row col-lg-6 form-group" style="height: 135px;margin-right: 15px;">
                                        <div class="col-lg-12 form-group">
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Enrollment No.:
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="regNo2" name="regNo2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div> 
                                        <div class="row col-lg-12 form-group" >
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">STY Number :
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="styNo2" name="styNo2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div> 
                                        <div class="row col-lg-12 form-group" >
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Credit Taken :
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="creditTaken2" name="creditTaken2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="row col-lg-6 form-group" style="height: 135px;margin-right: 15px;">
                                        <div class="row col-lg-12 form-group" >
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Name:
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="name2" name="name2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div> 
                                        <div class="row col-lg-12 form-group" >
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Total Credits :
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="totalCredit2" name="totalCredit2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div>
                                        <div class="row col-lg-12 form-group" >
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Remaining Credits :
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                <input type="text" id="remainingCredit2" name="remainingCredit2" class="form-control has-help-txt" readonly="true"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="row col-lg-12 form-group" > 
                                        <!--<div id="test" class="table-wrapper-scroll-y">-->
                                        <table  class="box table table-hover table-condensed table-bordered" cellspacing="0">
                                            <thead id="header">
                                                <tr class="" style="background-color:#008080;color:white">                                                                
                                                    <th width="5px;">Add</th>
                                                    <th width="10px;" >Subject Code - Desc</th>               
                                                    <th width="10px;" >Mode</th>               
                                                    <th width="10px;" >Subject Type</th>               
                                                    <th width="20px;" >Subject Batch Detail</th>              
                                                    <!--<th width="10px;" >Group</th>-->               
                                                    <th width="10px;" >STY No.</th>               
                                                    <th width="10px;" >Credit</th>      
                                                        <c:if test="${classDate=='Y'}">   
                                                        <th width="10px;" >Class Start Date</th>      
                                                        </c:if>                     
                                                </tr>
                                            </thead>  
                                            <tfoot>
                                                <tr id="filterrow" class="no-print">                                                           
                                                </tr>
                                            </tfoot>
                                            <tbody id="AddGIPSubject">
                                            </tbody>
                                        </table>                                                          
                                        <!--</div>-->                       
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right" >
                                <a href="javascript:saveGIPData();" class="btn btn-success btn-sm btn-flat"> Add</a>                                         
                                <button type="button" class="btn btn-success btn-sm btn-flat" data-dismiss="modal">Back</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
            <!--///////////////////Model window End///////////////////-->

            <!----------------------- Model Window Open --------------------->   
            <div id="myModal2" class="modal fade" role="dialog" style="width: 1000px;">
                <div class="modal-dialog modal-lg" style="width: 900px;">
                    <div class="modal-content">
                        <div class="modal-header" style="background-color:#001f3f;color:white">
                            <button type="button" class="close" data-dismiss="modal" style="opacity:1;color:white">&times;</button>
                            <h4 class="modal-title" id="auditheading"><b>Add Audit Subject</b></h4>
                        </div>
                        <div class="modal-body" style="text-align:right">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="row col-lg-4 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-6 col-lg-6 col-xs-6 col-md-6 control-label">Total Credits:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                            <input type="text" id="totalCredit3" name="totalCredit3" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div>
                                    <div class="row col-lg-4 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-6 col-lg-6 col-xs-6 col-md-6 control-label">Credit Taken:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                            <input type="text" id="creditTaken3" name="creditTaken3" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div>
                                    <div class="row col-lg-4 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-6 col-lg-6 col-xs-6 col-md-6 control-label">RemainingCredits:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                            <input type="text" id="remainingCredit3" name="remainingCredit3" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div>
                                </div><br>
                                <div class="row" >
                                    <div class="row col-lg-6 form-group" >
                                        <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Subject Code:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                            <select class="form-control" id="subjectCode" name="subjectCode" data-validation="required" data-live-search="true" onchange="auditSubjectDetail()">               
                                                <option value="">Select</option>                                            
                                            </select>
                                        </div> 
                                    </div>
                                    <div class="row col-lg-6 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Subject Desc:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                            <input type="text" id="subjectdesc" name="subjectdesc" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div>
                                </div><br>  
                                <div class="row">
                                    <div class="row col-lg-6 form-group"> 
                                        <div id="auditsubjectcompldiv" style="display:none">
                                            <select class="form-control" id="auditsubjectcompl" name="auditsubjectcompl" data-validation="required" data-live-search="true">               
                                                <option value="">Select</option>                                            
                                            </select>
                                        </div>&nbsp;
                                        <div id="auditsubjectcomptdiv" style="display:none">
                                            <select class="form-control" id="auditsubjectcompt" name="auditsubjectcompt" data-validation="required" data-live-search="true">               
                                                <option value="">Select</option>                                            
                                            </select>
                                        </div>&nbsp;
                                        <div id="auditsubjectcomppdiv" style="display:none">
                                            <select class="form-control" id="auditsubjectcompp" name="auditsubjectcompp" data-validation="required" data-live-search="true">               
                                                <option value="">Select</option>                                            
                                            </select>
                                        </div>
                                    </div>
                                    <div class="row col-lg-6 form-group"> 
                                        <c:if test="${classDate=='Y'}">   
                                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Class Start Date:
                                                <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                                            </c:if>
                                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                            <div class='input-group date' id='datetimepicker3'>
                                                <input type='text' class="form-control"  data-validation="required" id="classStartDate"  name="classStartDate" />
                                                <c:if test="${classDate=='Y'}">   
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row" >
                                    <div class="row col-lg-6 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Credit:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                            <input type="text" id="credit" name="credit" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div> 
                                    <div class="row col-lg-6 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Department:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                        <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                            <input type="text" id="runningDepar" name="runningDepar" class="form-control has-help-txt" readonly="true"/>
                                            <input type="hidden" id="runningDeparid" name="runningDeparid" class="form-control has-help-txt" readonly="true"/>
                                        </div>
                                    </div> 
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right" >
                                <a href="javascript:saveAuditSubject();" class="btn btn-success btn-md" style="width:100px"> Add</a>                                         
                                <button type="button" class="btn btn-warning btn-md" data-dismiss="modal" style="width:100px">Back</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
            <!--///////////////////Model window End///////////////////-->

            <!----------------------- Model Window Open --------------------->
            <div id="myModalE" class="modal fade" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title"><b>Elective Subject Detail</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="col-lg-12" style="margin-top:10px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Elective Subject:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                    <select class="form-control" id="electiveSubject" name="electiveSubject" data-live-search="true">
                                        <option value="">Select</option>
                                    </select>
                                </div>
                            </div> 
                            <div class="col-lg-12" style="margin-top:10px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Mode:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6">
                                    <select class="form-control" id="electiveSubjectMode" name="electiveSubjectMode" data-live-search="true">
                                        <option value="S" selected="true">Study</option>
                                        <option value="E">Exam</option>
                                    </select>
                                </div>
                            </div> 
                            <div class="col-lg-12" style="margin-top:10px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Batch Detail:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6" id="basketdetail">

                                </div>
                            </div> 
                            <div class="col-lg-12" style="margin-top:10px;">
                                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Class Start Date:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                                    <div class='input-group date' id='datetimepicker6'>
                                        <input type='text' class="form-control" id="electiveClassStartDate" name="electiveClassStartDate" />
                                        <span class="input-group-addon">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div> 

                            <input type="hidden"   name="oldElectiveSubject" id="oldElectiveSubject" value=""/> 
                            <input type="hidden"   name="basketidElectiveSubject" id="basketidElectiveSubject" value=""/> 
                        </div>
                        <div class="modal-footer">
                            <div class="col-lg-12 form-group"   > 
                                <div style="margin-top: 15px;margin-right: 15px;" class="form-group pull-right" >
                                    <a href="javascript:changeElectiveSubject();" class="btn btn-success btn-sm btn-flat">Change Elective</a>                                         
                                    <button type="button" class="btn btn-success btn-sm btn-flat" data-dismiss="modal">Back</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--------------------Model window End-------------------------->

            <!----------------------- Model Window Open For Faculty--------------------->   
            <div id="facultyModal" class="modal fade" role="dialog" style="width: 1000px;">
                <div class="modal-dialog modal-lg" style="width: 1000px;">
                    <div class="modal-content">
                        <div class="modal-header" style="background-color:#001f3f;color:white;position:sticky;top:0px;z-index:1">
                            <button type="button" class="close" data-dismiss="modal" style="opacity:1;color:white;margin-right:20px">&times;</button>
                            <h4 class="modal-title"><b>Assign Faculty</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="row col-lg-12 form-group">
                                    <div class="col-md-3 col-md-offset-2"> <input type="radio" name="fac" id="SD" value="SD" checked="true" onchange="assignFaculty(this.value)"/> Same Department</div>
                                    <div class="col-md-3">  <input type="radio" name="fac" id="OD" value="OD" onchange="assignFaculty(this.value)"/> Other Department</div>
                                </div>
                                <div class="row col-lg-12 form-group">
                                    <div class="row col-lg-8 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Faculty Code / Name:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top:7px">
                                            <select class="form-control" id="facultyid" name="facultyid" data-live-search="true">
                                                <option value="">Select</option>
                                            </select>
                                        </div>
                                    </div> 
                                    <div class="row col-lg-8 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top:7px">
                                            <input type="text" readonly="true" class="form-control" id="readonlySubjectCode"/>
                                        </div>
                                    </div> 
                                    <div class="row col-lg-8 form-group" >
                                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Component:
                                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                                        <div class="col-sm-6 col-lg-8 col-xs-6 col-md-6" style="margin-top:7px">
                                            <select class="form-control" id="subjectcomponentid" name="subjectcomponentid" data-live-search="true">
                                                <option value="">Select</option>
                                            </select>
                                        </div>
                                    </div> 
                                    <div class="col-md-3"> 
                                        <div style="margin-top: 5px;" class="form-group pull-right" >
                                            <a href="javascript:saveFaculty();" class="btn btn-success btn-sm" style="width:100px"> Save</a>  
                                            <button type="button" class="btn btn-warning btn-sm" style="width:100px" data-dismiss="modal">Back</button>                                          
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                        </div>
                    </div>   
                    <!--///////////////////Model window End///////////////////-->

                </div>
            </div>
            </form>
        </div>
    </div>
</div>
</div>
<script>
    $("#multiinstituteid").chosen({width: "100%"});
    $("#multiinstituteid2").chosen({width: "100%"});
    $("#semCode").chosen({width: "100%"});
    $("#semCode2").chosen({width: "100%"});
    $("#acadyear").chosen({width: "100%"});
    $("#programCode").chosen({width: "100%"});
    $("#enrollmentNo").chosen({width: "100%"});
    $("#electiveSubject").chosen({width: "100%"});
    $("#electiveSubjectMode").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});
    $("#facultyid").chosen({width: "100%"});
    $("#subjectcomponentid").chosen({width: "100%"});
    $("#auditsubjectcompl").chosen({width: "100%"});
    $("#auditsubjectcompt").chosen({width: "100%"});
    $("#auditsubjectcompp").chosen({width: "100%"});
    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
        getSemesterCode();
    });
    $('#enrollmentNo1').keypress(function (e) {
        if (e.which == 13) {
            $(this).blur();
        }
    });
    $(function () {
        $('#datetimepicker3').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker4').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker6').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });
    function checkCurrentDate(i) {
        var todayTime = new window.Date();
        var tMonth = (todayTime.getMonth() + 1) < 10 ? '0' + (todayTime.getMonth() + 1) : '' + (todayTime.getMonth() + 1);
        var tDay = todayTime.getDate() < 10 ? '0' + todayTime.getDate() : '' + todayTime.getDate();
        var tYear = todayTime.getFullYear();
        var date = $("#regconfirmdate" + i + "").val().split("-");
        var Year = date[0];
        var Month = date[1];
        var Date = date[2];
        if (Year < tYear) {
            $("#regconfirmdate" + i + "").val(tYear + "-" + tMonth + "-" + tDay);
            BootstrapDialog.alert("Please select another date greater than the Current date!");
        } else if (tYear == Year) {
            if (Month < tMonth) {
                $("#regconfirmdate" + i + "").val(tYear + "-" + tMonth + "-" + tDay);
                BootstrapDialog.alert("Please select another date greater than the Current date!");
            } else if (tMonth == Month) {
                if (Date < tDay) {
                    $("#regconfirmdate" + i + "").val(tYear + "-" + tMonth + "-" + tDay);
                    BootstrapDialog.alert("Please select another date greater than the Current date!");
                }
            }
        }
    }
    function checkDate(i) {
        var date = $("#regconfirmdate" + i + "").val().split("-");
        var sYear = date[0];
        var sMonth = date[1];
        var sDate = date[2];
        var regdate = $("#regconfdate").val().split("/");
        var Year = regdate[2];
        var Month = regdate[1];
        var Date = regdate[0];
        if ($("#regconfdate").val() != '') {
            if (sYear < Year) {
                $("#regconfirmdate" + i + "").val(regdate[2] + "-" + regdate[1] + "-" + regdate[0]);
                BootstrapDialog.alert("Please select another date greater than the Registration Confirmation date!");
            } else if (sYear == Year) {
                if (sMonth < Month) {
                    $("#regconfirmdate" + i + "").val(regdate[2] + "-" + regdate[1] + "-" + regdate[0]);
                    BootstrapDialog.alert("Please select another date greater than the Registration Confirmation date!");
                } else if (sMonth == Month) {
                    if (sDate < Date) {
                        $("#regconfirmdate" + i + "").val(regdate[2] + "-" + regdate[1] + "-" + regdate[0]);
                        BootstrapDialog.alert("Please select another date greater than the Registration Confirmation date!");
                    }
                }
            }
        }
    }
    function change() {
    }

    $(function () {
        var todayTime = new Date();
        var month = (todayTime.getMonth() + 1) < 10 ? '0' + (todayTime.getMonth() + 1) : '' + (todayTime.getMonth() + 1);
        var day = todayTime.getDate() < 10 ? '0' + todayTime.getDate() : '' + todayTime.getDate();
        var year = todayTime.getFullYear();
        $('#regConfirm_date').val(day + "/" + month + "/" + year);
    });
    function ShowHide() {
//        $('#multiinstituteid').val('x').trigger("chosen:updated");
//        $('#multiinstituteid2').val('x').trigger("chosen:updated");
        $('#semCode').empty().trigger("chosen:updated");
        $('#semCode2').empty().trigger("chosen:updated");
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        $("#noduesbtn").hide();
        $("#addreg").prop("disabled", true);
        $("#individualStudent").show();
        $("#allStudent").hide();
        $("#studentid").val('');
        $("#stuName").val('');
        $("#academic").val('');
        $("#programid").val('');
        $("#styNumber").val('');
        $("#branchid").val('');
        $("#program").val('');
        $("#sectionid").val('');
        $("#batchSubBatch").val('');
        $("#batchSubBatch1").val('');
        $("#subsectionid").val('');
        $("#groupid").val('');
        $("#enroll").val('');
        $("#allowforfeepay").val('');
        $("#feespaid").val('');
        $("#docverification").val('');
        $("#nodues").val('');
        $("#quotaid").val('');
        $("#stytypeid").val('');
        $("#fathername").val('');
        $("#rankenroll").val('');
        $("#totalCredit").val('');
        $("#creditTaken").val('');
        $("#remainingCredit").val('');
        $("#minCredit").val('');
        $("#userType").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        $("#acadyear").empty();
        $("#acadyear").trigger("chosen:updated");
        $("#programCode").empty();
        $("#programCode").trigger("chosen:updated");
        $("#enrollmentNo").empty();
        $("#enrollmentNo").trigger("chosen:updated");
        $("#enrollmentNo1").val('');
        $("#enrollmentNo11").empty();
        $('#printbutton').hide();
        getSemesterCode();
    }
    function ShowHide1() {
//        $('#multiinstituteid').val('').trigger("chosen:updated");
//        $('#multiinstituteid2').val('').trigger("chosen:updated");
        $('#semCode').empty().trigger("chosen:updated");
        $('#semCode2').empty().trigger("chosen:updated");
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        $("#noduesbtn").hide();
        $("#addreg").prop("disabled", true);
        $("#individualStudent").hide();
        $("#allStudent").show();
        $("#studentid").val('');
        $("#stuName").val('');
        $("#academic").val('');
        $("#programid").val('');
        $("#styNumber").val('');
        $("#branchid").val('');
        $("#program").val('');
        $("#sectionid").val('');
        $("#batchSubBatch").val('');
        $("#batchSubBatch1").val('');
        $("#subsectionid").val('');
        $("#groupid").val('');
        $("#enroll").val('');
        $("#allowforfeepay").val('');
        $("#feespaid").val('');
        $("#docverification").val('');
        $("#nodues").val('');
        $("#quotaid").val('');
        $("#stytypeid").val('');
        $("#fathername").val('');
        $("#rankenroll").val('');
        $("#totalCredit").val('');
        $("#creditTaken").val('');
        $("#remainingCredit").val('');
        $("#minCredit").val('');
        $("#userType").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        $("#studentactivity").empty();
        $("#enrollmentNo1").empty();
        $("#enrollmentNo11").empty();
        $('#printbutton').hide();
        getSemesterCode();
    }

    function getSemesterCode() {
        $("#noduesbtn").hide();
        $("#studentid").val('');
        $("#stuName").val('');
        $("#academic").val('');
        $("#programid").val('');
        $("#styNumber").val('');
        $("#branchid").val('');
        $("#program").val('');
        $("#sectionid").val('');
        $("#batchSubBatch").val('');
        $("#batchSubBatch1").val('');
        $("#subsectionid").val('');
        $("#groupid").val('');
        $("#enroll").val('');
        $("#allowforfeepay").val('');
        $("#feespaid").val('');
        $("#docverification").val('');
        $("#nodues").val('');
        $("#quotaid").val('');
        $("#stytypeid").val('');
        $("#fathername").val('');
        $("#rankenroll").val('');
        $("#totalCredit").val('');
        $("#creditTaken").val('');
        $("#remainingCredit").val('');
        $("#minCredit").val('');
        $("#userType").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        $("#semCode").empty().trigger("chosen:updated");
        $("#semCode2").empty().trigger("chosen:updated");
        $("#acadyear").empty();
        $("#programCode").empty().trigger("chosen:updated");
        $("#enrollmentNo").empty().trigger("chosen:updated");
        $('#printbutton').hide();
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        var instids = '';
        var students = $("input[name='students']:checked").val();
        if (students == 'programAcadWise') {
            instids = $('#multiinstituteid').val();
        } else {
            instids = $('#multiinstituteid2').val();
        }
        $.ajax({
            type: "POST", url: "addDropSubject/getSemesterCode",
            data: '&instituteids=' + instids,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false, beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.semestercodelist, function (i, item)
                {
                    str1 += '<option value="' + item[2] + '~@~' + item[3] + '">' + item[1] + ' -- ' + item[3] + '</option>'
                });
                $("#semCode").append(str1);
                $("#semCode").trigger("chosen:updated");
                $("#semCode2").append(str1);
                $("#semCode2").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function getAcademicYear() {
        $("#noduesbtn").hide();
        $("#studentid").val('');
        $("#stuName").val('');
        $("#academic").val('');
        $("#programid").val('');
        $("#styNumber").val('');
        $("#branchid").val('');
        $("#program").val('');
        $("#sectionid").val('');
        $("#batchSubBatch").val('');
        $("#batchSubBatch1").val('');
        $("#subsectionid").val('');
        $("#groupid").val('');
        $("#enroll").val('');
        $("#allowforfeepay").val('');
        $("#feespaid").val('');
        $("#docverification").val('');
        $("#nodues").val('');
        $("#quotaid").val('');
        $("#stytypeid").val('');
        $("#fathername").val('');
        $("#rankenroll").val('');
        $("#totalCredit").val('');
        $("#creditTaken").val('');
        $("#remainingCredit").val('');
        $("#minCredit").val('');
        $("#userType").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        var enrolment = $("#semCode").val().split("~@~");
        var registrationid = enrolment[0];
        $("#acadyear").empty();
        $("#programCode").empty();
        $("#programCode").trigger("chosen:updated");
        $("#enrollmentNo").empty();
        $("#enrollmentNo").trigger("chosen:updated");
        $('#printbutton').hide();
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        var instids = '';
        var students = $("input[name='students']:checked").val();
        if (students == 'programAcadWise') {
            instids = $('#multiinstituteid').val();
        } else {
            instids = $('#multiinstituteid2').val();
        }
        $.ajax({
            type: "POST", url: "addDropSubject/getAcademicYear",
            data: '&semCode=' + registrationid + '&instituteids=' + instids,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false, beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.acadyearList, function (i, item)
                {
                    str1 += '<option value="' + item + '">' + item + '</option>'
                });
                $("#acadyear").append(str1);
                $("#acadyear").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function getProgram() {
        $("#noduesbtn").hide();
        $("#studentid").val('');
        $("#stuName").val('');
        $("#academic").val('');
        $("#programid").val('');
        $("#styNumber").val('');
        $("#branchid").val('');
        $("#program").val('');
        $("#sectionid").val('');
        $("#batchSubBatch").val('');
        $("#batchSubBatch1").val('');
        $("#subsectionid").val('');
        $("#groupid").val('');
        $("#enroll").val('');
        $("#allowforfeepay").val('');
        $("#feespaid").val('');
        $("#docverification").val('');
        $("#nodues").val('');
        $("#quotaid").val('');
        $("#stytypeid").val('');
        $("#fathername").val('');
        $("#rankenroll").val('');
        $("#totalCredit").val('');
        $("#creditTaken").val('');
        $("#remainingCredit").val('');
        $("#minCredit").val('');
        $("#userType").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        var enrolment = $("#semCode").val().split("~@~");
        var registrationid = enrolment[0];
        $("#programCode").empty();
        $("#enrollmentNo").empty();
        $("#enrollmentNo").trigger("chosen:updated");
        $('#printbutton').hide();
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        var instids = '';
        var students = $("input[name='students']:checked").val();
        if (students == 'programAcadWise') {
            instids = $('#multiinstituteid').val();
        } else {
            instids = $('#multiinstituteid2').val();
        }
        $.ajax({
            type: "POST", url: "addDropSubject/getProgram",
            data: '&semCode=' + registrationid + '&acadyear=' + $("#acadyear").val() + '&instituteids=' + instids,
            dataType: "json", async: true,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.programList, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>'
                });
                $("#programCode").append(str1);
                $("#programCode").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function getStudentInfo(val) {
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        var instids = '';
        var students = $("input[name='students']:checked").val();
        if (students == 'programAcadWise') {
            instids = $('#multiinstituteid').val();
        } else {
            instids = $('#multiinstituteid2').val();
        }
        var regid = $("#semCode2").val().split("~@~");
        var enrollmentno = $("#enrollmentNo1").val();
        if (enrollmentno != '') {
            $.ajax({
                type: "POST",
                url: "addDropSubject/getStudetnInfo",
                data: '&enrollmentno=' + enrollmentno + '&semCode2=' + regid[0] + '&instituteids=' + instids,
                dataType: "json",
                async: true,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.studentInfo != '' && data.studentInfo != null) {
                        $.each(data.studentInfo, function (i, item)
                        {
                            $("#programcode1").val(item[0]);
                            $("#academicyear1").val(item[1]);
                            $("#selectedstudentid").val(item[2]);
                            $("#modeparameter").val(item[6]);
                        });
                    } else {
                        BootstrapDialog.alert("Invalid Enrollment No.");
                        $("#enrollmentNo1").val('');
                        $("#selectedstudentid").val('');
                        $("#academicyear1").val('');
                        $("#programcode1").val('');
                    }
                    if (val == 1) {
                        getStudent(1);
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        }
    }

    function getStudent(val) {
        $("#studentinstituteid").val('');
        $("#studentregistrationid").val('');
        $("#noduesbtn").hide();
        $("#studentid").val('');
        $("#stuName").val('');
        $("#academic").val('');
        $("#programid").val('');
        $("#styNumber").val('');
        $("#branchid").val('');
        $("#program").val('');
        $("#sectionid").val('');
        $("#batchSubBatch").val('');
        $("#batchSubBatch1").val('');
        $("#subsectionid").val('');
        $("#groupid").val('');
        $("#enroll").val('');
        $("#allowforfeepay").val('');
        $("#feespaid").val('');
        $("#docverification").val('');
        $("#nodues").val('');
        $("#quotaid").val('');
        $("#stytypeid").val('');
        $("#fathername").val('');
        $("#rankenroll").val('');
        $("#totalCredit").val('');
        $("#creditTaken").val('');
        $("#remainingCredit").val('');
        $("#minCredit").val('');
        $("#userType").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        $('#printbutton').hide();
        $("#enrollmentNo").empty().trigger("chosen:updated");
        $("#enrollmentNo11").empty().trigger("chosen:updated");
        var stuid = $("#selectedstudentid").val();
        var students = $("input[name='students']:checked").val();
        var regid = '';
        var acadyear = '';
        var progcode = '';
        var instids = '';
        if (students == 'programAcadWise') {
            var enrolment = $("#semCode").val().split("~@~");
            $("#registrationid").val(enrolment[0]);
            $("#semCode1").val(enrolment[1]);
            regid = $("#registrationid").val();
            acadyear = $("#acadyear").val();
            progcode = $("#programCode").val();
            instids = $('#multiinstituteid').val();
        } else {
            var enrolment = $("#semCode2").val().split("~@~");
            $("#registrationid").val(enrolment[0]);
            $("#semCode1").val(enrolment[1]);
            regid = enrolment[0];
            acadyear = $("#academicyear1").val();
            progcode = $("#programcode1").val();
            instids = $('#multiinstituteid2').val();
        }
        $.ajax({
            type: "POST", url: "addDropSubject/getStudentDetail",
            data: '&semCode=' + regid + '&acadyear=' + acadyear + '&programCode=' + progcode + '&instituteids=' + instids,
            dataType: "json",
            async: true, timeout: 5000, cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str2 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.studentList, function (i, item)
                {
                    str1 += '<option value="' + item[0] + "~@~" + item[1] + "~@~" + item[3] + "~@~" + item[4] + "~@~" + item[6] + "~@~" + item[7] + "~@~" + item[5] + ' / ' + item[8] + '' + "~@~" + item[9] + "~@~" + item[10] + "~@~" + item[11] + "~@~" + item[12] + "~@~" + item[2] + "~@~" + item[13] + "~@~" + item[14] + "~@~" + item[15] + "~@~" + item[16] + "~@~" + item[17] + "~@~" + item[18] + "~@~" + item[19] + "~@~" + item[20] + "~@~" + item[21] + "~@~" + item[22] + '">' + item[2] + " - " + item[1] + '</option>'
                    if (item[0] == stuid) {
                        str2 += '<option selected="true" value="' + item[0] + "~@~" + item[1] + "~@~" + item[3] + "~@~" + item[4] + "~@~" + item[6] + "~@~" + item[7] + "~@~" + item[5] + ' / ' + item[8] + '' + "~@~" + item[9] + "~@~" + item[10] + "~@~" + item[11] + "~@~" + item[12] + "~@~" + item[2] + "~@~" + item[13] + "~@~" + item[14] + "~@~" + item[15] + "~@~" + item[16] + "~@~" + item[17] + "~@~" + item[18] + "~@~" + item[19] + "~@~" + item[20] + "~@~" + item[21] + "~@~" + item[22] + '">' + item[2] + " - " + item[1] + '</option>'
                    }
                });
                $("#enrollmentNo").append(str1);
                $("#enrollmentNo").trigger("chosen:updated");
                $("#enrollmentNo11").append(str2);
                $("#enrollmentNo11").trigger("chosen:updated");
                if (val == 1) {
                    getStudentDetail();
                }
            }, error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function getStudentDetail() {
        $("#studentid").val('');
        $("#AddSubject").empty();
        $("#dropStudent").empty();
        $("#studentactivity").empty();
        $("#noduesbtn").hide();
        $("#addreg").prop("disabled", true);
        $("#addgip").prop("disabled", true);
        $("#addaudit").prop("disabled", true);
        $('#printbutton').hide();
        $("#nodues").val('');
        var students = $("input[name='students']:checked").val();
        var studentdata = '';
        var regid = '';
        var instids = '';
        if (students == 'programAcadWise') {
            studentdata = $("#enrollmentNo").val().split("~@~");
            regid = $("#registrationid").val();
            instids = $('#multiinstituteid').val();
        } else {
            var reg = $("#semCode2").val().split("~@~");
            regid = reg[0];
            instids = $('#multiinstituteid2').val();
            studentdata = $("#enrollmentNo11").val().split("~@~");
        }
        $("#studentid").val(studentdata[0]);
        $("#stuName").val(studentdata[1]);
        $("#academic").val(studentdata[2]);
        $("#programid").val(studentdata[3]);
        $("#styNumber").val(studentdata[4]);
        $("#branchid").val(studentdata[5]);
        if (studentdata[6] != '' && studentdata[6] != null && studentdata[6] != 'null') {
            $("#program").val(studentdata[6]);
        } else {
            $("#program").val('');
        }
        $("#sectionid").val(studentdata[7]);
        $("#batchSubBatch").val(studentdata[8] + " / " + studentdata[19]);
        $("#subsectionid").val(studentdata[9]);
        $("#groupid").val(studentdata[10]);
        $("#enroll").val(studentdata[11]);
        $("#allowforfeepay").val(studentdata[12]);
        $("#feespaid").val(studentdata[13]);
        $("#docverification").val(studentdata[14]);
        $("#quotaid").val(studentdata[15]);
        $("#stytypeid").val(studentdata[16]);
        $("#fathername").val(studentdata[17]);
        $("#rankenroll").val(studentdata[18] + " / " + studentdata[11]);
        $("#batchSubBatch1").val(studentdata[19]);
        $("#studentinstituteid").val(studentdata[20]);
        $("#studentregistrationid").val(studentdata[21]);
        $.ajax({
            type: "POST",
            url: "addDropSubject/getActivity",
            data: '&studentid=' + $("#studentid").val() + '&semCode=' + regid + '&academic=' + $("#academic").val()
                    + '&programid=' + $("#programid").val() + '&styNumber=' + $("#styNumber").val() + '&branchid=' + $("#branchid").val()
                    + '&sectionid=' + $("#sectionid").val() + '&subsectionid=' + $("#subsectionid").val() + '&groupid=' + $("#groupid").val() + '&instituteids=' + instids
                    + '&studentinstituteid=' + studentdata[20] + '&studentregistrationid=' + studentdata[21],
            dataType: "json", async: true,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            }, success: function (data) {
                var str = '';
                var total_credit = '';
                var taken_credit = '';
                var rema_credit = '';
                var minCredit = '';
                var userType = '';
                $.each(data.studentActivity, function (i, item)
                {
                    str += '<tr>';
                    str += '  <td>' + item.activity + '</td>';
                    if (item.activityStatus == 'N') {
                        str += '  <td>No</td>';
                        $("#nodues").val(item.activityStatus);
                        $("#noduesbtn").show();
                    } else {
                        str += '  <td>Yes</td>';
                    }
                    str += '  <td>' + item.remark + '</td>';
                    str += '  </tr> ';
                });
                $("#studentactivity").append(str);
                $.each(data.creditList, function (i, item)
                {
                    total_credit = item.maxCredit;
                    taken_credit = item.takenCredit;
                    rema_credit = item.remaningCredit;
                    minCredit = item.minCredit;
                    userType = item.userType;
                });
                $("#totalCredit").val(total_credit);
                $("#creditTaken").val(taken_credit);
                $("#remainingCredit").val(rema_credit);
                $("#minCredit").val(minCredit);
                $("#userType").val(userType);
            }, error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function loadData() {
        setTimeout(function () {
            var students = $("input[name='students']:checked").val();
            var regid = '';
            var flag = '';
            var showclassstartdate = $("#classDateflag").val();
            if (students == 'programAcadWise') {
                regid = $("#registrationid").val();
                if ($("#semCode").val() == '') {
                    flag = 'semcode';
                } else if ($("#enrollmentNo").val() == '') {
                    flag = 'enrollno';
                } else if ($("#programCode").val() == '') {
                    flag = 'progcode';
                } else if ($("#acadyear").val() == '') {
                    flag = 'acadyear';
                }
            } else {
                var reg = $("#semCode2").val().split("~@~");
                regid = reg[0];
                if ($("#semCode2").val() == '') {
                    flag = 'semcode';
                } else if ($("#enrollmentNo1").val() == '') {
                    flag = 'enrollno';
                }
            }
            var studentinstituteid = $("#studentinstituteid").val();
            var studentregistrationid = $("#studentregistrationid").val();
            if (flag == '') {
                if ($("#allowforfeepay").val() == 'N') {
                    BootstrapDialog.alert("Fee not submitted yet");
                } else if ($("#feespaid").val() == 'N') {
                    BootstrapDialog.alert("Student Fee is not submitted yet");
                } else if ($("#docverification").val() == 'N') {
                    BootstrapDialog.alert("Documents are not verified");
                } else if ($("#nodues").val() == 'N') {
                    BootstrapDialog.alert("Dues are not cleared");
                } else {
                    $("#noduesbtn").hide();
                    $("#addgip").prop("disabled", false);
                    $("#addaudit").prop("disabled", false);
                    $("#AddSubject").empty();
                    $("#dropStudent").empty();
                    $.blockUI();
                    $.ajax({
                        type: "POST",
                        url: "addDropSubject/getAddDropSubject",
                        data: '&studentid=' + $("#studentid").val() + '&semCode=' + regid + '&academic=' + $("#academic").val()
                                + '&programid=' + $("#programid").val() + '&styNumber=' + $("#styNumber").val() + '&branchid=' + $("#branchid").val()
                                + '&sectionid=' + $("#sectionid").val() + '&subsectionid=' + $("#subsectionid").val() + '&quotaid=' + $("#quotaid").val() + '&stytypeid=' + $("#stytypeid").val()
                                + '&studentinstituteid=' + studentinstituteid + '&studentregistrationid=' + studentregistrationid,
                        dataType: "json",
                        async: true,
                        timeout: 10000,
                        cache: false,
                        beforeSend: function (xhr, options) {
                            return true;
                        },
                        success: function (data) {
                            debugger;
                            var str = '';
                            var count = new Array();
                            var cou = 0;
                            var criditSum = 0;
                            var todayTime = new Date();
                            var month = (todayTime.getMonth() + 1) < 10 ? '0' + (todayTime.getMonth() + 1) : '' + (todayTime.getMonth() + 1);
                            var day = todayTime.getDate() < 10 ? '0' + todayTime.getDate() : '' + todayTime.getDate();
                            var year = todayTime.getFullYear();
                            if (data.alert == '') {
                                if (data.RegComf != '') {
                                    var reg = data.RegComf;
                                    var regconfflag = reg.split('~@~')[0];
                                    var regconfdate = reg.split('~@~')[1];
                                    $("#regconfflag").val(regconfflag);
                                    $("#regconfdate").val(regconfdate);
                                    $('#printbutton').show();
                                }
                                var droptotalsubject = 0;
                                $.each(data.dropList, function (i, item1) {
                                    var str1 = '';
                                    var str2 = '';
                                    str += '<tr id="' + item1.instituteid + '~@~' + item1.registrationid + '~@~' + item1.studentid + '~@~' + item1.academicyear + '~@~' + item1.programid + '~@~' + item1.sectionid + '~@~' + item1.subsectionid + '~@~' + item1.subjectid + '~@~' + item1.stynumber + '~@~' + item1.basketid + '~@~' + item1.subjtype + '~@~' + item1.totalccpmarks + '">';
                                    str += '<td id="' + item1.instituteid + '~@~' + item1.registrationid + '~@~' + item1.studentid + '~@~' + item1.academicyear + '~@~' + item1.programid + '~@~' + item1.sectionid + '~@~' + item1.subsectionid + '~@~' + item1.subjectid + '~@~' + item1.stynumber + '~@~' + item1.basketid + '~@~' + item1.subjtype + '~@~' + item1.totalccpmarks + '"><input type="checkbox" class="hidden" id="chk1' + i + '"  value="' + item1.instituteid + '~@~' + item1.registrationid + '~@~' + item1.studentid + '~@~' + item1.academicyear + '~@~' + item1.programid + '~@~' + item1.sectionid + '~@~' + item1.subsectionid + '~@~' + item1.subjectid + '~@~' + item1.stynumber + '~@~' + item1.basketid + '~@~' + item1.subjtype + '~@~' + item1.totalccpmarks + '"/>';
                                    str += '<img id="del_img' + i + '" alt="er" src="resources/img/if_trash.png"  onclick="callChkBox(' + i + ');"/>';
                                    str += '<img id="d_img' + i + '" class="hide" alt="er" src="resources/img/if_trash1.png"  onclick="callChkBox(' + i + ');"/>';
                                    str += '  </td>';
                                    str += '  <td>' + item1.subjectcode + "-" + item1.subjectdesc + '</td>';
//                                str2 += '<select class="form-control" id="dmode' + i + '"  name="dmode' + i + '"   data-live-search="true">';
//                                str += ' <td> ' + str2 + '</td>';
                                    str += '  <td>' + item1.subjtype.split("<br>")[0] + '<br>' + item1.subjtype.split("<br>")[2] + '</td>';
                                    str1 += '<input type="checkbox" checked="true" class="hidden" id="regconfirmchk' + i + '"  value=""/><select class="form-control" id="dropbasketDetail' + i + '"  name="dropbasketDetail' + i + '"  multiple="true" data-live-search="true"disabled="true">';
                                    str += ' <td> ' + str1 + '</td>';
                                    //                                str += '  <td>' + item1.subjectgroup + '</td>';
                                    str += '  <td>' + item1.stynumber + '</td>';
                                    str += '  <td>' + item1.totalccpmarks + '</td>';
                                    str += '  </tr> ';
                                    criditSum += parseInt(item1.totalccpmarks);
                                    count.push(cou++);
                                    droptotalsubject++;
                                });
                                if (droptotalsubject == 0) {
                                    $('#printbutton').hide();
                                    BootstrapDialog.alert("Subject not found for this Student...!");
                                }
                                $("#dropStudent").append(str);
                                $("#countval1").val(count);
                                $("#countvalforregconfirm").val(cou);
                                $("#criditSum").val(criditSum);
                                //                            $.each(data.dropList, function (q, item3) {
//                                var str2 = '';
//                                $("#dmode" + q).chosen({width: "100%"});
//                                $.each(item3.modeList, function (j, item2) {
//                                    str2 += '<option value="' + item2.mode + '">' + item2.modedesc + '</option>';
//                                });
//                                $("#dmode" + q).append(str2);
//                                $("#dmode" + q).trigger("chosen:updated");
//                            });
                                $.each(data.dropList, function (q, item3) {
                                    var str2 = '';
                                    var fstid = '';
                                    $("#dropbasketDetail" + q).chosen({width: "100%"});
                                    $.each(item3.batchList, function (j, item2) {
                                        if (j == 0) {
                                            fstid += item2.fstid;
                                        } else {
                                            fstid += ',' + item2.fstid;
                                        }
                                        str2 += '<option value="' + item2.batchid + '~@@~' + item2.componentid + '" selected>' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                    });
                                    $("#regconfirmchk" + q).val(fstid);
                                    $("#dropbasketDetail" + q).append(str2);
                                    $("#dropbasketDetail" + q).trigger("chosen:updated");
                                });
                                var str = '';
                                var count = new Array();
                                var count2 = new Array();
                                var cou = 0;
                                var cou1 = 0;
                                var count1 = 0;
                                var lecList = [];
                                var tutList = [];
                                var pracList = [];
                                if (data.addList != '' && data.addList != null) {
                                    $.each(data.addList, function (i, item1)
                                    {
                                        debugger;
                                        $.unblockUI();
                                        $("#addreg").prop("disabled", false);
                                        var str1 = '';
                                        var str2 = '';
                                        var str3 = '';
                                        var switchelectivesubject = '';
                                        switchelectivesubject += '<button type="button" class="btn btn-info" data-toggle="modal" onclick=javascript:getElectiveSubjectForSwap("' + encodeURI(item1.SubjectTypeid) + '","' + encodeURI(item1.totalccpmarks) + '","' + encodeURI(item1.subjectid) + '","' + encodeURI(item1.basketid) + '"); data-target="#myModalE">Swap Elective</button>';
                                        var regconfdate = $("#regconfdate").val();
                                        if (regconfdate != '') {
                                            var date = regconfdate.split('/');
                                            $('#classStartDate').val(date[0] + "/" + date[1] + "/" + date[1]);

                                            if (showclassstartdate == 'Y') {
                                                str3 += '<input type="date" class="form-control" id="regconfirmdate' + i + '" name="regconfirmdate' + i + '" value="' + date[2] + "-" + date[1] + "-" + date[0] + '" onblur="checkDate(' + i + ');"/>';
                                            } else {
                                                str3 += '<input type="date" style="display:none" class="form-control" id="regconfirmdate' + i + '" name="regconfirmdate' + i + '" value="' + date[2] + "-" + date[1] + "-" + date[0] + '" onblur="checkDate(' + i + ');"/>';
                                            }
                                        } else {
                                            $('#classStartDate').val(day + "/" + month + "/" + year);
                                            if (showclassstartdate == 'Y') {
                                                str3 += '<input type="date" class="form-control" id="regconfirmdate' + i + '" name="regconfirmdate' + i + '" value="' + year + "-" + month + "-" + day + '" onblur="checkCurrentDate(' + i + ');"/>';
                                            } else {
                                                str3 += '<input type="date" style="display:none" class="form-control" id="regconfirmdate' + i + '" name="regconfirmdate' + i + '" value="' + year + "-" + month + "-" + day + '" onblur="checkCurrentDate(' + i + ');"/>';
                                            }
                                        }

                                        str += '<tr>';
                                        str += '<td><input type="checkbox"  onclick=javascript:checkedSameGroup("' + item1.subjectgroup + '~~@~~' + i + '~~@~~' + item1.LecList.length + '~~@~~' + item1.TutList.length + '~~@~~' + item1.PracList.length + '");  id="chk2' + i + '"  value="' + item1.instituteid + '~@~' + item1.registrationid + '~@~' + item1.studentid + '~@~' + item1.academicyear + '~@~' + item1.programid + '~@~' + item1.sectionid + '~@~' + item1.subsectionid + '~@~' + item1.subjectid + '~@~' + item1.stynumber + '~@~' + item1.basketid + '~@~' + item1.subjtype + '~@~' + item1.totalccpmarks + '~@~' + item1.subjectgroup + '~@~' + item1.departmentid + '"/>';
                                        str += '  </td>';
                                        str += '  <td>' + item1.subjectcode + "-" + item1.subjectdesc + '</td>';
//                                str2 += '<select class="form-control" id="mode1' + i + '"  name="mode1' + i + '"   data-live-search="true">';
//                                str += ' <td> ' + str2 + '</td>';
                                        str += '  <td>' + item1.subjtype.split("<br>")[0] + '<br>' + item1.subjtype.split("<br>")[2] + '</td>';
                                        if (item1.LecList.length > 0) {
                                            $.each(item1.LecList, function (j, item2) {
                                                if (j === 0) {
//                                                 item2.componentcode + 
                                                    str1 += '<select class="form-control" id="addbasketDetail' + cou1 + '"  name="addbasketDetail' + cou1 + '"  data-live-search="true">';
                                                    str1 += '<option value="">Select</option></select>';
                                                    cou1++;
                                                }

                                            });
                                        }
                                        if (item1.TutList.length > 0) {
                                            $.each(item1.TutList, function (j, item2) {
                                                if (j === 0) {
//                                                 item2.componentcode + 
                                                    str1 += '<select class="form-control" id="addbasketDetail' + cou1 + '"  name="addbasketDetail' + cou1 + '"  data-live-search="true">';
                                                    str1 += '<option value="">Select</option></select>';
                                                    cou1++;
                                                }

                                            });
                                        }
                                        if (item1.PracList.length > 0) {
                                            $.each(item1.PracList, function (j, item2) {
                                                if (j === 0) {
//                                                 item2.componentcode + 
                                                    str1 += '<select class="form-control" id="addbasketDetail' + cou1 + '"  name="addbasketDetail' + cou1 + '"  data-live-search="true">';
                                                    str1 += '<option value="">Select</option></select>';
                                                    cou1++;
                                                }

                                            });
                                        }
                                        if (str1 != "") {
                                            str += ' <td> ' + str1 + '</td>';
                                        } else {
                                            var data = "'" + item1.departmentid + "," + item1.subjectid + "," + item1.basketid + "'";
                                            var facultyFor = "'SD'";
                                            var subjectcodedesc = "'" + item1.subjectcode + "-" + item1.subjectdesc + "'";
                                            str += ' <td style="text-align:center"><button type="button" id="assignFaculty1" onclick="setSubjectcodedesc(' + subjectcodedesc + ');setDepId(' + data + ');assignFaculty(' + facultyFor + ');" class="btn btn-info">Assign Faculty</button> </td>';
                                        }
                                        //                                str += '  <td>' + item1.subjectgroup + '</td>';
                                        str += '  <td>' + item1.stynumber + '</td>';
                                        if (showclassstartdate == 'Y') {
                                            str += '  <td>' + item1.totalccpmarks + '</td>';//credit
                                            str += '  <td>' + str3 + '</td>';
                                        } else {
                                            str += '  <td>' + item1.totalccpmarks + '' + str3 + '</td>';//credit
                                        }
                                        if (item1.subjtype.split("<br>")[0] != 'CURRENT' && item1.subjtype.split("<br>")[1] != 'C') {
                                            str += '  <td>' + switchelectivesubject + '</td>';
                                        } else {
                                            str += '  <td></td>';
                                        }
                                        str += '  </tr> ';
                                        count.push(cou++);
                                    });
                                    $('#hint').attr("data-original-title", "");
                                } else {
                                    $('#hint').attr("data-original-title", "No Subject Found");
                                }
                                $("#AddSubject").append(str);
                                $("#countval").val(count);
                                //                            $.each(data.addList, function (q, item3) {
//                                var str2 = '';
//                                $("#mode1" + q).chosen({width: "100%"});
//                                $.each(item3.modeList, function (j, item2) {
//                                    str2 += '<option value="' + item2.mode + '">' + item2.modedesc + '</option>';
//                                });
//                                $("#mode1" + q).append(str2);
//                                $("#mode1" + q).trigger("chosen:updated");
//                            });
                                $.each(data.addList, function (q, item3) {

                                    if (item3.LecList.length > 0) {
                                        var str2 = '';
                                        $("#addbasketDetail" + count1).chosen({width: "100%"});
                                        $.each(item3.LecList, function (j, item2) {
                                            var subsection = item2.batchcode.split('/')[3];
                                            if (subsection == $("#batchSubBatch1").val()) {
                                                if ($("#userType").val() != 'A') {
                                                    $("#addbasketDetail" + count1).prop("disabled", true);
                                                }
                                                str2 += '<option selected="true" value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '~@@~' + item2.mergefstid + '">' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                            } else {
                                                str2 += '<option value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '~@@~' + item2.mergefstid + '">' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                            }
                                        });
                                        $("#addbasketDetail" + count1).append(str2);
                                        $("#addbasketDetail" + count1).trigger("chosen:updated");
                                        count2.push(count1++);
                                    }

                                    if (item3.TutList.length > 0) {
                                        var str2 = '';
                                        $("#addbasketDetail" + count1).chosen({width: "100%"});
                                        $.each(item3.TutList, function (j, item2) {
                                            var subsection = item2.batchcode.split('/')[3];
                                            if (subsection == $("#batchSubBatch1").val()) {
                                                if ($("#userType").val() != 'A') {
                                                    $("#addbasketDetail" + count1).prop("disabled", true);
                                                }
                                                str2 += '<option selected="true" value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '~@@~' + item2.mergefstid + '">' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                            } else {
                                                str2 += '<option value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '~@@~' + item2.mergefstid + '">' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                            }
                                        });
                                        $("#addbasketDetail" + count1).append(str2);
                                        $("#addbasketDetail" + count1).trigger("chosen:updated");
                                        count2.push(count1++);
                                    }
                                    if (item3.PracList.length > 0) {
                                        var str2 = '';
                                        $("#addbasketDetail" + count1).chosen({width: "100%"});
                                        $.each(item3.PracList, function (j, item2) {
                                            var subsection = item2.batchcode.split('/')[3];
                                            if (subsection == $("#batchSubBatch1").val()) {
                                                if ($("#userType").val() != 'A') {
                                                    $("#addbasketDetail" + count1).prop("disabled", true);
                                                }
                                                str2 += '<option  selected="true" value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '~@@~' + item2.mergefstid + '">' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                            } else {
                                                str2 += '<option   value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '~@@~' + item2.mergefstid + '">' + item2.batchcode + '/' + item2.componentcode + '</option>';
                                            }
                                        });
                                        $("#addbasketDetail" + count1).append(str2);
                                        $("#addbasketDetail" + count1).trigger("chosen:updated");
                                        count2.push(count1++);
                                    }

                                });
                                $("#countBasket").val(count2);
                                $("#leclistforelectiveswap").val(lecList.join("~@#~"));
                                $("#tutlistforelectiveswap").val(tutList.join("~@#~"));
                                $("#praclistforelectiveswap").val(pracList.join("~@#~"));
                                setTimeout($.unblockUI, 1000);
                            } else {
                                BootstrapDialog.alert(data.alert);
                                $('#printbutton').hide();
                                setTimeout($.unblockUI, 1000);
                            }
                            setTimeout($.unblockUI, 1000);
                            $.unblockUI();
                        },
                        error: function (response) {
                            toastr.warning('Something Wrong...............', "Warning!!");
                            setTimeout($.unblockUI, 1000);
                            $.unblockUI();
                        }
                    });
                }
            } else {
                if (flag == 'enrollno') {
                    if (students == 'programAcadWise') {
                        BootstrapDialog.alert("Please Select  Enrollment No.");
                    } else {
                        BootstrapDialog.alert("Please Enter Enrollment No.");
                    }
                } else if (flag == 'progcode') {
                    BootstrapDialog.alert("Please Select Program..");
                } else if (flag == 'acadyear') {
                    BootstrapDialog.alert("Please Select Academic Year..");
                } else if (flag == 'semcode') {
                    BootstrapDialog.alert("Please Select Semester Code..");
                }
            }
        }, 1000);
    }


    function callChkBox(index) {
        var value = $("#chk1" + index).val();
        $("#chk1" + index).prop("checked", true);
        BootstrapDialog.confirm("Are you sure you want to drop this Subject?", function (Yes) {
            if (Yes) {
                $.ajax({type: "POST",
                    url: "addDropSubject/datacheckfordelete",
                    data: '&instituteid=' + value.split("~@~")[0] + '&registrationid=' + value.split("~@~")[1] + '&subjectid=' + value.split("~@~")[7] + '&studentid=' + value.split("~@~")[2],
                    datatype: 'json',
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        if (data.status == "NODATA") {
                            deleteSubject(value, index);
                        } else {
                            if (data.status == "SESM") {
                                BootstrapDialog.alert("Student Event Subject Marks is done for this subject. You can't drop this subject");
                            } else {
                                BootstrapDialog.alert("Student Attendance is done for this subject. Are you sure you want to drop this Subject?");
                                if (Yes) {
                                    deleteSubject(value, index);
                                }
                            }
                        }
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            }
        });
    }

    function deleteSubject(v, i) {
        var selectedcheckboxvalue = '';
        var selectedDropvalue = '';
        var creditTaken = $("#creditTaken").val();
        var minCredit = $("#minCredit").val();
        selectedcheckboxvalue += $('#chk1' + i + '').val();
        selectedDropvalue += $('#dropbasketDetail' + i + '').val();
        var dropCredit = selectedcheckboxvalue.split('~@~')[11];
        if ((creditTaken - dropCredit) < minCredit && minCredit != '0') {
            BootstrapDialog.alert("If you drop this subject, Credits taken(" + (creditTaken - dropCredit) + ") are getting lesser than Min Required Credits(" + minCredit + ") so you can't Drop the subject!! ");
        } else {
            var countval1 = $("#countval1").val().split(",");
            if ($('#chk1' + i + '').is(':checked')) {
                if ($('#dropbasketDetail' + i + '').val() == 'null' || $('#dropbasketDetail' + i + '').val() == '') {
                    BootstrapDialog.alert("Please Select the Subject Batch Detail");
                } else {
                    $.ajax({type: "POST",
                        url: "addDropSubject/delete",
                        data: '&selectedDropvalue=' + selectedDropvalue + '&selectedcheckboxvalue=' + selectedcheckboxvalue + '&dropListLength=' + countval1, dataType: "json",
                        async: false,
                        timeout: 5000,
                        cache: false,
                        beforeSend: function (xhr, options) {
                            return true;
                        },
                        success: function (data) {
                            if (data.status === 'Success') {
                                toastr.success('Record Successfully Delete', "Success!!");
                                var students = $("input[name='students']:checked").val();
                                if (students == 'programAcadWise') {
                                    getStudentDetail();
                                } else {
                                    getStudentInfo();
                                    getStudentDetail();
                                }
                                loadData();
                            } else {
                                BootstrapDialog.alert(data.status);
                            }
                        },
                        error: function (response) {
                            toastr.warning('Something Wrong...............', "Warning!!");
                        }
                    });
                }
            }
        }

    }

    function saveData()
    {
        var subjects = 0;
        var selectedcheckboxvalue = '';
        var selectedcheckbox = '';
        var selectedBaskete1 = '';
        var selectedBaskete2 = '';
        var innercount = 0;
        var creditcount = 0;
        var selectedMode = '';
        var regConfirmDate = '';
        var flag = true;
        var countval = [];
        countval = $("#countval").val().split(",");
        var countBasket = [];
        countBasket = $("#countBasket").val().split(",");
        for (var j = 0; j < countval.length; j++) {
            if ($('#chk2' + j).is(':checked')) {
                subjects++;
            }
        }
        if (subjects === 0)
        {
            BootstrapDialog.alert("Please Select Atleast One Subject");
        } else
        {
            var cc = 0;
            for (var i = 0; i < countval.length; i++)
            {
                var innercount1 = 0;
                var selectedBaskete = '';
                debugger;
                if ($('#chk2' + i + '').is(':checked'))
                {
                    if ($('#regconfirmdate' + i + '').val() == '') {
                        BootstrapDialog.alert("Please Select Class Start Date");
                        flag = false;
                    } else {
                        innercount++;
                        if (innercount === 1) {
                            selectedcheckboxvalue += $('#chk2' + i + '').val();
                            var countCredit = [];
                            countCredit = selectedcheckboxvalue.split('~@~');
                            creditcount = parseInt(countCredit[11]);
                        } else {
                            selectedcheckboxvalue += ',' + $('#chk2' + i + '').val();
                            var selectedcheckboxvalue1 = $('#chk2' + i + '').val();
                            var countCredit = [];
                            countCredit = selectedcheckboxvalue1.split('~@~'); // for credit Check MIN and Max limit
                            creditcount += parseInt(countCredit[11]);
                        }

                        selectedcheckbox = $('#chk2' + i + '').val();
                        var countval1 = [];
                        countval1 = selectedcheckbox.split("~@~");
                        var flag1 = true;
                        for (var cc = 0; cc < countBasket.length; cc++) {
                            selectedBaskete1 = $('#addbasketDetail' + cc + '').val();
                            var subj = [];
                            subj = selectedBaskete1.split("~@@~");
                            //select same subject batch(LTP) form all batches by compare subject
                            if (countval1[7] === subj[2]) {
                                innercount1++;
                                if (innercount1 === 1) {
                                    selectedBaskete += $('#addbasketDetail' + cc + '').val();
                                } else {
                                    selectedBaskete += '#~#' + $('#addbasketDetail' + cc + '').val();
                                }
                                flag1 = false;
                            }
                        }
                        //selectedMode = $('#mode1' + i + '').val();
                        if ($("#modeparameter").val() == 'N') {
                            selectedMode = 'S';
                        } else {
                            selectedMode = 'E';
                        }
                        regConfirmDate = $('#regconfirmdate' + i + '').val();
                        selectedcheckboxvalue += '~#@~' + selectedBaskete + '~@~' + selectedMode + '~@~' + regConfirmDate;
                    }
                    if (!flag && flag1) {
                        break;
                    }
                }
            }
            if (flag1) {
                BootstrapDialog.alert("Please Select the Subject Batch Detail");
            } else {
                creditcount += parseInt($("#criditSum").val());
                if (flag && creditcount <= parseInt($("#totalCredit").val())) {
                    saveAdd_BackSubject(selectedcheckboxvalue, selectedMode);
                } else {
                    if (flag && creditcount > parseInt($("#totalCredit").val())) {
                        var wieghtage = parseInt($("#totalCredit").val());
                        if ($("#userType").val() === "A") {
                            BootstrapDialog.confirm("Credit exceed form " + wieghtage + " You want to Add Subject? ", function (Yes) {
                                if (Yes) {
                                    saveAdd_BackSubject(selectedcheckboxvalue, selectedMode);
                                }
                            })
                        } else {
                            BootstrapDialog.alert("Credit exceed form " + wieghtage + " ");
                        }

                    }
                }
            }
        }
    }

    function saveAdd_BackSubject(selectedcheckboxvalue, selectedMode) {
        $.ajax({
            type: "POST",
            url: "addDropSubject/saveAdd_BackSubject",
            data: '&selectedcheckboxvalue=' + selectedcheckboxvalue + '&selectedMode=' + selectedMode,
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.status === 'Success') {
                    toastr.success('Record Successfully Saved.', "Success!!");
                    $('#myModal').modal('hide');
                    var students = $("input[name='students']:checked").val();
                    if (students == 'programAcadWise') {
                        getStudentDetail();
                    } else {
                        getStudentInfo();
                        getStudentDetail();
                    }
                    loadData();
                } else {
                    BootstrapDialog.alert(data.status);
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function GipSubjectData() {
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        if ($("#allowforfeepay").val() == 'N') {
            BootstrapDialog.alert("Fee not submitted yet");
        } else if ($("#feespaid").val() == 'N') {
            BootstrapDialog.alert("Student Fee is not submitted yet");
        } else if ($("#docverification").val() == 'N') {
            BootstrapDialog.alert("Documents are not verified");
        } else if ($("#nodues").val() == 'N') {
            BootstrapDialog.alert("Dues are not cleared");
        } else {
            $("#myModal1").modal();
            $("#regCode2").val($("#semCode1").val());
            $("#regNo2").val($("#enroll").val());
            $("#name2").val($("#stuName").val());
            $("#styNo2").val($("#styNumber").val());
            $("#totalCredit2").val($("#totalCredit").val());
            $("#creditTaken2").val($("#creditTaken").val());
            $("#remainingCredit2").val($("#remainingCredit").val());
            $("#AddGIPSubject").empty();
            $.blockUI();
            $.ajax({
                type: "POST",
                url: "addDropSubject/getGIPSubject",
                data: '&studentid=' + $("#studentid").val() + '&semCode=' + $("#registrationid").val() + '&academic=' + $("#academic").val()
                        + '&programid=' + $("#programid").val() + '&styNumber=' + $("#styNumber").val() + '&branchid=' + $("#branchid").val()
                        + '&sectionid=' + $("#sectionid").val() + '&subsectionid=' + $("#subsectionid").val()
                        + '&studentinstituteid=' + studentinstituteid + '&studentregistrationid=' + studentregistrationid,
                dataType: "json", async: true,
                timeout: 5000, cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                }, success: function (data) {
                    var str = '';
                    var count = new Array();
                    var count2 = new Array();
                    var cou = 0;
                    var cou1 = 0;
                    var count1 = 0;
                    if (data.Warning != '') {
                        BootstrapDialog.alert(data.Warning);
                    } else {
                        $.each(data.GIPList, function (i, item1)
                        {
                            var str1 = '';
                            var str2 = '';
                            var str3 = '';
                            str3 += '<input type="date" class="form-control" id="regconfirmdategip' + i + '" name="regconfirmdategip' + i + '"/>';
                            str += '<tr>';
                            str += '<td><input type="checkbox" onclick=javascript:checkedSameGroup1("' + item1.subjectgroup + '~~@~~' + i + '");  id="chk3' + i + '"  value="' + item1.instituteid + '~@~' + item1.registrationid + '~@~' + item1.studentid + '~@~' + item1.academicyear + '~@~' + item1.programid + '~@~' + item1.sectionid + '~@~' + item1.subsectionid + '~@~' + item1.subjectid + '~@~' + item1.stynumber + '~@~' + item1.credits + '~@~' + item1.subjectgroup + '"/>';
                            str += '  </td>';
                            str += '  <td>' + item1.subjectcode + "-" + item1.subjectdesc + '</td>';
                            str2 += '<select class="form-control" id="gipmode1' + i + '"  name="gipmode1' + i + '"   data-live-search="true">';
                            str += ' <td> ' + str2 + '</td>';
                            str += '  <td>' + item1.subjtype + '</td>';
                            if (item1.LecList.length > 0) {
                                $.each(item1.LecList, function (j, item2) {
                                    if (j === 0) {
                                        str1 += item2.componentcode + '<select class="form-control" id="GIPbasketDetail' + cou1 + '"  name="addbasketDetail' + cou1 + '"  data-live-search="true">';
                                        str1 += '<option value="">Select</option></select>';
                                        cou1++;
                                    }

                                });
                            }
                            if (item1.TutList.length > 0) {
                                $.each(item1.TutList, function (j, item2) {
                                    if (j === 0) {
                                        str1 += item2.componentcode + '<select class="form-control" id="GIPbasketDetail' + cou1 + '"  name="addbasketDetail' + cou1 + '"  data-live-search="true">';
                                        str1 += '<option value="">Select</option></select>';
                                        cou1++;
                                    }

                                });
                            }
                            if (item1.PracList.length > 0) {
                                $.each(item1.PracList, function (j, item2) {
                                    if (j === 0) {
                                        str1 += item2.componentcode + '<select class="form-control" id="GIPbasketDetail' + cou1 + '"  name="addbasketDetail' + cou1 + '"  data-live-search="true">';
                                        str1 += '<option value="">Select</option></select>';
                                        cou1++;
                                    }
                                });
                            }

                            str += ' <td> ' + str1 + '</td>';
                            str += ' <td> ' + item1.subjectgroup + '</td>';
                            str += '  <td>' + item1.stynumber + '</td>';
                            str += '  <td>' + item1.credits + '</td>';
                            str += '  <td>' + str3 + '</td>';
                            str += '  </tr> ';
                            count.push(cou++);
                            $('#datetimepicker2' + i + '').datetimepicker({
                                format: 'DD/MM/YYYY'
                            });
                        });
                        $("#AddGIPSubject").append(str);
                        $("#countvalGIP").val(count);
                        $.each(data.GIPList, function (q, item3) {
                            var str2 = '';
                            $("#gipmode1" + q).chosen({width: "100%"});
                            $.each(item3.modeList, function (j, item2) {
                                str2 += '<option value="' + item2.mode + '">' + item2.modedesc + '</option>';
                            });
                            $("#gipmode1" + q).append(str2);
                            $("#gipmode1" + q).trigger("chosen:updated");
                        });
                        $.each(data.GIPList, function (q, item3) {
                            if (item3.LecList.length > 0) {
                                var str2 = '';
                                $("#GIPbasketDetail" + count1).chosen({width: "100%"});
                                $.each(item3.LecList, function (j, item2) {
                                    str2 += '<option value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '">' + item2.batchcode + '</option>';
                                });
                                $("#GIPbasketDetail" + count1).append(str2);
                                $("#GIPbasketDetail" + count1).trigger("chosen:updated");
                                count2.push(count1++);
                            }

                            if (item3.TutList.length > 0) {
                                var str2 = '';
                                $("#GIPbasketDetail" + count1).chosen({width: "100%"});
                                $.each(item3.TutList, function (j, item2) {
                                    str2 += '<option value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '">' + item2.batchcode + '</option>';
                                });
                                $("#GIPbasketDetail" + count1).append(str2);
                                $("#GIPbasketDetail" + count1).trigger("chosen:updated");
                                count2.push(count1++);
                            }
                            if (item3.PracList.length > 0) {
                                var str2 = '';
                                $("#GIPbasketDetail" + count1).chosen({width: "100%"});
                                $.each(item3.PracList, function (j, item2) {
                                    str2 += '<option value="' + item2.batchid + '~@@~' + item2.componentid + '~@@~' + item2.subj + '">' + item2.batchcode + '</option>';
                                });
                                $("#GIPbasketDetail" + count1).append(str2);
                                $("#GIPbasketDetail" + count1).trigger("chosen:updated");
                                count2.push(count1++);
                            }
                        });
                        $("#countBasketGIP").val(count2);
                    }
                    setTimeout($.unblockUI, 1000);
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                    setTimeout($.unblockUI, 1000);
                }
            });
        }
    }

    function saveGIPData()
    {
        var subjects = 0;
        var selectedcheckboxvalue = '';
        var selectedcheckbox = '';
        var selectedBaskete1 = '';
        var innercount = 0;
        var creditcount = 0;
        var selectedMode = '';
        var regConfirmDate = '';
        var flag = true;
        var countval = [];
        countval = $("#countvalGIP").val().split(",");
        var countBasket = [];
        countBasket = $("#countBasketGIP").val().split(",");
        for (var j = 0; j < countval.length; j++) {
            if ($('#chk3' + j).is(':checked')) {
                subjects++;
            }
        }
        if (subjects === 0)
        {
            BootstrapDialog.alert("Please Select Atleast One Subject");
        } else
        {
            var cc = 0;
            for (var i = 0; i < countval.length; i++)
            {
                var innercount1 = 0;
                var selectedBaskete = '';
                if ($('#chk3' + i + '').is(':checked')) {
                    if ($('#GIPbasketDetail' + i + '').val() == 'null' || $('#GIPbasketDetail' + i + '').val() == '')
                    {
                        BootstrapDialog.alert("Please Select the Subject Batch Detail");
                        flag = false;
                    } else
                    {
                        innercount++;
                        if (innercount === 1) {
                            selectedcheckboxvalue += $('#chk3' + i + '').val();
                            var countCredit = [];
                            countCredit = selectedcheckboxvalue.split('~@~');
                            creditcount = parseInt(countCredit[9]);
                        } else {
                            selectedcheckboxvalue += ',' + $('#chk3' + i + '').val();
                            var selectedcheckboxvalue1 = $('#chk3' + i + '').val();
                            var countCredit = [];
                            countCredit = selectedcheckboxvalue1.split('~@~'); // for credit Check MIN and Max limit
                            creditcount += parseInt(countCredit[9]);
                        }

                        selectedcheckbox = $('#chk3' + i + '').val();
                        var countval1 = [];
                        countval1 = selectedcheckbox.split("~@~");
                        for (var cc = 0; cc < countBasket.length; cc++) {
                            selectedBaskete1 = $('#GIPbasketDetail' + cc + '').val();
                            var subj = [];
                            subj = selectedBaskete1.split("~@@~");
                            if (countval1[7] === subj[2]) {
                                innercount1++;
                                if (innercount1 === 1) {
                                    selectedBaskete += $('#GIPbasketDetail' + cc + '').val();
                                } else {
                                    selectedBaskete += '#~#' + $('#GIPbasketDetail' + cc + '').val();
                                }
                            }
                        }
                        selectedMode = $('#gipmode1' + i + '').val();
                        regConfirmDate = $('#regconfirmdategip' + i + '').val();
                        selectedcheckboxvalue += '~~' + selectedBaskete + '~@~' + selectedMode + '~@~' + regConfirmDate;
                    }
                }
            }
            creditcount += parseInt($("#criditSum").val());
            if (flag && creditcount <= parseInt($("#totalCredit").val())) {
                saveGipSubject(selectedcheckboxvalue);
            } else {
                if (flag && creditcount > parseInt($("#totalCredit").val())) {
                    var wieghtage = parseInt($("#totalCredit").val());
                    if ($("#userType").val() === "A") {
                        var retVal = confirm("Credit exceed form " + wieghtage + " ");
                        if (retVal == true) {
                            saveGipSubject(selectedcheckboxvalue);
                        } else {

                        }

                    } else {
                        BootstrapDialog.alert("Wieghtage exceed form " + wieghtage + " ");
                    }

                }
            }
        }
    }

    function saveGipSubject(selectedcheckboxvalue) {
        $.ajax({
            type: "POST", url: "addDropSubject/addGipSubject",
            data: '&selectedcheckboxvalue=' + selectedcheckboxvalue,
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.status === 'Success') {
                    toastr.success('Record Successfully Saved', "Success!!");
                    $('#myModal1').modal('hide');
                    var students = $("input[name='students']:checked").val();
                    if (students == 'programAcadWise') {
                        getStudentDetail();
                    } else {
                        getStudentInfo();
                        getStudentDetail();
                    }
                    loadData();
                    getReport();
                } else {
                    BootstrapDialog.alert(data.status);
                }

            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function coreSubjectData() {
        $("#myModal").modal();
        $("#regCode1").val($("#semCode1").val());
        $("#regNo1").val($("#enroll").val());
        $("#name1").val($("#stuName").val());
        $("#styNo1").val($("#styNumber").val());
        $("#totalCredit1").val($("#totalCredit").val());
        $("#creditTaken1").val($("#creditTaken").val());
        if ($("#creditTaken").val() == 0) {
            $("#remainingCredit1").val($("#totalCredit").val());
        } else {
            $("#remainingCredit1").val($("#remainingCredit").val());
        }
        $("#section").val($("#batchSubBatch").val());
        $("#subsection").val($("#batchSubBatch1").val());
        var heading = "Add Regular / BackLog Subjects for " + $("#semCode1").val() + " &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp;  <b>     " + $("#stuName").val() + "(" + $("#enroll").val() + ")</b>";
        $("#heading").html(heading);
    }

    function auditSubjectData() {
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        if ($("#allowforfeepay").val() == 'N') {
            BootstrapDialog.alert(" Fee not submit");
        } else if ($("#feespaid").val() == 'N') {
            BootstrapDialog.alert("Student Fees not submit");
        } else if ($("#docverification").val() == 'N') {
            BootstrapDialog.alert(" Document not verifed");
        } else if ($("#nodues").val() == 'N') {
            BootstrapDialog.alert("Dues not clear");
        } else {
            $("#myModal2").modal();
            $("#totalCredit3").val($("#totalCredit").val());
            $("#creditTaken3").val($("#creditTaken").val());
            $("#remainingCredit3").val($("#remainingCredit").val());
            var showclassstartdate = $("#classDateflag").val();
            var todayTime = new Date();
            var month = (todayTime.getMonth() + 1) < 10 ? '0' + (todayTime.getMonth() + 1) : '' + (todayTime.getMonth() + 1);
            var day = todayTime.getDate() < 10 ? '0' + todayTime.getDate() : '' + todayTime.getDate();
            var year = todayTime.getFullYear();
            var regconfdate = $("#regconfdate").val();
            var str3;
            if (regconfdate != '') {
                var date = regconfdate.split('/');
                if (showclassstartdate == 'Y') {
                    $('#classStartDate').val(date[0] + "/" + date[1] + "/" + date[1]);
                    document.getElementById("classStartDate").style.display = "show";
                } else {
                    $('#classStartDate').val(date[0] + "/" + date[1] + "/" + date[1]);
                    document.getElementById("classStartDate").style.display = "none";
                }
            } else {
                if (showclassstartdate == 'Y') {
                    $('#classStartDate').val(day + "/" + month + "/" + year);
                    document.getElementById("classStartDate").style.display = "show";
                } else {
                    $('#classStartDate').val(day + "/" + month + "/" + year);
                    document.getElementById("classStartDate").style.display = "none";
                }
            }
            var programid = $("#programid").val();
            var sectionid = $("#sectionid").val();
            var academicyear = $("#academic").val();
            $("#subjectCode").empty().trigger("chosen:updated");
            $.ajax({
                type: "POST",
                url: "addDropSubject/auditSubject",
                data: '&semCode=' + $("#registrationid").val() + '&programid=' + programid + '&sectionid=' + sectionid + '&academicyear=' + academicyear
                        + '&studentinstituteid=' + studentinstituteid + '&studentregistrationid=' + studentregistrationid,
                dataType: "json",
                async: false,
                timeout: 5000, cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {

                    var str1 = '<option >Select</option>';
                    $.each(data.auditSubject, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + " @ " + item[2] + " @ " + item[3] + " @ " + item[4] + " @ " + item[5] + '">' + item[1] + " -- " + item[2] + '</option>'
                    });
                    $("#subjectCode").append(str1);
                    $("#subjectCode").trigger("chosen:updated");
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        }
        var auditheading = "Add Audit Subject Subjects for " + $("#semCode1").val() + " &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp;  <b>     " + $("#stuName").val() + "(" + $("#enroll").val() + ")</b>";
        $("#auditheading").html(auditheading);
    }
    function auditSubjectDetail() {
        $("#auditsubjectcompl").empty().trigger("chosen:updated");
        $("#auditsubjectcompt").empty().trigger("chosen:updated");
        $("#auditsubjectcompp").empty().trigger("chosen:updated");
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        var subject = $("#subjectCode").val().split(" @ ");
        $("#subjectid").val(subject[0]);
        $("#subjectdesc").val(subject[1]);
        $("#runningDepar").val(subject[3]);
        $("#runningDeparid").val(subject[4]);
        $.ajax({
            type: "POST",
            url: "addDropSubject/getParameterForAuditSubjectCredit",
            datatype: "String",
            async: false,
            timeout: 5000,
            cache: false,
            data: '&subjectid=' + subject[0] + '&instituteid=' + studentinstituteid + '&registrationid=' + studentregistrationid,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                debugger;
                if (data[0] == "Y") {
                    $("#credit").val(0);
                } else {
                    if (subject[2] == 'null') {
                        $("#credit").val('--');
                    } else {
                        $("#credit").val(subject[2]);
                    }
                }
                var l = "<option value=''>Select Lecture Batch</option>";
                var t = "<option value=''>Select Tutorial Batch</option>";
                var p = "<option value=''>Select Practical Batch</option>";
                $.each(data[1], function (i, item) {
                    if (item[1] == "L") {
                        l += '<option value="' + item[0] + "~@~" + item[3] + "~@~" + item[9] + "~@~" + item[19] + '">' + item[17] + "-" + item[16] + "-" + item[10] + "=>" + item[4] + "/" + item[11] + "/" + item[12] + "/" + item[13] + "/" + item[14] + '/Lecture</option>';
                    } else if (item[1] == "T") {
                        t += '<option value="' + item[0] + "~@~" + item[3] + "~@~" + item[9] + "~@~" + item[19] + '">' + item[17] + "-" + item[16] + "-" + item[10] + "=>" + item[4] + "/" + item[11] + "/" + item[12] + "/" + item[13] + "/" + item[14] + '/Tutorial</option>';
                    } else {
                        p += '<option value="' + item[0] + "~@~" + item[3] + "~@~" + item[9] + "~@~" + item[19] + '">' + item[17] + "-" + item[16] + "-" + item[10] + "=>" + item[4] + "/" + item[11] + "/" + item[12] + "/" + item[13] + "/" + item[14] + '/Practical</option>';
                    }
                });
                $("#showbatches").val('');
                if (l != "<option value=''>Select Lecture Batch</option>") {
                    $("#auditsubjectcompl").append(l);
                    $("#auditsubjectcompl").trigger("chosen:updated");
                    $("#auditsubjectcompldiv").show();
                    $("#showbatches").val('l');
                } else {
                    $("#auditsubjectcompldiv").hide();
                }
                if (t != "<option value=''>Select Tutorial Batch</option>") {
                    $("#auditsubjectcompt").append(t);
                    $("#auditsubjectcompt").trigger("chosen:updated");
                    $("#auditsubjectcomptdiv").show();
                    $("#showbatches").val($("#showbatches").val() + ',t');
                } else {
                    $("#auditsubjectcomptdiv").hide();
                }
                if (p != "<option value=''>Select Practical Batch</option>") {
                    $("#auditsubjectcompp").append(p);
                    $("#auditsubjectcompp").trigger("chosen:updated");
                    $("#auditsubjectcomppdiv").show();
                    $("#showbatches").val($("#showbatches").val() + ',p');
                } else {
                    $("#auditsubjectcomppdiv").hide();
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }});
    }



    function saveAuditSubject() {
        var subjectCode = $("#subjectCode").val();
        var check = $("#classStartDate").val();
        if (subjectCode != '') {
            if (check != '') {
                var credit = $("#credit").val();
                var creditcount = parseInt($("#criditSum").val());
                creditcount += parseInt(credit);
                if (creditcount <= parseInt($("#totalCredit").val())) {
                    saveAuditSubject1();
                } else {
                    if (creditcount > parseInt($("#totalCredit").val())) {
                        var wieghtage = parseInt($("#totalCredit").val());
                        if ($("#userType").val() === "A") {
                            BootstrapDialog.confirm("Credit exceed form " + wieghtage + "", function (Yes) {
                                if (Yes) {
                                    saveAuditSubject1();
                                }
                            })
                        } else {
                            BootstrapDialog.alert("Wieghtage exceed form " + wieghtage + " ");
                        }

                    }
                }
            } else {
                BootstrapDialog.alert("Please select Class Start Date first..");
            }
        } else {
            BootstrapDialog.alert("Please select Subject Code..");
        }

    }




    function saveAuditSubject1() {
        var allbatch = $("#showbatches").val() ///  like ,l,t,p
        if (allbatch.startsWith(',')) {
            allbatch = allbatch.slice(1, 5);  //  remove , from starting index l,t,p
        }
        var selectedbatchcomponent = allbatch.split(','); ///  like l,t,p
        var flag = true;
        var batches = "";
        for (var i = 0; i < selectedbatchcomponent.length; i++) {
            var batchseleted = $("#auditsubjectcomp" + selectedbatchcomponent[i]).val(); // selectedbatchcomponent is ( l t p )
            if (batchseleted == "") {
                if (selectedbatchcomponent[i] == 'l')
                    BootstrapDialog.alert("Please Select Lecture Batch");
                if (selectedbatchcomponent[i] == 't')
                    BootstrapDialog.alert("Please Select Tutorial Batch");
                if (selectedbatchcomponent[i] == 'p')
                    BootstrapDialog.alert("Please Select Practical Batch");
                flag = false;
            } else {
                if (i == 0)
                    batches = batchseleted;
                else
                    batches += "@@" + batchseleted;
            }
        }
        if (flag) {
            var studentinstituteid = $("#studentinstituteid").val();
            var studentregistrationid = $("#studentregistrationid").val();
            if ($("#subjectCode").val() != '') {
                $.ajax({
                    type: "POST",
                    url: "addDropSubject/addAuditSubject",
                    data: '&studentid=' + $("#studentid").val() + '&semCode=' + $("#registrationid").val() + '&academic=' + $("#academic").val()
                            + '&programid=' + $("#programid").val() + '&styNumber=' + $("#styNumber").val() + '&branchid=' + $("#branchid").val()
                            + '&sectionid=' + $("#sectionid").val() + '&subsectionid=' + $("#subsectionid").val() + '&subjectid=' + $("#subjectid").val() + '&credit=' + $("#credit").val() + '&classstartdate=' + $("#classStartDate").val() + '&runningDeparid=' + $("#runningDeparid").val()
                            + '&studentinstituteid=' + studentinstituteid + '&studentregistrationid=' + studentregistrationid + '&batchdetail=' + batches,
                    dataType: "json",
                    async: false,
                    timeout: 5000, cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    },
                    success: function (data) {
                        if (data.status === 'Success') {
                            toastr.success('Record Successfully Saved', "Success!!");
                            $('#myModal2').modal('hide');
                            var students = $("input[name='students']:checked").val();
                            if (students == 'programAcadWise') {
                                getStudentDetail();
                            } else {
                                getStudentInfo();
                                getStudentDetail();
                            }
                            loadData();
                        } else {
                            BootstrapDialog.alert(data.status);
                        }

                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please select Subject Code");
            }
        }
    }

    function regConfermation() {
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        var students = $("input[name='students']:checked").val();
        var regid = '';
        var flag = '';
        if (students == 'programAcadWise') {
            regid = $("#registrationid").val();
            if ($("#semCode").val() == '') {
                flag = 'semcode';
            } else if ($("#enrollmentNo").val() == '') {
                flag = 'enrollno';
            } else if ($("#programCode").val() == '') {
                flag = 'progcode';
            } else if ($("#acadyear").val() == '') {
                flag = 'acadyear';
            }
        } else {
            var reg = $("#semCode2").val().split("~@~");
            regid = reg[0];
            if ($("#semCode2").val() == '') {
                flag = 'semcode';
            } else if ($("#enrollmentNo1").val() == '') {
                flag = 'enrollno';
            }
        }
        if (flag == '') {
            if ($("#regConfirm_date").val() != '') {
                var fstidforconfirm = '';
                var regConfirm_date = $("#regConfirm_date").val();
                var countvalue = $("#countvalforregconfirm").val();
                for (var i = 0; i < countvalue; i++) {
                    if ($('#regconfirmchk' + i + '').is(':checked'))
                    {
                        if (i == 0) {
                            fstidforconfirm += $('#regconfirmchk' + i + '').val();
                        } else {
                            fstidforconfirm += '~@~' + $('#regconfirmchk' + i + '').val();
                        }
                    }
                }
                $.ajax({
                    type: "POST",
                    url: "addDropSubject/regConfermation",
                    data: '&studentid=' + $("#studentid").val() + '&semCode=' + regid + '&fstid=' + fstidforconfirm + '&regConfirm_date=' + regConfirm_date
                            + '&studentinstituteid=' + studentinstituteid + '&studentregistrationid=' + studentregistrationid,
                    dataType: "json",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    }, success: function (data) {
                        if (data.status === 'Success') {
                            toastr.success('Student Registration Confirmed', "Success!!");
                            loadData();
                        } else {
                            BootstrapDialog.alert(data.status);
                        }

                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please Enter The Registration Confirmation Date");
            }
        } else {
            if (flag == 'enrollno') {
                BootstrapDialog.alert("Please Select Registraion No.");
            } else if (flag == 'progcode') {
                BootstrapDialog.alert("Please Select Program..");
            } else if (flag == 'acadyear') {
                BootstrapDialog.alert("Please Select Academic Year..");
            } else {
                BootstrapDialog.alert("Please Select Semester Code..");
            }
        }
    }
    function checkedSameGroup(group_index) {
        var groupid = group_index.split("~~@~~");
        var group = groupid[0];
        var index = groupid[1];
        var total = 0;
        if (groupid[2] > 0)
            total++;
        if (groupid[3] > 0)
            total++;
        if (groupid[4] > 0)
            total++;
        if (groupid[2] > 0 || groupid[3] > 0 || groupid[4] > 0) {
            var cout = 0
            if ($('#chk2' + index + '').is(':checked')) {
                cout++;
            } else {
                cout = 0;
            }
            var countval = [];
            countval = $("#countval").val().split(",");
            for (var j = 0; j < countval.length; j++) {
                var selectedcheckboxvalue1 = $('#chk2' + j + '').val();
                var countCredit = [];
                countCredit = selectedcheckboxvalue1.split('~@~');
                var subGroup = countCredit[12];
                if (group != '') {
                    if (group == subGroup) {
                        if (cout > 0) {
                            if ($('#addbasketDetail' + total).val() != null) {
                                $('#chk2' + j + '').prop("checked", true);
                            }
                        } else {
                            $('#chk2' + j + '').prop("checked", false);
                        }
                    }
                } else {
                    if ($('#chk2' + j + '').is(':checked')) {
                        $('#chk2' + j + '').prop("checked", true);
                    } else {
                        $('#chk2' + j + '').prop("checked", false);
                    }
                }
            }             //            }
        } else {
            BootstrapDialog.alert("Load Distribution is not defined for this subject...");
            $('#chk2' + index + '').prop("checked", false);
        }

    }
    function checkedSameGroup1(group_index) {
        var groupid = [];
        groupid = group_index.split("~~@~~");
        var group = groupid[0];
        var index = groupid[1];
        var cout = 0
        if ($('#chk3' + index + '').is(':checked')) {
            cout++;
        } else {
            cout = 0;
        }
        var countval = [];
        countval = $("#countval").val().split(",");
        for (var j = 0; j < countval.length; j++) {
            var selectedcheckboxvalue1 = $('#chk3' + j + '').val();
            var countCredit = [];
            countCredit = selectedcheckboxvalue1.split('~@~');
            var subGroup = countCredit[10];
            if (group == subGroup) {
                if (cout > 0) {
                    $('#chk3' + j + '').prop("checked", true);
                } else {
                    $('#chk3' + j + '').prop("checked", false);
                }
            } else {
                if ($('#chk3' + j + '').is(':checked')) {
                    $('#chk3' + j + '').prop("checked", true);
                } else {
                    $('#chk3' + j + '').prop("checked", false);
                }
            }

        }
    }
    function getReport() {
        if ($("#regconfflag").val() != 'Y') {
            regConfermation();
        }
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        var students = $("input[name='students']:checked").val();
        if ($("#studentid").val() != '' && $("#registrationid").val() != '') {
            window.location.assign('addDropSubject/addDropSubjectReport?excelfilename=addDropSubjectReport&studentid=' + $("#studentid").val() + '&studentregistrationid=' + studentregistrationid + '&acadyear=' + $("#academic").val() + '&subsectionid=' + $("#subsectionid").val() + '&stynumber=' + $("#styNumber").val() + '&programid=' + $("#programid").val() + '&branchid=' + $("#branchid").val() + '&branchcode=' + $("#program").val() + '&studentinstituteid=' + studentinstituteid + '');
        } else {
            BootstrapDialog.alert("Please Enter The Required Field..");
        }
    }


    function getElectiveSubjectForSwap(subjecttypeid, credit, oldelectivesub, basketid) {
        $("#basketdetail").empty();
        $("#electiveSubject").empty();
        $("#oldElectiveSubject").val(oldelectivesub);
        $("#basketidElectiveSubject").val(basketid);
        var str1 = '';
        var str2 = '';
        var str3 = '';
        var str4 = '';
        var cou1 = 0;
        var count1 = 0;
        var leclist = [];
        var tutlist = [];
        var praclist = [];
        leclist = $("#leclistforelectiveswap").val().split("~@#~");
        tutlist = $("#tutlistforelectiveswap").val().split("~@#~");
        praclist = $("#praclistforelectiveswap").val().split("~@#~");
        if (leclist.length > 1) {
            for (var i = 0; i < leclist.length; i++) {
                var lec = [];
                lec = leclist[i].split("~#~#");
                if (i === 0) {
                    str1 += lec[3] + '<select class="form-control" id="addbasketDetailElective' + cou1 + '"  name="addbasketDetailElective' + cou1 + '"  data-live-search="true">';
                    str1 += '<option value="">Select</option></select>';
                    cou1++;
                }
            }
        }
        if (tutlist.length > 1) {
            for (var i = 0; i < tutlist.length; i++) {
                var tut = [];
                tut = tutlist[i].split("~#~#");
                if (i === 0) {
                    str1 += tut[3] + '<select class="form-control" id="addbasketDetailElective' + cou1 + '"  name="addbasketDetailElective' + cou1 + '"  data-live-search="true">';
                    str1 += '<option value="">Select</option></select>';
                    cou1++;
                }
            }
        }
        if (praclist.length > 1) {
            for (var i = 0; i < praclist.length; i++) {
                var prac = [];
                prac = praclist[i].split("~#~#");
                if (i === 0) {
                    str1 += prac[3] + '<select class="form-control" id="addbasketDetailElective' + cou1 + '"  name="addbasketDetailElective' + cou1 + '"  data-live-search="true">';
                    str1 += '<option value="">Select</option></select>';
                    cou1++;
                }
            }
        }
        $("#basketdetail").append(str1);
        if (leclist.length > 1) {
            for (var i = 0; i < leclist.length; i++) {
                var lec = [];
                lec = leclist[i].split("~#~#");
                $("#addbasketDetailElective" + count1).chosen({width: "100%"});
                str2 += '<option value="' + lec[1] + '~@@~' + lec[2] + '~@@~' + '">' + lec[0] + '</option>';
            }
            $("#addbasketDetailElective" + count1).append(str2);
            $("#addbasketDetailElective" + count1).trigger("chosen:updated");
            count1++;
        }
        if (tutlist.length > 1) {
            for (var i = 0; i < tutlist.length; i++) {
                var tut = [];
                tut = tutlist[i].split("~#~#");
                $("#addbasketDetailElective" + count1).chosen({width: "100%"});
                str3 += '<option value="' + tut[1] + '~@@~' + tut[2] + '~@@~' + '">' + tut[0] + '</option>';
            }
            $("#addbasketDetailElective" + count1).append(str3);
            $("#addbasketDetailElective" + count1).trigger("chosen:updated");
            count1++;
        }
        if (praclist.length > 1) {
            for (var i = 0; i < praclist.length; i++) {
                var prac = [];
                prac = praclist[i].split("~#~#");
                $("#addbasketDetailElective" + count1).chosen({width: "100%"});
                str4 += '<option value="' + prac[1] + '~@@~' + prac[2] + '~@@~' + '">' + prac[0] + '</option>';
            }
            $("#addbasketDetailElective" + count1).append(str4);
            $("#addbasketDetailElective" + count1).trigger("chosen:updated");
            count1++;
        }
        $("#countBasketElective").val(count1);
        $.ajax({
            type: "POST",
            url: "addDropSubject/getElectiveSubject",
            data: '&semCode=' + $("#registrationid").val() + '&subjecttypeid=' + subjecttypeid + '&credit=' + credit, dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.electiveSubjectList, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '~@~' + item[3] + '">' + item[1] + "(" + item[2] + ")" + '</option>'
                });
                $("#electiveSubject").append(str1);
                $("#electiveSubject").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function changeElectiveSubject() {
        var oldelectivesubject = $("#oldElectiveSubject").val();
        var electiveSubjectMode = $("#electiveSubjectMode").val();
        var basketid = $("#basketidElectiveSubject").val();
        var selectedBaskete = '';
        if ($("#electiveSubject").val() != '' && $("#electiveClassStartDate").val() != '') {
            var basketcount = $("#countBasketElective").val();
            for (var cc = 0; cc < basketcount; cc++) {
                if (cc === 0) {
                    selectedBaskete += $('#addbasketDetailElective' + cc + '').val();
                } else {
                    selectedBaskete += '#~#' + $('#addbasketDetailElective' + cc + '').val();
                }
            }
            if (selectedBaskete != '') {
                $.ajax({
                    type: "POST",
                    url: "addDropSubject/changeElectiveSubject",
                    data: '&studentid=' + $("#studentid").val() + '&semCode=' + $("#registrationid").val() + '&academic=' + $("#academic").val() + '&basketid=' + basketid
                            + '&programid=' + $("#programid").val() + '&styNumber=' + $("#styNumber").val() + '&branchid=' + $("#branchid").val() + '&electiveSubjectMode=' + electiveSubjectMode + '&sectionid=' + $("#sectionid").val() + '&subsectionid=' + $("#subsectionid").val() + '&subjectid=' + $("#electiveSubject").val() + '&credit=' + $("#credit").val() + '&classstartdate=' + $("#electiveClassStartDate").val() + '&selectedBaskete=' + selectedBaskete + '&oldelectivesubject=' + oldelectivesubject,
                    dataType: "json",
                    async: false,
                    timeout: 5000,
                    cache: false,
                    beforeSend: function (xhr, options) {
                        return true;
                    }, success: function (data) {
                        if (data.status === 'Success') {
                            toastr.success('Record Successfully Saved', "Success!!");
                            $('#myModalE').modal('hide');
                            var students = $("input[name='students']:checked").val();
                            if (students == 'programAcadWise') {
                                getStudentDetail();
                            } else {
                                getStudentInfo();
                                getStudentDetail();
                            }
                            loadData();
                        } else {
                            BootstrapDialog.alert(data.status);
                        }

                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please select Subject Batch Details");
            }
        } else {
            BootstrapDialog.alert("Please select Elective Subject And Class Start Date");
        }

    }
    function setDepId(depid) {
        $("#departmentid").val(depid.split(',')[0]);
        $("#facsubjectid").val(depid.split(',')[1]);
        $("#basketid").val(depid.split(',')[2]);
    }
    function setSubjectcodedesc(subjectcodedesc) {
        $("#readonlySubjectCode").val(subjectcodedesc);
    }
    function assignFaculty(fac) {
        //fac is faculty from (SD)Same Department (OD)Other Department
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        var depid = $("#departmentid").val().split(',')[0];
        $("#facultyModal").modal();
        $("#facultyid").empty();
        $("#facultyid").trigger("chosen:updated");
        $("#subjectcomponentid").empty();
        $("#subjectcomponentid").trigger("chosen:updated");
        var subjectid = $("#facsubjectid").val();
        $.ajax({
            type: "POST",
            url: "addDropBasicSubject/getFaculty",
            data: '&departmentid=' + depid + '&facultyFrom=' + fac + '&subjectid=' + subjectid + '&studentinstituteid=' + studentinstituteid + '&studentregistrationid=' + studentregistrationid,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false, beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str = '<option>Select</option>';
                $.each(data.facultyList, function (i, item) {
                    str += '<option value="' + item[0] + '~@~' + item[3] + '">' + item[1] + ' / ' + item[2] + '</option>';
                });
                $("#facultyid").append(str);
                $("#facultyid").trigger("chosen:updated");
                var str1 = '<option>Select</option>';
                $.each(data.ComponentList, function (i, item) {
                    str1 += '<option value="' + item[0] + '">' + item[1] + ' / ' + item[2] + '</option>';
                });
                $("#subjectcomponentid").append(str1);
                $("#subjectcomponentid").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function saveFaculty() {
        var regid = '';
        var data = $("#departmentid").val();
        var depid = $("#departmentid").val();
        var subjectid = $("#facsubjectid").val();
        var basketid = $("#basketid").val();
        var subcomptid = $("#subjectcomponentid").val();
        var staffid = $("#facultyid").val().split('~@~')[0];
        var stafftype = $("#facultyid").val().split('~@~')[1];
        var studentdata = '';
        var students = $("input[name='students']:checked").val();
        var studentinstituteid = $("#studentinstituteid").val();
        var studentregistrationid = $("#studentregistrationid").val();
        if (students == 'programAcadWise') {
            studentdata = $("#enrollmentNo").val().split("~@~");
            regid = $("#registrationid").val();
        } else {
            studentdata = $("#enrollmentNo11").val().split("~@~");
            var reg = $("#semCode2").val().split("~@~");
            regid = reg[0];
        }
        var values = regid + "," + depid + "," + subjectid + "," + basketid + "," + staffid + "," + stafftype + "," + subcomptid + "," + studentdata + "," + studentinstituteid + "," + studentregistrationid;
        $.ajax({
            type: "POST",
            url: "addDropBasicSubject/saveFacultyLoad",
            data: '&allData=' + values,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false, beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                if (data.status === 'Success') {
                    toastr.success('Record Successfully Saved.', "Success!!");
                    $('#facultyModal').modal('hide');
                    loadData();
                    coreSubjectData();
                } else if (data.status === 'Error') {
                    toastr.error('Form Submission Failed.', "Error!!");
                } else {
                    BootstrapDialog.alert(data);
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }

    function showNoDuesModal() {
        $("#activitymodal").modal('show');
    }
</script>    
