package com.purchasing.health.api;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bsh.EvalError;
import com.purchasing.health.models.*;
import com.purchasing.health.repositories.*;
import com.purchasing.health.services.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/datavalue/")
public class DataValueApi {
    
    @Autowired
    private DataValueRepository dataValueRepository;
    @Autowired
    private DataElementRepository dataElementRepository;
    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;
    @Autowired
    private DataSetEncodedRepository dataSetEncodedRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private Engine engineCalculation;

    @GetMapping("list")
    public Iterable<DataValue> getDatavalues(){
        return dataValueRepository.findAll();
    }
    @GetMapping("list/{month}/{year}")
    public Iterable<DataValue> getDatavalues(@PathVariable String month,@PathVariable String year){
        return dataValueRepository.findByMonthAndYear(month,Integer.parseInt(year));
    }
     @GetMapping("list/dataelement/{dataelementid}")
    public Iterable<DataValue> getDatavaluesByDataElement(@PathVariable String dataelementid){
         DataElement de=null;
         boolean isExists=dataElementRepository.findById(Long.parseLong(dataelementid)).isPresent();
         if (isExists){
             de=dataElementRepository.findById(Long.parseLong(dataelementid))
                     .get();
         }

        return dataValueRepository.findByDataelement(de);
    }

      @GetMapping("list/dataset/{datasetid}")
    public Iterable<DataValue> getDatavaluesByDataSet(@PathVariable String datasetid){
        Dataset ds=null;
        boolean isExists=dataSetRepository.findById(Long.parseLong(datasetid))
                .isPresent();
        if (isExists){
            ds=dataSetRepository.findById(Long.parseLong(datasetid))
                    .get();
        }
        return dataValueRepository.findByDataset(ds);
    }

    @PostMapping("add")
    public Object addDataValue
    (
        @RequestParam("dataelementid") String dataelementid,
        @RequestParam("datasetid") String datasetid,
        @RequestParam("idtypeorgunit") Long idtypeorgunit,
        @RequestParam("idtypeorgunit") Long idorgunit,
        @RequestParam("month") String month,
        @RequestParam("year") Integer year,
        @RequestParam("value") String value
    ){
        Dataset sd=null;
        DataElement de=null;
        boolean isExistsDS=dataSetRepository.findById(Long.parseLong(datasetid)).isPresent();
        if (isExistsDS){
            sd=dataSetRepository.findById(Long.parseLong(datasetid)).get();
        }
        boolean isExistsDE=dataElementRepository.findById(Long.parseLong(dataelementid)).isPresent();
        if (isExistsDE){
            de=dataElementRepository.findById(Long.parseLong(dataelementid)).get();
        }
        String datecreated=String.format("%s%s%s", new Date().getYear(),
            Integer.toString(new Date().getMonth()).length()<2?"0"+ new Date().getMonth() :Integer.toString(new Date().getMonth()),
            Integer.toString(new Date().getDay()).length()<2?"0"+ new Date().getDay() :Integer.toString(new Date().getDay())

        );
        /* 
        DataValue dv=dataValueRepository.findByIdorgunitAndDataelementAndDatasetAndMonthAndYear
        (idorgunit, de, sd, month, year);
        if (dv==null) {
            dv=new DataValue();
            dv.setDataelement(de);
            dv.setDataset(sd);
            dv.setDatecreated(datecreated);
            dv.setYear(year);
            dv.setMonth(month);
            dv.setIdorgunit(idorgunit);
            dv.setValue(Double.parseDouble(value));

            dataValueRepository.save(dv);
        } else {
            dv.setYear(year);
            dv.setMonth(month);
            dv.setValue(Double.parseDouble(value));
            dv.setDatecreated(datecreated);
            dataValueRepository.save(dv);
        }
        */
         
        return null;
    }


