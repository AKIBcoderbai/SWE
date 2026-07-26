public interface Transport {
    public void deliver();
}

class Ship implements Transport{
    public void deliver(){
        System.out.println("Delivering with a Ship");
    }
}

class Truck implements Transport{
    public void deliver(){
        System.out.println("Delivering with a Truck");
    }
}