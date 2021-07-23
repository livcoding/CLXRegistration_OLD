<%-- 
    Document   : guestStudentMasterAddress
    Created on : Apr 9, 2019, 1:39:59 PM
    Author     : priya.sharma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<!DOCTYPE html>
<div class="step-pane sample-pane alert" data-step="2" id="tabs-2">   
    <form id="ajaxform_tab2"  class="form-horizontal">
        <input type="hidden" id='student_pk2' value="${personalInfo.gueststudentid}" class="" name="student_pk2">
        <!--        <div class="row col-lg-12">
                    <div class="row col-lg-6 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address/Contact Details
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    </div>
                </div>
                <div class="row col-lg-12">
        
                    <div class="row col-lg-6 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Correspondence Address:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    </div>
                </div>-->
        <br><br>
        <h4>Permanent Address</h4>
        <br><br>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 1:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"  data-validation="required" name="paddress1" id="paddress1" maxlength="70" placeholder="Enter Address 1 " value="${addressInfo.paddress1}" class="form-control has-help-txt" data-validation-error-msg="Please enter address 1!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 2:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70" name="paddress2"  id="paddress2" maxlength="70" placeholder="Enter Address 2 " value="${addressInfo.paddress2}" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 3:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70" name="pAddress3"  id="pAddress3" maxlength="70" placeholder="Enter Address 3 " value="${addressInfo.paddress3}" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Country: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">

                    <select class="form-control"  id="pCountry" onchange="getPermState();" name="pCountry" data-validation="required" data-live-search="true" data-validation-error-msg="Please select country!">
                        <option value="">Select</option>
                        <c:forEach items="${country}" var="cntryList">
                            <c:if test="${cntryList[0] == addressInfo.ccountryname}">
                                <option selected="true" value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
                            </c:if>
                            <c:if test="${cntryList[0] != addressInfo.ccountryname}">
                                <option value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
<!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required" name="city" id="city" maxlength="25" placeholder="City " value="${addressInfo.pcityname}" class="form-control has-help-txt"/>-->
                </div>
            </div>

        </div>


        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">State:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="pState" name="pState" onchange="getPermCity();" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select state!">
                        <option value="">Select</option>
                    </select> 
<!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required" name="pState"  id="pState" maxlength="25" placeholder="Enter State " value="${addressInfo.pstatename}" class="form-control has-help-txt"/>-->
                </div>
            </div>


            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">City:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="pCity" name="pCity" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select city!">
                        <option value="">Select</option>
                    </select> 
<!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required" name="pCountry" id="pCountry" maxlength="25" placeholder="Enter Country " value="${addressInfo.pcountryname}" class="form-control has-help-txt"/>-->
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Pin:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 6"  onkeypress="return isNumberKey(event)" data-validation="required" data-validation="number" name="pPIN" id="pPIN" maxlength="6" minlength="6" placeholder="Enter PIN " value="${addressInfo.ppin}" class="form-control has-help-txt" data-validation-error-msg="Please enter pin!"/>
                </div>
            </div>
        </div>

        <br><br><br>
        <h4>Correspondence Address</h4>
        <br><br>
        <div class="row col-lg-12" style="margin-top: 20px; margin-bottom: 20px">
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Same as Permanent  Address
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" id="copyAdd" name="copyAdd" value="" onclick="copyAddress();"/>  
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 1:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"  data-validation="required" name="address1" id="address1" maxlength="70" placeholder="Enter Address 1 " value="${addressInfo.caddress1}" class="form-control has-help-txt" data-validation-error-msg="Please enter address 1!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 2:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70" name="address2" id="address2" maxlength="70" placeholder="Enter Address 2 " value="${addressInfo.caddress2}" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 3:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70" name="address3"  id="address3" maxlength="70" placeholder="Enter Address 3 " value="${addressInfo.caddress3}" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Country: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="country" onchange="getStateName();" name="country" data-validation="required" data-live-search="true" data-validation-error-msg="Please select country!">
                        <option value="">Select</option>
                        <c:forEach items="${country}" var="cntryList">
                            <c:if test="${cntryList[0] == addressInfo.ccountryname}">
                                <option selected="true" value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
                            </c:if>
                            <c:if test="${cntryList[0] != addressInfo.ccountryname}">
                                <option value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
                            </c:if>
                        </c:forEach>
                    </select>
<!--                    <input type="text" data-validation-help="Max length is 100" name="city"  data-validation="required"  id="city" maxlength="100" placeholder="City " value="${addressInfo.ccityname}" class="form-control has-help-txt"/>-->
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">State:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="state" name="state" onchange="getCity();" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select state!">
                        <option value="">Select</option>
                    </select> 
