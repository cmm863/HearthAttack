//Opens a client to send default instance of boardModel message to Reader class

package com.hearthattack;

import com.protos.*;
import com.hearthattack.*;
import java.io.*;
import java.net.Socket;

public class Tester {
  public static void main(String[] args){
    int portNumber = Integer.parseInt(args[0]);
    try {
      FileOutputStream outfile = new FileOutputStream("test");
      Socket socket = new Socket("localhost", portNumber);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      CardProto.Card card = CardProto.Card.newBuilder().setName("").build();
      MinionProto.Minion minion = MinionProto.Minion.newBuilder().setCard(card).setTurnPlayed(0).setPosition(0).build();
      DeckProto.Deck deck = DeckProto.Deck.newBuilder().addCards(card).build();
      WeaponProto.Weapon weapon = WeaponProto.Weapon.newBuilder().setName("").setAttack(0).setDurability(0).build();
      HeroProto.Hero hero = HeroProto.Hero.newBuilder().setWeapon(weapon).setArmor(0).setMinion(minion).build();
      PlayerModelProto.PlayerModel player = PlayerModelProto.PlayerModel.newBuilder().setName("").setPlayerId(0).setHero(hero).setDeck(deck)
                                                            .setMaxMana(0).addMinions(minion).addHand(card).build();
      BoardModelProto.BoardModel board = BoardModelProto.BoardModel.newBuilder().setPlayer(player).setOpponent(player).build();
      outfile.write(board.toByteArray());
//      out.println(board.toByteArray());
//      out.flush();
      System.in.read();
      System.out.println("Finished sending message");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }
}