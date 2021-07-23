
var selectedRowArray = [];
var selectedCheckboxArray = [];
var selectedCheckboxArrayNew = [];
var selectedDelArray = [];
var selectedDelArrayNew = [];
var buttonLen = 0;
var buttons = [];
var tablelist = $('#datatable').DataTable();

function commonButtonMethod(cb) {
    loadSpinner();
    selectedRowArray = [];
    selectedCheckboxArray = [];
    selectedCheckboxArrayNew = [];
    selectedDelArray = [];
    selectedDelArrayNew = [];
    buttonLen = 0;
    buttons = [];
    switch (cb.add.value) {
        case "special":
        {
            if (cb.add.link !== "")
            {
                buttons.push({
                    text: '' + cb.add.name,
                    //titleAttr: 'Add New Record',
                    action: function (e, dt, node, config) {
                        if (typeof (cb.add.displayName) !== "undefined" && cb.add.displayName !== "") {
                            $(".header_name").html(' ' + cb.add.displayName);
                        } else {
                            if (cb.add.name == 'Add') {
                                $(".header_name").html('' + cb.add.name + " " + $("#headerText").text());
                            } else {
                                $(".header_name").html(' ' + cb.add.name);
                            }
                        }
                        var data = cb.add.data.split("~@~");
                        if (data[0] !== "" && data[1] !== "" && data[2] !== "" && data[3] !== "") {
                            $("#contentcontent").removeClass("hide");
                            $("#formcontent").load(cb.add.link + "?data=" + cb.add.data);
                        } else {
                            BootstrapDialog.alert("Please select the required fields.");
                        }
                    }
                });
            }
            break;
        }
        default :
        {
            if (cb.add.link !== "")
            {
                buttons.push({
                    text: '' + cb.add.name,
                    //titleAttr: 'Add New Record',
                    action: function (e, dt, node, config) {
                        if (typeof (cb.add.displayName) !== "undefined" && cb.add.displayName !== "") {
                            $(".header_name").html(' ' + cb.add.displayName);
                        } else {
                            if (cb.add.name == 'Add') {
                                $(".header_name").html('' + cb.add.name + " " + $("#headerText").text());
                            } else {
                                $(".header_name").html(' ' + cb.add.name);
                            }
                        }

                        $("#contentcontent").removeClass("hide");
                        $("#formcontent").load(cb.add.link);
                    }
                });


            }
        }

    }

    switch (cb.edit.value) {
        default :
            if (cb.edit.name !== "") {
                buttons.push({
                    text: ' ' + cb.edit.name,
                    action: function (e, dt, node, config) {
                        if (selectedRowArray.length > 0) {
                            if (selectedRowArray.length > 1)
                            {
                                BootstrapDialog.alert("Select any one row.");
                            } else {
                                if (typeof (cb.edit.displayName) !== "undefined" && cb.edit.displayName !== "") {
                                    $(".header_name").html(' ' + cb.edit.displayName);
                                } else {
                                    if (cb.add.name == 'Edit') {
                                        $(".header_name").html(' ' + cb.edit.name + " " + $("#headerText").text());
                                    } else {
                                        $(".header_name").html(' ' + cb.edit.name);
                                    }
                                }
                                $("#contentcontent").removeClass("hide");
                                $("#formcontent").load(cb.edit.link + "?pk=" + selectedRowArray[0]);
                            }
                        } else {
                            BootstrapDialog.alert("Select any row for edit.");
                        }
                    }
                });
            }

    }

    switch (cb.delete.value) {
        default :
            if (cb.delete.name !== "") {
                buttons.push({
                    text: ' ' + cb.delete.name,
//                    titleAttr: 'Delete Record',
                    action: function (e, dt, node, config) {
                        var ids = '';
//                        if (selectedRowArray.length > 0) {
//                            for (var i = 0; i < selectedRowArray.length; i++)
//                            {
//                                if (i == 0) {
//                                    ids += selectedRowArray[i];
//                                } else {
//                                    ids += ',' + selectedRowArray[i];
//                                }
//                            }
//                        }
                        if (selectedDelArrayNew.length > 0) {
                            for (var i = 0; i < selectedDelArrayNew.length; i++)
                            {
                                if (i == 0) {
                                    ids += encodeURI(selectedDelArrayNew[i]);
                                } else {
                                    ids += ',' + encodeURI(selectedDelArrayNew[i]);
                                }
                            }
                        }
                        if (selectedDelArrayNew.length > 0) {
//                        if (selectedRowArray.length > 0) {
                            BootstrapDialog.confirm("Are you sure you want to " + cb.delete.name.toLowerCase() + "?", function (result) {
                                if (result) {
                                    $.ajax({
                                        type: "POST",
                                        url: cb.delete.link,
                                        data: {
                                            "ids": ids
                                        },
                                        datatype: "json",
                                        async: false,
                                        timeout: 5000,
                                        cache: false,
                                        beforeSend: function (xhr, options) {

                                        },
                                        success: function (data) {
                                            if (data === 'Success') {
//                                                tablelist.row('.success').remove().draw(false);
                                                toastr.success(Success['delete_success'], "Success !!");
//                                                for (var i = 0; i < selectedRowArray.length; )
//                                                for (var i = 0; i < selectedRowArray.length; )
//                                                {
//                                                    tablelist.row("#" + selectedRowArray[i]).remove().draw();
//                                                    selectedRowArray.splice($.inArray(selectedRowArray[i], selectedRowArray), 1);
//                                                }
                                                loadForm(cb.list.link);
                                            } else if (data === 'Error') {
                                                toastr.error('Selected record(s) are in use', "Error !!");
                                            } else {
//                                                toastr.error('Can Not Delete Record, Please try again.', "Error !!");
                                                BootstrapDialog.alert(data);
                                            }
                                        },
                                        error: function (response) {
                                            toastr.warning('<strong><i>Something went wrong!</i></strong><br>Error code: <i>' + response.statusText + '</i><br>Error Message: <i>' + response.status + '.</i>', 'Warning !!');
                                        }
                                    });
                                }
                            });
                        } else {
                            BootstrapDialog.alert("Please select delete icon for  " + cb.delete.name.toLowerCase() + ".");

                        }
                    }
                });
            }

    }
//for creating new button other than existing one
    switch (cb.expression.value)
    {
        case 1:
            buttons.push({
                text: '' + cb.expression.name,
                action: function (e, dt, node, config) {
                    $("#formcontent").removeClass("hide");
                    loadForm();
                }
            });
            break;
        default:
    }

    switch (cb.expression.rnRequired)
    {
        case 1:
            break;
        default:
        {
            buttons.push({
                text: '<i class=\"fa fa-lg fa-refresh\"></i>',
                action: function (e, dt, node, config) {
                    $("#formcontent").removeClass("hide");
                    loadForm(cb.list.link);
                }
            });
        }

    }


    tablelist = $('#datatable').DataTable({
        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
        "order": [],
        "columnDefs": [{
                "searchable": false,
                "orderable": false,
                "targets": 0
            }],
        "oLanguage": {
//            "sSearch": "<span'>Filter records:</span>",
//            "sLengthMenu": "<span'>Display _MENU_ records per page</span>",
//            "sInfo": "<span'>_START_ - _END_ of _TOTAL_</span>"
        },
        "fnDrawCallback": function (oSettings) {
        }
    });



    buttons.push({
        extend: 'excelHtml5',
        mColumns: 'visible',
        text: '<i class=\"fa fa-lg fa-file-excel-o\"></i>',
        exportOptions: {
            columns: ':visible'
        }
    });
    setTimeout(
            function () {
                new $.fn.dataTable.Buttons(tablelist, {
                    buttons: buttons
                });
                $('#button_wrapper').append(tablelist.buttons(0, null).container());

            }, 100);

    $('#datatable tbody').on('click', 'tr', function (event) {
        var id = this.id;
        selectedRowArray = [];
        if (id != "") {
            var index = $.inArray(id, selectedRowArray);
        }

        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
            selectedRowArray.splice(index, 1);
        } else {
            tablelist.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            selectedRowArray.splice(index, 1);
            if (index === -1) {
                selectedRowArray.push(id);
            } else {
                selectedRowArray.splice(index, 1);
            }
        }

//        var id = this.id;
//        if (id != "") {
//            var index = $.inArray(id, selectedRowArray);
//            if (index === -1) {
//                selectedRowArray.push(id);
//            } else {
//                selectedRowArray.splice(index, 1);
//            }
////            $(this).toggleClass('active');
//        }
    });

    $('#datatable tbody').on('click', 'td', function (event) {
        var id = this.id;
        selectedDelArrayNew = [];
        if (id != "") {
            $('td input[type=checkbox]').each(function () {
                if (this.checked) {
                    var value = this.value;
                    var index = $.inArray(value, selectedDelArray);
                    if (value !== "" && value !== "undefined") {
                        selectedDelArray.push(value);
                    }
                    selectedDelArrayNew = selectedDelArray.filter(function (itm, i, a) {
                        return i == a.indexOf(itm);
                    });
                }
//                else {
//                    var value = this.value;
//                    var index = $.inArray(value, selectedDelArrayNew);
//                    if (value !== "" && value !== "undefined") {
//                        if (index !== -1) {
//                            selectedDelArrayNew.splice(index, 1);
//                        }
//                    }
//                }
                else {
                    var value = this.value;
                    var index = $.inArray(value, selectedDelArrayNew);
                    if (value !== "" && value !== "undefined") {
                        if (index !== -1) {
                            if (value === selectedDelArrayNew[0]) {
//                            selectedDelArrayNew.splice(index, 1);
                                selectedDelArrayNew = [];
                                selectedDelArray = [];
                            }
                        }
                    }
                }
            });
        }
    });

    /* Select on the basis of check box on list page*/
    $("#datatable").on('change', 'input[type="checkbox"]', function (event) {
        $('td input[type=checkbox]').each(function () {
            if (this.checked) {
                var value = this.value;
                var index = $.inArray(value, selectedCheckboxArray);
                if (value !== "" && value !== "undefined") {
                    selectedCheckboxArray.push(value);
                }
//                selectedCheckboxArrayNew = selectedCheckboxArray.unique();
                selectedCheckboxArrayNew = selectedCheckboxArray.filter(function (itm, i, a) {
                    return i == a.indexOf(itm);
                });
            } else {
                var value = this.value;
                var index = $.inArray(value, selectedCheckboxArrayNew);
                if (value !== "" && value !== "undefined") {
                    if (index !== -1) {
                        selectedCheckboxArrayNew.splice(index, 1);
                    }
                }
            }
        });
    });


    /*  Search filter table reset*/
    $("#datatable tfoot input").on('keyup change', function () {
        tablelist.column($(this).parent().index() + ':visible')
                .search(this.value)
                .draw();

    });

//      $('#datatable tfoot .search-sort').each( function () {
//        var title = $(this).text();
//        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
//    } );

//  tablelist.columns('.search-sort').every( function () {
//        var that = this;
//        $( 'input', this.footer() ).on( 'keyup change', function () {
////          if ( that.search() !== this.value ) {
//                that
//                    .search( this.value )
//                    .draw();
////            }
//        } );
//    } );
//    $('#datatable tfoot td').each(function () {
//        var title = $(this).text();
//        $(this).html('<input type="text" placeholder="Search ' + title + '" />');
//    });
//
//    tablelist.columns().every(function () {
//        var that = this;
//        $('input', this.footer()).on('keyup change', function () {
//            that.search(this.value).draw();
//        });
//    });

}
function loadSpinner() {
    $("#formcontent").html('<div class="text-center row" style="margin-top:30px;"><i class="fa fa-refresh fa-spin fa-3x"></></div>');
}


