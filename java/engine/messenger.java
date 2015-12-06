package com.hearthattack;

import com.protos.HAOutboundProto;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class Messenger {
  HAOutboundProto::MoveList.Builder msg = HAOutboundProto::MoveList.newBuilder();
  FileOutputStream output;
  
  public Messenger() {
    output =  new FileOutputStream("default.log");
  }
  
  public Messenger(String arg) {
    output = new FileOutputStream(arg);
  }
  public void send(List<ArrayList<HearthActionBoardPair>> moveSet) {
    msg.clear();
    HAOutboundProto::HABP val;
    for(int i = 0; i < moveSet.size(); i++) {
      msg.addMove(HAOutboundProto::MoveList::HABPList.newBuilder());
      for(int j = 0; j < moveSet.get(i).size(); j++) {
        val = this.convertABP(moveSet.get(i).get(j));
        msg.getMove(i).addActionBoardPair(val);
      }
    }
    msg.build().writeTo(output);
  }
  
  public HAOutboundProto::HABP convertABP(HearthActionBoardPair abp) {
    HAOutboundProto::HABP.builder ret;
    ret.setBoard(this.convertBoard(abp.board));
    ret.setAction(this.convertAction(abp.action));
    return ret;
  }
  
  public HAOutboundProto::Board convertBoard(BoardModel board) {
    HAOutboundProto::Board.builder ret;
    ret.setCurrentPlayer(this.convertPlayer(board.getCurrentPlayer()));
    ret.setWaitingPlayer(this.convertPlayer(board.getWaitingPlayer()));
    return ret;
  }
  
  public HAOutboundProto::Action convertAction(HearthAction action) {
    HAOutboundProto::Action.builder ret;
    ret.setVerb(action.verb_);
    ret.setActionPerformerPlayerSide(action.getAPPS());
    ret.setCardOrCharacterIndex(action.getIndex());
    ret.setTargetPlayerSide(action.getTPS());
    ret.setTargetCharacterIndex(action.targetCharacterIndex);
    return ret;
  }
  
  public PlayerModelProto::PlayerModel convertPlayer(PlayerModel player) {
    PlayerModelProto::PlayerModel.builder ret;
    ret.setName(player.getName());
    ret.setPlayer_id(player.getPlayerId());
    ret.setHero(this.convertHero(player.getHero()));
    ret.setDeck(this.convertDeck(player.getDeck()));
    ret.setMana(player.getMana());
    ret.setMax_mana(player.getMaxMana());
    ret.setDeck_pos(player.getDeckPos());
    ret.setFatigue_damage(player.getFatigueDamage());
    for(minion : player.getMinions()) {
      ret.addMinions(this.convertMinion(minion));
    }
    for(card : player.getHand()) {
      ret.addHand(this.convertCard(card));
    }
    ret.setOverload(player.getOverload());
    ret.setNum_cards_used(player.getNumCardsUsed());
    return ret;
  }
  
  public HeroProto.Hero convertHero(HeroModel hero) {
    HeroProto.Hero.builder ret;
    ret.setWeapon(this.convertWeapon(hero.getWeapon()));
    ret.setArmor(hero.getArmor());
    ret.setMinion(this.convertMinion(hero));
    return ret;
  }
  
  public WeaponProto.Weapon convertWeapon(WeaponCard weapon) {
    WeaponProto.Weapon.builder ret;
    ret.setName(weapon.getName());
    ret.setDurability(weapon.getWeaponCharge());
    ret.setAttack(weapon.getWeaponDamage());
    return ret;
  }
  
  public DeckProto.Deck convertDeck(Deck deck) {
    DeckProto.Deck.builder ret;
    for(card : deck.getDeck()) {
      ret.addCards(this.convertCard(card));
    }
    return ret;
  }
  
  public MinionProto.Minion convertMinion(Minion minion) {
    MinionProto.Minion.builder ret;
    ret.setTaunt(minion.getTaunt());
    ret.setDivine_Shield(minion.getDivineShield());
    ret.setWindfury(minion.getWindfury());
    ret.setCharge(minion.getCharge());
    ret.setImmune(minion.getImmune());
    ret.setHas_attacked(minion.hasAttacked());
    ret.setHas_windfury_attacked(minion.hasWindfuryAttacked());
    ret.setFrozen(minion.getFrozen());
    ret.setSilenced(minion.isSilenced());
    ret.setStealthed_until_revealed(minion.getStealthedUntilRevealed());
    ret.setStealthed_until_next_turn(minion.getStealthedUntilNextTurn());
    ret.setHero_targetable(minion.isHeroTargetable());
    ret.setHealth(minion.getHealth());
    ret.setMax_health(minion.getMaxHealth());
    ret.setAura_health(minion.getAuraHealth());
    ret.setAttack(minion.getBaseAttack());
    ret.setExtra_attack_until_turn_end(minion.getExtraAttackUntilTurnEnd());
    ret.setAura_attack(minion.getAuraAttack());
    ret.setDestroy_on_turn_start(minion.getDestroyOnTurnStart());
    ret.setDestroy_on_turn_end(minion.getDestroyOnTurnEnd());
    ret.setSpell_damage(minion.getSpellDamage());
    ret.setCant_attack(!minion.canAttack());//ADD SHIT
    ret.setTribe(minion.getTribe());
    ret.setCard(this.convertCard(minion));
    return ret;
  }
  
  public CardProto.Card convertCard(Card card) {
    CardProto.Card.builder ret;
    ret.setHas_been_used(card.hasBeenUsed());
    ret.setIn_hand(card.getInHand());
    ret.setName(card.getName());
    return ret;
  }
}