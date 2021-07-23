<%-- 
    Document   : departmentSubjectTaggingAdd
    Created on : Dec 5, 2018, 2:23:16 PM
    Author     : ankit.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">                 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Department Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-8 col-lg-8 col-xs-8 col-md-8">
                    <select class="form-control" id="dept_code" name="dept_code"   data-live-search="true" data-validation="required" data-validation-error-msg="Please select department code!">               
                        <option value="">Select</option>
                        <c:forEach items="${depart_code}" var="depart_code">
                            <option value="${depart_code[0]}"><c:out value="${depart_code[1]} / ${depart_code[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Subject Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-8 col-lg-8 col-xs-8 col-md-8">
                    <select class="form-control" id="sub_code" name="sub_code" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select subject code!">
                        <option value="">Select</option>
                        <c:forEach items="${subject_code}" var="subject_code">
                            <option value="${subject_code[0]}"><c:out value="${subject_code[1]} / ${subject_code[2]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button> 
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
                &nbsp; &nbsp; &nbsp; <button type="button" class="btn  btn-info fa fa-question-circle" data-toggle="tooltip" onclick="hint();" style="padding:2px;font-size:25px;float:right;color:yellow" title="Hint.."/>
            </div>
           
        </div>
    </div>
</form>
<script>

    function myReset() {
        $("#dept_code").val('').trigger("chosen:updated");
        $("#sub_code").val('').trigger("chosen:updated");
    }

    $("#dept_code").chosen();
    $("#sub_code").chosen();

    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
    function hint() {
        BootstrapDialog.show({
            title: 'Department_Subejct_Tagging',
            message: (Hint['department_subejct_tagging']),
            buttons: [{
                    label: 'Ok',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
        });
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
                                    url: "DepartmentSubjectTagging/save",
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
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("DepartmentSubjectTagging/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
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