package org.academiadecodigo.fastwritter;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable{

    public int score;
    private String name;
    private final Socket clientSocket;
    private Game game;
    private ArrayList<ClientHandler> players;

    public ClientHandler(Socket clientSocket, Game game) {
        this.clientSocket = clientSocket;
        this.game = game;
        this.players = game.players;
    }

    @Override
    public void run() {
    players.add(this);
    game.dispatch(this);

    }
    public Socket getClientSocket(){
        return clientSocket;
    }

    public void setName(String name) {
     this.name = name;
     Thread.currentThread().setName(this.name);

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
}
