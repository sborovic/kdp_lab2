package lab2;

public class Consumer {

	public static void main(String[] args) throws Exception {
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		int id = 1;
		AB ab = new PCClass();

		if (!ab.init(host, port))
			return;

		for (int i = 2; i < args.length; i++) {
			String name = args[i];

			Goods goods = ab.getGoods(name);
			System.out.println("(id="+id+") Consuming file " + name);

			int size = goods.getNumLines();
			for (int j = 0; j < size; j++) {
				System.out.println("(id="+id+") Consumed line " + goods.readLine());
				Thread.sleep(1000 + (int) (Math.random() * 734));
			}
		}
		ab.close();
	}
}
