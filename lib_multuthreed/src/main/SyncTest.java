package main;

public class SyncTest {
    public static void main(String[] args) throws Exception {
    	int key = 1;
    	switch (key) {
			case 1: {
		        function01();//≤‚ ‘sleep
				break;
			}
//			case 2: {
//		        function02();//≤‚ ‘join
//				break;
//			}
//			case 3: {
//		        function03();//≤‚ ‘interrupt
//				break;
//			}
//			case 4: {
//		        function04();
//				break;
//			}
//			case 5: {
//		        function05();
//				break;
//			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + key);
		}
    }    
    
    public static void function01() throws Exception {
        var add = new AddThread();
        var dec = new DecThread();
        add.start();
        dec.start();
        add.join();
        dec.join();
        System.out.println(Counter.count);
    }  

}

class Counter {
	public static final Object lock = new Object();
    public static int count = 0;
}

class AddThread extends Thread {
    public void run() {
        for (int i=0; i<100; i++) {
            synchronized(Counter.lock) {
            	System.out.println("jia");
                Counter.count += 1;
            }
        }
    }
}

class DecThread extends Thread {
    public void run() {
        for (int i=0; i<100; i++) {
            synchronized(Counter.lock) {
            	System.out.println("jian");
                Counter.count -= 1;
            }
        }
    }
}