 @GetMapping("zsdatavalues")
 public Object getDataValues(@RequestParam("datasetid") String datasetid,
 @RequestParam("month") String month,@RequestParam("year") Integer year,
 @RequestParam("idorgunit") Long idorgunit
 ) throws EvalError {
    boolean isExistsDS=dataSetRepository.findById(Long.parseLong(datasetid)).isPresent();
     Dataset dataset=null;
    if (isExistsDS){
        dataset=dataSetRepository.findById(Long.parseLong(datasetid)).get();
    }
    List<DataValue> dataValues=(List<DataValue>) dataValueRepository.findByDatasetAndMonthAndYearAndIdorgunitOrderById(dataset, month, year, idorgunit);
    Map<String,Object> mapData=new HashMap<>();
     if (calculationRepository.findByDatasetidAndStatus(dataset, 1).isPresent()){
         Calculation __calculation__=calculationRepository.findByDatasetidAndStatus(dataset, 1).get();
         engineCalculation.setDataValueList(dataValues);
         engineCalculation.setCalculation(__calculation__);
         System.out.println("Calculation:"+engineCalculation.runEngine());
     }
    if (dataValues.size()==0) {
        mapData.put("action", "created");
        //mapData.put("dataelements", dataSetRepository.findById(Long.parseLong(datasetid)).get());
        mapData.put("dataset", dataset);
        mapData.put("dataelements", dataElementRepository.findByDataset(dataset));
    }else{
        mapData.put("action", "updated");
        mapData.put("dataset", dataset);
        mapData.put("dataelements", dataValues);
        mapData.put("amount",engineCalculation.runEngine());

    }



    return mapData;
 }


