package Composite;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Folder dir=new Folder("Online");
        dir.add(new SimpleFile("Practice1.txt"));
        dir.add(new SimpleFile("Practice2.txt"));
        Folder practice=new Folder("practice_prev_year");
        practice.add(new SimpleFile("22_A"));
        practice.add(new SimpleFile("22_B"));
        dir.add(practice);

        dir.print();
    }
}

abstract class FolderComponent{
    FolderComponent(){
    }
    public abstract void print();
}

class SimpleFile extends FolderComponent{
    private String title;
    SimpleFile(String title) {
        super();
        this.title=title;
    }

    @Override
    public void print() {
        // TODO Auto-generated method stub
        System.out.println(this.title);
        //throw new UnsupportedOperationException("Unimplemented method 'print'");
    }
    
}

class Folder extends FolderComponent{
    protected String title;
    List<FolderComponent> children;
    Folder(String title){
        super();
        this.title=title;
        this.children=new ArrayList<FolderComponent>();
    }

    public void add(FolderComponent component){
        children.addLast(component);
    }

    public void remove(int i){
        if(i<children.size())
        {
            children.remove(i);
        }
    }

    public FolderComponent getChild(int i){
        return i<children.size()? children.get(i):null;
    }

    public void print(){
        System.out.println(title);
        for(var child : children){
            System.out.print("\t");
            child.print();
        }
    }


}