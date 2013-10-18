package yotov.c.e.rsa;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Write {
	public static void writing(char[] message) {
	    try {
	        File text = new File("GoodMorningVietnam.txt");
	        FileOutputStream outputFile = new FileOutputStream(text);
	        OutputStreamWriter outputStream = new OutputStreamWriter(outputFile);    
	        Writer writer = new BufferedWriter(outputStream);
	        for(int i=0; i<message.length; i++) {
	        	writer.write(message[i]);
	        }
	        writer.close();
	    } catch (IOException e) {
	        System.err.println("Problem writing to file.");
	    }
	}
}