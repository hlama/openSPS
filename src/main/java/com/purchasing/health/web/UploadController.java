package com.purchasing.health.web;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.purchasing.health.models.Provinces;
import com.purchasing.health.repositories.ProvinceRepository;

@Controller
public class UploadController {
 
 @Autowired
 ProvinceRepository provinceRepository;   
 static String[] HEADERs = { "dhis2id", "codename", "name" };
 @GetMapping("/province/adding")
public String HomeUpload(){
    return "upload";
}

 @PostMapping("/province/adding")   
 public String UploadCSVProvince(@RequestParam("file") MultipartFile file,Model model ){
    if (!file.isEmpty()) {
        try {
            
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            
            List<Provinces> ProvinceList=new ArrayList<>();
            for (CSVRecord csvRecord : csvRecords) {    
                Provinces provinces=new Provinces();
                provinces.setDhis2id(csvRecord.get("dhis2id"));
                provinces.setCodename(csvRecord.get("codename"));
                provinces.setName(csvRecord.get("name"));
                ProvinceList.add(provinces);
                Provinces pFind=provinceRepository.findByDhis2id(provinces.getDhis2id());
                if (pFind==null) {
                    provinceRepository.save(provinces);
                } else {
                    pFind.setCodename(provinces.getCodename());
                    pFind.setName(provinces.getName());
                    provinceRepository.save(pFind);

                }

               // System.out.println(provinces.getName());

            }
            
            provinceRepository.saveAll(ProvinceList);
            model.addAttribute("provinces", ProvinceList);
        }catch(Exception e){
                 model.addAttribute("provinces", e.getMessage());
        }

        }
  return "upload";
 }
}
