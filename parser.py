from protos import deck_pb2, hero_pb2, card_pb2, player_model_pb2, weapon_pb2, minion_pb2
from helpers import *


# Create Weapon
weapon = weapon_pb2.Weapon()
weapon.name = ""
weapon.durability = 0
weapon.attack = 0

# Create Hero
hero = hero_pb2.Hero()
hero.weapon.CopyFrom(weapon)
hero.armor = 0

# Create Deck
deck = deck_pb2.Deck()
with open("decks/basic_test_warlock.deck") as f:
    lines = f.readlines()
for line in lines:
    double = False
    if line[0] == '2':
        double = True
        line = line[2:]
    card = card_pb2.Card()
    card.has_been_used = False
    card.in_hand = False
    card.name = line.strip()
    deck.cards.extend([card])
    if double:
        deck.cards.extend([card])


player_model = player_model_pb2.PlayerModel()
player_model.name = "connor"
player_model.player_id = 0
player_model.hero.CopyFrom(hero)
player_model.deck.CopyFrom(deck)
player_model.max_mana = 1

for card in player_model.deck.cards:
    print(card.name)

'''
logfile = open("/Users/connor/Library/Logs/Unity/Player.log", 'r')
loglines = follow(logfile)
for line in loglines:
    if "TRANSITIONING" in line:
        if "to FRIENDLY HAND" in line:             # Card drawn
            print("I drew: " + parseName(line))

        elif "to FRIENDLY PLAY" in line:           # Friendly Minion summoned
            print("I summoned: " + parseName(line))
        elif "to OPPOSING PLAY" in line:           # Opponent minion summoned
            print("Opp summoned: " + parseName(line))
        elif "to FRIENDLY SECRET" in line:         # Secret summoned by mad scientist
            print("Secret summoned: " + parseName(line))
    elif "from FRIENDLY HAND ->" in line:
        if "FRIENDLY DECK" in line:                # Mulliganed card
            print("I Mulliganed: " + parseName(line))
        else:                                       # Any friendly card played
            print("I Played: " + parseName(line))
    elif "from OPPOSING HAND" in line:           # Any opponent card played
        print("Opp played: " + parseName(line))
    elif "BlockType=ATTACK" in line \
            and ".DebugPrintPower()" in line: # Either you or opponent attacks
        print(parseName(line) + " is attacking " + parseTarget(line))
    elif "BlockType=POWER" in line \
            and "Target=0" not in line \
            and "PowerTaskList.DebugPrintPower()" in line:
        print(parseName(line) + " is targeting " + parseTarget(line))
'''