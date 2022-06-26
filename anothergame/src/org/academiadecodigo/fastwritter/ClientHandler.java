package org.academiadecodigo.fastwritter;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler implements Runnable {

    private final PrintStream outGame;
    public int score;
    public PrintWriter outPlayer;
    private String name;
    private final Socket clientSocket;
    private Game game;
    private ArrayList<ClientHandler> players;
    private ArrayList<ClientHandler> winners;
    BufferedReader in;
    String[] wordToPlay;
    Prompt prompt;


    public ClientHandler(Socket clientSocket, Game game) throws IOException {
        this.clientSocket = clientSocket;
        this.game = game;
        this.players = game.players;
        this.winners = game.winners;
        this.outPlayer = new PrintWriter(this.getClientSocket().getOutputStream(), true);
        this.outGame = new PrintStream(this.getClientSocket().getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.getClientSocket().getInputStream()));
        this.wordToPlay = game.wordToPlay;
    }

    @Override
    public void run() {

        try {
            this.prompt = new Prompt(this.clientSocket.getInputStream(), outGame);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //            setName();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
        System.out.println("start");
        roundStart();
        game.broadCast(winners.get(0) + "has won the game!");


    }


    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setName() throws IOException, InterruptedException {
        if (!(game.getUserReadyCounter() == game.MAX_PLAYERS)) {
            game.sendMessage(this, "Choose a name fag");

            this.name = in.readLine();

            Thread.currentThread().setName(this.name);

            System.out.println(game.getUserReadyCounter());

            game.setUserReadyCounter((game.getUserReadyCounter() + 1));

            System.out.println((game.getUserReadyCounter()));

            if ((game.getUserReadyCounter()) < game.MAX_PLAYERS) {
                System.out.println("wait");
                wait();
            }
            if ((game.getUserReadyCounter()) == game.MAX_PLAYERS) {
                System.out.println("notify");
                notifyAll();
                System.out.println("notify");
            }


        }
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    /*public void start(ClientHandler player) {
        try {
            while (true) {

                String[] word = game.wordToCompare();
                for (int i = 0; i <word.length ; i++) {

                }
                System.out.println(word);
                game.sendMessage(player, word);
                //read block devia estar depois do broadcast da word (l 82) (?)
                String read = in.readLine();

                System.out.println(read);

                System.out.println(player.score);


            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

//    public synchronized void compare() throws IOException {
//
//        System.out.println("compare");
//        game.sendMessage(this, serverWord);
//        String playerWord = in.readLine();
//        while (!playerWord.equals(serverWord)) {
//            game.sendMessage(this, "fucking idiot, its wrong");
//            game.sendMessage(this, "\n\n");
//            game.sendMessage(this, serverWord);
//            playerWord = in.readLine();
//        }
//        System.out.println(this.score);
//        this.score++;
//        System.out.println(this.score);
//    }

    public void roundStart() {
//        StringInputScanner word1 = new StringInputScanner();
//        word1.setMessage(wordToPlay[0]+ "\n");
//
//        StringInputScanner word2 = new StringInputScanner();
//        word2.setMessage(wordToPlay[1]+ "\n");
//
//        StringInputScanner word3 = new StringInputScanner();
//        word3.setMessage(wordToPlay[2]+ "\n");
//
//        StringInputScanner word4 = new StringInputScanner();
//        word4.setMessage(wordToPlay[3]+ "\n");
//
//        StringInputScanner word5 = new StringInputScanner();
//        word5.setMessage(wordToPlay[4]+ "\n");
//
//        StringInputScanner word6 = new StringInputScanner();
//        word6.setMessage(wordToPlay[5]+ "\n");
//
//        StringInputScanner word7 = new StringInputScanner();
//        word7.setMessage(wordToPlay[6]+ "\n");
//
//        StringInputScanner word8 = new StringInputScanner();
//        word8.setMessage(wordToPlay[7]+ "\n");
//
//        StringInputScanner word9 = new StringInputScanner();
//        word9.setMessage(wordToPlay[8]+ "\n");
//
//        StringInputScanner word10 = new StringInputScanner();
//        word10.setMessage(wordToPlay[9]+ "\n");
//
//        StringInputScanner word11 = new StringInputScanner();
//        word11.setMessage(wordToPlay[10]+ "\n");
//
//        StringInputScanner word12 = new StringInputScanner();
//        word12.setMessage(wordToPlay[11]+ "\n");
//
//        StringInputScanner word13 = new StringInputScanner();
//        word13.setMessage(wordToPlay[12]+ "\n");
//
//        StringInputScanner word14 = new StringInputScanner();
//        word14.setMessage(wordToPlay[13]+ "\n");
//
//        StringInputScanner word15 = new StringSetInputScanner();
//        word15.setMessage(wordToPlay[14]+ "\n");
//
//
//        String answer1 = prompt.getUserInput(word1);
//        System.out.println("answer:" + answer1);
//            while (wordToPlay[0] != answer1) {
//
//
//
//        }
        Set<String> current0 = new HashSet<>();

        current0.add(wordToPlay[0]);
        current0.add(wordToPlay[1]);
        current0.add(wordToPlay[2]);
        current0.add(wordToPlay[3]);
        current0.add(wordToPlay[4]);
        current0.add(wordToPlay[5]);
        current0.add(wordToPlay[6]);
        current0.add(wordToPlay[7]);
        current0.add(wordToPlay[8]);
        current0.add(wordToPlay[9]);
        current0.add(wordToPlay[10]);
        current0.add(wordToPlay[11]);
        current0.add(wordToPlay[12]);
        current0.add(wordToPlay[13]);
        current0.add(wordToPlay[14]);
        StringInputScanner round1Word = new StringSetInputScanner(current0);
        round1Word.setError("burr de merda");

        round1Word.setMessage("Type it fast: " + wordToPlay[0] + "\n");
        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[0]);

        round1Word.setError("merda de burr");
        round1Word.setMessage("Tyoe it fast: " + wordToPlay[1] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[1]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[2] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[2]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[3] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[3]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[4] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[4]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[5] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[5]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[6] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[6]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[7] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[7]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[8] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[8]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[9] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[9]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[10] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[10]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[11] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[11]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[12] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[12]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[13] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[13]);

        round1Word.setMessage("Tyoe it fast: " + wordToPlay[14] + "\n");

        prompt.getUserInput(round1Word);
        current0.remove(wordToPlay[14]);

        game.winners.add(this);
        game.sendMessage(this,"You have finished, wait for others to finish :)");





            /*Set<String> current0 = new HashSet<>();

            current0.add(wordToPlay[0]);
            StringInputScanner round1Word = new StringSetInputScanner(current0);
            round1Word.setMessage("Type it fast: " + wordToPlay[0] + "\n");
            round1Word.setError("burr de merda");
            prompt.getUserInput(round1Word);
            current0.remove(wordToPlay[0]);
        System.out.println(current0);

        Set<String> current1 = new HashSet<>();

            current1.add(wordToPlay[1]);
        System.out.println(current1);
            StringInputScanner round2Word = new StringSetInputScanner(current1);
            round2Word.setMessage(wordToPlay[1] + "\n");
            round2Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current1.remove(wordToPlay[1]);

        Set<String> current2 = new HashSet<>();
            current2.add(wordToPlay[2]);
            StringInputScanner round3Word = new StringSetInputScanner(current2);
            round3Word.setMessage(wordToPlay[2] + "\n");
            round3Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current2.remove(wordToPlay[2]);

        Set<String> current3 = new HashSet<>();
            current3.add(wordToPlay[3]);
            StringInputScanner round4Word = new StringSetInputScanner(current3);
            round4Word.setMessage(wordToPlay[3] + "\n");
            round4Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current3.remove(wordToPlay[3]);

        Set<String> current4 = new HashSet<>();
            current4.add(wordToPlay[4]);
            StringInputScanner round5Word = new StringSetInputScanner(current4);
            round5Word.setMessage(wordToPlay[4] + "\n");
            round5Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current4.remove(wordToPlay[4]);

        Set<String> current5 = new HashSet<>();
            current5.add(wordToPlay[5]);
            StringInputScanner round6Word = new StringSetInputScanner(current5);
            round6Word.setMessage(wordToPlay[5] + "\n");
            round6Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current5.remove(wordToPlay[6]);

        Set<String> current6 = new HashSet<>();
            current6.add(wordToPlay[6]);
            StringInputScanner round7Word = new StringSetInputScanner(current6);
            round7Word.setMessage(wordToPlay[6] + "\n");
            round7Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current6.remove(wordToPlay[6]);

        Set<String> current7 = new HashSet<>();
            current7.add(wordToPlay[7]);
            StringInputScanner round8Word = new StringSetInputScanner(current7);
            round8Word.setMessage(wordToPlay[7] + "\n");
            round8Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current7.remove(wordToPlay[7]);

        Set<String> current8 = new HashSet<>();
            current8.add(wordToPlay[8]);
            StringInputScanner round9Word = new StringSetInputScanner(current8);
            round9Word.setMessage(wordToPlay[8] + "\n");
            round9Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current8.remove(wordToPlay[8]);

        Set<String> current9 = new HashSet<>();
            current9.add(wordToPlay[9]);
            StringInputScanner round10Word = new StringSetInputScanner(current9);
            round10Word.setMessage(wordToPlay[9] + "\n");
            round10Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current9.remove(wordToPlay[9]);

        Set<String> current10 = new HashSet<>();
            current10.add(wordToPlay[10]);
            StringInputScanner round11Word = new StringSetInputScanner(current10);
            round11Word.setMessage(wordToPlay[10] + "\n");
            round11Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current10.remove(wordToPlay[10]);

        Set<String> current11 = new HashSet<>();
            current11.add(wordToPlay[11]);
            StringInputScanner round12Word = new StringSetInputScanner(current11);
            round12Word.setMessage(wordToPlay[11] + "\n");
            round12Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current11.remove(wordToPlay[11]);

        Set<String> current12 = new HashSet<>();
            current12.add(wordToPlay[12]);
            StringInputScanner round13Word = new StringSetInputScanner(current12);
            round13Word.setMessage(wordToPlay[12] + "\n");
            round13Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current12.remove(wordToPlay[12]);

        Set<String> current13 = new HashSet<>();
            current13.add(wordToPlay[13]);
            StringInputScanner round14Word = new StringSetInputScanner(current13);
            round14Word.setMessage(wordToPlay[13] + "\n");
            round14Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current13.remove(wordToPlay[13]);

        Set<String> current14 = new HashSet<>();
            current14.add(wordToPlay[14]);
            StringInputScanner round15Word = new StringSetInputScanner(current14);
            round15Word.setMessage(wordToPlay[14] + "\n");
            round15Word.setError("burr de merda");
            prompt.getUserInput(round2Word);
            current14.remove(wordToPlay[14]);*/



        }

    }

