/*
 *
 *
 *
 *
 */

package com.hearthattack;

import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.HearthActionBoardPair;
import com.hearthsim.player.playercontroller.HAAI;
import com.hearthsim.util.Bool;
import com.hearthsim.Reader;
import com.hearthsim.OutputHandler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import java.lang.Runnable;
import java.lang.Thread;

public static void main() {
  boolean gameOver = false;
  AtomicBoolean update = new AtomicBoolean(false);
  AtomicBoolean terminate = new AtomicBoolean(false);
  ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
  Lock read = rwLock.readLock();
  BoardModel updateBoard = new BoardModel();
  BoardModel currentBoard;
  List<HearthActionBoardPair> recommendation;

  //Open Reader in new thread
  (new Thread(new Reader(updateBoard, update, terminate, rwLock)).start();

  while(!terminate.get()) {
    while(!update.get())
    read.lock();
    try {
      currentBoard = updateBoard.deepCopy();
    } finally {
      update.set(false);
      read.unlock();
    }
    if(currentBoard.isDead(PlayerSide.CURRENT_PLAYER) || currentBoard.isDead(PlayerSide.WAITING_PLAYER)) {
      terminate.set(true);
      break;
    }
    recommendation = HAAI.playTurn(currentBoard.getTurn(), currentBoard);
    OutputHandler(recommendation);
  }
}
