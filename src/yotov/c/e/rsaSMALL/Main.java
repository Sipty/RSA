package yotov.c.e.rsaSMALL;

import java.math.BigInteger;
import java.util.Random;

public class Main {

	
	public static void main(String[] args) {

		// Declarations
		int p,q,n,m,e,d;
		
		// generate & assign primes
		KeyGen.primeGen();
		q = KeyGen.getPrime();
		do {
			p = KeyGen.getPrime();
		}while(p==q);	// make sure that p & q are different
		
		// apply totient
		m = (q-1)*(p-1);
		
		// prepare keys
		n = q*p;
		e = KeyGen.coprimeTo(m);
		d = KeyGen.getD(m, e);
				
		System.out.println(" p = " +p +"\n q = "+q+ "\n m = "+m+ "\n n = "+n +"\n e = "+e+"\n d = "+d);
	
		System.out.println(Crypto.expand(6, 5, 133));
		
		final long startTimeEncr = System.currentTimeMillis();
		Crypto.encrypt("Hello!",
				e, n);
		final long endTimeEncr = System.currentTimeMillis();

		final long startTimeDecr = System.currentTimeMillis();
		Crypto.decrypt(d, n);
		final long endTimeDecr = System.currentTimeMillis();
		
		System.out.println("Total encryption time: " + (endTimeEncr - startTimeEncr) );
		System.out.println("Total decryption time: " + (endTimeDecr - startTimeDecr) + "\n Bit length of n: "+BigInteger.valueOf(n).bitLength());
		

	}
	
	// quality of life printer
	public static void print(String line) {
		System.out.println(line);
	}
}