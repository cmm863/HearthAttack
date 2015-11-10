#ifndef HAND_H
#define HAND_H

#include "deck.h"

class Hand
{
  int m_numCards;
  Card m_cardList[10];
  Deck m_deck;
  Card* m_field[14];

  public:

  Hand();

  void addCard(Card* card);

  void addCard(string name);

  void removeCard(string name);

  void removeCard(int index);

  void decidePlay(int mana, Character you, Character opp);

  int getNumCards(){return m_numCards;}

  void print();
};

#endif // HAND_H
