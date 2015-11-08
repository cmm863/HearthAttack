/*
 *
 *
 *
 *
 */

//package com.hearthattack;

//add imports

import com.hearthsim.model.BoardModel;
//import com.hearthattack.Bool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;
import java.io.*;


public class Reader implements Runnable {
  private AtomicBoolean update;
  private AtomicBoolean terminate;
  private BoardModel newBoard;
  private Lock write;

  public Reader(BoardModel sharedBoard, AtomicBoolean sharedFlag, AtomicBoolean sharedTerm, ReentrantReadWriteLock rwLock) {
    newBoard = sharedBoard;
    update = sharedFlag;
    terminate = sharedTerm;
    write = rwLock.writeLock();
  }

  public void run() {
    BufferedReader istream = new BufferedReader(new InputStreamReader(System.in));//Will need to change this to appropriate stream
    //board_model_pb2.BoardModel protoBoard;
	try {
    while(!terminate.get()){
      while(!istream.ready())
      //protoBoard.parseFrom(System.in);
      write.lock();
        //sharedBoard(protoBoard);
        update.set(true);
        write.unlock();
    }
	}
	catch (IOException e) {
	  e.printStackTrace();
	}
  }
}
