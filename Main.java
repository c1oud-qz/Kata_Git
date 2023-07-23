package KataTest;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main (String [] args) throws IOException{
        Scanner sc = new Scanner(System.in, "cp866");
        String text = "";
        text += sc.nextLine().replaceAll("\\s", "");
        sc.close();
        if (text.length()<3){
            throw new IOException("Неправильный ввод.");
        }
        System.out.println(calc(text));
    }
    public static String[] separateSymbols(String str_to_sep) throws IOException {
        String[] separated = new String[3];
        String first = "", second = "";
        char op = 48;
        int i = 0;
        String operators = "*+-/";
        boolean isNotOne = false;
        for (int l=0; l<str_to_sep.length(); l++){
            char elem = str_to_sep.charAt(l);
            if ((operators.indexOf(elem)==-1)){
                continue;
            }
            else{
                isNotOne = true;
            }
        }
        if (isNotOne){
            do{
                char temp = str_to_sep.charAt(i);
                first += Character.toString(temp);
                i++;
                if ((str_to_sep.charAt(i)=='*')||(str_to_sep.charAt(i)=='+')||(str_to_sep.charAt(i)=='-')||(str_to_sep.charAt(i)=='/')){
                    separated[0] = first;
                    op = str_to_sep.charAt(i);
                    first = Character.toString(op);
                    separated[1] = first;
                    i++;
                }
            }
            while(op == 48);
            do{
                char temp = str_to_sep.charAt(i);
                second += Character.toString(temp);
                i++;
            }
            while((i<str_to_sep.length())&&(op != 48)&&(str_to_sep.charAt(i)>'/'));
            separated[2] = second;
            
            if (i<str_to_sep.length()){
                throw new IOException("Ввод более двух операндов!");
            }
            return separated;
        }
        else{
            throw new IOException("Неправильный ввод.");
        }
    }
    
    public static String calc(String input) throws IOException{
        String arr[] = separateSymbols(input);
        char oper = arr[1].charAt(0);
        String rim [] = new String[]{"I", "V", "X", "L", "C"};
        String str_match = "IVXLC";
        for (int i=0; i<input.length(); i++){
            char elem = input.charAt(i);
            if (str_match.indexOf(elem)==-1){
                if(Character.isDigit(elem)==false){
                    if ((elem=='*')||(elem=='+')||(elem=='-')||(elem=='/')){
                        continue;
                    }
                    else{
                        throw new IOException("Неправильный ввод.");
                    }
                }
            }
        }

        boolean needRim = false;
        for (int i=0; i<input.length(); i++){
            boolean isRim = false;
            for (int k=0; k<rim.length; k++){
                if ((Character.toString(input.charAt(i))).equals(rim[k])) {
                    isRim = true;
                    needRim = isRim;
                }
            }
        }
        for (int i=0; i<input.length(); i++){
            if ((needRim==true)&&((Character.isDigit(input.charAt(i)))==true)){
                throw new IOException("Несопоставимый ввод! Либо римские, либо арабские цифры в одном выражении.");
            }
            else {
                continue;
            }
        }
        if (needRim == false){
            int a = Integer.valueOf(arr[0]);
            int b = Integer.valueOf(arr[2]);
            int str = doMath(a, oper, b);
            return Integer.toString(str);
        }
        else{
            int conv1 = convertRimToArab(arr[0]);
            int conv2 = convertRimToArab(arr[2]);
            if ((conv1<=conv2)&&((oper == '-')||(oper == '/'))){
                throw new IOException("В римской системе счисления результат не может быть записан в виде отрицательного числа или 0.");
            }
            int conv_result = doMath(conv1, oper, conv2);
            return convertArabToRim(conv_result);
        }
    }
    public static int doMath(int a, char oper, int b) throws IOException{
        if ((a>10)||(b>10)){
            throw new IOException("Вводимые числа не должны быть больше 10!");
        }
        if ((a<1)||(b<1)){
            throw new IOException("Вводимые числа должны быть больше 0!");
        }
        int result = 0;
        switch(oper){
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default: throw new IOException();
        }
        return result;
    }
    public static int value(char n) {
        if (n == 'I')   
            {return 1; }  
        if (n == 'V')   
            {return 5; }  
        if (n == 'X')   
            {return 10; }  
        if (n == 'L')   
            {return 50; }  
        if (n == 'C')   
            {return 100; }
        return -1;  
    }
    public static int convertRimToArab(String s){
        int total = 0;
        for (int i=0; i<s.length(); i++){   
            int s1 = value(s.charAt(i));
            if ((i+1) < s.length()){   
                int s2 = value(s.charAt(i+1)); 
                if (s1 >= s2){
                    total = total + s1;   
                }   
                else{
                    total = total - s1; 
                }
            }
            else{
                total = total + s1;   
            }
        }
        return total;
    }
    public static String convertArabToRim(int number){
        String[] rim = { "I", "V", "X", "L", "C"};
		int count = 0, digit = 0;
		String result = "";
		StringBuilder strRes = new StringBuilder();
		String stringNumber = String.valueOf(number);
		for (int i = stringNumber.length()-1; i >= 0; i--) {
			strRes.delete(0, strRes.length());
            digit = Character.getNumericValue(stringNumber.charAt(i));
			if (1 <= digit && digit < 4) {
				for (int j = 0; j < digit; j++) {
					strRes.append(rim[count]);
				}
				count += 2;
			} else if (4 <= digit && digit < 9) {
				count += 2;
				if (digit == 4) {
					strRes.append(rim[count-2]);
				}
				strRes.append(rim[count-1]);
				for (int j = 0; j < (digit-5); j++) {
					strRes.append(rim[count-2]);
				}
			} else if (digit == 9) {
				count += 2;
				strRes.append(rim[count-2] + rim[count]);
			} else if (digit == 0) {
				count += 2;
			}
			result = strRes.append(result).toString();
		}
		return result;
    }
}