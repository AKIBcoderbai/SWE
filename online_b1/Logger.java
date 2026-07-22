import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class  Logger {
    private static Logger instance=null;
    private Integer secret_code;
    private List<String> transactionLog = new ArrayList<>();

    private Logger()
    {
        System.out.println("Instance Created for the first time");
        Random rand=new Random();
        secret_code=rand.nextInt();
    }

    public static Logger getInstance()
    {
        if(instance==null)
        {
            instance=new Logger();
        }
        return instance;
    }

    public Integer getSecretCode()
    {
        return secret_code;
    }

    public void log(String message)
    {
        transactionLog.add(message);
        System.out.println("[LOG] " + message);
    }

    public void printAllLogs()
    {
        System.out.println("--- Full audit trail (" + transactionLog.size() + " entries) ---");
        for (String entry : transactionLog) {
            System.out.println(entry);
        }
    }

}