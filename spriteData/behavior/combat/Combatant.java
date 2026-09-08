package spriteData.behavior.combat;

import spriteData.weaponSprite.WeaponSprite;

public interface Combatant {
    void clearWPSprite();
    void setWPSprite(WeaponSprite s);
    WeaponSprite getWPSprite();
    void onAttk();
}
