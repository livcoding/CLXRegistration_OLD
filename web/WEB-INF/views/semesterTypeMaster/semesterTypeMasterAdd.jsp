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
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Semester Type Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <select class="form-control" id="sty_type_code" name="sty_type_code" data-validation="required" data-live-search="true" data-validation-error-msg="Please select semester type code!">
                        <option value="">Select</option>
                        <option value="REG">REG</option>
                        <option value="RWJ">RWJ</option>
                        <option value="SUP">SUP</option>
                        <option value="SAP">SAP</option>
                        <option value="GIP">GIP</option>
                    </select>
                </div>
            </div>
        </div> 
            <div class="row col-lg-12 form-group" >
                <div class="row col-lg-6 form-group">
                    <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Semester Type Desc:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                        <input type="text" name="sty_Type_Desc" id="sty_Type_Desc" maxlength="50" data-validation-help="Max Length is 50" data-validation="required" placeholder="Enter Semester Type Description" class="form-control" data-validation-error-msg="Please enter semester type description!">
                    </div>
                </div>
            </div> 
        <div class=" col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" >Grade Improvement(i.e.E2D):</label>
                <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6  checkbox-inline">
                    <input type="checkbox" id="ETOD" name="ETOD" value="Y"/>
                </div>
            </div>

        </div>
        <div class="col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad"><span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
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
    $("#sty_type_code").chosen();

    function myReset() {
        $("#sty_type_code").val('').trigger("chosen:updated");
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
                                    url: "semesterTypeMaster/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        // $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success('Record Successfully Saved.', "Success!!");
                                            loadForm("semesterTypeMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        // setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        // $.unblockUI();
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