<!--                    <input type="text" data-validation-help="Max length is 100" name="state"  data-validation="required" id="state" maxlength="100" placeholder="Enter State " value="${addressInfo.cstatename}" class="form-control has-help-txt"/>-->
                </div>
            </div>

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">City:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="city" name="city" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select city!">
                        <option value="">Select</option>
                    </select>   
<!--                    <input type="text" data-validation-help="Max length is 25" name="country"  data-validation="required" id="country" maxlength="100" placeholder="Enter Country " value="${addressInfo.ccountryname}" class="form-control has-help-txt"/>-->
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PIN Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 6" name="pin"  onkeypress="return isNumberKey(event)" data-validation="required" data-validation="number"  id="pin" minlength="6" maxlength="6"  placeholder="Enter PIN " value="${addressInfo.cpin}" class="form-control has-help-txt"   data-validation-error-msg="Please enter pin!"/>
                </div>
            </div>
        </div>

        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Mobile No.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 10"  data-validation="required" onkeypress="return isNumberKey(event)"  data-validation="number" name="studentMobileNo" id="studentMobileNo" maxlength="10" minlength="10" placeholder="Enter Student Mobile No. " value="${addressInfo.smobileno}" class="form-control has-help-txt" data-validation-error-msg="Please enter student mob. no.!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Mobile No.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 10"  data-validation="required" onkeypress="return isNumberKey(event)" data-validation="number" name="parentMobileNo" id="parentMobileNo" maxlength="10" minlength="10" placeholder="Enter Parent Mobile No. " value="${addressInfo.pmobileno}" class="form-control has-help-txt" data-validation-error-msg="Please enter parent mob. no.!"/>
                </div>
            </div>


        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Email Id.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 60" name="studentEmailId" data-validation="email" data-validation="required" id="studentEmailId" maxlength="60" placeholder="Enter Student Email Id " value="${addressInfo.semailid}" class="form-control has-help-txt" data-validation-error-msg="Please enter student emailid!/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Email Id.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 60"  name="parentEmailId" data-validation="email" data-validation="required"  id="parentEmailId" maxlength="60" placeholder="Enter Parent Email Id  " value="${addressInfo.pemailid}" class="form-control has-help-txt" data-validation-error-msg="Please enter parent emailid!"/>
                </div>
            </div>


        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Guest Validity From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker2'>
                        <div class='input-group date' id='validFromId'>
                            <fmt:formatDate value='${personalInfo.validfrom}' var='validFromId' type='date' pattern='dd/MM/yyyy'/>
                            <input type='text' name="validfrom" id="validfrom" data-validation="required" onblur="checkToDate();" value="${validFromId}" class="form-control"   data-validation-error-msg="Please select guest validity from date!"/>
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div> 
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Guest Validity To Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker3'>
                        <div class='input-group date' id='validUptoId'>
                            <fmt:formatDate value='${personalInfo.validupto}' var='validUptoId' type='date' pattern='dd/MM/yyyy'/>
                            <input type='text' name="validupto" id="validupto" data-validation="required" onblur="checkFromDate();" value="${validUptoId}" class="form-control"data-validation-error-msg="Please select guest validity to date!" />
                            <span class="input-group-addon">
                                <span class="glyphicon glyphicon-calendar"></span>
                            </span>
                        </div> 
                    </div>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Login to be Created:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${personalInfo.studentloginid == 'Y'}">
                        <input type="checkbox" id="studentLoginId" name="studentLoginId" checked="true" value="Y"/>  
                    </c:if>
                    <c:if test="${personalInfo.studentloginid!= 'Y'}">
                        <input type="checkbox" id="studentLoginId" name="studentLoginId" value="Y"/>  
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Login to be Created:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${personalInfo.parentloginid == 'Y'}">
                        <input type="checkbox" id="parentLoginId" name="parentLoginId" checked="true" value="Y"/>  
                    </c:if>
                    <c:if test="${personalInfo.parentloginid!= 'Y'}">
                        <input type="checkbox" id="parentLoginId" name="parentLoginId" value="Y"/>  
                    </c:if>
                </div>
            </div>
        </div>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:setFormIdAndUrl('ajaxform_tab2','guestStudentMaster/addressDetailUpdate',2);" class="btn btn-success btn-sm btn-flat"> Update</a>
            </div> 
        </div>
    </form>
