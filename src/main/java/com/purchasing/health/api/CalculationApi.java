package com.purchasing.health.api;

import com.purchasing.health.models.Calculation;
import com.purchasing.health.models.Dataset;
import com.purchasing.health.repositories.CalculationRepository;
import com.purchasing.health.repositories.DataSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/engine/")
public class CalculationApi {
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private DataSetRepository dataSetRepository;

    @GetMapping("list")
    public Iterable<Calculation> calculations(){
        return calculationRepository.findAll();
    }

    @PostMapping("add")
    public Calculation calculationAdd(@RequestParam(name = "datasetid") Long datasetid,
                                      @RequestParam(name = "startdate") Long startdate,
                                      @RequestParam(name ="enddate") Long enddate,
                                      @RequestParam(name = "formula") String formula,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "status") Integer status){
        Dataset dataset=dataSetRepository.findById(datasetid).get();
        Calculation calculation=calculationRepository.findByDatasetidAndStartdateAndEnddate
                (
                        dataset,
                        startdate,
                        enddate
                );
        if (calculation==null){
            calculation=new Calculation();
            calculation.setDatasetid(dataset);
            calculation.setName(name);
            calculation.setDatecreated(new Date().toString());
            calculation.setStartdate(startdate);
            calculation.setEnddate(enddate);
            calculation.setFormula(formula);
            calculation.setStatus(status);
            calculationRepository.save(calculation);
        }else{
            calculation.setStatus(status);
            calculation.setDatecreated(new Date().toString());
            calculation.setFormula(formula);
            calculationRepository.save(calculation);
        }

        return calculation;
    }
}
