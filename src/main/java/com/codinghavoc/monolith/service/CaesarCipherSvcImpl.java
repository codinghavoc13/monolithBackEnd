package com.codinghavoc.monolith.service;

import org.springframework.stereotype.Service;

import com.codinghavoc.monolith.entity.CipherBody;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CaesarCipherSvcImpl implements CaesarCipherSvc{
    private static String LOWER_ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String NUMBERS = "0123456789";
    private static String TOTAL_ALPHABET = LOWER_ALPHA + UPPER_ALPHA + NUMBERS;

    public CipherBody process(CipherBody cb){
        CipherBody result = switch(cb.getAction()){
            case "E1K" -> encryptOneKey(cb);
            case "E2K" -> encryptTwoKeys(cb);
            case "D1K" -> decryptOneKnownKey(cb);
            case "D2K" -> decryptTwoKnownKeys(cb);
            default -> {
                result = new CipherBody();
                result.setMessage("Incorrect action selected");
                yield result;
            }
        };
        return result;
    }

    @Override
    public CipherBody encryptOneKey(CipherBody cb) {
        //Encrypt the message with the single key
        String encryptedMsg = encrypt(cb.getKeyOne(),cb.getMessage());

        //Store the encrypted message
        cb.setMessage(encryptedMsg);

        //Returning the encrypted message...
        return cb;
    }

    @Override
    public CipherBody decryptOneKnownKey(CipherBody cb){
        //Decrypt the message with the single key
        String decryptedMsg = encrypt(26l - cb.getKeyOne(), cb.getMessage());

        //Store the decrypted message
        cb.setMessage(decryptedMsg);

        //Returning the clear text message...
        return cb;
    }

    @Override
    public CipherBody decryptOneUnknownKey(CipherBody cb){
        //Decrypting the message...
        String decryptedMsg = decryptUnkKey(cb.getMessage());

        //Storing the decrypted message as the clear message
        cb.setMessage(decryptedMsg);

        //Returning the clear text message...
        return cb;
    }

    public CipherBody encryptTwoKeys(CipherBody cb){
        //Encrypt the message with the single key
        String encryptedMsg = encrypt2key(26l - cb.getKeyOne(), 26l - cb.getKeyTwo(), cb.getMessage());

        //Store the encrypted message
        cb.setMessage(encryptedMsg);

        //Returning the encrypted message...
        return cb;
    }

    public CipherBody decryptTwoKnownKeys(CipherBody cb){
        //Decrypt the message with the single key
        String decryptedMsg = encrypt2key(cb.getKeyOne(), cb.getKeyTwo(), cb.getMessage());

        //Store the decrypted message
        cb.setMessage(decryptedMsg);

        //Returning the clear text message...
        return cb;
    }
	
	private String shiftedAlpha(Long key){
        int i = key.intValue();
        String shifted = TOTAL_ALPHABET.substring(i)+TOTAL_ALPHABET.substring(0,i);
        return shifted;
	}
	
	private String encrypt(Long key, String input){
		String encr = "";
		String lowerMsg = input.toLowerCase();
        String shiftedAlphabet = shiftedAlpha(key);
        for(int ctr = 0; ctr < lowerMsg.length();ctr++){
            char currChar = lowerMsg.charAt(ctr);
            int idx = TOTAL_ALPHABET.indexOf(currChar);
            if(idx==-1){
            	encr+=currChar;
            }
            if(idx!=-1){
                char newChar = shiftedAlphabet.charAt(idx);
                encr += newChar;
            }
        }
        return encr;
    }
	
	private String encrypt2key(Long key1, Long key2, String input){
		String encr = "";
		String lowerMsg = input.toLowerCase();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String shiftedAlphabet1 = shiftedAlpha(key1);
        String shiftedAlphabet2 = shiftedAlpha(key2);
        for(int ctr = 0; ctr < lowerMsg.length();ctr++){
        	char currChar = lowerMsg.charAt(ctr);
            int idx = alphabet.indexOf(currChar);
            if(idx==-1){
            	encr+=currChar;
            }
            if(idx!=-1){
                if (ctr%2==0){
                    char newChar = shiftedAlphabet1.charAt(idx);
                    encr += newChar;
                }
                if (ctr%2==1){
                    char newChar = shiftedAlphabet2.charAt(idx);
                    encr += newChar;
                }
            }
        }
        return encr;
    }
    
    /*
     * TODO This one needs to be either reworked or removed
     */
    private String decryptUnkKey(String input){
		String result = "None Found";
        String[] bigrams = {"bk","fq","jc","jt","mj","qh","qx","vj","wz","zh","bq","fv","jd","jv","mq","qj","qy","vk","xb","zj","bx","fx","jf","jw","mx","qk","qz","vm","xg","zn","cb","fz","jg","jx","mz","ql","sx","vn","xj","zq","cf","gq","jh","jy","pq","qm","sz","vp","xk","zr","cg","gv","jk","jz","pv","qn","tq","vq","xv","zs","cj","gx","jl","kq","px","qo","tx","vt","xz","zx","cp","hk","jm","kv","qb","qp","vb","vw","yq","cv","hv","jn","kx","qc","qr","vc","vx","yv","cw","hx","jp","kz","qd","qs","vd","vz","yz","cx","hz","jq","lq","qe","qt","vf","wq","zb","dx","iy","jr","lx","qf","qv","vg","wv","zc","fk","jb","js","mg","qg","qw","vh","wx","zg"};
        for (int key1 = 0; key1<26;key1++){
        	String decr = "";
            String shiftedAlphabet = TOTAL_ALPHABET.substring(key1)+TOTAL_ALPHABET.substring(0,key1);
            for (int ctr = 0; ctr<input.length();ctr++){
                char currChar = input.charAt(ctr);
                int idx = TOTAL_ALPHABET.indexOf(currChar);
                if(idx==-1){
                	decr+=currChar;
                }
                if (idx !=-1){
                    char newChar = shiftedAlphabet.charAt(idx);
                    decr += newChar;
                }
            }
            int bgCheck = 0;
            for (int bg = 0; bg<bigrams.length; bg++){
                int check = decr.indexOf(bigrams[bg]);
                if(check != -1){ //if the bigram is present
                    bgCheck++;
                    continue;
                }
            }
            if (bgCheck==0){
                result = decr;
            }
        }
        return result;
    }
    
}
