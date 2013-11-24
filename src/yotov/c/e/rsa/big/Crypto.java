package yotov.c.e.rsa.big;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Crypto {
	
	// declarations
	//public static int[] encrMsg;
    private static ArrayList<BigInteger> encrMsg = new ArrayList<BigInteger>();
    
	public static void encrypt(String msg, BigInteger e, BigInteger n) {
		// encrypted message array
		BigInteger exp;
		// the encrypted msg
		String encr = "";
		
		for(int i=0; i<msg.length(); i++) {
			// convert to BigInteger
			int c = (int)msg.charAt(i);
			// encrypting
			exp = e.pow(c);
			encrMsg.add(exp.mod(n));
			
			encr += encrMsg.get(i)+" ";	// encrypt the message
			System.out.println("From: "+c+" to "+encrMsg.get(i));	// output encryption to console
		}	
		// write the encrypted message to a file
		//Write.writing(encr, "Encrypted message.txt");
	}
	
	public static void decrypt(BigInteger d, BigInteger n) {
		// convert to bigint
		BigInteger exp, result;
		// the decrypted msg
		String decr="The original message was: \n";
		
		for(int i=0; i<encrMsg.size(); i++) {
			exp = d.pow(encrMsg.get(i));
			result = exp.mod(n);

			decr += (char)result.intValue();	// decrypt the message
			System.out.println((char)result.intValue());	// output decryption to console
		}
		// write the decrypted message to a file
	    //Write.writing(decr, "Decrypted message.txt");
	}
	
	private static BigInteger pow(int x, BigInteger n) {
		BigInteger result = BigInteger.valueOf(1);
		BigInteger xBig = BigInteger.valueOf(x);
		while(!n.equals(BigInteger.ZERO)) {
			if(!(n.mod(BigInteger.valueOf(2))).equals(BigInteger.ZERO)) {
				result = result.multiply(xBig);
				n.subtract(BigInteger.ONE);
			}
			xBig = xBig.multiply(xBig);
			n.divide(BigInteger.valueOf(2));
		}
		return result;
	}
	
}
