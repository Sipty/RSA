package yotov.c.e.rsaBIG;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {
	private static int length = 512;	// prime numbers' default bit length
	private static Scanner user_input = new Scanner(System.in); // user input scanner
	private static String layer_1;	// user input strings
	private static String layer_2;
	
	public static void main(String[] args) throws IOException {
		boolean quit = false;	// main loop bool
		
		System.out.println("Welcome to C. Yotov's RSA implementation!");
		// If there is a problem with the menu, use the commented code below
/*
		final long startTimeKey = System.currentTimeMillis();
		KeyGen.keyCreation(256);
		final long endTimeKey = System.currentTimeMillis();

		final long startTimeEncr = System.currentTimeMillis();

		Encryption encr = new Encryption("rsa_public_key.txt");
		String fn = "pic",
				fex = ".jpg";
		encr.encrypt(fn+fex, fn+"_fresh.encrypted");
		
		final long endTimeEncr = System.currentTimeMillis();


		final long startTimeDecr = System.currentTimeMillis();

		Decryption decr = new Decryption("rsa_private_key.txt");
		decr.decrypt(fn+"_fresh.encrypted", fn+"_fresh"+fex);

		final long endTimeDecr = System.currentTimeMillis();
		
		System.out.println("Total encryption time: " + (endTimeEncr - startTimeEncr) );
		System.out.println("Total decryption time: " + (endTimeDecr - startTimeDecr) );
		System.out.println("Total key creation time: " + (endTimeKey - startTimeKey) );
*/
		
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
					break;
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
	
// KEY CREATION
	private static void keyCreation() {

		boolean kc_loop = true;	// makes sure key creation loops
		do {
			System.out.println("\n\n\n+==+ KEY CREATION +==+ \n\n"+
							"-- 1 - Create public & private keys -- \n"+
							"-- 2 - Change prime numbers' bit length (current: "+length+" bits) -- \n"+
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
		// default file names
		String fn= "pic", fex=".jpg";
		
		do {
			System.out.println("\n\n\n+==+ ENCRYPTION +==+ \n\n"+
								"-- 1 - Begin encryption -- \n"+
								"-- 2 - Change file name. (Currently:" +fn+fex+") -- \n"+
								"-- 3 - Go back -- \n");
			
			// take input
			layer_2 = user_input.next();
			switch(layer_2) {
				
				// Begin encryption
				case "1":
					
					final long startTimeEncr = System.currentTimeMillis();

					Encryption encr = new Encryption("rsa_public_key.txt");
					
					encr.encrypt(fn+fex, fn+"_fresh.encrypted");
					
					final long endTimeEncr = System.currentTimeMillis();

					System.out.println("Total encryption time: " + (endTimeEncr - startTimeEncr) );

					break;
					
				// Go back
				case "2": 
					boolean kbl_loop;	// loops until valid input is given

					
					System.out.println("Enter file name, hit enter and then write the file format (eg. .txt):");
					
					do{
						try {
							fn=user_input.next();
							fex = user_input.next();
							kbl_loop = false;
						}
						catch(Exception excptn) {
							System.out.println("Error: not an integer!\n"+
												"Try again: ");
							kbl_loop = true;
						}
					}while(kbl_loop);
					
					break;
				case "3":
					e_loop = false;
					break;
			}
			
		} while(e_loop);
	}

//DECRYPTION
	private static void decryption() {
		boolean d_loop = true;
		String fn= "pic", fex=".jpg";

		do {
			System.out.println("\n\n\n+==+ DECRYPTION +==+ \n\n"+
								"-- 1 - Begin decryption -- \n"+
								"-- 2 - Change file name (Currently:" +fn+fex+") -- \n"+
								"-- 3 - Go back -- \n");
			layer_2 = user_input.next();
			switch(layer_2) {
				case "1":
					final long startTimeDecr = System.currentTimeMillis();
		
					Decryption decr = new Decryption("rsa_private_key.txt");
					decr.decrypt(fn+"_fresh.encrypted", fn+"_fresh"+fex);
		
					final long endTimeDecr = System.currentTimeMillis();
					break;
					
				case "2": 
					boolean kbl_loop;	// loops until valid input is given
		
					System.out.println("Enter file name, hit enter and then write the file format (eg. .txt):");
					
					do{
						try {
							fn=user_input.next();
							fex = user_input.next();
							kbl_loop = false;
						}
						catch(Exception excptn) {
							System.out.println("Error: not an integer!\n"+
												"Try again: ");
							kbl_loop = true;
						}
					}while(kbl_loop);
					
							break;
							
						// Go back
					case "3":
							d_loop = false;
							break;
			}
		} while(d_loop);
	}
}