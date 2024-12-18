package com.purchasing.health.api;



import java.util.HashMap;
import java.util.Map;

import com.purchasing.health.models.Orgunits;
import com.purchasing.health.models.TypeOrgunit;
import com.purchasing.health.repositories.AireSanteRepository;
import com.purchasing.health.repositories.ZoneSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.purchasing.health.repositories.OrgunitRepository;
import com.purchasing.health.repositories.OrgunitTypeRepository;

@RestController
@RequestMapping("api/orgunit/")
public class OrgunitApi {

    @Autowired
    private OrgunitRepository orgunitRepository;
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;
    @Autowired
    private AireSanteRepository aireSanteRepository;
    @Autowired
    private ZoneSanteRepository zoneSanteRepository;

    @GetMapping("typelist")
     public Iterable<TypeOrgunit> getOrgunitType(){
        return orgunitTypeRepository.findAll();
    }
    
     @GetMapping("type/{name}")
     public TypeOrgunit getOrgunit(@PathVariable("name") String name){
        return orgunitTypeRepository.findByLibelle(name);
    }

    @GetMapping("list")
    public Iterable<Orgunits> getOrgunits(){
        return orgunitRepository.findAll();
    }

    /*
    @PostMapping("addtypeorg/{name}")
    public TypeOrgunit addTypeOrgunits(@PathVariable("name") String name){
        TypeOrgunit typeOrgunit=orgunitTypeRepository.findByLibelle(name);
       
        return Objects.isNull(typeOrgunit) ? orgunitTypeRepository.save(typeOrgunit) :typeOrgunit;
    }
     */
     @PostMapping("addtypeorg")
     public Object addTypeOrgunits(@RequestParam("name") String name){
        TypeOrgunit typeOrgunit=orgunitTypeRepository.findByLibelle(name);
        Map<String,Object> mapQuery=new HashMap<>();
        if(typeOrgunit==null){
            typeOrgunit=new TypeOrgunit(name);
            orgunitTypeRepository.save(typeOrgunit);
            mapQuery.put("result", "200");
            mapQuery.put("data", typeOrgunit);
            return typeOrgunit;
        }else{
            mapQuery.put("result", "210");
            mapQuery.put("data", "This Orgunit type already exists, please create another one...");
        }
        return mapQuery;
        
    }


    @GetMapping("list/{typeid}")
    public Iterable<Orgunits> getOrgunitList(@PathVariable(name = "typeid") long typeid){
        return orgunitRepository.findByTypeOrgunit(orgunitTypeRepository.findById(typeid).get());
    }

    @GetMapping("asid/{typeid}/{asid}")
    public Iterable<Orgunits> getOrgunitListByFilter(@PathVariable(name = "typeid") String typeid,@PathVariable(name = "asid") String asid){
         return orgunitRepository.findByTypeOrgunitAndAireSante(
                 orgunitTypeRepository.findById(Long.parseLong(typeid)).get(),
                 aireSanteRepository.findById(Long.parseLong(asid)).get()
         );
    }

    @GetMapping("{id}")
    public Orgunits getOne(@PathVariable(name = "id") String id){
         return  orgunitRepository.findById(Long.parseLong(id)).get();
    }
}
