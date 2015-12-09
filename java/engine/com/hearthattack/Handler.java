//Translates the PlayerModelProto.PlayerModel objects read from socket into BoardModel object
//One command line argument is needed: port number for server to listen on

package com.hearthattack;

import com.protos.*;
import com.hearthattack.*;
import com.hearthsim.model.*;
import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.CharacterIndex;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.ImplementedCardList;
import com.hearthsim.card.weapon.WeaponCard;
import com.hearthsim.exception.HSException;
import com.hearthsim.card.basic.weapon.FieryWarAxe;
import com.hearthsim.card.minion.heroes.TestHero;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;
import java.lang.Thread;
import java.io.IOException;
import java.lang.reflect.*;

public class Handler {
  public static void main(String[] args) throws IOException, HSException {
    boolean gameOver = false;
    BoardModel currentBoard;
    moveGen movegen = new moveGen(10);
    Messenger message = new Messenger();
    int turn;
    PlayerModelProto.PlayerModel playerProto = PlayerModelProto.PlayerModel.getDefaultInstance();
    PlayerModelProto.PlayerModel opponentProto = PlayerModelProto.PlayerModel.getDefaultInstance();
    ImplementedCardList cardList = ImplementedCardList.getInstance();
    AtomicBoolean update = new AtomicBoolean(false);
    AtomicBoolean terminate = new AtomicBoolean(false);
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    Lock read = rwLock.readLock();

    //Open Reader in new thread
    (new Thread(new Reader(playerProto, opponentProto, update, terminate, rwLock, Integer.parseInt(args[0])))).start();

    while(!terminate.get()) {
      System.out.println("Waiting for update");
      while(!update.get()); //wait for Reader to parse from log file
      System.out.println("Received update");
      read.lock();
      PlayerModel playerModel = makePlayerModel(cardList, playerProto);
      PlayerModel opponentModel = makePlayerModel(cardList, opponentProto);
      currentBoard = new BoardModel(playerModel, opponentModel);
      for(int i=0,j=0; i<playerProto.getMinionsCount() || j<opponentProto.getMinionsCount(); ){
        if(i>=playerProto.getMinionsCount()){
          currentBoard.placeMinion(PlayerSide.WAITING_PLAYER, makeMinion(cardList,opponentProto.getMinions(j)),
                                   CharacterIndex.fromInteger(j));
          ++j;
        } else if(j>=opponentProto.getMinionsCount()){
          currentBoard.placeMinion(PlayerSide.CURRENT_PLAYER, makeMinion(cardList,playerProto.getMinions(i)),
                                   CharacterIndex.fromInteger(i));
          ++i;
        } else if(playerProto.getMinions(i).getTurnPlayed() < opponentProto.getMinions(j).getTurnPlayed()){
          currentBoard.placeMinion(PlayerSide.CURRENT_PLAYER, makeMinion(cardList,playerProto.getMinions(i)),
                                   CharacterIndex.fromInteger(i));
          ++i;
        } else{
          currentBoard.placeMinion(PlayerSide.WAITING_PLAYER, makeMinion(cardList,opponentProto.getMinions(j)),
                                   CharacterIndex.fromInteger(j));
          ++j;
        }
      }
      turn = playerProto.getTurnNumber();
      update.set(false);
      read.unlock();
      if(currentBoard.isDead(PlayerSide.CURRENT_PLAYER) || currentBoard.isDead(PlayerSide.WAITING_PLAYER)) {
        terminate.set(true);
        break;
      }
      message.send(movegen.getMoves(turn, currentBoard));
    }
  }
  
  public static Card makeCard(ImplementedCardList cardList, CardProto.Card in){
    Card out = new Card();
    if(cardList.getCardForName(in.getName())!=null)
      out = cardList.getCardForName(in.getName()).createCardInstance();
    out.setInHand(in.getInHand());
    out.hasBeenUsed(in.getHasBeenUsed());
    return out;
  }
  
