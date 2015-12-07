#include "card.pb.h"
#include "deck.pb.h"
#include "hero.pb.h"
#include "minion.pb.h"
#include "moveList.pb.h"
#include "player_model.pb.h"
#include "update.pb.h"
#include "weapon.pb.h"
#include <iostream>
#include <fstream>

using namespace std;

float valCalc(com::protos::Minion& aMinion)
{
  float totalVal = 0;
  
  totalVal += aMinion.health()*1;
  totalVal += aMinion.attack()*1;

  if(aMinion.has_taunt() && aMinion.taunt())
  {
    totalVal += aMinion.health()*0.25;
    totalVal += aMinion.attack()*0.1;
  }
  if(aMinion.has_charge() && aMinion.charge())
    totalVal += aMinion.attack() * 0.75;
  if(aMinion.has_divine_shield() && aMinion.divine_shield())
    totalVal += 2;
  if(aMinion.has_stealthed_until_revealed() && aMinion.stealthed_until_revealed())
    totalVal += aMinion.attack() * .25;
  if(aMinion.has_windfury() && aMinion.windfury())
    totalVal += aMinion.attack() *.25;
  totalVal += aMinion.spell_damage();
  
  return totalVal;
}


float zombie_chow_val(com::protos::Board& aBoard)
{
  float val = 0;
  
  if(aBoard.waitingPlayer().armor() < 20)
    aBoard.waitingPlayer().armor() / 5.0 - 5;
  
  return val;
}


float value_of(com::protos::Minion& aMinion, com::protos::Board& aBoard)
{
  float theScore = 0;
  
  theScore += valCalc(aMinion);
  
  if(aMinion.name() == "Zombie Chow")
    theScore += zombie_chow_val;
  
  return theScore;
}

float score_board(com::protos::Board& aBoard)
{
  float theScore = 0;
  
  for(int i = 0; i < aBoard.currentPlayer().minions_size(); i++)
    theScore += value_of(aBoard.currentPlayer().minions(i), aBoard);
  for(int i = 0; i < aBoard().waitingPlayer().minions_size(); i++)
    theScore -= value_of(aBoard.waitingPlayer().minions(i), aBoard);
  
  theScore += currentPlayer.hero.armor() * (30 - currentPlayer.hero.armor()) / 30;
  theScore -= waitingPlayer.hero.armor() * (30 - waitingPlayer.hero.armor()) / 30;
 
  return theScore;
}

int main()
{
  com::protos::moveList theList;
  string msg;
  float score;
  float highscore = 0;
  int highIndex = 0;
 
  theList.ParseFromString(msg);
  
  for(int i = 0; i < theList.move_size(); i++)
  {
    score = score_board(theList.move(i).actionBoardPair(theList.move(i).actionBoardPair_size() - 1).board());
    if(score > highscore)
    {
      highscore = score;
      hihIndex = i;
    }
  }
  
  
  
  return 0;
}
