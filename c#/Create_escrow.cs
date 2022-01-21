using System;
using System.IO;
using System.Net;
using System.Security.Cryptography;
using System.Text;
using Newtonsoft.Json;

namespace TazapayApiRequestSample
{
    public class Program
    {
        public static void Main(string[] args)
        {
            try
            {
                var body = new 
                {
					initiated_by = "<valid uuid of escrow initiator>",
					buyer_id = "valid uuid of buyer",
					seller_id = "valid uuid of seller",
					txn_description = "Description",
					is_milstone = true,
					invoice_amount = 1000,
					invoice_currency = "USD"
                };
                string request = JsonConvert.SerializeObject(body);

                string result = Utilities.MakeRequest("POST", "/v1/escrow", request);

                Console.WriteLine(result);
            }
            catch (Exception e)
            {
                Console.WriteLine("Error completing request: " + e.Message);
            }
        }
    }

    static class Utilities
    {
        private const string accessKey = "TAZAPAY_ACCESS_KEY";
        private const string secretKey = "TAZAPAY_SECRET_KEY";
        private const bool log = true;

        public static string MakeRequest(string method, string urlPath, string body = null)
        {
            try
            {
                string httpMethod = method;
                Uri httpBaseURL = new Uri("https://api-sandbox.tazapay.com");
                string httpURLPath = urlPath;
                string httpBody = body;
                string salt = GenerateRandomString(8);
                long timestamp = DateTimeOffset.Now.ToUnixTimeSeconds();
                string signature = Sign(httpMethod, httpURLPath, salt, timestamp);

                Uri httpRequestURL = new Uri(httpBaseURL, urlPath);
                WebRequest request = HttpWebRequest.Create(httpRequestURL);
                request.Method = httpMethod;
                request.ContentType = "application/json";
                request.Headers.Add("salt", salt);
                request.Headers.Add("timestamp", timestamp.ToString());
                request.Headers.Add("signature", signature);
                request.Headers.Add("accessKey", accessKey);

                if (log)
                {
                    Console.WriteLine("web request method: " + httpMethod);
                    Console.WriteLine("web request url: " + httpRequestURL);
                    Console.WriteLine("web request body: " + httpBody);
                    Console.WriteLine("web request contentType: " + request.ContentType);
                    Console.WriteLine("web request salt: " + salt);
                    Console.WriteLine("web request timestamp: " + timestamp);
                    Console.WriteLine("web request signature: " + signature);
                    Console.WriteLine("web request access_key: " + accessKey);
                }

                return HttpRequest(request, body);
            }
            catch (Exception ex)
            {
                Console.WriteLine("Error generating request options: ", ex);
                throw;
            }
        }


        private static string Sign(string method, string urlPath, string salt, long timestamp)
        {

            try
            {
                string toSign = method.ToUpper() + urlPath + salt + timestamp + accessKey + secretKey;
                if (log)
                {
                    Console.WriteLine("\ntoSign: " + toSign);
                }

                UTF8Encoding encoding = new UTF8Encoding();
                byte[] secretKeyBytes = encoding.GetBytes(secretKey);
                byte[] signatureBytes = encoding.GetBytes(toSign);
                string signature = String.Empty;
                using (HMACSHA256 hmac = new HMACSHA256(secretKeyBytes))
                {
                    byte[] signatureHash = hmac.ComputeHash(signatureBytes);
                    string signatureHex = String.Concat(Array.ConvertAll(signatureHash, x => x.ToString("x2")));
                    signature = Convert.ToBase64String(encoding.GetBytes(signatureHex));
                }

                if (log)
                {
                    Console.WriteLine("signature: " + signature);
                }

                return signature;
            }
            catch (Exception)
            {
                Console.WriteLine("Error generating signature");
                throw;
            }

        }
        private static string GenerateRandomString(int size)
        {
            try
            {
                using (RandomNumberGenerator rng = new RNGCryptoServiceProvider())
                {
                    byte[] randomBytes = new byte[size];
                    rng.GetBytes(randomBytes);
                    return String.Concat(Array.ConvertAll(randomBytes, x => x.ToString("x2")));
                }
            }
            catch (Exception)
            {
                Console.WriteLine("Error generating salt");
                throw;
            }
        }

        private static string HttpRequest(WebRequest request, string body)
        {
            string response = String.Empty;

            try
            {
                if (!String.IsNullOrWhiteSpace(body))
                {
                    using (Stream requestStream = request.GetRequestStream())
                    {
                        UTF8Encoding encoding = new UTF8Encoding();
                        byte[] bodyBytes = encoding.GetBytes(body);
                        requestStream.Write(bodyBytes, 0, bodyBytes.Length);
                        requestStream.Close();
                    }
                }

                using (WebResponse webResponse = request.GetResponse())
                {
                    using (Stream responseStream = webResponse.GetResponseStream())
                    {
                        using (StreamReader streamReader = new StreamReader(responseStream))
                        {
                            response = streamReader.ReadToEnd();
                            if (log)
                            {
                                Console.Write("web response:" + response);
                            }
                        }
                    }
                }
            }
            catch (WebException e)
            {
                using (StreamReader streamReader = new StreamReader(e.Response.GetResponseStream()))
                {
                    response = streamReader.ReadToEnd();
                    if (log)
                    {
                        Console.Write("web response:" + response);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error occurred: " + e.Message);
            }

            return response;
        }
    }
}