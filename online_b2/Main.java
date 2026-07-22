import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        String type=sc.nextLine();

        Notification notification= SimpleNotificationFactory.getNotification(type);
        notification.notifyUser();
        sc.close();
    }
}

/**
 *  Notification
 */
interface  Notification {

    public void notifyUser();
}

class SMS implements Notification{

    @Override
    public void notifyUser() {
        // TODO Auto-generated method stub
        System.out.println("Using SMS to notify");
        //throw new UnsupportedOperationException("Unimplemented method 'notifyUser'");
    }
    
}

class Email implements Notification{

    @Override
    public void notifyUser() {
        // TODO Auto-generated method stub
        System.out.println("Using Email to notify");
        //throw new UnsupportedOperationException("Unimplemented method 'notifyUser'");
    }
    
}

class PushNotification implements Notification{

    @Override
    public void notifyUser() {
        // TODO Auto-generated method stub
        System.out.println("Using push notification to notify");
        //throw new UnsupportedOperationException("Unimplemented method 'notifyUser'");
    }
    
}

class Slackmessage implements Notification{

    @Override
    public void notifyUser() {
        // TODO Auto-generated method stub
        System.out.println("Using slack message to notify user");
        //throw new UnsupportedOperationException("Unimplemented method 'notifyUser'");
    }
    
}


class SimpleNotificationFactory{

    public static Notification getNotification(String type)
    {
        if(type.equalsIgnoreCase("SMS"))
        {
            return new SMS();
        }
        else if(type.equalsIgnoreCase("Email"))
        {
            return new Email();
        }
        else if(type.equalsIgnoreCase("Push"))
        {
            return new PushNotification();
        }
        else if(type.equalsIgnoreCase("Slackmessage")){
            return new Slackmessage();
        }

        throw new IllegalArgumentException();
    }

}