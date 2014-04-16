package yotov.c.e.rsaBIG;

import java.math.BigInteger;
import java.io.*;
class RsaKeyEncryption {
   private BigInteger n, e;
   public static void main(String[] args) {
     /* if (a.length<3) {
         System.out.println("Usage:");
         System.out.println("java RsaKeyEncryption key input output");
         return;
      }
      */
	  //String[] s = ["pic2.jpg", "pic2.jpg", "pic2_3.0.jpg"];
      String[] a = new String[3];
      a[0] = "key_16.txt";
      a[1] = "pic2.jpg";
      a[2] = "pic2_new.encrypted";
      
	  String keyFile = a[0];
      String input = a[1];
      String output = a[2];

      RsaKeyEncryption encryptor = new RsaKeyEncryption(keyFile);
      encryptor.encrypt(input,output);
   }

// Reading in RSA public key
   RsaKeyEncryption(String input) {
      try {
         BufferedReader in = new BufferedReader(new FileReader(input));
         String line = in.readLine();
         while (line!=null) {
            if (line.indexOf("Modulus: ")>=0) {
               n = new BigInteger(line.substring(9));
            }
            if (line.indexOf("Public key: ")>=0) {
               e = new BigInteger(line.substring(12));
            }
            line = in.readLine();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      System.out.println("--- Reading public key ---");
      System.out.println("Modulus: "+n);
      System.out.println("Key size: "+n.bitLength());
      System.out.println("Public key: "+e);
   }

// Encrypting original message
   public void encrypt(String intput, String output) {
      int keySize = n.bitLength();                       // In bits
      int clearTextSize = Math.min((keySize-1)/8,256);   // In bytes
      int cipherTextSize = 1 + (keySize-1)/8;            // In bytes
      System.out.println("Cleartext block size: "+clearTextSize);
      System.out.println("Ciphertext block size: "+cipherTextSize);
      try {
         FileInputStream fis = new FileInputStream(intput);
         FileOutputStream fos = new FileOutputStream(output);
         byte[] clearTextBlock = new byte[clearTextSize];
         byte[] cipherTextBlock = new byte[cipherTextSize];
         long blocks = 0;
         int dataSize = fis.read(clearTextBlock);
         boolean isPadded = false; 

//       Reading input message
         while (dataSize>0) {
            blocks++;
            if (dataSize<clearTextSize) {
               padBytesBlock(clearTextBlock,dataSize);
               isPadded = true;
            }
            
            BigInteger clearText = new BigInteger(1,clearTextBlock);
            BigInteger cipherText = clearText.modPow(e,n);
            byte[] cipherTextData = cipherText.toByteArray();
            putBytesBlock(cipherTextBlock,cipherTextData);
            fos.write(cipherTextBlock);
            
            dataSize = fis.read(clearTextBlock);
         }

//       Adding a full padding block, if needed
         if (!isPadded) {
            padBytesBlock(clearTextBlock,0);
            BigInteger clearText = new BigInteger(1,clearTextBlock);
            BigInteger cipherText = clearText.modPow(e,n);
            byte[] cipherTextData = cipherText.toByteArray();
            putBytesBlock(cipherTextBlock,cipherTextData);
            fos.write(cipherTextBlock);
         }

         fis.close();
         fos.close();
         
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

// Putting bytes data into a block
   public static void putBytesBlock(byte[] block, byte[] data) {
      int bSize = block.length;
      int dSize = data.length;
      int i = 0;
      while (i<dSize && i<bSize) {
          block[bSize-i-1] = data[dSize-i-1];
          i++;
      }
      while (i<bSize) {
          block[bSize-i-1] = (byte)0x00;
          i++;
      }
   }

// Padding input message block
   public static void padBytesBlock(byte[] block, int dataSize) {
      int bSize = block.length;
      int padSize = bSize - dataSize;
      int padValue = padSize%bSize;
      for (int i=0; i<padSize; i++) {    
          block[bSize-i-1] = (byte) padValue;
      }
   }
}
