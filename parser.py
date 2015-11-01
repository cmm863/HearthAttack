from protos import deck_pb2, hero_pb2, card_pb2, player_model_pb2, weapon_pb2, minion_pb2, update_pb2
from helpers import *
from myconfig import *
import json
from pprint import pprint
import suggestion
import time


expansion_sets = [
    "Basic",
    "Blackrock Mountain",
    "Classic",
    "Curse of Naxxramas",
    "Goblins vs Gnomes",
    "The Grand Tournament"
]
hero_list = [
    ["Malfurion Stormrage", "Druid"],
    ["Rexxar", "Hunter"],
    ["Jaina Proudmoore", "Mage"],
    ["Uther Lightbringer", "Paladin"],
    ["Anduin Wrynn", "Priest"],
    ["Valeera Sanguinar", "Rogue"],
    ["Thrall", "Shaman"],
    ["Gul'dan", "Warlock"],
    ["Garrosh Hellscream", "Warrior"],
    ["Alleria Windrunner", "Hunter"],
    ["Medivh", "Mage"],
    ["Magni Bronzebeard", "Warrior"]
]
hero_power_list = [
    "Shapeshift",
    "Steady Shot",
    "Fireblast",
    "Reinforce",
    "Lesser Heal",
    "Dagger Mastery",
    "Totemic Call",
    "Life Tap",
    "Armor Up!"
]

tag_change_handler = [
    "DAMAGE",
    "NUM_ATTACKS_THIS_TURN",
    "EXHAUSTED",
    "RESET"
]

with open("AllSets.json") as json_file:
    data = json.load(json_file)

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

enemy_model = player_model_pb2.PlayerModel()
enemy_model.name = "connor"
enemy_model.player_id = 1
enemy_model.hero.CopyFrom(hero)
enemy_model.deck.CopyFrom(deck)
enemy_model.max_mana = 1

message = update_pb2.Update()
message.inst = "PRE"

'''
board = board_model_pb2.BoardModel()
board.currentPlayer.CopyFrom(player_model)
board.waitingPlayer.CopyFrom(enemy_model)
'''

played_first = True
cards_drawn = 0
turns_ended = 0

logfile = open(configFile, 'r')
print("Begin - Backtrace")
logfile.seek(getSeek(logfile))
print("Backtrace Complete")

