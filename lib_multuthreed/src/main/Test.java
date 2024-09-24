package main;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Test extends Thread{
	
	
	public static void main(String[] args) {
        Thread myThread = new Thread();
        myThread.start(); // �����߳�
		
		LocalDateTime startTime = LocalDateTime.now();
        // �g�о@�A�r�g
        int duration = 2000;
        // �K���Օr
        LocalDateTime endTime = startTime.plusSeconds(TimeUnit.MILLISECONDS.toSeconds(duration));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddkkmmssSSS");
        String execId = startTime.format(dtf2);
        // ����`�וr�g
        int sleepTime = 1;
        
        // �F���Օr
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println("��ʼ"+execId);
        try {
            while (nowTime.compareTo(endTime) < 1) {
                // �g����ֹ�ե�������ڴ_�J
                String bootCheckFilePath = "data/file.txt";
                File bootCheckFile = new File(bootCheckFilePath);
                if (bootCheckFile.exists()) {
                    // �g����ֹ�ե����뤬���ڤ�����ϡ��I��򤷤ʤ�
                    if (sleepTime != 0) {
                        // �Τ����Ե��h����ȡ�äޤǥ���`�פ���
                    	/*û�ҵ��̶߳���λ��*/
                    	myThread.sleep(sleepTime * 1000);
                    }
                    nowTime = LocalDateTime.now();
                    continue;
                }
                // �����O���I��gλ��ȡ�ä���
                int requestMaxCount = 30;
                
                // ��Q�ꥯ�����ȹ���Ʃ`�֥뤫�������O���ФΥꥯ�����ȼ�����ȡ�ä���,���������ݿ�鵽�ļ���
                int settingCount = 20;
                
                // ȡ�ä��������O���ФΥꥯ�����ȼ������O���ե�����Υꥯ���������޼���δ�����ɤ���
                if (requestMaxCount <= settingCount) {
                    // ���Хꥯ�����Ȥ�ͬ�r�g�п����������ޤ��_���Ƥ��뤿�ᡢ�I���K�ˤ��ޤ�����
                    if (sleepTime != 0) {
                        // �Τ����Ե��h����ȡ�äޤǥ���`�פ���
                        Thread.sleep(sleepTime * 1000);
                    }
                    nowTime = LocalDateTime.now();
                    continue;
                }
                
                // �O���ե�����Υꥯ���������޼��� - �I���Хꥯ�����ȼ����μ����֡������O����gʩ����
                List<RequestMng> mnglist = new ArrayList<>(); 
                mnglist.add(new RequestMng());
                mnglist.add(new RequestMng());
                mnglist.add(new RequestMng());
                if (mnglist == null || mnglist.size() < 1) {
                    // ����Ȥʤ����Хꥯ�����Ȥ����ڤ��ʤ����ᡢ�I���K�ˤ��ޤ�����
                    if (sleepTime != 0) {
                        // �ΤΥꥯ���������Ťޤǥ���`�פ���
                        Thread.sleep(sleepTime * 1000);
                    }
                    nowTime = LocalDateTime.now();
                    continue;
                }
                // ���󥹥��󥹤Υꥹ��
                List<TestExec> jobs = new ArrayList<TestExec>();
                // �ꥯ�����ȅgλ�ǥ���åɻ�����
                int index = 0;
                for (RequestMng convReqMng : mnglist) {

                    ////////////////////////////////////////
                    // �ե����뚰�������O��
                    ////////////////////////////////////////
                	index++;
                    jobs.add(new TestExec("kaishi",index));
                }

                final ForkJoinPool pool = new ForkJoinPool(requestMaxCount);
                // �K�ФǄI���g��
                pool.submit(() -> jobs.parallelStream().forEach(job -> {
                    try {
                        job.excute();
                    } catch (Exception e) {
                        System.out.print("����3");
                    }
                })).get();
                nowTime = LocalDateTime.now();
                System.out.println("����ʱ�䣺"+nowTime+"����ʱ�䣺"+endTime);
            }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
