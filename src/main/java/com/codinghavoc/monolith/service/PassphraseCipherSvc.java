package com.codinghavoc.monolith.service;

import com.codinghavoc.monolith.entity.PassPhraseBody;

public interface PassphraseCipherSvc {
    PassPhraseBody encrypt(PassPhraseBody body);
    PassPhraseBody decrypt(PassPhraseBody body);
}
