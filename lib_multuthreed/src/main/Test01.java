package main;

import java.time.LocalTime;

public class Test01 {
    public static void main(String[] args) throws InterruptedException  {
    	int key = 4;
    	switch (key) {
			case 1: {
		        function01();//����sleep
				break;
			}
			case 2: {
		        function02();//����join
				break;
			}
			case 3: {
		        function03();//����interrupt
				break;
			}
			case 4: {
		        function04();//�����ػ��߳�
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
        t.start(); // ����t�߳�
        try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // �˴�main�̻߳�ȴ�t����
        System.out.println("end");
    }
    public static void function03() {
        Thread t = new MyThread();
        t.start();
        try {
			Thread.sleep(10000);// ��ͣ1��
	        t.interrupt(); // �ж�t�߳�
	        t.join(); // �ȴ�t�߳̽���
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.println("end");
    	
    }
    public static void function04() {
        Thread t = new DaemonThread();
		// ���ע����һ�У��۲�Thread1��ִ�����:
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
