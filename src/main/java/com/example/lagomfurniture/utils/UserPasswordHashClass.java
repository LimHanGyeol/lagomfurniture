package com.example.lagomfurniture.utils;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class UserPasswordHashClass {
    // 비밀번호 SHA 256 로 암호화
    public String getSHA256(String plainText) {
        String shaString = "";

        try {
            MessageDigest encrypt = MessageDigest.getInstance("SHA-256");
            encrypt.update(plainText.getBytes());
            byte byteData[] = encrypt.digest();
            StringBuffer stringBuffer = new StringBuffer();
            int byteSize = byteData.length;

            for (int i = 0; i < byteSize; i++) {
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            shaString = stringBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            shaString = null;
        }
        return shaString;
    }
}
