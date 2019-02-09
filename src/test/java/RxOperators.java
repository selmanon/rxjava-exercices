import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;

public class RxOperators {
    @Test
    public void testRetry() {
        TestObserver<Integer> ts = new TestObserver<>();

        final boolean[] val = new boolean[] { true };
        Observable.just(1, 2, 3)
                .map(i -> {
                    // fail only the first time that i = 2
                    if (i == 2 && val[0]) {
                        val[0] = false;
                        throw new IllegalArgumentException("throw!");
                    }
                    return i;
                })
                .retry()
                .subscribe(ts);

        ts.awaitTerminalEvent();
        assertThat(ts.values(), Matchers.equalTo(Arrays.asList(1, 2, 3)));
    }
}
