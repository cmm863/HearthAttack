#include "hand.h"

Hand::Hand()
{
  m_numCards = 0;
  for(int i = 0; i < 14; i++)
    m_field[i] = NULL;
}

void Hand::addCard(Card* card)
{
  m_cardList[m_numCards] = *card;
  m_numCards++;
}

void Hand::addCard(string name)
{
  Card* card = m_deck.findCard(name);
  addCard(card);
}

void Hand::removeCard(string name)
{
  bool found = false;

  for(int i = 0; i < m_numCards - 1; i++)
  {
    if(found || m_cardList[i].getName() == name)
    {
      m_cardList[i] = m_cardList[i+1];
      found = true;
      m_numCards--;
    }
  }
}

void Hand::decidePlay(int mana, Character you, Character opp)
{
  int highVal;
  int playIndex = -1;
  string playName;

  if(mana <= 0)
    return;

  for(int i = 0; i < m_numCards; i++)
  {
    if(m_cardList[i].getVal(you, opp, m_cardList, m_field) > highVal && m_cardList[i].getCost() <= mana)
    {
      highVal = m_cardList[i].getVal(you, opp, m_cardList, m_field);
      playIndex = i;
    }
  }

  if(playIndex == -1)
    return;

  playName = m_cardList[playIndex].getName();
  cout<<"Play the card \"" <<playName <<"\"\n";
  removeCard(playName);
  decidePlay(mana - m_cardList[playIndex].getCost(), you, opp);
  addCard(playName);
}