 @PostMapping("zsdatavalues")
 public Object addDataValues(@RequestParam("data")String data) throws IOException
 {
    ObjectMapper objectMapper=new ObjectMapper();
    boolean isExistOrgunit=orgunitTypeRepository.findById(Long.parseLong("2")).isPresent();
     TypeOrgunit typeOrgunit=null;
    if (isExistOrgunit){
        typeOrgunit=orgunitTypeRepository.findById(Long.parseLong("2")).get();
    }

    List<Map<String,Object>> datavaluesList=objectMapper.readValue(data, List.class);
    boolean isExistsDS=dataSetRepository.findById(Long.parseLong(datavaluesList.get(0).get("datasetid").toString())).isPresent();
     Dataset ds=null;
     if (isExistsDS){
        ds=dataSetRepository.findById(Long.parseLong(datavaluesList.get(0).get("datasetid").toString())).get();
    }

    String month=datavaluesList.get(0).get("month").toString();
    Integer year=Integer.parseInt(datavaluesList.get(0).get("year").toString());
    Long idorgunit=Long.parseLong(datavaluesList.get(0).get("idorgunit").toString());
     String datecreated= String.valueOf(new Date().getYear()) + (Integer.toString(new Date().getMonth()).length() < 2 ? "0" + new Date().getMonth() : Integer.toString(new Date().getMonth())) + "0" + new Date().getDay();
    List<DataValue> dataValues=(List<DataValue>) dataValueRepository.findByDatasetAndMonthAndYearAndIdorgunitOrderById(ds, month, year, idorgunit);
    if (dataValues.isEmpty()) {
        for (Map<String,Object> datavalue : datavaluesList) {
            
            DataValue dv=new DataValue();
                dv.setDataset(ds);
                dv.setIdorgunit(idorgunit);
                dv.setMonth(month);
                dv.setYear(year);
                dv.setDataelement(
                        (
                                dataElementRepository.findById(Long.parseLong(datavalue.get("dataelementid").toString())).isPresent()?
                                dataElementRepository.findById(Long.parseLong(datavalue.get("dataelementid").toString())).get():
                                null
                                )

                );
                dv.setDatecreated(datecreated);
                dv.setTypeOrgunit(typeOrgunit);
                dv.setValue(Double.parseDouble(datavalue.get("value").toString()));
                dataValueRepository.save(dv);
        }
        System.out.println("ADDING DATA");
    } else {
                for (Map<String,Object> datavalue : datavaluesList) {
                   // System.out.println("DATA ELEMENT ID:"+datavalue.get("dataelementid").toString());
                    //System.out.println("ORGUNIT ID:"+datavalue.get("idorgunit").toString());
                DataElement de=(
                        dataElementRepository.findById(Long.parseLong(datavalue.get("dataelementid").toString())).isPresent()?
                        dataElementRepository.findById(Long.parseLong(datavalue.get("dataelementid").toString())).get():
                        null
                );
                DataValue dv=dataValueRepository.findByIdorgunitAndDataelementAndDatasetAndMonthAndYear(idorgunit, de, ds, month, year);
                System.out.println(dv.getValue());
                /*
                 * dv.setDataset(ds);
                dv.setIdorgunit(idorgunit);
                dv.setMonth(month);
                dv.setYear(year);
                dv.setDataelement(
                    dataElementRepository.findById(Long.parseLong(datavalue.get("dataelementid").toString())).get()
                );
                dv.setDatecreated(datecreated);
                dv.setTypeOrgunit(typeOrgunit);
                 */
                dv.setValue(Double.parseDouble(datavalue.get("value").toString()));
                dataValueRepository.save(dv);
                
        }
        System.out.println("UPDATE PROCESS....");
    }
    /*
    for (Map<String,Object> datavalue : datavaluesList) {
         String datecreated=String.format("%s%s%s", new Date().getYear(),
            Integer.toString(new Date().getMonth()).length()<2?"0"+Integer.toString(new Date().getMonth()):Integer.toString(new Date().getMonth()),
            Integer.toString(new Date().getDay()).length()<2?"0"+Integer.toString(new Date().getDay()):Integer.toString(new Date().getDay()));
        
        Dataset sd=dataSetRepository.findById(Long.parseLong(datavalue.get("datasetid").toString())).get();
        DataElement de=dataElementRepository.findById(Long.parseLong(datavalue.get("dataelementid").toString())).get();
        System.out.println(sd.getDatasetname());
        System.out.println(de.getDatalelementname());
        List<DataValue> dv= (List<DataValue>) this.dataValueRepository.findByDatasetAndMonthAndYearAndIdorgunit
        		(sd, datavalue.get("monht").toString(), Integer.parseInt(datavalue.get("year").toString()), Long.parseLong(datavalue.get("idorgunit").toString()));
        if(dv.size()==0) {
        	DataValue datavalueSet=new DataValue();
        	datavalueSet.setDataset(sd);
        	datavalueSet.setDataelement(de);
        	datavalueSet.setYear(Integer.parseInt(datavalue.get("year").toString()));
        	datavalueSet.setMonth(datavalue.get("month").toString());
        	datavalueSet.setValue(Double.parseDouble(datavalue.get("value").toString()));
        	datavalueSet.setDatecreated(datecreated);
        	datavalueSet.setTypeOrgunit(typeOrgunit);
        	datavalueSet.setIdorgunit(Long.parseLong(datavalue.get("idorgunit").toString()));
        	this.dataValueRepository.save(datavalueSet);
        	System.out.println(String.format("Data not found for DataSet:%s and DataELement:%s", 
        			sd.getDatasetname(),de.getDatalelementname()));
        }else {
        	
        }
    }
     */
    return data;
 }

 @GetMapping("gets")
 public DataValue get(
    @RequestParam("idorgunit")Long idorgunit,
    @RequestParam("dataelementid")Long dataelementid,
    @RequestParam("datasetid")Long datasetid,
    @RequestParam("month")String month,
    @RequestParam("year")Integer year


 ){
       return dataValueRepository.findByIdorgunitAndDataelementAndDatasetAndMonthAndYear
        (
            idorgunit,
                (
                        dataElementRepository.findById(dataelementid).isPresent()?
                        dataElementRepository.findById(dataelementid).get():
                        null
                ),
                (
                        dataSetRepository.findById(datasetid).isPresent()?
                        dataSetRepository.findById(datasetid).get():
                        null
                 ),
            month,
            year
        );
 }
}
