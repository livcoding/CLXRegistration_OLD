<%-- 
    Document   : oldvsNewSubjectAdd
    Created on : Mar 6, 2019, 12:15:43 PM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Old Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="subjectCode" name="subjectCode" data-validation="required" data-live-search="true" data-validation-error-msg="You have not answered Old Subject Code">
                        <option value="">Select</option>
                        <c:forEach items="${subjectCode}" var="subList">
                            <option value="${subList[0]}"><c:out value="${subList[1]} --- ${subList[2]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">New Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="newSubjectCode" name="newSubjectCode" data-validation="required" data-live-search="true" data-validation-error-msg="You have not answered New Subject Code">
                        <option value="">Select</option>
                        <c:forEach items="${subjectCode}" var="subList">
                            <option value="${subList[0]}"><c:out value="${subList[1]} --- ${subList[2]}"/></option>
                        </c:forEach> 
                    </select>
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="semesterCode" name="semesterCode" data-validation="required" data-live-search="true" data-validation-error-msg="You have not answered Semester Code">
                        <option value="">Select</option>
                        <c:forEach items="${semesterCode}" var="semCode">
                            <option value="${semCode[0]}"><c:out value="${semCode[1]} --- ${semCode[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">WEF:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <div class='input-group date' id='datetimepicker1'>
                        <input type='text' class="form-control" id="wef" name="wef" data-validation="required" value="${data[0][3]}"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Remarks:</label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <textarea rows="2" cols="25" id="remarks" name="remarks" data-validation-help="Max Length is 250" maxlength="250" data-validation-optional="true" class="form-control" data-validation-error-msg="Please Enter Remarks" placeholder="Enter Remarks"></textarea>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label" id="Ad"><span style="color: green"> Active</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
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
    $("#subjectCode").chosen();
    $("#newSubjectCode").chosen();
    $("#semesterCode").chosen();

    $('#datetimepicker1').datetimepicker({
        format: 'DD/MM/YYYY'
    });

    function myReset() {
        $("#subjectCode").val('').trigger("chosen:updated");
        $("#newSubjectCode").val('').trigger("chosen:updated");
        $("#semesterCode").val('').trigger("chosen:updated");
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "oldVsNewSubject/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Successfully Saved', "Success!!");
                                            loadForm("oldVsNewSubject/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        setTimeout($.unblockUI, 1000);
                                        toastr.warning('Something Wrong.', "Warning!!");
                                    }
                                });
                                return false; // Will stop the submission of the form
                            }
                        }
                );
            }, 100);

    function saveData() {
        var oldSubjectCode = $("#subjectCode").val();
        var newSubjectCode = $("#newSubjectCode").val();
        if (oldSubjectCode != '') {
            if (newSubjectCode != '') {
                if (oldSubjectCode != newSubjectCode) {
                    $("#ajaxform").submit();
                } else {
                    BootstrapDialog.alert("Old Subject Code And New Subject Code Can't be Same...!");
                }
            } else {
                BootstrapDialog.alert("New Subject Code Can't be blank..!");
            }
        } else {
            BootstrapDialog.alert("Old Subject Code Can't be blank..!");
        }
    }
</script>

