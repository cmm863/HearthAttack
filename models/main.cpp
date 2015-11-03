#include "Hand.h"

int main()
{
  ifstream inFile("log.txt");
  string inString;
  Hand hand;
  Character player(30, 0), opp(30, 0);
  int input;
  int mana = 1;

  while(true)
  {
    while(inFile>>inString)
    {
      if(inString == "I")
      {
        cin>>inString;
        if(inString == "drew:")
        {
          getline(inFile, inString);
          hand.addCard(inString);
        }
        else if(inString == "Played:" || inString == "Mulliganed:")
        {
          getline(inFile, inString);
          hand.removeCard(inString);
        }
      }
    }

    hand.decidePlay(mana, player, opp);

    cout<<"Next turn? 1. Yes \t 2. No" <<endl;
    cin>>input;

    if(input == 2)
    {
      inFile.close();
      inFile.open("log.txt");
      if(mana < 10)
        mana++;
      break;
    }

    else if(input != 1)
    {
      cout<<"That's not an option. TERMINATING IMMEDIATELY BOOM POW FUCK YOU" <<endl;
      break;
    }
  }
}
