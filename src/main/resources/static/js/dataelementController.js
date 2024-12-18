var app=angular.module('app.dataelement',[]);
app.controller("dataelementCtrl",function ($scope,$http,$httpParamSerializer) {

    $scope.changeDataset=()=>{
        datasetlist.forEach(element => {
            if (element.datasetname==$scope.datasetid) {
                $scope.dsid=element.id;
            }
        });
    }
   $scope.sendData=()=>{

    let dt_start=new Date($scope.startdate)
     let dt_end=new Date($scope.enddate)
    let start_date_format=dt_start.getFullYear().toString()+""+
        ((dt_start.getMonth()+1).toString().length==1?"0"+(dt_start.getMonth()+1).toString():(dt_start.getMonth()+1).toString())+
        ((dt_start.getDate()).toString().length==1?"0"+(dt_start.getDate()).toString():""+(dt_start.getDate()).toString())
    
    let end_date_format=dt_end.getFullYear().toString()+""+
        ((dt_end.getMonth()+1).toString().length==1?"0"+(dt_end.getMonth()+1).toString():(dt_end.getMonth()+1).toString())+
        ((dt_end.getDate()).toString().length==1?"0"+(dt_end.getDate()).toString():""+(dt_end.getDate()).toString())
    console.log("Start Date:",start_date_format)
    console.log("End Date:",end_date_format)
    if ($scope.dataelement!='' && $scope.dsid!='' && $scope.dhis2id!='' && start_date_format!='' && end_date_format!='' && $scope.point!='') {
        let pb=document.querySelector("#progressbar2");
        pb.setAttribute('style','visibility: visible;')

        let form={
            "datalelementname":$scope.dataelement,
            "datasetid":$scope.dsid,
            "dhis2id":$scope.dhis2id,
            "dataelementtype":"Number",
            "start_periode":start_date_format.substring(0,6),
            "end_periode":end_date_format.substring(0,6),
            "point":$scope.point
    
        }
        $http({
            url:'/api/dataelement/add',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            method:'post',
            data:$httpParamSerializer(form)
        }).then(function(response) {
            console.log("Succes :",response.data)
            location.reload();
        },function (error) {
            console.error(error)
        })
        
    } else {
        
    }
   
   }

})
app.controller("datatableCtrl",function($scope){
    console.log('IN THE SCOPE:',dataelements)
    let dataelementList=[];
    console.log('START')
    dataelements.forEach(de=>{
       /* let data={
            "dhis2id":de.dhis2id,
            "datalelementname":de.datalelementname,
            "dataset":de.dataset.datasetname,
            "startdate":de.startPeriode,
            "enddate":de.endPeriode,
            "score":de.point
        }
        */
        dataelementList.push([de.dhis2id,de.datalelementname,de.dataset.datasetname,de.startPeriode,de.endPeriode,de.point])
    });
    console.log('END')
    console.log(dataelementList)
    console.log('START DT')
    let dataTable=new DataTable('#dataelementTable', {
        columns: [
            { title: 'DHIS2ID' },
            { title: 'Prestation' },
            { title: 'Formulaire' },
            { title: 'Start Date' },
            { title: 'End date' },
            { title: 'Score' }
        ],
        data: dataelementList
    });
    console.log('END DT')
    dataTable.$('tr').click(function() {
        let dhis2id = dataTable.row(this).data()[0];
       // alert(dhis2id);
        dataelements.forEach(elt=>{
            if (elt.dhis2id==dhis2id){
                console.log('Object found :',elt)
            }
        })
    });
    
})