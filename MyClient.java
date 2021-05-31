import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MyClient {

	static String response_var;
	static int n_Rec = 0, n_Len = 0;

	static String largest=" ";
	static DataInputStream din;
	static PrintStream dout;
	static Socket s;

	public static void main(String[] args) throws Exception {
		s = new Socket("localhost", 50000);
		din = new DataInputStream(s.getInputStream());
		dout = new PrintStream(s.getOutputStream(), true);

		send("HELO");

		send("AUTH " + System.getProperty("user.name"));

		send("REDY");

		while (!(response_var.equals("NONE"))) {
			if (!(response_var.startsWith("JOBN") || response_var.startsWith("JOBP"))) {
				if (response_var.startsWith("JCPL")) {
					send("REDY");
				}
				continue;
			}
			int[] data = getJobInfo();
			if(largest.equals(" ")){
				largest=findLargest();
			}
			send("SCHD "+data[1]+" " + largest);

			send("REDY");

		}

		send("QUIT");

		din.close();
		dout.close();
		s.close();
	}
	public static String findLargest() throws Exception{

		send("GETS All");
		String temp;
		temp=response_var;
		String[] data=temp.split(" ");
		n_Rec = Integer.parseInt(data[1]);
		n_Len = Integer.parseInt(data[2]);
		send("OK");
		temp = response_var;
		send("OK");
		String[] servers = temp.split("\n");
		String name="";
		int disk_size=0;
		for(String i : servers) {
			String[] j=i.split(" ");
			String n = j[0];
			int ds = Integer.parseInt(j[4].trim());
			if(ds>disk_size) {
				name = n;
				disk_size=ds;
			}
		}
		return name+" 0";
	}

	//	Extracting information about the Job into an integer array

	public static int[] getJobInfo() {
		String[] info;
		info = response_var.split(" ");
		int[] data;
		data = new int[info.length - 1];
		for (int i = 1; i < info.length; i++) {
			data[i - 1] = Integer.parseInt(info[i]);
		}
		return data;
	}

	// Sending message to the server

	public static void send(String str) throws Exception {
		str += "\n";
		dout.print(str);
		dout.flush();
		receive();
	}

	// Reading the message from the server

	public static void receive() throws Exception {

		int SIZE;
		SIZE = Math.max(500, n_Rec * n_Len + 1);
		byte[] bytes;
		bytes = new byte[SIZE];
		din.read(bytes);

		String str;
		str = new String(bytes, StandardCharsets.UTF_8);
		response_var = str.trim();
	}
}
