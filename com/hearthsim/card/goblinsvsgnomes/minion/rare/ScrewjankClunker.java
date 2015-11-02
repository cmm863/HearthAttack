package com.hearthsim.card.goblinsvsgnomes.minion.rare;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.event.effect.EffectCharacter;
import com.hearthsim.event.effect.EffectCharacterBuffDelta;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.filter.FilterCharacterTargetedBattlecry;

public class ScrewjankClunker extends Minion implements MinionBattlecryInterface {
    private final static FilterCharacterTargetedBattlecry filter = new FilterCharacterTargetedBattlecry() {
        protected boolean includeOwnMinions() {
            return true;
        }
        protected MinionTribe tribeFilter() {
            return MinionTribe.MECH;
        }
    };

    private final static EffectCharacter battlecryAction = new EffectCharacterBuffDelta(2, 2);

    public ScrewjankClunker() {
        super();
    }

    @Override
    public FilterCharacter getBattlecryFilter() {
        return ScrewjankClunker.filter;
    }

    @Override
    public EffectCharacter getBattlecryEffect() {
        return ScrewjankClunker.battlecryAction;
    }
}
