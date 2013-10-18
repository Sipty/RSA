package yotov.c.e.rsa;


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
	
	// Declarations
	private static int p,q,n,m,e,d;
	private String msg;
	
	public static void main(String[] args) {
		
		// generate & assign primes
		KeyGen.primeGen();
		q = KeyGen.getQ();
		p = KeyGen.getP();
		
		// apply totient
		m = (q-1)*(p-1);
		// prepare keys
		n = q*p;
		e = KeyGen.coprimeTo(m);
		d = KeyGen.getD(n, m, e);
		
		// output
		print("q="+q + " p="+p +" n="+n +" m="+m +" e="+e +" d="+d);
		
		Crypto.encrypt("Hello", e, n);
		Crypto.decrypt(d, n);
		
	}
	
	// quality of life printer
	public static void print(String line) {
		System.out.println(line);
	}
}