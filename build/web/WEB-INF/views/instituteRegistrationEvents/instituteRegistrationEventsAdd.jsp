<%-- 
    Document   : instituteRegistrationEventsAdd
    Created on : Jan 15, 2019, 12:06:20 PM
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
                    <select class="form-control" id="semesterCode" name="semesterCode" data-validation="required" onchange="setData(this.value);"  data-live-search="true">
                        <option value="">Select</option>
                        <c:forEach items="${data}" var="list">
                            <option value="${list.id.registrationid}~@~${list.registrationdesc}~@~${list.preventfromdate}~@~${list.preventenddate}~@~${list.courseregistrationdatefrom}~@~${list.courseregistrationdateto}~@~${list.attendancefromdate}~@~${list.attendancetodate}">
                                <c:out value="${list.registrationcode}"/></option>
                            </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Desc:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="registrationDesc" name="registrationDesc" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PR Event Reg From:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="fromdate" name="fromdate" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PR Event Reg To:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="todate" name="todate" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg From:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="courseRegFrom" name="courseRegFrom" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg To:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="courseRegTo" name="courseRegTo" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Entry From:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="attendanceentryFrom" name="attendanceentryFrom" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Entry To:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="attendanceentryTo" name="attendanceentryTo" value="" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Pre Registration Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="preregistrationAllowed" name="preregistrationAllowed" value="Y"/>  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attend Entry Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="attendentryAllowed" name="attendentryAllowed" value="Y"/>  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Marks Entry Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="marksentryAllowed" name="marksentryAllowed" value="Y"/>  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Grade Entry Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="gradeentryAllowed" name="gradeentryAllowed" value="Y"/>  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Srs Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="srsAllowed" name="srsAllowed" value="Y"/>  
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplementary  request Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="supplymentryrequestAllowed" name="supplymentryrequestAllowed" value="Y"/>  
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Hostel Allocation Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="checkbox" id="hostelallocationallowed" name="hostelallocationallowed" value="Y"/>  
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>


<script>
    $("#semesterCode").chosen();

    function myReset() {
        $("#semesterCode").val('').trigger("chosen:updated");
    }

    function setData(values) {
        $("#registrationDesc").val('');
        $("#fromdate").val('');
        $("#todate").val('');
        $("#courseRegFrom").val('');
        $("#courseRegTo").val('');
        $("#attendanceentryFrom").val('');
        $("#attendanceentryTo").val('');
        var datas = values.split("~@~");
        $("#registrationDesc").val(datas[1]);
        $("#fromdate").val(datas[2]);
        $("#todate").val(datas[3]);
        $("#courseRegFrom").val(datas[4]);
        $("#courseRegTo").val(datas[5]);
        $("#attendanceentryFrom").val(datas[6]);
        $("#attendanceentryTo").val(datas[7]);
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
                                $.ajax({
                                    type: "POST",
                                    url: "instituteRegistrationEvents/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Saved', "Success!!");
                                            loadForm("instituteRegistrationEvents/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                    },
                                    error: function (response) {
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