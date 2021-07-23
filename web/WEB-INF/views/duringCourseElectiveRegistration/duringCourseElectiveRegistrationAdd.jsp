<%-- 
    Document   : DuringCourseElectiveRegistartionAdd
    Created on : 15 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Program Code<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="programId" name="programId" data-validation="required" style="width:85%;" data-validation-error-msg="Please select program code!">
                    <option value="">Select</option>
                    <c:forEach items="${prog_list}" var="prog_list">
                        <option value="${prog_list[2]}"><c:out value="${prog_list[0]} / ${prog_list[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" ><h3 style="background-color:#008080;color:white"><center>During Course Registration</center></h3></div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Credit To Counted in Course Registration for Subjects Type :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="creditTo" name="creditTo" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list">
                        <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Elective Type Subject Shown in Course Registration for Subjects Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="elective" name="elective" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list">
                        <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>    

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Elective Subject To be Disabled While Course registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="elecSub" name="elecSub" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list">
                        <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>    

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Show Back Paper In Course Registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="showBack" name="showBack" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Editable Back Paper In Course Registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="editBack" name="editBack" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Allow Not Offered Subjects during Course Registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="allowNot" name="allowNot" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Default Selected option for Back papers :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="defaultSel" name="defaultSel" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Back Paper Selection Mandatory:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="backPaperSel" name="backPaperSel" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Printing of Course Registration is allowed?:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="printCourse" name="printCourse" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Student Fee Dues Checking During Course Registration:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="studentFee" name="studentFee" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Back paper Fee Calculation during Course Registration:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="backPaperFee" name="backPaperFee" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" ><h3 style="background-color:#008080;color:white"><center>During Elective Registration</center></h3></div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Credit To Counted in Elective Registration for Subjects Type :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="creditToElec" name="creditToElec" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list">
                        <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div> 

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Elective Type Subject Shown in Elective Choice for Subjects Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="elecType" name="elecType" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list">
                        <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div> 
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Type of Electives subjects to be kept disable in Elective Registration Form:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="typeElec" name="typeElec" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">During Elective Subject Choices Preference can be modified:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="duringElec" name="duringElec" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Printing of Elective Preferences is allowed:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="printElec" name="printElec" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <option value="">Select</option>
                    <option value="Y">Yes</option>
                    <option value="N">No</option>
                </select>
            </div>
        </div>
    </div>
    <div class="col-lg-12 form-group">              
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
        </div>
    </div>
</div>
</form>

<script>


    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();

    });
    $("#programId").chosen();
    $("#creditTo").chosen();
    $("#elective").chosen();
    $("#elecSub").chosen();
    $("#showBack").chosen();
    $("#editBack").chosen();
    $("#allowNot").chosen();
    $("#defaultSel").chosen();
    $("#backPaperSel").chosen();
    $("#printCourse").chosen();
    $("#studentFee").chosen();
    $("#backPaperFee").chosen();
    $("#elecType").chosen();
    $("#creditToElec").chosen();
    $("#typeElec").chosen();
    $("#duringElec").chosen();
    $("#printElec").chosen();

    function myReset() {
        $("#programId").val('').trigger("chosen:updated");
        $("#creditTo").val('').trigger("chosen:updated");
        $("#elective").val('').trigger("chosen:updated");
        $("#elecSub").val('').trigger("chosen:updated");
        $("#showBack").val('').trigger("chosen:updated");
        $("#editBack").val('').trigger("chosen:updated");
        $("#allowNot").val('').trigger("chosen:updated");
        $("#defaultSel").val('').trigger("chosen:updated");
        $("#backPaperSel").val('').trigger("chosen:updated");
        $("#printCourse").val('').trigger("chosen:updated");
        $("#studentFee").val('').trigger("chosen:updated");
        $("#backPaperFee").val('').trigger("chosen:updated");
        $("#elecType").val('').trigger("chosen:updated");
        $("#creditToElec").val('').trigger("chosen:updated");
        $("#typeElec").val('').trigger("chosen:updated");
        $("#duringElec").val('').trigger("chosen:updated");
        $("#printElec").val('').trigger("chosen:updated");
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
                                    url: "duringCourseElectiveRegistration/save",
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
                                            toastr.success('Record Successfully Saved.', "Success!!");
                                            loadForm("duringCourseElectiveRegistration/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else if (data === 'Duplicate') {
                                            BootstrapDialog.alert("Record is already exists for this Program Code");
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