package spriteData.behavior.combat;

import spriteData.weaponSprite.WeaponSprite;

public interface Combatant {
    void setWPSprite(WeaponSprite s);
    WeaponSprite getWPSprite();
    void onAttk();
}
