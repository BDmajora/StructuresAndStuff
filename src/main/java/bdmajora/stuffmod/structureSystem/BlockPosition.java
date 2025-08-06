package bdmajora.stuffmod.structureSystem;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.WorldSource;

/**
 * Mutable 3D integer vector for position and offset calculations.
 */
public class BlockPosition {
	public int x, y, z;

	// Constructors
	public BlockPosition() {
		this(0, 0, 0);
	}

	public BlockPosition(int size) {
		this(size, size, size);
	}

	public BlockPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockPosition(CompoundTag tag) {
		readFromNBT(tag);
	}

	// Copy
	public BlockPosition copy() {
		return new BlockPosition(this.x, this.y, this.z);
	}

	// Set
	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Arithmetic (scalar)
	public BlockPosition add(int value) {
		this.x += value;
		this.y += value;
		this.z += value;
		return this;
	}

	public BlockPosition subtract(int value) {
		this.x -= value;
		this.y -= value;
		this.z -= value;
		return this;
	}

	public BlockPosition multiply(int value) {
		this.x *= value;
		this.y *= value;
		this.z *= value;
		return this;
	}

	public BlockPosition divide(int value) {
		this.x /= value;
		this.y /= value;
		this.z /= value;
		return this;
	}

	// Arithmetic (vector)
	public BlockPosition add(BlockPosition other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}

	public BlockPosition subtract(BlockPosition other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}

	public BlockPosition multiply(BlockPosition other) {
		this.x *= other.x;
		this.y *= other.y;
		this.z *= other.z;
		return this;
	}

	public BlockPosition divide(BlockPosition other) {
		this.x /= other.x;
		this.y /= other.y;
		this.z /= other.z;
		return this;
	}

	// Distance
	public double distanceTo(BlockPosition other) {
		double dx = other.x - this.x;
		double dy = other.y - this.y;
		double dz = other.z - this.z;
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	// Rotation (discrete)
	public BlockPosition rotate(Direction direction) {
		switch (direction) {
			case SOUTH: // Z+
				return new BlockPosition(this.z, this.y, -this.x);
			case NORTH: // Z-
				return new BlockPosition(-this.z, this.y, this.x);
			case WEST:  // X-
				return new BlockPosition(-this.x, this.y, -this.z);
			case EAST:  // X+
				return new BlockPosition(this.x, this.y, this.z);
			default:
				return this.copy();
		}
	}

	public BlockPosition rotate(BlockPosition origin, Direction direction) {
		BlockPosition rotated = this.rotate(direction);
		return rotated.add(origin);
	}

	// Rotation (continuous)
	public BlockPosition rotateX(double angle) {
		double cos = MathHelper.cos((float) angle);
		double sin = MathHelper.sin((float) angle);
		int newY = (int) Math.round(y * cos - z * sin);
		int newZ = (int) Math.round(y * sin + z * cos);
		this.y = newY;
		this.z = newZ;
		return this;
	}

	public BlockPosition rotateY(double angle) {
		double cos = MathHelper.cos((float) angle);
		double sin = MathHelper.sin((float) angle);
		int newX = (int) Math.round(x * cos - z * sin);
		int newZ = (int) Math.round(x * sin + z * cos);
		this.x = newX;
		this.z = newZ;
		return this;
	}

	public BlockPosition rotateX(BlockPosition origin, double angle) {
		this.add(origin);
		return this.rotateX(angle);
	}

	public BlockPosition rotateY(BlockPosition origin, double angle) {
		this.rotateY(angle);
		return this.add(origin);
	}

	// TileEntity fetch
	public TileEntity getTileEntity(WorldSource worldSource) {
		return worldSource.getTileEntity(this.x, this.y, this.z);
	}

	// NBT IO
	public void writeToNBT(CompoundTag tag) {
		tag.putInt("x", this.x);
		tag.putInt("y", this.y);
		tag.putInt("z", this.z);
	}

	public void readFromNBT(CompoundTag tag) {
		this.x = tag.getInteger("x");
		this.y = tag.getInteger("y");
		this.z = tag.getInteger("z");
	}

	// Equals & Hash
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BlockPosition)) return false;
		BlockPosition other = (BlockPosition) o;
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		return result;
	}

	@Override
	public String toString() {
		return "Vec3i{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
	}
}