</div>
<script>
    $("#pCountry").chosen({width: "100%"});
    $("#pState").chosen({width: "100%"});
    $("#pCity").chosen({width: "100%"});
    $("#country").chosen({width: "100%"});
    $("#state").chosen({width: "100%"});
    $("#city").chosen({width: "100%"});

    $(function () {
        $('#datetimepicker2').datetimepicker({
            format: 'DD/MM/YYYY'
        });
        $('#datetimepicker3').datetimepicker({
            useCurrent: false,
            format: 'DD/MM/YYYY'
        });
//        $("#datetimepicker2").on("dp.change", function (e) {
//            $('#datetimepicker3').data("DateTimePicker").minDate(e.date);
//        });
//        $("#datetimepicker3").on("dp.change", function (e) {
//            $('#datetimepicker2').data("DateTimePicker").maxDate(e.date);
//        });
    });
    function saveData() {
        $("#ajaxform_tab2").submit();
    }


    function isNumberKey(evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }

    function copyAddress() {
        if ($("#copyAdd").prop('checked')) {
            $("#myCheck").prop("checked", true);
            var address1 = $('#paddress1').val();
            var address2 = $('#paddress2').val();
            var address3 = $('#pAddress3').val();
            var city = $('#pCity').val();
            var state = $('#pState').val();
            var country = $('#pCountry').val();
            var pin = $('#pPIN').val();
            if (address1 != '') {
                if (country != '') {
                    if (state != '') {
                        if (city != '') {
                            if (pin != '') {
                                $('#address1').val($('#paddress1').val());
                                $('#address2').val($('#paddress2').val());
                                $('#address3').val($('#pAddress3').val());
                                $('#country').val($('#pCountry').val());
                                $("#country").trigger("chosen:updated");
                                getStateName();
                                $('#state').val($('#pState').val());
                                $("#state").trigger("chosen:updated");
                                getCity();
                                $('#city').val($('#pCity').val());
                                $("#city").trigger("chosen:updated")
                                $('#pin').val($('#pPIN').val());
                            } else {
                                BootstrapDialog.alert("Please Enter  the Permanent Pin Code First!!");
                                $("#copyAdd").prop("checked", false);
                            }
                        } else {
                            BootstrapDialog.alert("Please Select the Permanent City First!!");
                            $("#copyAdd").prop("checked", false);
                        }
                    } else {
                        BootstrapDialog.alert("Please Select the Permanent State First!!");
                        $("#copyAdd").prop("checked", false);
                    }
                } else {
                    BootstrapDialog.alert("Please Select the Permanent Country First!!");
                    $("#copyAdd").prop("checked", false);
                }
            } else {
                BootstrapDialog.alert("Please Enter the Permanent Address First!!");
                $("#copyAdd").prop("checked", false);
            }
        } else {
            $('#address1').val('');
            $('#address2').val('');
            $('#address3').val('');
            $('#city').val('x');
            $('#city').val('').trigger("chosen:updated");
            $('#state').val('x');
            $('#state').val('').trigger("chosen:updated");
            $('#country').val('x');
            $('#country').val('').trigger("chosen:updated");
            $('#pin').val('');
        }
    }
    function myReset() {
        $("#registrationType").val('').trigger("chosen:updated");
        $("#pCountry").val('').trigger("chosen:update")
        $("#pState").val('').trigger("chosen:update")
        $("#pcity").val('').trigger("chosen:update")
        $("#country").val('').trigger("chosen:update")
        $("#state").val('').trigger("chosen:update")
        $("#city").val('').trigger("chosen:update")

        $("#oddEven").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }

