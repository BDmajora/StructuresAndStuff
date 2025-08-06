package bdmajora.stuffmod.structureSystem;

import net.minecraft.core.util.helper.Axis;

public enum Rotation {
	NONE("none"),
	CLOCKWISE_90("clockwise_90"),
	CLOCKWISE_180("180"),
	COUNTERCLOCKWISE_90("counterclockwise_90");

	private final String id;

	private Rotation(String id) {
		this.id = id;
	}

	public Rotation getRotated(Rotation other) {
		switch (other) {
			case CLOCKWISE_180:
				switch (this) {
					case NONE: return CLOCKWISE_180;
					case CLOCKWISE_90: return COUNTERCLOCKWISE_90;
					case CLOCKWISE_180: return NONE;
					case COUNTERCLOCKWISE_90: return CLOCKWISE_90;
				}
			case COUNTERCLOCKWISE_90:
				switch (this) {
					case NONE: return COUNTERCLOCKWISE_90;
					case CLOCKWISE_90: return NONE;
					case CLOCKWISE_180: return CLOCKWISE_90;
					case COUNTERCLOCKWISE_90: return CLOCKWISE_180;
				}
			case CLOCKWISE_90:
				switch (this) {
					case NONE: return CLOCKWISE_90;
					case CLOCKWISE_90: return CLOCKWISE_180;
					case CLOCKWISE_180: return COUNTERCLOCKWISE_90;
					case COUNTERCLOCKWISE_90: return NONE;
				}
			default:
				return this;
		}
	}

	public Direction rotate(Direction direction) {
		if (direction.getAxis() == Axis.Y) {
			return direction;
		}
		switch (this) {
			case CLOCKWISE_90:
				return direction.rotate(1);
			case CLOCKWISE_180:
				return direction.getOpposite();
			case COUNTERCLOCKWISE_90:
				return direction.rotate(1).getOpposite();
			default:
				return direction;
		}
	}

	public int rotate(int index, int max) {
		switch (this) {
			case CLOCKWISE_90:
				return (index + max / 4) % max;
			case CLOCKWISE_180:
				return (index + max / 2) % max;
			case COUNTERCLOCKWISE_90:
				return (index + max * 3 / 4) % max;
			default:
				return index;
		}
	}
}
