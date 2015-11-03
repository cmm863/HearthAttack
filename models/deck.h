#ifndef DECK_H
#define DECK_H

#include "card.h"

class Deck
{
  int m_numCards;
  Card m_cardList[30];

  public:

  Deck();

  Card* findCard(string name);
};

#endif // DECK_H
