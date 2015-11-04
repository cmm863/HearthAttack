/*
 *
 *
 *
 *
 */

package com.hearthattack;

//add imports

import com.hearthsim.model.BoardModel;
import com.hearthattack.Bool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;


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
    board_model_pb2.BoardModel protoBoard;
    while(!terminate.get()){
      while(!istream.ready())
      protoBoard.parseFrom(System.in);
      write.lock();
      try {
        sharedBoard(protoBoard);
      } finally {
        update.set(true);
        write.unlock();
      }
    }
  }
}
