package yotov.c.e.rsaBIG;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Decryption {

	// initialize variables
		private  BigInteger d, n;
	Decryption(String key_file) {
		// load the private keys
		try {
	         BufferedReader in = new BufferedReader(new FileReader(key_file));
	         String line = in.readLine();
	         while (line!=null) {
	            if (line.indexOf("Modulus: ")>=0) {
	               n = new BigInteger(line.substring(9));
	            }
	            if (line.indexOf("Private key: ")>=0) {
	               d = new BigInteger(line.substring(13));
	            }
	            line = in.readLine();
	         }
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
		System.out.println("--- Reading private key ---");
		System.out.println("Modulus: "+n);
		System.out.println("Key size: "+n.bitLength());
		System.out.println("Private key: "+d);
		
	}
	
	// msg decryption
	public void decrypt(String input_name, String output_name) {
		System.out.println("decr's read");

		int keySize = n.bitLength(),
			clearTextSize = Math.min((keySize-1)/8,256),
			cipherTextSize = 1+(keySize-1)/8;
		System.out.println("Cleartext block size: "+clearTextSize);
		System.out.println("Ciphertext block size: "+cipherTextSize);
		
		try {
			FileInputStream fis = new FileInputStream(input_name);
			FileOutputStream fos = new FileOutputStream(output_name);
			byte[] clearTextBlock = new byte[clearTextSize];
			byte[] cipherTextBlock = new byte[cipherTextSize];
			int dataSize = 0;
			
			while(fis.read(cipherTextBlock)>0) {

				BigInteger cipherText = new BigInteger(1, cipherTextBlock);
				BigInteger clearText = mp(cipherText,d,n);
				byte[] clearTextData = clearText.toByteArray();
				putBytesBlock(clearTextBlock, clearTextData);
				
				dataSize = clearTextSize;
				if(fis.available()==0) {
					dataSize = getDataSize(clearTextBlock);
				}
				if(dataSize>0) {
					fos.write(clearTextBlock,  0,  dataSize);
				}
			}
				fis.close();
				fos.close();
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void putBytesBlock(byte[] block, byte[] data) {
		int bSize = block.length,
			dSize = data.length,
			i = 0;
		
		while(i<dSize && i<bSize) {
			block[bSize-i-1] = data [dSize-i-1];
			i++;
		}
		while(i<bSize) {
			block[bSize-i-1] = (byte)0x00;
			i++;
		}
	}
	
	public static int getDataSize(byte[] block) {
		int bSize = block.length,
				padValue = block[bSize-1];
		return (bSize-padValue)%bSize;
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
