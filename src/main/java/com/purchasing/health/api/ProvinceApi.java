package com.purchasing.health.api;

import com.purchasing.health.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@RequestMapping("/api/provinces/")
public class ProvinceApi {

    @Autowired
    private ProvinceRepository provinceRepository;
}
