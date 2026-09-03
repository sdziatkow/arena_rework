package movement;

/** Describes what the NPC is doing.
 * <br> IDLE - Standing still.
 * <br> FREE - Moving randomly.
 * <br> HUNTING - Moving randomly and detecting for a Hurtable Object to come within its range.
 * <br> CHASING - Moving towards Hurtable Object
 * <br>
 */
public enum NPCState {
    IDLE, FREE, HUNTING, COMBAT
}
