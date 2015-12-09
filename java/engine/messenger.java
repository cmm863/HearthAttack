package com.hearthattack;

import com.protos.HAOutboundProto;
import com.protos.CardProto;
import com.protos.DeckProto;
import com.protos.HeroProto;
import com.protos.MinionProto;
import com.protos.PlayerModelProto;
import com.protos.WeaponProto;

import com.hearthsim.util.HearthAction;
import com.hearthsim.util.HearthActionBoardPair;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.Card;
import com.hearthsim.card.CharacterIndex;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.weapon.WeaponCard;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Messenger {
  HAOutboundProto.MoveList.Builder msg = HAOutboundProto.MoveList.newBuilder();
  FileOutputStream output;
  
  public Messenger() throws FileNotFoundException {
    output =  new FileOutputStream("default.log");
  }
  
  public Messenger(String arg) throws FileNotFoundException {
    output = new FileOutputStream(arg);
  }
  public void send(List<ArrayList<HearthActionBoardPair>> moveSet) throws IOException {
    msg.clear();
    HAOutboundProto.HABP val;
    HAOutboundProto.MoveList.HABPList.Builder move = HAOutboundProto.MoveList.HABPList.newBuilder();
    for(int i = 0; i < moveSet.size(); i++) {
      move.clear();
      for(int j = 0; j < moveSet.get(i).size(); j++) {
        val = this.convertABP(moveSet.get(i).get(j));
        move.addActionBoardPair(val);
      }
      msg.addMove(move.build());
    }
    msg.build().writeTo(output);
  }
  
  public HAOutboundProto.HABP convertABP(HearthActionBoardPair abp) {
    HAOutboundProto.HABP.Builder ret = HAOutboundProto.HABP.newBuilder();
    ret.setBoard(this.convertBoard(abp.board));
    ret.setAction(this.convertAction(abp.action));
    return ret.build();
  }
  
  public HAOutboundProto.Board convertBoard(BoardModel board) {
    HAOutboundProto.Board.Builder ret = HAOutboundProto.Board.newBuilder();
    ret.setCurrentPlayer(this.convertPlayer(board.getCurrentPlayer()));
    ret.setWaitingPlayer(this.convertPlayer(board.getWaitingPlayer()));
    return ret.build();
  }
  
  public HAOutboundProto.Action convertAction(HearthAction action) {
    HAOutboundProto.Action.Builder ret = HAOutboundProto.Action.newBuilder();
    ret.setVerb(this.convertVerb(action.verb_));
    ret.setActionPerformerPlayerSide(this.convertPlayerSide(action.getAPPS()));
    ret.setCardOrCharacterIndex(action.getIndex());
    ret.setTargetPlayerSide(this.convertPlayerSide(action.getTPS()));
    ret.setTargetCharacterIndex(this.convertCharIndex(action.targetCharacterIndex));
    return ret.build();
  }
  
  public PlayerModelProto.PlayerModel convertPlayer(PlayerModel player) {
    PlayerModelProto.PlayerModel.Builder ret = PlayerModelProto.PlayerModel.newBuilder();
    ret.setName(player.getName());
    ret.setPlayerId(player.getPlayerId());
    ret.setHero(this.convertHero(player.getHero()));
    ret.setDeck(this.convertDeck(player.getDeck()));
    ret.setMana(player.getMana());
    ret.setMaxMana(player.getMaxMana());
    ret.setDeckPos(player.getDeckPos());
    ret.setFatigueDamage(player.getFatigueDamage());
    for(Minion minion : player.getMinions()) {
      ret.addMinions(this.convertMinion(minion));
    }
    for(Card card : player.getHand()) {
      ret.addHand(this.convertCard(card));
    }
    ret.setOverload(player.getOverload());
    ret.setNumCardsUsed(player.getNumCardsUsed());
    return ret.build();
  }
  
  public HeroProto.Hero convertHero(Hero hero) {
    HeroProto.Hero.Builder ret = HeroProto.Hero.newBuilder();
    ret.setWeapon(this.convertWeapon(hero.getWeapon()));
    ret.setArmor(hero.getArmor());
    ret.setMinion(this.convertMinion(hero));
    return ret.build();
  }
  
  public WeaponProto.Weapon convertWeapon(WeaponCard weapon) {
    WeaponProto.Weapon.Builder ret = WeaponProto.Weapon.newBuilder();
    ret.setName(weapon.getName());
    ret.setDurability(weapon.getWeaponCharge());
    ret.setAttack(weapon.getWeaponDamage());
    return ret.build();
  }
  
  public DeckProto.Deck convertDeck(Deck deck) {
    DeckProto.Deck.Builder ret = DeckProto.Deck.newBuilder();
    for(Card card : deck.getDeck()) {
      ret.addCards(this.convertCard(card));
    }
    return ret.build();
  }
  
  public MinionProto.Minion convertMinion(Minion minion) {
    MinionProto.Minion.Builder ret = MinionProto.Minion.newBuilder();
    ret.setTaunt(minion.getTaunt());
    ret.setDivineShield(minion.getDivineShield());
    ret.setWindfury(minion.getWindfury());
    ret.setCharge(minion.getCharge());
    ret.setImmune(minion.getImmune());
    ret.setHasAttacked(minion.hasAttacked());
    ret.setHasWindfuryAttacked(minion.hasWindFuryAttacked());
    ret.setFrozen(minion.getFrozen());
    ret.setSilenced(minion.isSilenced());
    ret.setStealthedUntilRevealed(minion.getStealthedUntilRevealed());
    ret.setStealthedUntilNextTurn(minion.getStealthedUntilNextTurn());
    ret.setHeroTargetable(minion.isHeroTargetable());
    ret.setHealth(minion.getHealth());
    ret.setMaxHealth(minion.getMaxHealth());
    ret.setAuraHealth(minion.getAuraHealth());
    ret.setAttack(minion.getBaseAttack());
    ret.setExtraAttackUntilTurnEnd(minion.getExtraAttackUntilTurnEnd());
    ret.setAuraAttack(minion.getAuraAttack());
    ret.setDestroyOnTurnStart(minion.getDestroyOnTurnStart());
    ret.setDestroyOnTurnEnd(minion.getDestroyOnTurnEnd());
    ret.setSpellDamage(minion.getSpellDamage());
    ret.setCantAttack(!minion.canAttack());//ADD SHIT
    ret.setTribe(this.convertTribe(minion.getTribe()));
    ret.setCard(this.convertCard(minion));
    return ret.build();
  }
  
  public CardProto.Card convertCard(Card card) {
    CardProto.Card.Builder ret = CardProto.Card.newBuilder();
    ret.setHasBeenUsed(card.hasBeenUsed());
    ret.setInHand(card.setInHand());
    ret.setName(card.getName());
    return ret.build();
  }

  public HAOutboundProto.Action.Verb convertVerb(HearthAction.Verb verb) {
    switch (verb) {
      case USE_CARD :
        return HAOutboundProto.Action.Verb.USE_CARD;
      case HERO_ABILITY :
        return HAOutboundProto.Action.Verb.HERO_ABILITY;
      case ATTACK :
        return HAOutboundProto.Action.Verb.ATTACK;
      case UNTARGETABLE_BATTLECRY :
        return HAOutboundProto.Action.Verb.UNTARGETABLE_BATTLECRY;
      case TARGETABLE_BATTLECRY :
        return HAOutboundProto.Action.Verb.TARGETABLE_BATTLECRY;
      case START_TURN :
        return HAOutboundProto.Action.Verb.START_TURN;
      case END_TURN :
        return HAOutboundProto.Action.Verb.END_TURN;
      case DO_NOT_USE_CARD :
        return HAOutboundProto.Action.Verb.DO_NOT_USE_CARD;
      case DO_NOT_ATTACK :
        return HAOutboundProto.Action.Verb.DO_NOT_ATTACK;
      case DO_NOT_USE_HEROPOWER :
        return HAOutboundProto.Action.Verb.DO_NOT_USE_HEROPOWER;
      case RNG :
        return HAOutboundProto.Action.Verb.RNG;
      case DRAW_CARDS :
        return HAOutboundProto.Action.Verb.DRAW_CARDS;
      default :
        return HAOutboundProto.Action.Verb.RNG;
    }
  }
  public HAOutboundProto.Action.PlayerSide convertPlayerSide(PlayerSide side) {
    switch (side) {
      case CURRENT_PLAYER :
        return HAOutboundProto.Action.PlayerSide.CURRENT_PLAYER;
      case WAITING_PLAYER :
        return HAOutboundProto.Action.PlayerSide.WAITING_PLAYER;
    }
    return HAOutboundProto.Action.PlayerSide.WAITING_PLAYER;
  }

  public HAOutboundProto.Action.CharacterIndex convertCharIndex(CharacterIndex index) {
    switch (index) {
      case HERO :
        return HAOutboundProto.Action.CharacterIndex.HERO;
      case MINION_1 :
        return HAOutboundProto.Action.CharacterIndex.MINION_1;
      case MINION_2 :
        return HAOutboundProto.Action.CharacterIndex.MINION_2;
      case MINION_3 :
        return HAOutboundProto.Action.CharacterIndex.MINION_3;
      case MINION_4 :
        return HAOutboundProto.Action.CharacterIndex.MINION_4;
      case MINION_5 :
        return HAOutboundProto.Action.CharacterIndex.MINION_5;
      case MINION_6 :
        return HAOutboundProto.Action.CharacterIndex.MINION_6;
      case MINION_7 :
        return HAOutboundProto.Action.CharacterIndex.MINION_7;
      case MINION_8 :
        return HAOutboundProto.Action.CharacterIndex.MINION_8;
      case MINION_9 :
        return HAOutboundProto.Action.CharacterIndex.MINION_9;
      case UNKNOWN :
        return HAOutboundProto.Action.CharacterIndex.UNKNOWN;
    }
    return HAOutboundProto.Action.CharacterIndex.UNKNOWN;
  }

  public MinionProto.Minion.Tribe convertTribe(Minion.MinionTribe tribe) {
    switch (tribe) {
      case NONE :
        return MinionProto.Minion.Tribe.NONE;
      case BEAST :
        return MinionProto.Minion.Tribe.BEAST;
      case MECH :
        return MinionProto.Minion.Tribe.MECH;
      case MURLOC :
        return MinionProto.Minion.Tribe.MURLOC;
      case PIRATE :
        return MinionProto.Minion.Tribe.PIRATE;
      case DEMON :
        return MinionProto.Minion.Tribe.DEMON;
      case DRAGON :
        return MinionProto.Minion.Tribe.DRAGON;
      case TOTEM :
        return MinionProto.Minion.Tribe.TOTEM;
    }
    return MinionProto.Minion.Tribe.NONE;
  }
}