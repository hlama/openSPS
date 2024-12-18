var app=angular.module('app.dataset',[]);
app.controller("datasetCtrl",function ($scope,$http,$httpParamSerializer) {
new DataTable('#datasetTable')
    $scope.status=0
console.log("ORG :",orgunit)
$scope.checked=function() {
    console.log("Statut is :",$scope.active)
    $scope.status=($scope.active==true?1:0)
}
document.querySelector("#typeorgunitid").onchange=function(){
    message_error.setAttribute('style','visibility:hidden')
    orgunit.forEach(element => {
        if (element.libelle==document.querySelector("#typeorgunitid").value) {
            $scope.idtype=element.id;
            console.log('Id found :',$scope.idtype)
        }
    });
}
document.querySelector("#periode").onchange=function(){
    let periode=document.querySelector("#periode").value.toString().trim()
    if(periode=="Mensuelle"){
        $scope.temporality="MONTHLY"
    }else{
        $scope.temporality="QUATERLY"
    }
    console.log($scope.temporality)
}

$scope.add=function(){

    let message_error=document.querySelector("#message_error")
    let btn_sender=document.querySelector("#btn_sender")
    let message_loading=document.querySelector("#message_loading")
    let pbr=document.querySelector("#progressbar2")
    if ($scope.datasetname!='' && $scope.periode!='' && $scope.idtype!='') {
        console.log('ID TYPE ORGUNIT:',$scope.idtype)
        btn_sender.disabled=true;
        message_loading.setAttribute('style',"visibility:visible");
        pbr.setAttribute('style','visibility:visible');
        $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
        $http({
            url:'/api/dataset/add',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            method:'post',
            data:$httpParamSerializer({
                "datasetname":$scope.datasetname,
                "periode":$scope.temporality,
                "typeorgunitid":$scope.idtype,
                "statut":$scope.status
            })
        }).then(function(response) {
            console.log("Succes :",response.data)
            location.reload();
        },function (error) {
            console.error(error)
        })
    } else {
        message_error.setAttribute('style','visibility:visible;font-size: 1em; text-align: center;padding: .5em;width: 100%; color:#990000;')
    }
   
    $scope.statusChange=function(){
        console.log(obj)
    }
    
}

})
