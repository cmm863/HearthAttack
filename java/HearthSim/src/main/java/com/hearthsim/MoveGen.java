/*
 *
 *
 *
 *
 */

package com.hearthsim;

import com.hearthsim.exception.HSException;
import com.hearthsim.exception.HSInvalidCardException;
import com.hearthsim.exception.HSInvalidParamFileException;
import com.hearthsim.exception.HSParamNotFoundException;

import com.hearthsim.player.playercontroller.WeightedScorer;
import com.hearthsim.util.factory.BoardStateFactoryBase;
import com.hearthsim.util.factory.DepthBoardStateFactory;
import com.hearthsim.util.factory.BreadthBoardStateFactory;
import com.hearthsim.util.HearthActionBoardPair;
import com.hearthsim.util.tree.HearthTreeNode;
import com.hearthsim.util.tree.StopNode;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.util.CardFactory;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MoveGen {
  private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
  private final static int MAX_THINK_TIME = 20000;
  private int numMoves = 10;
  private WeightedScorer scorer = new WeightedScorer();

  public MoveGen() {
    this(10);
  }

  public MoveGen(int x){
    numMoves = x;
    this.scorer.setMyAttackWeight(0.9);
    this.scorer.setMyHealthWeight(0.9);
    this.scorer.setEnemyAttackWeight(1.0);
    this.scorer.setEnemyHealthWeight(1.0);
    this.scorer.setTauntWeight(1.0);
    this.scorer.setMyHeroHealthWeight(0.1);
    this.scorer.setEnemyHeroHealthWeight(0.1);
    this.scorer.setManaWeight(0.1);
    this.scorer.setMyNumMinionsWeight(0.5);
    this.scorer.setEnemyNumMinionsWeight(0.5);
    this.scorer.setSpellDamageAddWeight(0.0);
    this.scorer.setSpellDamageMultiplierWeight(0.5);
    this.scorer.setMyDivineShieldWeight(0.0);
    this.scorer.setEnemyDivineShieldWeight(0.0);

    this.scorer.setMyWeaponWeight(0.5);
    this.scorer.setEnemyWeaponWeight(0.5);
  }

  public MoveGen(int x, WeightedScorer y){
    this.scorer = y;
    numMoves = x;
  }

  public int getNumMoves() {
    return numMoves;
  }

  public void setNumMoves(int x) {
    numMoves = x;
  }

  //Pre : Takes a turn number and a BoardModel, just as a normal HearthSim AI would.
  //Post: Returns a list of selected moves sorted in decreasing order
  //      of value with the end of the move occurring in the 0th position of the list.
  public List<ArrayList<HearthActionBoardPair>> getMoves(int turn, BoardModel board) throws HSException{
    PlayerModel playerModel0 = board.getCurrentPlayer();
    PlayerModel playerModel1 = board.getWaitingPlayer();
    BoardStateFactoryBase factory = new BreadthBoardStateFactory(playerModel0.getDeck(), playerModel1.getDeck());

    HearthTreeNode toRet = new HearthTreeNode(board);
    HearthTreeNode rootNode = factory.doMoves(toRet, this.scorer);
    if(rootNode.getChildren() != null) {
      log.warn("tree generated at least " + rootNode.getChildren().size() + " children.");
    }
    ArrayList<ArrayList<HearthActionBoardPair>> retList = new ArrayList<>();
    ArrayList<HearthTreeNode> sortedChildArray = new ArrayList<>();

    sortedChildArray = findChildren(rootNode);
    int i = 0;
    for( HearthTreeNode endState : sortedChildArray) {
      if( i == numMoves) {
        break;
      }
      HearthTreeNode current = endState;
      retList.add(new ArrayList<HearthActionBoardPair>());
      while(current.getParent() != null) {
        retList.get(i).add(new HearthActionBoardPair(current.getAction(), current.data_.deepCopy()));
        current = current.getParent();
      }
      retList.get(i).add(new HearthActionBoardPair(current.getAction(), current.data_.deepCopy()));
      i++;
    }
    return retList;
  }

  //Pre :  Takes a HearthTreeNode parent as the root of a tree.
  //Post:  returns a list of leaf nodes of the tree sorted by the value of
  //         getScore() such that the highest value is in the 0th position.
  //Desc:  Recursively calls itself on each child node.  If the parent is a leaf,
  //       returns an ArrayList containing just the parent node.  Otherwise, takes
  //       the ArrayList returned from its child nodes and sorts them according to value.
  private ArrayList<HearthTreeNode> findChildren(HearthTreeNode parent) {
    ArrayList<HearthTreeNode> ret = new ArrayList<>();
    if(parent.getChildren() == null) {
      ret.add(parent);
    } else {
      ArrayList<HearthTreeNode> x = new ArrayList<>();
      ArrayList<HearthTreeNode> temp = new ArrayList<>();
      for(HearthTreeNode child : parent.getChildren()){
        x = findChildren(child);
        int i = 0;
        int j = 0;
        //sorts moves by quality
        while(i < ret.size() && j < x.size() && temp.size() < numMoves) {
          if(ret.get(i).getScore() > x.get(j).getScore()) {
            temp.add(ret.get(i));
            i++;
          } else {
            temp.add(x.get(j));
            j++;
          }
        }
        //fills any remaining space in moveset
        if((i < ret.size() || j < x.size()) && temp.size() < numMoves) {
          if(i < ret.size()) {
            while( i < ret.size() && temp.size() < numMoves){
              temp.add(ret.get(i));
              i++;
            }
          } else {
            while( j < x.size() && temp.size() < numMoves) {
              temp.add(x.get(j));
              j++;
            }
          }
        }
        ret = temp;
        temp = x;
        temp.clear();
      }
    }

    return ret;
  }
}