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
        myThread.start(); // 启动线程
		
		LocalDateTime startTime = LocalDateTime.now();
        // 実行継続時間
        int duration = 2000;
        // 終了日時
        LocalDateTime endTime = startTime.plusSeconds(TimeUnit.MILLISECONDS.toSeconds(duration));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddkkmmssSSS");
        String execId = startTime.format(dtf2);
        // スリープ時間
        int sleepTime = 1;
        
        // 現在日時
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println("开始"+execId);
        try {
            while (nowTime.compareTo(endTime) < 1) {
                // 実行抑止ファイル存在確認
                String bootCheckFilePath = "data/file.txt";
                File bootCheckFile = new File(bootCheckFilePath);
                if (bootCheckFile.exists()) {
                    // 実行抑止ファイルが存在する場合、処理をしない
                    if (sleepTime != 0) {
                        // 次の属性登録待ち取得までスリープする
                    	/*没找到线程定义位置*/
                    	myThread.sleep(sleepTime * 1000);
                    }
                    nowTime = LocalDateTime.now();
                    continue;
                }
                // 属性設定処理単位を取得する
                int requestMaxCount = 30;
                
                // 変換リクエスト管理テーブルから属性設定中のリクエスト件数を取得する,这里是数据库查到的件数
                int settingCount = 20;
                
                // 取得した属性設定中のリクエスト件数が設定ファイルのリクエスト上限件数未満かどうか
                if (requestMaxCount <= settingCount) {
                    // 移行リクエストの同時実行可能数の上限に達しているため、処理を終了しました。
                    if (sleepTime != 0) {
                        // 次の属性登録待ち取得までスリープする
                        Thread.sleep(sleepTime * 1000);
                    }
                    nowTime = LocalDateTime.now();
                    continue;
                }
                
                // 設定ファイルのリクエスト上限件数 - 処理中リクエスト件数の件数分、属性設定を実施する
                List<RequestMng> mnglist = new ArrayList<>(); 
                mnglist.add(new RequestMng());
                mnglist.add(new RequestMng());
                mnglist.add(new RequestMng());
                if (mnglist == null || mnglist.size() < 1) {
                    // 対象となる移行リクエストが存在しないため、処理を終了しました。
                    if (sleepTime != 0) {
                        // 次のリクエスト送信までスリープする
                        Thread.sleep(sleepTime * 1000);
                    }
                    nowTime = LocalDateTime.now();
                    continue;
                }
                // インスタンスのリスト
                List<TestExec> jobs = new ArrayList<TestExec>();
                // リクエスト単位でスレッド化する
                int index = 0;
                for (RequestMng convReqMng : mnglist) {

                    ////////////////////////////////////////
                    // ファイル毎に属性設定
                    ////////////////////////////////////////
                	index++;
                    jobs.add(new TestExec("kaishi",index));
                }

                final ForkJoinPool pool = new ForkJoinPool(requestMaxCount);
                // 並列で処理を実行
                pool.submit(() -> jobs.parallelStream().forEach(job -> {
                    try {
                        job.excute();
                    } catch (Exception e) {
                        System.out.print("错误3");
                    }
                })).get();
                nowTime = LocalDateTime.now();
                System.out.println("现在时间："+nowTime+"结束时间："+endTime);
            }
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
