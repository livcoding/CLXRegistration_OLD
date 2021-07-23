<%-- 
Document   : preRequisiteForRegistrationEdit
Created on : Feb 5, 2019, 4:53:00 PM
Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Academic Year:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="acadYear" name="acadYear" readonly="true" value="${data[0][1]}" placeholder="Academic Year" class="form-control"  data-validation-error-msg="Please select academic year!">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="programCode" name="programCode" readonly="true" value="${data[0][2]}" placeholder="Program Code" class="form-control"  data-validation-error-msg="Please select program code!">
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Branch Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="branchCode" name="branchCode" readonly="true" value="${data[0][3]}" placeholder="Branch Code" class="form-control" data-validation-error-msg="Please select branch code!">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">STY Number:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="styNumber" name="styNumber" readonly="true" value="${data[0][4]}"onkeypress="return isNumberKey(event)" placeholder="STY Number" class="form-control"  data-validation-error-msg="Please select sty number!">
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Max Fail Subjects:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="maxFailSubject" name="maxFailSubject" maxlength="2" onkeypress="return isNumberKey(event)"data-validation="number" data-validation-optional="true" data-validation-help="Max Length is 2" placeholder="Enter Max Fail Subjects" class="form-control" value="${data[0][5]}">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Min Earned Points:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="minEarnedPoints" name="minEarnedPoints" value="${data[0][6]}"onkeypress="return isNumberKey(event)" maxlength="2" data-validation-help="Max Length is 2" data-validation="number" data-validation-optional="true" placeholder="Enter Min Earned Points" class="form-control" data-validation-error-msg="">
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Max No of Attempts:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="maxNoAttempts" name="maxNoAttempts" value="${data[0][7]}" onkeypress="return isNumberKey(event)"maxlength="2" data-validation-help="Max Length is 2" data-validation="number" data-validation-optional="true" placeholder="Enter Max No of Attempts" class="form-control" data-validation-error-msg="">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Min Credits:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="minCredit" name="minCredit" value="${data[0][8]}"onkeypress="return isNumberKey(event)" maxlength="2" data-validation-help="Max Length is 2" data-validation="number" data-validation-optional="true" placeholder="Enter Min Credits" class="form-control" data-validation-error-msg="">
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Lateral Entry Credits:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="lateralEntryCredit" name="lateralEntryCredit" onkeypress="return isNumberKey(event)"value="${data[0][9]}" maxlength="2" data-validation-help="Max Length is 2" data-validation="number" data-validation-optional="true" placeholder="Enter Lateral Entry Credits" class="form-control">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Min SGPA:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="minSGPA" name="minSGPA" value="${data[0][11]}"onkeypress="return isNumberKey(event)" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-optional="true" placeholder="Enter Min SGPA" class="form-control" data-validation-error-msg="">
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Min CGPA:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text"  id="minCGPA" name="minCGPA" value="${data[0][10]}" onkeypress="return isNumberKey(event)"maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-optional="true" placeholder="Enter Min CGPA" class="form-control">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Min % Attendance:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="minPercentAttendance" name="minPercentAttendance" onkeypress="return isNumberKey(event)"value="${data[0][12]}" maxlength="5" data-validation-help="Max Length is 5" data-validation="number" data-validation-optional="true" placeholder="Enter Percent Attendance" class="form-control" data-validation-error-msg="">
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Max Disciplinary:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text"  id="maxDiscplinary" name="maxDiscplinary"onkeypress="return isNumberKey(event)" value="${data[0][13]}" maxlength="2" data-validation-help="Max Length is 2" data-validation="number" data-validation-optional="true" placeholder="Enter Max Discplinary" class="form-control">
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data[0][16]!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data[0][16]=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <input type="text" class="hidden" name="proogramId" id="proogramId" value="${data[0][14]}"/>
    <input type="text" class="hidden" name="branchId" id="branchId" value="${data[0][15]}"/>
    <div class="col-lg-12 form-group">              
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
        </div>
    </div>
</form>

<script>

    $("#minSGPA").focusout(function () {

        if (parseFloat($("#minSGPA").val()) > 500)
        {
            BootstrapDialog.alert("Invalid Value Min SGPA. Enter Value less then 500");
            document.getElementById("minSGPA").value = '';
        }
    });

    $("#minCGPA").focusout(function () {

        if (parseFloat($("#minCGPA").val()) > 500)
        {
            BootstrapDialog.alert("Invalid Value Min CGPA. Enter Value less then 500 ");
            document.getElementById("minCGPA").value = '';
        }
    });
// $("#minPercentAttendance").focusout(function(){
//
//    if(parseFloat($("#minPercentAttendance").val()) >100)
//    {
//         alert("Invalid Value Min % Attendance:");
//         document.getElementById("minPercentAttendance").value='';
//    }
//});


  function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }
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
                                    url: "preRequisiteForRegistration/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        //  $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success(Success['update_success'], "Success!!");
                                            loadForm("preRequisiteForRegistration/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        // setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //  $.unblockUI();
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

    function saveData() {
        var minper = $("#minPercentAttendance").val();
        if (minper >= 100) {
            BootstrapDialog.alert("Min % Attendance should not be greater than or equal to 100%");
        } else {
            $("#ajaxform").submit();
        }
    }

</script>