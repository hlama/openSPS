var app=angular.module('app.form.dataentry',[]);
app.controller("dataentryCtrl",function ($scope,$http,$httpParamSerializer) {
console.log("Dataset is:",datasets)
console.log("Data map length is:",mapdata[0].length)
console.log("Data map is:",mapdata)
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
            if (element.name==$scope.zsdata){
                $scope.datasetid=element.id;
                console.log(element.periode.toString().toUpperCase())
                if (element.periode.toString().toUpperCase()=="QUATERLY") {
                    $scope.periodicity=[]
                    $scope.periodicity=["03","06","09","12"];
                }else{
                    $scope.periodicity=[]
                    for(i=1;i<=12;i++){
                        $scope.periodicity.push(
                            i.toString().length<2?"0"+i.toString():i.toString()
                        )
                    }
                    console.log("Periodicity:",$scope.periodicity)
                }
                return;
            }
        });
    }
  

    $scope.getDatavalues=()=>{
        $scope.hideprogressbar=false;
        $scope.disabledButton=true;
        let objectSearch={
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
            console.log('Success:',response.data)
            $scope.inputs=response.data.dataelements;
            $scope.action=response.data.action;
            $scope.dataValueList=[];
            console.log($scope.inputs)
            console.log("Action:",response.data.action)
            $scope.max_point=0;
            $scope.datascored=0;
            
            let parent=document.querySelector('#generate');
            $scope.hideImageForm=false;
                parent.querySelectorAll('*').forEach(n => n.remove());
            if (response.data.action=="created") {
                $scope.max_point=0;
                $scope.inputs.forEach(data=>{
                    let divForm=document.createElement("div");
                        divForm.setAttribute("class","form-group");
                    let labelElement=document.createElement("h6");
                        labelElement.innerText=data.datalelementname+"("+data.point+" points)";
                    let messagePoint=document.createElement("span");
                        messagePoint.setAttribute("id","mess_"+data.dhis2id);
                        messagePoint.setAttribute("visibility","hidden");
                    let fieldElement=document.createElement("input");
                        fieldElement.setAttribute("type","number");
                        fieldElement.setAttribute("class","form-control form-control-lg");
                        fieldElement.setAttribute("id",data.dhis2id);
                        fieldElement.setAttribute("min",0);
                        fieldElement.setAttribute("max",data.point)
                        $scope.max_point=$scope.max_point+data.point;
                      
                            parent.appendChild(labelElement);
                            parent.appendChild(messagePoint);
                            parent.appendChild(fieldElement);
                })
            } else {
                $scope.max_point=0;
                $scope.datascored=0;
                $scope.inputs.forEach(data=>{
                    let divForm=document.createElement("div");
                        divForm.setAttribute("class","form-group");
                    let labelElement=document.createElement("h6");
                        labelElement.innerText=data.dataelement.datalelementname+"("+data.dataelement.point+" points)";
                    let messagePoint=document.createElement("span");
                        messagePoint.setAttribute("id","mess_"+data.dataelement.dhis2id);
                        messagePoint.setAttribute("visibility","hidden");
                    let fieldElement=document.createElement("input");
                        fieldElement.setAttribute("type","number");
                        fieldElement.setAttribute("class","form-control form-control-lg");
                        fieldElement.setAttribute("id",data.dataelement.dhis2id);
                        fieldElement.setAttribute("min",0);
                        fieldElement.setAttribute("max",data.dataelement.point)
                        fieldElement.setAttribute("value",data.value)
                        $scope.max_point=$scope.max_point+parseFloat(data.dataelement.point);
                        $scope.datascored=$scope.datascored+parseFloat(data.value);
                      
                            parent.appendChild(labelElement);
                            parent.appendChild(messagePoint);
                            parent.appendChild(fieldElement);
                })
            }
            $scope.title=$scope.zselement.name;
            $scope.subtitle="La Zone de sante a obtenue un score de "+$scope.datascored+"/"+$scope.max_point+" points";
           
            let btn_Save=document.createElement("button");
                btn_Save.setAttribute("type","button");
                btn_Save.innerText="ENREGISTRER"
                btn_Save.setAttribute("class","btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn btn-rounded");
                btn_Save.setAttribute("style","float: right; width: 100%; background-color:#990000;margin-top:0.5em;")
            parent.appendChild(btn_Save)
            $scope.hideprogressbar=true;
            $scope.disabledButton=false;
            
            btn_Save.onclick=()=>{
                let isvalidate=$scope.inputs.length;
                console.log("SIZE:",isvalidate)
                $scope.formFields=[];
                $scope.controlCheckPoint(isvalidate,$scope.inputs)
                if ($scope.action=="created") {
                    $scope.inputs.forEach(field=>{
                        let idField="#"+field.dhis2id;
                        let inputField={
                            "dataelementid":field.id,
                            "datasetid":$scope.datasetid,
                            "value":(document.querySelector(idField).value==""?0:parseFloat(document.querySelector(idField).value)),
                            "year":parseInt($scope.year),
                            "month":$scope.month.toString().trim(),
                            "idorgunit":parseInt($scope.idorgunit)
                        }
                        $scope.formFields.push(inputField)
                    });
                } else {
                    $scope.inputs.forEach(field=>{
                        let idField="#"+field.dataelement.dhis2id;
                        let inputField={
                            "dataelementid":field.dataelement.id,
                            "datasetid":$scope.datasetid,
                            "value":(document.querySelector(idField).value==""?0:parseFloat(document.querySelector(idField).value)),
                            "year":parseInt($scope.year),
                            "month":$scope.month.toString().trim(),
                            "idorgunit":parseInt($scope.idorgunit)
                        }
                        $scope.formFields.push(inputField)
                    });
                }
                console.log($scope.formFields)

               if ($scope.isvalidatform==$scope.inputs.length) {
                $http({
                    url:'/api/datavalue/zsdatavalues',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    method:'post',
                    data:$httpParamSerializer({data:JSON.stringify($scope.formFields)})
                   // data:$httpParamSerializer({data:JSON.stringify(test)})
                }).then(function(response) {
                    console.log("Succes :",response.data)
                    document.querySelector("#demo-show-toast").click()
                    //location.reload();
                },function (error) {
                    console.error(error)
                })
               } else {
                document.querySelector("#btnmodal").click()
               }
            }

          //  let parent=document.querySelector("#formgenerate");
            //console.log(parent.childNodes())
        },(error)=>{
            console.error('Error is :',error);
        })
    }
    $scope.dataelementChange=(fieldid)=>{
      
        console.log(
            {
                "id":fieldid
            }
        )
     
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