  public static Minion makeMinion(ImplementedCardList cardList, MinionProto.Minion in){
    Minion out = new Minion();
    try{
      if(cardList.getCardForName(in.getCard().getName())!=null)
        out = (Minion)cardList.getCardForName(in.getCard().getName()).cardClass_.getConstructor().newInstance();
    }
    catch (NoSuchMethodException e) {
      System.out.println("NoSuchMethodException");
    }
    catch (InstantiationException e) {
      System.out.println("InstantiationException");
    }
    catch (IllegalAccessException e) {
      System.out.println("IllegalAccessException");
    }
    catch (InvocationTargetException e) {
      System.out.println("InvocationTargetException");
    }
    out.setInHand(in.getCard().getInHand());
    out.hasBeenUsed(in.getCard().getHasBeenUsed());
    if(in.hasTaunt())
      out.setTaunt(in.getTaunt());
    if(in.hasDivineShield())
      out.setDivineShield(in.getDivineShield());
    if(in.hasWindfury())
      out.setWindfury(in.getWindfury());
    if(in.hasCharge())
      out.setCharge(in.getCharge());
    if(in.hasImmune())
      out.setImmune(in.getImmune());
    if(in.hasHasAttacked())
      out.hasAttacked(in.getHasAttacked());
    if(in.hasHasWindfuryAttacked())
      out.hasWindFuryAttacked(in.getHasWindfuryAttacked());
    if(in.hasFrozen())
      out.setFrozen(in.getFrozen());
    //setSilenced() setter function is protected in Minion class
    if(in.hasStealthedUntilRevealed())
      out.setStealthedUntilRevealed(in.getStealthedUntilRevealed());
    if(in.hasStealthedUntilNextTurn())
      out.setStealthedUntilNextTurn(in.getStealthedUntilNextTurn());
    if(in.hasHeroTargetable())
      out.setHeroTargetable(in.getHeroTargetable());
    if(in.hasHealth())
      out.setHealth((byte)in.getHealth());
    out.setMaxHealth((byte)in.getMaxHealth());
    if(in.hasAuraHealth())
      out.setAuraHealth((byte)in.getAuraHealth());
    if(in.hasAttack())
      out.setAttack((byte)in.getAttack());
    if(in.hasExtraAttackUntilTurnEnd())
      out.setExtraAttackUntilTurnEnd((byte)in.getExtraAttackUntilTurnEnd());
    if(in.hasAuraAttack())
      out.setAuraAttack((byte)in.getAuraAttack());
    if(in.hasDestroyOnTurnStart())
      out.setDestroyOnTurnStart(in.getDestroyOnTurnStart());
    if(in.hasDestroyOnTurnEnd())
      out.setDestroyOnTurnEnd(in.getDestroyOnTurnEnd());
    if(in.hasSpellDamage())
      out.setSpellDamage((byte)in.getSpellDamage());
    //CantAttack has no setter function in Minion class
    //Tribe cannot be set for Minion Class, always retrieved from ImplementedCardList
    return out;
  }
  
  public static WeaponCard makeWeapon(ImplementedCardList cardList, WeaponProto.Weapon in){
    WeaponCard out = new FieryWarAxe();
    try{
      if(cardList.getCardForName(in.getName())!=null)
        out = (WeaponCard)cardList.getCardForName(in.getName()).cardClass_.getConstructor().newInstance();
    }
    catch (NoSuchMethodException e) {
      System.out.println("NoSuchMethodException");
    }
    catch (InstantiationException e) {
      System.out.println("InstantiationException");
    }
    catch (IllegalAccessException e) {
      System.out.println("IllegalAccessException");
    }
    catch (InvocationTargetException e) {
      System.out.println("InvocationTargetException");
    }
    out.setInHand(false);
    out.hasBeenUsed(true);
    out.setWeaponDamage((byte)in.getAttack());
    out.setWeaponCharge((byte)in.getDurability());
    return out;
  }
  
