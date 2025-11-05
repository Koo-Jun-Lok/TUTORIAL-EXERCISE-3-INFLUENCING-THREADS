// Main Class
public class AirTrafficControl {

    public static void main(String[] args) {
        System.out.println("Control Tower: Starting Air Traffic System...\n");

        Runway runway = new Runway();

        // 4 airplane threads
        Airplane mas01 = new Airplane("Flight MAS01 EL-MARIACHI", runway);
        Airplane mas02 = new Airplane("Flight MAS02 EUDORA", runway);
        Airplane mas03 = new Airplane("Flight MAS03 DEJAVU", runway);
        Airplane mas04 = new Airplane("Flight MAS04 UTOPIA", runway);

        // Set priorities
        mas01.setPriority(Thread.MAX_PRIORITY);  // VIP
        mas02.setPriority(Thread.NORM_PRIORITY); // Normal
        mas03.setPriority(Thread.MIN_PRIORITY);  // Low
        mas04.setPriority(Thread.NORM_PRIORITY); // Normal

        // Start flights
        mas01.start();
        mas02.start();
        mas03.start();
        mas04.start();

        mas04.interrupt();

        // Control Tower waits until all finish
        try {
            mas01.join();
            mas02.join();
            mas03.join();
            mas04.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All flights completed. Control Tower closing operations.");

        // Simulate delay
//        mas04.interrupt();
    }
}

// Separate class: Runway
class Runway {

    public synchronized void useRunway(String flightName) {
        System.out.println(flightName + " requesting runway access...");
        System.out.println(flightName + " taking off...");
        try {
            Thread.sleep(2000); // simulate take-off time
        } catch (InterruptedException e) {
            System.out.println(flightName + " interrupted! Delayed due to heavy rain.");
            return;
        }
        System.out.println(flightName + " leaving the runway.\n");
    }
}

// Separate class: Airplane
class Airplane extends Thread {
    private String flightName;
    private Runway runway;

    public Airplane(String name, Runway runway) {
        this.flightName = name;
        this.runway = runway;
    }

    @Override
    public void run() {
        runway.useRunway(flightName);
    }
}
