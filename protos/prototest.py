import card_pb2
import deck_pb2
import minion_pb2
import weapon_pb2
import hero_pb2
import player_model_pb2

# Create a deck of 2 cards
## Tests card.proto and deck.proto
deck = deck_pb2.Deck()      # Create deck instance

# Create card addition instance
card_one = deck.cards.add()     
card_one.name = "Blood Imp"     # Modify card attributes
card_one.has_been_used = False
card_one.in_hand = True

# Create second card instance
card_two = deck.cards.add()     
card_two.name = "Elven Archer"  # Modify card attributes
card_two.in_hand = True
card_two.has_been_used = True

print("--DECK TEST--")
print(deck.__str__())

# Create Weapon
## Tests weapon.proto
weapon = weapon_pb2.Weapon()
weapon.name = "Gorehowl"
weapon.durability = 1
weapon.attack = 7


# Create Hero
## Tests hero.proto and weapon.proto
hero = hero_pb2.Hero()
hero.weapon.CopyFrom(weapon)
hero.armor = 2

print("--HERO TEST--")
print(hero.__str__())

# Create Minion
## Tests minion.proto
minion_one = minion_pb2.Minion()
minion_one.has_attacked = True
minion_one.health = 2
minion_one.max_health = 4
minion_one.attack = 3
minion_one.spell_damage = 0
minion_one.tribe = minion_pb2.Minion.MECH


# Create Player Model
## Tests deck, card, weapon, minion, hero, and player_model protos
player_model = player_model_pb2.PlayerModel()
player_model.name = "Connor"
player_model.player_id = 2
player_model.hero.CopyFrom(hero)
player_model.deck.CopyFrom(deck)
player_model.mana = 3
player_model.max_mana = 5
player_model.deck_pos = 9
player_model.fatigue_damage = 0
player_model.overload = 0
player_model.num_cards_used = 2
player_model.hand.extend([card_one, card_two])
player_model.minions.extend([minion_one])

print("--PLAYER MODEL TEST--")
print(player_model.__str__())