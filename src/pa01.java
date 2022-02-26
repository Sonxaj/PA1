/*=============================================================================
| Assignment: pa01 - Encrypting a plaintext file using the Vigenere cipher
|
| Author: Jonas Turner
| Language: Java
|
| To Compile: javac pa01.java
| gcc -o pa01 pa01.c
| g++ -o pa01 pa01.cpp
|
| To Execute: java -> java pa01 kX.txt pX.txt
| or c++ -> ./pa01 kX.txt pX.txt
| or c -> ./pa01 kX.txt pX.txt
| where kX.txt is the keytext file
| and pX.txt is plaintext file
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Spring 2022
| Instructor: McAlpin
| Due Date: Sunday, February 26, 2022
|
+=============================================================================*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class pa01 {

    public static void main(String[] args){
        try {
            // read file from script
            Scanner keyInput = new Scanner(new File(args[0]));
            Scanner plaintextInput = new Scanner(new File(args[1]));

            // debug
            //Scanner keyInput = new Scanner(new File("src/k3.txt"));
            //Scanner plaintextInput = new Scanner(new File("src/p3.txt"));

            StringBuilder keyBuilder = new StringBuilder();
            StringBuilder plainBuilder = new StringBuilder();

            // read key file
            while(keyInput.hasNextLine()){
                keyBuilder.append(keyInput.nextLine());
            }
            keyInput.close();


            // read plaintext file
            while(plaintextInput.hasNextLine()){
                plainBuilder.append(plaintextInput.nextLine());
            }
            plaintextInput.close();

            // get read results
            String keyTextTemp = keyBuilder.toString();
            String plainTextTemp = plainBuilder.toString();

            // clean builders
            keyBuilder.setLength(0);
            plainBuilder.setLength(0);

            // clean actual text from any non-alphabetical characters
            for(char c:keyTextTemp.toCharArray()){
                if(isCharacter(c)){
                    keyBuilder.append(c);
                }
            }
            for(char c:plainTextTemp.toCharArray()){
                if(isCharacter(c)){
                    plainBuilder.append(c);
                }
            }

            // make everything lowercase
            String key = keyBuilder.toString().toLowerCase(Locale.ROOT);
            String plainText = plainBuilder.toString().toLowerCase(Locale.ROOT);

            // pad with x's if possible
            plainText = padString(plainText);

            // apply cipher algorithm
            String cipherText = vigenereCipherAlg(plainText, key);

            // truncate if possible
            plainText = plainText.substring(0, Math.min(plainText.length(), 512));

            // print
            printResult(plainText, key, cipherText);

            // profit

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static String vigenereCipherAlg(String plaintext, String key){

        StringBuilder result = new StringBuilder();

        // convert text and key to arrays
        char[] plaintextTemp = plaintext.toCharArray();
        char[] keyTemp = key.toCharArray();

        // used to track position inside keyword for circular repetition
        int keyPos = 0;

        for (char c:plaintextTemp) {
            // generate ciphertext in that position
            int cipherChar = (charToInt(keyTemp[keyPos]) + charToInt(c)) % 26;

            // append to result
            result.append(intToChar(cipherChar));

            // check if we've reached the end of the key
            // if so, start from the first letter
            if(keyPos == (key.length() - 1))
                keyPos = 0;
            else
                keyPos++;
        }

        return result.toString();
    }

    // conversion stuff
    public static int charToInt(char input){
        return (input - 97);
    }

    public static char intToChar(int input){
        return (char)(input + 97);
    }

    public static boolean isCharacter(char input){
        // check if current character is only alphabetical
        // i.e., the unicode value is between 97 (a) and 122 (z) OR 65 (A) and 90 (Z)
        return (input >= 65 && input <= 90) || (input >= 97 && input <= 122);
    }

    public static String padString(String s){
        // leave if already max length
        if(s.length() == 512) return s;

        StringBuilder sBuilder = new StringBuilder(s);

        // pad with x's until 512 limit
        sBuilder.append("x".repeat(Math.max(0, 512 - sBuilder.length())));

        return sBuilder.toString();
    }

    public static void printResult(String plainText, String key, String cipherText){
        System.out.print("\n\n");

        System.out.print("Vigenere Key:\n\n");

        for (int i = 0, size = key.length(); i < size; i += 80){
            System.out.println(key.substring(i, Math.min(i + 80, size)));
        }

        System.out.print("\n\n");

        System.out.print("Plaintext:\n\n");

        for (int i = 0, size = plainText.length(); i < size; i += 80){
            System.out.println(plainText.substring(i, Math.min(i + 80, size)));
        }

        System.out.print("\n\n");

        System.out.print("Ciphertext:\n\n");

        for (int i = 0, size = cipherText.length(); i < size; i += 80){
            System.out.println(cipherText.substring(i, Math.min(i + 80, size)));
        }
    }
}

/*=============================================================================
| I Jonas Turner (4852668) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/
