<%-- 
    Document   : guestStudentMasterPersonalDetails
    Created on : Apr 9, 2019, 3:13:12 PM
    Author     : priya.sharma
--%>
<%@include file="../mainjstl.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="tabs-1" class="step-pane active sample-pane alert" data-step="1">
    <form method="POST" id="ajaxform_tab1" class="form-horizontal">
        <input type="hidden" id='student_pk1' value="${personalInfo.gueststudentid}" class="" name="student_pk1">
        <div class="row col-lg-12 form-group" id="divContainer">      
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Enrollment No.:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20"  onkeypress="return isNumberKey(event)"  data-validation="required" id="enrollmentNo" maxlength="20" placeholder="Enter Enrollment No. " name="enrollmentNo" value="${personalInfo.enrollmentno}" class="form-control has-help-txt" data-validation-error-msg="Please enter enrollment no.!"/>
                    </div>
                </div>

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">From Institute Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">

                        <input type="text"  data-validation="required"  data-validation-help="Max length is 100" id="frominstititename"  name="frominstititename"  maxlength="100" value="${personalInfo.frominstitutename}" class="form-control has-help-txt" data-validation-error-msg="Please enter from institute name!"/>

                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group" >      
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 60"  onkeyup="this.value = this.value.toUpperCase();" data-validation="required" id="name" maxlength="60" placeholder="Enter Name " name="name"  value="${personalInfo.name}" class="form-control has-help-txt" data-validation-error-msg="Please enter name!"/>
                    </div>
                </div> 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">DOB:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <div class='input-group date' id='datetimepicker1'>
                            <div class='input-group date' id='dob'>
                                <fmt:formatDate value='${personalInfo.dateofbirth}' var='dateOfb' type='date' pattern='dd/MM/yyyy'/>
                                <input type='text' name="dob" data-validation="required" value="${dateOfb}" class="form-control" data-validation-error-msg="Please select dob !"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>     
                        </div>
                    </div>
                </div> 
            </div>
            <div class="row col-lg-12 form-group">      
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Academic Year:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <select class="form-control " id="acadYear" name="acadYear" data-validation="required" data-live-search="true" data-validation-error-msg="Please select academic year!">
                            <option value="">Select</option>
                            <c:forEach items="${academic}" var="acadYrList">
                                <c:if test="${acadYrList[1]== personalInfo.acadyear}">
                                    <option value="${acadYrList[1]}" selected="true"><c:out value="${acadYrList[1]}"/></option>
                                </c:if>
                                <c:if test="${acadYrList[1]!= personalInfo.acadyear}">
                                    <option value="${acadYrList[1]}"><c:out value="${acadYrList[1]}"/></option>                     
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div> 
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Father's Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 60"   onkeyup="this.value = this.value.toUpperCase();" data-validation="required" id="fathersName" name="fathersName" maxlength="60" placeholder="Enter Father's Name " value="${personalInfo.fathersname}" class="form-control has-help-txt"   data-validation-error-msg="Please enter father's name!"/>
                    </div>
                </div> 
            </div>
            <div class="row col-lg-12 form-group" >      
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Mother's Name:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 60"  onkeyup="this.value = this.value.toUpperCase();" data-validation="required" id="mothersName" name="mothersName"maxlength="60" placeholder="Enter Mother's Name " value="${personalInfo.mothersname}" class="form-control has-help-txt"  data-validation-error-msg="Please enter mother's name!"/>
                    </div>
                </div> 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Nationality:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 100"  data-validation="required" id="nationality" name="nationality" maxlength="100" placeholder="Enter Nationality " value="${personalInfo.nationality}" class="form-control has-help-txt"  data-validation-error-msg="Please enter nationality!"/>
                    </div>
                </div> 
            </div>

            <div class="row col-lg-12 form-group"> 
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Gender:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <select class="form-control " id="gender" name="gender" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select gender!">
                            <option value="">Select</option>
                            <c:if test="${personalInfo.gender=='M'}">
                                <option value="M" selected="true">Male</option>
                                <option value="F">Female</option>
                                <option value="T">Transgender</option>
                            </c:if>
                            <c:if test="${personalInfo.gender=='F'}">
                                <option value="M">Male</option>
                                <option value="F" selected="true">Female</option>
                                <option value="T">Transgender</option>
                            </c:if>
                            <c:if test="${personalInfo.gender=='T'}">
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                                <option value="T" selected="true">Transgender</option>
                            </c:if>

                        </select>
                    </div>
                </div>

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Blood Group:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <select class="form-control " id="bloodgroup" name="bloodgroup" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select blood group!">
                            <option value="">Select</option>
                            <c:if test="${personalInfo.bloodgroup=='None'}">
                                <option value="A+" >A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='A+'}">
                                <option value="A+" selected="true">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='A-'}">
                                <option value="A+" >A+</option>
                                <option value="A-" selected="true">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='B+'}">
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+"  selected="true">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='B-'}">
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-" selected="true">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='O+'}">
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+" selected="true">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='O-'}">
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-" selected="true">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='AB+'}">
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+" selected="true">AB+</option>
                                <option value="AB-">AB-</option>
                            </c:if>
                            <c:if test="${personalInfo.bloodgroup=='AB-'}">
                                <option value="A+">A+</option>
                                <option value="A-">A-</option>
                                <option value="B+">B+</option>
                                <option value="B-">B-</option>
                                <option value="O+">O+</option>
                                <option value="O-">O-</option>
                                <option value="AB+">AB+</option>
                                <option value="AB-" selected="true">AB-</option>
                            </c:if>
                        </select></div>
                </div>

            </div>
            <div class="row col-lg-12 form-group">                 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Program Code:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20"  onkeyup="this.value = this.value.toUpperCase();" id="program" maxlength="20" placeholder="Enter Program Code" name="program" value="${personalInfo.programcode}" class="form-control has-help-txt"/>
                    </div>
                </div>
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Branch Code:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 20"    onkeyup="this.value = this.value.toUpperCase();"id="branch" maxlength="20" placeholder="Enter Branch Code" name="branch" value="${personalInfo.branchcode}" class="form-control has-help-txt"/>
                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group">                 
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">STY No.:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text"  id="sem" placeholder="Enter STY No." onkeypress="return isNumberKey(event)" data-validation-help="Max length is 2"  maxlength="2" name="sem"  value="${personalInfo.stynumber}" class="form-control has-help-txt"/>
                    </div>
                </div>
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Allotee Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">

                        <input type="text"   id="sectCode" placeholder="Enter Section Code"  data-validation-help="Max length is 20"  maxlength="20" onkeyup="this.value = this.value.toUpperCase();"name="sectCode" value="${personalInfo.sectioncode}" class="form-control has-help-txt"/>

                    </div>
                </div>
            </div>
            <div class="row col-lg-12 form-group">                 

                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Allotee Sub Section:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text"   id="subSect" placeholder="Enter Sub Section Code" onkeyup="this.value = this.value.toUpperCase();" data-validation-help="Max length is 20"  maxlength="20"  name="subSect" value="${personalInfo.subsectioncode}" class="form-control has-help-txt"/>

                    </div>
                </div>
                <div class="row col-lg-6 form-group">
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Reference Detail:</label>
                    <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                        <input type="text" data-validation-help="Max length is 100"  id="referenceDetail"  name="referenceDetail" maxlength="100" placeholder="Enter Reference Detail: " value="${personalInfo.referencedetail}" class="form-control has-help-txt"/>
                    </div>
                </div>

                <div class="col-lg-12 form-group">              
                    <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                        <a href="javascript:setFormIdAndUrl('ajaxform_tab1','guestStudentMaster/personalInfoUpdate',1);" class="btn btn-success btn-sm btn-flat">Update</a>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
    $("#acadYear").chosen();
    $("#bloodgroup").chosen();
    $("#gender").chosen();
    $('#datetimepicker1').datetimepicker({
        format: 'DD/MM/YYYY'
    });
</script>