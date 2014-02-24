package yotov.c.e.rsaBIG;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Crypto {
	
	// declarations
	//public static int[] encrMsg;
    private static ArrayList<BigInteger> encrMsg = new ArrayList<BigInteger>();
    
    
    
    
	public static BigInteger[] encrypt(String msg, BigInteger e, BigInteger n) {
		BigInteger msgLetter;
		
		// create encrypted msg array 
		BigInteger[] encr = new BigInteger[msg.length()];
		
		//loop through the msg, encrypting every letter and adding it to the encrMsg array
		for(int i=0; i<msg.length(); i++) {	// loop through each letter
				
				msgLetter = BigInteger.valueOf(((int)msg.charAt(i)));	// convert letter's ASCII value to a BigInt
				
				encr[i] = msgLetter.modPow(e, n);	// encrypt and put in array
		}
		
		return encr;
	}
		/* Almost working modPow
		int msgPow = 1, charPos = 0;
		BigInteger msgBig, msgEncr = BigInteger.valueOf(1);
		String bin = e.toString(2);
		
		// message encryption
		while(charPos<msg.length()) {
			// char encryption start
			for(int i=0; i<bin.length(); i++) {
				if(bin.charAt(i)==1) {
					msgBig = BigInteger.valueOf(((int)msg.charAt(charPos)));
					msgEncr = msgEncr.multiply((msgBig.pow(msgPow))).mod(n);
				}
				else {
					msgPow=*2;
				}
			}
			msgEncr =% n;
			// char encryption end
			charPos++;	// move the msg character pointer forward
		}
	}	
	/*
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
	*/
	
	
	public static void decrypt(BigInteger d, BigInteger n) {
		// convert to bigint
		BigInteger exp, result;
		// the decrypted msg
		String decr="The original message was: \n";
		
		for(int i=0; i<encrMsg.size(); i++) {
			exp = pow(encrMsg.get(i), d);
			result = exp.mod(n);

			decr += (char)result.intValue();	// decrypt the message
			System.out.println((char)result.intValue());	// output decryption to console
			
			//TEST VIEW:
			System.out.println("TEST: "+result.toString());
			
		}
		// write the decrypted message to a file
	    //Write.writing(decr, "Decrypted message.txt");
	}
	
	//custom power of, will probably end up changing it out
	private static BigInteger pow(BigInteger base, BigInteger exponent) {
		  BigInteger result = BigInteger.ONE;
		  while (exponent.signum() > 0) {
		    if (exponent.testBit(0)) result = result.multiply(base);
		    base = base.multiply(base);
		    exponent = exponent.shiftRight(1);
		  }
		  return result;
		}
	
	/*
	public static BigInteger pow(int x, BigInteger n) {
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
	*/
}
