<%-- 
    Document   : academicYearWise
    Created on : Feb 12, 2019, 4:06:14 PM
    Author     : ashutosh1.kumar
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../mainjstl.jsp"%>
<form id="ajaxform2" class="form-horizontal">
    <div class="col-lg-12" style="margin-top:10px;">            
        <div class="row col-lg-12 form-group" >
            <label style="text-transform: capitalize;" class="col-sm-3 col-lg-3 col-xs-3 col-md-3 control-label">Activity Type:
                <span style="font-size:19px;font-weight:bold;" class="text-danger"> *</span></label>
            <div class="col-sm-6 col-lg-5 col-xs-6 col-md-6">
                <select class="form-control" id="activityTypeAcd" name="activityTypeAcd" data-validation="required" data-live-search="true" data-validation-error-msg="Please select activity type!">               
                    <option value="">Select</option>
                    <c:forEach items="${activity}" var="activity">
                        <option value="${activity[0]}"><c:out value="${activity[1]}"/></option>
                    </c:forEach>
                </select>
            </div>   
        </div>
        <div class="col-lg-12" style="margin-top:10px;">          
            <div class="col-lg-12 form-group">              
                <div style="margin-right: 15px;" class="form-group pull-right">
                </div>
            </div>
            <div id="specialCase_Button1" class="row pull-right" style="margin-right: 0px;margin-bottom: 5px;"></div>
            <table id="datatable" class="box table table-hover table-condensed table-bordered" cellspacing="0">
                <thead id="header">
                    <tr class="" >
                        <th style="display:none"></th>
                        <th>Sl.no.</th>
                        <th>Enrollment No.</th>
                        <th>Name</th>
                        <th>Program(Branch)</th>
                        <th>STY No.</th>
                        <th>Registration Permission</th>
                        <th>Remarks</th>
                    </tr>
                </thead>  
                <tbody>
                </tbody>
            </table>
        </div>
        <input type="hidden"   name="countvalac" id="countvalac" value=""/>
        <div class="col-lg-12 form-group">              
            <div style="margin-top: 10px;margin-right: 15px;" class="form-group pull-right">
                <a href="javascript:saveAcadWiseRecord();" class=" btn buttons-html5 btn-success">Save</a>
            </div>
        </div>
    </div> 
</form>

<script>
    $("#activityTypeAcd").chosen({width: "100%"});

    var btns1 = [];
    table2 = $('#datatable').DataTable({
        "lengthMenu": [[-1], ["All"]],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }]
    });
    btns1.push({
        extend: 'excel',
        // mColumns: 'visible',
        text: '<i class=\"fa fa-lg fa-file-excel-o\"></i>',
        exportOptions: {
//            columns: ':visible'
            modifier: {
                page: 'all'
            }
        }
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(table2, {
                    buttons: btns1
                });
                $('#specialCase_Button1').append(table2.buttons(0, null).container());
            }, 100);


    function loadAcdWiseNoDuesData() {
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        var programid = $("#programid").val();
        if (regCode != '') {
            if (acadYear != '') {
                if (programid != '') {
                    $('#datatable').DataTable().clear().draw();
                    $.blockUI();
                    $.ajax({
                        url: "registrationNoDuesManagement/getAcademicYearWiseGridData",
                        dataType: 'json',
                        async: true,
                        timeout: 10000,
                        cache: false,
                        contentType: false,
                        processData: false,
                        data: "regCode=" + regCode + "&acadYear=" + acadYear + "&programid=" + programid,
                        error: (function (response) {
                            alert('Server Error' + stringify(response));
                            setTimeout($.unblockUI, 1000);
                        }),
                        success: function (data) {
                            if (data.acadWiseData != null) {
                                var countt = 0;
                                $.each(data.acadWiseData, function (i, item)
                                {
                                    countt++;
                                    var str2 = '';
                                    var str = '';
                                    var str1 = '';
                                    str += '<input type="text" name="bulkremarks' + i + '" id="bulkremarks' + i + '"  class="form-control" placeholder="Enter Remarks" />';
                                    str2 += '<input type="radio" id="regstatus' + i + '" name="regstatus' + i + '"  checked="true" value="Y"/>Allow &nbsp;<input type="radio" id="regstatus' + i + '" name="regstatus' + i + '" value="N"/>Not Allow ';
                                    str1 += ' <tr id="' + item[2] + '">';
                                    str1 += ' <td style="display:none"><input type="hidden" class="case" checked="true" name="chk2' + i + '" id="chk2' + i + '" value="' + item[2] + '"/>';
                                    str1 += '  </td>';
                                    str1 += ' <td>' + countt + '</td>';
                                    str1 += ' <td>' + item[0] + '</td>';
                                    str1 += ' <td>' + item[1] + '</td>';
                                    str1 += ' <td>' + item[4] + '(' + item[5] + ')' + '</td>';
                                    str1 += ' <td>' + item[3] + '</td>';
                                    str1 += ' <td>' + str2 + '</td>';
                                    str1 += ' <td>' + str + '</td>';
                                    str1 += ' </tr>';
                                    table2.row.add($(str1)).draw();
                                    $("#countvalac").val(countt);
                                });
                            } else {
                                BootstrapDialog.alert("Data Not Found For This Filter,Please Select Another ...");
                            }
                            setTimeout($.unblockUI, 1000);
                        }
                    });
                }
            }
        }
    }

    function saveAcadWiseRecord() {
        var regCode = $("#regCode").val();
        var acadYear = $("#acadYear").val();
        var data = $("#countvalac").val();
        var programid = $("#programid").val();
        if (regCode != '') {
            if (acadYear != '') {
                if (programid != '') {
                    if (data != '') {
                        setFormIdAndUrl('ajaxform2', 'registrationNoDuesManagement/saveAcadWiseRecord', 2);
                    } else {
                        BootstrapDialog.alert("Please Load Students...");
                    }
                } else {
                    BootstrapDialog.alert("Please Select Program Code...");
                }
            } else {
                BootstrapDialog.alert("Please Select Academic Year ...");
            }
        } else {
            BootstrapDialog.alert("Please Select Semester Code...");
        }
    }

</script>
