package spriteData.behavior.combat;

import spriteData.weaponSprite.WeaponSprite;

public interface Combatant {
    WeaponSprite getWPSprite();
    void onAttk();
}
