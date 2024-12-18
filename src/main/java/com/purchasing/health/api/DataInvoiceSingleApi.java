package com.purchasing.health.api;

import bsh.EvalError;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purchasing.health.models.*;
import com.purchasing.health.repositories.*;
import com.purchasing.health.services.Engine;
import com.purchasing.health.services.PdfExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices/")
public class DataInvoiceSingleApi {

    @Autowired
    private ZoneSanteRepository zoneSanteRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private AccountBankRepository accountBankRepository;
    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private SettingAmountRepository settingAmountRepository;
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private OrgunitRepository orgunitRepository;
    private PdfExporter exporter;
    @Autowired
    private Engine engine;
    @Autowired
    private DataValueRepository dataValueRepository;
    @PostMapping("zs")
    public void invoiceSingleHealthZone(HttpServletResponse response, @RequestParam("data") String data) throws IOException, EvalError {
        ObjectMapper mapper=new ObjectMapper();
        List<Map<String,Object>>dataMap=mapper.readValue(data,List.class);
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        exporter=new PdfExporter(dataMap);
        String periodPaid= dataMap.get(0).get("year").toString()+dataMap.get(0).get("month").toString()+"25";
        TypeOrgunit typeOrgunit=dataSetRepository.findById(Long.parseLong(dataMap.get(0).get("datasetid").toString())).get().getTypeOrgunit();
        SettingAmount settingAmount=settingAmountRepository.findByStartPeriodeLessThanAndEndPeriodeGreaterThanEqualAndTypeOrgunit(
                Long.parseLong(periodPaid),
                Long.parseLong(periodPaid),
                typeOrgunit
        );
        System.out.println("Setting pay:"+settingAmount.getAmount());
        System.out.println("Type org:"+typeOrgunit.getLibelle());
        System.out.println("Start Date:"+periodPaid);
        System.out.println("End Date:"+periodPaid);
       // System.out.println(settingAmount.getLabel());
        //  System.out.println(dataMap.get(0).get("dataelementname").toString());
        boolean isNotExits=zoneSanteRepository.findById(Long.parseLong(dataMap.get(0).get("idorgunit").toString())).isEmpty();
        ZoneSante zoneSante=null;
        Orgunits orgunits=null;
        Dataset dataset=dataSetRepository.findById(
                Long.parseLong(dataMap.get(0).get("datasetid").toString())
        ).get();
        boolean isCalculation=calculationRepository.findByDatasetidAndStatus(dataset,1).isPresent();
        Calculation calculation=null;
        List<DataValue> dataValueList=null;
        if (isCalculation){
            calculation=calculationRepository.findByDatasetidAndStatus(dataset,1).get();
            dataValueList= (List<DataValue>) dataValueRepository.findByDatasetAndMonthAndYearAndIdorgunitOrderById(
                    dataset,
                    dataMap.get(0).get("month").toString(),
                    Integer.parseInt(dataMap.get(0).get("year").toString()),
                    Long.parseLong(dataMap.get(0).get("idorgunit").toString())

            );
            System.out.println("CALCUALTION FOUND:"+calculation.getFormula());
        }else {
            System.out.println("CALCUALTION NOT FOUND");
        }
        if (!isNotExits){
            zoneSante=zoneSanteRepository.findById(Long.parseLong(dataMap.get(0).get("idorgunit").toString()))
                    .get();
            engine.setCalculation(calculation);
            engine.setDataValueList(dataValueList);
            exporter.setZoneSante(zoneSante);
            exporter.setMonthPaid(dataMap.get(0).get("month").toString());
            exporter.setYear(Integer.parseInt(dataMap.get(0).get("year").toString()));
            exporter.setAccountBank(accountBankRepository.findByIdorgunit(zoneSante.getId()));
            exporter.setSettingAmount(settingAmount);
            exporter.export(response,zoneSante.getIdProvinces(),zoneSante);
            exporter.setAmountCalculed(Double.parseDouble(engine.runEngine()));
            System.out.println("ENGINE VALUE ECZ:"+exporter.getAmountCalculed());
        }else {
            engine.setCalculation(calculation);
            engine.setDataValueList(dataValueList);
            orgunits=orgunitRepository.findById(Long.parseLong(dataMap.get(0).get("idorgunit").toString())).get();
            exporter.setOrgunits(orgunits);
            exporter.setMonthPaid(dataMap.get(0).get("month").toString());
            exporter.setYear(Integer.parseInt(dataMap.get(0).get("year").toString()));
            exporter.setAccountBank(accountBankRepository.findByIdorgunit(orgunits.getId()));
            exporter.setSettingAmount(settingAmount);
            exporter.exportOrg(response,orgunits.getAireSante().getIdProvinces(),orgunits);
            exporter.setAmountCalculed(Double.parseDouble(engine.runEngine()));
            System.out.println("ENGINE VALUE ORG:"+exporter.getAmountCalculed());

        }

       // return  null;
    }
}
