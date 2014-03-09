package yotov.c.e.rsaBIG;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Crypto {
	
    // msg encryption
	public static BigInteger[] encrypt(String msg, BigInteger e, BigInteger n) {
		BigInteger msgLetter;
		// create encrypted msg array 
		BigInteger[] encr = new BigInteger[msg.length()];
		//loop through the msg, encrypting every letter and adding it to the encrMsg array
		for(int i=0; i<msg.length(); i++) {	// loop through each letter				
				msgLetter = BigInteger.valueOf(((int)msg.charAt(i)));	// convert letter's ASCII value to a BigInt
				encr[i] = mp(msgLetter,e, n);	// encrypt and put in array
		}
		return encr;
	}
	
	// msg decryption
	public static String decrypt(BigInteger[] encrMsg, BigInteger d, BigInteger n) {
		String msg="";
		BigInteger val;
		for(int i=0; i<encrMsg.length; i++) {
			
			val = mp(encrMsg[i],d, n);	// convert the decrypted letter to an ASCII symbol
			msg += (char)val.intValue();
		}
		return msg;
	}
	
	// modulus power function
	public static BigInteger mp(BigInteger base,BigInteger exponent,BigInteger modulus) {
	    BigInteger result=BigInteger.ONE;	// initialize variable
	    
		// failsafe
		if( base.compareTo(BigInteger.ONE) < 0 || exponent.compareTo(BigInteger.ZERO) < 0 || modulus.compareTo(BigInteger.ONE) < 1) {
	    	return BigInteger.ZERO;
	    }
		
		
		while(exponent.compareTo(BigInteger.ZERO) > 0) {
			// if ((exponent % 2) == 1)
			if(exponent.mod( BigInteger.ONE.add(BigInteger.ONE )).equals(BigInteger.ONE)) {
				// result = (result*base)%modulus;
				result = (result.multiply(base)).mod(modulus);
			}
			// base = (base*base) %mod
			base = (base.multiply(base)).mod(modulus);	
			// exponent = floor(exponent/2)
			exponent = exponent.divide(BigInteger.ONE.add(BigInteger.ONE ));
		}
		
		return result;
	}
	
}
