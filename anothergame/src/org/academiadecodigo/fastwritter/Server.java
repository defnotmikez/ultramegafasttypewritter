package org.academiadecodigo.fastwritter;

public class Server {
    public static void main(String[] args) {
         final int PORT = 6969;
         Game game = new Game(PORT);

         game.start();


    }
}
