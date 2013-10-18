package yotov.c.e.rsa;

import java.math.BigInteger;

public class Crypto {
	
	// declarations
	public static BigInteger[] encrMsg;
    
	public static void encrypt(String msg, int e, int n) {
		// encrypted message array
		encrMsg = new BigInteger[msg.length()];
		BigInteger c, exp, 
			nBig = BigInteger.valueOf(n);
		// the encrypted msg
		String encr = "";
		
		for(int i=0; i<msg.length(); i++) {
			// convert to BigInteger
			c = BigInteger.valueOf((int)msg.charAt(i));
			// encrypting
			exp = pow(c,e);
			encrMsg[i] = exp.mod(nBig);
			
			encr += encrMsg[i].toString()+" ";	// encrypt the message
			System.out.println("From: "+c+" to "+encrMsg[i].toString());	// output encryption to console
		}	
		// write the encrypted message to a file
		Write.writing(encr, "Encrypted message.txt");
	}
	
	public static void decrypt(int d, int n) {
		// convert to bigint
		BigInteger exp, result, 
			nBig = BigInteger.valueOf(n);
		// the decrypted msg
		String decr="The original message was: \n";
		
		for(int i=0; i<encrMsg.length; i++) {
			exp = pow(encrMsg[i], d); 
			result = exp.mod(nBig);

			decr += (char)result.intValue();	// decrypt the message
			System.out.println((char)result.intValue());	// output decryption to console
		}
		// write the decrypted message to a file
	    Write.writing(decr, "Decrypted message.txt");
		
	}
	
	// change to private
	private static BigInteger pow(BigInteger x, int n) {
		BigInteger result = BigInteger.valueOf(1);
		while(n!=0) {
			if(n%2!=0) {
				result = result.multiply(x);
				n -= 1;
			}
			x = x.multiply(x);
			n /= 2;
		}
		return result;
	}
	
}
