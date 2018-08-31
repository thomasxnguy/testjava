import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {

    public class A {
        String text;

        public A(String s) {
            ExecutorTest.A.this.text = s;
        }
    }


    public static void main(String[] args) {

        // Test Optional
        ExecutorTest.A test = new ExecutorTest().new A("hello"); //Because A not static
        Optional<ExecutorTest.A> optionalNotNull = Optional.of(test);
        Optional<ExecutorTest.A> optionalMaybeNull = Optional.ofNullable(test);

        //Diff
        ExecutorTest.A optionalOrElse = Optional.ofNullable(test).orElse(test);
        ExecutorTest.A optionalOrElseGet = Optional.ofNullable(test).orElseGet(() -> test);


        // Test executor
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(new Runnable() {
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
        executorService.shutdown();

    }

}
