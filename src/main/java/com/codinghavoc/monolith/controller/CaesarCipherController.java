package com.codinghavoc.monolith.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.entity.CipherBody;
import com.codinghavoc.monolith.service.CaesarCipherSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/caesarCipher")
public class CaesarCipherController {
    CaesarCipherSvc ccSvc;

    @PostMapping("/process")
    public ResponseEntity<CipherBody> process(@RequestBody CipherBody cb){
        return new ResponseEntity<>(ccSvc.process(cb), HttpStatus.OK);
    }
    
}
