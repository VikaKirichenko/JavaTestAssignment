package ru.assigment.kirichenko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static boolean isNumber(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * Получение индекса символа, иду щего после ]
     * @param text исходный текст
     * @param index индекс, с которого начинаем искать конец пожстроки
     * @return индекса символа, иду щего после ]
     */
    public static Integer getIndex(String text, int index, boolean isSubstr){
        // знаем, что одна скобка есть
        int l = 0;
        int i = 0;
        for (i = index; i < text.length(); i++) {
            if (text.charAt(i) == '[') {
                l++;
            } else if (text.charAt(i) == ']') {
                l--;
            }
            if (isSubstr){
                if (l == 0)
                    break;
            }
        }
        if(!isSubstr){
            return l;
        }
        return i+1;
    }

    /**
     * Получение подстроки, идущей после числа
     * @param text исходная строка
     * @param index индекс, с которого начинается пожсторка
     * @return подстрока, идущая после числа
     */
    public static String readLine(String text, int index){
        StringBuilder line = new StringBuilder();
        int l = 1;
        for (int i = index; i < text.length(); i++) {
            if (text.charAt(i)=='['){
                l++;
            }
            else if(text.charAt(i)==']'){
                l--;
            }
            if(l==0)
                break;
            line.append(text.charAt(i));
        }
        return line.toString();
    }

    /**
     * Получение итоговой строки без чисел
     * @param text исходный текст
     * @return отформатированная строка
     */
    public static String getline(String text){
        //итоговая строка
        StringBuilder resultLine = new StringBuilder();
        //количество повторов строки
        StringBuilder numberCounting = new StringBuilder();
        //индекс, считающий будут ли у нас строки без повторов
        int index = 0;
        for (int i = 0; i < text.length(); i++) {
            String character = Character.toString(text.charAt(i));
            numberCounting.append(character);
            if (isNumber(numberCounting.toString())){
                int number = Integer.parseInt(numberCounting.toString());
                // если след знак не ], значит число не однозначное
                if (text.charAt(i+1)=='['){
                    index = getIndex(text,i+1,true);
                    //полчаем отформатированную подстроку
                    String appended = getline(readLine(text, i + 2));
                    // и добавляем ее в результирующую
                    resultLine.append(appended.repeat(Math.max(0, number)));
                    //обновляем для следующих повторений
                    numberCounting = new StringBuilder();
                    i  = index-1;
                }
            }
            else{
                resultLine.append(numberCounting);
                numberCounting = new StringBuilder();
            }
        }
        return resultLine.toString();
    }

    /**
     * метод, проверяющий корректность строки
     * @param str исходная строка
     * @return валидность
     */
    public static boolean isCorrect(String str){
        //проверка на несоответствие символов
        boolean onlyLatinAlphabet = str.matches("^[a-zA-Z0-9\\[\\]]+$");
        if(!onlyLatinAlphabet){
            return false;
        }
        // строка начинается с символов скобок
        if (str.charAt(0)==']'||str.charAt(0)=='['){
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            //в строке неправильно расставлены скобки
            if (getIndex(str,0,false)!=0){
                return false;
            }
            //число обозначает не число повторений
            if(isNumber(Character.toString(str.charAt(i))) &&
                    (str.charAt(i+1)!='[' && !isNumber(Character.toString(str.charAt(i+1))))){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    String line = reader.readLine();
        String result = "";
	    if(isCorrect(line)){
             result = getline(line);
        }
	    else{
	        result = "Line is not correct";
        }
        System.out.println(result);
        //подчистить код, написать проверку на валидность строки, написать комменты, а что если двузначное число
    }
}
