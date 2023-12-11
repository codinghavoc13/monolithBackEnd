package com.codinghavoc.monolith.cipher.service;

import com.codinghavoc.monolith.cipher.entity.CipherBody;

public interface CaesarCipherSvc {
    /**
     * Takes a CipherBody object from the front end and determines which
     * encryption/decryption method to forward the CipherBody object to.
     * The encryption/decryption method will return the processed message
     * which is then returned from here back toward the front end.
     * @param cb The unprocessed message body
     * @return The process message body
     */
     public CipherBody process(CipherBody cb);

    /**
     * Take in a CipherBody object. Parse out the clear message and single key. 
     * Encrypt the message, clear out the clear message and key, return the
     * encrypted message
     * @param cb CipherBody object with a clear message and key
     * @return CipherBody object with only an encrypted message
     */
    CipherBody encryptOneKey(CipherBody cb);

    /**
     * Take in a CipherBody object. Parse out the encrypted message and single key. 
     * Encrypt the message, clear out the encrypted message and key, return the
     * clear message
     * @param cb CipherBody object with a encrypted message and key
     * @return CipherBody object with only an clear message
     */
    CipherBody decryptOneKnownKey(CipherBody cb);
    
    /**
     * Take in a CipherBody object. Parse out the encrypted message. 
     * Decrypt the message, clear out the encrypted message, return the
     * clear message
     * @param cb CipherBody object with a encrypted message
     * @return CipherBody object with only a clear message
     */
    CipherBody decryptOneUnknownKey(CipherBody cb);

    /**
     * Take in a CipherBody object. Parse out the clear message and both keys. 
     * Encrypt the message, clear out the clear message and key, return the
     * encrypted message
     * @param cb CipherBody object with a clear message and keys
     * @return CipherBody object with only an encrypted message
     */
    CipherBody encryptTwoKeys(CipherBody cb);

    /**
     * Take in a CipherBody object. Parse out the encrypted message and both keys.
     * Decrypt the message, clear out the encrypted message and key, return the
     * cleared message
     * @param cb CipherBody object with a encrypted message and keys
     * @return CipherBody object with only a cleared message
     */
    CipherBody decryptTwoKnownKeys(CipherBody cb);    
}
