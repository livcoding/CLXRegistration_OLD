<%-- 
    Document   : offereSubjectTaggingEdit
    Created on : 18 Dec, 2018, 5:14:43 PM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">     
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label"><font style="color:black">Semester Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="semCode" name="semCode" data-validation="required" data-live-search="true" disabled="disabled" data-validation-error-msg="Please select semester code!">       
                        <c:forEach items="${regList}" var="reList">                        
                            <c:if test="${reList.id.registrationid == data.id.registrationid}">
                                <option selected="true" value="${reList.id.registrationid}~@~${reList.registrationtype}"><c:out value="${reList.registrationcode} -- ${reList.registrationdesc}"/></option>
                            </c:if>
                            <c:if test="${reList.id.registrationid != data.id.registrationid}">
                                <option value="${reList.id.registrationid}"><c:out value="${reList.registrationcode} -- ${reList.registrationdesc}"/></option>
                            </c:if>
                        </c:forEach> 
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font style="color:black">Department Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="deptCode" name="deptCode" data-validation="required" onchange="getCurrSubCode();" data-live-search="true" data-validation-error-msg="Please select department code!">               
                        <option value="">Select</option>
                        <c:forEach items="${deptList}" var="depList">
                            <c:choose>
                                <c:when test="${depList[0] == data.departmentid}">
                                    <option selected="true" value="${depList[0]}"><c:out value="${depList[1]} -- ${depList[2]}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${depList[0]}"><c:out value="${depList[1]} -- ${depList[2]}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label"><font style="color:black">Offer Subject Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="currSubCode" name="currSubCode"  onchange="getOldSubCode(this.value);compareSubject();" data-validation="required" data-live-search="true"   data-validation-error-msg="Please select offer subject code!">               
                        <option value="">Select</option>
                        <c:forEach items="${subList}" var="suList">
                            <c:choose>
                                <c:when test="${suList[0] == data.currentsubjectid}">
                                    <option selected="true" value="${suList[0]}"><c:out value="${suList[1]} -- ${suList[2]}"/></option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${suList[0]}"><c:out value="${suList[1]} -- ${suList[2]}"/></option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach> 
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font style="color:black">Old Subject Code:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="oldSubCode" name="oldSubCode"  onchange="compareSubject();" data-live-search="true"  data-validation-error-msg="Please select old subject code!">               
                        <option value="">Select</option>
                        <c:forEach items="${subList}" var="suList">
                            <c:if test="${suList[0] == data.odsubjectid}">
                                <option selected="true" value="${suList[0]}"><c:out value="${suList[1]} -- ${suList[2]}"/></option>
                            </c:if>
                            <c:if test="${suList[0] != data.odsubjectid}">
                                <option value="${suList[0]}"><c:out value="${suList[1]} -- ${suList[2]}"/></option>
                            </c:if>
                        </c:forEach> 
                    </select>
                </div>
            </div> 
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label"><font style="color:black">Subject Type:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="subType" name="subType" data-validation="required" data-live-search="true"  data-validation-error-msg="Please select subject type!">               
                        <c:forEach items="${subTypeList}" var="subTyList">
                            <c:if test="${subTyList[0] == data.subjecttypeid}">
                                <option selected="true" value="${subTyList[0]}"><c:out value="${subTyList[1]} -- ${subTyList[2]}"/></option>
                            </c:if>
                            <c:if test="${subTyList[0] != data.subjecttypeid}">
                                <option value="${subTyList[0]}"><c:out value="${subTyList[1]} -- ${subTyList[2]}"/></option>
                            </c:if>
                        </c:forEach> 
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label"><font style="color:black">Credit:</font>
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" name="credit" id="credit" maxlength="3" value="${data.credits}"  data-validation-help="Max Length is 3" data-validation="number" onkeypress="return isNumberKey(event)" placeholder="Enter Credit" class="form-control" data-validation-error-msg="Please enter Credit">                                              
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-1 col-lg-4 col-xs-12 col-md-3 control-label" id="Ad">
                    <c:if test="${data.deactive=='N'}">
                        <span style="color: green"> Active</span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-3 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N"/>   
                    </div>
                </c:if>
                <c:if test="${data.deactive=='Y'}">
                    <span style="color: red"> Deactive</span></label>
                    <div class="col-sm-6 col-lg-8 col-xs-3 col-md-6 checkbox-inline">
                        <input type="checkbox" onclick="change()" id="deactive" name="deactive"  value="Y"/>   
                    </div>
                </c:if>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Audit Subject:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6 checkbox-inline">
                    <c:if test="${data.auditsubject == 'Y'}">
                        <input type="checkbox" id="auditsubject" name="auditsubject"  checked="true" value="Y"/>   
                    </c:if>
                    <c:if test="${data.auditsubject != 'Y'}">
                        <input type="checkbox" id="auditsubject" name="auditsubject" value="N"/>   
                    </c:if>          
                </div>
            </div>
        </div>
        <div class="col-lg-12" style="margin-top:10px">
            <table id="datatable1" class="box table table-hover table-condensed table-bordered"  cellspacing="0">
                <thead id="header" >
                    <tr class="" style="background-color:#008080;color:white" >
                        <th width="25px;" >Subject Component Code</th>
                        <th width="15px;" >Total Marks</th>
                        <th width="15px;" >Passing Marks</th>
                        <th width="15px;" >No. of Hours</th>
                        <th width="15px;" >No. of Class(Week)</th>
                        <th width="15px;" >Total Class</th>
                        <th width="250px" >Faculty Code/Name</th>
                        <th width="9px;" >Active</th>
                    </tr>
                </thead> 
                <tbody> 
                    <% int i = 1;%>
                    <c:forEach var="subComp" items="${subComp}">
                        <tr>
                            <c:set var="flag" value="0"></c:set>  
                            <c:forEach var="list" items="${detailDto}"> 
                                <c:if test="${subComp.id.subjectcomponentid==list[0]}">
                                <tr id="${list[0]}" >
                                    <td id="${list[0]}">
                                        <input type="checkbox" onclick="callChkBox(<%=i%>);" id="chk<%=i%>" name="chk<%=i%>" style="display:none"  checked="true" value="${list[0]}"/>                     
                                        <input type="hidden" id="subjectComId<%=i%>" name="subjectComId<%=i%>"  value="${subComp.id.subjectcomponentid}"/>                                       
                                        <c:set var="flag" value="${(flag+1)}"></c:set>
                                        ${list[1]}
                                    </td>                     
                                    <td><input type="text" id="total<%=i%>" name="total<%=i%>" value="${list[3]}" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3" class="form-control has-help-txt"    data-validation-error-msg="Please enter total marks!"/></td>
                                    <td><input type="text"  id="passing<%=i%>" name="passing<%=i%>" value="${list[2]}" style="width:90px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt"  data-validation-error-msg="Please enter passing marks!"/></td>
                                    <td><input type="text" id="noofhours<%=i%>" name="noofhours<%=i%>" value="${list[4]}" style="width:60px" onkeypress="return isNumberKey(event)"   maxlength="3"  class="form-control has-help-txt" data-validation-error-msg="Please enter no.of hours!" /></td>
                                    <td><input type="text" id="noofclass<%=i%>" name ="noofclass<%=i%>" value="${list[5]}" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt"  data-validation-error-msg="Please enter no. of class(Week)!"/></td>
                                    <td><input type="text" id="totalclass<%=i%>" name ="totalclass<%=i%>" value="${list[6]}" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt"  data-validation-error-msg="Please enter total class!"/></td>
                                    <td>
                                        <select class="form-control" id="facultyid<%=i%>" name="facultyid<%=i%>" >
                                            <option value="">Select</option>
                                            <c:forEach var="stafflist" items="${stafflist}"> 
                                                <c:if test="${stafflist[0] == list[8]}">
                                                    <option selected="true" value="${stafflist[0]}'~@~'${stafflist[3]}"><c:out value="${stafflist[1]} -- ${stafflist[2]}"/></option>
                                                </c:if>
                                                <c:if test="${stafflist[0] != list[8]}">
                                                    <option value="${stafflist[0]}'~@~'${stafflist[3]}"><c:out value="${stafflist[1]} -- ${stafflist[2]}"/></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <c:if test="${list[7]=='N'}">
                                        <td><input type="checkbox" id="deactive<%=i%>" name="deactive<%=i%>" checked="true"id="chek<%=i%>" /></td>
                                        </c:if>
                                        <c:if test="${list[7]=='Y'}">
                                        <td><input type="checkbox" id="deactive<%=i%>" name="deactive<%=i%>"   id="chek<%=i%>" /></td>
                                        </c:if>
                                        <c:if test="${list[7]==''||list[7]==null}">
                                        <td><input type="checkbox" id="deactive<%=i%>" name="deactive<%=i%>" id="chek<%=i%>" /></td>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${flag==0}">
                                <td id="${subComp.id.subjectcomponentid}}">
                                    <input type="checkbox" onclick="callChkBox(<%=i%>);" id="chk<%=i%>" name="chk<%=i%>" style="display:none"  value="${subComp.id.subjectcomponentid}"/>                       
                                    <input type="hidden" id="subjectComId<%=i%>" name="subjectComId<%=i%>"  value="${subComp.id.subjectcomponentid}"/>                       
                                    ${subComp.subjectcomponentcode}</td>                     
                                <td><input type="text" id="total<%=i%>" name="total<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3" class="form-control has-help-txt" disabled="true"   data-validation-error-msg="Please enter total marks!"/></td>
                                <td><input type="text"  id="passing<%=i%>" name="passing<%=i%>" style="width:90px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" disabled="true"  data-validation-error-msg="Please enter passing marks!"/></td>
                                <td><input type="text" id="noofhours<%=i%>" name="noofhours<%=i%>" style="width:60px" onkeypress="return isNumberKey(event)"   maxlength="3"  class="form-control has-help-txt" disabled="true" data-validation-error-msg="Please enter no.of hours!"/></td>
                                <td><input type="text" id="noofclass<%=i%>" name ="noofclass<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" disabled="true" data-validation-error-msg="Please enter no. of class(Week)!"/></td>
                                <td><input type="text" id="totalclass<%=i%>" name ="totalclass<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" disabled="true" data-validation-error-msg="Please enter total class!"/></td>
                                <td>
                                    <select class="form-control" id="facultyid<%=i%>" name="facultyid<%=i%>" disabled="true" >
                                        <option value="">Select</option>
                                        <c:forEach var="stafflist" items="${stafflist}"> 
                                            <option value="${stafflist[0]}'~@~'${stafflist[3]}"><c:out value="${stafflist[1]} -- ${stafflist[2]}"/></option>
                                        </c:forEach>
                                    </select>
                                </td>
                                <td><input type="checkbox" align="center" id="deactive<%=i%>" name="deactive<%=i%>" /></td>
                                </c:if>
                        </tr>
                        <% i++;%> 
                    </c:forEach>

                </tbody>
            </table>
        </div> 
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Update</a>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
    <input type="hidden" value="${data.id.offersubjectid}" id="offerSubjectId" name="offerSubjectId"/>
    <input type="hidden" value="${data.id.registrationid}" id="regId" name="regId"/>
