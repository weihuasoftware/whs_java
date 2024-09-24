package main;

import java.time.LocalTime;

public class Test01 {
    public static void main(String[] args) throws InterruptedException  {
    	int key = 4;
    	switch (key) {
			case 1: {
		        function01();//测试sleep
				break;
			}
			case 2: {
		        function02();//测试join
				break;
			}
			case 3: {
		        function03();//测试interrupt
				break;
			}
			case 4: {
		        function04();//测试守护线程
				break;
			}
//			case 5: {
//		        function05();
//				break;
//			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + key);
		}
        
    }
    
    public static void function01() {
        System.out.println("main start...");
        Thread t = new Thread() {
            public void run() {
                System.out.println("thread run...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("thread end.");
            }
        };
        t.start();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {}
        System.out.println("main end...");
    	
    }    
    public static void function02() {
        Thread t = new Thread(() -> {
            System.out.println("hello");
        });
        System.out.println("start");
        t.start(); // 启动t线程
        try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 此处main线程会等待t结束
        System.out.println("end");
    }
    public static void function03() {
        Thread t = new MyThread();
        t.start();
        try {
			Thread.sleep(10000);// 暂停1秒
	        t.interrupt(); // 中断t线程
	        t.join(); // 等待t线程结束
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.println("end");
    	
    }
    public static void function04() {
        Thread t = new DaemonThread();
		// 如果注释下一行，观察Thread1的执行情况:
		t.setDaemon(true);
		t.start();
		System.out.println("main: wait 3 sec...");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println("main: end.");
    	
    }
}

class MyThread extends Thread {
    public void run() {
        int n = 0;
        try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        while (! isInterrupted()) {
            n ++;
            System.out.println(n + " hello!");
        }
    }
}

class DaemonThread extends Thread {

	public void run() {
		for (;;) {
            System.out.println(LocalTime.now());
			System.out.println("Thread-1: running...");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
}
