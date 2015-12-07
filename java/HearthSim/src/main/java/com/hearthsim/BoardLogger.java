



package com.hearthsim;

import com.hearthsim.model.BoardModel;
import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.util.HearthAction;

import com.hearthsim.exception.HSException;

public class BoardLogger {
  private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BoardLogger.class);
  public void log(BoardModel board) throws HSException {
    String str = "";
    log.warn("------BOARDSTATE------");
    log.warn(board.toString());
    for (Card card : board.getCurrentPlayer().getHand()) {
      str = str + card.getName();
      str = str + ", ";
    }
    log.warn("P0 Hand : " + str);
    str = "";
    for (Minion minion : board.getCurrentPlayer().getMinions()) {
      str = str + minion.getName();
      str = str + ", ";
    }
    log.warn("P0 Field : " + str);
    str = "";
    for (Card card : board.getWaitingPlayer().getHand()) {
      str = str + card.getName();
      str = str + ", ";
    }
    log.warn("P1 Hand : " + str);
    str = "";
    for (Minion minion : board.getWaitingPlayer().getMinions()) {
      str = str + minion.getName();
      str = str + ", ";
    }
    log.warn("P1 Field : " + str);
  }
}
