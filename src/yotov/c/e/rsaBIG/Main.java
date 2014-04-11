package yotov.c.e.rsaBIG;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;


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
		
		boolean quit = false;
		Scanner user_input = new Scanner(System.in);
		String command_1, command_2, 
				file_name="default.txt";


		int length = 64;	// prime numbers' default bit length
		
		// initializing the keys and key components
		
		// menu loop
		System.out.println("Welcome to C. Yotov's RSA implementation!");
		do {
			// Main Menu (MM)
			System.out.println("\n\n\n+==+ MAIN MENU +==+\n\n"+
								"-- 1 - Key Creation -- \n"+
								"-- 2 - Encryption -- \n"+
								"-- 3 - Decryption -- \n"+
								"-- 4 - End -- \n\n");
			command_1 = user_input.next();
			
			// MM switch
			switch(command_1) {
			
			// Key Creation(KC) menu
				case "1":

					boolean kc_loop = true;
					do {	// an infinite do while loop is used, so that the user doesn't go back unwillingly
						System.out.println("\n\n\n+==+ KEY CREATION +==+ \n\n"+
										
										"-- 1 - Create public & private keys -- \n"+
										"-- 2 - Change keys' bit length (current: "+length+" bits) -- \n"+
										"-- 3 - Go back --");
						
						// take input
						command_2 = user_input.next();
						switch(command_2) {		
						
							// KC
							case "1":
								
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
								String public_pair = e+" "+n;	// e & n the public pair
								String private_pair = d+" "+n;	// d & n the private pair
								
								FileHandler.write("public.txt", public_pair);
								FileHandler.write("private.txt", private_pair);
								
								System.out.println("\nPublic & Private keys have been created and saved.");
								break;
									
							// Key bit length
							case "2":
								boolean kbl_loop;	// makes sure valid input is given
								System.out.println("Enter required bit length: ");
								
								// take user's length input, if an int
								do{
									try {
										length = Integer.parseInt(user_input.next());
										kbl_loop = false;
									}
									catch(Exception excptn) {
										System.out.println("Error: not an integer!\n"+
															"Try again: ");
										kbl_loop = true;
									}
								}while(kbl_loop);
								
								System.out.println("Primes' bit length changed to "+length+".");
								break;
							
								// quit
							case "3":
								kc_loop = false;
								break;
							default:
								System.out.println("Invalid input.");
								break;
						}
					}while(kc_loop);
					break;
					
			// Encryption (E)
				case "2":
					boolean e_loop = true;
					// create a default text file
					FileHandler.write("default.txt", "anito e pi4");
					
					do {
						System.out.println("\n\n\n+==+ ENCRYPTION +==+ \n\n"+
											"Current file: "+file_name+" 2\n"+
											"-- 1 - Begin encryption -- \n"+
											"-- 2 - Go back -- \n");
						
						// take input
						command_2 = user_input.next();
						switch(command_2) {
							
							// Begin encryption
							case "1":

								String msg = FileHandler.read(file_name);
								System.out.println(msg);
								BigInteger[] encrMsg = new BigInteger[msg.length()];
								
								// load the public keys
								String public_key = FileHandler.read("public.txt");
								String[] keys = public_key.split(" ");	
								BigInteger e = new BigInteger(keys[0].trim());
								BigInteger n = new BigInteger(keys[1].trim());
								
								// encrypt the message
								encrMsg = Crypto.encrypt(msg, e, n); 
								
								// output encrypted values & writes them to a file
								String encrMsg_String="";
								for(int i=0; i<msg.length(); i++) {
									if(encrMsg[i] != BigInteger.ZERO) {	// empty cell filter
										System.out.println(msg.charAt(i) + " = " + encrMsg[i]);	//	original letter = encrypted ASCII value
										//add values to a string for writing
										encrMsg_String+=encrMsg[i]+" ";
										
									}
								}
								FileHandler.write("encrypted_msg.txt", encrMsg_String);
								break;
								
							// Go back
							case "2":
								e_loop = false;
								break;
						}
						
					} while(e_loop);
					break;
				
			// Decryption (D)
				case "3":
					boolean d_loop = true;
					
					do {
						System.out.println("\n\n\n+==+ DECRYPTION +==+ \n\n"+
											"Current file: "+file_name+" 2\n"+
											"-- 1 - Begin decryption -- \n"+
											"-- 2 - Go back -- \n");
						
						// take input
						command_2 = user_input.next();
						switch(command_2) {
							
							// Begin encryption
							case "1":
								
								// decrypt the message

								// load the text file
								String msg = FileHandler.read("encrypted_msg.txt");
								// create the array holding each encrypted letter
								// and one that will ceep the decrypted message
								String[] values = msg.split(" ");	
								BigInteger[] decrMsg = new BigInteger[values.length];
								
								for(int i=0; i<values.length; i++) {
									System.out.println(values[i]);
									if(values[i] != null)
										decrMsg[i] = new BigInteger(values[i].trim());
								}
								

								// load the private keys
								String public_key = FileHandler.read("private.txt");
								String[] keys = public_key.split(" ");	
								System.out.println(keys[0]);
								BigInteger d = new BigInteger(keys[0].trim());
								BigInteger n = new BigInteger(keys[1].trim());
								String originalMsg = Crypto.decrypt(decrMsg, d, n);
								System.out.println("The original message was: "+ originalMsg +"\n");	// output msg to console
								break;
								
							// Go back
							case "2":
								d_loop = false;
								break;
						}
						
					} while(d_loop);
					break;
					
				default: 
					System.out.println("Please choose a valid menu.");
					
			}
			
			/*
			
			// read the message
			String msg = FileHandler.read("file.txt");
			System.out.println(msg);
			
			BigInteger[] encrMsg = new BigInteger[msg.length()];
			
			
			
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
			*/
		} while (quit == false);
		
		
	}
}