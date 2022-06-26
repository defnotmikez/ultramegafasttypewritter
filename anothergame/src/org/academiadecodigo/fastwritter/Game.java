package org.academiadecodigo.fastwritter;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    public final int FINAL_SCORE = 15;
    public final int MAX_PLAYERS = 2;
    private final String DEFAULT_NAME = "Player ";
    public ArrayList<ClientHandler> winners;
    //private ClientHandler clientHandler;
    private ServerSocket serverSocket;
    private final int port;
    public ArrayList<ClientHandler> players = new ArrayList<>();
    private int userReadyCounter;
    LinkedList<String> wordOcean = new LinkedList<>(Arrays.asList("colonel", "scissors", "quinoa", "address", "intelligence", "weird", "harass", "broadcast", "scarce", "inspire", "temperature", "specific", "suburban", "broccoli", "vacuum", "bourbon", "nauseous", "grateful", "lightning", "deviation", "congress", "wind", "pavement", "monstrous", "reception", "stride", "inhibition", "socialist", "discrimination", "approval", "answer", "gregarious", "dominate", "strikebreaker", "nomination", "technology", "conversation", "contraction", "dome", "possibility", "sunshine", "punish", "timetable", "accessible", "unrest", "spirit", "policeman", "utter", "weapon", "shortage", "experience", "audience", "operation", "emphasis", "credibility", "flourish", "majority", "vertical", "pumpkin", "version", "ecstasy", "steward", "healthy", "alcohol", "nightmare", "timetable", "digress", "measure", "marble", "witness", "restaurant", "disappear", "mosquito", "landowner", "landmower", "scenario", "industry", "shiver", "tragedy", "impound", "available", "transition", "demonstration", "paragraph", "prevalence", "joystick", "pornhub", "qualified", "wilderness", "survival", "enfix", "fortune", "mutual", "theory", "pattern", "premature", "temptation", "brainstorm", "empirical", "scramble", "elaborate", "judge", "characteristic", "cemetery", "recovery", "snatch", "sensitivity", "flourish", "electron", "pneumonia"));
    public String[] wordToPlay;

    public int getUserReadyCounter() {
        return userReadyCounter;
    }

    public void setUserReadyCounter(int userReadyCounter) {
        this.userReadyCounter = userReadyCounter;
    }

    public Game(int port) {
        this.port = port;
        this.wordToPlay = wordToCompare();

    }

    public void listen() {
        ClientHandler clientHandler;
        System.out.println("start");
        try {
            int connections = 0;
            this.serverSocket = new ServerSocket(this.port);

            ExecutorService playerThreads = Executors.newFixedThreadPool(MAX_PLAYERS);
            while (connections != MAX_PLAYERS) {
                Socket clientSocket = this.serverSocket.accept();

                clientHandler = new ClientHandler(clientSocket, this);
                connections++;


                String connectionMessage = connections + "/" + MAX_PLAYERS + " players connected.";
                System.out.println(connectionMessage);
                broadCast(connectionMessage);
                players.add(clientHandler);

            }
            startCountdown();
            for (int i = 0; i < players.size(); i++) {
                playerThreads.submit(players.get(i));
                System.out.println("test");
            }


        } catch (IOException e) {
            throw new RuntimeException(e);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    String[] wordToCompare() {
        LinkedList<String> wordToPlay = new LinkedList<>();
        for (int j = 0; j < FINAL_SCORE; j++) {
            String word = generateWord();

            while(wordToPlay.contains(word) && !wordToPlay.isEmpty()){
                word = generateWord();
            }
            wordToPlay.add(word);
        }
        for (String s : wordToPlay) {
            System.out.println(s);
        }
        return wordToPlay.toArray(new String[0]);
    }

    private String generateWord() {
        int rand = (int) Math.abs(Math.random() * wordOcean.size());
        return wordOcean.get(rand);
    }


    public synchronized void broadCast(String message) {
        for (ClientHandler player : this.players) {

            try {
                player.outPlayer.println(message);
                System.out.println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    synchronized void sendMessage(ClientHandler player, String message) {
        for (ClientHandler player1 : this.players) {

            if (player == player1) {
                try {
                    player.outPlayer.println(message);
                    System.out.println(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /*public ClientHandler getClientHandler() {
        return clientHandler;
    }*/
    synchronized void startCountdown() throws InterruptedException {

        broadCast("Game will start in...");
        this.wait(1000);
        broadCast("5");
        this.wait(1000);
        broadCast("4");
        this.wait(1000);
        broadCast("3");
        this.wait(1000);
        broadCast("2");
        this.wait(1000);
        broadCast("1");
        this.wait(1000);




  /*      for (ClientHandler player : this.players) {

            try {
                PrintWriter outPlayer = new PrintWriter(player.getClientSocket().getOutputStream(), true);
                outPlayer.println("Game will start in...");
                System.out.println("Game will start in...");
                this.wait(1000);
                outPlayer.println("5");

                this.wait(1000);
                outPlayer.println("4");

                this.wait(1000);
                outPlayer.println("3");

                this.wait(1000);
                outPlayer.println("2");

                this.wait(1000);
                outPlayer.println("1");


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }*/
    }

    private void endGameScoreboard() {

        for (ClientHandler player : this.players) {
            String[] finalScores = {String.valueOf(player.score)};

            MenuInputScanner scanner = new MenuInputScanner(finalScores);
            //devo tar a fazer 4x player1 score
            //devo precisar de outra iteração pelos scores

            scanner.setMessage("Final score was: " + "\n" +
                    player.getName() + " :" + player.score + "\n" +
                    player.getName() + " :" + player.score + "\n" +
                    player.getName() + " :" + player.score + "\n" +
                    player.getName() + " :" + player.score + "\n"

            );


        }
    }
}
