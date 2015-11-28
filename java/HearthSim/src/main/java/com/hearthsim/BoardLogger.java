



package com.hearthsim;

import com.hearthsim.model.BoardModel;
import com.hearthsim.card.Card;
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
    log.warn("P1 Hand : " + str);
    str = "";
    for (Card card : board.getWaitingPlayer().getHand()) {
      str = str + card.getName();
      str = str + ", ";
    }
    log.warn("P2 Hand : " + str);
  }
}
