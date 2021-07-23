<%-- 
    Document   : registrationMasterAdd
    Created on : Jan 10, 2019, 4:00:01 PM
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
                    <input type="text" data-validation-help="Max length is 20" onkeyup="this.value = this.value.toUpperCase();" data-validation="required" id="registrationCode" maxlength="20" placeholder="Enter Semester Code " name="registrationCode" class="form-control has-help-txt"  data-validation-error-msg="Please enter semester code!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Desc:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 100"  data-validation="required" id="registrationDesc" maxlength="100" placeholder="Enter Registration Desc " name="registrationDesc" class="form-control has-help-txt"  data-validation-error-msg="Please enter semester description!"/>
                </div>
            </div> 
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker1'>
                        <input type='text' class="form-control" id="fromdate" name="fromdate" data-validation="required"  data-validation-error-msg="Please select semester from date!"/>
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
                        <input type='text' class="form-control" id="todate" name="todate" data-validation="required"  data-validation-error-msg="Please select semester to date!"/>
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
                    <input type="text" data-validation-help="Max length is 50"  id="registrationCaption" maxlength="50" placeholder="Enter Registration Caption " name="registrationCaption" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Place Of Registration:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 50"  id="placeOfRegistration" maxlength="50" placeholder="Enter Place Of Registration " name="placeOfRegistration" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;"   class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Registration Type:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="registrationType" onchange="EnableRegID()" name="registrationType" data-live-search="true">
                        <option value="R">Regular</option>
                        <option value="S">Short Semester(Summer)</option>
                        <option value="P">Supplementary</option>
                        <option value="F">Fast Track</option>
                    </select>
                </div>
            </div>

        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Registration:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger" id="regisid"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="registrationid" name="registrationid" disabled="true"  data-live-search="true" data-validation-error-msg="Please select parent registration!">
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Odd Even:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="oddEven" name="oddEven" data-validation="required" data-live-search="true" data-validation-error-msg="Please select odd even!">
                        <option value="">Select</option>
                        <option value="1">Odd</option>
                        <option value="2">Even</option>
                        <option value="0">Even And Odd(Both)</option>
                    </select>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" id="suppleDate" style="display:none;">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplementary From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker11'>
                        <input type='text' class="form-control" id="suppFromDate" name="suppFromDate"  data-validation-error-msg="Please select supplementary from date!"/>
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
                        <input type='text' class="form-control" id="suppToDate" name="suppToDate"  data-validation-error-msg="Please select supplementary to date!"/>
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
                    <input type="text" data-validation-help="Max length is 20"  id="academicYearTitle" maxlength="20" placeholder="Enter Academic Year Title" name="academicYearTitle" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Grade Sheet Title:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 60"  id="gradeSheetTitle" maxlength="60" placeholder="Enter Grade Sheet Title" name="gradeSheetTitle" class="form-control has-help-txt"/>
                </div>
            </div> 
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From Attendance:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class="col-lg-3">
                        <label class="radio-inline">
                            <input type="radio"  id="excludeAttandanceYes" name="excludeAttandance" onChange="Disabletxt()" checked="true" value="Y"/>Yes 
                        </label>
                    </div>
                    <div class="col-lg-3">
                        <label class="radio-inline">
                            <input type="radio" id="excludeAttandanceNo" name="excludeAttandance" onChange="Disabletxt()"  value="N"/> No
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Period From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger" id="apfd"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker3'>
                        <input type='text' class="form-control" id="attPerfromdate" name="attPerFromdate" disabled="true" data-validation="required" data-validation-error-msg="Please select attendance period from date!" />
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Period To Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"  id="aptd"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker4'>
                        <input type='text' class="form-control" id="attPerTodate" name="attPerTodate" disabled="true" data-validation="required"  data-validation-error-msg="Please select attendance period to date!"/>
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
                        <input type='text' class="form-control" id="examPerioddate" name="examPerioddate" data-validation="required"  data-validation-error-msg="Please select exam period date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Allow Back paper:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" id="allowBackPaper" name="allowBackPaper" value="Y"/>  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" style="border: 1px solid;padding:8px;margin-left: 5px;">
            <div class="row col-lg-6 form-group">
                <div class="row col-lg-12 form-group" style="text-align:center;" >
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
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. From Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker9'>
                                <input type='text' class="form-control" id="courseRegFromdate" name="courseRegFromdate" data-validation="required" data-validation-error-msg="Please select course reg. from date!"/>
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
                                <input type='text' class="form-control" id="courseRegTodate" name="courseRegTodate" data-validation="required"  data-validation-error-msg="Please select course reg. to date!"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div> 
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Finalization Status:</label>
                        <div class="row col-lg-6 form-group" >
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
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. Completed:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="row col-lg-6 form-group" >
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
                        </div> 
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg. Broadcast:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                        <div class="row col-lg-6 form-group" >
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
                        </div> 
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <div class="row col-lg-12 form-group" id="Ri" style="display: none">
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude from Pre Reg.:</label>
                        <div class="row col-lg-6 form-group" >
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio"  id="excludePreRegYes" name="excludePreReg" onchange="Disabletxtfield()" checked="true" value="Y"/>Yes 
                                </label>
                            </div>
                            <div class="col-lg-3">
                                <label class="radio-inline">
                                    <input type="radio" id="excludePreRegNo" name="excludePreReg" onchange="Disabletxtfield()" value="N"/> No
                                </label>
                            </div>
                        </div> 
                    </div>

                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Reg. Event From Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger" id="refd"> </span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker7'>
                                <input type='text' class="form-control" id="regEventFrom" name="regEventFrom" disabled="true" data-validation="required" data-validation-error-msg="Please select reg. event from  date!"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="row col-lg-12 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Reg. Event To Date:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"  id="retd"> </span></label>
                        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                            <div class='input-group date' id='datetimepicker8'>
                                <input type='text' class="form-control" id="regEventTo" name="regEventTo"  disabled="true"  data-validation="required" data-validation-error-msg="Please select reg. event to date!"/>
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
                    </div> 
                </div>
                <div class="row col-lg-12 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Event Broadcast:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="row col-lg-6 form-group" >
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
                                    <input type='text' class="form-control" id="gipRegFrom" name="gipRegFrom" data-validation="required" data-validation-error-msg="Please select GIP reg. from date!"/>
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
                                    <input type='text' class="form-control" id="gipRegTo" name="gipRegTo"  data-validation="required" data-validation-error-msg="Please select GIP reg. to date!"/>
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
                </div>
            </div>

        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From DateSheet:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
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
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From Invg Duty:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
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
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Exclude From Class Time Table:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
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
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label"> Registration Completed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
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
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Lock Registration:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
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
                </div>
            </div>
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" id="Ad">
                    <span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>           
                </div>
            </div> 
        </div>
        <!----------------------- Model Window Start --------------------->

        <div id="myModal" class="modal fade" role="dialog" style="width: 1000px;">
            <div class="modal-dialog" style="width: 800px;">
                <div class="modal-content">
                    <div class="modal-header"  style="background-color:#001f3f;color:white">
                        <button type="button" class="close" data-dismiss="modal" style="opacity:1;color:white">&times;</button>
                        <h4 class="modal-title "><b>Registration Master PopUp</b></h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="row col-lg-7 form-group" style="margin-left:30px">
                                <label style="text-transform: capitalize;text-align:center;"><b>Exam Grade Master &nbsp; &nbsp;</b>
                                    <input type="checkbox"  onclick="changeCouReg()" id="examGrade" name="examGrade" onchange="ShowHide1();" value="exam"/>  
                                </label>
                            </div>
                            <div class="row col-lg-12 form-group" id="Ay" style="display: none">
                                <label style="text-transform: capitalize;" class="col-sm-12 col-lg-3 col-xs-12 col-md-4 control-label">Academic Year:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-12 col-lg-6 col-xs-12 col-md-8">
                                    <select class="form-control" id="academicyear" name="academicyear" >               
                                        <option value="">Select</option>
                                        <c:forEach items="${acadList}" var="acadList">
                                            <option value="${acadList.id.academicyear}"><c:out value="${acadList.id.academicyear}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="row col-lg-12 form-group" id="Pc" style="display: none">
                                <label style="text-transform:capitalize;" class="col-sm-12 col-lg-3 col-xs-12 col-md-4 control-label">Program Code:
                                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-12 col-lg-6 col-xs-12 col-md-8">
                                    <select class="form-control" id="programid" name="programid" multiple="true" >               
                                        <option value="">Select</option>
                                        <c:forEach items="${progList}" var="progList">
                                            <option value="${progList[2]}"><c:out value="${progList[1]} / ${progList[0]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div> 
                            <div class="row col-lg-7 form-group" style="margin-left:30px">
                                <label style="text-transform: capitalize;text-align:center" ><b>Load Distribution Grants &nbsp; &nbsp;</b>
                                    <input type="checkbox"  onclick="changeRegInfo()" id="loadDistribution" name="loadDistribution" value="loaddist" onchange="ShowHide();" />  
                                </label>
                            </div>
                            <div class="row col-lg-12 form-group" id="Sc"  style="display: none">
                                <label style="text-transform:capitalize;" class="col-sm-12 col-lg-3 col-xs-12 col-md-4 control-label">From Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                                <div class="col-sm-12 col-lg-6 col-xs-12 col-md-8">
                                    <select class="form-control" id="semesterCode" name="semesterCode" >
                                        <option value="">Select</option>
                                        <c:forEach items="${semCode}" var="semCode">
                                            <option value="${semCode[0]}"><c:out value="${semCode[1]}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>


                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" data-dismiss="modal">Done</button>
                    </div>
                </div>
            </div>
        </div>
        <!----------------------- Model Window End --------------------->

        <!-- <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Registration Instruction:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea rows="4" cols="40" id="cou_reg_ins" name="cou_reg_ins"  maxlength="1950" style="width:130%;min-width:130%;max-width:130%;min-height:130px"></textarea>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Elective Instruction: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea rows="4" cols="40" id="elective_inst" name="elective_inst"   maxlength="1950" style="width:130%;min-width:130%;max-width:130%;min-height:130px"></textarea>
                </div>
            </div> 
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplimentary Instruction: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger" ></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <textarea rows="4" cols="40" id="sub_ins" name="sub_ins" maxlength="1950" style="width:130%;min-width:130%;max-width:130%;min-height:130px"></textarea>
                </div>
            </div>
        </div> -->
        <div class="row col-lg-12">
            <div class="col-sm-6 col-lg-3">
                <div style="margin-left: 15px;" class="form-group pull-left-container">
                    <button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">Copy Data</button>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form>

