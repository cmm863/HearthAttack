package com.hearthsim.test.groovy.card.classic.spell

import com.hearthsim.card.CharacterIndex
import com.hearthsim.card.basic.minion.KoboldGeomancer
import com.hearthsim.card.basic.minion.Voidwalker
import com.hearthsim.card.basic.minion.WarGolem
import com.hearthsim.card.classic.minion.common.FaerieDragon
import com.hearthsim.card.classic.minion.common.StranglethornTiger
import com.hearthsim.card.classic.spell.common.DeadlyShot
import com.hearthsim.model.BoardModel
import com.hearthsim.test.groovy.card.CardSpec
import com.hearthsim.test.helpers.BoardModelBuilder
import com.hearthsim.util.tree.HearthTreeNode
import com.hearthsim.util.tree.RandomEffectNode

import static com.hearthsim.model.PlayerSide.CURRENT_PLAYER
import static com.hearthsim.model.PlayerSide.WAITING_PLAYER

class DeadlyShotSpec extends CardSpec {

    HearthTreeNode root
    BoardModel startingBoard

    def setup() {
        startingBoard = new BoardModelBuilder().make {
            currentPlayer {
                hand([DeadlyShot])
                field([[minion: Voidwalker], [minion: WarGolem]])
                mana(10)
            }
            waitingPlayer {
                field([[minion: FaerieDragon],[minion: StranglethornTiger]])
            }
        }

        root = new HearthTreeNode(startingBoard)
    }

    def "returned node is normal for only one target"() {
        startingBoard.removeMinion(WAITING_PLAYER, CharacterIndex.MINION_1);

        def copiedBoard = startingBoard.deepCopy()
        def theCard = root.data_.getCurrentPlayer().getHand().get(0)
        def ret = theCard.useOn(CURRENT_PLAYER, CharacterIndex.HERO, root)
        ret = ret.getChildren().get(0);

        expect:
        ret != null
        ret instanceof HearthTreeNode
        !(ret instanceof RandomEffectNode)

        assertBoardDelta(copiedBoard, ret.data_) {
            currentPlayer {
                removeCardFromHand(DeadlyShot)
                mana(7)
                numCardsUsed(1)
            }
            waitingPlayer {
                removeMinion(CharacterIndex.MINION_1)
            }
        }
    }

    def "returned node is RNG for two or more targets"() {
        def copiedBoard = startingBoard.deepCopy()
        def theCard = root.data_.getCurrentPlayer().getHand().get(0)
        def ret = theCard.useOn(CURRENT_PLAYER, CharacterIndex.HERO, root)

        expect:
        ret != null
        ret instanceof RandomEffectNode
        ret.numChildren() == 2

        assertBoardDelta(copiedBoard, ret.data_) {
            currentPlayer {
                numCardsUsed(1)
            }
        }
    }

    def "returned node is null for no targets"() {
        startingBoard.removeMinion(WAITING_PLAYER, CharacterIndex.MINION_1);
        startingBoard.removeMinion(WAITING_PLAYER, CharacterIndex.MINION_1);

        def copiedBoard = startingBoard.deepCopy()
        def theCard = root.data_.getCurrentPlayer().getHand().get(0)
        def ret = theCard.useOn(CURRENT_PLAYER, CharacterIndex.HERO, root)

        expect:
        ret == null
    }

    def "hits enemy minions"() {
        def theCard = root.data_.getCurrentPlayer().getHand().get(0)
        def ret = theCard.useOn(CURRENT_PLAYER, CharacterIndex.HERO, root)

        expect:
        ret != null
        ret instanceof RandomEffectNode
        ret.numChildren() == 2

        def copiedBoard = ret.data_.deepCopy()

        HearthTreeNode child0 = ret.getChildren().get(0);
        assertBoardDelta(copiedBoard, child0.data_) {
            currentPlayer {
                removeCardFromHand(DeadlyShot)
                mana(7)
            }
            waitingPlayer {
                removeMinion(CharacterIndex.MINION_1)
            }
        }

        HearthTreeNode child1 = ret.getChildren().get(1);
        assertBoardDelta(copiedBoard, child1.data_) {
            currentPlayer {
                removeCardFromHand(DeadlyShot)
                mana(7)
            }
            waitingPlayer {
                removeMinion(CharacterIndex.MINION_2)
            }
        }
    }

    def "is effected by spellpower"() {
        startingBoard.placeMinion(CURRENT_PLAYER, new KoboldGeomancer())

        def theCard = root.data_.getCurrentPlayer().getHand().get(0)
        def ret = theCard.useOn(CURRENT_PLAYER, CharacterIndex.HERO, root)

        expect:
        ret != null
        ret instanceof RandomEffectNode
        ret.numChildren() == 2

        def copiedBoard = ret.data_.deepCopy()

        HearthTreeNode child0 = ret.getChildren().get(0);
        assertBoardDelta(copiedBoard, child0.data_) {
            currentPlayer {
                removeCardFromHand(DeadlyShot)
                mana(7)
            }
            waitingPlayer {
                removeMinion(CharacterIndex.MINION_1)
            }
        }

        HearthTreeNode child1 = ret.getChildren().get(1);
        assertBoardDelta(copiedBoard, child1.data_) {
            currentPlayer {
                removeCardFromHand(DeadlyShot)
                mana(7)
            }
            waitingPlayer {
                removeMinion(CharacterIndex.MINION_2)
            }
        }
    }
}
