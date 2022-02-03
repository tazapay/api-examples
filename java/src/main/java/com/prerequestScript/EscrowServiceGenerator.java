package com.prerequestScript;

import com.core.ClientConfig;
import com.core.ServiceGenerator;
import lombok.SneakyThrows;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static com.commons.ContextManager.getTestContext;



public class EscrowServiceGenerator extends ServiceGenerator<EscrowServiceClient> {

    public EscrowServiceGenerator() {
        super(config());
    }

    public EscrowServiceClient createService() {
        return createService(EscrowServiceClient.class);
    }

    @Override
    public List<Interceptor> interceptorList(long time){
        String timeStamp = String.valueOf((new Date().getTime()/1000)-10);
        String salt = RandomStringUtils.randomAlphabetic(12);;
        String accessKey = getTestContext().getAttribute("com.tazapay.escrowService.accessKey");
        String secretKey = getTestContext().getAttribute("com.tazapay.escrowService.secret");
        Interceptor headersInterceptor = chain -> {
            Request request = chain.request();
            String unSigned =  request.method().toUpperCase() + request.url().encodedPath() + salt + timeStamp +
                    accessKey + secretKey;
            String signature = generateSignature(unSigned, secretKey);
            Request newRequest = request.newBuilder().addHeader("timestamp", timeStamp)
                    .addHeader("salt", salt)
                    .addHeader("accesskey", accessKey)
                    .addHeader("signature", signature)
                    .addHeader("timestamp", timeStamp)
                    .build();
            return chain.proceed(newRequest);
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return List.of(headersInterceptor, loggingInterceptor);
    }

    private static ClientConfig config(){
        return ClientConfig.builder()
                .baseUrl(getTestContext().getAttribute("com.tazapay.escrowService.baseUrl"))
                .build();
    }

    @SneakyThrows
    public static String generateSignature( String payload, String secretKey) {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        sha256HMAC.init(secret_key);
        String hexString = Hex.encodeHexString(sha256HMAC.doFinal(payload.getBytes("UTF-8")));
        return Base64.getEncoder().encodeToString(hexString.getBytes());
    }

}
