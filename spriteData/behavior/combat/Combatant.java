package spriteData.behavior.combat;

import spriteData.weaponSprite.WeaponSprite;

public interface Combatant {
    void setAttkSpeed(double speed);
    double getAttkSpeed();
    void clearWPSprite();
    void setWPSprite(WeaponSprite s);
    WeaponSprite getWPSprite();
    void onAttk();
}
