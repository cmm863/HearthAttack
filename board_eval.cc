#include "card.pb.h"
#include "deck.pb.h"
#include "hero.pb.h"
#include "minion.pb.h"
#include "moveList.pb.h"
#include "player_model.pb.h"
#include "update.pb.h"
#include "weapon.pb.h"
#include <iostream>
#include <fstream>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>  /* define socket */
#include <netinet/in.h>  /* define internet socket */
#include <netdb.h>       /* define internet socket */

#define SERVER_PORT 3334        /* define a server port number */

using namespace std;

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
  
  if(aBoard.waitingplayer().hero().armor() < 20)
    aBoard.waitingplayer().hero().armor() / 5.0 - 5;
  
  return val;
}


float value_of(const com::protos::Minion& aMinion, const com::protos::Board& aBoard)
{
  float theScore = 0;
  
  theScore += valCalc(aMinion);
  
  if(aMinion.card().name() == "Zombie Chow")
    theScore += zombie_chow_val(aMinion, aBoard);
  
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
int main()
{
  com::protos::MoveList theList;
  string msg;
  float score;
  float highscore = 0;
  int highIndex = 0;
 
  int sd, ns, k;
  struct sockaddr_in server_addr = { AF_INET, htons( SERVER_PORT ) };
    struct sockaddr_in client_addr = { AF_INET };
    unsigned int client_len = sizeof( client_addr );
    char buf[32768], *host;

    /* create a stream socket */
    if( ( sd = socket( AF_INET, SOCK_STREAM, 0 ) ) == -1 )
    {
	perror( "server: socket failed" );
	exit( 1 );
    }
    
    /* bind the socket to an internet port */
    if( bind(sd, (struct sockaddr*)&server_addr, sizeof(server_addr)) == -1 )
    {
	perror( "server: bind failed" );
	exit( 1 );
    }

    /* listen for clients */
    if( listen( sd, 1 ) == -1 )
    {
	perror( "server: listen failed" );
	exit( 1 );
    }

    printf("SERVER is listening for clients to establish a connection\n");

    if( ( ns = accept( sd, (struct sockaddr*)&client_addr,
                       &client_len ) ) == -1 )
    {
        perror( "server: accept failed" );
        exit( 1 );
    }

    printf("accept() successful.. a client has connected! waiting for a message\n");

    /* data transfer on connected socket ns */
    while( (k = read(ns, buf, sizeof(buf))) != 0)
    {    
    	printf("SERVER RECEIVED MESSAGE\n");
    	msg = buf;
    	
    	theList.ParseFromString(msg);
  
	  for(int i = 0; i < theList.move_size(); i++)
	  {
	    score = score_board(theList.move(i).actionboardpair(0).board());
	    if(score > highscore)
	    {
	      highscore = score;
	      highIndex = i;
	    }
	  }
	  
	  /* Need the Action proto to be adjusted before this can be finished
	  for(int i = theList.move(highIndex).actionboardpair_length() - 1; i >= 0; i--)
	  {
	    printMove(theList.move(highIndex).actionboardpair(i).action());
	  }
	  */
    }
    close(ns);
    close(sd);
    unlink( (const char*)&server_addr.sin_addr);
 
 
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


int main()
{
    

    return(0);
}
