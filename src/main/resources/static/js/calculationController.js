var app=angular.module('app.calculation',[]);
app.controller("calculationCtrl",function ($scope,$http,$httpParamSerializer) {
console.log("Calculation list:",calculations)
document.querySelector("#datasetid").onchange=function(){
    console.log("ID:", document.querySelector("#datasetid").value)
}
  let coastingList=[{}]
  let dataTable=new DataTable('#calculationDataTable', {
                    "data" : calculations,
                    "columns" : [
                       {"title":"ID","data":"id"},
                       {"title":"TITRE","data":"name"},
                       {"title":"START DATE","data":"startdate"},
                       {"title":"END DATE","data":"enddate"},
                       {"title":"DATE CREATION","data":"datecreated"},
                       {"title":"FORMULE","data":"formula"}
              ],
              paging: true,
              searching: true,
              "bDestroy": true,
                "rowCallback": function( row, data, index ) {
                        var allData = this.api().column(4).data().toArray();
                        $('td:eq(5)', row).css('color', '#800080');
                        $('td:eq(5)', row).css('font-family', "Consolas,'courier new'");
                        $('td:eq(5)', row).css('font-weight', "bold");
                      }
          });
         // dataTable.destroy()
        $('#calculationDataTable').on('click', 'td', function () {
              var tr = $(this).closest('tr');
              var row = dataTable.row( tr ).data();           // returns undefined
              console.log(row);
              console.log(dataTable.row( tr ).column(2));
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
            document.querySelector("#datasetid").value.toString().trim()!=''
             && document.querySelector("#startdate").value.toString().trim()!=''
             && document.querySelector("#enddate").value.toString().trim()!=''
             && document.querySelector("#formula").value!=''
        ){
        //console.log("GLOBAL:",datasetGlobal)
        //console.log(document.querySelector("#datasetid").value.toString().trim())
        console.log(
            "FORM NAME:",document.querySelector("#datasetid").options[document.querySelector("#datasetid").selectedIndex].text

        );

         let form={
                    "name":document.querySelector("#datasetid").options[document.querySelector("#datasetid").selectedIndex].text,
                    "formula":document.querySelector("#formula").value.toString().trim(),
                    "startdate":document.querySelector("#startdate").value.toString().trim().replaceAll("-",""),
                    "enddate":document.querySelector("#enddate").value.toString().trim().replaceAll("-",""),
                    "datasetid":parseInt(document.querySelector("#datasetid").value),
                    "status":1

         }
         console.log("Object Post:",form)
         let pb=document.querySelector("#progressbar2");
             pb.setAttribute('style','visibility: visible;')
               $http({
                        url:'/api/engine/add',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        method:'post',
                        data:$httpParamSerializer(form)
                    }).then(function(response) {
                        console.log("Succes :",response.data)
                        setTimeout(() => { window.location.href=location.href;  }, 2000);

                    },function (error) {
                        (function() {
                                            'use strict';
                                            window['counter'] = 0;
                                            var snackbarContainer = document.querySelector('#demo-toast-example');
                                            var showToastButton = document.querySelector('#demo-show-toast');
                                            showToastButton.addEventListener('click', function() {
                                              'use strict';
                                              var data = {message:"Une erreur s'est produite, veuillez verifier le formzt des donnees"};
                                              snackbarContainer.MaterialSnackbar.showSnackbar(data);

                                            });
                                          }());
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