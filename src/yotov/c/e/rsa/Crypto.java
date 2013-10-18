package yotov.c.e.rsa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Crypto {
	
	// declarations
	public static BigInteger[] encrMsg;
    
	public static void encrypt(String msg, int e, int n) {
		// encrypted message array
		encrMsg = new BigInteger[msg.length()];
		int c;
		BigInteger  exp, 
			nBig = BigInteger.valueOf(n);
		// the encrypted msg
		String encr = "";
		
		for(int i=0; i<msg.length(); i++) {
			// convert to BigInteger
			c = (int)msg.charAt(i);
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
		// prepare scanner
		try {
			Scanner sc = new Scanner(new File("Encrypted message.txt"));
	
			// convert to bigint
			BigInteger exp, result, 
				nBig = BigInteger.valueOf(n);
			
			// the decrypted msg
			String decr="The original message was: \n";
			
			for(int i=0; sc.hasNextInt(); i++) {
		    	int bi = sc.nextInt();
			    System.out.println(bi);
		    	
				exp = pow(bi, d);
				result = exp.mod(nBig);
	
				decr += (char)result.intValue();	// decrypt the message
				System.out.println((char)result.intValue());	// output decryption to console
			}
			// write the decrypted message to a file
		    Write.writing(decr, "Decrypted message.txt");

		} catch (IOException e) {
	        System.err.println("Problem writing to file.");
	    }
		
	}
	
	// change to private
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
