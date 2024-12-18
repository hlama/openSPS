package com.purchasing.health.api;

import bsh.EvalError;
import bsh.Interpreter;
import com.purchasing.health.models.Dataset;
import com.purchasing.health.models.SettingAmount;
import com.purchasing.health.models.TypeOrgunit;
import com.purchasing.health.repositories.DataSetRepository;
import com.purchasing.health.repositories.OrgunitTypeRepository;
import com.purchasing.health.repositories.SettingAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/amount/")
public class SettingAmountApi {

    @Autowired
    private SettingAmountRepository settingAmountRepository;
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;
    @Autowired
    private DataSetRepository dataSetRepository;
   @GetMapping("list")
    public Iterable<SettingAmount> getAmounts(){
        String data="System.out.println(Math.sqrt(9));";
       Interpreter interpreter=new Interpreter();
       try {
           interpreter.eval(data);
       } catch (EvalError e) {
           throw new RuntimeException(e);
       }
       return settingAmountRepository.findAll();
    }


    @PostMapping("add")
    public SettingAmount addSettingAmount(@RequestParam(name = "label")String label,
                                          @RequestParam(name = "amount") Double amount,
                                          @RequestParam(name = "start") Long start,
                                          @RequestParam(name = "end") Long end,
                                          @RequestParam(name = "year") Integer year,
                                          @RequestParam(name = "idtype") Long idtype,
                                          @RequestParam(name = "datasetid") Long datasetid){
       TypeOrgunit typeOrgunit=orgunitTypeRepository.findById(idtype).get();
        Dataset dataset=dataSetRepository.findById(datasetid).get();
       SettingAmount settingAmount=settingAmountRepository.findByStartPeriodeAndEndPeriodeAndTypeOrgunit
               (
                       start,
                       end,
                       typeOrgunit
               );
       if (settingAmount==null){
           settingAmount=new SettingAmount(
                   label,
                   amount,
                   start,
                   end,
                   year,
                   typeOrgunit,
                   dataset
           );
           settingAmountRepository.save(settingAmount);
       }else {
            settingAmount.setLabel(label);
            settingAmount.setStartPeriode(start);
            settingAmount.setEndPeriode(end);
            settingAmount.setTypeOrgunit(typeOrgunit);
            settingAmount.setYear(year);
            settingAmount.setAmount(amount);
            settingAmount.setDatasetid(dataset);
            settingAmountRepository.save(settingAmount);
       }


       return  settingAmount;
    }
    @GetMapping("periode")
    public Iterable<SettingAmount> getSettingAmountByPeriode(@RequestParam(name = "start") Long start,
                                                             @RequestParam(name = "end") Long end){
       return settingAmountRepository.findStartEndPeriode(start,end);
        //return settingAmountRepository.findByStartPeriodeLessThanAndEndPeriodeGreaterThanEqual(start, end)
    }

    @GetMapping("periodetest")
    public SettingAmount getSettingAmountByPeriode(@RequestParam(name = "start") Long start,
                                                             @RequestParam(name = "end") Long end,
                                                   @RequestParam(name = "idtype") Long idtype){
        return settingAmountRepository.findByStartPeriodeLessThanAndEndPeriodeGreaterThanEqualAndTypeOrgunit(
                start,
                end,
                orgunitTypeRepository.findById(idtype).get()
        );
        //return settingAmountRepository.findByStartPeriodeLessThanAndEndPeriodeGreaterThanEqual(start, end)
    }

    @GetMapping("type")
    public Iterable<SettingAmount> getSettingAmountByTypeOrgunit(@RequestParam(name = "id") Long id){
       TypeOrgunit typeOrgunit=orgunitTypeRepository.findById(id).get();
       return settingAmountRepository.findByTypeOrgunit(typeOrgunit);
    }
}
