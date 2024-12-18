var app=angular.module('app.bank',[]);

app.controller("bankCtrl",function ($scope,$http,$httpParamSerializer) {
    let selectTypeOrgunit=document.querySelector("#typeorgunitid");
    let selectOrgunit=document.querySelector("#dataorgunit");
    let selectBank=document.querySelector("#idbank");
    let accountbankText=document.querySelector("#accountbank");
    let selectAccountStatus=document.querySelector("#accountstatus");
let funcPromise=function(data,category){
 return new Promise((resolve,reject)=>{
    try{
            if(category=="HGR"){
            console.log("Size HGR:",data.length)
                data.forEach(org=>{
                           if(org.typeOrgunit.libelle=="HGR"){
                               console.log("Hopital:",org)
                                let optionValue=document.createElement("option");
                                    optionValue.value=org.id;
                                    optionValue.innerHTML=org.name
                                    selectOrgunit.appendChild(optionValue)
                               $scope.orgunitList.push(org)

                           }
                       })
                        let response={"response":200}
                            resolve(response)
            }else if(category=="FOSA"){
                             data.forEach(org=>{
                                        if(org.typeOrgunit.libelle!="HGR"){
                                            console.log("FOSA:",org)
                                             let optionValue=document.createElement("option");
                                                 optionValue.value=org.id;
                                                 optionValue.innerHTML=org.name
                                                 selectOrgunit.appendChild(optionValue)
                                            $scope.orgunitList.push(org)
                                        }
                                    })
                                     let response={"response":200}
                                         resolve(response)
            }else{
                data.forEach(org=>{
                                      console.log("ZS:",org)
                                      $scope.orgunitList.push(org)
                                      let optionValue=document.createElement("option");
                                      optionValue.value=org.id;
                                      optionValue.innerHTML=org.name
                                      selectOrgunit.appendChild(optionValue)

                                })
                                let response={"response":200}
                                resolve(response)
            }

        //resolve(data/2)
    }catch(e){
        reject(e)
    }
 })
}

console.log("mapdate:",mapdata)
let accountbankList=[]
    accountbanks.forEach(b=>{
        var info={
                "libelle":b.libelle,
                "accountnumber":b.accountnumber,
                "activite":b.activity=='O'?'Active':'Inactive',
                "banque":b.idbank.name
        }
        accountbankList.push(info)
    })
let bankList=[];
banks.forEach(elt=>{
    console.log("Object:",elt)
    bankList.push([elt.id,elt.name,elt.codename,elt.phone])
})

let updateTable=function(){
  let dataTable=new DataTable('#bankDataTable', {
                    "data" : accountbankList,
                    "columns" : [
                        { "data" : "libelle","title":"Nom entite" },
                        { "data" : "accountnumber","title":"Numero de compte" },
                        { "data" : "activite","title":"Etat du compte" },
                        { "data" : "banque","title":"Nom de la banque" }
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
}
  let dataTable=new DataTable('#bankDataTable', {
                    "data" : accountbankList,
                    "columns" : [
                        { "data" : "libelle","title":"Nom entite" },
                        { "data" : "accountnumber","title":"Numero de compte" },
                        { "data" : "activite","title":"Etat du compte" },
                        { "data" : "banque","title":"Nom de la banque" }
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
            document.querySelector("#typeorgunitid").value.toString().trim()!='' &&
            document.querySelector("#dataorgunit").value.toString().trim()!='' &&
            document.querySelector("#idbank").value.toString().trim()!='' &&
            document.querySelector("#accountbank").value.toString().trim()!=''
        ){
         let pb=document.querySelector("#progressbar2");
             pb.setAttribute('style','visibility: visible;')
                let form={
                           "idbank":document.querySelector("#idbank").value.toString().trim(),
                           "accountnumber":document.querySelector("#accountbank").value.toString().trim(),
                           "idorgunit":selectOrgunit.options[selectOrgunit.selectedIndex].value.toString().trim(),
                           "libelle":selectOrgunit.options[selectOrgunit.selectedIndex].text.toString().trim(),
                            "status":"O"
                 }
                 console.log("Object is :",form)

               $http({
                        url:'/api/bank/accountbank/add',
                        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                        method:'post',
                        data:$httpParamSerializer(form)
                    }).then(function(response) {
                        (function() {
                                           'use strict';
                                            window['counter'] = 0;
                                            var snackbarContainer = document.querySelector('#demo-toast-example');
                                            var showToastButton = document.querySelector('#demo-show-toast');
                                            showToastButton.addEventListener('click', function() {
                                              'use strict';
                                              var data = {message:"Compte bancaire enregistre avec succes!"};
                                              snackbarContainer.MaterialSnackbar.showSnackbar(data);

                                            });
                       }());
                       //updateTable()
                        window.location.href=location.href
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
 let clearFunc=()=>{

 }
selectTypeOrgunit.onchange= function(){
     let cases=document.querySelector("#typeorgunitid").value.toString().trim();
      console.log("CASES:",cases)
   if (cases=="Zone de Sante"){
                       $scope.orgunitList=mapdata.zonelist
                       document.querySelector("#dataorgunit").disabled="";
                       document.querySelector("#dataorgunit").innerHTML="";
                       let optionValue1=document.createElement("option");
                            optionValue1.innerHTML="Selectionnez la structure";
                            optionValue1.selected="true"
                            optionValue1.disabled="disabled"
                            selectOrgunit.appendChild(optionValue1)
         funcPromise(mapdata.zonelist,cases).then((response)=>{
             console.log('SIZE:',response)
         },(error)=>{
          console.log('rejct:',error)
         })
      }else if(cases=="HGR"){
      console.log("Orgunits:",mapdata.orglist)
        $scope.orgunitList=mapdata.orglist
        document.querySelector("#dataorgunit").disabled="";
        document.querySelector("#dataorgunit").innerHTML="";
        let optionValue1=document.createElement("option");
            optionValue1.innerHTML="Selectionnez la structure";
            optionValue1.selected="true"
            optionValue1.disabled="disabled"
            selectOrgunit.appendChild(optionValue1)
           funcPromise(mapdata.orglist,cases).then((response)=>{
                        console.log('SIZE:',response)
                    },(error)=>{
                     console.log('rejct:',error)
                    })

      }else{
      $scope.orgunitList=[]
         document.querySelector("#dataorgunit").disabled="";
              document.querySelector("#dataorgunit").innerHTML="";
              let optionValue1=document.createElement("option");
                  optionValue1.innerHTML="Selectionnez la structure";
                  optionValue1.selected="true"
                  optionValue1.disabled="disabled"
                  selectOrgunit.appendChild(optionValue1)
                  funcPromise(mapdata.orglist,cases).then((response)=>{
                               console.log('SIZE:',response)
                           },(error)=>{
                            console.log('rejct:',error)
                           })
      }
    }
selectOrgunit.onchange=function(){
console.log("ID ORG:",+selectOrgunit.value)
  let urlFormed='/api/bank/accountbank/orgunit/'+selectOrgunit.value
               $http({
                    url:urlFormed,
                    method:'GET'
               }).then((response)=>{
                    if(response.data.idbank!=undefined){
                    console.log(response.data)

                       selectBank.innerHTML=response.data.idbank.name
                      let optionValue=document.createElement("option");
                      let optionAccountStatus=document.createElement("option");
                          optionValue.value=response.data.idbank.id;
                          optionValue.innerHTML=response.data.idbank.name
                          selectBank.appendChild(optionValue)
                          selectBank.value=response.data.idbank.id;
                          selectBank.disabled=""
                          document.querySelector("#accountbank").value=response.data.accountnumber
                          optionAccountStatus.value=response.data.activity
                          optionAccountStatus.innerHTML=(response.data.activity=="O"?"ACTIVE":"INACTIVE")
                          selectAccountStatus.appendChild(optionAccountStatus)
                          selectAccountStatus.disabled=""
                          selectAccountStatus.value=response.data.activity
                          if(response.data.activity=="0"){
                                let optionAccountOther=document.createElement("option");
                                optionAccountOther.value="N";
                                optionAccountOther.innerHTML="INACTIVE"
                          }

                    }else{
                        selectBank.innerHTML=""
                         let optionValueBlank=document.createElement("option");
                                                    optionValueBlank.innerHTML="Choisir la banque ";
                                                    optionValueBlank.selected="true"
                                                    optionValueBlank.disabled="disabled"
                                                    selectBank.appendChild(optionValueBlank)
                        banks.forEach(bank=>{
                            let optionValue=document.createElement("option");
                                optionValue.value=bank.id;
                                optionValue.innerHTML=bank.name
                                selectBank.appendChild(optionValue)
                        })
                        selectBank.disabled=""
                        document.querySelector("#accountbank").disabled=""
                        document.querySelector("#accountbank").value=""
                        document.querySelector("#btn_sender").disabled=""


                    }

               },(error)=>{

               })
            }
})