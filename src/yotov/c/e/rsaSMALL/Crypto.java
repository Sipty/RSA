package yotov.c.e.rsaSMALL;

import java.io.File;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Crypto {
	
	// declarations
	//public static int[] encrMsg;
    private static ArrayList<Integer> encrMsg = new ArrayList<Integer>();
    
	public static void encrypt(String msg, int e, int n) {
		// encrypted message array
		BigInteger exp, 
			nBig = BigInteger.valueOf(n);
		// the encrypted msg
		String encr = "";
		
		for(int i=0; i<msg.length(); i++) {
			// convert to BigInteger
			int c = (int)msg.charAt(i);
			// encrypting
			exp = pow(c,e);
			encrMsg.add(exp.mod(nBig).intValue());
			
			encr += encrMsg.get(i)+" ";	// encrypt the message
			//System.out.println("From: "+c+" to "+encrMsg.get(i));	// output encryption to console
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
		
		for(int i=0; i<encrMsg.size(); i++) {
			exp = pow(encrMsg.get(i), d);
			result = exp.mod(nBig);

			decr += (char)result.intValue();	// decrypt the message
			System.out.println((char)result.intValue());	// output decryption to console
		}
		// write the decrypted message to a file
	    Write.writing(decr, "Decrypted message.txt");
	}
	
	public static int expand(int message, int exponent, int n) {
		int result=message;
		
		do {
			exponent-=1;
			result*=message;
		}while(result<n);
		
		
		while(exponent>0) {
			exponent-=1;
			result = message*(result%n);
		}
			
		return result;
	}
	private static BigInteger pow(int x, int n) {
		BigInteger result = BigInteger.valueOf(1);
		BigInteger xBig = BigInteger.valueOf(x);
		while(n!=0) {
			if(n%2!=0) {
				result = result.multiply(xBig);
				n -= 1;
			}
			xBig = xBig.multiply(xBig);
			n /= 2;
		}
		return result;
	}
	
	
}
