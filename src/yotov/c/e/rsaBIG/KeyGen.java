package yotov.c.e.rsaBIG;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class KeyGen {
	
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
	
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		while(!(a.equals(BigInteger.ZERO)) && !(b.equals(BigInteger.ZERO)))
		{
			BigInteger c = b;
			b = a.mod(b);
			a = c;
		}
		
		return a.add(b);
	}
	
	// random number generator
	public static int rng(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;	// nextInt is exclusive of the top values, so add +1
	    return randomNum;
	}
	
}
