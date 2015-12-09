//Runs concurrently with Handler class, parses PlayerModelProto.PlayerModel object from FileInputStream

package com.hearthattack;

import com.protos.PlayerModelProto;
import com.protos.BoardModelProto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Reader implements Runnable {
  private PlayerModelProto.PlayerModel playerModel;
  private PlayerModelProto.PlayerModel opponentModel;
  private AtomicBoolean update;
  private AtomicBoolean terminate;
  private Lock write;
  private int portNumber;

  public Reader(PlayerModelProto.PlayerModel sharedPlayer, PlayerModelProto.PlayerModel sharedOpponent, AtomicBoolean sharedFlag,
                AtomicBoolean sharedTerm, ReentrantReadWriteLock rwLock, int port) {
    playerModel = sharedPlayer;
    opponentModel = sharedOpponent;
    update = sharedFlag;
    terminate = sharedTerm;
    write = rwLock.writeLock();
    portNumber = port;
  }

  public void run() {
    try {
      ServerSocket serverSocket = new ServerSocket(portNumber);
      Socket clientSocket = serverSocket.accept();
      DataInputStream in = new DataInputStream(clientSocket.getInputStream());
      int length;
      byte[] message;
      while(!terminate.get()){
        System.out.println("Waiting for message");
        length = in.readInt();
        while(length == 0)
          length = in.readInt();
        message = new byte[length];
        in.readFully(message, 0, message.length);
        System.out.println("Received a message");
        BoardModelProto.BoardModel boardModel = BoardModelProto.BoardModel.parseFrom(message);
        playerModel = boardModel.getPlayer();
        opponentModel = boardModel.getOpponent();
        System.out.println(playerModel);
        write.lock();
        update.set(true);
        write.unlock();
        System.out.println("Translated message");
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}