</form>

<script>
    $("#semCode").chosen({width: '100%'});
    $("#subType").chosen({width: '100%'});
    $("#oldSubCode").chosen({width: '100%'});
    $("#currSubCode").chosen({width: '100%'});
    $("#deptCode").chosen({width: '100%'});
    $("#facultyid").chosen({width: '100%'});
    $("#subjectcomponentid").chosen({width: '100%'});


    $(document).ready(function () {
        for (var i = 0; i < 4; i++) {
            $('#facultyid' + i).chosen({width: '100%'});
        }
        for (var i = 1; i < 4; i++) {
            if ($("#chk" + i).prop('checked') == true)
            {
                $('#deactive' + i).prop('checked', true);
                $("#total" + i).removeAttr("disabled");
                $("#total" + i).attr({'required': "true", 'data-validation': "number"});
                $("#passing" + i).removeAttr("disabled");
                $("#passing" + i).attr({'required': "true", 'data-validation': "number"});
                $("#noofhours" + i).removeAttr("disabled");
                $("#noofhours" + i).attr({'required': "true", 'data-validation': "number"});
                $("#noofclass" + i).removeAttr("disabled");
                $("#noofclass" + i).attr({'required': "true", 'data-validation': "number"});
                $("#totalclass" + i).removeAttr("disabled");
                $("#totalclass" + i).attr({'required': "true", 'data-validation': "number"});
            }
        }
    });

    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
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
    function compareSubject()
    {
        var oldSubCode = $("#oldSubCode").val();
        var currSubCode = $("#currSubCode").val();
        if (oldSubCode == currSubCode) {
            BootstrapDialog.alert("Old Subject Code and Offer Subject Code Does not Same.");
            $("#oldSubCode").val('').trigger("chosen:updated");
        }
    }

    function callChkBox(index) {
        var value = $("#chk" + index).val();
        var lfckv = document.getElementById("chk" + index).checked;
        if (lfckv === true) {
            checkDisableTrue(value, index);
        } else if (lfckv === false) {
            checkDisableFalse(value, index);
        }
    }
    function checkDisableTrue(v, i) {
        $("#chk" + v).val('');
        $('#deactive' + i).prop('checked', true);
        $("#total" + i).removeAttr("disabled");
        $("#total" + i).attr({'required': "true", 'data-validation': "number"});
        $("#passing" + i).removeAttr("disabled");
        $("#passing" + i).attr({'required': "true", 'data-validation': "number"});
        $("#noofhours" + i).removeAttr("disabled");
        $("#noofhours" + i).attr({'required': "true", 'data-validation': "number"});
        $("#noofclass" + i).removeAttr("disabled");
        $("#noofclass" + i).attr({'required': "true", 'data-validation': "number"});
        $("#totalclass" + i).removeAttr("disabled");
        $("#totalclass" + i).attr({'required': "true", 'data-validation': "number"});
        $("#facultyid" + i).removeAttr("disabled");
        $('#facultyid' + i + '_chosen').removeClass("chosen-disabled");

    }
    function checkDisableFalse(v, i) {
        $("#chk" + v).val('');
        $("#total" + i).val('');
        $("#passing" + i).val('');
        $("#noofhours" + i).val('');
        $("#noofclass" + i).val('');
        $("#totalclass" + i).val('');
        $('#deactive' + i).prop('checked', false);
        $("#total" + i).attr("disabled", "disabled");
        $("#passing" + i).attr("disabled", "disabled");
        $("#noofhours" + i).attr("disabled", "disabled");
        $("#noofclass" + i).attr("disabled", "disabled");
        $("#totalclass" + i).attr("disabled", "disabled");
        $('#facultyid' + i).attr("disabled", "disabled");
        $('#facultyid' + i + '_chosen').addClass("chosen-disabled");
    }

    function getOldSubCode(subid) {
        var department = $("#deptCode").val();
        var semester = $("#semCode").val();
        if (department != '' && semester != '') {
            $("#oldSubCode").empty();
            $.ajax({
                type: "POST",
                url: "offereSubjectTagging/getSubjectCode",
                data: '&department=' + department + '&subjectid=' + subid + '&registrationid=' + semester,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.oldSubject != '') {
                        var str1 = '';
                        str1 += '<option value="">Select</option>';
                        $.each(data.oldSubject, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + "  --  " + item[2] + '</option>'
                        });
                        $("#oldSubCode").append(str1);
                        $("#oldSubCode").trigger("chosen:updated");
                    } else {
                        $("#oldSubCode").val('').trigger("chosen:updated");
                        $("#currSubCode").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("Old Subject Code Not Found For This Department...!");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            $("#deptCode").val('').trigger("chosen:updated");
            $("#oldSubCode").empty();
            $("#oldSubCode").val('').trigger("chosen:updated");
            $("#currSubCode").empty();
            $("#currSubCode").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Department Code...!");
        }
    }

    function getCurrSubCode() {
        $("#currSubCode").empty();
        var department = $("#deptCode").val();
        var oldSubject = $("#oldSubCode").val();
        var subid = ' ';
        var semester = $("#semCode").val();
        if (department != '') {
            $.ajax({
                type: "POST",
                url: "offereSubjectTagging/getSubjectCode",
                data: '&department=' + department + '&subjectid=' + subid + '&registrationid=' + semester,
                dataType: "json",
                async: false,
                timeout: 5000,
                cache: false,
                beforeSend: function (xhr, options) {
                    return true;
                },
                success: function (data) {
                    if (data.oldSubject != null) {
                        var str1 = '';
                        var str1 = '<option value="">Select</option>';
                        $.each(data.oldSubject, function (i, item)
                        {
                            str1 += '<option value="' + item[0] + '">' + item[1] + "  --  " + item[2] + '</option>'
                        });
                        $("#currSubCode").append(str1);
                        $("#currSubCode").trigger("chosen:updated");
                    } else {
                        $("#currSubCode").empty();
                        $("#currSubCode").val('').trigger("chosen:updated");
                        $("#oldSubCode").empty();
                        $("#oldSubCode").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("Offer Subject Code Not Found For This Departmet...!");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            $("#currSubCode").empty();
            $("#currSubCode").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Department Code...!");
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
                                    url: "offereSubjectTagging/update",
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
                                            loadForm("offereSubjectTagging/list");
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
        var semesterType = $("#semCode").val().split('~@~')[1];
        var i = 1;
        var flag = 0;
        var staff = 1;
        var save = false;
        for (i = 1; i < 4; i++) {
            if ($("#chk" + i + "").is(":checked")) {
                save = true;
                var total = parseFloat($("#total" + i).val());
                var passing = parseFloat($("#passing" + i).val());
                var totalclass = parseFloat($("#totalclass" + i).val());
                var noofclass = parseFloat($("#noofclass" + i).val());
                var facultyid = $("#facultyid" + i).val();
                if (passing > total) {
                    flag = 1;
                } else if (passing == total) {
                    flag = 2;
                }
                if (noofclass > totalclass) {
                    flag = 3;
                }
                if (facultyid == '') {
                    staff = 0;
                }
            }
        }
        if (flag != 0) {
            if (flag == 1) {
                BootstrapDialog.alert("Passing Marks should not be Greater than Total Marks.");
            } else if (flag == 2) {
                BootstrapDialog.alert("Passing Marks should not be equal to Total Marks.");
            } else if (flag == 3) {
                BootstrapDialog.alert("No. of Class(Week) should not be greater than Total Class.");
            }
        } else {
            debugger;
            if (save) {
                if (semesterType != 'S') {
                    $("#ajaxform").submit();
                } else {
                    if (staff != 0) {
                        $("#ajaxform").submit();
                    } else {
                        BootstrapDialog.alert("Please Select Faculty Code/Name...!");
                    }
                }
            } else {
                BootstrapDialog.alert("Please Select atleast one Subject Component.");
            }
        }
    }
</script>

