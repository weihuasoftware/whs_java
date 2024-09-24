package main;

public class SyncCounter {
	public static void main(String[] args) {
		var c1 =new CounterSync();
		var c2 =new CounterSync();

		// 对c1进行操作的线程:
		new Thread(() -> {
		    c1.add(1);
		}).start();
		new Thread(() -> {
		    c1.dec(1);
		}).start();

		// 对c2进行操作的线程:
		new Thread(() -> {
		    c2.add(1);
		}).start();
		new Thread(() -> {
		    c2.dec(1);
		}).start();
	}

}

class CounterSync {
    private int count = 0;

    public void add(int n) {
        synchronized(this) {
            count += n;
            System.out.println(count);
        }
    }

    public void dec(int n) {
        synchronized(this) {
            count -= n;
            System.out.println(count);
        }
    }

    public int get() {
        return count;
    }
}
