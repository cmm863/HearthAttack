//Translates the PlayerModelProto.PlayerModel objects read from file into BoardModel object
//One command line argument is needed: System path for the file to be read

package com.hearthattack;

import com.protos.*;
import com.hearthsim.model.*;
import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.ImplementedCardList;
import com.hearthsim.card.weapon.WeaponCard;

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
    PlayerModelProto.PlayerModel playerProto = PlayerModelProto.PlayerModel.getDefaultInstance();
    PlayerModelProto.PlayerModel opponentProto = PlayerModelProto.PlayerModel.getDefaultInstance();
    ImplementedCardList cardList = ImplementedCardList.getInstance();
    AtomicBoolean update = new AtomicBoolean(false);
    AtomicBoolean terminate = new AtomicBoolean(false);
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    Lock read = rwLock.readLock();

    //Open Reader in new thread
    (new Thread(new Reader(playerProto, opponentProto, update, terminate, rwLock, args[0]))).start();

    while(!terminate.get()) {
      while(!update.get()); //wait for Reader to parse from log file
        read.lock();
        PlayerModel playerModel = makePlayerModel(cardList, playerProto);
        PlayerModel opponentModel = makePlayerModel(cardList, opponentProto);
        currentBoard = new BoardModel(playerModel, opponentModel);
        update.set(false);
        read.unlock();
      if(currentBoard.isDead(PlayerSide.CURRENT_PLAYER) || currentBoard.isDead(PlayerSide.WAITING_PLAYER)) {
        terminate.set(true);
        break;
      }
      //send currentBoard to tree generator
    }
  }
  
  public static Card makeCard(ImplementedCardList cardList, CardProto.Card in){
    Card out = cardList.getCardForName(in.getName()).createCardInstance();
    out.setInHand(in.getInHand());
    out.hasBeenUsed(in.getHasBeenUsed());
    return out;
  }
  
  public static Minion makeMinion(ImplementedCardList cardList, MinionProto.Minion in){
    Minion out = (Minion)makeCard(cardList,in.getCard());
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
    WeaponCard out = (WeaponCard)cardList.getCardForName(in.getName()).createCardInstance();
    out.setWeaponDamage((byte)in.getAttack());
    out.setWeaponCharge((byte)in.getDurability());
    return out;
  }
  
  public static Hero makeHero(ImplementedCardList cardList, HeroProto.Hero in){
    Hero out = (Hero)makeMinion(cardList,in.getMinion());
    out.setWeapon(makeWeapon(cardList,in.getWeapon()));
    out.setArmor((byte)in.getArmor());
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
    for(int i=0; i<in.getMinionsCount(); ++i)
      out.addMinion(i,makeMinion(cardList,in.getMinions(i)));
    out.setHand(makeHand(cardList,in));
    if(in.hasOverload())
      out.setOverload((byte)in.getOverload());
    if(in.hasNumCardsUsed())
      out.setNumCardsUsed((byte)in.getNumCardsUsed());
    return out;
  }
}
