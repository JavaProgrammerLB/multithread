package liubei.juc.callable_future;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class CallableFutureTutorial001 {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public void work() {
        printNowDate();
        printExecutorInfo((ThreadPoolExecutor) executor);
        Callable<List<String>> tasks = () -> {
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Thread.sleep(100);
                resultList.add("[LiuBei]: " + i);
                printExecutorInfo((ThreadPoolExecutor) executor);
            }
            return resultList;
        };

        Future<List<String>> future = executor.submit(tasks);

        List<String> result = null;
        try {
            result = future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            future.cancel(true);
        }

        if (result != null) {
            result.stream().forEach(str -> {
                System.out.println("- " + str);
            });
            printNowDate();
        } else {
            System.out.println("result == null");
        }
        printExecutorInfo((ThreadPoolExecutor) executor);
        executor.shutdown();
    }

    public static void main(String[] args) {
        CallableFutureTutorial001 app = new CallableFutureTutorial001();
        app.work();
    }

    private static void printNowDate() {
        System.out.println("Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    private static void printExecutorInfo(ThreadPoolExecutor threadPoolExecutor) {
        System.out.println("activeCount = " + threadPoolExecutor.getActiveCount()
        +" corePoolSize = " + threadPoolExecutor.getCorePoolSize()
        +" completedTaskCount = " + threadPoolExecutor.getCompletedTaskCount()
        +" queue: " + threadPoolExecutor.getQueue()
        +" rejected policy: " + threadPoolExecutor.getRejectedExecutionHandler());
    }

}

