package com.codinghavoc.monolith.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codinghavoc.monolith.entity.PassPhraseBody;
import com.codinghavoc.monolith.service.PassphraseCipherSvc;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/passphraseCipher")
public class PassPhraseCipherController {
    PassphraseCipherSvc ppsvc;
    
    @PostMapping("/encrypt")
    public ResponseEntity<PassPhraseBody> encrypt(@RequestBody PassPhraseBody ppb){
        return new ResponseEntity<>(ppsvc.encrypt(ppb), HttpStatus.OK);
    }
    
    @PostMapping("/decrypt")
    public ResponseEntity<PassPhraseBody> decrypt(@RequestBody PassPhraseBody ppb){
        return new ResponseEntity<>(ppsvc.decrypt(ppb), HttpStatus.OK);
    }
}
