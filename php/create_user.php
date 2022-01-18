<?php
/*
 * generate salt value
 */
$chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789`~!@#$%^&*()-=_+';
$l = strlen($chars) - 1;
$salt = '';
for ($i = 0; $i < 8; ++$i) {
    $salt .= $chars[rand(0, $l)];
}
$method= "POST";
$APIEndpoint = "/v1/user";
$timestamp = time();
$apiKey = "<your_api_key>";
$apiSecret = "<your_api_secret_key>";
/*
 * generate to_sign
 * to_sign = toUpperCase(Method) + Api-Endpoint + Salt + Timestamp + API-Key + API-Secret
 */
$to_sign = $method.$APIEndpoint.$salt.$timestamp.$apiKey.$apiSecret;

/*
 * generate signature
 * $hmacSHA256 is generate hmacSHA256
 * $signature is convert hmacSHA256 into base64 encode
 * in document: signature = Base64(hmacSHA256(to_sign, API-Secret))
 */
$hmacSHA256 = hash_hmac('sha256', $to_sign, $apiSecret);
$signature = base64_encode($hmacSHA256);
echo "<h2>" . $signature . "</h2>";
$curl = curl_init();

curl_setopt_array($curl, array(
  CURLOPT_URL => 'https://api-sandbox.tazapay.com/v1/user',
  CURLOPT_RETURNTRANSFER => true,
  CURLOPT_ENCODING => '',
  CURLOPT_MAXREDIRS => 10,
  CURLOPT_TIMEOUT => 0,
  CURLOPT_FOLLOWLOCATION => true,
  CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
  CURLOPT_CUSTOMREQUEST => 'POST',
  CURLOPT_POSTFIELDS =>'{
    "email": "ankit+phpscript12jan01_02@tazapay.com",
    "country": "SG",
    "contact_code": "+65",
    "contact_number": "",
    "partners_customer_id": "12345",
    "ind_bus_type": "Individual",
    "first_name": "Ankit",
    "last_name": "K"
}',
  CURLOPT_HTTPHEADER => array(
        'accesskey: '.$apiKey,
        'salt: '.$salt,
        'signature: '.$signature,
        'timestamp: '.$timestamp,
        'Content-Type: application/json'
  ),
)
);


$response = curl_exec($curl);
$info = curl_getinfo($curl);
$header_info = curl_getinfo($curl, CURLINFO_HEADER_OUT);
echo "Response:".$response;
curl_close($curl);