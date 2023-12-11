package com.codinghavoc.monolith.cipher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.cipher.entity.CipherBody;
import com.codinghavoc.monolith.cipher.service.CaesarCipherSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/caesarCipher")
public class CaesarCipherController {
    CaesarCipherSvc ccSvc;

    @GetMapping("/test")
    public String sayHello(){
        return "It works";
    }

    @PostMapping("/process")
    public ResponseEntity<CipherBody> process(@RequestBody CipherBody cb){
        return new ResponseEntity<>(ccSvc.process(cb), HttpStatus.OK);
    }
    
}
