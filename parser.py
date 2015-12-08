from myconfig import *
from handler import *


expansion_sets = [
    "Basic",
    "Blackrock Mountain",
    "Classic",
    "Curse of Naxxramas",
    "Goblins vs Gnomes",
    "The Grand Tournament",
    "League of Explorers"
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
    "RESET",
    "ZONE"
]

logparser = open("ourlog.dat", 'w')
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
with open("decks/weapon_secret_test_paladin.deck") as f:
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

for c in player_model.deck.cards:
    pass
    #print(c.name)
enemy_model = player_model_pb2.PlayerModel()
enemy_model.name = "connor"
enemy_model.player_id = 1
enemy_model.hero.CopyFrom(hero)
enemy_model.deck.CopyFrom(deck)
enemy_model.max_mana = 1

message = update_pb2.Update()
#message.inst = "PRE"

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
        #message.inst = "RESET"
        #print(message.SerializeToString())
        logparser.write(line)
        logparser.write(message.__str__())

        continue
    elif "PowerTaskList.DebugPrintPower() -     TAG_CHANGE" in line:
        l = []
        l.append("Power")
        l.append(parseTag(line))
        l.append(parse_value(line))
        l.append(parse_entity(line))
        player_model, enemy_model = update_board(l, player_model, enemy_model)
        continue
        if tag in tag_change_handler and "Entity=[" in line: #checks for tag handled by parser and
            print(tag + " Detected") #Filters out Special Entities (Gamestate, User, Innkeeper, etc)
            message.Clear()
            message.inst = tag
            message.args.extend(updateEntity(line))
            message.args.append(line.split(" value=", 1)[1].strip('\n'))
            logparser.write(line)
            logparser.write(message.__str__())
        else:
            continue
    elif "[Zone] ZoneChangeList.ProcessChanges() - TRANSITIONING" in line:
        l = []
        l.append("Zone")
        l.append(parseZone(line).strip('\n'))
        l.append(parse_id(line))
        try:
            l.append(parseName(line))
        except:
            continue
        player_model, enemy_model = update_board(l, player_model, enemy_model)

    else: #line contains no valid message for updater
        pass

    #print(message.SerializeToString())
    #print(message)

