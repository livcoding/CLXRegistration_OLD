//$('#itemList').DataTable({
//        "order": [[0, "desc"]],
//        "columnDefs": [
//            {"orderable": false, "targets": 0}
//        ],
//        "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]],
//    });
//    var table = $('#itemList').DataTable();
//var selectedRowArray=[];
//$('#itemList tbody').on('click', 'tr', function (event) {
//        var id = this.id;
//        if (id != "") {
//            var index = $.inArray(id, selectedRowArray);
//            if (index === -1) {
//                selectedRowArray.push(id);
//            } else {
//                selectedRowArray.splice(index, 1);
//            }
//            $(this).toggleClass('active');
//        }
//        alert(selectedRowArray);
//    });
