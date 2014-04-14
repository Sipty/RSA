package yotov.c.e.rsaBIG;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class genFile {
 
    public static BigInteger[] read() throws FileNotFoundException, IOException {
        File file = new File("warding.pdf");
 
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
        byte[][] blocks = divideArray(bytes, 64);
        
        // convert byte array to a numeric array for encryption
        BigInteger[] numBlocks =  new BigInteger[blocks.length];
        for(int i=0; i<blocks.length; i++) {
        	numBlocks[i] = new BigInteger(blocks[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for(int i=0; i<blocks.length; i++){
        	outputStream.write(blocks[i]);
        }
        byte repBytes[] = outputStream.toByteArray();	// reproduced Bytes[]
        
        for(int i=0; i<numBlocks.length; i++) {
        	System.out.println(numBlocks[i]);
        	
        }
        
        // write the byte array into a file
        File someFile = new File("wardingREWRITTEN.pdf");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(repBytes);
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
}
