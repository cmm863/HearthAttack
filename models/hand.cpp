#include "hand.h"

Hand::Hand()
{
  m_numCards = 0;
  for(int i = 0; i < 14; i++)
    m_field[i] = NULL;
}

void Hand::print()
{
  cout<<"The hand is: ";
  for(int i = 0; i < m_numCards; i++)
  {
    cout<<m_cardList[i].getName() <<", ";
  }
  cout<<endl <<endl;
}

void Hand::addCard(Card* card)
{
  if(card != NULL)
  {
    m_cardList[m_numCards] = *card;
    if(m_numCards < 10)
    {
      cout<<"The card: " <<card->getName() <<" has been added to the hand." <<endl;
      m_numCards++;
    }
    else
      cout<<"Handsize too big to draw." <<endl;
  }
}

void Hand::addCard(string name)
{
  Card* card = m_deck.findCard(name);
  addCard(card);
}

void Hand::removeCard(string name)
{
  bool found = false;

  for(int i = 0; i < m_numCards; i++)
  {
    if(found || m_cardList[i].getName() == name)
    {
      if(!found)
      {
        cout<<"The card: " <<m_cardList[i].getName() <<" has been removed from the hand." <<endl;
        found = true;
      }
      if((i < m_numCards - 1))
        m_cardList[i] = m_cardList[i+1];
    }
  }

  if(found)
    m_numCards--;
}

void Hand::removeCard(int index)
{
  if(index > m_numCards)
    return;

  cout<<"The card: " <<m_cardList[index].getName() <<" has been removed from the hand." <<endl;

  for(int i = index; i < m_numCards - 1; i++)
    m_cardList[i] = m_cardList[i+1];
  m_cardList[m_numCards - 1].setName("");
}

/*
void Hand::decidePlay(int mana, Character you, Character opp)
{
  int highVal = 0;
  int playIndex = -1;
  string playName;
  int playCost;

  //cout<<"Happening??" <<endl;

  if(mana <= 0)
    return;

  cout<<"The mana value for this call to dP is: " <<mana <<endl;

  for(int i = 0; i < m_numCards; i++)
  {
   //cout<<"For loop??" <<endl;
    //cout<<m_cardList[i].getName() <<" has cost " <<m_cardList[i].getCost() <<" and val "
     //   <<m_cardList[i].getVal(you, opp, m_cardList, m_field) <<endl;
    if(m_cardList[i].getCost() <= mana && m_cardList[i].getVal(you, opp, m_cardList, m_field) > highVal)
    {
      //cout<<m_cardList[i].getName() <<" should be played..." <<endl;
      highVal = m_cardList[i].getVal(you, opp, m_cardList, m_field);
      playIndex = i;
    }
  }

  if(playIndex == -1)
    return;

  playName = m_cardList[playIndex].getName();
  playCost = m_cardList[playIndex].getCost();
  cout<<"Play the card \"" <<playName <<"\"\n";
  removeCard(playName);
  decidePlay(mana - playCost, you, opp);
  addCard(playName);
}
*/

void Hand::decidePlay(int mana, Character you, Character opp)
{
  int M = mana;     //total amount of mana for this turn

  float V[m_numCards + 1][M + 1]; 	   //value table intialized to have rows = number of cards in hand + 1
             //for 0 init, and columns = total mana avaiable this turn
  int keep[m_numCards + 1][M + 1];

  //initialize first row to 0's
  for (int i = 0; i <= M; i++)
    V[0][i] = 0;

  //computing value table
  for (int x = 1; x < m_numCards + 1; x++)     //x represents which card (item) we are looking at (ROW)
    for (int y = 0; y <= M; y++) //y represents how mana we are working with (COLUMN)

      //if the mana cost of this card is less than or equal to the mana we have to work with
      //&& the value of the card + the value in the row above (previous card) and columns back
      //equal to the mana available minues the mana of this card is > GREATER THAN the
      // value of the square above it then add the value to the table and the card to
      // the keep array
      if((m_cardList[x-1].getCost() <= y) && (m_cardList[x-1].getVal(you, opp, m_cardList, m_field) + V[x-1][y-m_cardList[x-1].getCost()] > V[x-1][y]))
      {
        V[x][y] = m_cardList[x-1].getVal(you, opp, m_cardList, m_field) + V[x-1][y-m_cardList[x-1].getCost()];
        keep[x][y] = 1;
      }

      //else the value from the square above is used and the card not added to keep colum
      else
      {
        V[x][y] = V[x-1][y];
        keep[x][y] = 0;
      }

  for(int i = 0; i < m_numCards + 1; i++)
  {
    for(int j = 0; j <= M; j++)
      cout<<V[i][j] <<", ";

    cout<<endl;
  }

  //parse keep array for cards
  int K = M;
  for(int j = m_numCards + 1; j > 0; j--) //j represents the total number of cards in hand in other words
                 //start from the bottom right corner and work backwards
    if(keep[j][K] == 1)     //if its a keep, we used the card, else statement not needed as
                 //the for loop willl naturally skip what we dont need
    {
      cout<<"Play " <<m_cardList[j - 1].getName() <<endl;
      K = K - m_cardList[j-1].getCost();
      //removeCard(j-1);
    }

}
