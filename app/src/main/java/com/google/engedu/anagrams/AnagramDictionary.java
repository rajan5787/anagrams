package com.google.engedu.anagrams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet wordSet;
    private HashMap lettersToWord;
    private ArrayList wordList;
    private String key;
    String line;
    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));

        wordSet = new HashSet();
        wordList = new ArrayList();
        lettersToWord = new HashMap();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            ArrayList all = new ArrayList();
            key = alphabeticalOrder(word);
            if(!lettersToWord.containsKey(key)){
                all.add(word);
                lettersToWord.put(key,all);
            }
            else{
                all= (ArrayList) lettersToWord.get(key);
                all.add(word);
                lettersToWord.put(key, all);
            }
        }
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> resultant;
        ArrayList<String> result = new ArrayList<String>();
        for(char alphabet='a';alphabet<='z';alphabet++) {
            String newWord = word + alphabet;
            String extendedKey = alphabeticalOrder(newWord);
            if(lettersToWord.containsKey(extendedKey) ){
                resultant = new ArrayList();
                resultant = (ArrayList) lettersToWord.get(extendedKey);
                for(int i = 0;i< resultant.size();i++)
                    if(!resultant.get(i).contains(word))
                    result.add(String.valueOf(resultant.get(i)));
            }
        }
        return result;
    }

    public boolean isGoodWord(String word, String base) {
        boolean check = true;
        if (wordSet.contains(word) && !base.contains(word))
            return true;
        else
            return false;
    }
    public String pickGoodStarterWord() {
        int num;
        String randomWord = null;
        ArrayList res;
        while(true) {
            res = new ArrayList();
            num = random.nextInt(62991)+1;
            randomWord = wordList.get(num).toString();
            res = (ArrayList) lettersToWord.get(alphabeticalOrder(randomWord));
            if(randomWord.length() >= DEFAULT_WORD_LENGTH && randomWord.length() <= MAX_WORD_LENGTH&&res.size()>=MIN_NUM_ANAGRAMS){
                break;
            }
        }
        return randomWord;
    }
    public String alphabeticalOrder(String word){
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }
}
