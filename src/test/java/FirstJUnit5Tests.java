import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

public class FirstJUnit5Tests {

    @Test
    public void operationFinallyOrder() {
        Observable.empty()
                .firstOrError()
                .subscribe(System.out::println);

    }

}
