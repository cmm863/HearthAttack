#include "deck.h"

Deck::Deck()
{
  m_numCards = 30;
  m_cardList[0].init("Flame Imp", 2, 3, 1, 0, Card::fl_imp_val, demon);
  m_cardList[1].init("Flame Imp", 2, 3, 1, 0, Card::fl_imp_val, demon);
  m_cardList[2].init("Voidwalker", 3, 1, 1, 0, NULL, demon);
  m_cardList[3].init("Voidwalker", 3, 1, 1, 0, NULL, demon);
  m_cardList[4].init("Leper Gnome", 1, 2, 1, 1);
  m_cardList[5].init("Leper Gnome", 1, 2, 1, 1);
  m_cardList[6].init("Abusive Sergeant", 1, 2, 1, 0, Card::abusive_val);
  m_cardList[7].init("Abusive Sergeant", 1, 2, 1, 0, Card::abusive_val);
  m_cardList[8].init("Dire Wolf Alpha", 2, 2, 2, 0, Card::d_wolf_val, beast);
  m_cardList[9].init("Dire Wolf Alpha", 2, 2, 2, 0, Card::d_wolf_val, beast);
  m_cardList[10].init("Haunted Creeper", 2, 1, 2, 2.5, NULL, beast);
  m_cardList[11].init("Haunted Creeper", 2, 1, 2, 2.5, NULL, beast);
  m_cardList[12].init("Knife Juggler", 2, 3, 2, .5, Card::k_juggler_val);
  m_cardList[13].init("Knife Juggler", 2, 3, 2, .5, Card::k_juggler_val);
  m_cardList[14].init("Harvest Golem", 3, 2, 3, 2.25, NULL, mech);
  m_cardList[15].init("Harvest Golem", 3, 2, 3, 2.25, NULL, mech);
  m_cardList[16].init("Imp Gang Boss", 4, 2, 3, 1.25, NULL, demon);
  m_cardList[17].init("Imp Gang Boss", 4, 2, 3, 1.25, NULL, demon);
  m_cardList[18].init("Scarlet Crusader", 1, 3, 3, 0);
  m_cardList[19].init("Scarlet Crusader", 1, 3, 3, 0);
  m_cardList[20].init("Shattered Sun Cleric", 2, 3, 3, 0, Card::shatt_val);
  m_cardList[21].init("Shattered Sun Cleric", 2, 3, 3, 0, Card::shatt_val);
  m_cardList[22].init("Piloted Shredder", 3, 4, 4, 4, NULL, mech);
  m_cardList[23].init("Piloted Shredder", 3, 4, 4, 4, NULL, mech);
  m_cardList[24].init("Voidcaller", 4, 3, 4, 0, Card::voidcall_val, demon);
  m_cardList[25].init("Voidcaller", 4, 3, 4, 0, Card::voidcall_val, demon);
  m_cardList[26].init("Doomguard", 7, 5, 5, 0, Card::doom_val, demon, false);
  m_cardList[27].init("Doomguard", 7, 5, 5, 0, Card::doom_val, demon, false);
  m_cardList[28].init("Leeroy Jenkins", 2, 6, 5, 0, Card::leeroy_val);
  m_cardList[29].init("Loatheb", 5, 5, 5, 1);
}

Card* Deck::findCard(string name)
{
  int index;

  for(int i = 0; i < 30; i++)
  {
    if(m_cardList[i].getName() == name)
    {
    index = i;
    break;
    }
  }

  return &(m_cardList[index]);
}
