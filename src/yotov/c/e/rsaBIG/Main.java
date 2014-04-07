package yotov.c.e.rsaBIG;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;


/* 
 * LEGEND:
 * 
 * q, p - primes
 * m = (q-1)(p-1)
 * 
 * 
 * Public Key:	Private Key:
 * n, e			n, d
 * 
 * n = qp
 * e coprime to m
 * d = (1 + nm) / e 
 * 
 */

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		// menu loop
		
		
		
		// write the message
		FileHandler.write("file.txt", "I'm a working RSA algorithm!");
		// read the message
		String msg = FileHandler.read("file.txt");
		System.out.println(msg);
		
		BigInteger p, q;
		BigInteger[] encrMsg = new BigInteger[msg.length()];
		int length = 64;	// prime numbers' bit length
		q = KeyGen.primeGen(length);
		do {
			p = KeyGen.primeGen(length);
		} while(q.equals(p)); 	// make sure that p&q are different
		
		BigInteger m = q.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE)); // apply totient
		BigInteger n = q.multiply(p);	// create n = q*p
		BigInteger e = KeyGen.coprime(m);	// e coprime to m
		BigInteger d = KeyGen.modInverse(e, m);	// d = modular inverse of m
		
		System.out.println(" p = " +p +"\n q = "+q+ "\n m = "+m+ "\n n = "+n +"\n e = "+e+"\n d = "+d);
		
		// encrypt the message
		encrMsg = Crypto.encrypt(msg, e, n); 
		for(int i=0; i<msg.length(); i++) {
			if(encrMsg[i] != BigInteger.ZERO) {	// empty cell filter
				System.out.println(msg.charAt(i) + " = " + encrMsg[i]);	//	original letter = encrypted ASCII value
			}
		}
		
		// decrypt the message
		String originalMsg = Crypto.decrypt(encrMsg, d, n);
		System.out.println("The original message was: "+ originalMsg +"\n");	// output msg to console
	}
}