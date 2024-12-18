var app=angular.module('app.zs.dataentry',[]);
app.controller("dataentryCtrl",function ($scope,$http,$httpParamSerializer) {
console.log("DataSet list:",datasets)
    $scope.formPrint=[];
    $scope.hideImageForm=true;
    $scope.hideprogressbar=true;
    $scope.disabledButton=false;
    $scope.periodicity=[]
    
  
    $scope.visibility=true;
    document.querySelector("#zsdata").onchange=()=>{
        mapdata.forEach(element => {
            if (element.name==$scope.zsdata){
                $scope.zselement=element;
                $scope.idorgunit=element.id;
                console.log($scope.zselement)
                
                return;
            }
        });
    }


    document.querySelector("#datasetname").onchange=()=>{
        datasets.forEach(element => {
            if (element.datasetname==document.querySelector("#datasetname").value){
                $scope.datasetid=element.id;
                console.log(element.periode.toString().toUpperCase())
                if (element.periode.toString().toUpperCase()=="QUATERLY") {
                    $scope.periodicity=["03","06","09","12"];
                }
                return;
            }
        });
    }
  
    $scope.test=()=>{
        console.log($scope.year)
    }

    $scope.getDatavalues=()=>{
        $scope.hideprogressbar=false;
        $scope.disabledButton=true;
        let objectSearch={}
        objectSearch={
                        "datasetid":$scope.datasetid,
                        "month":$scope.month.toString().trim(),
                        "year":parseInt($scope.year),
                        "idorgunit":parseInt($scope.idorgunit)
                    }
        console.log(objectSearch)
        if ($scope.datasetid!=undefined && $scope.month!=null && $scope.year!=null && $scope.idorgunit!=undefined ) {
            
            objectSearch={
                "datasetid":$scope.datasetid,
                "month":$scope.month.toString().trim(),
                "year":parseInt($scope.year),
                "idorgunit":parseInt($scope.idorgunit)
            }
            console.log("ObjectParam:",objectSearch);
            $http({
                method:'GET',
                url:"/api/datavalue/zsdatavalues",
                params:objectSearch
            }).then((response)=>{
                //console.log('Success:',response.data)
                $scope.inputs=response.data.dataelements;
                $scope.action=response.data.action;
                $scope.amount=response.data.amount;
                $scope.dataValueList=[];
                console.log($scope.inputs)
                console.log("Action:",response.data.action)
                $scope.max_point=0;
                $scope.datascored=0;
                
                let parent=document.querySelector('#parent_table');
                
                   parent.querySelectorAll('*').forEach(n => n.remove());
                if (response.data.action=="created") {
                    document.querySelector("#demo-show-toast").click()
                } else {
          
                    $scope.hideImageForm=false;
                    $scope.idgeneratePrint=0;
                    $scope.max_point=0;
                    $scope.inputs.forEach(field=>{
                        $scope.idgeneratePrint=$scope.idgeneratePrint+1;
                        let idField="#"+field.dataelement.dhis2id;
                        $scope.max_point=$scope.max_point+parseFloat(field.dataelement.point);
                        $scope.datascored=$scope.datascored+parseFloat(field.value);
                        let inputField={
                            "id":$scope.idgeneratePrint,
                            "dataelementid":field.dataelement.id,
                            "dataelementname":field.dataelement.datalelementname,
                            "point":field.dataelement.point,
                            "value":field.value,
                            "score":Number((parseFloat(field.value)/parseFloat(field.dataelement.point))*100).toFixed(2),
                           // "score":Number((parseFloat(field.value)/parseFloat(field.dataelement.point))*100).toFixed(2),
                            "datasetid":$scope.datasetid,
                           // "value":(document.querySelector(idField).value==""?0:parseFloat(document.querySelector(idField).value)),
                            "year":parseInt($scope.year),
                            "month":$scope.month.toString().trim(),
                            "idorgunit":parseInt($scope.idorgunit)
                        }
                        $scope.formPrint.push(inputField)
                    });
                    console.log($scope.formPrint)
                   let table=document.createElement("table");
                        table.setAttribute("class","table table-hover");
                    let th1=document.createElement("th");
                        th1.innerHTML="N°";
                    let th2=document.createElement("th");
                        th2.innerHTML="Domaines";
                    let th3=document.createElement("th");
                        th3.innerHTML="Points disponibles";
                    let th4=document.createElement("th");
                        th4.innerHTML="Points qualité attribues";
                    let th5=document.createElement("th");
                        th5.innerHTML="Score Obtenu";
                    let trhead=document.createElement("tr");
                        trhead.appendChild(th1)
                        trhead.appendChild(th2)
                        trhead.appendChild(th3)
                        trhead.appendChild(th4)
                        trhead.appendChild(th5)
                    let thead=document.createElement("thead")
                        thead.appendChild(trhead)
                        table.appendChild(thead);
                    let parent_table=document.querySelector("#parent_table")
                    
                        //parent_table.appendChild(table)
                        let tbody=document.createElement("tbody")
    
                     $scope.formPrint.forEach(e=>{
                        let row=document.createElement("tr");
                        let field1=document.createElement("td")
                            field1.innerHTML=e.id;
                        let field2=document.createElement("td")
                            field2.innerHTML=e.dataelementname;
                        let field3=document.createElement("td")
                            field3.innerHTML=e.point;
                        let field4=document.createElement("td")
                            field4.innerHTML=e.value;
                        let field5=document.createElement("td")
                            field5.innerHTML=Number(e.score).toFixed(2)+"%";
                            row.appendChild(field1);
                            row.appendChild(field2);
                            row.appendChild(field3);
                            row.appendChild(field4);
                            row.appendChild(field5);
                            tbody.appendChild(row);
                        
                            
                     })
                    
                     let table_sub=document.createElement("table"); 
                     let row_sub=document.createElement("tr");
                     let row_sub_amount=document.createElement("tr");
                     let cellsub1=document.createElement("td")
                     let cellsub2=document.createElement("td")
                     let cellsub3=document.createElement("td")
                     let cellsub4=document.createElement("td")
                     let cellsub5=document.createElement("td")
    
                     let cellsubamount1=document.createElement("td")
                     let cellsubamount2=document.createElement("td")
                     let cellsubamount3=document.createElement("td")
                     let cellsubamount4=document.createElement("td")
                     let cellsubamount5=document.createElement("td")
                         cellsub1.innerHTML="#"
                         cellsub2.innerHTML="POINT OBTENU/SCORE"
                         cellsub3.innerHTML=$scope.max_point;
                         cellsub4.innerHTML=$scope.datascored;
                         cellsub5.innerHTML=(Number(parseFloat($scope.datascored)/parseFloat($scope.max_point))*100).toFixed(2)+"%"
                         row_sub.appendChild(cellsub1);
                         row_sub.appendChild(cellsub2);
                         row_sub.appendChild(cellsub3);
                         row_sub.appendChild(cellsub4);
                         row_sub.appendChild(cellsub5);
                         cellsubamount2.innerHTML="TOTAL SUBSIDES"
                         cellsubamount3.innerHTML=$scope.amount+" $"
                         row_sub_amount.appendChild(cellsubamount1);
                         row_sub_amount.appendChild(cellsubamount2);
                         row_sub_amount.appendChild(cellsubamount3);
                         row_sub_amount.appendChild(cellsubamount4);
                         row_sub_amount.appendChild(cellsubamount5);
                         row_sub.setAttribute("style","font-weight:bold")
                         row_sub_amount.setAttribute("style","font-weight:bold")
                         tbody.appendChild(row_sub);
                         tbody.appendChild(row_sub_amount);
                         table.appendChild(tbody);
                         parent_table.appendChild(table);
                        // paneToPrint.appendChild(table);
                     $scope.title=$scope.zselement.name;
                     $scope.subtitle="La Zone de sante a obtenue un score de "+$scope.datascored+"/"+$scope.max_point+" points";

                    let formSender=document.createElement("form")
                        formSender.method="POST"
                        formSender.action="/api/invoices/zs"
                     let inputSender=document.createElement("input")
                         inputSender.setAttribute("type","hidden")
                         inputSender.setAttribute("name","data")
                         inputSender.setAttribute("id","data")
                         inputSender.setAttribute("value",JSON.stringify($scope.formPrint))
                     let btnPosting=document.createElement("button")
                         btnPosting.setAttribute("type","submit")
                         btnPosting.setAttribute("text","SUBMITTER")
                         btnPosting.setAttribute("id","btnPosting")
                         btnPosting.setAttribute("style","visibility:hidden")
                         formSender.appendChild(inputSender)
                         formSender.appendChild(btnPosting)
                         parent.appendChild(formSender)

                     let btn_Save=document.createElement("button");
                         btn_Save.setAttribute("type","button");
                         btn_Save.innerText="IMPRIMER FACTURE"
                         btn_Save.setAttribute("class","btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn btn-rounded");
                         btn_Save.setAttribute("style","float: left; width: 50%; background-color:#990000;margin-top:1em;")
                         //paneToPrint.appendChild(btn_Save);
                         parent.appendChild(btn_Save)
                        // parent_table.appendChild(paneToPrint);
                         //parent.appendChild(btn_Save);
                   //  paneToPrint.appendChild(table);
                     btn_Save.onclick=()=>{
                     btnPosting.click()
                     //location.reload()
                   /*
                      let config = {
                                             headers: { 'Accept': 'application/pdf' },
                                             responseType : "arrayBuffer"
                                         };
                                         console.log("OBJECT:",JSON.stringify($scope.formPrint))
                                         $http({
                                            method:'POST',
                                            url:"/api/invoices/zs",
                                            params:{
                                                        "data":JSON.stringify($scope.formPrint)
                                            }

                                         }).then((response)=>{
                                          const reportBuffer = new Uint8Array(response.data);
                                          const file = new Blob([reportBuffer], {type: 'application/pdf'});
                                          const fileURL = window.URL.createObjectURL(file);

                                          const fileName = 'file.pdf';
                                          var a_link = document.createElement('a');
                                          document.body.appendChild(a_link);
                                          a_link.style = 'display: none';
                                          a_link.href = fileURL;
                                          a_link.download = fileName;
                                          a_link.click();

                                         },(error)=>{

                                         })
                   */

                     }
                }
                $scope.hideprogressbar=true;
                $scope.disabledButton=false;
            },(error)=>{
                console.error('Error is :',error);
            })
        } else {
            console.log("BUGG")
            $scope.hideprogressbar=true;
            $scope.disabledButton=false;
            document.querySelector('#demo-show-toast-bug').click()
        }
           
      
    }
    $(document).ready(function() {
        $("form").bind("keypress", function(e) {
            if (e.keyCode == 13) {
                return false;
            }
        });
    });
    $scope.controlCheckPoint=((elts,tabs)=>{
     if ($scope.action=="created") {
        tabs.forEach(obj=>{
            let inputPoint=document.querySelector("#"+obj.dhis2id).value;
            if (obj.point<inputPoint) {
                let mess=document.querySelector("#mess_"+obj.dhis2id);
                    mess.innerText="La valeur saisit est superieure au point disponible";
                    mess.setAttribute("style","visibility:visible;color:red;")
                elts=elts-1;
            }
           
        })
     } else {
        tabs.forEach(obj=>{
            let inputPoint=document.querySelector("#"+obj.dataelement.dhis2id).value;
            if (obj.point<inputPoint) {
                let mess=document.querySelector("#mess_"+obj.dataelement.dhis2id);
                    mess.innerText="La valeur saisit est superieure au point disponible";
                    mess.setAttribute("style","visibility:visible;color:red;")
                elts=elts-1;
            }
           
        })
     }
        console.log("Current value:",elts)
        $scope.isvalidatform=elts;
    })
})
