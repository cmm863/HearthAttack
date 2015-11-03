#include "character.h"

Character::Character()
{
  m_name = "";
  m_hp = 1;
  m_atk = 0;
}

Character::Character(int hp, int atk)
{
  m_hp = hp;
  m_atk = atk;
}
