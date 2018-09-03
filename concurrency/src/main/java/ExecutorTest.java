import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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


        //Stream

        List<String> A = Arrays.asList("one","two","three","four");

        List<String> B = A.stream().map(s -> s.concat("test")).collect(Collectors.toList());

        B.stream().forEach(s -> System.out.println(s));

        if (B.stream().filter(s-> "onetest".equals(s)).findAny().isPresent()) {
            System.out.println("its here!");
        }

        List<Integer> list = Arrays.asList(1, 10, 3, 7, 5);
        int a = list.stream()
                .peek(num -> System.out.println("will filter " + num))
                .filter(x -> x > 5)
                .findFirst()
                .get();
        System.out.println(a);

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
