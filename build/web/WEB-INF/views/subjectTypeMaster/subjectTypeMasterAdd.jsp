<%-- 
    Document   : subjectTypeMasterAdd
    Created on : Dec 12, 2018, 10:21:38 AM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                        <option value="">Select</option>
                        <option value="C">Core</option>
                        <option value="P">Optional Core</option>
                        <option value="S">Supplementary/Zero Credit Core</option>
                        <option value="E">Departmental Electives</option>
                        <option value="I">Institute Electives</option>
                        <option value="O">Open Electives</option>
                    </select>
                </div>
            </div>
        </div> 
    </div>
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Type Desc:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="subject_Type_Desc" id="subject_Type_Desc" maxlength="50" data-validation-help="Max Length is 50" data-validation="required" placeholder="Enter Subject Type Description" class="form-control" data-validation-error-msg="Please enter subject type description!">                                              
                </div>
            </div>
        </div> 
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label" id="Ad"><span style="color: green"> Active</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6 checkbox-inline">
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
</form>
<script>
    $("#subject_Type").chosen();

    function myReset() {
        $("#subject_Type").val('').trigger("chosen:updated");
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
                                //$.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "subjectTypeMaster/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                     //   $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            loadForm("subjectTypeMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                      //  setTimeout($.unblockUI, 1000);
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
        $("#ajaxform").submit();
    }
</script>


