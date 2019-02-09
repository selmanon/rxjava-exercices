import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class RxSubjects {
    @Test
    public void testDelayWithRebatchRequests() {
        TestSubscriber<Integer> ts = new TestSubscriber<>();
        Subject<Integer> subject = PublishSubject.create();

        subject
                .toFlowable(BackpressureStrategy.LATEST)
                .delay(0, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(ignored -> {
                    System.err.println("before work");
                    return ignored;
                })
                .delay(0, TimeUnit.MILLISECONDS, Schedulers.computation())

                .map(ignored -> {
                    System.err.println("work");
                    Thread.sleep(2000);
                    return ignored;
                })
                .rebatchRequests(1)
                .lastOrError().toFlowable()
                .subscribe(ts);

        for (int i = 0; i < 32; i++) {
            subject.onNext(i);
        }

        subject.onComplete();

        ts.awaitTerminalEvent();
        assertThat(ts.valueCount(), is(lessThan(32)));
    }


}
