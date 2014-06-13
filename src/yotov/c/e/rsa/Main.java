package yotov.c.e.rsa;

import java.math.BigInteger;

import yotov.c.e.rsaSMALL.Crypto;

class Main {
	
	// Declarations
	private static int p,q,n,m,e,d;
	
	public static void main(String[] args) {
		
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
		
		// output
		print("q="+q + " p="+p +" n="+n +" m="+m +" e="+e +" d="+d);
		
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