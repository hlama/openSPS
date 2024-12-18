package com.purchasing.health.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.purchasing.health.models.DataElement;
import com.purchasing.health.models.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.purchasing.health.repositories.DataElementRepository;
import com.purchasing.health.repositories.DataSetRepository;

@RestController
@RequestMapping("/api/dataelement/")
@Service
public class DataElementApi {
    
    @Autowired
    private DataElementRepository dataElementRepository;
    @Autowired
    private DataSetRepository dataSetRepository;

    @GetMapping("list")
    public Iterable<DataElement> getDataElements(){
        return dataElementRepository.findAll();
    }

     @GetMapping("dataset/{id}")
    public Iterable<DataElement> getDataElementsByDataSet(@PathVariable("id") Long id){
         System.out.println("Data set is :"+dataSetRepository.findById(id).get().getDatasetname());
        return dataElementRepository.findByDataset(dataSetRepository.findById(id).get());
    }
    @PostMapping("add")
    public Object addDataElement(
        @RequestParam("datalelementname") String datalelementname,
        @RequestParam("dataelementtype") String dataelementtype,
        @RequestParam("datasetid") String datasetid,
        @RequestParam("dhis2id") String dhis2id,
        @RequestParam("start_periode") String start_periode,
        @RequestParam("end_periode") String end_periode,
        @RequestParam("point") String point
    )
        {
            Map<String,Object> mapQuery=new HashMap<>();
            Dataset ds=dataSetRepository.findById(Long.parseLong(datasetid)).get();
            DataElement de= dataElementRepository.findByDatalelementnameAndDataset(datalelementname, ds);
            if (de==null) {
                de=new DataElement(dhis2id, datalelementname, start_periode, end_periode, dataelementtype, ds,Double.parseDouble(point));
                dataElementRepository.save(de);
                mapQuery.put("result", "200");
                mapQuery.put("data", de);
            } else {
                de.setDataset(ds);
                mapQuery.put("result", "210");                
                mapQuery.put("data", "This DataElement already exists, please create another one..");

            }
            return mapQuery;
        }

        @GetMapping("update")
        public  Iterable<DataElement> update(){
            long id=252;
            Dataset ds=dataSetRepository.findById(id).get();
            List<DataElement>dataElementList=(List<DataElement>)dataElementRepository.findByDataset(ds);
            ds.setPeriode("QUATERLY");
            for(DataElement de:dataElementList){
                de.setDataset(ds);
                dataElementRepository.save(de);
            }
            dataSetRepository.save(ds);
            return dataElementRepository.findByDataset(ds);


        }
}
