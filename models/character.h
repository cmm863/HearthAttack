#ifndef CHARACTER_H
#define CHARACTER_H

#include "ai.h"

class Character
{
  protected:

  string m_name;
  int m_hp;
  int m_atk;

  public:

  Character();

  Character(int hp, int atk);

  int getHP(){return m_hp;}
  int getAtk(){return m_atk;}
};

#endif // CHARACTER_H
