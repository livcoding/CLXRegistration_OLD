<%-- 
    Document   : supplementartSubjectEntryAdd
    Created on : Sep 25, 2019, 3:29:08 PM
    Author     : malkeet.singh
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">     
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Semester Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select class="form-control" id="semestercode" name="semestercode" onchange="getEnrollmentno();" data-validation="required" data-validation-error-msg="Please select semester code!">
                        <option value="">Select</option>
                        <c:forEach items="${semCode}" var="semCode">
                            <option value="${semCode[0]}~@~${semCode[2]}"><c:out value="${semCode[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Enrollment No.:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select   class="form-control" id="enrollmentno" name="enrollmentno" onchange="getSubjects();" data-validation="required" data-validation-error-msg="Please select enrollment number!" >
                    </select>
                </div>
            </div>
        </div>                
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Subject Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <select class="form-control" id="subjectcode" name="subjectcode" onchange="setDepartmentName(this.value);" data-validation="required" data-validation-error-msg="Please select subject code!" >
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Department:<span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text" readonly="ture" id="department" name="department" class="form-control"/>
                </div>
            </div>
        </div>  
        <div class="row col-lg-12 form-group" >
            <div class="row col-lg-6 form-group">
                <label style="text-transform:capitalize;" class="col-sm-3 col-lg-6 col-xs-12 col-md-3 control-label">Subject Credit:<span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                    <input type="text"readonly="ture" id="subjectcredit" name="subjectcredit" class="form-control" />
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right"> 
                <button type="button" class="btn  btn-info fa fa-question-circle" data-toggle="tooltip" onclick="hint();" style="padding:2px;font-size:25px;color:yellow" title="Hint.."/>
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>

    $(document).ready(function () {
        $('[data-toggle="tooltip"]').tooltip();
    });
    function hint() {
        BootstrapDialog.show({
            title: 'Supplementary Subject Entry Hint:',
            message: (Hint['suppl_subj_entry']),
            buttons: [{
                    label: 'Ok',
                    action: function (dialog) {
                        dialog.close();
                    }
                }]
        });
    }
    $("#semestercode").chosen({width: "100%"});
    $("#enrollmentno").chosen({width: "100%"});
    $("#subjectcode").chosen({width: "100%"});

    function myReset() {
        $("#semestercode").val('').trigger("chosen:updated");
        $("#enrollmentno").empty();
        $("#enrollmentno").val('').trigger("chosen:updated");
        $("#subjectcode").empty();
        $("#subjectcode").val('').trigger("chosen:updated");
    }

    function setDepartmentName(departmentname) {
        var departmentcode = departmentname.split('~@~')[3];
        if (departmentcode != null || departmentcode != '') {
            $("#department").val(departmentcode);
        } else {
            $("#department").val('--');
        }
        var subjectcredit = departmentname.split('~@~')[1];
        if (subjectcredit != null || subjectcredit != '') {
            $("#subjectcredit").val(subjectcredit.split('@')[0]);
        } else {
            $("#subjectcredit").val('--');
        }
    }

    function getEnrollmentno() {
        $("#subjectcode").empty();
        $("#department").val('');
        $("#subjectcredit").val('');
        if ($("#semestercode").val() != '') {
            $.blockUI();
            $.ajax({
                url: "supplementarySubjectEntry/getEnrollmentno",
                dataType: 'json',
                async: true,
                cache: false,
                contentType: false,
                processData: false,
                data: "&registrationid=" + $("#semestercode").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                    setTimeout($.unblockUI, 1000);
                }),
                success: function (data) {
                    $("#enrollmentno").empty();
                    if (data.enrollmentno != null) {
                        var str1 = '';
                        str1 = '<option value="">Select</option>';
                        $.each(data.enrollmentno, function (i, item)
                        {
                            str1 += '<option value="' + item[1] + '~@~' + item[6] + '~@~' + item[7] + '">' + item[2] + ' / ' + item[3] + '</option>'
                        });
                        $("#enrollmentno").append(str1);
                        $("#enrollmentno").trigger("chosen:updated");
                    } else {
                        $("#enrollmentno").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("No Student found...!");
                    }
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
            setTimeout($.unblockUI, 1000);
        }
    }

    function getSubjects() {
        $("#department").val('');
        $("#subjectcredit").val('');
        if ($("#semestercode").val() != '') {
            $.blockUI();
            $.ajax({
                url: "supplementarySubjectEntry/getSubjects",
                dataType: 'json',
                async: true,
                cache: false,
                contentType: false,
                processData: false,
                data: "&registrationid=" + $("#semestercode").val() + "&studentid=" + $("#enrollmentno").val(),
                error: (function (response) {
                    BootstrapDialog.alert('Server Error' + response);
                    setTimeout($.unblockUI, 1000);
                }),
                success: function (data) {
                    $("#subjectcode").empty();
                    if (data.subjectCode != null) {
                        var str1 = '';
                        str1 = '<option value="">Select</option>';
                        $.each(data.subjectCode, function (i, item)
                        {                            //subjectid     credits@failstyno     reasontype     Departmentcode  
                            str1 += '<option value="' + item[1] + '~@~' + item[4] + '~@~' + item[5] + '~@~' + item[6] + '">' + item[2] + ' / ' + item[3] + '</option>'
                        });
                        $("#subjectcode").append(str1);
                        $("#subjectcode").trigger("chosen:updated");
                    } else {
                        $("#subjectcode").val('').trigger("chosen:updated");
                        BootstrapDialog.alert("No More Subject found for this student...!");
                    }
                    setTimeout($.unblockUI, 1000);
                }
            });
        } else {
            BootstrapDialog.alert("Please Select Semester Code first!");
            setTimeout($.unblockUI, 1000);
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
                                    url: "supplementarySubjectEntry/save",
                                    data: $("#ajaxform").serialize(),
                                    datatype: "String",
                                    async: true,
                                    timeout: 5000,
                                    cache: false,
                                    beforeSend: function (xhr, options) {
                                        return true;
                                    },
                                    success: function (data) {
                                        if (data === 'Success') {
                                            toastr.success(Success['save_success'], "Success!!");
                                            //  loadForm("supplementarySubjectEntry/list");                         
                                            if ($("#listenrollmentNo").val() != "") {
                                                getGridData();
                                                getSubjects();
                                            } else {
                                                getSubjects();
                                            }
                                        } else if (data === 'Error') {
                                            toastr.error(Error['error_code'], "Error!!");
                                        } else {
                                            BootstrapDialog.alert(data);
                                        }
                                        setTimeout($.unblockUI, 1000);
                                    },
                                    error: function (response) {
                                        toastr.warning('Something Wrong.', "Warning!!");
                                        setTimeout($.unblockUI, 1000);
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

    function show(i) {
        if ($("#chk" + i).is(":checked")) {
            validateCheckBox();
            $("#lpCriteriaType" + i).val('AND').trigger("chosen:updated");
            document.getElementById("maxCredit" + i).disabled = false;
            document.getElementById("maxLsubjectCredit" + i).disabled = false;
            document.getElementById("maxPsubjectCredit" + i).disabled = false;
            document.getElementById("maxLtheorySubject" + i).disabled = false;
            document.getElementById("maxPlabSubject" + i).disabled = false;
        } else {
            validateCheckBox();
            $("#lpCriteriaType" + i).val('AND').trigger("chosen:updated");
            document.getElementById("maxCredit" + i).disabled = true;
            document.getElementById("maxLsubjectCredit" + i).disabled = true;
            document.getElementById("maxPsubjectCredit" + i).disabled = true;
            document.getElementById("maxLtheorySubject" + i).disabled = true;
            document.getElementById("maxPlabSubject" + i).disabled = true;
        }
    }
</script>