<%-- 
    Document   : registrationMasterEdit
    Created on : Jan 10, 2019, 4:00:30 PM
    Author     : ashutosh1.kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">      
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 20" onkeyup="this.value = this.value.toUpperCase();" value="${data[0][2]}"  data-validation="required" id="registrationCode" maxlength="20" placeholder="Enter Semester Code " name="registrationCode" class="form-control has-help-txt" data-validation-error-msg="Please enter semester code!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Desc:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 100" value="${data[0][3]}" data-validation="required" id="registrationDesc" maxlength="100" placeholder="Enter Registration Desc " name="registrationDesc" class="form-control has-help-txt" data-validation-error-msg="Please enter semester description!"/>
                </div>
            </div> 
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker1'>
                        <fmt:formatDate value='${data[0][4]}' var='reg_from' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="fromdate" name="fromdate" onblur="checkRegistrationToDate();" data-validation="required" value="${reg_from}" data-validation-error-msg="Please select semester from date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span> 
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester To Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker2'>
                        <fmt:formatDate value='${data[0][5]}' var='reg_to' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="todate" onblur="checkRegistrationFromDate();"  name="todate" data-validation="required" value="${reg_to}" data-validation-error-msg="Please select semester to date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Caption:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 50" value="${data[0][6]}" id="registrationCaption" maxlength="50" placeholder="Enter Registration Caption " name="registrationCaption" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Place Of Registration:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 50" value="${data[0][7]}"  id="placeOfRegistration" maxlength="50" placeholder="Enter Place Of Registration " name="placeOfRegistration" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Registration Type:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="registrationType" name="registrationType" onchange="EnableRegID()" data-live-search="true">
                        <c:if test="${data[0][8]=='R'}">
                            <option selected="true" value="R">Regular</option>
                            <option value="S">Short Semester(Summer)</option>
                            <option value="P">Supplementary</option>
                            <option value="F">Fast Track</option>
                        </c:if>
                        <c:if test="${data[0][8]=='S'}">
                            <option value="R">Regular</option>
                            <option selected="true" value="S">Short Semester(Summer)</option>
                            <option value="P">Supplementary</option>
                            <option value="F">Fast Track</option>
                        </c:if>
                        <c:if test="${data[0][8]=='P'}">
                            <option value="R">Regular</option>
                            <option value="S">Short Semester(Summer)</option>
                            <option selected="true" value="P">Supplementary</option>
                            <option value="F">Fast Track</option>
                        </c:if>
                        <c:if test="${data[0][8]=='F'}">
                            <option value="R">Regular</option>
                            <option value="S">Short Semester(Summer)</option>
                            <option value="P">Supplementary</option>
                            <option selected="true" value="F">Fast Track</option>
                        </c:if>
                        <c:if test="${data[0][8]=='N' || data[0][8]== null}">
                            <option selected="true" value="R">Regular</option>
                            <option value="S">Short Semester(Summer)</option>
                            <option value="P">Supplementary</option>
                            <option value="F">Fast Track</option>
                        </c:if>
                    </select>
                </div>
            </div>

        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Registration:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger" id="regisid"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][8]=='P'}">
                        <select class="form-control" id="parentsregistrationid" name="parentsregistrationid"  data-live-search="true" data-validation-error-msg="Please select parent registration!">
                            <option value="">Select</option>                       
                            <c:forEach items="${sec_list}" var="sec_list">
                                <c:if test="${data[0][9]==sec_list[0]}">
                                    <option value="${sec_list[0]}" selected="true"><c:out value="${sec_list[1]}"/></option>
                                </c:if>
                                <c:if test="${data[0][9]!=sec_list[0]}">
                                    <option value="${sec_list[0]}" ><c:out value="${sec_list[1]}"/></option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </c:if>
                    <c:if test="${data[0][8]!='P'}">
                        <select class="form-control" id="parentsregistrationid" name="parentsregistrationid" disabled="true" data-live-search="true" data-validation-error-msg="Please select registration id!">
                            <option value="">Select</option>                       
                            <c:forEach items="${sec_list}" var="sec_list">
                                <option value="${sec_list[0]}"><c:out value="${sec_list[1]}"/></option>
                            </c:forEach>
                        </select>
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Odd Even:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="oddEven" name="oddEven" data-validation="required" data-live-search="true" data-validation-error-msg="Please select odd even!">
                        <option value="">Select</option>
                        <c:if test="${data[0][10]=='1'}">
                            <option selected="true" value="1">Odd</option>
                            <option value="2">Even</option>
                            <option value="0">Even And Odd(Both)</option>
                        </c:if>
                        <c:if test="${data[0][10]=='2'}">
                            <option selected="true" value="2">Even</option>
                            <option value="1">Odd</option>
                            <option value="0">Even And Odd(Both)</option>
                        </c:if>
                        <c:if test="${data[0][10]=='0'}">
                            <option selected="true" value="0">Even And Odd(Both)</option>
                            <option value="1">Odd</option>
                            <option value="2">Even</option>
                        </c:if>
                        <c:if test="${data[0][10]==null}">
                            <option value="0">Even And Odd(Both)</option>
                            <option value="1">Odd</option>
                            <option value="2">Even</option>
                        </c:if>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" id="suppleDate" style="display:none">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplementary From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker11'>
                        <fmt:formatDate value='${data[0][26]}' var='sup_from' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="suppFromDate" name="suppFromDate" value="${sup_from}"  data-validation-error-msg="Please select supplementary from date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplementary To Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker12'>
                        <fmt:formatDate value='${data[0][27]}' var='sup_to' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="suppToDate" name="suppToDate" value="${sup_to}" data-validation-error-msg="Please select supplementary to date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Academic Year Title:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 20" value="${data[0][11]}"  id="academicYearTitle" maxlength="20" placeholder="Enter Academic Year Title" name="academicYearTitle" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Grade Sheet Title:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 60" value="${data[0][12]}" id="gradeSheetTitle" maxlength="60" placeholder="Enter Grade Sheet Title" name="gradeSheetTitle" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div> 
        <div class="row col-lg-12 form-group" >  
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From Attendance:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][13]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeAttandanceYes" name="excludeAttandance" onChange="Disabletxt()" checked="true"  value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeAttandanceNo" name="excludeAttandance" onChange="Disabletxt()" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][13]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeAttandanceYes" name="excludeAttandance" onChange="Disabletxt()" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeAttandanceNo" name="excludeAttandance" onChange="Disabletxt()" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Period From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker3'>
                        <fmt:formatDate value='${data[0][14]}' var='att_from' type='date' pattern='dd/MM/yyyy'/>
                        <c:if test="${data[0][13]=='N'}">
                            <input type='text' class="form-control" id="attPerfromdate" value="${att_from}" onblur="checkAttendancePeriodToDate();" name="attPerFromdate" data-validation="required" data-validation-error-msg="Please select attendance period from date!"/>
                        </c:if>
                        <c:if test="${data[0][13]=='Y'}">
                            <input type='text' class="form-control" id="attPerfromdate" value="${att_from}" onblur="checkAttendancePeriodToDate();"disabled="true"  name="attPerFromdate" data-validation="required" data-validation-error-msg="Please select attendance period from date!"/>
                        </c:if>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Period To Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker4'>

                        <fmt:formatDate value='${data[0][15]}' var='att_to' type='date' pattern='dd/MM/yyyy'/>
                        <c:if test="${data[0][13]=='N'}">
                            <input type='text' class="form-control" id="attPerTodate" value="${att_to}" onblur="checkAttendancePeriodFromDate();" name="attPerTodate" data-validation="required" data-validation-error-msg="Please select attendance period to date!"/>
                        </c:if>
                        <c:if test="${data[0][13]=='Y'}">
                            <input type='text' class="form-control" id="attPerTodate" onblur="checkAttendancePeriodFromDate();" value="${att_to}" disabled="true" name="attPerTodate" data-validation="required" data-validation-error-msg="Please select attendance period to date!"/>
                        </c:if>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Result Publish Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker5'>
                        <fmt:formatDate value='${data[0][16]}' var='exm_per' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="examPerioddate" value="${exm_per}" name="examPerioddate" data-validation="required" data-validation-error-msg="Please select exam period date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Allow Back paper:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${data[0][17]=='Y'}">
                        <input type="checkbox" id="allowBackPaper" name="allowBackPaper" checked="true" value="Y"/>  
                    </c:if>
                    <c:if test="${data[0][17]=='N'}">
                        <input type="checkbox" id="allowBackPaper" name="allowBackPaper" value="Y"/>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" style="border: 1px solid;padding:8px;margin-left: 5px;">
            <div class="row col-lg-6 form-group">
                <div class="row col-lg-12 form-group" style="text-align:center;">
                    <label style="text-transform: capitalize;text-align:center;"><b>Course Registration &nbsp; &nbsp;</b>
                        <input type="checkbox"  onclick="changeCouReg()" id="courseRegistration" name="courseRegistration" checked="true" value=""/>  
                    </label>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <div class="row col-lg-12 form-group" style="text-align:center;" >
                    <label style="text-transform: capitalize;text-align:center" class="col-sm-12 col-lg-12 col-xs-12 col-md-12 control-label"><b>Pre Registration Information &nbsp; &nbsp;</b>
                        <input type="checkbox"  onclick="changeRegInfo()" id="regInformation" name="regInformation"  value=""/>  
                    </label>
                </div>
            </div>
            <div class=" col-lg-6 form-group" >
                <div class="row col-lg-12 form-group" id="Cr">
                    <div class="row col-lg-12 form-group">
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. From Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker9'>
                                <fmt:formatDate value='${data[0][18]}' var='course_from' type='date' pattern='dd/MM/yyyy'/>
                                <input type='text' class="form-control" id="courseRegFromdate" value="${course_from}" onblur="checkCourseRegToDate();" name="courseRegFromdate" data-validation="required" data-validation-error-msg="Please select course reg. from date!"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. To Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker10'>
                                <fmt:formatDate value='${data[0][19]}' var='course_to' type='date' pattern='dd/MM/yyyy'/>
                                <input type='text' class="form-control" value="${course_to}" onblur="checkCourseRegFromDate();" id="courseRegTodate" name="courseRegTodate" data-validation="required" data-validation-error-msg="Please select course reg. to date!"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div> 
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Finalization Status:</label>
                        <div class="row col-lg-6 form-group" >
                            <c:if test="${data[0][20]=='Y'}">
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="finStatusYes" name="finStatus" checked="true" value="Y"/>Yes 
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="finStatusNo" name="finStatus" value="N"/> No
                                    </label>
                                </div>
                            </c:if>                        
                            <c:if test="${data[0][20]=='N'}">
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="finStatusYes" name="finStatus" value="Y"/>Yes 
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="finStatusNo" name="finStatus" checked="true" value="N"/> No
                                    </label>
                                </div>
                            </c:if>
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. Completed:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="row col-lg-6 form-group" >
                            <c:if test="${data[0][38]=='Y'}"> 
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="courseRegCompletYes" name="courseRegComplet" checked="true" value="Y"/>Yes 
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="courseRegCompletNo" name="courseRegComplet"  value="N"/> No
                                    </label>
                                </div>
                            </c:if>
                            <c:if test="${data[0][38]!='Y'}">
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="courseRegCompletYes" name="courseRegComplet" value="Y"/>Yes 
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="courseRegCompletNo" name="courseRegComplet" checked="true" value="N"/> No
                                    </label>
                                </div>
                            </c:if>
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. Broadcast:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="row col-lg-6 form-group" >
                            <c:if test="${data[0][39]=='Y'}">
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="courseRegBroadcastYes" name="courseRegBroadcast" checked="true" value="Y"/>Yes 
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="courseRegBroadcastNo" name="courseRegBroadcast" value="N"/> No
                                    </label>
                                </div>
                            </c:if>
                            <c:if test="${data[0][39]!='Y'}">
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio"  id="courseRegBroadcastYes" name="courseRegBroadcast" value="Y"/>Yes 
                                    </label>
                                </div>
                                <div class="col-lg-3">
                                    <label class="radio-inline">
                                        <input type="radio" id="courseRegBroadcastNo" name="courseRegBroadcast" checked="true" value="N"/> No
                                    </label>
                                </div>
                            </c:if>
                        </div> 
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group">

                <!--                <div class="row col-lg-12 form-group" >
                                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude from Pre Reg.:</label>
                                    <div class="row col-lg-6 form-group" >
                <c:if test="${data[0][21]=='Y'}">
                    <div class="col-lg-3">
                        <label class="radio-inline">
                            <input type="radio"  id="excludePreRegYes" name="excludePreReg" checked="true" onchange="Disabletxtfield()" value="Y"/>Yes 
                        </label>
                    </div>
                    <div class="col-lg-3">
                        <label class="radio-inline">
                            <input type="radio" id="excludePreRegNo" name="excludePreReg"onchange="Disabletxtfield()" value="N"/> No
                        </label>
                    </div>
                </c:if>                        
                <c:if test="${data[0][21]=='N'}">
                    <div class="col-lg-3">
                        <label class="radio-inline">
                            <input type="radio"  id="excludePreRegYes" name="excludePreReg" onchange="Disabletxtfield()" value="Y"/>Yes 
                        </label>
                    </div>
                    <div class="col-lg-3">
                        <label class="radio-inline">
                            <input type="radio" id="excludePreRegNo" name="excludePreReg" onchange="Disabletxtfield()"checked="true" value="N"/> No
                        </label>
                    </div>
                </c:if>  
            </div> 
        </div>-->
                <div class="row col-lg-12 form-group" id="Ri" style="display: none">
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Reg. Event From Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker7'>
                                <fmt:formatDate value='${data[0][22]}' var='regevent_from' type='date' pattern='dd/MM/yyyy'/>
                                <input type='text' class="form-control" id="regEventFrom" value="${regevent_from}" onblur="checkRegEventToDate();"  name="regEventFrom" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Reg. Event To Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker8'>
                                <fmt:formatDate value='${data[0][23]}' var='regevent_to' type='date' pattern='dd/MM/yyyy'/>
                                <input type='text' class="form-control" id="regEventTo" onblur="checkRegEventFromDate();" value="${regevent_to}" name="regEventTo" />
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div> 
                </div> 
                <div class="row col-lg-12 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Event Completed:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="row col-lg-6 form-group" >
                        <c:if test="${data[0][24]=='Y'}">
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio"  id="eventCompletYes" name="eventComplet" checked="true" value="Y"/>Yes 
                                </label>
                            </div>
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio" id="eventCompletNo" name="eventComplet" value="N"/> No
                                </label>
                            </div> 
                        </c:if>                        
                        <c:if test="${data[0][24]=='N'}">
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio"  id="eventCompletYes" name="eventComplet" value="Y"/>Yes 
                                </label>
                            </div>
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio" id="eventCompletNo" name="eventComplet" checked="true" value="N"/> No
                                </label>
                            </div> 
                        </c:if>

                    </div> 
                </div>
                <div class="row col-lg-12 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Event Broadcast:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="row col-lg-6 form-group" >
                        <c:if test="${data[0][25]=='Y'}">
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio"  id="eventBroadcastYes" name="eventBroadcast" checked="true" value="Y"/>Yes 
                                </label>
                            </div>
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio" id="eventBroadcastNo" name="eventBroadcast" value="N"/> No
                                </label>
                            </div>
                        </c:if>                        
                        <c:if test="${data[0][25]=='N'}">
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio"  id="eventBroadcastYes" name="eventBroadcast" value="Y"/>Yes 
                                </label>
                            </div>
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio" id="eventBroadcastNo" name="eventBroadcast" checked="true" value="N"/> No
                                </label>
                            </div>
                        </c:if>
                    </div> 
                </div>
                <div class="row col-lg-12 form-group" >  
                    <div class="row col-lg-12 form-group" style="text-align:center;" >
                        <label style="text-transform: capitalize;text-align:center;"><b>GIP Registration &nbsp; &nbsp;</b>
                            <input type="checkbox"  onclick="changeGipReg()" id="gipRegistration" name="gipRegistration" checked="true" value=""/>  
                        </label>
                    </div>
                    <div class="row col-lg-12 form-group" id="Gr" style="">                                               
                        <div class="row col-lg-12 form-group" >
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">GIP Reg. From Date:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger" id="refd">*</span></label>
                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                <div class='input-group date' id='datetimepicker13'>
                                    <fmt:formatDate value='${data[0][40]}' var='gipreg_from' type='date' pattern='dd/MM/yyyy'/>
                                    <input type='text' class="form-control" id="gipRegFrom" value="${gipreg_from}" name="gipRegFrom" onblur="checkGipRegToDate();" data-validation="required" data-validation-error-msg="Please select GIP reg. from  date!"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div class="row col-lg-12 form-group" >
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">GIP Reg. To Date:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"  id="retd">*</span></label>
                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                <div class='input-group date' id='datetimepicker14'>
                                    <fmt:formatDate value='${data[0][41]}' var='gipreg_to' type='date' pattern='dd/MM/yyyy'/>
                                    <input type='text' class="form-control" id="gipRegTo" value="${gipreg_to}" name="gipRegTo" onblur="checkGipRegFromDate();" data-validation="required" data-validation-error-msg="Please select GIP reg. to date!"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div> 
                        <div class="row col-lg-12 form-group" >
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">GIP Reg. Broadcast:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                            <div class="row col-lg-6 form-group" >
                                <c:if test="${data[0][42]=='Y'}">
                                    <div class="col-lg-3">
                                        <label class="radio-inline">
                                            <input type="radio"  id="gipRegBroadcastYes" name="gipRegBroadcast" checked="true" value="Y"/>Yes 
                                        </label>
                                    </div>
                                    <div class="col-lg-3">
                                        <label class="radio-inline">
                                            <input type="radio" id="gipRegBroadcastNo" name="gipRegBroadcast"  value="N"/>No
                                        </label>
                                    </div>
                                </c:if>
                                <c:if test="${data[0][42]!='Y'}">
                                    <div class="col-lg-3">
                                        <label class="radio-inline">
                                            <input type="radio"  id="gipRegBroadcastYes" name="gipRegBroadcast" value="Y"/>Yes  
                                        </label>
                                    </div>
                                    <div class="col-lg-3">
                                        <label class="radio-inline" style="margin-left:10px">
                                            <input type="radio" id="gipRegBroadcastNo" name="gipRegBroadcast" checked="true" value="N"/> No
                                        </label>
                                    </div>
                                </c:if>
                            </div> 
                        </div>
                    </div>  
                </div>
            </div>
        </div> 

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From SRS:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][28]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeSrsYes" name="excludeSrs" checked="true" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeSrsNo" name="excludeSrs" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][28]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeSrsYes" name="excludeSrs" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeSrsNo" name="excludeSrs" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>

        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From DateSheet:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][29]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeDateSheetYes" name="excludeDateSheet" checked="true" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeDateSheetNo" name="excludeDateSheet" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][29]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeDateSheetYes" name="excludeDateSheet" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeDateSheetNo" name="excludeDateSheet" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From Invg Duty:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][30]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeInvgDutyYes" name="excludeInvgDuty" checked="true" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeInvgDutyNo" name="excludeInvgDuty" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][30]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeInvgDutyYes" name="excludeInvgDuty" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeInvgDutyNo" name="excludeInvgDuty" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From Class Time Table:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][31]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeTimeTableYes" name="excludeTimeTable" checked="true" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeTimeTableNo" name="excludeTimeTable" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][31]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="excludeTimeTableYes" name="excludeTimeTable" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="excludeTimeTableNo" name="excludeTimeTable" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label"> Registration Completed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][32]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="regCompleteYes" name="regComplete" checked="true" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="regCompleteNo" name="regComplete" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][32]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="regCompleteYes" name="regComplete" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="regCompleteNo" name="regComplete" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Lock Registration:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][33]=='Y'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="lockRegYes" name="lockReg" checked="true" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="lockRegNo" name="lockReg" value="N"/> No
                            </label>
                        </div>
                    </c:if>                        
                    <c:if test="${data[0][33]=='N'}">
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio"  id="lockRegYes" name="lockReg" value="Y"/>Yes 
                            </label>
                        </div>
                        <div class="col-lg-3">
                            <label class="radio-inline">
                                <input type="radio" id="lockRegNo" name="lockReg" checked="true" value="N"/> No
                            </label>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data[0][34]=='N'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data[0][34]=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div> 
        </div>    
        <!--<div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Registration Instruction: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea rows="4" cols="40" id="cou_reg_ins" name="cou_reg_ins" maxlength="1950" style="width:130%;min-width:130%;max-width:130%;min-height:130px">${data[0][35]}</textarea>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Elective Instruction: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea rows="4" cols="40" id="elective_inst" name="elective_inst" maxlength="1950" style="width:130%;min-width:130%;max-width:130%;min-height:130px">${data[0][36]}</textarea>
                </div>
            </div> 
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplimentary Instruction: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea rows="4" cols="40" id="sub_ins" name="sub_ins" maxlength="1950" style="width:130%;min-width:130%;max-width:130%;min-height:130px">${data[0][37]} </textarea>
                </div>
            </div>
        </div>-->
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
        <input type="hidden" name="registrationId" value="${data[0][1]}"/>
    </div>
