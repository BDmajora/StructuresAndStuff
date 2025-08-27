package bdmajora.stuffmod.world;

/**
 * Utility class to handle rotation of structure pieces.
 */
public class PieceRotation {

	public enum Rotation {
		NONE,
		CLOCKWISE_90,
		CLOCKWISE_180,
		COUNTERCLOCKWISE_90
	}

	/**
	 * Rotates a StructureWrapper around its center.
	 *
	 * @param wrapper  the structure to rotate
	 * @param rotation the rotation to apply
	 * @return a new rotated StructureWrapper
	 */
	public static StructureWrapper rotate(StructureWrapper wrapper, Rotation rotation) {
		if (rotation == null || rotation == Rotation.NONE) {
			return wrapper;
		}

		int cx = wrapper.getCenterX();
		int cz = wrapper.getCenterZ();

		int[][] corners = {
			{wrapper.getMinX(), wrapper.getMinZ()},
			{wrapper.getMinX(), wrapper.getMaxZ()},
			{wrapper.getMaxX(), wrapper.getMinZ()},
			{wrapper.getMaxX(), wrapper.getMaxZ()}
		};

		int newMinX = Integer.MAX_VALUE;
		int newMinZ = Integer.MAX_VALUE;
		int newMaxX = Integer.MIN_VALUE;
		int newMaxZ = Integer.MIN_VALUE;

		for (int[] c : corners) {
			int x = c[0] - cx;
			int z = c[1] - cz;
			int rx, rz;

			switch (rotation) {
				case CLOCKWISE_90:
					rx = z;
					rz = -x;
					break;
				case CLOCKWISE_180:
					rx = -x;
					rz = -z;
					break;
				case COUNTERCLOCKWISE_90:
					rx = -z;
					rz = x;
					break;
				default:
					rx = x;
					rz = z;
			}

			int nx = cx + rx;
			int nz = cz + rz;

			if (nx < newMinX) newMinX = nx;
			if (nz < newMinZ) newMinZ = nz;
			if (nx > newMaxX) newMaxX = nx;
			if (nz > newMaxZ) newMaxZ = nz;
		}

		return new StructureWrapper(
			newMinX, wrapper.getMinY(), newMinZ,
			newMaxX, wrapper.getMaxY(), newMaxZ
		);
	}
}
