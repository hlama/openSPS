let app=angular.module("app.province",[]);
app.controller("addCtrl",($scope)=>{
/*
    new DataTable('#provincesDataTable',{
        searching: true,
        paging: true,
        info: false,
        "autoWidth": false,
        "columns": [
            { "width": "20%" },
            { "width": "100%" },
            { "width": "100%" },
            { "width": "100%" },
            ]
    });
    */
 new DataTable('#provincesDataTable');
})