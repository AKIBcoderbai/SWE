import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Client client1=new Client();
        GameConfig gameConfig1=client1.instantiateGameConfig();
        Client client2=new Client();
        GameConfig gameConfig2=client2.instantiateGameConfig();
        boolean result=gameConfig1==gameConfig2;
        System.out.println("gameConfig1==gameConfig2 :"+(result));
        System.out.print(Objects.equals(gameConfig1, gameConfig2));
    }
}

class GameConfig {
    private static volatile GameConfig instance = null;

    private GameConfig() {
        System.out.println("First time instantiating");
    }

    public static GameConfig getInstance() {
        synchronized (GameConfig.class) {
            if (instance == null) {
                instance = new GameConfig();
            }
        }
        return instance;
    }
}

class Client{
    public GameConfig instantiateGameConfig()
    {
        GameConfig gameConfig=GameConfig.getInstance();
        return gameConfig;
    }
}
