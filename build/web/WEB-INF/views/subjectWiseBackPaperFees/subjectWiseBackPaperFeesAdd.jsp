<%-- 
    Document   : SubjectWiseBackPaperFeesAdd
    Created on : 13 JUL : 2019 
    Author     : Malkeet Singh
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">                 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="semesterCode" name="semesterCode" data-live-search="true" data-validation="required" data-validation-error-msg="Please select semester code!">
                        <option value="">Select</option>
                        <c:forEach items="${semCode}" var="list">
                            <option value="${list[0]}"><c:out value="${list[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Department:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="department" onchange="getSubjectCode();" name="department"data-live-search="true"  data-validation="required" data-validation-error-msg="Please select department!">
                        <option value="">Select</option>
                        <c:forEach items="${dapartmentData}" var="list">
                            <option value="${list[0]}"><c:out value="${list[1]}"/></option>
                        </c:forEach>
                    </select>   
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="subjectCode" name="subjectCode"data-live-search="true"  data-validation="required" data-validation-error-msg="Please select subject code!">
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Fee Amount:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" name="backPaperFee" id="backPaperFee" maxlength="5" onkeypress="return isNumberKey(event)" data-live-search="true" data-validation-help="Max Length is 5" data-validation="number" placeholder="Enter Fee Amount" class="form-control" data-validation-error-msg="Please enter fee amount!">
                </div>
            </div>
        </div> 

        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save Or Update</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>


    $("#semesterCode").chosen({width: "100%"});
    $("#department").chosen({width: "100%"});
    $("#subjectCode").chosen({width: "100%"});

    function myReset() {
        $("#semesterCode").val('').trigger("chosen:updated");
        $("#department").val('').trigger("chosen:updated");
        $("#subjectCode").empty();
        $("#subjectCode").val('').trigger("chosen:updated");
    }

    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }
    function getSubjectCode() {
        $("#subjectCode").empty();
        $.blockUI();
        $.ajax({
            type: "POST",
            url: "subjectWiseBackPaperFees/getSubjectCode",
            data: '&departmentid=' + $("#department").val(),
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.subjectCode, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '/' + item[2] + '</option>'
                });
                $("#subjectCode").append(str1);
                $("#subjectCode").trigger("chosen:updated");
                setTimeout($.unblockUI, 1000);
            },
            error: function (response) {
                BootstrapDialog.alert("SomeThing Went Wrong...");
                setTimeout($.unblockUI, 1000);
            }
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "subjectWiseBackPaperFees/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'save') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("subjectWiseBackPaperFees/list");
                                        } else if (data === 'update') {
                                            toastr.success(Success['update_success'], "Success!!");
                                            loadForm("subjectWiseBackPaperFees/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                        setTimeout($.unblockUI, 1000);
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