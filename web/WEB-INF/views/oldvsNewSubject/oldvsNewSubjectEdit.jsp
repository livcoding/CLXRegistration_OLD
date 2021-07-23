<%-- 
    Document   : oldvsNewSubjectEdit
    Created on : Mar 6, 2019, 12:16:01 PM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container">
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Old Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" name="sub_id" id="sub_id"  readonly="true" value="${data[0][6]}"  class="form-control"> 
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">New Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <select class="form-control" id="newSubjectCode" name="newSubjectCode" data-validation="required" data-live-search="true" data-validation-error-msg="You have not answered New Subject Code">
                        <option value="">Select</option>
                        <c:forEach items="${subjectCode}" var="list">
                            <c:if test="${list[0]==data[0][2]}">
                                <option selected="true" value="${data[0][2]}"><c:out value="${list[1]} --- ${list[2]}"/></option>
                            </c:if>
                            <c:if test="${list[0]!=data[0][2]}">
                                <option value="${list[0]}"><c:out value="${list[1]} --- ${list[2]}"/></option>
                            </c:if> 
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div> 
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" name="sub_id" id="sub_id"  readonly="true" value="${data[0][9]}"  class="form-control"> 
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">WEF:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <div class='input-group date' id='datetimepicker1'>
                        <fmt:formatDate value='${data[0][3]}' var='wefDate' type='date' pattern='dd/MM/yyyy'/>
                        <input type='text' class="form-control" id="wef" name="wef" data-validation="required" value="${wefDate}"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-12 col-md-3 control-label">Remarks:</label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <textarea rows="3" cols="35" id="remarks" name="remarks" data-validation-help="Max Length is 250" maxlength="250" data-validation-optional="true" class="form-control" data-validation-error-msg="Please Enter Remarks" placeholder="Enter Remarks">${data[0][4]}</textarea>               
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data[0][5]=='N'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data[0][5]=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
        <input type="hidden" name="semesterid" id="semesterid" value="${data[0][0]}"/>
        <input type="hidden" name="subjectCode" id="subjectid" value="${data[0][1]}"/>
        <input type="hidden" name="semesterCode" id="semesterCode" value="${data[0][8]}"/>
    </div>
</form> 

<script>
//    $("#subjectCode").chosen();
    $("#newSubjectCode").chosen();
//    $("#semesterCode").chosen();

    $('#datetimepicker1').datetimepicker({
        format: 'DD/MM/YYYY'
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "oldVsNewSubject/update",
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
                                            toastr.success('Successfully Updated', "Success!!");
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


