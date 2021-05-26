import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MyClient {

	static DataInputStream din;
	static PrintStream dout;
	static Socket s;

	static String response;
	static int nRec = 0, nLen = 0;

	static String largest=" ";

	public static void main(String[] args) throws Exception {

		s = new Socket("localhost", 50000);
		din = new DataInputStream(s.getInputStream());
		dout = new PrintStream(s.getOutputStream(), true);

		send("HELO");
		send("AUTH " + System.getProperty("user.name"));
		send("REDY");
		send("QUIT");
	}

	//Extracting information about the job into an integer array.

	public static int[] getJobInfo() {

		String[] info = response.split(" ");
		int[] data = new int[info.length - 1];

		for (int i = 1; i < info.length; i++) {
		}
		return data;
	}

	//Sending message to server

	public static void send(String str) throws Exception {

		dout.print(str);
		System.out.println("Client: " + str);
		receive();
	}
}
