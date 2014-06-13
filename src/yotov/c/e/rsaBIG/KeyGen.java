package yotov.c.e.rsaBIG;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class KeyGen {
	public static void main(String[] args) {
		
	}
	public static void keyCreation(int length) {
		// Q & P creation
		BigInteger q = KeyGen.primeGen(length), p;
		do {
			p = KeyGen.primeGen(length);
		} while(q.equals(p)); 	// make sure that p&q are different
		
		// public & private keys
		BigInteger n = q.multiply(p),	// create n = q*p
				m = q.subtract(BigInteger.ONE).multiply(p.subtract(BigInteger.ONE)), // apply totient
				e = KeyGen.coprime(m),	// e coprime to m
				d = KeyGen.modInverse(e, m);	// d = modular inverse of m, n;
		
		// write down the keys
		String public_pair = "Public key: "+e+"\nModulus: "+n;	// e & n the public pair
		String private_pair = "Private key: "+d+"\nModulus: "+n;	// d & n the private pair
		
		FileHandler.write("rsa_public_key.txt", public_pair);
		FileHandler.write("rsa_private_key.txt", private_pair);	
	}
	
    // Prime generation
	public static BigInteger primeGen(int length) {
		BigInteger bi;	// bigint object
		Random rnd = new Random();	// create seed

		bi = BigInteger.probablePrime(length, rnd);	// generate prime
		return bi;
	}
	
	public static BigInteger coprime(BigInteger value) {
		BigInteger e;
		
		do {
			e = primeGen(value.bitLength()-1);
			if(gcd(e, value).equals(BigInteger.ONE))
				return e;
		}while(true);
    }
	
	// Euclidean algorithm
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		while(!(a.equals(BigInteger.ZERO)) && !(b.equals(BigInteger.ZERO)))
		{
			BigInteger c = b;
			b = a.mod(b);
			a = c;
		}
		
		return a.add(b);
	}
	
	// extended Euclidean
	public static BigInteger modInverse(BigInteger e, BigInteger m) {
		BigInteger i = m, v = BigInteger.ZERO, d = BigInteger.ONE;
		
		while (e.compareTo(BigInteger.ZERO) > 0) {
			BigInteger t = i.divide(e), x = e;
			e = i.mod(x);
			i = x;
			x = d;
			d = v.subtract(t.multiply(x));
			v = x;
		}
		v = v.mod(m);
		
		if (e.compareTo(BigInteger.ZERO) < 0) {
			v = v.add(m).mod(m);
		}
		return v;
	}
	
	// random number generator
	public static int rng(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;	// nextInt is exclusive of the top values, so add +1
	    return randomNum;
	}
	
}
