package com.codinghavoc.monolith.cipher.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CipherBody {
    private String message;
    private Long keyOne;
    private Long keyTwo;
    private String action;    
}
