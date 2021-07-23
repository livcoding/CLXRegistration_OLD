<%-- 
    Document   : sis_RegistrationActivityMasterEdit
    Created on : Feb 14, 2019, 5:27:11 PM
    Author     : ankur.goyal
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="col-lg-12 container" style="margin-top:20px">
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Activity Name:<span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                    <input type="text" id="activityName" name="activityName" maxlength="60" data-validation-help="Max Length is 60" value="${data.activityname}" data-validation="required" placeholder="Enter Activity Name" class="form-control"  data-validation-error-msg="Please enter activity name!">
                </div>
            </div>
        </div>
        <div class=" col-lg-12 container" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;"  class="col-lg-5 col-xs-12 col-md-3 control-label">Fee Head:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="padding-top:9px">
                    <select class="form-control" id="feeHead" name="feeHead" data-live-search="true" data-validation="required" data-validation-error-msg="Please select fee head!" >
                        <c:forEach items="${feeHead}" var="feeHead">
                            <c:choose>
                                <c:when test="${feeHead[0]==data.feeheadid}">
                                    <option value="${feeHead[0]}-${feeHead[1]}-${feeHead[2]}" selected="true"><c:out value="${feeHead[1]}- ${feeHead[2]}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${feeHead[0]}"><c:out value="${feeHead[1]}- ${feeHead[2]}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                </div
            </div>
        </div> 
        <div class="col-lg-12 container">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label" id="Ad">
                    <c:if test="${data.deactive!='Y'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data.deactive=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
        </div>
        <input type="text" class="hidden" name="instituteId" id="instituteId" value="${data.id.instituteid}"/>
        <input type="text" class="hidden" name="activityId" id="activityId" value="${data.id.activityid}"/>
        <input type="text" class="hidden" name="feeheadid" id="feeheadid" value="${data.feeheadid}"/>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</div>
</form> 

<script>

    $("#feeHead").chosen();

    var val = getValue();
    if (val == 'Y') {
        var val = $("#feeHead").val();
        var v = val.split('-');
        var x = "<option value=" + v[0] + ">" + v[1] + "-" + v[2] + "</option>";
        document.getElementById("activityName").setAttribute("readonly", true);
        $("#feeHead").empty().trigger("chosen:updated");
        $("#feeHead").append(x).trigger("chosen:updated");
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
                                // $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "registrationActivityMaster/update",
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
                                            toastr.success("Record Successfully Updated", "Success!!");
                                            loadForm("registrationActivityMaster/list");
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


