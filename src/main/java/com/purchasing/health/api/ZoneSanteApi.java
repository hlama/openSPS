package com.purchasing.health.api;

import com.purchasing.health.models.Provinces;
import com.purchasing.health.models.ZoneSante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.purchasing.health.repositories.ProvinceRepository;
import com.purchasing.health.repositories.ZoneSanteRepository;

@RestController
@RequestMapping("/api/healthzone/")
public class ZoneSanteApi {
    
    @Autowired
    private ZoneSanteRepository zoneSanteRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    
    @PostMapping("add")
    public ZoneSante add(
        @RequestParam("codename") String codename,
        @RequestParam("dhis2id") String dhis2id,
        @RequestParam("name") String name,
        @RequestParam("provinceid") String provinceid
    )
        {
            Provinces provinces=provinceRepository.findByDhis2id(provinceid);
            ZoneSante zoneSante=new ZoneSante(dhis2id, codename, name, provinces);
            if (zoneSanteRepository.findByDhis2id(dhis2id)==null) {
                zoneSanteRepository.save(zoneSante);
            }else{
                return zoneSanteRepository.findByDhis2id(dhis2id);
            }
            return zoneSante;
        }
        
        @GetMapping("list")
    public Iterable<ZoneSante> getZoneSante(){
        return zoneSanteRepository.findAll();
    }
    @GetMapping("{id}")
    public ZoneSante getUniqueZoneSante(@PathVariable(name = "id") String id){
        return  zoneSanteRepository.findById(Long.parseLong(id)).get();
    }
}
