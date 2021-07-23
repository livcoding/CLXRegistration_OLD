<%-- 
    Document   : facultySubjectTaggingAdd
    Created on : Jan 22, 2019, 3:52:39 PM
    Author     : ankit.kumar
--%>

<%@include file="../mainjstl.jsp"%>
<form method="POST" id="ajaxform" class="form-horizontal">
    <div class="row col-lg-12 form-group" id="divContainer">
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger"></span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <input type="text" id="reg_cod" name="reg_cod" value="${reg_code}" readonly="true"/>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Academic Year:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="acad_year" name="acad_year" data-live-search="true" data-validation="required" onchange="getSemesterCode();">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize    ;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Semester Number:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="sem_id" name="sem_id" data-live-search="true" data-validation="required" onchange="getProgramCode();">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize    ;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Program Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="pr_id" name="pr_id" data-live-search="true" data-validation="required"  onchange="getSectionCode();">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12 form-group">
            <div class="row col-lg-6 form-group">
                <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">Section Code:
                    <span style="font-size:19px;font-weight:bold;" class="text-danger">*</span></label>
                <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
                    <select class="form-control" id="sec_id" name="sec_id" data-live-search="true" data-validation="required" multiple="true">               
                        <option value="">Select</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="row col-lg-12">
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveData();" class="btn btn-success btn-sm btn-flat">Save</a>
                <button class="btn btn-warning btn-sm btn-flat" id="erase" type="reset">Reset</button> 
                <a href="javascript:closePage();" class="btn btn-danger btn-sm btn-flat">Cancel</a>
            </div>
        </div>
    </div>
</form>
<script>
    $("#reg_id").chosen();
    $("#acad_year").chosen();
    $("#pr_id").chosen();
    $("#sec_id").chosen();
    $("#sem_id").chosen();


    function getAcadYear() {
        $("#acad_year").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getAcadyear",
            data: '&regId=' + $("#reg_id").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.acadyear, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[0] + '</option>'
                });
                $("#acad_year").append(str1);
                $("#acad_year").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
    function getProgramCode() {
        $("#pr_id").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getProgramCode",
            data: '&regId=' + $("#reg_id").val() + '&acadYear=' + $("#acad_year").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.pr_code, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#pr_id").append(str1);
                $("#pr_id").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }

    function getSectionCode() {
        $("#sec_id").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getSectionCode",
            data: '&regId=' + $("#reg_id").val() + '&acadYear=' + $("#acad_year").val() + '&programId=' + $("#pr_id").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                $.each(data.br_code, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#sec_id").append(str1);
                $("#sec_id").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
    function getSemesterCode() {
        $("#sem_id").empty();
        $.ajax({
            type: "POST",
            url: "facultySubjectTagging/getSemesterCode",
            data: '&regId=' + $("#reg_id").val() + '&acadYear=' + $("#acad_year").val() + '&programId=' + $("#pr_id").val(),
            dataType: "json",
            async: false,
            timeout: 5000,
            cache: false,
            beforeSend: function (xhr, options) {
                return true;
            },
            success: function (data) {
                var str1 = '';
                var str1 = '<option value="">Select</option>';
                $.each(data.sem_code, function (i, item)
                {
                    str1 += '<option value="' + item[0] + '">' + item[1] + '</option>'
                });
                $("#sem_id").append(str1);
                $("#sem_id").trigger("chosen:updated");
            },
            error: function (response) {
                toastr.warning('Something Wrong.', "Warning!!");
            }
        });
    }
</script>