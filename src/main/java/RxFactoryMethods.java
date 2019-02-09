import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RxFactoryMethods {



        public static void main(String[] args) {

            Observable<String> values = Observable.create(observableEmitter ->{
                observableEmitter.onNext("Hello");
                observableEmitter.onNext("World");
            });

            Disposable subscription =  values.subscribe(
                v -> System.out.println("Received: " + v),

                e -> System.out.println("Error: " + e),

                () -> System.out.println("Completed")
        );

        }
}
