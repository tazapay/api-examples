{
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
            }
    
    public static String generateSignature( String payload, String secretKey) {
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        sha256HMAC.init(secret_key);
        String hexString = Hex.encodeHexString(sha256HMAC.doFinal(payload.getBytes("UTF-8")));
        return Base64.getEncoder().encodeToString(hexString.getBytes());
    }
}
