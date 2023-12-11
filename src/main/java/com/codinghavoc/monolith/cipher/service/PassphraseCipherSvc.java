package com.codinghavoc.monolith.cipher.service;

import com.codinghavoc.monolith.cipher.entity.PassPhraseBody;

public interface PassphraseCipherSvc {
    PassPhraseBody encrypt(PassPhraseBody body);
    PassPhraseBody decrypt(PassPhraseBody body);
}
