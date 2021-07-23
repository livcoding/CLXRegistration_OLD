<%-- 
    Document   : studentNRSubjectsAdd
    Created on : Sep 21, 2019
    Author     : malkeet.singh
--%>

<%@include file="../mainjstl.jsp"%>
<form id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Student Name:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <input type="" id="studentname" value="${studentname}" readonly="true"  class="form-control">
            </div>
        </div>
    </div>
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">NR Of Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="nrsemcode" name="nrsemcode" data-validation="required"  data-live-search="true" data-validation-error-msg="Please select nr of semester code!">
                    <option value="">Select</option>
                    <c:forEach items="${semCode}" var="semCode">
                        <option value="${semCode[0]}"><c:out value="${semCode[1]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Type:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="subjecttype" name="subjecttype" data-validation="required" onchange="getSubject();" data-live-search="true" data-validation-error-msg="Please select subject type!">
                    <option value="">Select</option>
                    <c:forEach items="${subjectType}" var="subjectType">
                        <option value="${subjectType[0]}"><c:out value="${subjectType[1]} / ${subjectType[2]}"/></option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div> 
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <select class="form-control" id="subjectcode" name="subjectcode" onchange="setSubValue(this.value);" data-validation="required" data-live-search="true" data-validation-error-msg="Please select subject code!">
                    <option value="">Select</option>
                </select>
            </div>
        </div>
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Name:<span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <input type="text" name="subjectname" id="subjectname" readonly="true"  class="form-control">
            </div>
        </div>
    </div> 
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Basket:<span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <input type="text" name="subjectbasket" id="subjectbasket" readonly="true"  class="form-control">
            </div>
        </div>
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Subject Credit:<span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <input type="text" name="subjectcredit" id="subjectcredit" readonly="true"  class="form-control">
            </div>
        </div>
    </div> 
    <div class="row col-lg-12 form-group" >
        <div class="row col-lg-6 form-group">
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-5 col-xs-12 col-md-3 control-label">Reason:<span style="font-size:19px;font-weight:bold;" class="text-danger"> </span></label>
            <div class="col-sm-9 col-lg-7 col-xs-12 col-md-6">
                <input type="text" name="reason" id="reason" maxlength="160" data-validation-help="Max Length is 160"  placeholder="Enter Reason" class="form-control" data-validation-error-msg="Please enter reason!">
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
        <p style="color:green"><b>NR</b> : Not Register Subjects during Course Registration</p>
    </div>
    <div class="col-lg-12 form-group">              
        <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
            <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat"> Save</a>
            <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" id="erase" type="reset"> Reset</button> 
            <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat"> Cancel</a>
        </div>
    </div>
    <input type="hidden" name="basketid" id="basketid" >
    <input type="hidden" id="stuid" name="stuid" value="${studentid}">
    <input type="hidden" id="programid" name="programid" >
    <input type="hidden" id="sectionid" name="sectionid" >
    <input type="hidden" id="academicyear"name="academicyear" >
    <input type="hidden" id="stynumber" name="stynumber" >
</form>

<script>
    $("#nrsemcode").chosen();
    $("#subjecttype").chosen();
    $("#subjectcode").chosen();

    function myReset() {
        $("#nrsemcode").val('').trigger("chosen:updated");
        $("#subjecttype").val('').trigger("chosen:updated");
        $("#subjectcode").empty();
        $("#subjectcode").val('').trigger("chosen:updated");
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
    function getSubject() {
        $("#subjectcode").empty();
        $("#subjectcode").val('x').trigger("chosen:updated");
        $.ajax({
            url: "studentNRSubjects/getSubjectCode",
            dataType: 'json',
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            data: "studentid=" + $("#stuid").val() + "&registrationid=" + $("#nrsemcode").val() + "&subjecttype=" + $("#subjecttype").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                if (data.subCode != '') {
                    $.each(data.subCode, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '~@~' + item[2] + '~@~' + item[3] + '~@~' + item[4] + '~@~' + item[5] + '~@~' + item[6] + '~@~' + item[7] + '~@~' + item[8] + '~@~' + item[9] + '">' + item[1] + ' / ' + item[2] + '</option>'
                    });
                    $("#subjectcode").append(str1);
                    $("#subjectcode").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("Subject not found..");
                }
            }
        });
    }
    function setSubValue(value) {
        var v = value.split('~@~');
        $("#subjectname").val(v[1]);
        if (v[2] != 'null') {
            $("#subjectcredit").val(v[2]);
        } else {
            $("#subjectcredit").val('--');
        }
        $("#subjectbasket").val(v[3]);
        $("#basketid").val(v[4]);
        $("#programid").val(v[5]);
        $("#sectionid").val(v[6]);
        $("#academicyear").val(v[7]);
        $("#stynumber").val(v[8]);
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
                                    url: "studentNRSubjects/save",
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
                                            toastr.success('Record Successfully Saved.', "Success!!");
//                                            loadForm("studentNRSubjects/list");
                                            closePage();
                                            getGridDate();
                                        } else if (data === 'Error') {
                                            toastr.error('Form Submission Failed.', "Error!!");
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


