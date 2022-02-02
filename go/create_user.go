package main

import (
	"crypto/hmac"
	"crypto/rand"
	"crypto/sha256"
	"encoding/base64"
	"encoding/hex"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"time"
)

//genrateRandomString
func genrateRandomString(len int) (string, error) {
	b := make([]byte, len)
	if _, err := rand.Read(b); err != nil {
		return "", err
	}
	s := fmt.Sprintf("%X", b)
	return s, nil
}

// generateTimeStamp
func generateTimeStamp() int64 {
	return time.Now().Unix()
}

func generateSignatureString(path, method, signtureSalt, accessKey, secretKey, timestamp string) string {
	return fmt.Sprintf("%s%s%s%s%s%s", strings.ToUpper(method), path, signtureSalt, timestamp, accessKey, secretKey)
}

func generateSHA56(data string, secret string) string {
	h := hmac.New(sha256.New, []byte(secret))
	h.Write([]byte(data))
	sha := hex.EncodeToString(h.Sum(nil))
	return sha
}

func GenerateSignature(path, method, salt, accessKey, secret, timestamp string) string {
	signatureString := generateSignatureString(path, method, salt, accessKey, secret, timestamp)
	signature := generateSHA56(signatureString, secret)
	signature = base64.StdEncoding.EncodeToString([]byte(signature))
	return signature
}

func main() {
	salt, err := genrateRandomString(12)
	if err != nil {
		fmt.Println("err")
		return
	}
	timeStamp := generateTimeStamp()
	accessKey := "your access key"
	secretKey := "your secret key"
	singnature := GenerateSignature("/v1/user", "POST", salt, accessKey, secretKey, fmt.Sprintf("%d", timeStamp))
	url := "https://api-sandbox.tazapay.com"
	method := "POST"

	// country input format is ISO 3166 alpha-2
	// optional fields: contact_code, contact_number, partners_customer_id
	payload := strings.NewReader(`{
		"email": "abc@gmail.com",
		"country": "IN",
		"ind_bus_type": "Business",
		"business_name": "Waynes industry",
		"contact_code": "",
		"contact_number": "",
		"partners_customer_id": ""
	}
`)

	client := &http.Client{}
	req, err := http.NewRequest(method, url, payload)

	if err != nil {
		fmt.Println(err)
		return
	}
	req.Header.Add("accesskey", accessKey)
	req.Header.Add("signature", singnature)
	req.Header.Add("salt", salt)
	req.Header.Add("timestamp", fmt.Sprintf("%d", timeStamp))
	req.Header.Add("Content-Type", "application/json")

	res, err := client.Do(req)
	if err != nil {
		fmt.Println(err)
		return
	}
	defer res.Body.Close()

	body, err := ioutil.ReadAll(res.Body)
	if err != nil {
		fmt.Println(err)
		return
	}
	fmt.Println(string(body))
}
