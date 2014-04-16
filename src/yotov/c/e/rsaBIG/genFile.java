package yotov.c.e.rsaBIG;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class genFile {
 
    public static BigInteger[] read(int length) throws FileNotFoundException, IOException {
        File file = new File("pic2.jpg");
 
        FileInputStream fis = new FileInputStream(file);
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
            }
        } catch (IOException ex) {
        	System.out.println("Error handling file!");
        }
        // the file's byte array
        byte[] bytes = bos.toByteArray();
        
        // split the file's byte array into blocks
        byte[][] blocks = divideArray(bytes, 32);
        
        int bytesLength=0;
        int lenB=0;

        // convert byte array to a numeric array for encryption
        BigInteger[] numBlocks = new BigInteger[blocks.length];
        for(int i=0; i<blocks.length; i++) {
        	numBlocks[i] = new BigInteger(1, blocks[i]);
        	bytesLength+=blocks[i].length;
        	lenB+=blocks[i].length;
        }
        
        // TODO: Mislq, 4e v nakasvaneto e problema - proveri byte koda.
        // proveri i kak se sabirat, za vseki slu4ai
        
        // convert numeric array back into a byte array
        int len=0;
        byte repBlocks[][] = new byte[numBlocks.length][];
        for(int i=0; i<blocks.length; i++) {
        	BigInteger temp = numBlocks[i];
        	repBlocks[i] = temp.toByteArray();
        	len+=repBlocks[i].length;
        }
        System.out.println(bytes.length+" for bytes");
        System.out.println(len+" for repBlocks");
        System.out.println(lenB+" for blocks");

        
        byte toBeBytes[] = new byte[bytes.length*2];
        copySmallArraysToBigArray(repBlocks, toBeBytes);
        // write the byte array into a file
        File someFile = new File("pic2_2.0.jpg");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(toBeBytes);
        fos.flush();
        fos.close();
        
        return numBlocks;
        /*
// check if it works :3
System.out.println(numRep);
if (Arrays.equals(numRep.toByteArray(), blocks[3]))
{
System.out.println("Yup, they're the same!");
}
*/
        // reproduce the old bytes array by concatenating it's divided up form (blocks[][])
    }
    
    
    
    public static void write(byte blocks[][]) throws FileNotFoundException, IOException {
    
    }
    
	public static byte[][] divideArray(byte[] source, int chunksize) {
	
	        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];
	
	        int start = 0;
	
	        for(int i = 0; i < ret.length; i++) {
	            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
	            start += chunksize ;
	        }
	
	        return ret;
    }
	
	public static void copySmallArraysToBigArray(final byte[][] smallArrays,
		    final byte[] bigArray){
		    int currentOffset = 0;
		    for(final byte[] currentArray : smallArrays) {
		        System.arraycopy(currentArray, 0, bigArray, currentOffset, currentArray.length);
		        currentOffset += currentArray.length;
		    }
	}
	
	public static void write(BigInteger numBlocks[]) throws FileNotFoundException, IOException {
	}
	
}

/*package yotov.c.e.rsaBIG;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class genFile {
 
    public static BigInteger[] read(int length) throws FileNotFoundException, IOException {
        File file = new File("pic.jpg");
 
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
            }
        } catch (IOException ex) {
        	System.out.println("Error handling file!");
        }
        // the file's byte array
        byte[] fileBytes = bos.toByteArray();
        
        // split the file's byte array into blocks smaller than the size of key n
        byte[][] byteBlocks = divideArray(fileBytes, 32);
        
        // convert byte array to a numeric array for encryption
        BigInteger[] intBlocks =  new BigInteger[byteBlocks.length];
        for(int i=0; i<byteBlocks.length; i++) {
        	// converting each block to positive big integers
        	intBlocks[i] = new BigInteger(1, byteBlocks[i]);
        }
        return intBlocks;
    }
    
    public static void write(BigInteger numBlocks[]) throws FileNotFoundException, IOException {
    	
    	byte byteBlocks[][] = new byte[numBlocks.length][]; 
    	for( int i = 0; i<numBlocks.length; i++) {
    		byteBlocks[i] = numBlocks[i].toByteArray();
    	}
    	 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         for(int i=0; i<byteBlocks.length; i++){
          outputStream.write(byteBlocks[i]);
         }
         byte repBytes[] = outputStream.toByteArray();	// reproduced Bytes[]
         
        // write the byte array into a file
        File someFile = new File("pic_rewritten.jpg");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(repBytes);
        fos.flush();
        fos.close();
    }
    
	public static byte[][] divideArray(byte[] source, int chunksize) {

        byte[][] ret = new byte[(int)Math.ceil(source.length / (double)chunksize)][chunksize];

        int start = 0;

        for(int i = 0; i < ret.length; i++) {
            ret[i] = Arrays.copyOfRange(source,start, start + chunksize);
            start += chunksize ;
        }

        return ret;
    }
}
*/