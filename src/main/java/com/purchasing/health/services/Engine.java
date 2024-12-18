package com.purchasing.health.services;

import bsh.EvalError;
import bsh.Interpreter;
import com.purchasing.health.models.AggregateDictionnary;
import com.purchasing.health.models.Calculation;
import com.purchasing.health.models.DataValue;
import com.purchasing.health.repositories.AggregateDictionnaryRepository;
import com.purchasing.health.repositories.CalculationRepository;
import com.purchasing.health.repositories.DataSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Configurable
public class Engine {
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private AggregateDictionnaryRepository aggregateDictionnaryRepository;
    private Calculation calculation;
    private List<DataValue> dataValueList=null;
    private Aggregate __aggregate__=null;

    public List<DataValue> getDataValueList() {
        return dataValueList;
    }

    public void setDataValueList(List<DataValue> dataValueList) {
        this.dataValueList = dataValueList;
    }

    public Calculation getCalculation() {
        return calculation;
    }

    public void setCalculation(Calculation calculation) {
        this.calculation = calculation;
    }

    public Engine() {
        dataValueList=new ArrayList<>();
    }


    public String runEngine() throws EvalError {
        Interpreter __interpreter__=new Interpreter();
        String __formula__=this.calculation.getFormula();
        AtomicReference<Boolean> __isFormulaFound__= new AtomicReference<>(false);
        AtomicReference<String> __currentCode__=new AtomicReference<>("");
        String dataValueCalculted="";
        AggregateDictionnary __aggregateDictionnary__=null;
       List<AggregateDictionnary> __aggregateDictionnaryList__= (List<AggregateDictionnary>) aggregateDictionnaryRepository.findAll();
       __aggregateDictionnaryList__.stream().forEach(e->{
           if(__formula__.contains(e.getCode())){
               __isFormulaFound__.set(true);
               __currentCode__.set(e.getCode());
           }
       });
        if (__isFormulaFound__.get()){
            __aggregateDictionnary__=aggregateDictionnaryRepository.findByCodeContaining(__currentCode__.get()).get();
            switch(__aggregateDictionnary__.getCode()){
                case "sum(*)"->{
                    System.out.println("SOMME:"+__aggregateDictionnary__.getCode());
                    __aggregate__=Aggregate.SUM;
                    dataValueCalculted=__formula__.replace("sum(*)",this.__engineCalculate__(__aggregate__).toString());
                    dataValueCalculted= String.valueOf((Math.round((Double) __interpreter__.eval(dataValueCalculted))));
                    System.out.println("AMOUNT ENGINE:"+dataValueCalculted);
                }
                case "{*}"->{
                    __aggregate__=Aggregate.UNIQUE;
                    dataValueCalculted=this.__engineCalculate__(__aggregate__).toString();
                }
                case "avg(*)"->{
                    __aggregate__=Aggregate.AVG;
                    dataValueCalculted=this.__engineCalculate__(__aggregate__).toString();
                }

            }
        }else{
            System.out.println("LIKE NOT FOUND");
        }
        return dataValueCalculted;
    }

    public void createDictionnary(){
        List<AggregateDictionnary> list= (List<AggregateDictionnary>) aggregateDictionnaryRepository.findAll();
        if (list.isEmpty()){
            List<AggregateDictionnary> aggregateDictionnaryList=Arrays.asList(
                    new AggregateDictionnary("sum(*)","Allows you to add up all the data entered in a form "),
                    new AggregateDictionnary("{*}","Select a form field to execute a formula"),
                    new AggregateDictionnary("avg(*)","Calculates the average value of data entered in the form")
            );
            aggregateDictionnaryRepository.saveAll(aggregateDictionnaryList);
            System.out.println("Added");
        }else{

        }


    }
    private Double __engineCalculate__(Aggregate aggregate){
        Double __result__=0.0;
        switch (aggregate){
            case SUM -> {
               __result__= this.getDataValueList().stream().mapToDouble(e->e.getValue()).sum();
               return __result__;
            }
            case AVG -> {
                __result__=this.getDataValueList().stream().mapToDouble(e->e.getValue()).average().getAsDouble();
                return  __result__;
            }

        }
        return __result__;
    }

}
