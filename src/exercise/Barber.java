package exercise;

public class Barber implements Runnable {

    private final WaitingRoom waitingRoom;
    private String name;

    public Barber(WaitingRoom waitingRoom, String name) {
        this.waitingRoom = waitingRoom;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Customer customer = waitingRoom.nextCustomer();

                System.out.println("barber " + this.name + " call and shave customer " + customer);
                customer.callAndShave();
            }

        } catch (InterruptedException e) {
            System.out.println("barber " + this.name + " has finished his job");
        }
    }
}
