#include "card.h"

void Card::init(string name, int hp, int atk, int cost, float val, float (*effVal)(Character, Character, Card*, Card**), Tribe tr, bool exh)
{
  m_name = name;
  m_hp = hp;
  m_atk = atk;
  m_cost = cost;
  m_val = val;
  m_effVal = effVal;
  m_tr = tr;
  m_exh = exh;
  spDmg = 0;
  m_taunt = false;
  m_charge = false;
  m_divShield = false;
  m_windfury = false;
  m_stealth = false;

  if(name == "Voidwalker")
    m_taunt = true;
  else if(name == "Scarlet Crusader")
    m_divShield  = true;
  else if(name == "Leeroy Jenkins")
    m_charge = true;
}

float Card::getVal(Character you, Character opp, Card* hand, Card** field)
{
  return valCalc() + m_val + m_effVal(you, opp, hand, field);
}


float Card::valCalc ()
{
  float totalVal = 0;

  totalVal += m_hp*1;
  totalVal += m_atk*1;

  if(m_taunt)
  {
    totalVal += m_hp*0.5;
    totalVal += m_atk * 0.1;
  }
  if(m_charge)
    totalVal += m_atk * 0.75;
  if(m_divShield)
    totalVal += 2;
  if(m_stealth)
    totalVal += m_atk * .25;
  if(m_windfury)
    totalVal += m_atk *.25;
  totalVal += spDmg;

  return totalVal;
}


float Card::fl_imp_val(Character you, Character opp, Card* hand, Card** field)
{
  if(you.getHP() < 10)
  {
    if(you.getHP() <= 3)
      return -10;
    else
      return -3;
  }

  return 0;
}

float Card::abusive_val(Character you, Character opp, Card* hand, Card** field)
{
  for(int i = 0; i < 7; i++)
  {
    if(field[i] != NULL)
    {
      if(!field[i]->m_exh)
      {
        return 2;
        break;
      }
    }
  }

  return 0;
}

float Card::d_wolf_val(Character you, Character opp, Card* hand, Card** field)
{
  bool found = false;
  float retVal = 0;

  for(int i = 0; i < 7; i++)
  {
    if(field[i] != NULL)
    {
      if(!field[i]->m_exh)
      {
        retVal++;
        if(found)
          break;
        found = true;
      }
    }
  }

  return retVal;
}

float Card::k_juggler_val(Character you, Character opp, Card* hand, Card** field)
{
  //More complicated will work on later
  return 0;
}

float Card::shatt_val(Character you, Character opp, Card* hand, Card** field)
{
  for(int i = 0; i < 7; i++)
  {
    if(field[i] != NULL)
    {
      return 2;
      break;
    }
  }

  return 0;
}

float Card::voidcall_val(Character you, Character opp, Card* hand, Card** field)
{
  float valAdd = 0;
  int count = 0;
  bool found = false;

  for(int i = 0; i < 10; i++)
  {
    if(hand[i].m_tr == demon)
    {
      if(hand[i].m_name != "Voidcaller" || found)
      {
        valAdd += .5 * hand[i].getVal(you, opp, hand, field);
        count++;
      }
      else
        found = true;
    }
  }

  if(count != 0)
    valAdd /= count;

  return valAdd;
}

float Card::doom_val(Character you, Character opp, Card* hand, Card** field)
{
  float retVal = 0;

  for(int i = 0; i < 2; i++)
  {
    if(hand[i].m_name != "")
      retVal - 3;
  }

  return retVal;
}

float Card::leeroy_val(Character you, Character opp, Card* hand, Card** field)
{
  float retVal = -4;
  int totAtk = 0;
  for(int i = 0; i < 7; i++)
  {
    if(field[i] != NULL)
      totAtk += field[i]->m_atk;
  }

  totAtk += 6;

  if(totAtk >= opp.getHP())
    retVal = 10000;

  return retVal;
}
