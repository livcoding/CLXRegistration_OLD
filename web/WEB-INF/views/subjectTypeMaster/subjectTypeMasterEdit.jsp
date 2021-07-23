<%-- 
    Document   : subjectTypeMasterEdit
    Created on : Dec 12, 2018, 10:22:03 AM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <c:if test="${data.subjecttype == 'C'}">
                        <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="C" selected="true">Core</option>
                            <option value="P">Optional Core</option>
                            <option value="S">Supplementary/Zero Credit Core</option>
                            <option value="E">Departmental Electives</option>
                            <option value="I">Institute Electives</option>
                            <option value="O">Open Electives</option>
                        </select>
                    </c:if>
                    <c:if test="${data.subjecttype == 'P'}">
                        <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="P" selected="true">Optional Core</option>
                            <option value="S">Supplementary/Zero Credit Core</option>
                            <option value="E">Departmental Electives</option>
                            <option value="I">Institute Electives</option>
                            <option value="O">Open Electives</option>
                            <option value="C">Core</option>

                        </select>
                    </c:if>
                    <c:if test="${data.subjecttype == 'S'}">
                        <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="S" selected="true">Supplementary/Zero Credit Core</option>
                            <option value="E">Departmental Electives</option>
                            <option value="I">Institute Electives</option>
                            <option value="O">Open Electives</option>
                            <option value="C">Core</option>
                            <option value="P">Optional Core</option>

                        </select>
                    </c:if>
                    <c:if test="${data.subjecttype == 'E'}">
                        <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="E" selected="true">Departmental Electives</option>
                            <option value="I">Institute Electives</option>
                            <option value="O">Open Electives</option>
                            <option value="C">Core</option>
                            <option value="P">Optional Core</option>
                            <option value="S">Supplementary/Zero Credit Core</option>
                        </select>
                    </c:if>
                    <c:if test="${data.subjecttype == 'I'}">
                        <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="I" selected="true">Institute Electives</option>
                            <option value="O">Open Electives</option>
                            <option value="C">Core</option>
                            <option value="P">Optional Core</option>
                            <option value="S">Supplementary/Zero Credit Core</option>
                            <option value="E">Departmental Electives</option>
                        </select>
                    </c:if>
                    <c:if test="${data.subjecttype == 'O'}">
                        <select class="form-control" id="subject_Type" name="subject_Type" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject type!">
                            <option value="O" selected="true">Open Electives</option>
                            <option value="C">Core</option>
                            <option value="P">Optional Core</option>
                            <option value="S">Supplementary/Zero Credit Core</option>
                            <option value="E">Departmental Electives</option>
                            <option value="I">Institute Electives</option>
                        </select>
                    </c:if>
                </div>
            </div>
        </div> 
        <div class="col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Type Desc:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="subject_Type_Desc" id="subject_Type_Desc" maxlength="50" data-validation-help="Max Length is 50" value="${data.subjecttypedesc}" data-validation="required" placeholder="Enter Subject Type Description" class="form-control" data-validation-error-msg="Please enter subject type description!">                                              
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Seq ID:</label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" name="Seq_Id" id="Seq_Id" maxlength="4" data-validation-help="Max Length is 4" data-validation-optional="true" data-validation="number" value="${data.seqid}" data-validation="required" placeholder="Enter Seq ID" class="form-control">                                              
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data.deactive!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data.deactive=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-7 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <input type="text" class="hidden" name="institute_id" id="institute_id" value="${data.id.instituteid}"/>
    <input type="text" class="hidden" name="subject_type_id" id="subject_type_id" value="${data.id.subjecttypeid}"/>
    <input type="text" class="hidden" name="sub_Type" id="sub_Type" value="${data.subjecttype}"/>
    <div class="col-lg-12 form-group">              
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
        </div>
    </div>    

</form> 

<script>
    $("#subject_Type").chosen();

    var val = getValue();
    if (val == 'Y') {
        var desc = document.getElementById("subject_Type_Desc").value;
        document.getElementById("subject_Type_Desc").addEventListener("keydown", function () {
            this.value = desc
        });
        document.getElementById("subject_Type_Desc").setAttribute("readonly", true);
        $("#subject_Type").empty().trigger("chosen:updated");
        var v = $("#sub_Type").val();
        var C = '<option value="C" selected="true">Core</option>';
        var P = '<option value="P" selected="true">Optional Core</option>';
        var S = '<option value="S" selected="true">Supplementary/Zero Credit Core</option>';
        var E = '<option value="E" selected="true">Departmental Electives</option>';
        var I = '<option value="I" selected="true">Institute Electives</option>';
        var O = '<option value="O" selected="true">Open Electives</option>';
        if (v == 'C') {
            $("#subject_Type").append(C).trigger("chosen:updated");
        } else if (v == 'P') {
            $("#subject_Type").append(P).trigger("chosen:updated");
        } else if (v == 'S') {
            $("#subject_Type").append(S).trigger("chosen:updated");
        } else if (v == 'E') {
            $("#subject_Type").append(E).trigger("chosen:updated");
        } else if (v == 'I') {
            $("#subject_Type").append(I).trigger("chosen:updated");
        } else if (v == 'O') {
            $("#subject_Type").append(O).trigger("chosen:updated");
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
                                //  $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "subjectTypeMaster/update",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: false,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        //         $.blockUI();
                                        if (data === 'Success') {
                                            toastr.success(Success['update_success'], "Success!!");
                                            loadForm("subjectTypeMaster/list");
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        //  setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        //   $.unblockUI();
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


