package com.purchasing.health.api;


import java.util.HashMap;
import java.util.Map;

import com.purchasing.health.models.Dataset;
import com.purchasing.health.models.TypeOrgunit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.purchasing.health.repositories.DataSetRepository;
import com.purchasing.health.repositories.OrgunitTypeRepository;

@RestController
@RequestMapping("/api/dataset")
public class DataSetApi {
    
    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;

    @GetMapping("list")
    public Iterable<Dataset> getDataSets(){
        return dataSetRepository.findAll();
    }

    @PostMapping("add")
    public Object addDataSet(
        @RequestParam("datasetname") String datasetname,
        @RequestParam("periode") String periode,
        @RequestParam("typeorgunitid") String typeorgunitid,
        @RequestParam("statut") String statut

    )
        {
            Map<String,Object> mapQuery=new HashMap<>();
            TypeOrgunit typeOrgunit=orgunitTypeRepository.findById(Long.parseLong(typeorgunitid)).get();
            Dataset dataset=dataSetRepository.findByDatasetname(datasetname);
            if (dataset==null) {
                dataset=new Dataset(datasetname, typeOrgunit, periode,Integer.parseInt(statut));
                dataSetRepository.save(dataset);
                mapQuery.put("result", "200");
                mapQuery.put("data", dataset);
                
            } else {
                mapQuery.put("result", "210");
                mapQuery.put("data", "This dataset exists, please create another one...");
            }
            return mapQuery;
        }
}
