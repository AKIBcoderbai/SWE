public class Abstract_Implementation {
    public interface Flight {
        void book();
    }

    public interface Hotel {
        void reserve();
    }

    public interface Activity {
        void schedule();
    }

    // --- Relaxation Family ---
    public class BusinessFlight implements Flight {
        public void book() {
            System.out.println("Booking Business Class Flight...");
        }
    }

    public class FiveStarResort implements Hotel {
        public void reserve() {
            System.out.println("Reserving 5-Star Resort...");
        }
    }

    public class SpaTreatment implements Activity {
        public void schedule() {
            System.out.println("Scheduling Spa Treatment...");
        }
    }

    // --- Adventure Family ---
    public class EconomyFlight implements Flight {
        public void book() {
            System.out.println("Booking Economy Flight...");
        }
    }

    public class MountainCabin implements Hotel {
        public void reserve() {
            System.out.println("Reserving Mountain Cabin...");
        }
    }

    public class HikingTour implements Activity {
        public void schedule() {
            System.out.println("Scheduling Hiking Tour...");
        }
    }

    public interface HolidayFactory {
        Flight createFlight();

        Hotel createHotel();

        Activity createActivity();
    }

    public class RelaxationFactory implements HolidayFactory {
        public Flight createFlight() {
            return new BusinessFlight();
        }

        public Hotel createHotel() {
            return new FiveStarResort();
        }

        public Activity createActivity() {
            return new SpaTreatment();
        }
    }

    public class AdventureFactory implements HolidayFactory {
        public Flight createFlight() {
            return new EconomyFlight();
        }

        public Hotel createHotel() {
            return new MountainCabin();
        }

        public Activity createActivity() {
            return new HikingTour();
        }
    }

    public void example() {
        HolidayFactory luxuryFactory = new RelaxationFactory();

        // The factory hands the client 3 separate, matching objects
        Flight luxuryFlight = luxuryFactory.createFlight();
        Hotel luxuryHotel = luxuryFactory.createHotel();
        Activity luxuryActivity = luxuryFactory.createActivity();

        System.out.println("--- Relaxation Itinerary ---");
        luxuryFlight.book();
        luxuryHotel.reserve();
        luxuryActivity.schedule();

        // 2. Client wants an Adventure Holiday
        HolidayFactory adventureFactory = new AdventureFactory();

        Flight advFlight = adventureFactory.createFlight();
        Hotel advHotel = adventureFactory.createHotel();
        Activity advActivity = adventureFactory.createActivity();

        System.out.println("\n--- Adventure Itinerary ---");
        advFlight.book();
        advHotel.reserve();
        advActivity.schedule();
    }

}
