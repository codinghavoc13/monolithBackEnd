package com.codinghavoc.monolith.cipher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PassPhraseBody {
    private String message;
    private String passphrase;
    
}
