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
        <input type="hidden" id='student_pk2' value="" class="" name="student_pk2">
        <!--        <div class="row col-lg-12">
                    <div class="row col-lg-6 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address/Contact Details
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    </div>
                </div>-->

        <!--        <div class="row col-lg-12">
                    <div class="row col-lg-6 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Permanent Address:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    </div>
                </div>-->
        <br>
        <h4>Permanent Address</h4>
        <br><br>
        <div class="row col-lg-12">
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 1:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"  data-validation="required" id="paddress1" maxlength="70" placeholder="Enter Address 1 " name="paddress1" class="form-control has-help-txt"  data-validation-error-msg="Please enter address 1!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 2:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"   id="paddress2" maxlength="70" placeholder="Enter Address 2 " name="paddress2" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 3:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"   id="pAddress3" maxlength="70" placeholder="Enter Address 3 " name="pAddress3" class="form-control has-help-txt"/>
                </div>
            </div>

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Country:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"  id="pCountry" maxlength="25" placeholder="Enter Country " name="pCountry" class="form-control has-help-txt"/>-->
                    <select class="form-control"  id="pCountry" onchange="getPermState();" name="pCountry" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select country!">
                        <option value="">Select</option>
                        <c:forEach items="${country}" var="cntryList">
                            <option value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>

        </div>


        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">State:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"   id="pState" maxlength="25" placeholder="Enter State " name="pState" class="form-control has-help-txt"/>-->
                    <select class="form-control"  id="pState" name="pState" onchange="getPermCity();" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select state!">
                        <option value="">Select</option>
                    </select>
                </div>
            </div>


            <!--            <div class="row col-lg-6 form-group" >
                            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Country:
                                <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                            <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                                                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"  id="pCountry" maxlength="25" placeholder="Enter Country " name="pCountry" class="form-control has-help-txt"/>
                                <select class="form-control"  id="pCountry" onchange="getStateName();" name="pCountry" data-validation="required" data-live-search="true">
                                    <option value="">Select</option>
            <c:forEach items="${country}" var="cntryList">
                <option value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
            </c:forEach>
        </select>
    </div>
