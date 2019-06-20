package GameLogic;

import GameLogic.*;
import Protocol.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.net.*;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Collections;

// java GameLogic/pictionaryServer words 2 60 5000

/**
* Parent class for pictionary server processes
*/
public class pictionaryServer extends Thread {
    public volatile List<pictionaryPlayer> players;
    public volatile List<Socket> clientConnections;
    public volatile List<ObjectOutputStream> serverOutputStreams;
    public volatile int guessTime;//In seconds
    public volatile String curword; // Current word
    public volatile Boolean started; // Has the game started
    public volatile Boolean done; // All players have guessed the word
    public volatile long roundStartTime; // Time at which this round started
    public volatile String drawer;
    public List<String> words; // wordbank
    public int nRounds; // Number of rounds
    public int port; // Server port
    public ServerSocket serverSocket; // Server socket
   private volatile List synchedList;

    /**
    * @param wbFp word bank filepath
    * @param nRounds number of rounds
    * @param guessTime amount of clock time per sub-round in ms
    * @param port network port for server
    */
    public pictionaryServer(String wbFp, int nRounds, int guessTime, int port) throws FileNotFoundException {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String hostname = ip.getHostName();
            System.out.println("IP address : " + ip);
            System.out.println("Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }

        this.started = false;
        this.players = Collections.synchronizedList(new CopyOnWriteArrayList<pictionaryPlayer>());

        Scanner s = new Scanner(new File(wbFp));
        this.words = new ArrayList<String>();
        while (s.hasNext()) {
            words.add(s.next());
        }
        s.close();
        System.out.println("Starting Server...");
        System.out.print("WordList: ");
        System.out.println(words);


        this.nRounds = nRounds;
        System.out.print("nRounds: ");
        System.out.println(nRounds);
        this.guessTime = guessTime;
        System.out.print("guessTime: ");
        System.out.println(guessTime);

        this.serverOutputStreams = new ArrayList<ObjectOutputStream>();


        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Server Socket: ");
        System.out.println(serverSocket);

        /**
        * Wait for players in a new thread
        */
        System.out.println("Starting Lobby Thread... ");
        pictionaryLobbyThread plt = new pictionaryLobbyThread(this);
        plt.start();


        System.out.println("Press Enter key to exit join mode.");
        try {System.in.read(); plt.interrupt();} catch (Exception e) {}

        System.out.println("Initiating Game Start...");
        System.out.print("Output Terminals:");
        System.out.println(serverOutputStreams);
        System.out.print("Players:");
        System.out.println(players);
        runGame();
    }

    /**
    * Broadcast Datagram to all players
    */
    public synchronized void sendPlayerMsg(pictionaryDatagram dg) {
        try {
            for (ObjectOutputStream serverOutputStream : serverOutputStreams) {
                serverOutputStream.writeObject(dg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * Actually running the game
    */
    public synchronized void runGame() {


        System.out.println("Welcome to Pictionary!");
        this.started = true;
        Scanner sc = new Scanner(System.in);

        Random rand = new Random();

        for (int i = 0; i < nRounds; i++) {
            System.out.println("Round:"+i);
            for (int j = 0; j < this.players.size(); j++) {
                this.drawer = this.players.get(j).name;
                System.out.println(this.players.get(j).name);
                this.curword =  words.get(rand.nextInt(words.size()));
                System.out.println(curword);

                System.out.println("Server Perspective:");
                System.out.println(this.players);

                datagramSTARTROUND msg = new datagramSTARTROUND(guessTime, curword.length(), this.players.get(j).name, this.players);


                sendPlayerMsg(msg);

                this.done = false;
                this.roundStartTime = System.currentTimeMillis() / 1000L;


                try {
                    wait(100);
                    serverOutputStreams.get(j).writeObject(new datagramPLAYERMSG("Server",  "Your word is " + curword));
                }  catch (Exception e) {
                    e.printStackTrace();
                }


                for (int k = 0; k < curword.length(); k++) {
                    if (!this.done) {
                        try {
                            wait(((long)guessTime) * 1000L / curword.length());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.sendPlayerMsg(new datagramHINT(k, curword.substring(k, k + 1)));
                    } else {
                        break;
                    }
                }
                sendPlayerMsg(new datagramPLAYERMSG("Server", "The word was " + curword));
                resetPlayers();
            }
        }
            sendPlayerMsg(new datagramENDGAME(players));
            try{
            for (Socket s : clientConnections) {
                s.close();
            }}
            catch (Exception e) {
                ;
            }
            System.exit(0);
    }

    /**
    * Reset all players
    */
    private synchronized void resetPlayers() {
        for (int i = 0; i < players.size(); i++) {
            pictionaryPlayer playerCpy = players.get(i);
            playerCpy.hasGuessedWord = false;
            players.set(i, playerCpy);
        }
    }
    /**
    * Start a pictionary server from the command line
    */
    public static void main(String[] args) throws FileNotFoundException {
        // String wbFp,int nRounds,int guessTime, int port
        new pictionaryServer(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));

    }

}