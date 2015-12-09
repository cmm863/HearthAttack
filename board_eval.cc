#include "protos/card.pb.h"
#include "protos/deck.pb.h"
#include "protos/hero.pb.h"
#include "protos/minion.pb.h"
#include "protos/moveList.pb.h"
#include "protos/player_model.pb.h"
#include "protos/update.pb.h"
#include "protos/weapon.pb.h"
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#define SERVER_PORT 3334        /* define a server port number */

using namespace std;

const float CREEPER_VAL = 3;
const float JUGGLER_VAL = 1.25;
const float PILOT_SHREDDER_VAL 3.6;
const float BELCHER_VAL = 2;
const float MINIBOT_VAL = 0.5;


void error(const char *msg)
{
    perror(msg);
    exit(1);
}

float valCalc(const com::protos::Minion& aMinion)
{
  float totalVal = 0;
  
  totalVal += aMinion.health()*1;
  totalVal += aMinion.attack()*1;

  if(aMinion.has_taunt() && aMinion.taunt())
  {
    totalVal += aMinion.health()*0.25;
    totalVal += aMinion.attack()*0.1;
  }
  if(aMinion.has_charge() && aMinion.charge())
    totalVal += aMinion.attack() * 0.75;
  if(aMinion.has_divine_shield() && aMinion.divine_shield())
    totalVal += 2;
  if(aMinion.has_stealthed_until_revealed() && aMinion.stealthed_until_revealed())
    totalVal += aMinion.attack() * .25;
  if(aMinion.has_windfury() && aMinion.windfury())
    totalVal += aMinion.attack() *.25;
  totalVal += aMinion.spell_damage();
  
  return totalVal;
}


float zombie_chow_val(const com::protos::Minion& aMinion, const com::protos::Board& aBoard)
{
  float val = 0;
  com::protos::Hero waitHero = aBoard.waitingplayer().hero();
  com::protos::Hero currHero = aBoard.currentplayer().hero();
  
  if((waitHero.armor() + waitHero.minion().health() < 20)
    (waitHero.armor() + waitHero.minion().health()) / 5.0 - 5;
  
  return val;
}

float gurubashi_val(const com::protos::Minion& aMinion)
{
  float val = 0;
    
  if(!aMinion.silenced())
    val = aMinion.health() * .33;
     
  
  return val; 
}

float swordsmith_val(const com::protos::Board& aBoard)
{
  float val = 0;
  bool cardonboard = false;
  
  for(int i = 0; i < aBoard.currentplayer().minions_size(); i++)
  {
    cardonboard = true;
    if(i > 0)
      val += .25;
  }
  
  val += cardonboard;
  
  return val;
}

float arc_golem_val(const com::protos::Board& aBoard)
{
  float val = 0;
    
  val -= (aBoard.waitingplayer().max_mana() - 9) / 2;
  
 return val; 
}


float value_of(const com::protos::Minion& aMinion, const com::protos::Board& aBoard)
{
  float theScore = 0;
  string theName = aMinion.card().name();
  
  
  theScore += valCalc(aMinion);
  
  if(theName == "Zombie Chow")
    theScore += zombie_chow_val(aMinion, aBoard);
  else if(theName == "Haunted Creeper")
    theScore += CREEPER_VAL;
  else if(theName == "Knife Juggler")
    theScore += JUGGLER_VAL;
  else if(theName == "Master Swordsmith")
    theScore += swordsmith_val(aBoard);
  else if(theName == "Arcane Golem")
    theScore += arc_golem_val(aBoard);
  else if(theName == "Piloted Shredder")
    theScore += PILOT_SHREDDER_VAL;
  else if(theName == "Gurubashi Berserker")
    theScore += gurubashi_val(aMinion);
  else if(theName == "Shielded Minibot")
    theScore += MINIBOT_VAL;
  else if(theName == "Sludge Belcher")
    theScore += BELCHER_VAL;
  
  
  return theScore;
}

float score_board(const com::protos::Board& aBoard)
{
  float theScore = 0;
  
  for(int i = 0; i < aBoard.currentplayer().minions_size(); i++)
    theScore += value_of(aBoard.currentplayer().minions(i), aBoard);
  for(int i = 0; i < aBoard.waitingplayer().minions_size(); i++)
    theScore -= value_of(aBoard.waitingplayer().minions(i), aBoard);
  
  theScore += aBoard.currentplayer().hero().armor() * (30 - aBoard.currentplayer().hero().armor()) / 30;
  theScore -= aBoard.waitingplayer().hero().armor() * (30 - aBoard.waitingplayer().hero().armor()) / 30;
 
  return theScore;
}
//Need the Action proto to be adjusted for in order to be able to print the moves
/*
void printMove(const com::protos::Action& theAction, const com::protos::Board& theBoard)
{
  com::protos::Action_Verb verb = theAction.verb();
  com::protos::PlayerModel playerOne = theBoard.currentplayer();
  com::protos::PlayerModel playerTwo = theBoard.waitingplayer();
  com::protos::Card theCard; 
  theCard = theAction.card(); 
   
  if(verb == 1)
  {
    cout<<"Play the card: " <<theCard <<" targeting " 
  }
}
*/
int main(int argc, char *argv[])
{
  com::protos::MoveList theList;
  string msg;
  float score;
  float highscore = 0;
  int highIndex = 0;
  int sockfd, newsockfd, portno;
  socklen_t clilen;
  char buffer[256];
  struct sockaddr_in serv_addr, cli_addr;
  int n;
  if (argc < 2) {
    fprintf(stderr,"ERROR, no port provided\n");
    exit(1);
  }
  sockfd = socket(AF_INET, SOCK_STREAM, 0);
  if (sockfd < 0) 
    error("ERROR opening socket");
  bzero((char *) &serv_addr, sizeof(serv_addr));
  portno = atoi(argv[1]);
  serv_addr.sin_family = AF_INET;
  serv_addr.sin_addr.s_addr = INADDR_ANY;
  serv_addr.sin_port = htons(portno);
  if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) 
    error("ERROR on binding");
  listen(sockfd,5);
  clilen = sizeof(cli_addr);
  newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
  if (newsockfd < 0) 
    error("ERROR on accept");
  bzero(buffer,256);
  n = read(newsockfd,buffer,255);
  if (n < 0) error("ERROR reading from socket");
  printf("Here is the message: %s\n",buffer);
  n = write(newsockfd,"I got your message",18);
  if (n < 0) error("ERROR writing to socket");
  close(newsockfd);
  close(sockfd);
 
  return 0;
}

/************************************************************************/
/*   PROGRAM NAME: server1.c  (works with client.c)                     */
/*                                                                      */
/*   Server creates a socket to listen for the connection from Client   */
/*   When the communication established, Server echoes data from Client */
/*   and writes them back.                                              */
/*                                                                      */
/*   Using socket() to create an endpoint for communication. It         */
/*   returns socket descriptor. Stream socket (SOCK_STREAM) is used here*/
/*   as opposed to a Datagram Socket (SOCK_DGRAM)                       */  
/*   Using bind() to bind/assign a name to an unnamed socket.           */
/*   Using listen() to listen for connections on a socket.              */
/*   Using accept() to accept a connection on a socket. It returns      */
/*   the descriptor for the accepted socket.                            */
/*                                                                      */
/*   To run this program, first compile the server_ex.c and run it      */
/*   on a server machine. Then run the client program on another        */
/*   machine.                                                           */
/*                                                                      */
/*   COMPILE:        gcc server1.c -o server1 -lnsl                     */
/*                                                                      */
/************************************************************************/
