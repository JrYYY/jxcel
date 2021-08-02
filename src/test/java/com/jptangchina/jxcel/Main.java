package com.jptangchina.jxcel;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String brackets = scanner.nextLine();
        char[] bracket = brackets.toCharArray();
        int[] bracketPosition = new int[bracket.length];
        int smallPosition = 0, mediumPosition = -1;
        for (int i = 0; i < bracket.length; i++) {
            char a = bracket[i];
            if (a == '(') {
                smallPosition = i;
            } else if (a == '[') {
                mediumPosition = i;
            } else if (a == ')') {
                if (i - 1 == -1 || i - 1 != smallPosition){
                    
                }else if(i - 1 == smallPosition){

                }
            } else if (a == ']') {

            }
        }
    }
}