<script>
    $("#registrationType").chosen();
    $("#registrationid").chosen();
    $("#oddEven").chosen();
    $("#programid").chosen({width: "80%"});
    $("#academicyear").chosen({width: "80%"});
    $("#semesterCode").chosen({width: "80%"});
    $("#registrationid").prop("disabled", true);


    function Disabletxt() {

        var result = document.querySelector('input[name="excludeAttandance"]:checked').value;
        if (result == "Y") {
            document.getElementById("attPerfromdate").value = '';
            document.getElementById("attPerTodate").value = '';
            document.getElementById("attPerfromdate").setAttribute('disabled', true);
            document.getElementById("attPerTodate").setAttribute('disabled', true);
            var apfd = document.getElementById("apfd");
            apfd.innerHTML = "";
            var aptd = document.getElementById("aptd");
            aptd.innerHTML = "";
        } else {
            document.getElementById("attPerfromdate").value = '';
            document.getElementById("attPerTodate").value = '';
            document.getElementById("attPerfromdate").removeAttribute('disabled');
            document.getElementById("attPerTodate").removeAttribute('disabled');
            var apfd = document.getElementById("apfd");
            apfd.innerHTML = "*";
            var aptd = document.getElementById("aptd");
            aptd.innerHTML = "*";
        }
    }

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



    function myReset() {
        $("#registrationid").empty();
        $("#registrationid").val('').trigger("chosen:updated");
        $("#registrationType").val('R').trigger("chosen:updated");
        $("#oddEven").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
        document.getElementById("attPerfromdate").setAttribute('disabled', true);
        document.getElementById("attPerTodate").setAttribute('disabled', true);
        document.getElementById("regEventFrom").setAttribute('disabled', true);
        document.getElementById("regEventTo").setAttribute('disabled', true);
        var re = document.getElementById("regisid");
        re.innerHTML = "";
        var apfd = document.getElementById("apfd");
        apfd.innerHTML = "";
        var aptd = document.getElementById("aptd");
        aptd.innerHTML = "";
        var apfd = document.getElementById("refd");
        apfd.innerHTML = "";
        var aptd = document.getElementById("retd");
        aptd.innerHTML = "";
    }
    $("#datetimepicker3").focusout(function () {
        var dateFrom = "01/05/2019";
        var dateTo = "25/05/2019";
        var dateCheck = "02/05/2019";

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

    function Disabletxtfield() {

        var result = document.querySelector('input[name="excludePreReg"]:checked').value;
        if (result == "Y") {

            document.getElementById("regEventFrom").value = '';
            document.getElementById("regEventTo").value = '';
            document.getElementById("regEventFrom").setAttribute('disabled', true);
            document.getElementById("regEventTo").setAttribute('disabled', true);
            var apfd = document.getElementById("refd");
            apfd.innerHTML = "";
            var aptd = document.getElementById("retd");
            aptd.innerHTML = "";

        } else {
            document.getElementById("regEventFrom").value = '';
            document.getElementById("regEventTo").value = '';
            document.getElementById("regEventFrom").removeAttribute('disabled');
            document.getElementById("regEventTo").removeAttribute('disabled');
            var apfd = document.getElementById("refd");
            apfd.innerHTML = "*";
            var aptd = document.getElementById("retd");
            aptd.innerHTML = "*";

        }
    }
    $(function () {
        $('#datetimepicker1').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker2').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker1").on("dp.change", function (e) {
            $('#datetimepicker2').data("DateTimePicker").minDate(e.date);
            $("#attPerTodate").val('');
            $("#attPerfromdate").val('');
            $("#examPerioddate").val('');
            $("#regEventTo").val('');
            $("#regEventFrom").val('');
            $("#hostelTodate").val('');
            $("#hostelFromdate").val('');
        });
        $("#datetimepicker2").on("dp.change", function (e) {
            $('#datetimepicker1').data("DateTimePicker").maxDate(e.date);
            $("#attPerTodate").val('');
            $("#attPerfromdate").val('');
            $("#examPerioddate").val('');
            $("#regEventTo").val('');
            $("#regEventFrom").val('');
            $("#hostelTodate").val('');
            $("#hostelFromdate").val('');
        });
    });
    $(function () {
        $('#datetimepicker3').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker4').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker3").on("dp.change", function (e) {
            $('#datetimepicker4').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker4").on("dp.change", function (e) {
            $('#datetimepicker3').data("DateTimePicker").maxDate(e.date);
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
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker9").on("dp.change", function (e) {
            $('#datetimepicker10').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker10").on("dp.change", function (e) {
            $('#datetimepicker9').data("DateTimePicker").maxDate(e.date);
        });
    });
    $(function () {
        $('#datetimepicker7').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker8').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker7").on("dp.change", function (e) {
            $('#datetimepicker8').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker8").on("dp.change", function (e) {
            $('#datetimepicker7').data("DateTimePicker").maxDate(e.date);
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
        $("#datetimepicker11").on("dp.change", function (e) {
            $('#datetimepicker12').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker12").on("dp.change", function (e) {
            $('#datetimepicker11').data("DateTimePicker").maxDate(e.date);
        });
    });
    $(function () {
        $('#datetimepicker13').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker14').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
        $("#datetimepicker13").on("dp.change", function (e) {
            $('#datetimepicker14').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker14").on("dp.change", function (e) {
            $('#datetimepicker13').data("DateTimePicker").maxDate(e.date);
        });
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

    function ShowHide() {
        var semster = $("#Sc").show();
        if ($("#loadDistribution").prop('checked') == true) {
            $('#Sc').show();
        } else {
            $('#Sc').hide();
        }
    }
    function ShowHide1() {
        if ($("#examGrade").prop('checked') == true) {
            $('#Ay').show();
            $('#Pc').show();
        } else {
            $('#Ay').hide();
            $('#Pc').hide();
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
                                    url: "registrationMaster/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        //    $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Saved.', "Success!!");
                                            loadForm("registrationMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        //   setTimeout($.unblockUI, 1000);
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


</script>
