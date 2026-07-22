abstract class TransportFactory {
    abstract Transport geTransport();
}

class TruckFactory extends TransportFactory{
    public Transport geTransport(){
        return new Truck();
    }
}

class ShipFactory extends TransportFactory{
     public Transport geTransport(){
        return new Ship();
    }
}