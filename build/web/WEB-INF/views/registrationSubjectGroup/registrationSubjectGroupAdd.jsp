<%-- 
    Document   : RegistrationSubjectGroupAdd
    Created on : 19 JUL : 2019 
    Author     : Malkeet Singh
--%>


<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer" style="margin-top:20px">                 
        <div class="row col-lg-12 form-group" >
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-2 col-xs-12 col-md-3 control-label">Program Code:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <div class="col-md-6">
                    <select class="form-control" id="programid" name="programid"  onchange="getSubjects();getGroupId();resetGroupedSubject();"  data-validation="required" >
                        <option value="">Select</option>
                        <c:forEach items="${prog_list}" var="prog_list">
                            <option value="${prog_list[2]}"><c:out value="${prog_list[0]}"/>/<c:out value="${prog_list[1]}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>           
        <div class="row col-lg-12 form-group" >
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-2 col-xs-12 col-md-3 control-label">Group Name:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <div class="col-md-6">
                    <input type="radio" id="group" name="group" value="exists" checked="true" onclick="existGroup();"> &nbsp; Existing Group:
                    <select class="form-control" name="groupid"  id="groupid1" onchange="getGroupedSubjects();" data-validation="required" >
                        <option value="">Select</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <input type="radio" id="group" name="group" value="new" onclick="newGroup();"> &nbsp; New Group:<input type="text" name="groupid" id="groupid2" onkeyup="this.value = this.value.toUpperCase();"  maxlength="10" data-validation-help="Max Length is 10" placeholder="Enter Group Name" data-validation="required" disabled="true" class="form-control" data-validation-error-msg="You Have Not Enter Correct Value">
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group" >
            <label style="text-transform:capitalize;" class="col-sm-3 col-lg-2 col-xs-12 col-md-3 control-label">Subjects:<span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-9 col-lg-6 col-xs-12 col-md-6">
                <div class="col-md-6"> &nbsp; Existing Subjects:
                    <select class="form-control" id="groupedSubject" name="groupedSubject" multiple="true" data-validation="required" disabled="true">
                        <option value="">Select</option>
                    </select>
                </div>
                <div class="col-md-6">&nbsp; Add New Subjects:
                    <select class="form-control" id="subjectid" name="subjectid" multiple="true" data-validation="required" >
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                <button onclick="myReset()" class="btn btn-warning btn-sm btn-flat" type="reset"> Reset</button>
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>


    $("#programid").chosen({width: "100%"});
    $("#groupid1").chosen({width: "100%"});
    $("#subjectid").chosen({width: "100%"});
    $("#groupedSubject").chosen({width: "100%"});

    function myReset() {
        $('#groupid1').prop('disabled', false).trigger("chosen:updated");
        $("#programid").val('x').trigger("chosen:updated");
        $("#groupid1").empty();
        $("#groupid1").val('x').trigger("chosen:updated");
        $("#subjectid").empty();
        $("#subjectid").val('x').trigger("chosen:updated");
        $("#groupedSubject").empty();
        $("#groupedSubject").val('x').trigger("chosen:updated");
    }
    function resetGroupedSubject() {
        $("#groupedSubject").empty();
        $("#groupedSubject").val('x').trigger("chosen:updated");
    }

    function existGroup() {
        $('#groupid1').prop('disabled', false).trigger("chosen:updated");
        $("#groupid2").val('');
        $('#groupid2').prop('disabled', true);
        $('#subjectid').val('x').trigger("chosen:updated");
        $('#groupedSubject').val('x').trigger("chosen:updated");
        $('#groupedSubject').prop('disabled', true).trigger("chosen:updated");
    }
    function newGroup() {
        $("#groupid2").val('');
        $('#groupid2').prop('disabled', false);
        $("#groupid1").val('x').trigger("chosen:updated");
        $('#groupid1').prop('disabled', true).trigger("chosen:updated");
        $('#subjectid').val('x').trigger("chosen:updated");
        $('#groupedSubject').val('x').trigger("chosen:updated");
        $('#groupedSubject').prop('disabled', true).trigger("chosen:updated");
    }

    function isNumberKey(evt)
    {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;
        return true;
    }

    function getGroupId() {
        $("#groupid1").empty();
        $.ajax({
            url: "registrationSubjectGroup/getGroupId",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "programid=" + $("#programid").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.groupid, function (i, item)
                {
                    str1 += '<option value="' + item + '">' + item + '</option>'
                });
                $("#groupid1").append(str1);
                $("#groupid1").trigger("chosen:updated");
            }
        });
    }

    function getSubjects() {
        $("#subjectid").empty();
        $.ajax({
            url: "registrationSubjectGroup/getSubjects",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "programid=" + $("#programid").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                var str1 = '';
                $.each(data.subjects, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + ' - ' + item[2] + '</option>'
                });
                $("#subjectid").append(str1);
                $("#subjectid").trigger("chosen:updated");
            }
        });
    }

    function getGroupedSubjects() {
        $("#groupedSubject").empty();
        $.ajax({
            url: "registrationSubjectGroup/getGroupedSubjects",
            dataType: 'json',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            data: "groupid=" + $("#groupid1").val() + "&programid=" + $("#programid").val(),
            error: (function (response) {
                alert('Server Error' + response);
            }),
            success: function (data) {
                if (data.groupedSubjects != '' && data.groupedSubjects != null) {
                    var str1 = '';
                    $.each(data.groupedSubjects, function (i, item)
                    {
                        str1 += '<option value="' + item[0] + '" selected="true">' + item[1] + ' - ' + item[2] + '</option>'
                    });
                    $("#groupedSubject").append(str1);
                    $("#groupedSubject").trigger("chosen:updated");
                } else {
                    BootstrapDialog.alert("No Active Subjects found for this Group");
                    $("#groupedSubject").val('').trigger("chosen:updated");
                }
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
                                $.blockUI();
                                $.ajax({
                                    type: "POST",
                                    url: "registrationSubjectGroup/save",
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
                                            loadForm("registrationSubjectGroup/list");
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

</script>