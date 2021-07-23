<%-- 
    Document   : ExamPatterMasterEdit
    Created on : 8 AUG : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label"><span style="color:black;">Pattern Code:</span><span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="patternCode" id="patternCode" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" value="${data.patterncode}" data-validation-help="Max Length is 20" data-validation="required" placeholder="Enter Pattern Code" class="form-control"  data-validation-error-msg="Please enter pattern code!">
                </div>
            </div>
        </div> 
        <div class=" col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label" ><span style="color:black;">Pattern Name:</span><span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="patternName" id="patternName" maxlength="40" value="${data.patternname}" data-validation-help="Max Length is 40" data-validation="required" placeholder="Enter Pattern Name" class="form-control"  data-validation-error-msg="Please enter pattern name!">
                </div>
            </div>
        </div>
        <div class=" col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label" ><span style="color:black;">Min Attendance:</span><span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="minAttendance" id="minAttendance" maxlength="4" value="${data.minattendance}" onkeypress="return isNumberKey(event)"  data-validation-help="Max Length is 4" data-validation="required" data-validation-allowing="float" placeholder="Enter Min Attendance" class="form-control"  data-validation-error-msg="Please enter min attendance!">
                </div>
            </div>
        </div>
        <div class="col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-6 col-lg-5 col-xs-12 col-sm-6 control-label" id="Ad">
                    <c:if test="${data.deactive!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data.deactive=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-7 col-xs-12 col-md-6 checkbox-inline">   
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:updateData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div> 
    <input type="hidden" name="PatternId" id="PatternId"  value="${data.id.patternid}" >
</form>

<script>
    var val = getValue();
    if (val == 'Y') {
        document.getElementById("patternCode").setAttribute("readonly", true);
        document.getElementById("patternName").setAttribute("readonly", true);
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
    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "examPatternMaster/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Update.', "Success!!");
                                            loadForm("examPatternMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        $.unblockUI();
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


