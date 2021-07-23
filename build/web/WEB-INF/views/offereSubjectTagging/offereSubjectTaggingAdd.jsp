<%-- 
    Document   : offereSubjectTaggingAdd
    Created on : 18 Dec, 2018, 5:12:08 PM
    Author     : deepak.gupta
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">      
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="semCode" name="semCode" data-validation="required" onchange="clearData(); setSemesterType(this.value);" data-live-search="true"  data-validation-error-msg="Please select semester code!">               
                        <option value="">Select</option>
                        <c:forEach items="${semester}" var="list">
                            <option value="${list[0]}~@~${list[5]}"><c:out value="${list[1]} -- ${list[2]}"/></option>
                        </c:forEach> 
                    </select>
                    <input type="hidden" name="semesterType" id="semesterType"/>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Department Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="deptCode" name="deptCode" data-validation="required" onchange="getCurrSubCode();" data-live-search="true"   data-validation-error-msg="Please select department code!">               
                        <option value="">Select</option>
                        <c:forEach items="${deptList}" var="depList">
                            <option value="${depList[0]}"><c:out value="${depList[1]} -- ${depList[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div> 

        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Offer Subject Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="currSubCode" name="currSubCode" onchange="getOldSubCode(this.value); getSubjectComponent(); compareSubject();" data-validation="required" data-live-search="true"   data-validation-error-msg="Please select offer subject code!">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Old Subject Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="oldSubCode" name="oldSubCode"  onchange="compareSubject();"    data-validation-error-msg="Please select old subject code!">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div> 
        </div> 
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Subject Type:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                    <select class="form-control" id="subType" name="subType" data-validation="required" data-live-search="true"   data-validation-error-msg="Please select subject type!">               
                        <option value="">Select</option>
                        <c:forEach items="${subTypeList}" var="subTyList">
                            <option value="${subTyList[0]}"><c:out value="${subTyList[1]} -- ${subTyList[2]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-4 col-xs-3 col-md-3 control-label">Credit:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-8 col-xs-12 col-md-6">
                    <input type="text" name="credit" id="credit" maxlength="3" onblur="enableSubjectComponent();" data-validation-help="Max Length is 3" data-validation="number" onkeypress="return isNumberKey(event)" placeholder="Enter Credit" class="form-control" data-validation-error-msg="Please enter Credit">                                              
                </div>
            </div> 
        </div>
        <div id="newfaculty" >
            <div class="row col-lg-12 form-group">
                <div class="row col-lg-6 form-group" >
                    <label style="text-transform: capitalize;" class="col-sm-3 col-lg-5 col-xs-3 col-md-3 control-label">Faculty From:
                        <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                    <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6" style="margin-top: 8px;">
                        <input type="radio" name="facultyfrom" id="SD" value="SD"onclick="getFaculty(this.value)" class="checkbox-inline"/> Same Department
                        <br><input type="radio" name="facultyfrom" id="OD" value="OD" onclick="getFaculty(this.value)" class="checkbox-inline"/>  Other Department
                        <br><input type="radio" name="facultyfrom" id="OI" value="OI" onclick="getFaculty(this.value)" class="checkbox-inline"/>  Other Institute
                    </div>
                </div> 
            <div class="row col-lg-6 form-group" style="margin-left: 10px;" >
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label" >
                    <span> Audit Subject:</span></label>
                <div class="col-sm-6 col-lg-4 col-xs-6 col-md-6 checkbox-inline">
                    <input type="checkbox"  id="auditsubject" name="auditsubject" value="Y"/>           
                </div>
            </div> 
            </div> 
        </div>
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group" >
                <label style="text-transform:capitalize;" class="col-sm-1 col-lg-4 col-xs-12 col-md-3 control-label" id="Ad"><span style="color: green"> Active</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-3 col-md-6 checkbox-inline">
                    <input type="checkbox" onclick="change()" id="deactive" name="deactive" checked="true" value="N">
                </div>
            </div>
        </div>
        <div class="col-lg-12" style="margin-top:10px;">
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
                    <c:forEach items="${componentList}" var="list" >
                        <tr id="${list.getIdStr()}" >
                            <td id="${list.getIdStr()}">${list.subjectcomponentcode}
                            </td>                     
                            <td><input type="text" id="total<%=i%>" name="total<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3" class="form-control has-help-txt" disabled="true"   data-validation-error-msg="Please enter total marks!"/></td>
                            <td><input type="text"  id="passing<%=i%>" name="passing<%=i%>" style="width:90px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" disabled="true"  data-validation-error-msg="Please enter passing marks!"/></td>
                            <td><input type="text" id="noofhours<%=i%>" name="noofhours<%=i%>" style="width:60px" onkeypress="return isNumberKey(event)"   maxlength="3"  class="form-control has-help-txt" disabled="true"  data-validation-error-msg="Please enter no.of hours!"/></td>
                            <td><input type="text" id="noofclass<%=i%>" name ="noofclass<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" disabled="true"  data-validation-error-msg="Please enter no. of class(Week)!" /></td>
                            <td><input type="text" id="totalclass<%=i%>" name ="totalclass<%=i%>" style="width:70px" onkeypress="return isNumberKey(event)"  maxlength="3"  class="form-control has-help-txt" disabled="true"  data-validation-error-msg="Please enter total class!"/></td>
                            <td>
                                <select class="form-control" id="facultyid<%=i%>" name="facultyid<%=i%>" disabled="true" >
                                    <option value="">Select</option>
                                </select>
                            </td>
                            <td><input type="checkbox" align="center" id="deactive<%=i%>" name="deactive<%=i%>" />
                                <input type="checkbox" id="chk<%=i%>" name="chk<%=i%>" style="display:none" /> 
                                <input type="hidden" id="ids<%=i%>" name="ids<%=i%>" value="${list.getIdStr()}"/> </td>
                        </tr>
                        <% i++;%>                        
                    </c:forEach> 
                </tbody>
            </table>
        </div> 
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button> 
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
            </div>
        </div>
    </div>
