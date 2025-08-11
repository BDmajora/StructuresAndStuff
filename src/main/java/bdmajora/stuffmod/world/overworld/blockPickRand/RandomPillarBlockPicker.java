package bdmajora.stuffmod.world.overworld.blockPickRand;

import java.util.Random;

public class RandomPillarBlockPicker {

	// Block IDs
	public static final int DIRT = 220;
	public static final int STONE = 1;
	public static final int BASALT = 2;
	public static final int LIMESTONE = 3;

	private static final int[] BLOCK_OPTIONS = {DIRT, STONE, BASALT, LIMESTONE};

	/**
	 * Picks a random block type from the available pillar block options.
	 *
	 * @param random Random instance (pass in the one you're already using so results stay deterministic)
	 * @return The block ID to use
	 */
	public static int getRandomBlock(Random random) {
		int index = random.nextInt(BLOCK_OPTIONS.length);
		return BLOCK_OPTIONS[index];
	}
}
