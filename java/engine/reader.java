//Runs concurrently with Handler class, parses PlayerModelProto.PlayerModel object from FileInputStream

package com.hearthattack;

import com.protos.PlayerModelProto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;
import java.io.*;


public class Reader implements Runnable {
  private PlayerModelProto.PlayerModel playerModel;
  private PlayerModelProto.PlayerModel opponentModel;
  private AtomicBoolean update;
  private AtomicBoolean terminate;
  private Lock write;
  private String path;

  public Reader(PlayerModelProto.PlayerModel sharedPlayer, PlayerModelProto.PlayerModel sharedOpponent, AtomicBoolean sharedFlag,
                AtomicBoolean sharedTerm, ReentrantReadWriteLock rwLock, String filePath) {
    playerModel = sharedPlayer;
    opponentModel = sharedOpponent;
    update = sharedFlag;
    terminate = sharedTerm;
    write = rwLock.writeLock();
    path = filePath;
  }

  public void run() {
    try {
      FileInputStream istream = new FileInputStream(path);
      while(!terminate.get()){
        while(istream.available() > 0); //wait for new data in stream
          playerModel = PlayerModelProto.PlayerModel.parseFrom(istream);
          opponentModel = PlayerModelProto.PlayerModel.parseFrom(istream);
          write.lock();
          update.set(true);
          write.unlock();
      }
      istream.close();
    }
    catch (FileNotFoundException e) {
      System.out.println("Unable to open file");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}
