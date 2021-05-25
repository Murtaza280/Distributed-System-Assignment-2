import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
public class MyClient {{
	static String response;
	static int nRec = 0, nLen = 0;
	
	static String largest=" ";
	
	public static void main(String[] args) throws Exception {
		Socket s = new Socket("localhost", 50000);
		DataInputStream din = new DataInputStream(s.getInputStream());
		PrintStream dout = new PrintStream(s.getOutputStream(), true);
		send("HELO");
		send("AUTH " + System.getProperty("user.name"));
		send("REDY");
		
		send("QUIT");
	}
}

