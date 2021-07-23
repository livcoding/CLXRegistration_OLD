<%-- 
Document   : guestStudentMasterPersonalDetails
Created on : Apr 9, 2019, 3:13:12 PM
Author     : priya.sharma
--%>
<%@include file="../mainjstl.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="tabs-1" class="step-pane active sample-pane alert" data-step="1">
    <form method="POST" id="ajaxform_tab1" class="form-horizontal">
        <input type="hidden" id='student_pk1' value="" class="" name="student_pk1">
        <div class="row col-lg-12 form-group" id="divContainer">      
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Enrollment No.:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20" onkeypress="return isNumberKey(event)" data-validation="required" id="enrollmentNo" maxlength="20" placeholder="Enter Enrollment No. " name="enrollmentNo" class="form-control has-help-txt"  data-validation-error-msg="Please enter enrollment no.!"/>
                    </div>
                </div>

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">From Institute Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text"  data-validation="required" data-validation-help="Max length is 100" id="frominstititename"  maxlength="100"  placeholder="Enter Institute Name " name="frominstititename" class="form-control has-help-txt"  data-validation-error-msg="Please enter from institute name!"/>
                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group" >      
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 60"  onkeyup="this.value = this.value.toUpperCase();" data-validation="required" id="name" maxlength="60" placeholder="Enter Name " name="name" class="form-control has-help-txt"  data-validation-error-msg="Please enter name!"/>
                    </div>
                </div> 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">DOB:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <div class='input-group date' id='datetimepicker1'>
                            <input type='text' class="form-control" id="dob" name="dob" data-validation="required" data-validation-error-msg="Please select dob !" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div>
                    </div>
                </div> 
            </div>
            <div class="row col-lg-12 form-group">      
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Academic Year:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <select class="form-control" id="academicYear" name="academicYear" data-validation="required" data-live-search="true" data-validation-error-msg="Please select academic year!">
                            <option value="">Select</option>
                            <c:forEach items="${academic}" var="list">
                                <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                            </c:forEach> 
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Father's Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 60"   onkeyup="this.value = this.value.toUpperCase();"  data-validation="required" id="fathersName" maxlength="60" placeholder="Enter Father's Name " name="fathersName" class="form-control has-help-txt"  data-validation-error-msg="Please enter father's name!"/>
                    </div>
                </div> 
            </div>
            <div class="row col-lg-12 form-group" >      
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Mother's Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 60"  onkeyup="this.value = this.value.toUpperCase();"  data-validation="required" id="mothersName" maxlength="60" placeholder="Enter Mother's Name " name="mothersName" class="form-control has-help-txt"  data-validation-error-msg="Please enter mother's name!"/>
                    </div>
                </div> 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Nationality:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 100"  data-validation="required" id="nationality" maxlength="100" placeholder="Enter Nationality " name="nationality" class="form-control has-help-txt"  data-validation-error-msg="Please enter nationality!"/>
                    </div>
                </div> 
            </div>

            <div class="row col-lg-12 form-group"> 
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Gender:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger" > *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <select class="form-control " id="gender" name="gender" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select gender!">
                            <option value="">Select</option>
                            <option value="M" selected="true">Male</option>
                            <option value="F">Female</option>
                            <option value="T">Transgender</option>
                        </select>
                    </div>
                </div>

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Blood Group:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger" > *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <select class="form-control" id="bloodGroup" name="bloodGroup" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select blood group!">
                            <option value="">Select</option>
                            <option value="A+">A+</option>
                            <option value="A-">A-</option>
                            <option value="B+">B+</option>
                            <option value="B-">B-</option>
                            <option value="O+">O+</option>
                            <option value="O-">O-</option>
                            <option value="AB+">AB+</option>
                            <option value="AB-">AB-</option>

                        </select>
                    </div>
                </div>

            </div>
            <div class="row col-lg-12 form-group">                 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Program Code:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20"  onkeyup="this.value = this.value.toUpperCase();"  id="program" maxlength="20" placeholder="Enter Program Code" name="program" class="form-control has-help-txt"/>

                    </div>
                </div>
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Branch Code:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20"   onkeyup="this.value = this.value.toUpperCase();"  id="branch" maxlength="20" placeholder="Enter Branch Code" name="branch" class="form-control has-help-txt"/>
                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group">                 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">STY No.:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text"  id="sem" placeholder="Enter STY No." data-validation-help="Max length is 2"  onkeypress="return isNumberKey(event)" maxlength="2" name="sem" data-validation-optional="true" data-validation="number" class="form-control has-help-txt"/>
                    </div>
                </div>
                <div class="row col-lg-6 form-group" id="alloteeSection" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Allotee Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text"   id="sectCode" placeholder="Enter Section Code"   data-validation-help="Max length is 20"  maxlength="20"  onkeyup="this.value = this.value.toUpperCase();"  name="sectCode" class="form-control has-help-txt"/>
                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group">                 

                <div class="row col-lg-6 form-group" id="alloteeSubSection" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Allotee Sub Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text"   id="subSect" placeholder="Enter Sub Section Code"  data-validation-help="Max length is 20"  maxlength="20"  onkeyup="this.value = this.value.toUpperCase();"  name="subSect" class="form-control has-help-txt"/>
                    </div>
                </div>
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Reference Detail:</label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20"  id="referenceDetail" maxlength="20" placeholder="Enter Reference Detail: " name="referenceDetail" class="form-control has-help-txt"/>
                    </div>
                </div>
                <div class="col-lg-12 form-group">              
                    <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                        <a href="javascript:setFormIdAndUrl('ajaxform_tab1','guestStudentMaster/personalInfoSave',1);" class="btn btn-success btn-sm btn-flat"> Save</a>
                        <button onclick="personalReset();" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script>

    $("#gender").chosen();
    $("#academicYear").chosen();
    $("#bloodGroup").chosen();
    $(document).ready(function () {

    });

    $('#datetimepicker1').datetimepicker({
        format: 'DD/MM/YYYY'
    });

    function personalReset() {
        $("#academicYear").val('').trigger("chosen:updated");
        $("#gender").val('').trigger("chosen:updated");
        $("#bloodGroup").val('').trigger("chosen:updated");

    }
</script>