let app=angular.module("app.fosa",[]);
app.controller("addCtrl",($scope)=>{
/*
    new DataTable('#fosaDataTable',{
        searching: true,
        paging: true,
        info: false,
        "autoWidth": false,
        "columns": [
            { "width": "20%" },
            { "width": "100%" },
            { "width": "100%" },
            { "width": "100%" }
            ]
    });
    */

    new DataTable('#fosaDataTable');
    console.log('Angularjs')

})