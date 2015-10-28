from protos import deck_pb2, hero_pb2, card_pb2, player_model_pb2, weapon_pb2, minion_pb2
import json
from pprint import pprint

expansion_sets = [
    "Basic",
    "Blackrock Mountain",
    "Classic",
    "Curse of Naxxramas",
    "Goblins vs Gnomes",
    "The Grand Tournament"
]

def suggest_play(player):
  with open("AllSets.json") as json_file:
    data = json.load(json_file)
  best=0
  best_name=""
  for card_set in expansion_sets:
	for current_card in data[card_set]:
		try:
			if current_card["cost"] > player.max_mana or current_card["cost"] <= best:
				continue
			for hand_card in player.hand:
				if hand_card.name == current_card["name"]:
					best = current_card["cost"]
					best_name = hand_card.name
					break
		except:
			pass
  print("Play: " + best_name)
