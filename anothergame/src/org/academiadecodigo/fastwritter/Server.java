package org.academiadecodigo.fastwritter;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;

import java.io.DataOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Server {
    public static void main(String[] args) {

        final int PORT = 6969;
         Game game = new Game(PORT);

         game.listen();

    }
}
