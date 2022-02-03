package tests;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;


public class GenerateSignature {
    public static void main(String[] args) throws Exception {
        GenerateSignature sign = new GenerateSignature();
        sign.generateSignature();
        System.out.println(sign.generateSignature());

    }
    public String generateSignature() throws Exception {

        Date now = new Date();
        String httpMethod = "";         //Provide the required httpMethod
        String pathName = "";           //Provide the required end point url
        long timeStamp = now.getTime();
        long val=(timeStamp/1000)-10;
        System.out.println("timestamp : "+val);
        String timestamp = Long.toString(val);
        String salt = getRandomValue(12);
        String secretKey = "";          //Provide secretKey of environment
        String accesskey = "";          //Provide accessKey of environment
        String unSigned = httpMethod + pathName + salt + timestamp + accesskey + secretKey;
        System.out.println(unSigned);
        String signature = createSignature(unSigned, secretKey);
        return signature;
    }

    public static String getRandomValue(int length){
        String charSet = "abcdefghijklmnopqrstuvwxyz";
        SecureRandom random = new SecureRandom();
        StringBuilder value = new StringBuilder(length);
        for (int i = 0; i < length; i++){
            value.append(charSet.charAt(random
                    .nextInt(charSet.length())));
        }
        return value.toString();
    }

    public static String encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
    }



    public String encodeBase(String data) {

        byte[] encodedBytes = Base64.getEncoder().encode(data.getBytes());
        System.out.println("encodedBytes " + new String(encodedBytes));
        return new String(encodedBytes);


    }

    public String createSignature(String unSigned, String secretKey) throws Exception {
        String hmacsign = encode(secretKey,unSigned);
        String base64sign = encodeBase(hmacsign);
        return base64sign;

    }
}
