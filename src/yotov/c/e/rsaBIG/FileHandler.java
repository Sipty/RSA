package yotov.c.e.rsaBIG;

import java.io.*;

public class FileHandler {
	public static String read(String fileName) {
		String msg=null;
        try {
            byte[] buffer = new byte[(int) fileName.length()];
            FileInputStream inputStream = new FileInputStream(fileName);
            int total=0, nRead=0;
            
            while((nRead = inputStream.read(buffer)) > -1) {
                total += nRead;
            }	
            // close stream
            inputStream.close();	
            
            // the message
            msg = new String(buffer);
        }
        
        catch(FileNotFoundException ex) {System.out.println("Unable to open file '" + fileName + "'");				
        }
        catch(IOException ex) {System.out.println("Error reading file '" + fileName + "'");					
        }
        
        return msg;
	}
	
	public static void write(String fileName,String msg) {

        try {
            // Put some bytes in a buffer so we can
            // write them. Usually this would be
            // image data or something. Or it might
            // be unicode text.
            String bytes = msg;
            byte[] buffer = bytes.getBytes();

            FileOutputStream outputStream =
                new FileOutputStream(fileName);

            // write() writes as many bytes from the buffer
            // as the length of the buffer. You can also
            // use
            // write(buffer, offset, length)
            // if you want to write a specific number of
            // bytes, or only part of the buffer.
            outputStream.write(buffer);

            // Always close files.
            outputStream.close();		

            System.out.println("Wrote " + buffer.length + 
                " bytes");
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
	}
}