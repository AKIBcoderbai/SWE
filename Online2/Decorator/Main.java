package Decorator;
public class Main{
    public static void main(String[] args) {
        Pizza pizza=new SausageDecorator(new CheeseDecorator(new PlainPizza()));
        System.out.println(pizza.getDescription());
        System.out.println(pizza.cost());
    }
}


// Component interface
interface Pizza{
    String getDescription();
    double cost();

}

//concrete component
class PlainPizza implements Pizza{

    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return new String("Plain pizza");
    }

    @Override
    public double cost() {
        // TODO Auto-generated method stub
       return 9.00;
    }
    
}


abstract class PizzaDecorator  implements Pizza{
    private Pizza wrappee;

    PizzaDecorator(Pizza wrappee){
        this.wrappee=wrappee;
    }
    public String getDescription()
    {
       return wrappee.getDescription();
    }

    public double cost(){
        return wrappee.cost();
    }

}

class CheeseDecorator extends PizzaDecorator{

    CheeseDecorator(Pizza wrappee){
        super(wrappee);
    }

    public String getDescription(){
        return super.getDescription()+", Cheese";
    }

    public double cost(){
        return super.cost()+1.5;
    }

}

class SausageDecorator extends PizzaDecorator{

    SausageDecorator(Pizza wrappee){
        super(wrappee);
    }

    public String getDescription(){
        return super.getDescription()+", Sausage";
    }

    public double cost(){
        return super.cost()+2.5;
    }

}