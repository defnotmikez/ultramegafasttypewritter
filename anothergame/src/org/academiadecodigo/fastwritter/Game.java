package org.academiadecodigo.fastwritter;

import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringSetInputScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {
    private final int FINAL_SCORE = 5;
    private final int MAX_PLAYERS = 4;
    private final String DEFAULT_NAME = "Player ";
    //private ClientHandler clientHandler;
    private ServerSocket serverSocket;
    private final int port;
    public ArrayList<ClientHandler> players = new ArrayList<>();

    public Game(int port) {
        this.port = port;

    }

    public void start() {
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
                clientHandler.setName(DEFAULT_NAME + connections);
                System.out.println(connections);


                String connectionMessage =  connections + "/" + MAX_PLAYERS + " players connected." ;
                System.out.println(connectionMessage);
                broadCast(connectionMessage);
                System.out.println(connections);

                players.add(clientHandler);

            }
            for (int i = 0; i < players.size(); i++) {
                playerThreads.submit(players.get(i));

            }
        startCountdown();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispatch(ClientHandler player) {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("dispatch");
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(player.getClientSocket().getInputStream()));
            //startCountdown();
            while (true) {

                System.out.println("comparação");

                String word = wordToCompare();

                System.out.println(word);
                broadCast(word);
                //read block devia estar depois do broadcast da word (l 82) (?)
                String read = in.readLine();

                System.out.println(read);



                compare(read, word);

                System.out.println(player.score);

                if (player.score == FINAL_SCORE) {
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String wordToCompare() {
        String[] words = {"word1", "word2", "colonel", "scissors", "quinoa", "address", "intelligence", "weird", "harass", "broadcast", "scarce", "inspire", "temperature", "specific", "suburban", "broccoli", "vacuum", "bourbon", "nauseous", "grateful", "lightning", "deviation", "congress", "wind", "pavement", "monstrous", "reception", "stride", "inhibition", "socialist", "discrimination", "approval", "answer", "gregarious", "dominate", "strikebreaker", "nomination", "technology", "conversation", "contraction", "dome", "possibility", "sunshine", "punish", "timetable", "accessible", "unrest", "spirit", "policeman", "utter", "weapon", "shortage", "experience", "audience", "operation", "emphasis", "credibility", "flourish", "majority", "vertical", "pumpkin", "version", "ecstasy", "steward", "healthy", "alcohol", "nightmare", "timetable", "digress", "measure", "marble", "witness", "restaurant", "disappear", "mosquito", "landowner", "landmower", "scenario", "industry", "shiver", "tragedy", "impound", "available", "transition", "demonstration", "paragraph", "prevalence", "joystick", "pornhub", "qualified", "wilderness", "survival", "enfix", "fortune", "mutual", "theory", "pattern", "premature", "temptation", "brainstorm", "empirical", "scramble", "elaborate", "judge", "characteristic", "cemetery", "recovery", "snatch", "sensitivity", "flourish", "electron", "pneumonia"};
        int i = (int) Math.abs(Math.random() * words.length);
        return words[i];
    }

    public synchronized void compare(String read, String word) {
        if (read.equals(word) && players.get(0) == players.get(0)) {
            players.get(0).score++;

        }


    }


    private synchronized void broadCast(String message) {
        for (ClientHandler player : this.players) {
            System.out.println("BC");
            try {
                PrintWriter outPlayer = new PrintWriter(player.getClientSocket().getOutputStream(), true);
                outPlayer.println(message);
                System.out.println(message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    /*public ClientHandler getClientHandler() {
        return clientHandler;
    }*/
    private synchronized void startCountdown() {
        for (ClientHandler player : this.players) {
            System.out.println("BC");
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
        }
    }
    private void endGameScoreboard () {

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
