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
	private static int length = 256;	// prime numbers' default bit length
	private static Scanner user_input = new Scanner(System.in); // user input scanner
	private static String layer_1;	// user input strings
	private static String layer_2;
	
	public static void main(String[] args) throws IOException {
		boolean quit = false;	// main loop bool
		
		System.out.println("Welcome to C. Yotov's RSA implementation!");
		
		KeyGen.keyCreation(64);
		
		// time the program
		final long startTimeEncr = System.currentTimeMillis();

		Crypto encr = new Crypto("rsa_public_key.txt");
		String fn = "pic",
				fex = ".jpg";
		encr.encrypt(fn+fex, fn+"_fresh.encrypted");
		
		final long endTimeEncr = System.currentTimeMillis();


		final long startTimeDecr = System.currentTimeMillis();

		Decryption decr = new Decryption("rsa_private_key.txt");
		decr.decrypt(fn+"_fresh.encrypted", fn+"_fresh"+fex);

		final long endTimeDecr = System.currentTimeMillis();
		
		System.out.println("Total encryption time: " + (endTimeEncr - startTimeEncr) );
		System.out.println("Total encryption time: " + (endTimeDecr - startTimeDecr) );


	}
		
		
		/*
		do {
		// MAIN MENU
			System.out.println("\n\n\n+==+ MAIN MENU +==+\n\n"+
								"-- 1 - Key Creation -- \n"+
								"-- 2 - Encryption -- \n"+
								"-- 3 - Decryption -- \n"+
								"-- 4 - End -- \n\n");
			layer_1 = user_input.next();	// request user input
			
			// go into the first layer of the menu
			switch(layer_1) {
			
			// KEY CREATION
				case "1":
					keyCreation();
					break;
			// ENCRYPTION
				case "2":
					encryption();
					break;
			// DECRYPTION
				case "3":
					decryption();
			// exit
				case "4":
					quit = true;
					System.out.println("End.");
					break;
				default: 
					System.out.println("Please choose a valid menu.");
			}
		} while (quit == false);
		
		
	}
	*/
// KEY CREATION
	private static void keyCreation() {

		boolean kc_loop = true;	// makes sure key creation loops
		do {
			System.out.println("\n\n\n+==+ KEY CREATION +==+ \n\n"+
							"-- 1 - Create public & private keys -- \n"+
							"-- 2 - Change keys' bit length (current: "+length+" bits) -- \n"+
							"-- 3 - Go back --");
			// take input
			layer_2 = user_input.next();
			
			switch(layer_2) {		
			// Key creation
				case "1":
					KeyGen.keyCreation(length);
					
					System.out.println("\nPublic & Private keys have been created and saved.");
					break;
			// Key bit length
				case "2":
					boolean kbl_loop;	// loops until valid input is given
					
					System.out.println("Enter required bit length: ");
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
				
			// Quit
				case "3":
					kc_loop = false;
					break;
				default:
					System.out.println("Invalid input.");
					break;
			}
		}while(kc_loop);
	}

// ENCRYPTION
	private static void encryption() {

		boolean e_loop = true;
		
		do {
			System.out.println("\n\n\n+==+ ENCRYPTION +==+ \n\n"+
								"-- 1 - Begin encryption -- \n"+
								"-- 2 - Go back -- \n");
			
			// take input
			layer_2 = user_input.next();
			switch(layer_2) {
				
				// Begin encryption
				case "1":
					try {
					BigInteger intBlocks[] = genFile.read(length); 
					BigInteger[] encrMsg = new BigInteger[intBlocks.length];
					genFile.write(intBlocks);
					// load the public keys
					String public_key = FileHandler.read("public.txt");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					String[] keys = public_key.split(" ");	
					BigInteger e = new BigInteger(keys[0].trim());
					BigInteger n = new BigInteger(keys[1].trim());
					
					// encrypt the message
					encrMsg = Crypto.encrypt(intBlocks, e, n); 
					
					// write cipher text to file
					String encrMsg_String="";

					for(int i=0; i<encrMsg.length; i++) {
						encrMsg_String+=encrMsg[i]+" ";


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
	
	}

//DECRYPTION
	private static void decryption() {
		boolean d_loop = true;
		
		do {
			System.out.println("\n\n\n+==+ DECRYPTION +==+ \n\n"+
								"-- 1 - Begin decryption -- \n"+
								"-- 2 - Go back -- \n");
			
			// take input
			layer_2 = user_input.next();
			switch(layer_2) {
				
				// Begin encryption
				case "1":
					
					// decrypt the message

					// load the cipher file
					String msg = FileHandler.read("encrypted_msg.txt");
					// create the array holding each encrypted block
					// and one that will keep the decrypted message
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
					
					genFile.write(Crypto.decrypt(decrMsg, d, n));
					break;
					
				// Go back
				case "2":
					d_loop = false;
					break;
			}
			
		} while(d_loop);
		break;
	}
}