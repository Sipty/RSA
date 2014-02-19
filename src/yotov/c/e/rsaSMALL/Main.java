package yotov.c.e.rsaSMALL;

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
		
		/*
		Crypto.encrypt("Hello", e, n);
		Crypto.decrypt(d, n);
		
		
		/*
		BigInteger m = q.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE)); // apply totient
		BigInteger n = q.multiply(p);	// create n = q*p
		BigInteger e = KeyGen.coprime(m);	// e coprime to m
		BigInteger d = e.modInverse(m);	// d = modular inverse of m
		
		System.out.println(" p = " +p +"\n q = "+q+ "\n m = "+m+ "\n n = "+n +"\n e = "+e+"\n d = "+d);
		

		Crypto.encrypt("Hello!", e, n);
		//Crypto.decrypt(d, n);
		
		/*
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
		
		Crypto.encrypt("Hello!", e, n);
		Crypto.decrypt(d, n);
		*/
	}
	
	// quality of life printer
	public static void print(String line) {
		System.out.println(line);
	}
}