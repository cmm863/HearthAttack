__author__ = 'connor'
from protos import deck_pb2, hero_pb2, card_pb2, player_model_pb2, weapon_pb2, minion_pb2, update_pb2
import json
from helpers import *

expansion_sets = [
    "Basic",
    "Blackrock Mountain",
    "Classic",
    "Curse of Naxxramas",
    "Goblins vs Gnomes",
    "The Grand Tournament",
    "League of Explorers"
]

mechanics = [
    "Taunt",
    "Battlecry",
    "Charge"
]


def update_board(l, home_m, enemy_m):
    opponent = []
    home_team = []

    if l[0] == "Zone":
        # l[0] = TYPE
        # l[1] = ZONE
        # l[2] = ID
        # l[3] = NAME
        if l[1] == "FRIENDLY HAND":
            # Draw Card
            print("Drew: " + l[3])
            card = get_card_from_deck(home_m, l[3])
            if card is None:
                if len(l[3]) > 0:
                    card = card_pb2.Card()
                    card.has_been_used = False
                    card.in_hand = False
                    card.name = l[3]
                    card.id = int(l[2])
                    home_m.hand.extend([card])
                return home_m, enemy_m
            card.in_hand = True
            card.id = int(l[2])
            home_m.hand.extend([card])
            home_m.deck.cards.remove(card)
        elif l[1] == "OPPOSING HAND":
            # Card returned to opp hand
            pass
        elif l[1] == "FRIENDLY PLAY (Hero)":
            # Your Hero
            pass
        elif l[1] == "FRIENDLY PLAY (Hero Power)":
            # Your Hero Power
            pass
        elif l[1] == "OPPOSING PLAY (Hero)":
            # Opp Hero
            pass
        elif l[1] == "OPPOSING PLAY (Hero Power)":
            # Opp Hero Power
            pass
        elif l[1] == "FRIENDLY DECK":
            # Returned card to deck
            print("Mulliganed: " + l[3])
            card = get_card_from_hand(home_m, l[2])
            card.in_hand = False
            home_m.hand.remove(card)
            home_m.deck.cards.extend([card])
        elif l[1] == "FRIENDLY PLAY":
            # Friendly Minion
            print(l[3])
            card = get_card_from_hand(home_m, l[2])
            if card is None:
                if len(l[3]) > 0:
                    print(l[3])
                    card = card_pb2.Card()
                    card.has_been_used = False
                    card.in_hand = False
                    card.name = l[3]
                    card.id = int(l[2])
                    print(card)
                    minion = return_minion(card)
                    print(minion)
                    home_m.minions.extend([minion])
                return home_m, enemy_m
            print("Summoned: " + l[3])
            card.in_hand = False
            home_m.hand.remove(card)
            minion = return_minion(card)
            home_m.minions.extend([minion])
            #output_minions(home_m)
        elif l[1] == "OPPOSING PLAY":
            # Opp Minion
            print("Enemy Summoned: " + l[3])
            card = card_pb2.Card()
            card.has_been_used = False
            card.in_hand = False
            card.name = l[3]
            card.id = int(l[2])
            minion = return_minion(card)
            enemy_m.minions.extend([minion])
            #output_minions(enemy_m)
        elif l[1] == "FRIENDLY SECRET":
            # Secret on your side
            print(l[3])
            pass
        elif l[1] == "OPPOSING SECRET":
            # Secret on their side
            pass
        elif l[1] == "FRIENDLY GRAVEYARD":
            # Card is gone
            minion = None
            print(l[3] + " died")
            for m in home_m.minions:
                if m.card.id == int(l[2]):
                    minion = m
                    break
            if minion is not None:
                home_m.minions.remove(minion)
            #output_minions(home_m)
        elif l[1] == "OPPOSING GRAVEYARD":
            # Opp card is gone
            minion = None
            for m in enemy_m.minions:
                if m.card.id == int(l[2]):
                    minion = m
                    break
            if minion is not None:
                enemy_m.minions.remove(minion)
                print(l[3] + " died")
                #output_minions(enemy_m)
        elif l[1] == "FRIENDLY PLAY (Weapon)":
            # Added weapon to your side
            print(return_weapon(l[3]))

            pass
        elif l[1] == "OPPOSING PLAY (Weapon)":
            # Added weapon to opp side
            pass
        elif l[1] == "":
            # Spell Played
            pass
    elif l[0] == "Power":
        # l[0] = TYPE
        # l[1] = TAG
        # l[2] = VALUE
        # l[3] = ENTITY
        ## Occasionally there is no value but only at the beginning
        if l[1] == "PLAYER_ID":
            # Lets us know player ID and name of player Entity
            if "MiRaGe" in l[3]:
                home_m.player_id = int(l[2])
                home_m.name = l[3]
            else:
                enemy_m.player_id = int(l[2])
                enemy_m.name = l[3]
            pass
        elif l[1] == "TURN":
            # value = turn number
            print("TURN: " + l[2])
            pass
        elif l[1] == "RESOURCES":
            if l[3] == "MiRaGe":
                home_m.mana = int(l[2])
            else:
                enemy_m.mana = int(l[2])
        elif l[1] == "DAMAGE":
            print("Damage: " + l[2] + " on " + l[3])
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                minion.damage = int(l[2])
                minion.health = minion.max_health - minion.damage
        elif l[1] == "ARMOR":
            if home_m.player_id == int(parse_player_id(l[3])):
                home_m.hero.armor = int(l[2])
            else:
                enemy_m.hero.armor = int(l[2])
        elif l[1] == "HEALTH":
            print("Health change: " + l[3])
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if minion.max_health < int(l[2]):
                    minion.health = minion.health + int(l[2]) - minion.max_health
                minion.max_health = int(l[2])
                if minion.max_health < minion.health:
                    minion.health = minion.max_health
                minion.health = minion.max_health - minion.damage
        elif l[1] == "ATK":
            print("Attack change: " + l[3])
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                minion.attack = int(l[2])
        elif l[1] == "FROZEN":
            print("Frozen: " + parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if int(l[2]) == 1:
                    minion.frozen = True
                else:
                    minion.frozen = False
        elif l[1] == "SILENCED":
            print("Silenced: " +  parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                minion.silenced = True
        elif l[1] == "TAUNT":
            print("Taunted: " + parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if int(l[2]) == 1:
                    minion.taunt = True
                else:
                    minion.taunt = False
        elif l[1] == "CHARGE":
            print("Charged: " + parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if int(l[2]) == 1:
                    minion.charge = True
                else:
                    minion.charge = False
        elif l[1] == "WINDFURY":
            print("Windfurious: " + parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if int(l[2]) == 1:
                    minion.windfury = True
                else:
                    minion.windfury = False
        elif l[1] == "DEATHRATTLE":
            print("Deathrattled: " + parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if int(l[2]) == 1:
                    minion.deathrattle = True
                else:
                    minion.deathrattle = False
        elif l[1] == "DIVINE_SHIELD":
            print("Divined: " + parseName(l[3]))
            if home_m.player_id == int(parse_player_id(l[3])):
                minion = get_minion_from_field(home_m, parse_id(l[3]))
            else:
                minion = get_minion_from_field(enemy_m, parse_id(l[3]))
            if minion is not None:
                if int(l[2]) == 1:
                    minion.divine_shield = True
                else:
                    minion.divine_shield = False
    return home_m, enemy_m


def output_hand(home_m):
    print("Hand:")
    for card in home_m.hand:
        print("\t" + card.name)


def output_deck(home_m):
    print("Deck:")
    for card in home_m.deck.cards:
        print("\t" + card.name)


def output_minions(home_m):
    print("Minions:")
    for m in home_m.minions:
        print(m)


def get_card_from_hand(home_m, card_id):
    for card in home_m.hand:
        if card.id == int(card_id):
            return card


def get_card_from_deck(home_m, card_name):
    for card in home_m.deck.cards:
        if card.name == card_name:
            return card


def get_minion_from_field(home_m, card_id):
    for minion in home_m.minions:
        if minion.card.id == int(card_id):
            return minion
    return None


def return_minion(card):
    minion = minion_pb2.Minion()
    minion.card.CopyFrom(card)
    with open("AllSets.json") as json_file:
        data = json.load(json_file)
    for expansion in expansion_sets:
        for card_json in data[expansion]:
            if card_json["name"] == card.name:
                minion.max_health = card_json["health"]
                minion.health = minion.max_health
                minion.attack = card_json['attack']
                minion.damage = 0
                try:
                    for mechanic in card_json['mechanics']:
                        if mechanic == "Taunt":
                            minion.taunt = True
                        elif mechanic == "Divine Shield":
                            minion.divine_shield = True
                        elif mechanic == "Windfury":
                            minion.windfury = True
                        elif mechanic == "Charge":
                            minion.charge = True
                        elif mechanic == "Deathrattle":
                            minion.deathrattle = True
                except:
                    pass
                try:
                    race = card_json['race']
                    if race == "Beast":
                        minion.tribe = minion_pb2.Minion.BEAST
                    elif race == "Mech":
                        minion.tribe = minion_pb2.Minion.MECH
                    elif race == "Murloc":
                        minion.tribe = minion_pb2.Minion.MURLOC
                    elif race == "Pirate":
                        minion.tribe = minion_pb2.Minion.PIRATE
                    elif race == "Demon":
                        minion.tribe = minion_pb2.Minion.DEMON
                    elif race == "Dragon":
                        minion.tribe = minion_pb2.Minion.DRAGON
                    elif race == "Totem":
                        minion.tribe = minion_pb2.Minion.TOTEM
                except:
                    minion.tribe = minion_pb2.Minion.NONE
                return minion


def return_weapon(name):
    weapon = weapon_pb2.Weapon()
    weapon.name = name
    with open("AllSets.json") as json_file:
        data = json.load(json_file)
    for expansion in expansion_sets:
        for card_json in data[expansion]:
            if card_json['name'] == weapon.name:
                weapon.durability = card_json['durability']
                weapon.attack = card_json['attack']
    return weapon