</div>-->
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">City:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> * </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"  id="pcity" maxlength="25" placeholder="City " name="pcity" class="form-control has-help-txt"/>-->
                    <select class="form-control"  id="pcity" name="pcity" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select city!">
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PIN:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 6"  onkeypress="return isNumberKey(event)" data-validation="number" id="pPIN" maxlength="6" minlength="6" placeholder="Enter PIN " name="pPIN" class="form-control has-help-txt "  data-validation-error-msg="Please enter pin!"/>
                </div>
            </div>
        </div>
        <br><br>
        <h3>Correspondence Address</h3>
        <br><br>
        <div class="row col-lg-12" style="margin-top: 20px; margin-bottom: 20px">
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Same as Permanent Address
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" id="copyAdd" name="copyAdd" value="" onclick="copyAddress();"/>  
                </div>
            </div>
        </div>

        <!--        <div class="row col-lg-12">
        
                    <div class="row col-lg-6 form-group" >
                        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Correspondence Address:
                            <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                    </div>
                </div>-->

        <div class="row col-lg-12">
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 1:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"  data-validation="required" id="address1" maxlength="70" placeholder="Enter Address 1 " name="address1" class="form-control has-help-txt"  data-validation-error-msg="Please enter address 1!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 2:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"  id="address2" maxlength="70" placeholder="Enter Address 2 " name="address2" class="form-control has-help-txt"/>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Address 3:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 70"  id="address3" maxlength="70" placeholder="Enter Address 3 " name="address3" class="form-control has-help-txt"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Country: 
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="country" onchange="getStateName();" name="country" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select country!">
                        <option value="">Select</option>
                        <c:forEach items="${country}" var="cntryList">
                            <option value="${cntryList[0]}"><c:out value="${cntryList[1]} / ${cntryList[2]}"/></option>
                        </c:forEach>
                    </select>
                    <!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"  id="city" maxlength="25" placeholder="City " name="city" class="form-control has-help-txt"/>-->
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">State:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="state" name="state" onchange="getCity();" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select state!">
                        <option value="">Select</option>
                    </select>
                    <!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"  id="state" maxlength="25" placeholder="Enter State " name="state" class="form-control has-help-txt"/>-->
                </div>
            </div>

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">City:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> * </span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control"  id="city" name="city" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select city!">
                        <option value="">Select</option>
                    </select>
                    <!--                    <input type="text" data-validation-help="Max length is 25"  data-validation="required"  id="country" maxlength="25" placeholder="Enter Country " name="country" class="form-control has-help-txt"/>-->
                </div>
            </div>
        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PIN:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">  *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 6" data-validation="number" onkeypress="return isNumberKey(event)" id="pin" maxlength="6" minlength="6" placeholder="Enter PIN " name="pin" class="form-control has-help-txt"  data-validation-error-msg="Please enter pin!"/>
                </div>
            </div>  
        </div>



        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Mobile No.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 10"  data-validation="number" onkeypress="return isNumberKey(event)" required="true" id="studentMobileNo" maxlength="10" minlength="10" placeholder="Enter Student Mobile No. " name="studentMobileNo" class="form-control has-help-txt"  data-validation-error-msg="Please enter student mob. no.!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Mobile No.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" data-validation-help="Max length is 10"  data-validation="number" onkeypress="return isNumberKey(event)" required="true" id="parentMobileNo" maxlength="10" minlength="10" placeholder="Enter Parent Mobile No. " name="parentMobileNo" class="form-control has-help-txt"  data-validation-error-msg="Please enter parent mob. no.!"/>
                </div>
            </div>


        </div>
        <div class="row col-lg-12">

            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Email Id.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="email"  data-validation="email" id="studentEmailId" maxlength="60"data-validation-help="Max length is 60" placeholder="Enter Student Email Id " name="studentEmailId" data-error="that email address is invalid" class="form-control has-help-txt"  data-validation-error-msg="Please enter student emailid!"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Email Id.:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="email" data-validation="email" id="parentEmailId" maxlength="60" data-validation-help="Max length is 60"placeholder="Enter Parent Email Id  " name="parentEmailId" data-error="that email address is invalid" class="form-control has-help-txt"  data-validation-error-msg="Please enter parent emailid!"/>
                </div>
            </div>


        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Guest Validity From Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker2'>
                        <input type='text' class="form-control" id="fromdate" name="fromdate" data-validation="required"  data-validation-error-msg="Please select guest validity from date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Guest Validity To Date:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <div class='input-group date' id='datetimepicker3'>
                        <input type='text' class="form-control" id="todate" name="todate" data-validation="required"  data-validation-error-msg="Please select guest validity to date!"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Student Login to be Created:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" id="studentLoginId" name="studentLoginId" value="Y"/>  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Parent Login to be Created:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox" id="parentLoginId" name="parentLoginId" value="Y"/>  
                </div>
            </div>

        </div>

        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:setFormIdAndUrl('ajaxform_tab2','guestStudentMaster/addressDetailSave',2);" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset();" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
            </div> 
        </div>
    </form>
</div>
<script>
    $("#pCountry").chosen({width: "100%"});
    $("#pState").chosen({width: "100%"});
    $("#pcity").chosen({width: "100%"});
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
        $("#datetimepicker2").on("dp.change", function (e) {
            $('#datetimepicker3').data("DateTimePicker").minDate(e.date);
        });
        $("#datetimepicker3").on("dp.change", function (e) {
            $('#datetimepicker2').data("DateTimePicker").maxDate(e.date);
        });
    });
    function saveData() {
        $("#ajaxform").submit();
    }


    function myReset() {
        $("#pCountry").val('').trigger("chosen:updated");
        $("#pState").empty();
        $("#pState").val('').trigger("chosen:updated");
        $("#pcity").empty();
        $("#pcity").val('').trigger("chosen:updated");
        $("#country").val('').trigger("chosen:updated");
        $("#state").empty();
        $("#state").val('').trigger("chosen:updated");
        $("#city").empty();
        $("#city").val('').trigger("chosen:updated");
        $("#oddEven").val('x').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
    }
    function isNumberKey(evt)
    {
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
            var city = $('#pcity').val();
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
                                $('#city').val($('#pcity').val());
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
                            str1 += '<option value="' + item[0] + '">' + item[2] + ' / ' + item[3] + '</option>'
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
                    $("#City").empty();
                    if (data.city != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.city, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[3] + ' / ' + item[4] + '</option>'
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
                            str1 += '<option value="' + item[0] + '">' + item[2] + ' / ' + item[3] + '</option>'
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
                    $("#pcity").empty();
                    if (data.city != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.city, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[3] + ' / ' + item[4] + '</option>'
                        });
                        $("#pcity").append(str1);
                        $("#pcity").trigger("chosen:updated");
                    } else {
                        BootstrapDialog.alert("City Not Found For This State,Please Select Another State...");
                    }
                }
            });
        } else {
            BootstrapDialog.alert("Please select country and state!!");
        }
    }

</script>