//    $(document).ready(function () {
//        getStateName();
//        getCity();
//        getPermState();
//        getPermCity();
//    });
    function getStateName() {
        var cntryId = $("#country").val();
        if (cntryId != '') {
            $.ajax({
                url: "guestStudentMaster/getStateOfCountry",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "cntryId=" + cntryId,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#state").empty();
                    if (data.state != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.state, function (i, item)
                        {
                            if (item[0] == '${addressInfo.cstatename}') {
                                str1 += '<option value="' + item[0] + '" selected="true">' + item[2] + ' / ' + item[3] + '</option>';
                            } else {
                                str1 += '<option value="' + item[0] + '">' + item[2] + ' / ' + item[3] + '</option>';
                            }
                        });
                        $("#state").append(str1);
                        $("#state").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("State Not Found For This Country,Please Select Another Country...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please select any country!!");
        }
    }

    function getCity() {
        var cntryId = $("#country").val();
        var stateId = $("#state").val();
        if (cntryId != '' && stateId != '') {
            $.ajax({
                url: "guestStudentMaster/getCityOfState",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "cntryId=" + cntryId + "&stateId=" + stateId,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#city").empty();
                    if (data.city != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.city, function (i, item)
                        {
                            if (item[0] == '${addressInfo.ccityname}') {
                                str1 += '<option value="' + item[0] + '" selected="true">' + item[3] + ' / ' + item[4] + '</option>';
                            } else {
                                str1 += '<option value="' + item[0] + '">' + item[3] + ' / ' + item[4] + '</option>';
                            }
                        });
                        $("#city").append(str1);
                        $("#city").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("City Not Found For This State,Please Select Another State...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please select country and state!!");
        }
    }
    function getPermState() {
        var cntryId = $("#pCountry").val();
        if (cntryId != '') {
            $.ajax({
                url: "guestStudentMaster/getStateOfCountry",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "cntryId=" + cntryId,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#pState").empty();
                    if (data.state != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.state, function (i, item)
                        {
                            if (item[0] == '${addressInfo.pstatename}') {
                                str1 += '<option value="' + item[0] + '" selected="true">' + item[2] + ' / ' + item[3] + '</option>';
                            } else {
                                str1 += '<option value="' + item[0] + '">' + item[2] + ' / ' + item[3] + '</option>';
                            }
                        });
                        $("#pState").append(str1);
                        $("#pState").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("State Not Found For This Country,Please Select Another Country...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please select any country!!");
        }
    }

    function getPermCity() {
        var cntryId = $("#pCountry").val();
        var stateId = $("#pState").val();
        if (cntryId != '' && stateId != '') {
            $.ajax({
                url: "guestStudentMaster/getCityOfState",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "cntryId=" + cntryId + "&stateId=" + stateId,
                error: (function (response) {
                    alert('Server Error' + response);
                }),
                success: function (data) {
                    $("#pCity").empty();
                    if (data.city != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.city, function (i, item)
                        {
                            if (item[0] == '${addressInfo.pcityname}') {
                                str1 += '<option value="' + item[0] + '" selected="true">' + item[3] + ' / ' + item[4] + '</option>';
                            } else {
                                str1 += '<option value="' + item[0] + '">' + item[3] + ' / ' + item[4] + '</option>';
                            }
                        });
                        $("#pCity").append(str1);
                        $("#pCity").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("City Not Found For This State,Please Select Another State...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please select country and state!!");
        }
    }
    function checkToDate() {
        var start_date = $("#validfrom").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#validupto").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#validupto").val() != '') {
            if ($("#validfrom").val() != '') {
                if (fromYear > toYear) {
                    $("#validfrom").val('');
                    BootstrapDialog.alert("Please select another date less than the Guest Validity from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#validfrom").val('');
                        BootstrapDialog.alert("Please select another date less than the Guest Validity date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#validfrom").val('');
                            BootstrapDialog.alert("Please select another date less than the Guest Validity from date!");
                        }
                    }
                }

            } else {
                $("#validfrom").val('');
                BootstrapDialog.alert("Please select  Guest Validity from date first!");
            }
        }
    }
    
     function checkFromDate() {
        var start_date = $("#validfrom").val().split("/");
        var dateOne = new Date(start_date[2], start_date[0], start_date[1]);
        var fromYear = start_date[2];
        var fromMonth = start_date[1];
        var fromDate = start_date[0];
        var end_date = $("#validupto").val().split("/");
        var dateTwo = new Date(end_date[2], end_date[0], end_date[1]);
        var toYear = end_date[2];
        var toMonth = end_date[1];
        var toDate = end_date[0];
        if ($("#validupto").val() != '') {
            if ($("#validfrom").val() != '') {
                if (fromYear > toYear) {
                    $("#validupto").val('');
                    BootstrapDialog.alert("Please select another date greater than the Guest Validity from date!");
                } else if (fromYear == toYear) {
                    if (fromMonth > toMonth) {
                        $("#validupto").val('');
                        BootstrapDialog.alert("Please select another date greater than the Guest Validity date!");
                    } else if (fromMonth == toMonth) {
                        if (fromDate > toDate) {
                            $("#validupto").val('');
                            BootstrapDialog.alert("Please select another date greater than the Guest Validity from date!");
                        }
                    }
                }

            } else {
                $("#validupto").val('');
                BootstrapDialog.alert("Please select  Guest Validity from date first!");
            }
        }
    }
</script>