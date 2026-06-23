package com.ohgiraffers.test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncodingDecoding {
    public static void main(String[] args) {

        //java 8에서 제공하는 기본 Base64 Encoder와 Decoder
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();

        String testStr = "base54로인코딩한비밀키";
        byte[] encoderArr = testStr.getBytes();

        byte[] encodeByte = encoder.encode(encoderArr);
        String encodeStr = new String(encodeByte);
        System.out.println("encodeStr: " + encodeStr);

        byte[] decodeByte = decoder.decode(encodeStr);
        System.out.println("new String(decodeByte) = " + new String(decodeByte));

        /* HS512를 위한 KeyGenerator 생성 */
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA512");
            keyGenerator.init(512); // 512 비트 키 사이즈 설정

            /* 비밀 키 생성 */
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("secretKey.getEncoded() = " + secretKey.getEncoded());

            /* 키를 Base64로 인코딩하여 문자열로 변환 */
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("HS512 Key : " + encodedKey);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}
