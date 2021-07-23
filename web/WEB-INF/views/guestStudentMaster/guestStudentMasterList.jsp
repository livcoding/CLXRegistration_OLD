
<%@include file="../mainjstl.jsp"%><div class="row col-lg-12 form-group"></div>
<div class="row col-lg-12 form-group">
    <div class="row col-lg-6 form-group" >
        <label style="text-transform: capitalize;" class="col-sm-3 col-lg-6 col-xs-3 col-md-3 control-label">From College/Institute:
            <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
        <div class="col-sm-6 col-lg-6 col-xs-6 col-md-6">
            <select class="form-control" id="frominstititename" onchange="loadData();" name="frominstititename" data-validation="required" data-live-search="true" data-validation-error-msg="You have not select any Institute Name">
                <option value="">Select</option>
                <c:forEach items="${institute}" var="list">
                    <option value="${list}"><c:out value="${list}"/></option>
                </c:forEach> 
            </select>
        </div>
    </div>
    <!--    <div class="row col-lg-12">
            <div style="margin-top: -45px;margin-right: 15px;" class="form-group pull-right col-sm-3 col-lg-6">
                <a href="javascript:loadData();" class="btn btn-success btn-sm btn-flat">Load</a>
            </div>
        </div>-->
</div>
<div class="row col-lg-12 form-group"></div>
<div class="row col-lg-12 form-group"></div>
<div class="col-lg-12" style="margin-top:10px;">
    <div id="button_wrapper" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
    <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
        <thead id="header">
            <tr class="" >
                <th style="display:none"></th>
                <th >Sl No.</th>
                <th>Enrollment No.</th>
                <th>Name</th>
                <th>Program Code</th>
                <th>Branch Code</th>
                <th>Section Code</th>
                <th>Sub Section Code</th>
                <th>DOB</th>
                <th>Gender</th>
                <th>Mobile No.</th>
                <th>Email Id</th>
                <th>Parent Mobile No.</th>
                <th>Parent Email Id</th></tr>
        </thead> 
    </table>
</div>

<script>
    $("#frominstititename").chosen();

    var cb = {
        add: {
            link: "guestStudentMaster/add",
            name: "Add",
            value: ""
        },
        edit: {
            link: "guestStudentMaster/edit",
            name: "Edit",
            value: ""
        },
        delete: {
            link: "",
            name: "",
            value: ""
        },
        list: {
            link: "guestStudentMaster/list"
        },
        expression: {
            link: "",
            name: "",
            value: ""
        }
    }
    commonButtonMethod(cb);

    function loadData() {
        $('#datatable').DataTable().clear().draw();
        if ($("#frominstititename").val() != '') {
            $.blockUI();
            $.ajax({
                url: "guestStudentMaster/getAllStudentMaster",
                dataType: 'json',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                data: "fromInstituteId=" + $("#frominstititename").val(),
                error: (function (response) {
                    alert('Server Error' + response);
                    setTimeout($.unblockUI, 1000);
                }),
                success: function (data) {
                    if (data.studentData != null && data.studentData != '') {

                        $.each(data.studentData, function (i, item)
                        {
                            var str1 = '';

                            var str3 = '';
                            if (item[3] == null) {
                                str3 = '--';
                            } else {
                                str3 = item[3];
                            }
                            var str4 = '';
                            if (item[4] == null) {
                                str4 = '--';
                            } else {
                                str4 = item[4];
                            }
                            var str5 = '';
                            if (item[5] == null) {
                                str5 = '--';
                            } else {
                                str5 = item[5];
                            }
                            var str6 = '';
                            if (item[6] == null) {
                                str6 = '--';
                            } else {
                                str6 = item[6];
                            }
                            var str9 = '';
                            if (item[9] == null) {
                                str9 = '--';
                            } else {
                                str9 = item[9];
                            }
                            var str10 = '';
                            if (item[10] == null) {
                                str10 = '--';
                            } else {
                                str10 = item[10];
                            }
                            var str11 = '';
                            if (item[11] == null) {
                                str11 = '--';
                            } else {
                                str11 = item[11];
                            }
                            var str12 = '';
                            if (item[12] == null) {
                                str12 = '--';
                            } else {
                                str12 = item[12];
                            }
                            str1 += '<tr id="' + item[0] + '">';
                            str1 += '  <td style="display:none"> </td>';
                            str1 += '  <td>' + (i + 1) + '</td>';
                            str1 += '  <td>' + item[1] + '</td>'; //Enrollment No.
                            str1 += '  <td>' + item[2] + '</td>';//name
                            str1 += '  <td>' + str3 + '</td>';//Program Code
                            str1 += '  <td>' + str4 + '</td>';//Branch Code
                            str1 += '  <td>' + str5 + '</td>';//Section Code
                            str1 += '  <td>' + str6 + '</td>';// Sub Section Code   
                            var todayTime = new Date(item[7]);
                            var month = todayTime.getMonth() + 1;
                            var day = todayTime.getDate();
                            var year = todayTime.getFullYear();
                            var d = day + "/" + month + "/" + year;
                            str1 += '  <td>' + d + '</td>';//DOB
                            str1 += '  <td>' + item[8] + '</td>';//Gender
                            str1 += '  <td>' + str9 + '</td>';//Mobile No
                            str1 += '  <td>' + str11 + '</td>';//EmailId
                            str1 += '  <td>' + str10 + '</td>';//ParentMobile No
                            str1 += '  <td>' + str12 + '</td>';//Parent Email Id    
                            str1 += '  </tr>';
                            tablelist.row.add($(str1)).draw();
                        });
                        setTimeout($.unblockUI, 1000);
                    } else {
                        BootstrapDialog.alert("Branch Not Found For This Program,Please Select Another Program...");
                    }
                }
            });
            setTimeout($.unblockUI, 1000);
        } else {
            BootstrapDialog.alert("Please select the filters !!");
        }
    }
</script>

