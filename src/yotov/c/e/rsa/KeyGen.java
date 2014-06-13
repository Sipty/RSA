package yotov.c.e.rsa;

import java.util.ArrayList;
import java.util.Random;

class KeyGen {
	
	// Declarations
	// find all primes up to max
    private static int max = 100, min = 10,	// primes' range
    				p=0, q=0; 	// p, q - the primes

    // declare arrays
    private static Boolean[] numberPool = new Boolean[max];
    private static ArrayList<Integer> primePool = new ArrayList<Integer>();
	
    
    // Eratosthene's sieve is used to generate the primes
	public static void primeGen() {
		// prepare array
	    for(int i=0; i<max; i++) numberPool[i]=false;
	    // tick off 0 and 1, so that following loops are simpler
	    numberPool[0]=true;
	    numberPool[1]=true;
	    
	    // NB: in numberPool the false numbers end up being prime
	    for(int i=0; i<max; ++i) {
	    	int num=0, incr=0;	// num will be incremented by incr
	        
	        // if we find a prime, start marking the non-primes
	        if(!numberPool[i]) {   // if number is a prime
	            num=i;            // use it as our base
	            for(int j=0; j<max; ++j) {
	                
	                if(incr==0) {  // add p just in the beginning
	                    incr+=num;   // otherwise prime would be overwritten
	                }
	                
	                incr+=num;
	                if(incr<max) {
	                	numberPool[incr] = true;
	                }
	            }
	        }
	    }
	    
	    // fill prime array
	    for(int i=0; i<max; i++) {
	    	if(!numberPool[i]) {	
	    		primePool.add(i);
	        }
	    }
	}

	// generate the GCD, using Euclidean algorithm
	public static int euclids(int a, int b) {
		int temp;
		// make sure a>b
		if(a<b) {
			temp = a;
			a = b;
			b = temp;
		}
		
		// loop Euclid's until ready
		while(true) {
			temp = a % b;
			a = b;
			b = temp;
			
			if(b==0) 
				return a;
		}
	}
	
	// return e coprime to m
	public static int coprimeTo(int m) {
		int e;
	    for(int i=rng(0,max); true; i++) {
	        if(euclids(i, m)==1) {
	            e=i;
	            return e;
	        }
	    }
	}
	
	// generate d, such that d = (1 + i*m) / e
	public static int getD(int m, int e) {
		int d;
		for(int i=0; true; i++) {
	        if((1+i*m)%e==0) {
	            d=(1+i*m)/e;
	            break;
	        }
	    }
		return d;
	}
	
	// random number generator
	public static int rng(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;	// nextInt is exclusive of the top values, so add +1
	    return randomNum;
	}

	// getters
	public static int getPrime() {
	    do {
	        p = primePool.get(rng(0, (primePool.size()-1)));
	    }while(p<min);
	    
		return p;
	}
	
}
