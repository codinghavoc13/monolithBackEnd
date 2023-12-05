package com.codinghavoc.monolith.service;

import com.codinghavoc.monolith.entity.PassPhraseBody;

public interface PassphraseCipherSvc {
    String encrypt(PassPhraseBody body);
    String decrypt(PassPhraseBody body);
}
