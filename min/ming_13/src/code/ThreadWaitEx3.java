package code;

import java.util.ArrayList;

public class ThreadWaitEx3 {
    public static void main(String[] args)throws Exception {
        Table__ table = new Table__();

        new Thread(new Cook__(table),"COOK1").start();
        new Thread(new Customer__(table, "donut"),"CUST1").start();
        new Thread(new Customer__(table, "burger"),"CUST2").start();

        Thread.sleep(5000);
        System.exit(0);
    }
}

class Customer__ implements Runnable{
    private Table__ table;
    private String food;

    Customer__(Table__ table, String food){
        this.table = table;
        this.food = food;
    }
    public void run(){
        while (true){
            try { Thread.sleep(10);} catch (InterruptedException e){}
            String name = Thread.currentThread().getName();

            table.remove(food);
            System.out.println(name +" ate a "+food);
        }
    }
}

class Cook__ implements Runnable{
    private Table__ table;

    Cook__(Table__ table) { this.table = table; }

    public void run(){
        while (true){
            int idx = (int)(Math.random() * table.dishNum());
            table.add(table.dishNames[idx]);

            try { Thread.sleep(1); }catch (InterruptedException e){}
        }
    }
}

class Table__{
    String [] dishNames = { "donut","donut","burger" };
    final int MAX_FOOD = 6;

    private ArrayList<String> dishes = new ArrayList<>();

    public synchronized void add(String dish){
        while (dishes.size() >= MAX_FOOD){
            String name = Thread.currentThread().getName();
            System.out.println(name + " is waiting.");
            try {
                wait();
                Thread.sleep(500);
            }catch (InterruptedException e) {}
        }
        dishes.add(dish);
        notify();
        System.out.println("Dishes : "+dishes.toString());
    }
    public void remove(String dishName){
        synchronized (this){
            String name = Thread.currentThread().getName();
            while (dishes.size() ==0){
                System.out.println(name + " is waiting.");
                try {
                    wait();
                    Thread.sleep(500);
                }catch (InterruptedException e) {}
            }
            while(true){
                for(int i=0; i<dishes.size(); i++){
                    if(dishName.equals(dishes.get(i))){
                        dishes.remove(i);
                        notify();
                        return ;
                    }
                }
                try {
                    System.out.println(name + " is waiting.");
                    wait();
                    Thread.sleep(500);
                }catch (InterruptedException e){}
            }
        }
    }
    public int dishNum()    { return dishNames.length; }
}