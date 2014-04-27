package yotov.c.e.rsaBIG;

import java.io.*;

public class FileHandler {
	public static String read(String fileName) throws IOException { 
			 String msg=null;  
			 byte [] buffer =null;  
			 
			 File a_file = new File(fileName);  
			 try  
			 {  
				 FileInputStream fis = new FileInputStream(fileName);  
				 int length = (int)a_file.length();  
				 buffer = new byte [length];  
				 fis.read(buffer);  
				 fis.close();  
			 }  
			 catch(IOException e){e.printStackTrace();
			 }  
			 msg = new String(buffer);  
			 return msg;  
	}

	public static void write(String fileName,String msg) {

        try {
            // Put some bytes in a buffer so we can
            // write them. 
            String bytes = msg;
            byte[] buffer = bytes.getBytes();

            FileOutputStream outputStream =
                new FileOutputStream(fileName);

            // write() writes as many bytes from the buffer
            // as the length of the buffer. 
            outputStream.write(buffer);

            // Always close files.
            outputStream.close();		
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing file '"
                + fileName + "'");
        }
	}
}