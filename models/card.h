#ifndef CARD_H
#define CARD_H

#include "character.h"

class Card: public Character
{
  string m_name;
  int m_cost;
  bool m_exh;
  float m_val;
  float (*m_effVal)(Character, Character, Card*, Card**);
  Tribe m_tr;
  bool m_taunt;
  bool m_charge;
  bool m_divShield;
  bool m_stealth;
  bool m_windfury;
  int spDmg;

  public:

  static float fl_imp_val(Character you, Character opp,  Card* hand, Card** field);
  static float abusive_val(Character you, Character opp, Card* hand, Card** field);
  static float d_wolf_val(Character you, Character opp, Card* hand, Card** field);
  static float k_juggler_val(Character you, Character opp, Card* hand, Card** field);
  static float shatt_val(Character you, Character opp, Card* hand, Card** field);
  static float voidcall_val(Character you, Character opp, Card* hand, Card** field);
  static float doom_val(Character you, Character opp, Card* hand, Card** field);
  static float leeroy_val(Character you, Character opp, Card* hand, Card** field);

  void init(string name, int hp, int atk, int cost, float val, float (*effVal)(Character, Character, Card*, Card**)=NULL, Tribe tr=na, bool exh=false);
  float valCalc();
  int getCost(){return m_cost;}
  float getVal(Character you, Character opp, Card* hand, Card** field);
  string getName(){return m_name;}
  bool is_exh(){return m_exh;}
  Tribe getTribe(){return m_tr;}
};

#endif // CARD_H
