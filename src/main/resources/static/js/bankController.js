var app=angular.module('app.bank',[]);
app.controller("bankCtrl",function ($scope,$http,$httpParamSerializer) {
console.log("AngularJS")
let bankList=[];
banks.forEach(elt=>{
    console.log("Object:",elt)
    bankList.push([elt.id,elt.name,elt.codename,elt.phone])
})
  let dataTable=new DataTable('#bankDataTable', {
                    "data" : banks,
                    "columns" : [
                        { "data" : "id",title:'id'},
                        { "data" : "codename",title:'codename'},
                        { "data" : "name",'title':'name'},
                        { "data" : "phone",'title':'phone'}
              ],
              paging: true,
              searching: true,
              "bDestroy": true
          });
         // dataTable.destroy()
        $('#bankDataTable').on('click', 'td', function () {
              var tr = $(this).closest('tr');
              var row = dataTable.row( tr ).data();           // returns undefined
              console.log(row);
            })
  //dataTable.destroy();
/*
      console.log("DEBUT BOUCLE")
      let bankList=[]
      banks.forEach(e=>{
            bankList.push([e.id,e.codename,e.name,e.phone])
      })
      console.log("FIN BOUCLE")
      console.log("Bank list Array :",bankList)
      */

/*
      let dataTable=new DataTable('#bankDataTable', {
              columns: [
                  { title: 'id' },
                  { title: 'Code Operateur' },
                  { title: 'Operateur Banque' },
                  { title: 'Phone' }
              ],
              data: bankList
          });

          */
    $scope.PostData=()=>{
    if(
            $scope.bankname.toString().trim()!='' && $scope.codebank.toString().trim() && $scope.phonebank.toString().trim()!=''
        ){
         let pb=document.querySelector("#progressbar2");
             pb.setAttribute('style','visibility: visible;')
            let form={
                        "name":$scope.bankname.toString().trim(),
                        "codename":$scope.codebank.toString().trim(),
                        "phone":$scope.phonebank.toString().trim()
                }
               $http({
                        url:'/api/bank/add',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        method:'post',
                        data:$httpParamSerializer(form)
                    }).then(function(response) {
                        console.log("Succes :",response.data)
                        setTimeout(() => { location.reload();  }, 2000);
                        location.reload();
                    },function (error) {
                        console.error(error)
                    })
        }else{
            (function() {
                    'use strict';
                    window['counter'] = 0;
                    var snackbarContainer = document.querySelector('#demo-toast-example');
                    var showToastButton = document.querySelector('#demo-show-toast');
                    showToastButton.addEventListener('click', function() {
                      'use strict';
                      var data = {message:"SVP, veillez remplir les champs vides..."};
                      snackbarContainer.MaterialSnackbar.showSnackbar(data);

                    });
                  }());
        }

    }

})