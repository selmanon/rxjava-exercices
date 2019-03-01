import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureSample {

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> f = exec.submit(new MyCallable());

        System.out.println(f.isDone()); //False, always false as the f.get() is not called

        System.out.println(f.get()); //Waits until the task is done, then prints 1 => blocks the thread

        System.out.println(f.isDone()); //True

    }

    /**
     * A task that sleeps for a second, then returns 1
     **/
    public static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(2000);
            return 1;
        }

    }
}
