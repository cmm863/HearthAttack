/*
 *
 *
 *
 *
 */

package com.hearthattack;

import com.protos.PlayerModelProto;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.heroes.TestHero;
//import com.hearthsim.model.PlayerSide;
//import com.hearthsim.util.HearthActionBoardPair;
//import com.hearthsim.player.playercontroller.HAAI;
//import com.hearthsim.util.Bool;
//import com.hearthsim.Reader;
//import com.hearthsim.OutputHandler;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;
import java.lang.Runnable;
import java.lang.Thread;

public class Handler {
	public static void main(String[] args) {
	  boolean gameOver = false;
	  BoardModel currentBoard;
	  PlayerModelProto playerProto;
	  PlayerModelProto opponentProto;
	  AtomicBoolean update = new AtomicBoolean(false);
	  AtomicBoolean terminate = new AtomicBoolean(false);
	  ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	  Lock read = rwLock.readLock();

	  //Open Reader in new thread
	  (new Thread(new Reader(playerProto, opponentProto, update, terminate, rwLock))).start();

	  while(!terminate.get()) {
		while(!update.get()); //wait for Reader to parse from log file
		read.lock();
		  Card[] playerCards = new Card[playerProto.getDeck().getCardsCount()];
		  for(int i=0; i<playerProto.getDeck().getCardsCount(); i++) {
			playerCards[i].hasbeenUsed(playerProto.getDeck().getCards(i).getHasBeenUsed());
			playerCards[i].setInHand(playerProto.getDeck().getCards(i).getInHand());
			//playerProto.getDeck().getCards(i).getName();  Card class doesn't actually have a string setter function
		  }
		  Card[] opponentCards = new Card[opponentProto.getDeck().getCardsCount()];
		  for(int i=0; i<opponentProto.getDeck().getCardsCount(); i++) {
			opponentCards[i].hasbeenUsed(opponentProto.getDeck().getCards(i).getHasBeenUsed());
			opponentCards[i].setInHand(opponentProto.getDeck().getCards(i).getInHand());
			//opponentProto.getDeck().getCards(i).getName();  Need to understand how it constructs normally first (groovy)
		  }
		  Hero playerHero = new TestHero();
		  Hero opponentHero = new TestHero();
		  Deck playerDeck = new Deck(playerCards);
		  Deck opponentDeck = new Deck(opponentCards);
		  PlayerModel playerModel = new PlayerModel(playerModel.getPlayerId(),playerModel.getName(), playerHero, playerDeck);
		  PlayerModel opponentModel = new PlayerModel(opponentModel.getPlayerId(),opponentModel.getName(), opponentHero, opponentDeck);
		  currentBoard = new BoardModel(playerModel, opponentModel);
		  update.set(false);
		  read.unlock();
		//if(currentBoard.isDead(PlayerSide.CURRENT_PLAYER) || currentBoard.isDead(PlayerSide.WAITING_PLAYER)) {
		//  terminate.set(true);
		//  break;
		//}
		//test current board and generate tree, etc.
	  }
	}
}