</form>

<script>
    var val = getValue();
    if (val == 'Y') {
        document.getElementById("registrationCode").setAttribute("readonly", true);
        document.getElementById("registrationDesc").setAttribute("readonly", true);
    }
    $("#registrationType").chosen();
    $("#parentsregistrationid").chosen();
    $("#oddEven").chosen();

    function EnableRegID() {


        var result = document.getElementById("registrationType").value;

        $('#registrationid').prop('disabled', false).trigger("chosen:updated");
        if (result == "P") {
            var vv = document.getElementById("regisid");
            vv.innerHTML = "*";
            $('#parentsregistrationid').attr("data-validation", "required")
            $("#registrationid").prop("disabled", false);
            $('#Cr').hide();
            $('#suppleDate').show();
            document.getElementById("courseRegistration").removeAttribute('checked');
            $('#suppFromDate').attr("data-validation", "required");
            $('#suppToDate').attr("data-validation", "required");
            $('#courseRegFromdate').attr("data-validation", "");
            $('#courseRegTodate').attr("data-validation", "");
            $("#registrationid").empty().trigger("chosen:updated");
            $.ajax({
                type: "POST",
                url: "registrationMaster/getRegId",
                data: '&registrationType=' + $("#prog_code").val(),
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    var str1 = '';
                    var str1 = '<option value="">Select</option>';
                    $.each(data.sec_list, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                    });
                    $("#registrationid").append(str1);
                    $("#registrationid").trigger("chosen:updated");
                },
                error: function (response) {
                    BootstrapDialog.alert("Some Thing Went Wrong...");
                }
            });
        } else if (result !== "P") {
            $('#registrationid').prop('disabled', true).trigger("chosen:updated");
            $('#parentsregistrationid').attr("data-validation", "");
            var vv = document.getElementById("regisid");
            $('#Cr').show();
            $('#suppleDate').hide();
            $('#suppFromDate').attr("data-validation", "");
            $('#suppToDate').attr("data-validation", "");
            $('#courseRegFromdate').attr("data-validation", "required");
            $('#courseRegTodate').attr("data-validation", "required");
            vv.innerHTML = "";
        }


    }

    function Disabletxtfield() {

        var result = document.querySelector('input[name="excludePreReg"]:checked').value;
        if (result == "Y") {

            document.getElementById("regEventFrom").value = '';
            document.getElementById("regEventTo").value = '';
            document.getElementById("regEventFrom").setAttribute('disabled', true);
            document.getElementById("regEventTo").setAttribute('disabled', true);

        } else {
            document.getElementById("regEventFrom").value = '';
            document.getElementById("regEventTo").value = '';
            document.getElementById("regEventFrom").removeAttribute('disabled');
            document.getElementById("regEventTo").removeAttribute('disabled');

        }
    }

    function Disabletxt() {

        var result = document.querySelector('input[name="excludeAttandance"]:checked').value;
        if (result == "Y") {

            document.getElementById("attPerfromdate").value = '';
            document.getElementById("attPerTodate").value = '';
            document.getElementById("attPerfromdate").setAttribute('disabled', true);
            document.getElementById("attPerTodate").setAttribute('disabled', true);

        } else {
            document.getElementById("attPerfromdate").value = '';
            document.getElementById("attPerTodate").value = '';
            document.getElementById("attPerfromdate").removeAttribute('disabled');
            document.getElementById("attPerTodate").removeAttribute('disabled');

        }
    }
    $("#datetimepicker3").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("attPerfromdate").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");

                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]);  // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);

                if (check >= from && check <= to) {

                } else {
                    BootstrapDialog.alert("Attendance From Date must be between Semester From Date and To Date. ");
                    document.getElementById("attPerfromdate").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("attPerfromdate").value = "";
        }
    });

    $("#datetimepicker4").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("attPerTodate").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check >= from && check <= to) {

                } else {
                    BootstrapDialog.alert("Attendance To Date must be between Semester From Date and To Date. ");
                    document.getElementById("attPerTodate").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("attPerTodate").value = "";
        }
    });
    $("#datetimepicker5").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("examPerioddate").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check >= from && check <= to) {

                } else {
                    BootstrapDialog.alert("Exam Period Date must be between Semester From Date and To Date. ");
                    document.getElementById("examPerioddate").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("examPerioddate").value = "";
        }
    });

    $("#datetimepicker7").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("regEventFrom").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check >= from && check <= to) {

                } else {
                    BootstrapDialog.alert("Reg. Event From Date must be between Semester From Date and To Date. ");
                    document.getElementById("regEventFrom").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("regEventFrom").value = "";
        }
    });
    $("#datetimepicker8").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("regEventTo").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check >= from && check <= to) {

                } else {
                    BootstrapDialog.alert("Reg. Event To Date must be between Semester From Date and To Date. ");
                    document.getElementById("regEventTo").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("regEventTo").value = "";
        }
    });
    $("#datetimepicker10").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("courseRegTodate").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check <= to) {
                } else {
                    BootstrapDialog.alert("Course Reg. To Date should not be greater than Semester To Date. ");
                    document.getElementById("courseRegTodate").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester To Date. ");
            document.getElementById("courseRegTodate").value = "";
        }
    });
    $("#datetimepicker11").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("suppFromDate").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check >= from && check <= to) {
                } else {
                    BootstrapDialog.alert("Supplementary From Date must be between Semester From Date and To Date. ");
                    document.getElementById("suppFromDate").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("suppFromDate").value = "";
        }
    });
    $("#datetimepicker12").focusout(function () {
        var fromdate = document.getElementById("fromdate").value;
        var todate = document.getElementById("todate").value;
        if (!fromdate == '' && !todate == '') {
            var chkdate = document.getElementById("suppToDate").value;
            if (chkdate != '') {
                var d1 = fromdate.split("/");
                var d2 = todate.split("/");
                var c = chkdate.split("/");
                var from = new Date(d1[2], parseInt(d1[1]) - 1, d1[0]); // -1 because months are from 0 to 11
                var to = new Date(d2[2], parseInt(d2[1]) - 1, d2[0]);
                var check = new Date(c[2], parseInt(c[1]) - 1, c[0]);
                if (check >= from && check <= to) {
                } else {
                    BootstrapDialog.alert("Supplementary To Date must be between Semester From Date and To Date. ");
                    document.getElementById("suppToDate").value = "";
                }
            }
        } else {
            BootstrapDialog.alert("Plese Select Semester From Date and To Date. ");
            document.getElementById("suppToDate").value = "";
        }
    });
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker2').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker3').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker4').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker5').datetimepicker({
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker9').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker10').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker7').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker8').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker11').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker12').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        $('#datetimepicker13').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker14').datetimepicker({
            useCurrent: false, //Important! See issue #1075
            format: 'DD/MM/YYYY'
        });
    });
    $(function () {
        var v = $('#suppFromDate').val();
        if (v != '') {
            $('#suppleDate').show();
        }
    });
    function change() {
        if ($("#deactive").prop('checked') == true) {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        } else
        {
            $("#deactive").val("Y");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: red"> Deactive</span>');
        }
    }

    function changeCouReg() {
        if ($("#courseRegistration").prop('checked') == true) {
            $("#Cr").show();
            $('#courseRegFromdate').attr("data-validation", "required");
            $('#courseRegTodate').attr("data-validation", "required");
        } else
        {
            $("#Cr").hide();
            $('#courseRegFromdate').attr("data-validation", "");
            $('#courseRegTodate').attr("data-validation", "");

        }
    }

    function changeRegInfo() {
        if ($("#regInformation").prop('checked') == true) {
            $("#Ri").show();
        } else
        {
            $("#Ri").hide();
        }
    }

    function changeGipReg() {
        if ($("#gipRegistration").prop('checked') == true) {
            $("#Gr").show();
            $('#gipRegFrom').attr("data-validation", "required");
            $('#gipRegTo').attr("data-validation", "required");
        } else
        {
            $("#Gr").hide();
            $('#gipRegFrom').attr("data-validation", "");
            $('#gipRegTo').attr("data-validation", "");

        }
    }

    setTimeout(
            function () {
                $.validate(
                        {
                            addValidClassOnAll: true,
                            onError: function () {
                                $("#alertdiv").remove();
                            },
                            onSuccess: function (e) {
                                // $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "registrationMaster/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        // $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Updated.', "Success!!");
                                            loadForm("registrationMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        // setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //   $.unblockUI();
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

    function saveData() {
        $("#ajaxform").submit();
    }

    function checkRegistrationFromDate() {
        $("#attPerTodate").val('');
        $("#attPerfromdate").val('');
        $("#examPerioddate").val('');
        $("#regEventTo").val('');
        $("#regEventFrom").val('');
        $("#hostelTodate").val('');
        $("#hostelFromdate").val('');
        var start_date = $("#fromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#todate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#todate").val() != '') {
            if ($("#fromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#todate").val('');
                    BootstrapDialog.alert("Please select another date greater than the Semester from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#todate").val('');
                        BootstrapDialog.alert("Please select another date greater than the Semester from date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#todate").val('');
                            BootstrapDialog.alert("Please select another date greater than the Semester from date!");
                        }
                    }
                }

            } else {
                $("#todate").val('');
                BootstrapDialog.alert("Please select Semester from date first!");
            }
        }
    }
    function checkRegistrationToDate() {
        $("#attPerTodate").val('');
        $("#attPerfromdate").val('');
        $("#examPerioddate").val('');
        $("#regEventTo").val('');
        $("#regEventFrom").val('');
        $("#hostelTodate").val('');
        $("#hostelFromdate").val('');
        var start_date = $("#fromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#todate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#todate").val() != '') {
            if ($("#fromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#fromdate").val('');
                    BootstrapDialog.alert("Please select another date Less than the Semester To date!");
                } else {
                    if (fromMonth > toMonth) {
                        $("#fromdate").val('');
                        BootstrapDialog.alert("Please select another date Less than the Semester To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#fromdate").val('');
                            BootstrapDialog.alert("Please select another date Less than the Semester To date!");
                        }
                    }
                }

            } else {
                $("#todate").val('');
                BootstrapDialog.alert("Please select Semester from date first!");
            }
        }
    }

    function checkAttendancePeriodFromDate() {
        var start_date = $("#attPerfromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#attPerTodate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#attPerTodate").val() != '') {
            if ($("#attPerfromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#attPerTodate").val('');
                    BootstrapDialog.alert("Please select another date greater than the Attendance Period from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#attPerTodate").val('');
                        BootstrapDialog.alert("Please select another date greater than the Attendance Period from date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#attPerTodate").val('');
                            BootstrapDialog.alert("Please select another date greater than the Attendance Period from date!");
                        }
                    }
                }

            } else {
                $("#attPerTodate").val('');
                BootstrapDialog.alert("Please select Attendance Period from date first!");
            }
        }
    }
    function checkAttendancePeriodToDate() {
        var start_date = $("#attPerfromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#attPerTodate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#attPerTodate").val() != '') {
            if ($("#attPerfromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#attPerfromdate").val('');
                    BootstrapDialog.alert("Please select another date Less than the Attendance Period To date!");
                } else {
                    if (fromMonth > toMonth) {
                        $("#attPerfromdate").val('');
                        BootstrapDialog.alert("Please select another date Less than the Attendance Period To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#attPerfromdate").val('');
                            BootstrapDialog.alert("Please select another date Less than the Attendance Period To date!");
                        }
                    }
                }

            } else {
                $("#attPerTodate").val('');
                BootstrapDialog.alert("Please select Attendance Period from date first!");
            }
        }
    }
    function checkCourseRegFromDate() {
        var start_date = $("#courseRegFromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#courseRegTodate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#courseRegTodate").val() != '') {
            if ($("#courseRegFromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#courseRegTodate").val('');
                    BootstrapDialog.alert("Please select another date greater than the Course Reg. from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#courseRegTodate").val('');
                        BootstrapDialog.alert("Please select another date greater than the Course Reg. date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#courseRegTodate").val('');
                            BootstrapDialog.alert("Please select another date greater than the Course Reg. from date!");
                        }
                    }
                }

            } else {
                $("#courseRegTodate").val('');
                BootstrapDialog.alert("Please select Course Reg. from date first!");
            }
        }
    }
    function checkCourseRegToDate() {
        var start_date = $("#courseRegFromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#courseRegTodate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#courseRegTodate").val() != '') {
            if ($("#courseRegFromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#courseRegFromdate").val('');
                    BootstrapDialog.alert("Please select another date less than the Course Reg. To date!");
                } else {
                    if (fromMonth > toMonth) {
                        $("#courseRegFromdate").val('');
                        BootstrapDialog.alert("Please select another date less than the Course Reg. To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#courseRegFromdate").val('');
                            BootstrapDialog.alert("Please select another date less than the Course Reg. To date!");
                        }
                    }
                }

            } else {
                $("#courseRegTodate").val('');
                BootstrapDialog.alert("Please select Course Reg. from date first!");
            }
        }
    }
    function checkRegEventFromDate() {
        var start_date = $("#regEventFrom").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#regEventTo").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#regEventTo").val() != '') {
            if ($("#regEventFrom").val() != '') {
                if (fromYear > toYear) {
                    $("#regEventTo").val('');
                    BootstrapDialog.alert("Please select another date greater than the Reg.Event from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#regEventTo").val('');
                        BootstrapDialog.alert("Please select another date greater than the  Reg.Event from date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#regEventTo").val('');
                            BootstrapDialog.alert("Please select another date greater than the Reg.Event from date!");
                        }
                    }
                }

            } else {
                $("#regEventTo").val('');
                BootstrapDialog.alert("Please select Reg.Event from date first!");
            }
        }
    }
    function checkRegEventToDate() {
        var start_date = $("#regEventFrom").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#regEventTo").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#regEventTo").val() != '') {
            if ($("#regEventFrom").val() != '') {
                if (fromYear > toYear) {
                    $("#regEventFrom").val('');
                    BootstrapDialog.alert("Please select another date less than the Reg.Event To date!");
                } else {
                    if (fromMonth > toMonth) {
                        $("#regEventFrom").val('');
                        BootstrapDialog.alert("Please select another date less than the  Reg.Event To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#regEventFrom").val('');
                            BootstrapDialog.alert("Please select another date less than the Reg.Event To date!");
                        }
                    }
                }

            } else {
                $("#regEventTo").val('');
                BootstrapDialog.alert("Please select Reg.Event from date first!");
            }
        }
    }

    function checkHostelFromDate() {
        var start_date = $("#hostelFromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#hostelTodate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#hostelTodate").val() != '') {
            if ($("#hostelFromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#hostelTodate").val('');
                    BootstrapDialog.alert("Please select another date greater than the Hostel Allocation From Date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#hostelTodate").val('');
                        BootstrapDialog.alert("Please select another date greater than the  Hostel Allocation From Date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#hostelTodate").val('');
                            BootstrapDialog.alert("Please select another date greater than the Hostel Allocation From Date!");
                        }
                    }
                }

            } else {
                $("#hostelTodate").val('');
                BootstrapDialog.alert("Please select Hostel Allocation From Date first!");
            }
        }
    }
    function checkHostelUpToDate() {
        var start_date = $("#hostelFromdate").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#hostelTodate").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#hostelTodate").val() != '') {
            if ($("#hostelFromdate").val() != '') {
                if (fromYear > toYear) {
                    $("#hostelFromdate").val('');
                    BootstrapDialog.alert("Please select another date less than the Hostel Allocation UpTo Date!");
                } else {
                    if (fromMonth > toMonth) {
                        $("#hostelFromdate").val('');
                        BootstrapDialog.alert("Please select another date less than the  Hostel Allocation UpTo Date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#hostelFromdate").val('');
                            BootstrapDialog.alert("Please select another date less than the Hostel Allocation UpTo Date!");
                        }
                    }
                }

            } else {
                $("#hostelTodate").val('');
                BootstrapDialog.alert("Please select Hostel Allocation From Date first!");
            }
        }
    }
    function checkGipRegToDate() {
        var start_date = $("#gipRegFrom").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#gipRegTo").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#gipRegTo").val() != '') {
            if ($("#gipRegFrom").val() != '') {
                if (fromYear > toYear) {
                    $("#gipRegFrom").val('');
                    BootstrapDialog.alert("Please select another date less than the Gip Reg. To date!");
                } else {
                    if (fromMonth > toMonth) {
                        $("#gipRegFrom").val('');
                        BootstrapDialog.alert("Please select another date less than the Gip Reg. To date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#gipRegFrom").val('');
                            BootstrapDialog.alert("Please select another date less than the gip Reg. To date!");
                        }
                    }
                }

            } else {
                $("#courseRegTodate").val('');
                BootstrapDialog.alert("Please select Gip Reg. from date first!");
            }
        }
    }
    function checkGipRegFromDate() {
        var start_date = $("#gipRegFrom").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#gipRegTo").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#gipRegTo").val() != '') {
            if ($("#gipRegFrom").val() != '') {
                if (fromYear > toYear) {
                    $("#gipRegTo").val('');
                    BootstrapDialog.alert("Please select another date greater than the Gip Reg. from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#gipRegTo").val('');
                        BootstrapDialog.alert("Please select another date greater than the Gip Reg. from date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#gipRegTo").val('');
                            BootstrapDialog.alert("Please select another date greater than the Gip Reg. from date!");
                        }
                    }
                }

            } else {
                $("#gipRegTo").val('');
                BootstrapDialog.alert("Please select Gip Reg. from date first!");
            }
        }
    }



</script>

