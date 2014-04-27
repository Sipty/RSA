package yotov.c.e.rsaBIG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Crypto {
	
	// initialize variables
	private BigInteger e, n;
	
	Crypto (String key_file) {
		// start timing
		
		 try {
	         BufferedReader in = new BufferedReader(new FileReader(key_file));
	         String line = in.readLine();
	         while (line!=null) {
	            if (line.indexOf("Modulus: ")>=0) {
	               n = new BigInteger(line.substring(9));
	            }
	            if (line.indexOf("Public key: ")>=0) {
	               e = new BigInteger(line.substring(12));
	            }
	            line = in.readLine();
	         }
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
		 System.out.println("--- Reading public key ---");
	     System.out.println("Modulus: "+n);
	     System.out.println("Key size: "+n.bitLength());
	     System.out.println("Public key: "+e);
		 
	}
	
    // msg encryption
	public void encrypt(String input_name, String output_name) {

		// get the bit size of n 
		// then calculate the byte size of the cipher and clear texts
		int keySize = n.bitLength(),
			clearTextSize = Math.min((keySize-1)/8, 256),
			cipherTextSize = 1+(keySize-1)/8;
		System.out.println("Cleartext block size: "+clearTextSize);
	    System.out.println("Ciphertext block size: "+cipherTextSize);
	      
		try {
			FileInputStream fis = new FileInputStream(input_name);
			FileOutputStream fos = new FileOutputStream(output_name);
			byte[] clearTextBlock = new byte[clearTextSize];
			byte[] cipherTextBlock = new byte[cipherTextSize];
			int dataSize = fis.read(clearTextBlock);
			boolean isPadded = false;
			
		// Read input message
			while(dataSize>0) {

				// check if the block needs padding
				if(dataSize<clearTextSize) {
					padBytesBlock(clearTextBlock, dataSize);
					isPadded = true;
				}
				
					// convert byte array to positive big int
				BigInteger clearText = new BigInteger(1, clearTextBlock);
					// and then encrypt it
				BigInteger cipherText = mp(clearText,e,n);
					// and finally convert back to bytes
				byte[] cipherTextData = cipherText.toByteArray();

				putBytesBlock(cipherTextBlock, cipherTextData);

				fos.write(cipherTextBlock);
				
				dataSize = fis.read(clearTextBlock);
			}
			
		// Add a padding block, if necessary
			if(!isPadded) {

				padBytesBlock(clearTextBlock, 0);
					// convert byte array to positive big int
				BigInteger clearText = new BigInteger(1, clearTextBlock);
					//	and then encrypt it
				BigInteger cipherText = mp(clearText,e,n);
					// and finally convert back to bytes
				byte[] cipherTextData = cipherText.toByteArray();
				putBytesBlock(cipherTextBlock, cipherTextData);
				fos.write(cipherTextBlock);
			}
			
			fis.close();
			fos.close();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// Put bytes data into a block
	public static void putBytesBlock(byte[] block, byte[] data) {

		int bSize = block.length,
			dSize = data.length,
			i = 0;
		
		while(i<dSize && i<bSize) {
			block[bSize-i-1] = data[dSize-i-1];
			i++;
		}
		while(i<bSize) {
			block[bSize-i-1] = (byte)0x00;
			i++;
		}
	}
	
	// Add padding to block
	public static void padBytesBlock(byte[] block, int dataSize) {
		int bSize = block.length,
			padSize = bSize - dataSize,
			padValue = padSize%bSize;
		
		for(int i=0; i<padSize; i++) {
			block[bSize-i-1] = (byte) padValue;
		}
	}
	
	// modulus power function
	private static BigInteger mp(BigInteger base,BigInteger exponent,BigInteger modulus) {
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
