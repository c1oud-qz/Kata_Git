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
        String rim [] = new String[]{"I", "V", "X", "L", "C"};
        boolean needRim = false;
        for (int i=0; i<text.length(); i++){
            boolean isRim = false;
            for (int k=0; k<rim.length; k++){
                if ((Character.toString(text.charAt(i))).equals(rim[k])) {
                    isRim = true;
                    needRim = isRim;
                }
            }
        }
        for (int i=0; i<text.length(); i++){
            if ((needRim==true)&&((Character.isDigit(text.charAt(i)))==true)){
                throw new IOException("Несопоставимый ввод! Либо римские, либо арабские цифры в одном выражении.");
            }
            else {
                continue;
            }
        }
        
        if (needRim == true){
            String first = "", second = "";
            char op = 48;
            int i = 0;
            do{
                char temp = text.charAt(i);
                first += Character.toString(temp);
                i++;
                if ((text.charAt(i)=='*')||(text.charAt(i)=='+')||(text.charAt(i)=='-')||(text.charAt(i)=='/')){
                    op = text.charAt(i);
                    i++;
                }
            }
            while(op == 48);
            do{
                char temp = text.charAt(i);
                second += Character.toString(temp);
                i++;
            }
            while((i<text.length())&&(op != 48)&&(text.charAt(i)>'/'));
            
            /* new method convertRimToArab
                value
             */
            int conv1 = convertRimToArab(first);
            int conv2 = convertRimToArab(second);
            if ((conv1<=conv2)&&((op == '-')||(op == '/'))){
                throw new IOException("В римской системе счисления результат не может быть записан в виде отрицательного числа или 0.");
            }
            // System.out.println(calc(conv1, op, conv2));
            int conv_result = calc(conv1, op, conv2);
            // new method convertArabToRim(conv_result)
            System.out.println(convertArabToRim(conv_result));
        }

        else{
            int first = 0, second = 0, temp = 0;
            char op = 48;
            for (int i=0; i<text.length(); i++){
                if (Character.isDigit(text.charAt(i)) == true){
                    temp = temp*10 + Character.getNumericValue(text.charAt(i));
                    if (Character.toString(op)!="0"){
                        second = temp;
                    }
                }
                else{
                    if ((text.charAt(i)=='+')||(text.charAt(i)=='-')||(text.charAt(i)=='/')||(text.charAt(i)=='*')){
                        first = temp;
                        temp = 0;
                        op = text.charAt(i);
                    }
                    else {
                        throw new IOException("Неправильный формат вводимого действия.");
                    }
                }
            }
            System.out.println(calc(first, op, second));
        }
    }
    public static int calc(int a, char oper, int b) throws IOException{
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