while not ("tag=PLAYSTATE" in line and "value=LOST" in line):
    line = '-'
    while True:
        line = ''.join((line, logfile.readline()))
        if not line[-1:] == '\n':
            time.sleep(0.1)
        else:
            break
    if "[Power] GameState" in line:  ##Ignores redundant log information
        continue
    elif "CREATE_GAME" in line:
        message.Clear()
        message.inst = "RESET"
        #print(message.SerializeToString())
        print(message)       
        continue
    if "TRANSITIONING" in line:
        ## Draw Card
        if "to FRIENDLY HAND" in line:
            card_name = parseName(line)             # Get card name
            print("Player Drew: " + card_name)
            cards_drawn += 1			#count drawn cards
            if cards_drawn >= 4:
                suggestion.suggest_play(player_model)  #print suggested play to user
                continue
            for deck_card in player_model.deck.cards:    # Cycle cards
                if deck_card.name == card_name:          # If card name matches
                    # If the card matches and hasn't been used/drawn
                    if deck_card.has_been_used is False and deck_card.in_hand is False:
                        deck_card.in_hand = True         # Set the card to be in hand
                        player_model.hand.extend([deck_card])
                    #else:
                        #continue

        ## Summon Friendly Minion
        elif "to FRIENDLY PLAY" in line:           # Friendly Minion summoned
            card_name = parseName(line)
            print("Player played: " + card_name)
            is_hero = False
            is_hero_power = False
            for hero in hero_list:
                if card_name == hero[0]:
                    hero = hero_pb2.Hero()
                    hero.weapon.CopyFrom(weapon)
                    hero.armor = 0
                    hero_minion = minion_pb2.Minion()
                    hero_minion.max_health = 30
                    hero_card = card_pb2.Card()
                    hero_card.name = card_name
                    hero_card.has_been_used = True
                    hero_card.in_hand = False
                    hero_minion.card.CopyFrom(hero_card)
                    hero.minion.CopyFrom(hero_minion)
                    player_model.hero.CopyFrom(hero)
                    #board_model.current_player.CopyFrom(player_model)
                    is_hero = True
                    break
            for hp in hero_power_list:
                if card_name == hp:
                    is_hero_power = True
                    break
            if is_hero or is_hero_power:
                continue
            for card_set in expansion_sets:
                for current_card in data[card_set]:
                    if card_name == current_card["name"]:
                        try:
                            minion = minion_pb2.Minion()
                            minion.has_attacked = False
                            minion.health = current_card["health"]
                            minion.max_health = current_card["health"]
                            minion.attack = current_card["attack"]
                            minion.spell_damage = 0
                            minion.tribe = minion_pb2.Minion.NONE
                            minion_card = card_pb2.Card()
                            minion_card.has_been_used = True
                            minion_card.in_hand = False
                            minion_card.name = card_name
                            minion.card.CopyFrom(minion_card)
                            player_model.minions.extend([minion])
                            #board_model.current_player.CopyFrom(player_model)
                        except:
                            pass

        elif "to OPPOSING PLAY" in line:           # Opponent minion summoned
            print("Opp summoned: " + parseName(line))
        elif "to FRIENDLY SECRET" in line:         # Secret summoned by mad scientist
            print("Secret summoned: " + parseName(line))
        else:
            continue
    ## Card played or thrown back
    elif "from FRIENDLY HAND ->" in line:
        try:
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
        except:
            pass
    elif "from OPPOSING HAND" in line:           # Any opponent card played
        print("Opp played: " + parseName(line))
    elif "BlockType=ATTACK" in line \
            and ".DebugPrintPower()" in line: # Either you or opponent attacks
        print(parseName(line) + " is attacking " + parseTarget(line))
    elif "BlockType=POWER" in line \
            and "Target=0" not in line \
            and "PowerTaskList.DebugPrintPower()" in line:
        print(parseName(line) + " is targeting " + parseTarget(line))
    elif "tag=CURRENT_PLAYER value=0" in line \
            and "PowerTaskList.DebugPrintPower()" in line:
        print("END TURN") # TURN ENDS HERE
        turns_ended += 1
        if turns_ended == 1:
            for card in player_model.hand:
                if card.name == "The Coin":		#The Coin indicates playing second
                    played_first = False
        if (turns_ended % 2) == played_first:
            player_model.max_mana = min(player_model.max_mana + 1, 10) #increase mana up to 10
        '''
                           ------MAIN BOARD UPDATE ROUTINE------
        This call marks an update to some variable on blizzards side.  While not all of these
        are useful to us, this also includes the changes in health, status, power, mana cost,
        available mana, max mana, special flags, board position, and virtually everything else
        that doesn't involve moving a card between zones.

        Virtually all board updates should be in here with the exception of Move(Play/Destroy/Recall),
        Draw, Create, and End Turn.
        '''

        '''
        I generated a simple log consisting of just a minion (Cogmaster) attacking a hero (Jaina).  I'll
        upload the rest of the log with it, but the three critical lines we need to parse are below.
        [Power] PowerTaskList.DebugPrintPower() -     TAG_CHANGE Entity=[name=Jaina Proudmoore id=36 zone=PLAY zonePos=0 cardId=HERO_08 player=2] tag=DAMAGE value=1

        [Power] PowerTaskList.DebugPrintPower() -     TAG_CHANGE Entity=[name=Cogmaster id=35 zone=PLAY zonePos=1 cardId=GVG_013 player=1] tag=NUM_ATTACKS_THIS_TURN value=1

        [Power] PowerTaskList.DebugPrintPower() -     TAG_CHANGE Entity=[name=Cogmaster id=35 zone=PLAY zonePos=1 cardId=GVG_013 player=1] tag=EXHAUSTED value=1
        '''
    elif "PowerTaskList.DebugPrintPower() -     TAG_CHANGE" in line:
        tag = parseTag(line)
        if tag in tag_change_handler and "Entity=[" in line: #checks for tag handled by parser and
            print(tag + " Detected") #Filters out Special Entities (Gamestate, User, Innkeeper, etc)
            message.Clear()
            message.inst = tag
            message.args.extend(updateEntity(line))
            message.args.append(line.split(" value=", 1)[1].strip('\n'))
        else:
            continue
    else: #line contains no valid message for updater
        continue
    #print(message.SerializeToString())
    print(message)

