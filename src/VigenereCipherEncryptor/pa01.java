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

package VigenereCipherEncryptor;

public class pa01 {

    public static void main(String[] args) {

        // read file from script

        // create key

        // read plaintext and pad with x

        // apply cipher algorithm

        // print

        // profit


        char a = 'a';
        char b = 'b';



        int c = (charToInt(a) + charToInt(b)) % 26;

        System.out.println(c);
    }

    public String vigenereCipherAlg(String plaintext, String key){

        StringBuilder result = new StringBuilder();

        char[] plaintextTemp = plaintext.toCharArray();
        char[] keyTemp = key.toCharArray();

        int keyPos = 0;

        for (char c:plaintextTemp) {
            int cipherChar = charToInt(keyTemp[keyPos]) + charToInt(c);
            result.append(intToChar(cipherChar));

            if(keyPos == (key.length() - 1))
                keyPos = 0;
            else
                keyPos++;
        }

        return result.toString();
    }

    public static int charToInt(char input){
        return (input - 97);
    }

    public static char intToChar(int input){
        return (char)(input + 97);
    }

    public boolean isCharacter(char input){
        // check if current character is only alphabet
        // i.e., ASCII is between 97 (a) and 122 (z) OR 65 (A) and 90 (Z)
        return (input >= 65 && input <= 90) || (input >= 97 && input <= 122);
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
