package ru.spmi.temnov.lab12;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MainTestInner {
    private void provideInput(String data){
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void getTVTest(){
        Appliances.TV tv= new Appliances().createTV("LG", 27);
        assertEquals("LG", tv.getFirm());
        System.out.println("Геттер TV работает корректно\n");
    }
    @Test
    void getFridgeTest(){
        Appliances.Fridge fr= new Appliances().createFridge("LG", 7);
        assertEquals("LG",fr.getFirm());
        System.out.println("Геттер Fridge работает корректно\n");
    }

    @Test
    public void showTest(){//проверка корректности метода вывода информации
        final PrintStream standardOut = System.out;
        final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Appliances.TV tv= new Appliances().createTV("LG", 27);
        Appliances.Fridge fr= new Appliances().createFridge("Samsung", 7);
        tv.show();
        assertEquals("Телевизор. Тип экрана: OLED, компания-производитель: LG, диагональ: 27, разрешение = FullHD.\n",  outputStreamCaptor.toString());
        System.setOut(standardOut);
        System.out.println("Вывод информации о телевизоре корректен " + outputStreamCaptor.toString());
        System.setOut(new PrintStream(outputStreamCaptor));
        fr.show();
        assertEquals("Телевизор. Тип экрана: OLED, компания-производитель: LG, диагональ: 27, разрешение = FullHD.\nХолодильник. Цвет: белый, компания-производитель: Samsung, число полок: 7, объем = 250 л.\n",  outputStreamCaptor.toString());
        System.setOut(standardOut);
        System.out.println("Вывод информации о холдильнике корректен " + outputStreamCaptor.toString());
    }
    @Test
    void randGetRandomCompanyTest(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertTrue((Boolean)method.invoke(m, RandomGenerator.getRandomComp()));
            System.out.println("Случайное значение массива company работает корректно\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void companyGetAllTest(){
        assertArrayEquals(new String[]{"LG", "Haier", "Sharp", "Samsung", "Bosch", "Siemens", "Hitachi"}, RandomGenerator.getAll());
        System.out.println("Получение массива фирм работает корректно\n");
    }

    @Test
    public void testFound1(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(true, method.invoke(m, "LG"));
            System.out.println("Товар фирмы LG существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFound2(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(false, method.invoke(m, "Qwerty"));
            System.out.println("Товара фирмы lG не существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFound3(){
        Main m = new Main();
        try {
            Method method = Main.class.getDeclaredMethod("find", String.class);
            method.setAccessible(true);
            assertEquals(false, method.invoke(m, ""));
            System.out.println("Товара фирмы без названия не существует\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void inputTest1(){
        Main m = new Main();
        provideInput("Haier");
        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);
            String output = (String) method.invoke(m);
            assertEquals("Haier", output);
            System.out.println("Ввод Haier подерживается\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void inputTest2(){
        Main m = new Main();
        provideInput("");
        try {
            Method method = Main.class.getDeclaredMethod("inputCompany", null);
            method.setAccessible(true);
            String output = (String) method.invoke(m);
            assertNull(output);
            System.out.println("Ввод пустой строки подерживается\n");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    private HashMap<String, Integer> counter(Main m){
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String st: RandomGenerator.getAll()){
            Integer num = 0;
            for (Appliances.TV app: m.getTV())
                if (app.getFirm().equals(st))
                    ++num;
            for (Appliances.Fridge app: m.getFridge())
                if (app.getFirm().equals(st))
                    ++num;
            hashMap.put(st, num);
        }
        return hashMap;
    }

    @Test
    public void countTestNotRandom() throws RuntimeException {//расчет показателей с заданными значениями
        System.out.println("Заданные значения!\n");
        Main m = new Main();
        try {
            Appliances app = new Appliances();
            Appliances.TV[] tv = new Appliances.TV[]{
                app.createTV("Haier",RandomGenerator.getRandomScreen()),
                app.createTV("LG",RandomGenerator.getRandomScreen()),
                app.createTV("Sharp",RandomGenerator.getRandomScreen()),
                app.createTV("Samsung",RandomGenerator.getRandomScreen()),
                app.createTV("LG",RandomGenerator.getRandomScreen())
            };
            m.setTV(tv);

            Appliances.Fridge[] fridge = new Appliances.Fridge[]{
                    app.createFridge("Samsung",RandomGenerator.getRandomShelf()),
                    app.createFridge("Samsung",RandomGenerator.getRandomShelf()),
                    app.createFridge("LG",RandomGenerator.getRandomShelf()),
                    app.createFridge("LG",RandomGenerator.getRandomShelf()),
                    app.createFridge("Sharp",RandomGenerator.getRandomShelf())
            };
            m.setFridge(fridge);

            Method method = Main.class.getDeclaredMethod("print");
            method.setAccessible(true);
            method.invoke(m, null);

            method = Main.class.getDeclaredMethod("count", String.class);
            method.setAccessible(true);

            int n = (int)method.invoke(m, "LG");
            assertEquals(4, n);
            System.out.printf("Количество товаров фирмы LG равно %d\n", n);

            n = (int)method.invoke(m, "Siemens");
            assertEquals(0, n);
            System.out.printf("Количество товаров фирмы Siemens равно %d\n", n);

            n = (int)method.invoke(m, "Sharp");
            assertEquals(2, n);
            System.out.printf("Количество товаров фирмы Sharp равно %d\n", n);

            n = (int)method.invoke(m, "Haier");
            assertEquals(1, n);
            System.out.printf("Количество товаров фирмы Haier равно %d\n", n);

            n = (int)method.invoke(m, "Hitachi");
            assertEquals(0, n);
            System.out.printf("Количество товаров фирмы Hitachi равно %d\n", n);

            n = (int)method.invoke(m, "Samsung");
            assertEquals(3, n);
            System.out.printf("Количество товаров фирмы Samsung равно %d\n", n);

            n = (int)method.invoke(m, "Bosch");
            assertEquals(0, n);
            System.out.printf("Количество товаров фирмы Bosch равно %d\n\n", n);
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void countTest() throws RuntimeException {//расчет показателей со случайными значениями
        System.out.println("Случайные значения!\n");
        Main m = new Main();
        try {
            HashMap<String, Integer> num= counter(m);
            Method method = Main.class.getDeclaredMethod("print");
            method.setAccessible(true);
            method.invoke(m, null);

            method = Main.class.getDeclaredMethod("count", String.class);
            method.setAccessible(true);

            for (String st: RandomGenerator.getAll()){
                assertEquals(num.get(st), method.invoke(m, st));
                System.out.printf("Количество товаров фирмы %s равно %d\n", st, num.get(st));
            }
            System.out.println();
        } catch (NoSuchMethodException e) {
            System.out.println("Нет такого метода! " + e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
