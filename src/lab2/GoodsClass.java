package lab2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@SuppressWarnings("serial")
public class GoodsClass implements Serializable, Goods {
	private String name;
	private Queue<String> data = new LinkedList<String>();

	public GoodsClass(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String[] getBody() {
		return data.toArray(new String[0]); 
	}

	@Override
	public void setBody(String[] body) {
		this.data = new LinkedList<>(Arrays.asList(body));

	}

	@Override
	public String readLine() {
		return data.remove();
	}

	@Override
	public void printLine(String body) {
		data.add(body);
	}

	@Override
	public int getNumLines() {
		return data.size();
	}

	@Override
	public void save(String name) {
		try (PrintWriter pw = new PrintWriter(name)) {
			for (String s : data) {
				pw.println(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void load(String name) {
		try (BufferedReader br = new BufferedReader(new FileReader(name))) {
			String line;
			while ((line = br.readLine()) != null)
				data.add(line);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
