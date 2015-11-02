package com.hearthsim.card.basic.spell;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellTargetableCard;
import com.hearthsim.event.effect.EffectCharacter;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.filter.FilterCharacterTargetedSpell;

public class AncestralHealing extends SpellTargetableCard {

    public static final EffectCharacter effect = (targetSide, targetCharacterIndex, boardState) -> {
        Minion targetCharacter = boardState.data_.getCharacter(targetSide, targetCharacterIndex);
        if (targetCharacter.getHealth() < targetCharacter.getMaxHealth())
            boardState = targetCharacter.takeHealAndNotify((byte) (targetCharacter.getMaxHealth() - targetCharacter.getHealth()), targetSide, boardState);
        targetCharacter.setTaunt(true);
        return boardState;
    };

    /**
     * Constructor
     *
     * @param hasBeenUsed Whether the card has already been used or not
     */
    @Deprecated
    public AncestralHealing(boolean hasBeenUsed) {
        this();
        this.hasBeenUsed = hasBeenUsed;
    }

    /**
     * Constructor
     *
     * Defaults to hasBeenUsed = false
     */
    public AncestralHealing() {
        super();
    }

    @Override
    public FilterCharacter getTargetableFilter() {
        return FilterCharacterTargetedSpell.ALL_MINIONS;
    }

    /**
     *
     * Use the card on the given target
     *
     * This card heals the target minion up to full health and gives it taunt.  Cannot be used on heroes.
     *
     * @return The boardState is manipulated and returned
     */
    @Override
    public EffectCharacter getTargetableEffect() {
        return AncestralHealing.effect;
    }
}