  public static Hero makeHero(ImplementedCardList cardList, HeroProto.Hero in){
    Hero out = new TestHero();
    try{
      if(cardList.getCardForName(in.getMinion().getCard().getName())!=null)
        out = (Hero)cardList.getCardForName(in.getMinion().getCard().getName()).cardClass_.getConstructor().newInstance();
    }
    catch (NoSuchMethodException e) {
      System.out.println("NoSuchMethodException");
    }
    catch (InstantiationException e) {
      System.out.println("InstantiationException");
    }
    catch (IllegalAccessException e) {
      System.out.println("IllegalAccessException");
    }
    catch (InvocationTargetException e) {
      System.out.println("InvocationTargetException");
    }
    out.setInHand(in.getMinion().getCard().getInHand());
    out.hasBeenUsed(in.getMinion().getCard().getHasBeenUsed());
    out.setWeapon(makeWeapon(cardList,in.getWeapon()));
    out.setArmor((byte)in.getArmor());
    if(in.getMinion().hasTaunt())
      out.setTaunt(in.getMinion().getTaunt());
    if(in.getMinion().hasDivineShield())
      out.setDivineShield(in.getMinion().getDivineShield());
    if(in.getMinion().hasWindfury())
      out.setWindfury(in.getMinion().getWindfury());
    if(in.getMinion().hasCharge())
      out.setCharge(in.getMinion().getCharge());
    if(in.getMinion().hasImmune())
      out.setImmune(in.getMinion().getImmune());
    if(in.getMinion().hasHasAttacked())
      out.hasAttacked(in.getMinion().getHasAttacked());
    if(in.getMinion().hasHasWindfuryAttacked())
      out.hasWindFuryAttacked(in.getMinion().getHasWindfuryAttacked());
    if(in.getMinion().hasFrozen())
      out.setFrozen(in.getMinion().getFrozen());
    //setSilenced() setter function is protected in Minion class
    if(in.getMinion().hasStealthedUntilRevealed())
      out.setStealthedUntilRevealed(in.getMinion().getStealthedUntilRevealed());
    if(in.getMinion().hasStealthedUntilNextTurn())
      out.setStealthedUntilNextTurn(in.getMinion().getStealthedUntilNextTurn());
    if(in.getMinion().hasHeroTargetable())
      out.setHeroTargetable(in.getMinion().getHeroTargetable());
    if(in.getMinion().hasHealth())
      out.setHealth((byte)in.getMinion().getHealth());
    out.setMaxHealth((byte)in.getMinion().getMaxHealth());
    if(in.getMinion().hasAuraHealth())
      out.setAuraHealth((byte)in.getMinion().getAuraHealth());
    if(in.getMinion().hasAttack())
      out.setAttack((byte)in.getMinion().getAttack());
    if(in.getMinion().hasExtraAttackUntilTurnEnd())
      out.setExtraAttackUntilTurnEnd((byte)in.getMinion().getExtraAttackUntilTurnEnd());
    if(in.getMinion().hasAuraAttack())
      out.setAuraAttack((byte)in.getMinion().getAuraAttack());
    if(in.getMinion().hasDestroyOnTurnStart())
      out.setDestroyOnTurnStart(in.getMinion().getDestroyOnTurnStart());
    if(in.getMinion().hasDestroyOnTurnEnd())
      out.setDestroyOnTurnEnd(in.getMinion().getDestroyOnTurnEnd());
    if(in.getMinion().hasSpellDamage())
      out.setSpellDamage((byte)in.getMinion().getSpellDamage());
    //CantAttack has no setter function in Minion class
    //Tribe cannot be set for Minion Class, always retrieved from ImplementedCardList
    return out;
  }
  
  public static Deck makeDeck(ImplementedCardList cardList, DeckProto.Deck in){
    Card[] cards = new Card[in.getCardsCount()];
    for(int i=0; i<in.getCardsCount(); ++i)
      cards[i] = makeCard(cardList,in.getCards(i));
    Deck out = new Deck(cards);
    return out;
  }
  
  public static HandModel makeHand(ImplementedCardList cardList, PlayerModelProto.PlayerModel player){
    HandModel hand = new HandModel();
    for(int i=0; i<player.getHandCount(); ++i)
      hand.add(makeCard(cardList,player.getHand(i)));
    return hand;
  }
  
  public static PlayerModel makePlayerModel(ImplementedCardList cardList, PlayerModelProto.PlayerModel in){
    PlayerModel out = new PlayerModel((byte)in.getPlayerId(),in.getName(),makeHero(cardList,in.getHero()),makeDeck(cardList,in.getDeck()));
    if(in.hasMana())
      out.setMana((byte)in.getMana());
    out.setMaxMana((byte)in.getMaxMana());
    if(in.hasDeckPos())
      out.setDeckPos((byte)in.getDeckPos());
    if(in.hasFatigueDamage())
      out.setFatigueDamage((byte)in.getFatigueDamage());
/*    for(int i=0; i<in.getMinionsCount(); ++i){
      for(int j=0; j<in.getMinionsCount(); ++j){
        if(in.getMinions(j).getPosition() == i)
          out.addMinion(i,makeMinion(cardList,in.getMinions(i)));
      }
    }*/
    out.setHand(makeHand(cardList,in));
    if(in.hasOverload())
      out.setOverload((byte)in.getOverload());
    if(in.hasNumCardsUsed())
      out.setNumCardsUsed((byte)in.getNumCardsUsed());
    return out;
  }
}
