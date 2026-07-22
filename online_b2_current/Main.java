

import java.util.Scanner;


interface Report{
    void open();
    void generate();
}

class PDFViewer implements Report{

    @Override
    public void open() {
        // TODO Auto-generated method stub
        System.out.println("Opening as PDF");
       // throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void generate() {
        // TODO Auto-generated method stub
        System.out.println("Generating PDF report");
        //throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }
    
}

class HTMLViewer implements Report{

    @Override
    public void open() {
        // TODO Auto-generated method stub
        System.out.println("Opening as HTML");
       // throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void generate() {
        // TODO Auto-generated method stub
        System.out.println("Generating HTML report");
        //throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }
    
}

class WordViewer implements Report{

    @Override
    public void open() {
        // TODO Auto-generated method stub
        System.out.println("Opening as Word");
       // throw new UnsupportedOperationException("Unimplemented method 'open'");
    }

    @Override
    public void generate() {
        // TODO Auto-generated method stub
        System.out.println("Generating Word report");
        //throw new UnsupportedOperationException("Unimplemented method 'generate'");
    }
    
}

class MyReportProcessor{
    private Report report;
    private Report getReportProcessor(String type)
    {
        if(type.equalsIgnoreCase("PDF"))
        {
            return new PDFViewer();
        }
        else if(type.equalsIgnoreCase("HTML"))
        {
            return new HTMLViewer();
        }
        else if(type.equalsIgnoreCase("WORD"))
        {
            return new WordViewer();
        }
        else throw new IllegalArgumentException();
    }

    public void process(String type)
    {
        report=getReportProcessor(type);
        report.open();
        report.generate();
        System.out.println("Finished Processing Report");
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String type=sc.nextLine();
        MyReportProcessor r=new MyReportProcessor();
        r.process(type);
    }

}
