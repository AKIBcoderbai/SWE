
class HolidayPackage {
    private String flight;
    private String hotel; // Spec asks for Hotel, not Cabin!
    private String activity;

    public void setFlight(String flight) { this.flight = flight; }
    public void setHotel(String hotel) { this.hotel = hotel; }
    public void setActivity(String activity) { this.activity = activity; }
    
    public void showPackage() {
        System.out.println("Package: " + flight + ", " + hotel + ", " + activity);
    }
}


interface PackageBuilder {
    void buildFlight();
    void buildHotel();
    void buildActivity();
    HolidayPackage getPackage();
}

class RelaxationBuilder implements PackageBuilder {
    private HolidayPackage pkg = new HolidayPackage();

    public void buildFlight() { pkg.setFlight("Business Class Flight"); }
    public void buildHotel() { pkg.setHotel("5-Star Resort"); }
    public void buildActivity() { pkg.setActivity("Spa Treatment"); }
    public HolidayPackage getPackage() { return pkg; }
}

class AdventureBuilder implements PackageBuilder {
    private HolidayPackage pkg = new HolidayPackage();

    public void buildFlight() { pkg.setFlight("Economy Flight"); }
    public void buildHotel() { pkg.setHotel("Mountain Cabin"); }
    public void buildActivity() { pkg.setActivity("Hiking Tour"); }
    public HolidayPackage getPackage() { return pkg; }
}

class TravelAgent {
    public HolidayPackage constructPackage(PackageBuilder builder) {
        builder.buildFlight();
        builder.buildHotel();
        builder.buildActivity();
        return builder.getPackage();
    }
}


public class Main{
    public static void main(String[] args) {
      PackageBuilder pkg=new RelaxationBuilder();
      TravelAgent agent=new TravelAgent();
      HolidayPackage holidayPackage=agent.constructPackage(pkg);
      holidayPackage.showPackage();

      holidayPackage=agent.constructPackage(new AdventureBuilder());
      holidayPackage.showPackage();
        
    }
}
