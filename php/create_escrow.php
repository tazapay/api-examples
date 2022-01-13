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
$APIEndpoint = "/v1/escrow";
$timestamp = time();
$apiKey = <your_api_key>; // string
$apiSecret = <your_api_secret_key> // string
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
$curl = curl_init();
curl_setopt_array(
    $curl,
    [
      CURLOPT_URL => 'https://api-sandbox.tazapay.com/v1/escrow',
      CURLOPT_RETURNTRANSFER => true,
      CURLOPT_ENCODING => '',
      CURLOPT_MAXREDIRS => 10,
      CURLOPT_TIMEOUT => 0,
      CURLOPT_FOLLOWLOCATION => true,
      CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
      CURLINFO_HEADER_OUT => true,
      CURLOPT_SSL_VERIFYPEER => true,
      CURLOPT_CUSTOMREQUEST => 'POST',
      CURLOPT_POSTFIELDS =>'{
        "txn_type": "goods",
        "release_mechanism": "tazapay",
        "reference_id": "",
        "initiated_by": "a42bd4be-811e-4ff7-8e16-1a052734204c",
        "buyer_id": "",
        "buyer": {
          "country": "SG",
          "email": "send@email.com",
          "ind_bus_type" : "Individual",
          "name": "buyer company/contact-person name"
        },
        "seller_id": "",
        "seller": {
          "country": "IN",
          "email": "send@email.com",
          "ind_bus_type" : "Individual",
          "name": "seller company/contact-person name"
        },
        "txn_description": "txn description",
        "txn_docs": [{
            "type": "Proforma Invoice",
            "url": "https://direct.download.link"
          },
          {
            "type": "Others",
            "name": "OTH - File",
            "url": "https://direct.download.link"
          }
        ],
        "is_milestone": false,
        "attributes": {},
        "invoice_currency": "USD",
        "invoice_amount": 100,
        "fee_tier": "standard",
        "fee_paid_by": "",
        "fee_percentage": 100,
        "release_docs": [{
            "type": "Proof of Work",
            "url": "https://direct.download.link"
          },
          {
            "type": "Others",
            "name": "OTH - File",
            "url": "https://direct.download.link"
          }
        ]
      }',
      CURLOPT_HTTPHEADER => [
        'accesskey: '.$apiKey,
        'salt: '.$salt,
        'signature: '.$signature,
        'timestamp: '.$timestamp,
        'Content-Type: application/json'
      ],
    ]
);
$response = curl_exec($curl);
$info = curl_getinfo($curl);
$header_info = curl_getinfo($curl, CURLINFO_HEADER_OUT);
echo "Response:".$response;
curl_close($curl);

