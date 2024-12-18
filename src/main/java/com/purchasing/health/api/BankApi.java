package com.purchasing.health.api;

import com.purchasing.health.models.*;
import com.purchasing.health.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/bank/")
public class BankApi {

    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private AccountBankRepository accountBankRepository;
    @Autowired
    private OrgunitRepository orgunitRepository;
    @Autowired
    private ZoneSanteRepository zoneSanteRepository;
    @Autowired
    private OrgunitTypeRepository orgunitTypeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private AireSanteRepository aireSanteRepository;

    @GetMapping("list")
    public Iterable<Bank> getBanks(){
        return bankRepository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Bank> getUniqueBank(@PathVariable (name="id") Long id){
        return bankRepository.findById(id);
    }

    @PostMapping("add")
    public Bank addBank(@RequestParam(name = "name") String name,
                        @RequestParam(name = "codename") String codename,
                        @RequestParam(name = "phone") String phone)
    {
        Bank bank=bankRepository.findByCodename(codename);
        if (bank == null) {
            bank=new Bank();
            bank.setCodename(codename);
            bank.setName(name);
            bank.setPhone(phone);
            bankRepository.save(bank);
        }else{
            bank.setName(name);
            bank.setPhone(phone);
            bankRepository.save(bank);
        }
        return bank;
    }

    @GetMapping("accountlist")
    public Iterable<AccountBank> getAccountBank(){
        return accountBankRepository.findAll();
    }

    @GetMapping("accountbank/{id}")
    public List<AccountBank> getAccountByBank(@PathVariable(name = "id") Long idbank){
        return accountBankRepository.findByBank(bankRepository.findById(idbank).get());
    }

    @PostMapping("accountbank/add")
    public AccountBank addAccountBank(@RequestParam(name = "idbank") Long idbank,
                                      @RequestParam(name = "accountnumber") String accountnumber,
                                      @RequestParam(name = "idorgunit") Long idorgunit,
                                      @RequestParam(name = "status") String status,
                                      @RequestParam(name = "libelle") String libelle)

    {
        Bank bank=bankRepository.findById(idbank).get();
        AccountBank accountBank=accountBankRepository.findByAccountnumber(accountnumber);
        if (accountBank==null){
            accountBank=new AccountBank();
            accountBank.setAccountnumber(accountnumber);
            accountBank.setIdbank(bank);
            accountBank.setIdorgunit(idorgunit);
            accountBank.setLibelle(libelle);
            accountBank.setActivity(status);
            accountBankRepository.save(accountBank);
        }else{
            accountBank.setAccountnumber(accountnumber);
            accountBank.setIdbank(bank);
            accountBank.setIdorgunit(idorgunit);
            accountBank.setActivity(status);
            accountBank.setLibelle(zoneSanteRepository.findById(idorgunit).get().getName());
            accountBankRepository.save(accountBank);
        }
        return  accountBank;
    }

    @GetMapping("accountbank/orgunit/{id}")
    public AccountBank getAccountOrgunit(@PathVariable (name = "id") String id ){
        return  accountBankRepository.findByIdorgunit(Long.parseLong(id));
    }
    @GetMapping("type/{typeid}")
    public Iterable<Object> getSelectTypeObject(@PathVariable(name = "typeid") String typeid){
        TypeOrgunit typeOrgunit=orgunitTypeRepository.findById(Long.parseLong(typeid)).get();
        List<Provinces>provincesList= (List<Provinces>) provinceRepository.findByActivity("O");
        List<ZoneSante>zoneSanteList=new ArrayList<>();
        List<AireSante> aireSanteList=new ArrayList<>();
        for (Provinces p:provincesList){
            zoneSanteList.addAll(
                    (Collection<? extends ZoneSante>) zoneSanteRepository.findByIdProvinces(p)
            );
        }
        for (ZoneSante zs:zoneSanteList){
            aireSanteList.addAll(
                    (Collection<? extends AireSante>) aireSanteRepository.findByZoneSante(zs)
            );
        }

        return Collections.singleton(zoneSanteList);

    }
}
