public class Main {
    public static void main(final String[] args) {
        Director director=new Director();
        Bicycle mountainBike=director.getBicycle(new MountainCommuter());
        mountainBike.show();
        Bicycle commuter=director.getBicycle(new Commuter());
        commuter.show();
    }
}


class Bicycle {
    String frame="";
    String gear="";
    String tire="";
    public String getFrame() {
        return frame;
    }
    public void setFrame(String frame) {
        this.frame = frame;
    }
    public String getGear() {
        return gear;
    }
    public void setGear(String gear) {
        this.gear = gear;
    }
    public String getTire() {
        return tire;
    }
    public void setTire(String tire) {
        this.tire = tire;
        }
    public void show()
    {
        System.out.println("Bike with "+frame+" "+gear+" "+tire);
    }
    }



interface BikeBuilder {
    void buildFrame();
    void buildGear();
    void buildTire();
    Bicycle getBicycle();
}

class Commuter implements BikeBuilder{
    private Bicycle bike=new Bicycle();
    @Override
    public void buildFrame() {
        // TODO Auto-generated method stub
        bike.setFrame("Aluminum Frame");
        //throw new UnsupportedOperationException("Unimplemented method 'buildFrame'");
    }

    @Override
    public void buildGear() {
        bike.setGear("Single gear");
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'buildGear'");
    }

    @Override
    public void buildTire() {
        // TODO Auto-generated method stub
        bike.setTire("Road Tire");
        //throw new UnsupportedOperationException("Unimplemented method 'buildTire'");
    }

    @Override
    public Bicycle getBicycle() {
        // TODO Auto-generated method stub
        return this.bike;
        //throw new UnsupportedOperationException("Unimplemented method 'getBicycle'");
    }

}


class MountainCommuter implements BikeBuilder{
    private Bicycle bike=new Bicycle();

    @Override
    public void buildFrame() {
        // TODO Auto-generated method stub
        bike.setFrame("Carbon fiber Frame");
        //throw new UnsupportedOperationException("Unimplemented method 'buildFrame'");
    }

    @Override
    public void buildGear() {
        bike.setGear("12 gear");
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'buildGear'");
    }

    @Override
    public void buildTire() {
        // TODO Auto-generated method stub
        bike.setTire("off Road grip Tire");
        //throw new UnsupportedOperationException("Unimplemented method 'buildTire'");
    }

    @Override
    public Bicycle getBicycle() {
        // TODO Auto-generated method stub
        return this.bike;
        //throw new UnsupportedOperationException("Unimplemented method 'getBicycle'");
    }
    
}

class Director{
    public Bicycle getBicycle(BikeBuilder bikeBuilder)
    {
        bikeBuilder.buildFrame();
        bikeBuilder.buildGear();
        bikeBuilder.buildTire();
        return bikeBuilder.getBicycle();
    }
}