import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Scanner;

public class Game {
    ArrayList<String> dictionary;
    String level;


    public Game(String level) throws Exception{
        this.level = level;
        dictionary  = new ArrayList<String>();
        generateDict();
    }

    void generateDict() throws Exception{
        String FILE = "/Users/jsnehi/Downloads/sowpods.txt";
        BufferedReader br = new BufferedReader(new FileReader(FILE));
        int len;
        if(level.equals("easy"))len=4;
        else if(level.equals("medium")) len=5;
        else len=6;
        String str;
        while((str=br.readLine())!=null){
            if(isValid(str,len))
                dictionary.add(str);
        }
    }



    boolean isValid(String word,int len){
        if(word.length()==len && areChractersUnique(word))
            return true;
        return false;
    }



    boolean areChractersUnique(String str) {
        int checker = 0;

        for (int i = 0; i < str.length(); ++i) {
            int val = (str.charAt(i)-'a');
            if ((checker & (1 << val)) > 0)
                return false;
            checker |= (1 << val);
        }

        return true;
    }



    void startGame(){
        String secretWord = dictionary.get((int)(Math.random()* dictionary.size()));
        boolean isgameEnd =false;
        while(!isgameEnd){
            Scanner sc = new Scanner(System.in);
            String userGuess = sc.next();
            int commonLetterCoun = getCount(userGuess,secretWord);
            System.out.println(commonLetterCoun);
            if(userGuess.equals(secretWord)){
                System.out.println("You Won");
                isgameEnd=true;
            }
            else{
                System.out.println(dictionary);
                String pcGuess = dictionary.get(0);
                System.out.println(pcGuess);
                int commonLetterCount = sc.nextInt();
                if(commonLetterCount>=0 && commonLetterCount<pcGuess.length()){
                    dictionary.remove(pcGuess);
                    reduceDictionary(pcGuess,commonLetterCount);
                }
                else if(commonLetterCount>=0){
                    dictionary.remove(pcGuess);
                }
                else {
                    System.out.println("Computer Won");
                    System.out.println("Correct Word is " + pcGuess);
                    isgameEnd=true;
                }

            }

        }

    }



    void reduceDictionary(String key,int count){
        ArrayList<String> newDictionary = new ArrayList<>();
      //  System.out.println(dictionary.size());
        for(String word : dictionary)
            if(getCount(key,word) == count)
                newDictionary.add(word);
        dictionary = newDictionary;
    }


    int getCount(String str1,String str2){
        HashSet<Character> set = new HashSet<Character>();
        for(int i=0;i<str1.length();i++)set.add(str1.charAt(i));
        int count =0;
        for(int i=0;i<str2.length();i++)if(set.contains(str2.charAt(i))) count++;
        return  count;
    }


}