</form>
<input type="hidden"  id="subComCheck" name="subComCheck" value="">
<script>
    $("#semCode").chosen({width: '100%'});
    $("#subType").chosen({width: '100%'});
    $("#oldSubCode").chosen({width: '100%'});
    $("#currSubCode").chosen({width: '100%'});
    $("#deptCode").chosen({width: '100%'});
    $(document).ready(function () {
        for (var i = 0; i < 4; i++) {
            $('#facultyid' + i).chosen({width: '100%'});
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
    function enableSubjectComponent() {
        debugger;
        if ($("#credit").val() != '') {
            debugger;
            var save = false;
            for (var i = 1; i < 4; i++) {
                debugger;
                if (document.getElementById("chk" + i).checked) {
                    save = true;
                }
            }
            if (save) {
                $("#subComCheck").val('');
            } else {
                $("#subComCheck").val("0");
            }
        } else {
            debugger;
            $("#subComCheck").val('');
        }
    }
    function myReset() {
        $("#semCode").val('').trigger("chosen:updated");
        $("#subType").val('').trigger("chosen:updated");
        $("#oldSubCode").empty();
        $("#oldSubCode").val('').trigger("chosen:updated");
        $("#currSubCode").empty();
        $("#currSubCode").val('').trigger("chosen:updated");
        $("#deptCode").val('').trigger("chosen:updated");
        $("#subComCheck").val('');
        var i = 1
        for (i = 1; i < 4; i++) {
            $("#total" + i).attr("disabled", "disabled");
            $("#passing" + i).attr("disabled", "disabled");
            $("#noofhours" + i).attr("disabled", "disabled");
            $("#noofclass" + i).attr("disabled", "disabled");
            $("#totalclass" + i).attr("disabled", "disabled");
            $('#facultyid' + i).attr("disabled", "disabled");
            $('#facultyid' + i).empty();
            $('#facultyid' + i).trigger("chosen:updated");
        }
        if ($("#deactive").val() == 'Y') {
            $("#deactive").val("N");
            $("#Ad").html("");
            $("#Ad").html('<span style="color: green"> Active</span>');
        }
        $("#semesterType").val('');
    }

    function setSemesterType(value) {
        debugger;
        $("#semesterType").val(value.split('~@~')[1]);
    }
    function clearData() {
        $("#semesterType").val('');
        $("#deptCode").val('').trigger("chosen:updated");
        $("#oldSubCode").empty();
        $("#oldSubCode").val('').trigger("chosen:updated");
        $("#currSubCode").empty();
        $("#currSubCode").val('').trigger("chosen:updated");
        $("#subType").val('').trigger("chosen:updated");
        $("#facultyid").empty();
        $("#facultyid").trigger("chosen:updated");
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

    //this function not in use.... Because qry is changed! same subject is not show in both LOV's...
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
        $('#facultyid' + i).attr("disabled", "disabled");
        $('#facultyid' + i + '_chosen').addClass("chosen-disabled");
        $("#facultyid" + i).removeAttr("disabled");
        $('#facultyid' + i + '_chosen').removeClass("chosen-disabled");
        enableSubjectComponent();
    }
    function checkDisableFalse(v, i) {
        $("#chk" + i).val('');
        $('#deactive' + i).prop('checked', false);
        $("#total" + i).attr("disabled", "disabled");
        $("#total" + i).val('');
        $("#passing" + i).attr("disabled", "disabled");
        $("#noofhours" + i).val('');
        $("#noofhours" + i).attr("disabled", "disabled");
        $("#noofclass" + i).val('');
        $("#noofclass" + i).attr("disabled", "disabled");
        $("#totalclass" + i).val('');
        $("#totalclass" + i).attr("disabled", "disabled");
        $("#facultyid" + i).removeAttr("disabled");
        $('#facultyid' + i + '_chosen').removeClass("chosen-disabled");
        $('#facultyid' + i).attr("disabled", "disabled");
        $('#facultyid' + i + '_chosen').addClass("chosen-disabled");
        enableSubjectComponent();
    }

    function getOldSubCode(subid) {
        if ($("#currSubCode").val().split('~@~')[1] == 'A') {
            $("#auditsubject").prop("checked", true);
        }else{
            $("#auditsubject").prop("checked", false);
        }
        var department = $("#deptCode").val();
        var semester = $("#semCode").val();
        if (department != '' && semester != '') {
            $("#oldSubCode").empty();
            $("#subType").val('').trigger("chosen:updated");
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
                        $("#oldSubCode").empty();
                        $("#oldSubCode").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("Old Subject Code Not Found For This Department...!");
                    }
                },
                error: function (response) {
                    toastr.warning('Something Wrong...............', "Warning!!");
                }
            });
        } else {
            $("#deptCode").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Semester Code...!");
        }
    }

    function getCurrSubCode() {
        $("#subType").val('').trigger("chosen:updated");
        $("#oldSubCode").empty();
        $("#oldSubCode").val('').trigger("chosen:updated");
        $("#facultyid").empty();
        $("#facultyid").trigger("chosen:updated");
        var department = $("#deptCode").val();
        $("#currSubCode").empty();
        var subid = ' ';
        var department = $("#deptCode").val();
        var semester = $("#semCode").val();
        if (semester != '') {
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
                        if (data.oldSubject != '') {
                            var str1 = '';
                            str1 += '<option value="">Select</option>';
                            $.each(data.oldSubject, function (i, item)
                            {
                                str1 += '<option value="' + item[0] + '~@~' + item[3] + '">' + item[1] + "  --  " + item[2] + '</option>'
                            });
                            $("#currSubCode").append(str1);
                            $("#currSubCode").trigger("chosen:updated");
                        } else {
                            $("#currSubCode").empty();
                            $("#currSubCode").val('').trigger("chosen:updated");
                            $("#oldSubCode").empty();
                            $("#oldSubCode").val('').trigger("chosen:updated");
                            BootstrapDialog.alert("Offer Subject Code Not Found For This Department...!");
                        }
                    },
                    error: function (response) {
                        toastr.warning('Something Wrong...............', "Warning!!");
                    }
                });
            } else {
                BootstrapDialog.alert("Please Select Department Code...!");
            }
        } else {
            $("#deptCode").val('').trigger("chosen:updated");
            BootstrapDialog.alert("Please Select Semester Code...!");
        }
    }
    function getSubjectComponent() {
        var semester = $("#semCode").val();
        var depid = $("#deptCode").val();
        $("#facultyid").empty();
        $("#facultyid").trigger("chosen:updated");
        getFaculty('SD');
        var subjectid = $("#currSubCode").val();
        $.ajax({
            type: "POST",
            url: "offereSubjectTagging/getSubjectComponent",
            data: '&subjectid=' + subjectid,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '<option value="">Select</option>';
                $('#chk1').prop('checked', false);
                callChkBox(1);
                $('#chk2').prop('checked', false);
                callChkBox(2);
                $('#chk3').prop('checked', false);
                callChkBox(3);
                $.each(data.ComponentList, function (i, item) {
                    if (item[1] == 'L') {
                        $('#chk1').prop('checked', true);
                        callChkBox(1);
                    } else if (item[1] == 'T') {
                        $('#chk2').prop('checked', true);
                        callChkBox(2);
                    } else if (item[1] == 'P') {
                        $('#chk3').prop('checked', true);
                        callChkBox(3);
                    }
                });
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
    }
    function getFaculty(fac) {
        var depid = $("#deptCode").val();
        for (var i = 0; i < 4; i++) {
            $('#facultyid' + i).empty();
            $('#facultyid' + i).trigger("chosen:updated");
        }
        $.ajax({
            type: "POST",
            url: "offereSubjectTagging/getFaculty",
            data: '&departmentid=' + depid + '&facultyFrom=' + fac,
            dataType: "json",
            async: true,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '<option value="">Select</option>';
                $.each(data.facultyList, function (i, item) {
                    str1 += '<option value="' + item[0] + '~@~' + item[3] + '" >' + item[1] + ' / ' + item[2] + '</option>';
                });
                for (var i = 0; i < 4; i++) {
                    $('#facultyid' + i).append(str1);
                    $('#facultyid' + i).trigger("chosen:updated");
                }
            },
            error: function (response) {
                toastr.warning('Something Wrong...............', "Warning!!");
            }
        });
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
                                    url: "offereSubjectTagging/save",
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
                                            toastr.success(Success['save_success'], "Success!!");
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
        var semesterType = $("#semesterType").val();
        var i = 1;
        var flag = 0;
        var staff = 1;
        var checkboxcheck = $("#subComCheck").val();
        for (i = 1; i < 4; i++) {
            if ($("#chk" + i + "").is(":checked")) {
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
                BootstrapDialog.alert("Passing Marks should not be Greater than Total Marks...!");
            } else if (flag == 2) {
                BootstrapDialog.alert("Passing Marks should not be equal to Total Marks...!");
            } else if (flag == 3) {
                BootstrapDialog.alert("No. of Class(Week) should not be greater than Total Class...!");
            }
        } else {
            if (checkboxcheck == '') {
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
                BootstrapDialog.alert("Subject Component Not found for this Subject...!");
            }
        }
    }
</script>

