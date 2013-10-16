

package yotov.c.e.rsa;

//created myself a power of function that takes integers, for the decryption
long long int power_of(long long int a, int b) {
 long long int c=a;
 
 for(int i=1; i<b; i++) {
     c = c*a;
 }
 return c;
}
public class KeyGeneration {
	public static void main(String[] args) {
	// KEY GENERATION
	    // STEP 1
	    srand(time(NULL));  // seed the rng, using the time
	    
	    // find all primals up to max
	    int max = 20;  // max=100 sometimes gives Encryped: -int values
	    int min = 1;
	    int p=0, q=0, n=0, m=0, d=-1, e=-1;   // p*q=n   -- n is part of the public and private keys
	                                        // m is the totient of n
	                                    // e & m are coprime
	                                    // d*e = 1+i*m, where i is any integer
	                                // n is a shared key,
	                                // e is the public key
	                                // d is the private key
	    // declare array
	    bool[] numberPool = numberPool[max];
	    for(int i=0; i<max; i++) number_pool[i]=false;
	    number_pool[0]=true;
	    number_pool[1]=true;
	    
	    // NB: in number_pool the false numbers end up being prime
	    for(int i=0; i<max; ++i) {
	    	p=0;
	        q=0;    // q will be incremented by p
	        
	        // if we find a prime, start marking the non-primes
	        if(!number_pool[i]) {   // if number is a prime
	            p=i;            // use it as our base
	            for(int j=0; j<max; ++j) {
	                
	                if(q==0) {  // add p just in the beginning
	                    q+=p;   // otherwise prime would be overwritten
	                }
	                
	                q+=p;
	                if(q>max)break;
	                else numberPool[q] = true;
	            }
	        }
	    }
	
	    
	    // fill prime array
	    ArrayList<Integer> primePool = new ArrayList<Integer>();
	    
	    for(int i=0; i<max; i++) {
	    	if(!numberPool[i]) {	
	    		primePool.add(i);
	        }
	    }
	    
	    // pick random primes, such that p,q>min
	    do {
	        p =primePool[rand() % primePool.size() + 1];
	    }while(p<min);
	    
	    do {
	        q =primePool[rand() % primePool.size() + 1];
	    }while(q<min);
	    
	    
	    
	    // STEP 2
	    n = p*q;
	    m = (p-1)*(q-1);    // apply totient function
	    
	    // generating e (part of the public key)
	    for(int i=2; true; i++) {
	        if(euclids(i, m)==1) {
	            e=i;
	            break;
	        }
	    }
	    // generating d (part of the private key)
	    for(int i=0; true; i++) {
	        if((1+i*m)%e==0) {
	            d=(1+i*m)/e;
	            break;
	        }
	    }
	    
	    System.out.println("p= "+p +" q="+q);
	    System.out.println("m= "+m +" e="+e +" d="+d);
	    System.out.println("Public key: n="+n +" e="+e);
	    System.out.println("Private key: n="+n +" d="+d);    
	    
	    // ENCRYPTION
	    String msg = "Hello";
	    bool[] numberPool = numberPool[max];
	    int[] encrMsg = encrMsg[msg.size()];   // TODO: make the array size flixible
	    int[] decrMsg = decrMsg[msg.size()];
	    // encrypt message
	    for(int i=0; i<msg.size(); i++) {
	        a
	        encr_msg[i] = pow((long double)msg[i], (long double)e) % n;
	        decr_msg[i] = power_of(encr_msg[i], d) % n;
	        
	        
	        std::cout<<"Original: "<< static_cast<int>(msg[i])<<std::endl;
	        std::cout<<"Encrypted: "<< static_cast<long long int>(encr_msg[i])<<std::endl;
	        std::cout<<"Decrypted: "<< static_cast<long int>(decr_msg[i])<<std::endl<<std::endl;
	    }
	}
}