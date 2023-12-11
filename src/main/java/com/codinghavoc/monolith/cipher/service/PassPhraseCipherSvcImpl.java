package com.codinghavoc.monolith.cipher.service;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.Constants;
import com.codinghavoc.monolith.cipher.entity.PassPhraseBody;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PassPhraseCipherSvcImpl implements PassphraseCipherSvc {

    @Override
    public PassPhraseBody encrypt(PassPhraseBody body) {
        String message = body.getMessage();
        String passphrase = body.getPassphrase();
        int j = 0; //used to get the corresponding char from passphrase
        char start; //the character being shifted
        char shift; //the character that will be used to shift
        int z; //the index of the new character
        char c; //the shifted character to add to the new, encrypted message
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < message.length(); i++){
            if(i>=passphrase.length()){
                j = i%passphrase.length();
            } else {
                j = i;
            }
            start = message.charAt(i);
            shift = passphrase.charAt(j);
            z = Constants.TOTAL_ALPHABET.indexOf(start) + (Constants.TOTAL_ALPHABET.indexOf(shift) + 1);
            if(z >= Constants.TOTAL_ALPHABET.length())z-=Constants.TOTAL_ALPHABET.length();
            c = Constants.TOTAL_ALPHABET.charAt(z);
            sb.append(c);
        }
        return new PassPhraseBody(sb.toString(),"");
    }

    @Override
    public PassPhraseBody decrypt(PassPhraseBody body) {
        String message = body.getMessage();
        String passphrase = body.getPassphrase();
        int j = 0; //used to get the corresponding char from passphrase
        char start; //the character being shifted
        char shift; //the character that will be used to shift
        int z; //the index of the new character
        char c; //the shifted character to add to the new, encrypted message
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < message.length(); i++){
            if(i>=passphrase.length()){
                j = i%passphrase.length();
            } else {
                j = i;
            }
            start = message.charAt(i);
            shift = passphrase.charAt(j);
            z = Constants.TOTAL_ALPHABET.indexOf(start) - (Constants.TOTAL_ALPHABET.indexOf(shift) + 1);
            if(z < 0 )z+=Constants.TOTAL_ALPHABET.length();
            c = Constants.TOTAL_ALPHABET.charAt(z);
            sb.append(c);
        }
        return new PassPhraseBody(sb.toString(),"");
    }
    
}
