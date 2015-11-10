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
        inFile>>inString;
        inFile>>std::ws;
        if(inString == "drew:")
        {
          getline(inFile, inString);
          hand.addCard(inString);
        }
        else if(inString == "played:" || inString == "mulliganed:")
        {
          getline(inFile, inString);
          hand.removeCard(inString);
        }
      }
    }

    hand.print();

    hand.decidePlay(mana, player, opp);

    cout<<"Next turn? 1. Yes \t 2. No" <<endl;
    cin>>input;

    if(input == 1)
    {
      inFile.close();
      inFile.open("log.txt");
      if(mana < 10)
        mana++;
    }

    else if(input == 2)
      break;

    else
    {
      cout<<"That's not an option. TERMINATING." <<endl;
      break;
    }
  }
}
