<%-- 
    Document   : instituteRegistrationEventsEdit
    Created on : Jan 15, 2019, 12:06:38 PM
    Author     : ashutosh1.kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">      
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Code:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="semesterCode" name="semesterCode" value="${data[0][2]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Desc:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="registrationDesc" name="registrationDesc" value="${data[0][3]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PR Event Reg From:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="fromdate" name="fromdate" value="${data[0][4]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">PR Event Reg To:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="todate" name="todate" value="${data[0][5]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg From:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="courseRegFrom" name="courseRegFrom" value="${data[0][6]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Course Reg To:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="courseRegTo" name="courseRegTo" value="${data[0][7]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Entry From:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="attendanceentryFrom" name="attendanceentryFrom" value="${data[0][8]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attendance Entry To:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="attendanceentryTo" name="attendanceentryTo" value="${data[0][9]}" class="form-control has-help-txt" readonly="true"/>
                </div>
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Pre Registration Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][10]=='Y'}">
                        <input type="checkbox" id="preregistrationAllowed" name="preregistrationAllowed" checked="true" value="Y"/>   
                    </c:if>
                    <c:if test="${data[0][10]=='N'}">
                        <input type="checkbox" id="preregistrationAllowed" name="preregistrationAllowed" value="Y"/>  
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Attend Entry Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][11]=='Y'}">
                        <input type="checkbox" id="attendentryAllowed" name="attendentryAllowed" checked="true" value="Y"/>   
                    </c:if>
                    <c:if test="${data[0][11]=='N'}">
                        <input type="checkbox" id="attendentryAllowed" name="attendentryAllowed" value="Y"/>  
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Marks Entry Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][12]=='Y'}">
                        <input type="checkbox" id="marksentryAllowed" name="marksentryAllowed" checked="true" value="Y"/>    
                    </c:if>
                    <c:if test="${data[0][12]=='N'}">
                        <input type="checkbox" id="marksentryAllowed" name="marksentryAllowed" value="Y"/> 
                    </c:if> 
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Grade Entry Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][13]=='Y'}">
                        <input type="checkbox" id="gradeentryAllowed" name="gradeentryAllowed" checked="true" value="Y"/>     
                    </c:if>
                    <c:if test="${data[0][13]=='N'}">
                        <input type="checkbox" id="gradeentryAllowed" name="gradeentryAllowed" value="Y"/>  
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Srs Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][14]=='Y'}">
                        <input type="checkbox" id="srsAllowed" name="srsAllowed" checked="true" value="Y"/>     
                    </c:if>
                    <c:if test="${data[0][14]=='N'}">
                        <input type="checkbox" id="srsAllowed" name="srsAllowed" value="Y"/>  
                    </c:if>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Supplementary request Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][15]=='Y'}">
                        <input type="checkbox" id="supplymentryrequestAllowed" name="supplymentryrequestAllowed" checked="true" value="Y"/>   
                    </c:if>
                    <c:if test="${data[0][15]=='N'}">
                        <input type="checkbox" id="supplymentryrequestAllowed" name="supplymentryrequestAllowed" value="Y"/>  
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Hostel Allocation Allowed:</label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <c:if test="${data[0][16]=='Y'}">
                        <input type="checkbox" id="hostelallocationallowed" name="hostelallocationallowed" checked="true" value="Y"/>     
                    </c:if>
                    <c:if test="${data[0][16]=='N'}">
                        <input type="checkbox" id="hostelallocationallowed" name="hostelallocationallowed" value="Y"/>  
                    </c:if>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:updateData();" class="btn btn-success btn-sm btn-flat">Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
        <input type="hidden" name="registrationid" value="${data[0][1]}"/>
    </div>
</form>


<script>

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
                                    url: "instituteRegistrationEvents/update",
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
                                            toastr.success('Record Successfully Updated', "Success!!");
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

    function updateData() {
        $("#ajaxform").submit();
    }
</script>



