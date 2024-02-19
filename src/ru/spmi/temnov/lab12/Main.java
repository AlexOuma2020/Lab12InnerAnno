package ru.spmi.temnov.lab12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    Appliances app = new Appliances();
    Appliances.TV[] tv = new Appliances.TV[5];
    Appliances.Fridge[] fridge = new Appliances.Fridge[5];
    Main(){
        for (int i = 0; i < 5; ++i){
            tv[i] = app.createTV(RandomGenerator.getRandomComp(),RandomGenerator.getRandomScreen());
            fridge[i] = app.createFridge(RandomGenerator.getRandomComp(), RandomGenerator.getRandomShelf());
        }
    }
    private void print(){
        System.out.println("Список телевизоров: ");
        for (Appliances.TV value : tv) value.show();
        System.out.println("\nСписок холодильников: ");
        for (Appliances.Fridge value : fridge) value.show();
        System.out.println();
    }

    public void setTV(Appliances.TV[] tvs){
        tv = tvs;
    }

    public void setFridge(Appliances.Fridge[] fr){
        fridge = fr;
    }
    private boolean find(String inp){
        for (String comp: RandomGenerator.getAll()){
            if (comp.equals(inp))
                return true;
        }
        return false;
    }
    private String inputCompany() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
    public String menu() {//без понятия как сделать тест
        String needed = null;
        boolean excep;
        do {
            excep = true;
            System.out.print("Введите название фирмы, количество товаров которой хотите узнать {LG, Haier, Sharp, Samsung, Bosch, Siemens, Hitachi}: ");
            try {
                needed = inputCompany();
            } catch (IOException e) {
                excep = false;
                System.out.println("Неверный формат ввода!");
            }
            if (!find(needed)) {
                System.out.println("Несуществующая фирма " + needed);
                excep = false;
            }
        } while (!excep);
        return needed;
    }

    private int count(String needed){//расчет ВП
        int num = 0;
        for (int i = 0; i < tv.length; ++i){
            if (tv[i].getFirm().equals(needed))
                ++num;
            if (fridge[i].getFirm().equals(needed))
                ++num;
        }
        return num;
    }

    public Appliances.TV[] getTV(){
        return tv;
    }

    public Appliances.Fridge[] getFridge(){
        return fridge;
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.print();
        System.out.printf("Товаров заданной фирмы = %d", m.count(m.menu()));
    }
}
