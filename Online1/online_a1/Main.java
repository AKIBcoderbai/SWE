import java.util.Scanner;

class SimpleFactory{
    public static Transport getTransport(String type){
        if(type.equalsIgnoreCase("TRUCK")){
            return new Truck();
        }
        else if(type.equalsIgnoreCase("SHIP")){
            return new Ship();
        }
        return null;
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("Enter Type of Transport:");
        Scanner scanner=new Scanner(System.in);
        String type=scanner.next();
        Transport transport;
        transport=SimpleFactory.getTransport(type);
        transport.deliver();
        scanner.close();
    }
}

// This was Factory method. But The problem looked for simple Factory
// Which is just no abstract Factories just write creation logic in the Transport Factory
