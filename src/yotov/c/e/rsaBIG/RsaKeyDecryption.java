package yotov.c.e.rsaBIG;

import java.math.BigInteger;
import java.io.*;
class RsaKeyDecryption {
   private BigInteger n, d;
   public static void main(String[] args) {
    /*  if (a.length<3) {
         System.out.println("Usage:");
         System.out.println("java RsaKeyDecryption key input output");
         return;
      }*/
   
      String[] a = new String[3];
      a[0] = "key_16.txt";
      a[1] = "pic2_new.encrypted";
      a[2] = "pic2_fresh.jpg";
      String keyFile = a[0];
      String input = a[1];
      String output = a[2];

      RsaKeyDecryption encryptor = new RsaKeyDecryption(keyFile);
      encryptor.decrypt(input,output);
   }

// Reading in RSA private key
   RsaKeyDecryption(String input) {
      try {
         BufferedReader in = new BufferedReader(new FileReader(input));
         String line = in.readLine();
         while (line!=null) {
            if (line.indexOf("Modulus: ")>=0) {
               n = new BigInteger(line.substring(9));
            }
            if (line.indexOf("Private key: ")>=0) {
               d = new BigInteger(line.substring(13));
            }
            line = in.readLine();
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      System.out.println("--- Reading private key ---");
      System.out.println("Modulus: "+n);
      System.out.println("Key size: "+n.bitLength());
      System.out.println("Private key: "+d);
   }

// Decrypting cipher text
   public void decrypt(String intput, String output) {
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
         int dataSize = 0;
         while (fis.read(cipherTextBlock)>0) {
            blocks++;
            BigInteger cipherText = new BigInteger(1,cipherTextBlock);
            BigInteger clearText = cipherText.modPow(d,n);
            byte[] clearTextData = clearText.toByteArray();
            putBytesBlock(clearTextBlock,clearTextData);

            dataSize = clearTextSize;
            if (fis.available()==0) {
               dataSize = getDataSize(clearTextBlock);
            }
            if (dataSize>0) {
               fos.write(clearTextBlock,0,dataSize);
            }
         }
         fis.close();
         fos.close();
         System.out.println("Decryption block count: "+blocks);
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

// Getting data size from a padded block
   public static int getDataSize(byte[] block) {
      int bSize = block.length;
      int padValue = block[bSize-1];
      return (bSize-padValue)%bSize;
   }
}