package a1_current;
public class Main {
    public static void main(String[] args) {
        ThemeFactory themeFactory=new HybridTheme();
        Button ligthButton=themeFactory.createButton();
        TextField lighTextField=themeFactory.createTextField();
        Dialog lightDialog=themeFactory.createDialog();
        ligthButton.press();
        lighTextField.enter();
        lightDialog.show();
    }
}

interface Button{
    public void press();
}

interface TextField{
    public void enter();

}

interface Dialog {
    public void show();
}

/**
 *  
 */
 interface  ThemeFactory  {
    public Button createButton();
    public TextField createTextField();
    public Dialog createDialog();
}


class LightTheme implements ThemeFactory{
    public Button createButton(){
        return new LightButton();
    }
    public TextField createTextField (){
        return new LightText();
    }

    public Dialog createDialog(){
        return new LightDialog();
    }
}

class HybridTheme implements ThemeFactory{

    @Override
    public Button createButton() {
        // TODO Auto-generated method stub
        return new HybridButton();
        //throw new UnsupportedOperationException("Unimplemented method 'createButton'");
    }

    @Override
    public TextField createTextField() {
        // TODO Auto-generated method stub
        return new HybridText();
        //throw new UnsupportedOperationException("Unimplemented method 'createTextField'");
    }

    @Override
    public Dialog createDialog() {
        // TODO Auto-generated method stub
        return new HybridDialog();
        //throw new UnsupportedOperationException("Unimplemented method 'createDialog'");
    }

}

class DarkTheme implements ThemeFactory{
    public Button createButton(){
        return new DarkButton();
    }
    public TextField createTextField (){
        return new DarkText();
    }

    public Dialog createDialog(){
        return new DarkDialog();
    }
}



class LightButton implements Button{
    public void press(){
        System.out.println("Press Light Button");
    }

}

/**
 
 */
 class LightText implements TextField {
   public void enter(){
    System.out.println("Enter in Light Theme");
   } 
}

 class LightDialog implements Dialog{
    public void show(){
        System.out.println("Show Light dialog");
    }
 }



class DarkButton implements Button{
    public void press(){
        System.out.println("Press Dark Button");
    }

}

 class DarkText implements TextField {
   public void enter(){
    System.out.println("Enter in Dark Theme");
   } 
}

class DarkDialog implements Dialog{
    public void show(){
        System.out.println("Show Dark dialog");
    }
 }

 class HybridButton implements Button{
    public void press(){
        System.out.println("Press Light and Dark mixed theme beautiful Button");
    }

}

 class HybridText implements TextField {
   public void enter(){
    System.out.println("Enter in Light and Dark mixed theme beautiful Theme");
   } 
}

class HybridDialog implements Dialog{
    public void show(){
        System.out.println("Show Light and Dark mixed theme beautiful dialog");
    }
 }