var app=angular.module('app.coasting',[]);
app.controller("coastingCtrl",function ($scope,$http,$httpParamSerializer) {
let datasetGlobal={}
let getDataSet=function(id){
console.log("PARAMETER ID:",id)
    datasets.forEach(e=>{
        if (e.id==id){
        console.log("PARAMETER ID FOUND:",e)
            datasetGlobal=e;
        }
    })
}
let coastingList=[]
let fixed=false
var currentCoasting={}
coastings.forEach((elt)=>{
    coastingList.push({
        "id":elt.id,
        "label":elt.label+" -Coasting",
        "datasetid":elt.datasetid.id,
        "dataset":elt.datasetid.datasetname,
        "categoryid":elt.typeOrgunit.id,
        "category":elt.typeOrgunit.libelle,
        "amount":elt.amount,
        "startPeriode":elt.startPeriode,
        "endPeriode":elt.endPeriode
    })
})
console.log("DATASETS:",datasets)
console.log("COASTINGS:",coastings)
console.log("COASTINGLIST:",coastingList)
document.querySelector("#datasetid").onchange=function(){
    console.log("Id value:",document.querySelector("#datasetid").value)
    document.querySelector("#startdate").value="";
    document.querySelector("#enddate").value="";
    document.querySelector("#amount").value="";
    coastingList.forEach((e)=>{

        if(e.datasetid==document.querySelector("#datasetid").value){

            let startDateFormat= e.startPeriode.toString().substring(0,4)+"-"+
                                            e.startPeriode.toString().substring(4,6)+"-"+
                                            e.startPeriode.toString().substring(6,8)
            let endDateFormat= e.endPeriode.toString().substring(0,4)+"-"+
                                                        e.endPeriode.toString().substring(4,6)+"-"+
                                                        e.endPeriode.toString().substring(6,8)
             document.querySelector("#startdate").value=startDateFormat;
             document.querySelector("#enddate").value=endDateFormat;
             document.querySelector("#amount").value=e.amount;
             //document.querySelector("#orgunitypeid").value=e.categoryid;




        }
    })

}
let bankList=[];
banks.forEach(elt=>{
    console.log("Object:",elt)
    bankList.push([elt.id,elt.name,elt.codename,elt.phone])
})
console.log("Coasting Tab:",coastingList)
  let dataTable=new DataTable('#coastingDataTable', {
                    "data" : coastingList,
                    "columns" : [
                       {"title":"ID","data":"id"},
                       {"title":"TITRE","data":"label"},
                       {"title":"FORMULAIRE","data":"dataset"},
                       {"title":"CATEGORIE","data":"category"},
                       {"title":"MONTANT (USD)","data":"amount"}
              ],
              paging: true,
              searching: true,
              "bDestroy": true
          });
         // dataTable.destroy()
        $('#coastingDataTable').on('click', 'td', function () {
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
            document.querySelector("#datasetid").value.toString().trim()!=''
             && document.querySelector("#startdate").value.toString().trim()!=''
             && document.querySelector("#enddate").value.toString().trim()!=''
             && document.querySelector("#amount").value!=''
        ){
        getDataSet(document.querySelector("#datasetid").value)
        //console.log("GLOBAL:",datasetGlobal)
        //console.log(document.querySelector("#datasetid").value.toString().trim())
        console.log(
            document.querySelector("#datasetid").options[document.querySelector("#datasetid").selectedIndex].text

        );
        console.log("Button object Coasting:",currentCoasting)
         let form={
                    "label":document.querySelector("#datasetid").options[document.querySelector("#datasetid").selectedIndex].text,
                    "amount":parseFloat(document.querySelector("#amount").value.toString().trim()),
                    "start":document.querySelector("#startdate").value.toString().trim().replaceAll("-",""),
                    "end":document.querySelector("#enddate").value.toString().trim().replaceAll("-",""),
                    "year":parseInt(document.querySelector("#enddate").value.toString().trim().substring(0,4)),
                    "idtype":datasetGlobal.typeOrgunit.id,
                    "datasetid":parseInt(document.querySelector("#datasetid").value)

         }
         console.log("Object Post:",form)

         let pb=document.querySelector("#progressbar2");
             pb.setAttribute('style','visibility: visible;')
               $http({
                        url:'/api/amount/add',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        method:'post',
                        data:$httpParamSerializer(form)
                    }).then(function(response) {
                        console.log("Succes :",response.data)
                        setTimeout(() => { location.reload();  }, 2000);
                        window.location.href=location.href
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