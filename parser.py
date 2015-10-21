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
    temp_card = card_pb2.Card()
    temp_card.has_been_used = False
    temp_card.in_hand = False
    temp_card.name = line.strip()
    deck.cards.extend([temp_card])
    if double:
        deck.cards.extend([temp_card])


player_model = player_model_pb2.PlayerModel()
player_model.name = "connor"
player_model.player_id = 0
player_model.hero.CopyFrom(hero)
player_model.deck.CopyFrom(deck)
player_model.max_mana = 1


logfile = open("/Users/connor/Library/Logs/Unity/Player.log", 'r')
loglines = follow(logfile)
for line in loglines:
    if "TRANSITIONING" in line:
        ## Draw Card
        if "to FRIENDLY HAND" in line:
            card_name = parseName(line)             # Get card name
            for deck_card in player_model.deck.cards:    # Cycle cards
                if deck_card.name == card_name:          # If card name matches
                    # If the card matches and hasn't been used/drawn
                    if deck_card.has_been_used is False and deck_card.in_hand is False:
                        deck_card.in_hand = True         # Set the card to be in hand
                        player_model.hand.extend([deck_card])
                        print(player_model.__str__())
                        break
                    else:
                        continue

        ## Summon Friendly Minion
        elif "to FRIENDLY PLAY" in line:           # Friendly Minion summoned
            card_name = parseName(line)

        elif "to OPPOSING PLAY" in line:           # Opponent minion summoned
            print("Opp summoned: " + parseName(line))
        elif "to FRIENDLY SECRET" in line:         # Secret summoned by mad scientist
            print("Secret summoned: " + parseName(line))
    ## Card played or thrown back
    elif "from FRIENDLY HAND ->" in line:
        card_name = parseName(line)
        for deck_card in player_model.deck.cards:
            if deck_card.name == card_name:
                # If the card has been drawn but not used
                if deck_card.in_hand is True and deck_card.has_been_used is False:
                    deck_card.in_hand = False
                    # If card is played and not returned to deck
                    if "FRIENDLY DECK" not in line:
                        deck_card.has_been_used = True
        i = 0
        for hand_card in player_model.hand:
            if hand_card.name == card_name:
                del player_model.hand[i]
                break
            i += 1
    elif "from OPPOSING HAND" in line:           # Any opponent card played
        print("Opp played: " + parseName(line))
    elif "BlockType=ATTACK" in line \
            and ".DebugPrintPower()" in line: # Either you or opponent attacks
        print(parseName(line) + " is attacking " + parseTarget(line))
    elif "BlockType=POWER" in line \
            and "Target=0" not in line \
            and "PowerTaskList.DebugPrintPower()" in line:
        print(parseName(line) + " is targeting " + parseTarget(line))