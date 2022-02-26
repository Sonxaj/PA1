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

package PA1;

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

            // read plaintext and pad with x

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
                    keyBuilder.append(c);
                }
            }

            // make everything lowercase
            String key = truncateString(keyBuilder.toString()).toLowerCase(Locale.ROOT);
            String plainText = truncateString(plainBuilder.toString()).toLowerCase(Locale.ROOT);

            // ensure we are within the 512 character buffer
            key = truncateString(key);
            plainText = truncateString(plainText);

            // pad with x's if possible
            key = padString(key);
            plainText = padString(plainText);

            // apply cipher algorithm
            String cipherText = vigenereCipherAlg(plainText, key);

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
            int cipherChar = charToInt(keyTemp[keyPos]) + charToInt(c);

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

    public static String truncateString(String s){
        // if input is longer than 512, truncate down to 512
        return (s.length() > 512) ? s.substring(0, 511) : s;
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
        System.out.println("\n\n");

        System.out.println("Vigenere Key:\n\n");

        // have to print 80 characters per line, so loop
        // variable for tracking current number of characters on the current line
        int count = 1;
        for(int i=0; i<key.length(); i++){

            if(count % 80 == 0){
                System.out.println("\n");
                count = 1;
            } else {
                System.out.println(key.charAt(i));
                count++;
            }
        }

        System.out.println("\n\n\n");

        System.out.println("Plaintext:\n\n");

        count = 1;
        for(int i=0; i<plainText.length(); i++){

            if(count % 80 == 0){
                System.out.println("\n");
                count = 1;
            } else {
                System.out.println(plainText.charAt(i));
                count++;
            }
        }

        System.out.println("\n\n\n");

        System.out.println("Ciphertext:\n\n");

        count = 1;
        for(int i=0; i<cipherText.length(); i++){

            if(count % 80 == 0){
                System.out.println("\n");
                count = 1;
            } else {
                System.out.println(cipherText.charAt(i));
                count++;
            }
        }

        System.out.println("\n");
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
