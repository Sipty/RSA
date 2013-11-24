package yotov.c.e.rsa.big;

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
		
		BigInteger p, q;
		int length = 1024;	// prime bitlength
		q = KeyGen.primeGen(length);
		do {
			p = KeyGen.primeGen(length);
		}while(q.equals(p)); 	// make sure that p&q are different
		
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