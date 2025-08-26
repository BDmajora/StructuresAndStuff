package bdmajora.stuffmod.world;

/**
 * A wrapper around a structure piece that keeps track of its region in world space.
 * Provides helpers for expansion, shifting, and collision.
 */
public class StructureWrapper {

	private int minX;
	private int minY;
	private int minZ;
	private int maxX;
	private int maxY;
	private int maxZ;

	/**
	 * Creates a wrapper around a piece using world coordinates.
	 *
	 * @param minX minimum X (inclusive)
	 * @param minY minimum Y (inclusive)
	 * @param minZ minimum Z (inclusive)
	 * @param maxX maximum X (inclusive)
	 * @param maxY maximum Y (inclusive)
	 * @param maxZ maximum Z (inclusive)
	 */
	public StructureWrapper(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		if (minX > maxX || minY > maxY || minZ > maxZ) {
			throw new IllegalArgumentException("Invalid structure region: min cannot be greater than max.");
		}

		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	/**
	 * Private constructor that skips validation (for empty regions).
	 */
	private StructureWrapper(int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean allowInvalid) {
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
	}

	/**
	 * Returns an "empty" region to expand into.
	 */
	public static StructureWrapper empty() {
		return new StructureWrapper(
			Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
			Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE,
			true
		);
	}

	/**
	 * Builds a region for a component relative to the given base coordinates and orientation.
	 *
	 * This provides the same placement math used by the original component helper,
	 * but returns a StructureWrapper (no other terminology is used).
	 *
	 * @param x base X
	 * @param y base Y
	 * @param z base Z
	 * @param offsetX local offset X
	 * @param offsetY local offset Y
	 * @param offsetZ local offset Z
	 * @param sizeX size along X
	 * @param sizeY size along Y
	 * @param sizeZ size along Z
	 * @param orientation 0..3 orientation (rotation)
	 * @return a new StructureWrapper covering that transformed space
	 */
	public static StructureWrapper getComponentToAddArea(
		int x, int y, int z,
		int offsetX, int offsetY, int offsetZ,
		int sizeX, int sizeY, int sizeZ,
		int orientation) {

		switch (orientation) {
			case 0: // default orientation
				return new StructureWrapper(
					x + offsetX,
					y + offsetY,
					z + offsetZ,
					x + offsetX + sizeX - 1,
					y + offsetY + sizeY - 1,
					z + offsetZ + sizeZ - 1
				);
			case 1: // rotate 90°
				return new StructureWrapper(
					x - sizeZ + 1 + offsetZ,
					y + offsetY,
					z + offsetX,
					x + offsetZ,
					y + offsetY + sizeY - 1,
					z + offsetX + sizeX - 1
				);
			case 2: // rotate 180°
				return new StructureWrapper(
					x + offsetX,
					y + offsetY,
					z - sizeZ + 1 + offsetZ,
					x + offsetX + sizeX - 1,
					y + offsetY + sizeY - 1,
					z + offsetZ
				);
			case 3: // rotate 270°
				return new StructureWrapper(
					x + offsetZ,
					y + offsetY,
					z - sizeX + 1 + offsetX,
					x + offsetZ + sizeZ - 1,
					y + offsetY + sizeY - 1,
					z + offsetX
				);
			default:
				throw new IllegalArgumentException("Invalid orientation: " + orientation);
		}
	}

	/**
	 * Checks if this structure intersects another.
	 * Touching edges or corners do NOT count as collisions.
	 */
	public boolean collidesWith(StructureWrapper other) {
		return this.maxX > other.minX && this.minX < other.maxX &&
			this.maxY > other.minY && this.minY < other.maxY &&
			this.maxZ > other.minZ && this.minZ < other.maxZ;
	}

	/**
	 * Returns true if the given coordinates are inside this region.
	 */
	public boolean containsPoint(int x, int y, int z) {
		return x >= minX && x <= maxX &&
			y >= minY && y <= maxY &&
			z >= minZ && z <= maxZ;
	}

	/**
	 * Expands this region to also include the given wrapper.
	 */
	public void expandTo(StructureWrapper other) {
		this.minX = Math.min(this.minX, other.minX);
		this.minY = Math.min(this.minY, other.minY);
		this.minZ = Math.min(this.minZ, other.minZ);
		this.maxX = Math.max(this.maxX, other.maxX);
		this.maxY = Math.max(this.maxY, other.maxY);
		this.maxZ = Math.max(this.maxZ, other.maxZ);
	}

	/**
	 * Offsets this region in world space.
	 */
	public void offset(int dx, int dy, int dz) {
		this.minX += dx;
		this.minY += dy;
		this.minZ += dz;
		this.maxX += dx;
		this.maxY += dy;
		this.maxZ += dz;
	}

	// ===== Getters for all six coordinates =====

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}

	public int getMinZ() {
		return minZ;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getMaxZ() {
		return maxZ;
	}

	/**
	 * @return width along X-axis.
	 */
	public int getWidth() {
		return maxX - minX + 1;
	}

	/**
	 * @return height along Y-axis.
	 */
	public int getHeight() {
		return maxY - minY + 1;
	}

	/**
	 * Alias for getHeight().
	 */
	public int getYSize() {
		return getHeight();
	}

	/**
	 * @return depth along Z-axis.
	 */
	public int getDepth() {
		return maxZ - minZ + 1;
	}

	/**
	 * @return the center X coordinate.
	 */
	public int getCenterX() {
		return minX + getWidth() / 2;
	}

	/**
	 * @return the center Y coordinate.
	 */
	public int getCenterY() {
		return minY + getHeight() / 2;
	}

	/**
	 * @return the center Z coordinate.
	 */
	public int getCenterZ() {
		return minZ + getDepth() / 2;
	}

	@Override
	public String toString() {
		return "StructureWrapper{" +
			"min=(" + minX + ", " + minY + ", " + minZ + ")" +
			", max=(" + maxX + ", " + maxY + ", " + maxZ + ")" +
			'}';
	}

	/**
	 * Alias for collidesWith method.
	 */
	public boolean intersects(StructureWrapper other) {
		return collidesWith(other);
	}
}
