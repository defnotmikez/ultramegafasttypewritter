package org.academiadecodigo.fastwritter;

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
    private final int MAX_PLAYERS = 2;
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
            int connections=0;
            this.serverSocket = new ServerSocket(this.port);

            ExecutorService playerThreads = Executors.newFixedThreadPool(4);
            while (connections != MAX_PLAYERS) {
                Socket clientSocket = this.serverSocket.accept();

                clientHandler = new ClientHandler(clientSocket,this);
                connections++;
                clientHandler.setName(DEFAULT_NAME + connections);
                System.out.println(connections);


                String connectionMessage = clientHandler.getName() + " : has connected.";
                System.out.println(connectionMessage);
                broadCast(connectionMessage);
                System.out.println(connections);

                players.add(clientHandler);

            }
            for (int i = 0; i < players.size(); i++) {
                playerThreads.submit(players.get(i));

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dispatch(ClientHandler player){
        System.out.println("dispatch");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(player.getClientSocket().getInputStream()));

            while(true){
                System.out.println("comparação");

                String word = wordToCompare();

                System.out.println(word);

                String read = in.readLine();

                System.out.println(read);

                broadCast(word);

                compare(read,word);

                System.out.println(player.score);

                if(player.score == FINAL_SCORE){
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String wordToCompare() {
        String[] words = {"word1","word2"};
        int i = (int) Math.abs(Math.random()* words.length);
        return words[i];
    }

    public synchronized void compare(String read, String word){
            if(read.equals(word) && players.get(0) == players.get(0)){
            players.get(0).score++;

            }


    }



    private synchronized void broadCast(String message) {
        for(ClientHandler player : this.players){
            System.out.println("BC");
            try{
                PrintWriter outPlayer = new PrintWriter(player.getClientSocket().getOutputStream(),true);
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
}
