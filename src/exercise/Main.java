package exercise;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        WaitingRoom waitingRoom = new WaitingRoom(28);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        executorService.submit(new Barber(waitingRoom, "1"));
        executorService.submit(new Barber(waitingRoom, "2"));
        executorService.submit(new Barber(waitingRoom, "3"));
        executorService.submit(new Barber(waitingRoom, "4"));
        executorService.submit(new Barber(waitingRoom, "5"));
        executorService.submit(new Barber(waitingRoom, "6"));

        List<Customer> customers = Stream.generate(() -> new Customer(waitingRoom))
                                         .limit(100)
                                         .peek(executorService::submit)
                                         .collect(toList());

        while (!customers.stream().allMatch(Customer::isShaved)) {
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println("All customers have been shaved");
        executorService.shutdownNow();
        executorService.awaitTermination(1, MINUTES);
    }

}
