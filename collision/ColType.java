package collision;

/**
 * STAGEBOX: The bounds of the stage itself.
 * <br>HURTBOX: Takes damage if hitbox enters bounds.
 * <br>HITBOX: Deals damage if entering a hurtbox.
 * <br>DETECTBOX: For detection of other boxes from far away.
 * <br>INTERACTBOX: Can interact if within bounds.
 * <br>WORLDBOX: General collision for anything with collision.
 * <br>CHECKBOX: Used to check for moving and or attacking.
 */
public enum ColType {
    STAGEBOX,
    HURTBOX,
    HITBOX,
    DETECTBOX,
    INTERACTBOX,
    WORLDBOX,
    CHECKBOX
}
