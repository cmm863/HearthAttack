/*
 *
 *
 *
 *
 */

package com.hearthattack;

//add imports

import com.protos.PlayerModelProto;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;
import java.io.*;


public class Reader implements Runnable {
  private PlayerModelProto playerModel;
  private PlayerModelProto opponentModel;
  private AtomicBoolean update;
  private AtomicBoolean terminate;
  private Lock write;

  public Reader(PlayerModelProto sharedPlayer, PlayerModelProto sharedOpponent, AtomicBoolean sharedFlag,
				AtomicBoolean sharedTerm, ReentrantReadWriteLock rwLock) {
    playerModel = sharedPlayer;
	opponentModel = sharedOpponent;
    update = sharedFlag;
    terminate = sharedTerm;
    write = rwLock.writeLock();
  }

  public void run() {
	try {
	  FileInputStream istream = new FileInputStream("Parser.log");
	  while(!terminate.get()){
		while(!istream.available()); //wait for new data in stream
		  playerModel = PlayerModelProto.parseFrom(istream);
		  opponentModel = PlayerModelProto.parseFrom(istream);
		  write.lock();
			update.set(true);
			write.unlock();
	  }
	}
	catch (FileNotFoundException e) {
		System.out.println("Unable to open file");
	}
	catch (IOException e) {
		e.printStackTrace();
	}
  }
}