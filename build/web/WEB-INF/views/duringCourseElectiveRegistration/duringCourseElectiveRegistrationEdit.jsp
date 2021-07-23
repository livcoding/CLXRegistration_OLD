<%-- 
    Document   : DuringCourseElectiveRegistartionEdit
    Created on : 15 JUL : 2019 
    Author     : Malkeet Singh
--%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Program Code<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">

                <input type="hidden" name="programId" value="${getData.id.programid}">  
                <select class="form-control" id="programCode" name="programCode" data-validation="required" style="width:85%;" disabled="true"  data-validation-error-msg="Please select program code!">
                    <option value="">Select</option>
                    <c:forEach items="${prog_list}" var="prog_list">
                        <c:if test="${prog_list[2]==programId}">
                            <option value="${prog_list[2]}" selected="true"><c:out value="${prog_list[0]} / ${prog_list[1]}"/></option>
                        </c:if>
                        <c:if test="${prog_list[2]!=programId}">
                            <option value="${prog_list[2]}"><c:out value="${prog_list[0]} / ${prog_list[1]}"/></option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" ><h3 style="background-color:#008080;color:white"><center>During Course Registration</center></h3></div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Credit To Counted in Course Registration for Subjects Type :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="creditTo" name="creditTo" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list" >
                        <c:set value="0" var="flag"/>
                        <c:forEach items="${creditTo}" var="creditTo"> 
                            <c:if test="${list[1] == creditTo}">
                                <c:set value="1" var="flag"/>
                            </c:if>
                        </c:forEach> 
                        <c:if test="${flag==1}">
                            <option value="${list[1]}" selected="true"><c:out value="${list[1]}"/></option>
                        </c:if>
                        <c:if test="${flag!=1}">
                            <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                        </c:if>
                    </c:forEach> 
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Elective Type Subject Shown in Course Registration for Subjects Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="elective" name="elective" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list" >
                        <c:set value="0" var="flag"/>
                        <c:forEach items="${elective}" var="elective"> 
                            <c:if test="${list[1] == elective}">
                                <c:set value="1" var="flag"/>
                            </c:if>
                        </c:forEach> 
                        <c:if test="${flag==1}">
                            <option value="${list[1]}" selected="true"><c:out value="${list[1]}"/></option>
                        </c:if>
                        <c:if test="${flag!=1}">
                            <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                        </c:if>
                    </c:forEach> 
                </select>
            </div>
        </div>
    </div>    

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Elective Subject To be Disabled While Course registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="elecSub" name="elecSub" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list" >
                        <c:set value="0" var="flag"/>
                        <c:forEach items="${elecSub}" var="elecSub"> 
                            <c:if test="${list[1] == elecSub}">
                                <c:set value="1" var="flag"/>
                            </c:if>
                        </c:forEach> 
                        <c:if test="${flag==1}">
                            <option value="${list[1]}" selected="true"><c:out value="${list[1]}"/></option>
                        </c:if>
                        <c:if test="${flag!=1}">
                            <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                        </c:if>
                    </c:forEach> 
                </select>
            </div>
        </div>
    </div>    

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Show Back Paper In Course Registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="showBack" name="showBack" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.showbackpaperincoursereg == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.showbackpaperincoursereg == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Editable Back Paper In Course Registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="editBack" name="editBack" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.enablebackpaperincoursereg == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.enablebackpaperincoursereg == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Allow Not Offered Subjects during Course Registration :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="allowNot" name="allowNot" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.allownotrunningbackpaper == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.allownotrunningbackpaper == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Default Selected option for Back papers :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="defaultSel" name="defaultSel" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.backpaperdefaultselected == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.backpaperdefaultselected == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Back Paper Selection Mandatory:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="backPaperSel" name="backPaperSel" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!"> 
                    <c:if test="${getData.backpaperselectionmandaoty == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.backpaperselectionmandaoty == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Printing of Course Registration:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="printCourse" name="printCourse" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.printingofcourseregistration == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.printingofcourseregistration == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Student Fee Dues Checking During Course Registration:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="studentFee" name="studentFee" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.feeduecheckisrequierdfor == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.feeduecheckisrequierdfor == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Back paper Fee Calculation during Course Registration:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="backPaperFee" name="backPaperFee" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.backpaperfeetobe == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.backpaperfeetobe == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="row col-lg-12 form-group" ><h3 style="background-color:#008080;color:white"><center>During Elective Registration</center></h3></div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Credit To Counted in Elective Registration for Subjects Type :<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="creditToElec" name="creditToElec" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list" >
                        <c:set value="0" var="flag"/>
                        <c:forEach items="${creditToElec}" var="creditToElec"> 
                            <c:if test="${list[1] == creditToElec}">
                                <c:set value="1" var="flag"/>
                            </c:if>
                        </c:forEach> 
                        <c:if test="${flag==1}">
                            <option value="${list[1]}" selected="true"><c:out value="${list[1]}"/></option>
                        </c:if>
                        <c:if test="${flag!=1}">
                            <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                        </c:if>
                    </c:forEach> 
                </select>
            </div>
        </div>
    </div> 
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Elective Type Subject Shown in Elective Choice for Subjects Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="elecType" name="elecType" multiple="true" data-validation="required" data-live-search="true" data-validation-error-msg="Please select this field!" style="width:85%;">
                    <c:forEach items="${data}" var="list" >
                        <c:set value="0" var="flag"/>
                        <c:forEach items="${elecType}" var="elecType"> 
                            <c:if test="${list[1] == elecType}">
                                <c:set value="1" var="flag"/>
                            </c:if>
                        </c:forEach> 
                        <c:if test="${flag==1}">
                            <option value="${list[1]}" selected="true"><c:out value="${list[1]}"/></option>
                        </c:if>
                        <c:if test="${flag!=1}">
                            <option value="${list[1]}"><c:out value="${list[1]}"/></option>
                        </c:if>
                    </c:forEach> 
                </select>
            </div>
        </div>
    </div> <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Type of Electives subjects to be kept disable in Elective Registration Form:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="typeElec" name="typeElec" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.electivetypetobedisabled_e == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.electivetypetobedisabled_e == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">During Elective Subject Choices Preference can be modified:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="duringElec" name="duringElec" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.modificationofpereferences == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.modificationofpereferences == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-10 form-group">
            <label style="text-transform:capitalize;" class="col-sm-6 col-lg-7 col-xs-12 col-md-6 control-label">Printing of Elective Preferences is allowed:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-12 col-md-6">
                <select class="form-control" id="printElec" name="printElec" data-validation="required" style="width:85%;" data-validation-error-msg="Please select this field!">
                    <c:if test="${getData.allowprintingofelectivechoices == 'Y'}">
                        <option value="Y" selected="true">Yes</option>
                        <option value="N" >No</option>
                    </c:if>
                    <c:if test="${getData.allowprintingofelectivechoices == 'N'}">
                        <option value="Y" >Yes</option>
                        <option value="N" selected="true">No</option>
                    </c:if> 
                </select>
            </div>
        </div>
    </div>

    <div class="col-lg-12 form-group">              
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:updateData();" class="btn btn-success btn-sm btn-flat"> Update</a>
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
        </div>
    </div>
</div>
</form>

<script>
    $("#programCode").chosen();
    $("#creditTo").chosen();
    $("#elective").chosen();
    $("#elecSub").chosen();
    $("#showBack").chosen();
    $("#editBack").chosen();
    $("#allowNot").chosen();
    $("#defaultSel").chosen();
    $("#backPaperSel").chosen();
    $("#printCourse").chosen();
    $("#studentFee").chosen();
    $("#backPaperFee").chosen();
    $("#elecType").chosen();
    $("#creditToElec").chosen();
    $("#typeElec").chosen();
    $("#duringElec").chosen();
    $("#printElec").chosen();

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
                                    url: "duringCourseElectiveRegistration/update",
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
                                            toastr.success('Record Successfully Updated.', "Success!!");
                                            loadForm("duringCourseElectiveRegistration/list");
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

    function updateData() {
        $("#ajaxform").submit();
    }
</script>


