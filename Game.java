import java.util.Random;
import java.util.Scanner;

public class Game {

    private boolean running = false;
    private String keyWord;
    private int mistakes;
    private char[] answer;
    private char lastLetter;

    final private String[][] words = {
            {"OWOCE", "JABŁKO", "GRUSZKA", "ŚLIWKA", "TRUSKAWKA", "JAGODA"},
            {"WARZYWA", "ZIEMNIAK", "BURAK", "MARCHEWKA", "DYNIA"},
            {"ZWIERZĘTA", "PIES", "KOT", "MYSZ", "KRÓLIK", "PAPUGA"},
            {"JEDZENIE", "ROSÓŁ", "PIZZA", "FRYTKI", "LODY", "KANAPKA"},
            {"POJAZDY", "SAMOCHÓD", "ROWER", "ŁÓDKA", "KAJAK", "HULAJNOGA"}
    };

    private final String[] ASCIIhangman = {
            " _______ ",
            " | /  |  ",
            " |/      ",
            " |       ",
            " |       ",
            "/ \\     "
    };
    private final String[] changes = { // row column character(s)
            "26O",
            "35/|",
            "37\\",
            "45/",
            "47\\"
    };


    private void getRandomKeyWord(){
        Random random = new Random();
        int category = random.nextInt(words.length);
        int word = random.nextInt(words[category].length-1)+1;
        this.keyWord = words[category][word];

        // delete later
        System.out.printf("category : %16s\n\n", words[category][0]);
    }

    private void getLetter(){
        do{
            System.out.print("\n-> ");
            this.lastLetter = new Scanner(System.in).nextLine().toUpperCase().charAt(0);
        }while(!(this.lastLetter >= 65 && this.lastLetter <= 90 || this.lastLetter == 260 || this.lastLetter ==  280 || this.lastLetter == 211 || this.lastLetter == 346 || this.lastLetter == 321 || this.lastLetter == 379 || this.lastLetter == 377 || this.lastLetter == 262 || this.lastLetter == 323));
    }

    private void addHangman(){
        int row = changes[this.mistakes-1].charAt(0) - 48;
        int col = changes[this.mistakes-1].charAt(1) - 48;
        String substring = changes[this.mistakes-1].substring(2);
        this.ASCIIhangman[row] = this.ASCIIhangman[row].substring(0, col) + substring + this.ASCIIhangman[row].substring(col+substring.length());
    }

    private void update(){
        boolean appears = false;
        for(int i = 0; i < this.keyWord.length(); i++){
            if(this.lastLetter == keyWord.charAt(i)){
                this.answer[i*2] = this.lastLetter;
                appears = true;
            }
        }
        if(!appears){
            this.mistakes += 1;
            addHangman();
        }
    }

    private void victory(){
        this.running = false;
        for(char c : this.answer){
            if(c == '_'){
                this.running = true;
                break;
            }
        }
    }

    private void end(){
        if(!running){
            printHangman();
            for(char c : this.keyWord.toCharArray())
                System.out.print(c + " ");
        }
        System.out.println();
    }

    private void blankAnswer(){
        this.answer = new char[this.keyWord.length()*2 - 1];
        for(int i = 0; i < this.keyWord.length()*2 - 1; i++){
            this.answer[i] = '_';
            if(i < this.keyWord.length()*2 - 2) {
                i++;
                this.answer[i] = ' ';
            }
        }
    }
    private void printAnswer(){
        System.out.println();
        for(char c : this.answer){
            System.out.print(c);
        }
        System.out.println();
    }
    private void printHangman(){
        for (String s : ASCIIhangman) {
            System.out.println(s);
        }
    }




    public void play(){
        this.running = true;
        getRandomKeyWord();
        blankAnswer();

        while(this.mistakes <= 5 && this.running){
            printHangman();
            printAnswer();
            if(this.mistakes == 5){
                break;
            }
            getLetter();
            update();
            victory();
        }


        this.running = false;
        if(this.mistakes == 5)
            System.out.println("\nYou lose.");
        else
            System.out.println("\nYou won!");
        end();
    }

}
