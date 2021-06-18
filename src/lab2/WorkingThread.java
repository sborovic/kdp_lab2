package lab2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import abb.LockBoundedBuffer;

public class WorkingThread extends Thread {
	private Socket client;
	private ConcurrentHashMap<String, LockBoundedBuffer<Goods>> buffers;

	public WorkingThread(ConcurrentHashMap<String, LockBoundedBuffer<Goods>> buffers, Socket client) {
		this.buffers = buffers;
		this.client = client;
	}

	@Override
	public void run() {
		try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
			String operation;
			while (!"stop".equals(operation = (String) in.readObject())) {
				String name = (String) in.readObject();
				Goods goods;
				Function<String, LockBoundedBuffer<Goods>> f = k -> new LockBoundedBuffer<Goods>();

				switch (operation) {
				case "put":
					goods = (Goods) in.readObject();
					out.writeObject("OK"); // opciono slanje potvrde
					buffers.computeIfAbsent(name, f).put(goods);
					break;
				case "get":		
					goods = buffers.computeIfAbsent(name, f).get();
					out.writeObject(goods);
					in.readObject(); // opcioni prijem potvrde
					break;

				default:
					System.err.println(String.format("*** Operation %s not supported! *** ", operation));
				}
			}

		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}

	}
}
