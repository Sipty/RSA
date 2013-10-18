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
		
		for(int i=0; i<msg.length(); i++) {
			// convert to bigint
			c = BigInteger.valueOf((int)msg.charAt(i));	
			// encrypting
			exp = pow(c,e);
			encrMsg[i] = exp.mod(nBig);
			// output
			System.out.println("From: "+c+" to "+encrMsg[i].toString());
		}	
	}
	
	public static void decrypt(int d, int n) {
		// convert to bigint
		BigInteger exp, result, 
			nBig = BigInteger.valueOf(n);
		
		// the decrypted msg
		char[]decr = new char[encrMsg.length];

		for(int i=0; i<encrMsg.length; i++) {
			exp = pow(encrMsg[i], d); 
			result = exp.mod(nBig);
			//result.intValue();
			System.out.println((char)result.intValue());
			decr[i] = (char)result.intValue();	// decrypt the message
		}
		// write the decrypted message
	    Write.writing(decr);	// move this to a for loop
		
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
