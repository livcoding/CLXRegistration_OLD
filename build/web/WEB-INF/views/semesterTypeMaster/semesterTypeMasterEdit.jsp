<%-- 
    Document   : subjectTypeMasterEdit
    Created on : Dec 12, 2018, 10:22:03 AM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 form-group">
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Semester Type Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <c:if test="${data[0][2] == 'REG'}">
                    <select class="form-control" id="sty_type_code" name="sty_type_code" data-validation="required" data-live-search="true" data-validation-error-msg="Please select semester type code!">
                        <option value="REG" selected="true">REG</option>
                        <option value="RWJ">RWJ</option>
                        <option value="SUP">SUP</option>
                        <option value="SAP">SAP</option>
                        <option value="GIP">GIP</option>
                    </select>
                </c:if>
                <c:if test="${data[0][2] == 'RWJ'}">
                    <select class="form-control" id="sty_type_code" name="sty_type_code" data-validation="required" data-live-search="true" data-validation-error-msg="Please select semester type code!">
                        <option value="RWJ" selected="true">RWJ</option>
                        <option value="SUP">SUP</option>
                        <option value="SAP">SAP</option>
                        <option value="REG">REG</option>
                        <option value="GIP">GIP</option>
                    </select>
                </c:if>
                <c:if test="${data[0][2] == 'SUP'}">
                    <select class="form-control" id="sty_type_code" name="sty_type_code" data-validation="required" data-live-search="true" data-validation-error-msg="Please select semester type code!">
                        <option value="SUP" selected="true">SUP</option>
                        <option value="SAP">SAP</option>
                        <option value="REG">REG</option>
                        <option value="RWJ">RWJ</option>
                        <option value="GIP">GIP</option>
                    </select>
                </c:if>
                <c:if test="${data[0][2] == 'SAP'}">
                    <select class="form-control" id="sty_type_code" name="sty_type_code" data-validation="required" data-live-search="true" data-validation-error-msg="Please select semester type code!">
                        <option value="SAP" selected="true">SAP</option>
                        <option value="REG">REG</option>
                        <option value="RWJ">RWJ</option>
                        <option value="SUP">SUP</option>
                        <option value="GIP">GIP</option>
                    </select>
                </c:if>
                <c:if test="${data[0][2] == 'GIP'}">
                    <select class="form-control" id="sty_type_code" name="sty_type_code" data-validation="required" data-live-search="true" data-validation-error-msg="Please select semester type code!"> 
                        <option value="GIP" selected="true">GIP</option>
                        <option value="REG">REG</option>
                        <option value="RWJ">RWJ</option>
                        <option value="SUP">SUP</option>
                        <option value="SAP">SAP</option>
                    </select>
                </c:if>
            </div>
        </div>
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Semester Type Desc:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <input type="text" name="subject_Type_Desc" id="subject_Type_Desc" maxlength="50" data-validation-help="Max Length is 50" value="${data[0][3]}" data-validation="required" placeholder="Enter Semester Type Description" class="form-control" data-validation-error-msg="Please enter semester type description!">                                              
            </div>
        </div>
        <div class="col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Seq ID:</label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="Seq_Id" id="Seq_Id" maxlength="4" data-validation-help="Max Length is 4" data-validation-optional="true" data-validation="number" value="${data[0][4]}" data-validation="required" placeholder="Enter Seq ID" class="form-control">                                              
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label  class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label" style="margin-left: 10px">Grade Improvement(i.e.E2D):</label>
                <c:if test="${data[0][5] == 'N'}">
                    <div class="col-sm-1 checkbox-inline">
                        <input type="checkbox" id="ETOD" name="ETOD" value="" onclick="callchkbox();" style="margin-left: 8px"/>
                    </div>
                </c:if>
                <c:if test="${data[0][5] == 'Y'}">
                    <div class="col-sm-1">
                        <input type="checkbox" id="ETOD" name="ETOD"  checked="true" value="" onclick="callchkbox();" style="margin-left: 8px"/>
                    </div>
                </c:if>  
            </div>
        </div>
        <div class="col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data[0][6]=='N'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N" style="margin-left: 8px"/>   
                    </div>
                </c:if>
                <c:if test="${data[0][6]=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>

        <input type="text" class="hidden" name="institute_id" id="institute_id" value="${data[0][0]}"/>
        <input type="text" class="hidden" name="sem_type_id" id="sem_type_id" value="${data[0][1]}"/>
        <input type="text" class="hidden" name="sem_type" id="sem_type" value="${data[0][2]}"/>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form> 

<script>

    $("#sty_type_code").chosen();

    var val = getValue();
    if (val == 'Y') {
        var desc = document.getElementById("subject_Type_Desc").value;
        document.getElementById("subject_Type_Desc").addEventListener("keydown", function () {
            this.value = desc
        });
        document.getElementById("subject_Type_Desc").setAttribute("readonly", true);
        $("#sty_type_code").empty().trigger("chosen:updated");
        var v = $("#sem_type").val();
        var REG = '<option value="REG" selected="true">REG</option>';
        var GIP = '<option value="GIP" selected="true">GIP</option>';
        var SUP = '<option value="SUP" selected="true">SUP</option>';
        var RWJ = '<option value="RWJ" selected="true">RWJ</option>';
        var SAP = '<option value="SAP" selected="true">SAP</option>';
        if (v == 'REG') {
            $("#sty_type_code").append(REG).trigger("chosen:updated");
        } else if (v == 'GIP') {
            $("#sty_type_code").append(GIP).trigger("chosen:updated");
        } else if (v == 'SUP') {
            $("#sty_type_code").append(SUP).trigger("chosen:updated");
        } else if (v == 'RWJ') {
            $("#sty_type_code").append(RWJ).trigger("chosen:updated");
        } else if (v == 'SAP') {
            $("#sty_type_code").append(SAP).trigger("chosen:updated");
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

    function callchkbox() {
        if ($("#ETOD").prop('checked') == true) {
            $("#ETOD").val("Y");
        } else {
            $("#ETOD").val("N");
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
                                //  $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "semesterTypeMaster/update",
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
                                            toastr.success('Record Successfully Updated.', "Success!!");
                                            loadForm("semesterTypeMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        //setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //$.unblockUI();
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


