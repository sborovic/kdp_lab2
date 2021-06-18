package lab2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import abb.LockBoundedBuffer;

public class Server {
	public static void main(String[] args) {
		int port = Integer.parseInt(args[0]);
		ConcurrentHashMap<String, LockBoundedBuffer<Goods>> buffers = new ConcurrentHashMap<>();
		try (ServerSocket server = new ServerSocket(port)) {
			System.out.println("Server started...");
			
			while (true) { 
				Socket client = server.accept();
				System.out.println("* WorkingThread started... *");
				new WorkingThread(buffers, client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
