package abb;

public interface AtomicBroadcastBuffer<T> {
	
	public void put(T item);
	public T get(int id);

}
