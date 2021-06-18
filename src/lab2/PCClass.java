package lab2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PCClass implements AB {

	private Socket client;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	@Override
	public boolean init(String host, int port) {
		try {
			client = new Socket(host, port);
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean close() {
		if (client != null)
			try {
				out.writeObject("stop");
				client.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public void putGoods(String name, Goods goods) {
		try {
			out.writeObject("put");
			out.writeObject(name);
			out.writeObject(goods);
			in.readObject(); // opcioni prijem potvrde

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Goods getGoods(String name) {
		try {
			out.writeObject("get");
			out.writeObject(name);
			Goods goods = (Goods) in.readObject();
			out.writeObject("OK"); // opciono slanje potvrde
			return goods;

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
