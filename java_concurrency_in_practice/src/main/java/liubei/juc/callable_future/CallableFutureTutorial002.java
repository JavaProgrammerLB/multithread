package liubei.juc.callable_future;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class CallableFutureTutorial002 {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public void work() {
        printNowDate();
        printExecutorInfo((ThreadPoolExecutor) executor);
        List<Callable<String>> tasks = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            final int temp = i;

            Callable<String> task = () -> {
                Thread.sleep(1000);
                return "[liubei]:" + temp;
            };
            tasks.add(task);
        }

        List<Future<String>> futures = new ArrayList<>();
        tasks.stream().forEach(task -> {
            Future<String> future = executor.submit(task);
            futures.add(future);
            printExecutorInfo((ThreadPoolExecutor) executor);
        });

        futures.stream().forEach(future -> {
            try {
                String result = future.get();
                System.out.println(result);
                printExecutorInfo((ThreadPoolExecutor) executor);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                future.cancel(true);
            }
        });

        executor.shutdown();
        printNowDate();
    }

    public static void main(String[] args) {
        CallableFutureTutorial002 app = new CallableFutureTutorial002();
        app.work();
    }

    private static void printNowDate() {
        System.out.println("Now is " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    private static void printExecutorInfo(ThreadPoolExecutor threadPoolExecutor) {
        System.out.println("activeCount = " + threadPoolExecutor.getActiveCount()
                + " corePoolSize = " + threadPoolExecutor.getCorePoolSize()
                + " completedTaskCount = " + threadPoolExecutor.getCompletedTaskCount()
                + " queue: " + threadPoolExecutor.getQueue()
                + " rejected policy: " + threadPoolExecutor.getRejectedExecutionHandler());
    }

}

