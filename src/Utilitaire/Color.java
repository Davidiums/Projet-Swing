package Utilitaire;

import java.util.Random;
import java.util.TreeSet;

public abstract class Color {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static String RANDOM(){
        TreeSet<String> colorArray = new TreeSet<>();
//        colorArray.add(BLACK);
//        colorArray.add(RED);
        colorArray.add(GREEN);
        colorArray.add(YELLOW);
        colorArray.add(BLUE);
        colorArray.add(PURPLE);
        colorArray.add(CYAN);
        Random random = new Random();
        int index = random.nextInt(colorArray.size());

        String[] colors = colorArray.toArray(new String[0]);
        return colors[index];
    }

}
