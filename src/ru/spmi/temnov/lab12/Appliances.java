package ru.spmi.temnov.lab12;

public class Appliances {//суперкласс (основной)

public Fridge createFridge(String firm, int shelf_num){//создание холодильника
    Fridge fridge = new Fridge(firm, shelf_num);
    return fridge;
}
public TV createTV(String firm, int screen){//создание телевизора
    TV tv = new TV(firm, screen);
    return tv;
}
class Fridge {//вложенный класс холодильника
    public String getFirm(){
        return firm;
    }
    private String firm;

    private int shelf_num;

    Fridge(String firm, int shelf_num) {
        this.firm = firm;
        this.shelf_num = shelf_num;
    }

    @fridgeChar(color = "белый", volume = 250)
    public void show(){
        try {
            fridgeChar st = Fridge.class.getMethod("show").getAnnotation(fridgeChar.class);
            System.out.printf("Холодильник. Цвет: %s, компания-производитель: %s, число полок: %d, объем = %d л.\n", st.color(), firm, shelf_num, st.volume());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

class TV {//вложенный класс телевизора
    public String getFirm(){
        return firm;
    }
    private String firm;
    private int screen;
    TV(String firm, int screen) {
        this.firm = firm;
        this.screen = screen;
    }

    @tvChar(display = "OLED", resolution = "FullHD")
    public void show(){
        try {
            tvChar st = TV.class.getMethod("show").getAnnotation(tvChar.class);
            System.out.printf("Телевизор. Тип экрана: %s, компания-производитель: %s, диагональ: %d, разрешение = %s.\n", st.display(), firm, screen, st.resolution());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
}
