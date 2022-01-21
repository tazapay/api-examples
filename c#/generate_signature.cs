using System;
using System.Security.Cryptography;
using System.Text;

namespace TazapayApiRequestSample
{
	public class Program
	{
		public static void Main(string[] args)
		{
			string salt = Utilities.GenerateRandomString(8);
			string httpMethod = "POST";
			string httpURLPath = "";
			long timestamp = DateTimeOffset.Now.ToUnixTimeSeconds();
			string result = Utilities.Sign(httpMethod, httpURLPath, salt, timestamp);
		}
	}
    static class Utilities
    {
        private const string accessKey = "TAZAPAY_ACCESS_KEY";
        private const string secretKey = "TAZAPAY_SECRET_KEY";
        private const bool log = true;

        public static string Sign(string method, string urlPath, string salt, long timestamp)
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
        public static string GenerateRandomString(int size)
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
    }
}