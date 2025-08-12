package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

public class NetherFortressStructureHelper {

	public static long chunkKey(int chunkX, int chunkZ) {
		return (((long) chunkX) << 32) | (chunkZ & 0xffffffffL);
	}

	/* Branch helper */
	public static class Branch {
		public int chunkX;
		public int chunkZ;
		public Direction direction;

		public Branch(int startChunkX, int startChunkZ, Direction dir) {
			this.chunkX = startChunkX;
			this.chunkZ = startChunkZ;
			this.direction = dir;
		}

		public void advance() {
			this.chunkX += direction.offsetX;
			this.chunkZ += direction.offsetZ;
		}

		public void turn(Turn t) {
			if (t == Turn.LEFT) {
				this.direction = this.direction.turnLeft();
			} else {
				this.direction = this.direction.turnRight();
			}
		}
	}

	public enum Turn { LEFT, RIGHT; }

	public enum Direction {
		NORTH(0, -1), SOUTH(0, 1), WEST(-1, 0), EAST(1, 0);

		public final int offsetX;
		public final int offsetZ;

		Direction(int offsetX, int offsetZ) {
			this.offsetX = offsetX;
			this.offsetZ = offsetZ;
		}

		public Direction turnLeft() {
			switch (this) {
				case NORTH: return WEST;
				case WEST: return SOUTH;
				case SOUTH: return EAST;
				default: return NORTH; // EAST -> NORTH
			}
		}

		public Direction turnRight() {
			switch (this) {
				case NORTH: return EAST;
				case EAST: return SOUTH;
				case SOUTH: return WEST;
				default: return NORTH; // WEST -> NORTH
			}
		}

		public Direction perpendicular(boolean positive) {
			// positive -> turnRight, negative -> turnLeft
			return positive ? this.turnRight() : this.turnLeft();
		}
	}
}
