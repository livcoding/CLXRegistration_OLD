<%-- 
    Document   : programSubjectTaggingEdit
    Created on : 22 Dec, 2018, 12:19:01 PM
    Author     : deepak.gupta
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Subject Code :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="subCode" id="subCode" class="form-control" value="${editData[14]}" readonly="true">                                              
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Subject Desc :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="subDesc" id="subDesc" class="form-control" value="${editData[15]}" readonly="true">                                              
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Credit :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6 ">
                    <input type="text" name="credit" id="credit" class="form-control" value="${editData[9]}" readonly="true">                                              
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Subject Running :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${editData[11] == 'N'}">
                        <input type="checkbox" id="subRunning" name="subRunning"  value="Y"/>   
                    </c:if>
                    <c:if test="${editData[11] == 'Y'}">
                        <input type="checkbox" id="subRunning" checked="true" name="subRunning" value="Y"/>   
                    </c:if>          
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">L/T/P :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" name="credit" id="credit" class="form-control" value="${editData[9]}" readonly="true">                                              
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Audit Subject:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${editData[18] == 'Y'}">
                        <input type="checkbox" id="auditsubject" name="auditsubject"  checked="true" value="Y"/>   
                    </c:if>
                    <c:if test="${editData[18] != 'Y'}">
                        <input type="checkbox" id="auditsubject" name="auditsubject" value="Y"/>   
                    </c:if>          
                </div>
            </div>
        </div>

        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">PST Populated :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${editData[10] == 'N'}">
                        <input type="checkbox" id="pstPopulated" name="pstPopulated"  value="Y"/>   
                    </c:if>
                    <c:if test="${editData[10] == 'Y'}">
                        <input type="checkbox" id="pstPopulated" checked="true" name="pstPopulated" value="Y"/>   
                    </c:if>          
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Custom Finalized :
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${editData[12] == 'N'}">
                        <input type="checkbox" id="customFin" name="customFin"  value="Y"/>   
                    </c:if>
                    <c:if test="${editData[12] == 'Y'}">
                        <input type="checkbox" id="customFin" checked="true" name="customFin" value="Y"/>   
                    </c:if>          
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label" id="Ad">
                    <span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6 checkbox-inline">
                    <c:if test="${editData[13] == 'Y'}">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </c:if>
                    <c:if test="${editData[13] == 'N'}">
                        <input type="checkbox" onclick="change()" id="deactive" checked="true" name="deactive" value="Y"/>   
                    </c:if>          
                </div>
            </div>
        </div>
        <input type="hidden" name="programSubId" value="${editData[0]}"/>    
        <input type="hidden" name="regId" value="${editData[1]}"/>    
        <input type="hidden" name="academicYear" value="${editData[2]}"/>    
        <input type="hidden" name="programId" value="${editData[3]}"/>    
        <input type="hidden" name="sectionId" value="${editData[4]}"/>    
        <input type="hidden" name="styNumber" value="${editData[5]}"/>    
        <input type="hidden" name="basketId" value="${editData[6]}"/>    
        <input type="hidden" name="subjectId" value="${editData[7]}"/>    
        <input type="hidden" name="deptId" value="${editData[8]}"/>    
        <input type="hidden" name="subjecttype" value="${editData[16]}"/>   
        <input type="hidden" name="subjecttypeid" value="${editData[17]}"/>     
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>
    $(document).ready(function () {
        change();
    });
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
                                $.ajax({
                                    type: "POST",
                                    url: "programSubjectTagging/update",
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
                                            toastr.success(Success['update_success'], "Success!!");
//                                            loadForm("programSubjectTagging/list");
                                            loadData('1');
                                            getchildData('1');
                                              closePage();
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