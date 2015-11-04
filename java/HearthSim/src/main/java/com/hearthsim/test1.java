package my.test;

import com.hearthsim.card.Card;

public class test1 {
  public static void main(String[] args) {
    final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(test1.class);
    Card player = new Card();
    log.error("HELLO WORLD?  This is not HearthSim");
  }

}
