package com.purchasing.health.web;
import java.io.IOException;
import java.util.*;

import com.purchasing.health.models.*;
import com.purchasing.health.repositories.*;
import com.purchasing.health.services.Engine;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RouterWeb {
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;
    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private DataElementRepository dataElementRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private ZoneSanteRepository zoneSanteRepository;
    @Autowired
    private AireSanteRepository aireSanteRepository;
    @Autowired
    private OrgunitRepository orgunitRepository;
    @Autowired
    private SettingAmountRepository settingAmountRepository;
    @Autowired
    private AccountBankRepository accountBankRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private CalculationRepository calculationRepository;
    @Autowired
    private Engine engine;
    @GetMapping("/")
    public String HomePage(){
        engine.createDictionnary();
        return "views/users/login";
    }
    @PostMapping()
    public ModelAndView Homepage(){
        ModelAndView modelAndView=new ModelAndView("redirect:/orgunits/province/add");
        return modelAndView;
    }

    @GetMapping("/login")
    public String LoginPage(){
        return "views/users/login";
    }

    @GetMapping("/dataset/add")
    public ModelAndView DataSetAdd(){
        ModelAndView modelAndView=new ModelAndView("views/dataset/add");
        modelAndView.addObject("title", "Creer un DataSet");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
         modelAndView.addObject("datasets", dataSetRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());

       // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
       //model.addAttribute("test", "Hornel");
        return modelAndView;
    }

        @GetMapping("/dataelement/add")
    public ModelAndView DataElementAdd(){
         ModelAndView modelAndView=new ModelAndView("views/dataelement/add");
         modelAndView.addObject("title", "Creer des prestations");
         modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
         modelAndView.addObject("datasets", dataSetRepository.findAll());
         modelAndView.addObject("dataelements", dataElementRepository.findAll());
         modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
       // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
       //model.addAttribute("test", "Hornel");
        return modelAndView;
    }


    @GetMapping("/bank/add")
    public ModelAndView BankAdd(){
        ModelAndView modelAndView=new ModelAndView("views/bank/add");
        modelAndView.addObject("title", "Ajouter une nouvelle banque ");
        modelAndView.addObject("subtitle", "Configurer une banque pour la facture des structures du projet ");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("datasets", dataSetRepository.findAll());
        modelAndView.addObject("dataelements", dataElementRepository.findAll());
        modelAndView.addObject("banks",bankRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());

        // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
        //model.addAttribute("test", "Hornel");
        return modelAndView;
    }


    @GetMapping("/coasting/add")
    public ModelAndView CoastingAdd(){
        ModelAndView modelAndView=new ModelAndView("views/coasting/add");
        modelAndView.addObject("title", "Configurer le coasting ");
        modelAndView.addObject("subtitle", "Fixer le cout pour chaque formulaire");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("datasets", dataSetRepository.findAll());
        // modelAndView.addObject("dataelements", dataElementRepository.findAll());
        modelAndView.addObject("banks",bankRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
        modelAndView.addObject("coastings", settingAmountRepository.findAll());

        // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
        //model.addAttribute("test", "Hornel");
        return modelAndView;
    }

    @GetMapping("/calculate/add")
    public ModelAndView CalculationAdd(){
        ModelAndView modelAndView=new ModelAndView("views/calculation/add");
        modelAndView.addObject("title", "Ajouter une regle de calcule ");
        modelAndView.addObject("subtitle", "Configurer une regle de calcule pour les formulaires");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("datasets", dataSetRepository.findAll());
     // modelAndView.addObject("dataelements", dataElementRepository.findAll());
        //modelAndView.addObject("banks",bankRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
       // modelAndView.addObject("coastings", settingAmountRepository.findAll());
        modelAndView.addObject("calculations", calculationRepository.findAll());

        // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
        //model.addAttribute("test", "Hornel");
        return modelAndView;
    }
    @GetMapping("/accountbank/add")
    public ModelAndView BankAddAccount(){
        Iterable<Provinces>provincesActive=provinceRepository.findByActivity("O");
        List<ZoneSante>zoneSanteListConfigured=new ArrayList<>();
        List<AireSante> aireSanteList=new ArrayList<>();
        List<Orgunits> orgunitsList=new ArrayList<>();
        for (Provinces p : provincesActive) {
            List<ZoneSante>zoneSanteList= (List<ZoneSante>) zoneSanteRepository.findByIdProvinces(p);
            zoneSanteListConfigured.addAll(zoneSanteList);
        }
        for (ZoneSante zs : zoneSanteListConfigured){
            aireSanteList.addAll(
                    (Collection<? extends AireSante>) aireSanteRepository.findByZoneSante(zs)
            );
        }
        for (AireSante as:aireSanteList){
            orgunitsList.addAll(
                    (Collection<? extends Orgunits>) orgunitRepository.findByAireSante(as)
            );
        }
        Map<String,Object> mapData=new HashMap<>();
        mapData.put("zonelist",zoneSanteListConfigured);
        mapData.put("aslist",aireSanteList);
        mapData.put("orglist",orgunitsList);
        //System.out.println("HGR Count:"+orgunitsList.stream().filter(orgunits -> orgunits.getTypeOrgunit().getId()==3).count());
        ModelAndView modelAndView=new ModelAndView("views/bank/add-account");
        modelAndView.addObject("title", "Creer compte bancaire ");
        modelAndView.addObject("subtitle", "Configurer un compte bancaire pour une structure ");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("zonelist", zoneSanteListConfigured);
        modelAndView.addObject("banks",bankRepository.findAll());
        modelAndView.addObject("accountbank",accountBankRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
        modelAndView.addObject("mapdata", mapData);


        // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
        //model.addAttribute("test", "Hornel");
        return modelAndView;
    }
    @GetMapping("/orgunits/province/add")
    public ModelAndView ProvinceAdd(){
        ModelAndView modelAndView=new ModelAndView("views/orgunits/provinces/add");
        modelAndView.addObject("title", "Ajouter Provinces");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
         modelAndView.addObject("datasets", dataSetRepository.findAll());
         modelAndView.addObject("dataelements", dataElementRepository.findAll());
         modelAndView.addObject("provinces", provinceRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());

       // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
       //model.addAttribute("test", "Hornel");
        return modelAndView;
    }

    @GetMapping("/orgunits/zs/add")
    public ModelAndView ZsAdd(ModelAndView modelAndView){
        modelAndView=new ModelAndView("views/orgunits/zs/add");
        modelAndView.addObject("title", "Ajouter Zones de sante");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("datasets", dataSetRepository.findAll());
        modelAndView.addObject("dataelements", dataElementRepository.findAll());
        modelAndView.addObject("zs", zoneSanteRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
        String test="/pL5A7C1at1M/rWrCdr321Qu/gjyO7pgLjyk";
        System.out.println(test.split("/")[2]);
       // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
       //model.addAttribute("test", "Hornel");
        return modelAndView;
    }

    // REVOIR L'OBJET TypeOrgunit ui est renvoyee 2 fois
    @GetMapping("/orgunits/as/add")
    public ModelAndView AsAdd(ModelAndView modelAndView){
        modelAndView=new ModelAndView("views/orgunits/as/add");
        modelAndView.addObject("title", "Ajouter Aire de Sante");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("datasets", dataSetRepository.findAll());
        modelAndView.addObject("dataelements", dataElementRepository.findAll());
        modelAndView.addObject("as", aireSanteRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
        String test="/pL5A7C1at1M/rWrCdr321Qu/gjyO7pgLjyk";
        System.out.println(test.split("/")[2]);
        // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
        //model.addAttribute("test", "Hornel");
        return modelAndView;
    }


    @GetMapping("/orgunits/fosa/add")
    public ModelAndView FosaAdd(ModelAndView modelAndView){
        modelAndView=new ModelAndView("views/orgunits/fosa/add");
        modelAndView.addObject("title", "Ajouter FOSA");
        modelAndView.addObject("type_orgunits", orgunitTypeRepository.findAll());
        modelAndView.addObject("datasets", dataSetRepository.findAll());
        modelAndView.addObject("dataelements", dataElementRepository.findAll());
        modelAndView.addObject("fosa", orgunitRepository.findAll());
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());

        // model.addAttribute("orgunitytpe",(List<TypeOrgunit>)orgunitTypeRepository.findAll());
        //model.addAttribute("test", "Hornel");
        return modelAndView;
    }

    @PostMapping("/orgunits/province/add")
    public ModelAndView ProvinceAdd(@RequestParam("orgunits") MultipartFile file,ModelAndView modelAndView) throws IOException {
        if (!file.isEmpty()){
            List<Provinces> provincesList=new ArrayList<>();
            modelAndView=new ModelAndView("views/orgunits/provinces/add");
            XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                Provinces provinces = new Provinces();
                XSSFRow row = worksheet.getRow(i);
                provinces.setDhis2id(row.getCell(0).getStringCellValue());
                provinces.setCodename(row.getCell(1).getStringCellValue());
                provinces.setName(row.getCell(2).getStringCellValue());
                provinces.setActivity(row.getCell(3).getStringCellValue());
               // System.out.println(String.format("Province:%s et Statut:%",provinces.getCodename(),provinces.getActivity()));
                provincesList.add(provinces);
                Provinces pFind=provinceRepository.findByDhis2id(provinces.getDhis2id());
                if (pFind==null){
                    provinceRepository.save(provinces);
                    provincesList.add(provinces);
                }else {
                    pFind.setActivity(provinces.getActivity());
                    pFind.setName(provinces.getName());
                    pFind.setCodename(provinces.getCodename());
                    provinceRepository.save(pFind);
                    provincesList.add(provinces);
                }
            }
          //  provinceRepository.saveAll(provincesList);
        }
        modelAndView.setViewName("redirect:/orgunits/province/add");
        return modelAndView;
    }


    @PostMapping("/orgunits/zs/add")
    public ModelAndView ZsAdd(@RequestParam("orgunits") MultipartFile file,ModelAndView modelAndView) throws IOException {
          modelAndView=new ModelAndView("views/orgunits/zs/add");
            if (!file.isEmpty()) {
                List<ZoneSante> ZoneSanteList=new ArrayList<>();
                XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
                XSSFSheet worksheet = workbook.getSheetAt(0);
                for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                    XSSFRow row = worksheet.getRow(i);
                    String dhis2idProvince=row.getCell(3).getStringCellValue().split("/")[2];
                    Provinces provinces=provinceRepository.findByDhis2id(dhis2idProvince);
                    ZoneSante zs=new ZoneSante();
                    zs.setDhis2id(row.getCell(0).getStringCellValue());
                    zs.setCodename(row.getCell(1).getStringCellValue());
                    zs.setName(row.getCell(2).getStringCellValue());
                    zs.setIdProvinces(provinces);
                    ZoneSante zsFind=zoneSanteRepository.findByDhis2id(zs.getDhis2id());
                    if (zsFind==null) {
                        zoneSanteRepository.save(zs);
                        ZoneSanteList.add(zs);
                    } else {
                        zsFind.setCodename(zs.getCodename());
                        zsFind.setName(zs.getName());
                        zsFind.setIdProvinces(provinces);
                        zoneSanteRepository.save(zsFind);
                        ZoneSanteList.add(zsFind);
                    }
                }
                modelAndView.addObject("zs", ZoneSanteList);

        }
        modelAndView.setViewName("redirect:/orgunits/zs/add");
            return modelAndView;
    }


    @PostMapping("/orgunits/as/add")
    public ModelAndView AsAdd(@RequestParam("orgunits") MultipartFile file,ModelAndView modelAndView) throws IOException {
        modelAndView=new ModelAndView("views/orgunits/as/add");
        if (!file.isEmpty()) {
            List<AireSante> aireSanteList=new ArrayList<>();
            XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                XSSFRow row = worksheet.getRow(i);
                String dhis2idProvince=row.getCell(3).getStringCellValue().split("/")[2];
                String dhis2idZoneSante=row.getCell(3).getStringCellValue().split("/")[3];
                Provinces provinces=provinceRepository.findByDhis2id(dhis2idProvince);
                ZoneSante zsId=zoneSanteRepository.findByDhis2id(dhis2idZoneSante);
                AireSante as=new AireSante();
                as.setDhis2id(row.getCell(0).getStringCellValue());
                as.setCodename(row.getCell(1).getStringCellValue());
                as.setName(row.getCell(2).getStringCellValue());
                as.setZoneSante(zsId);
                as.setIdProvinces(provinces);

                AireSante asFind=aireSanteRepository.findByDhis2id(as.getDhis2id());
                if (asFind==null) {
                    aireSanteRepository.save(as);
                    aireSanteList.add(as);

                } else {
                    asFind.setCodename(as.getCodename());
                    asFind.setName(as.getName());
                    asFind.setIdProvinces(provinces);
                    aireSanteRepository.save(asFind);
                    aireSanteList.add(asFind);
                }
            }
            modelAndView.addObject("as", aireSanteList);

        }
        modelAndView.setViewName("redirect:/orgunits/as/add");
        return modelAndView;
    }



    @PostMapping("/orgunits/fosa/add")
    public ModelAndView FosaAdd(@RequestParam("orgunits") MultipartFile file,ModelAndView modelAndView) throws IOException {

        if (!file.isEmpty()) {
            List<AireSante> aireSanteList=new ArrayList<>();
            List<Orgunits> orgunitsList=new ArrayList<>();
            XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);
            for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
                XSSFRow row = worksheet.getRow(i);
                //String dhis2idProvince=row.getCell(3).getStringCellValue().split("/")[2];
                //String dhis2idZoneSante=row.getCell(3).getStringCellValue().split("/")[3];
                String dhis2idAireSante=row.getCell(3).getStringCellValue().split("/")[4];
                String categoryOrgunit=row.getCell(4).getStringCellValue();
                //System.out.println(categoryOrgunit);
                //Provinces provinces=provinceRepository.findByDhis2id(dhis2idProvince);
                //ZoneSante zsId=zoneSanteRepository.findByDhis2id(dhis2idZoneSante);
                AireSante aireSante=aireSanteRepository.findByDhis2id(dhis2idAireSante);
                Orgunits orgunits=new Orgunits();
                TypeOrgunit typeOrgunit=orgunitTypeRepository.findByLibelle(categoryOrgunit);

               // as.setIdProvinces(provinces);
                if (aireSante==null){
                    System.out.printf("%s nullable%n",dhis2idAireSante);
                    System.out.println("Categorie FOSA:"+categoryOrgunit);
                    System.out.println("NOM FOSA:"+row.getCell(1).getStringCellValue());
                    //System.out.println(typeOrgunit.getLibelle());
                }else{
                    orgunits.setDhis2id(row.getCell(0).getStringCellValue());
                    orgunits.setName(row.getCell(1).getStringCellValue());
                    orgunits.setTypeOrgunit(typeOrgunit);
                    orgunits.setAireSante(aireSante);
                    Orgunits fosaFind=orgunitRepository.findByDhis2id(orgunits.getDhis2id());
                    if (fosaFind==null) {
                        orgunitRepository.save(orgunits);
                        orgunitsList.add(orgunits);
                        System.out.printf("%s added%n",orgunits.getName());

                    } else {
                        System.out.printf("%s exists%n",orgunits.getName());
                        fosaFind.setName(orgunits.getName());
                        fosaFind.setAireSante(aireSante);
                        fosaFind.setTypeOrgunit(typeOrgunit);
                        //   asFind.setIdProvinces(provinces);
                        orgunitRepository.save(fosaFind);
                        orgunitsList.add(fosaFind);
                    }
                }

            }
            modelAndView.addObject("fosa", orgunitsList);

        }
        modelAndView.setViewName("redirect:/orgunits/fosa/add");
        return modelAndView;
    }
    @GetMapping("/dataentry/{orgunittype}")
    public ModelAndView dataentry(ModelAndView modelAndView,@PathVariable("orgunittype") String orgunitype){
        TypeOrgunit typeOrgunit=orgunitTypeRepository.findById(Long.parseLong(orgunitype)).get();
        List<Orgunits> orgunitsConfigured=new ArrayList<>();
        Iterable<Provinces>provincesActive=null;
        provincesActive=provinceRepository.findByActivity("O");
        List<ZoneSante>zoneSanteList=new ArrayList<>();
        List<AireSante>aireSanteList=new ArrayList<>();
        List<Orgunits>orgunitsList=new ArrayList<>();
        List<ZoneSante>zoneSanteListConfigured=new ArrayList<>();
        List<Object>data=new ArrayList<>();
        for (Provinces p : provincesActive) {
                zoneSanteListConfigured.addAll(
                        (Collection<? extends ZoneSante>) zoneSanteRepository.findByIdProvinces(p)
                );

            //zoneSanteRepository.find
            //  zoneSanteList.addAll((Collection<? extends ZoneSante>) zoneSanteRepository.findByIdProvinces(p));
        }
        for (ZoneSante zs:zoneSanteListConfigured){
            aireSanteList.addAll(
                    (Collection<? extends AireSante>) aireSanteRepository.findByZoneSante(zs)
            );
        }
        for (Long l: accountBankRepository.IdOrgunits()) {
            String nameOrg=accountBankRepository.findByIdorgunit(l).getLibelle();
            boolean isNotExist=zoneSanteRepository.findByIdAndName(l,nameOrg).isEmpty();
            if (!isNotExist){
                System.out.println("ZS:"+zoneSanteRepository.findByIdAndName(l,nameOrg).get().getName());
                zoneSanteList.add(
                        zoneSanteRepository.findByIdAndName(l,nameOrg).get()
                );

            }
        }

        for (AireSante as:aireSanteList){

            orgunitsConfigured.addAll(
                    (Collection<? extends Orgunits>) orgunitRepository.findByTypeOrgunitAndAireSante(
                            typeOrgunit,
                            as
                    )
            );
        }
        if (!orgunitsConfigured.isEmpty()){
            for (long l :accountBankRepository.IdOrgunits()){
                String nameOrg=accountBankRepository.findByIdorgunit(l).getLibelle();
                boolean isNotExist=orgunitRepository.findByIdAndNameAndTypeOrgunit(l,nameOrg,typeOrgunit).isEmpty();
                if (!isNotExist){
                    orgunitsList.add(
                            orgunitRepository.findByIdAndNameAndTypeOrgunit(l,nameOrg,typeOrgunit).get()
                    );

                }
            }
        }
        if (orgunitsList.isEmpty()){
            data= Collections.singletonList(zoneSanteList);
        }else{

            data=Collections.singletonList(orgunitsList);
        }

        modelAndView=new ModelAndView("views/dataentry/form");

        modelAndView.addObject("datasets", dataSetRepository.findByTypeOrgunit(
                orgunitTypeRepository.findById(Long.parseLong(orgunitype)).get()
        ));
        modelAndView.addObject("category", typeOrgunit);
        modelAndView.addObject("zonelist", zoneSanteList);
        modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
        modelAndView.addObject("mapdata",data.get(0));

        return modelAndView;
    }


     @GetMapping("/invoice/{orgunittype}")
    public ModelAndView invoice(ModelAndView modelAndView,@PathVariable("orgunittype") String orgunitype){
         TypeOrgunit typeOrgunit=orgunitTypeRepository.findById(Long.parseLong(orgunitype)).get();
         List<Orgunits> orgunitsConfigured=new ArrayList<>();
         Iterable<Provinces>provincesActive=null;
         provincesActive=provinceRepository.findByActivity("O");
         List<ZoneSante>zoneSanteList=new ArrayList<>();
         List<AireSante>aireSanteList=new ArrayList<>();
         List<Orgunits>orgunitsList=new ArrayList<>();
         List<ZoneSante>zoneSanteListConfigured=new ArrayList<>();
         List<Object>data=new ArrayList<>();
         for (Provinces p : provincesActive) {
             zoneSanteListConfigured.addAll(
                     (Collection<? extends ZoneSante>) zoneSanteRepository.findByIdProvinces(p)
             );

             //zoneSanteRepository.find
             //  zoneSanteList.addAll((Collection<? extends ZoneSante>) zoneSanteRepository.findByIdProvinces(p));
         }
         for (ZoneSante zs:zoneSanteListConfigured){
             aireSanteList.addAll(
                     (Collection<? extends AireSante>) aireSanteRepository.findByZoneSante(zs)
             );
         }
         for (Long l: accountBankRepository.IdOrgunits()) {
             String nameOrg=accountBankRepository.findByIdorgunit(l).getLibelle();
             boolean isNotExist=zoneSanteRepository.findByIdAndName(l,nameOrg).isEmpty();
             if (!isNotExist){
                 System.out.println("ZS:"+zoneSanteRepository.findByIdAndName(l,nameOrg).get().getName());
                 zoneSanteList.add(
                         zoneSanteRepository.findByIdAndName(l,nameOrg).get()
                 );

             }
         }

         for (AireSante as:aireSanteList){

             orgunitsConfigured.addAll(
                     (Collection<? extends Orgunits>) orgunitRepository.findByTypeOrgunitAndAireSante(
                             typeOrgunit,
                             as
                     )
             );
         }
         if (!orgunitsConfigured.isEmpty()){
             for (long l :accountBankRepository.IdOrgunits()){
                 String nameOrg=accountBankRepository.findByIdorgunit(l).getLibelle();
                 boolean isNotExist=orgunitRepository.findByIdAndNameAndTypeOrgunit(l,nameOrg,typeOrgunit).isEmpty();
                 if (!isNotExist){
                     orgunitsList.add(
                             orgunitRepository.findByIdAndNameAndTypeOrgunit(l,nameOrg,typeOrgunit).get()
                     );

                 }
             }
         }
         if (orgunitsList.isEmpty()){
             data= Collections.singletonList(zoneSanteList);
         }else{

             data=Collections.singletonList(orgunitsList);
         }

         modelAndView=new ModelAndView("views/invoices/invoice");

         modelAndView.addObject("datasets", dataSetRepository.findByTypeOrgunit(
                 orgunitTypeRepository.findById(Long.parseLong(orgunitype)).get()
         ));
         modelAndView.addObject("category", typeOrgunit);
         modelAndView.addObject("zonelist", zoneSanteList);
         modelAndView.addObject("typelist", orgunitTypeRepository.findAll());
         modelAndView.addObject("mapdata",data.get(0));

         return modelAndView;
    }
}
