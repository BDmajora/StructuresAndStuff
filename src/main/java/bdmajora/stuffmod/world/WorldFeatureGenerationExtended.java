package bdmajora.stuffmod.world;

import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

/**
 * Extended world feature base class for structure-based generation.
 * Works with StructureWrapper instead of raw block placement.
 * Supports rotation via PieceRotation helper.
 */
public abstract class WorldFeatureGenerationExtended extends WorldFeature {

	private StructureWrapper baseStructure;
	private PieceRotation.Rotation rotation;

	public WorldFeatureGenerationExtended() {
		this.baseStructure = StructureWrapper.empty();
		this.rotation = PieceRotation.Rotation.NONE;
	}

	/**
	 * Core placement logic for the feature.
	 *
	 * Subclasses should implement actual block/structure placement here.
	 */
	@Override
	public abstract boolean place(World world, Random random, int x, int y, int z);

	/**
	 * Sets the base structure (unrotated).
	 */
	protected void setStructure(StructureWrapper structure) {
		this.baseStructure = structure;
	}

	/**
	 * @return the rotated view of the structure (rotation applied on access)
	 */
	public StructureWrapper getStructure() {
		return PieceRotation.rotate(this.baseStructure, this.rotation);
	}

	/**
	 * Sets the rotation for this feature.
	 */
	public void setRotation(PieceRotation.Rotation rotation) {
		this.rotation = (rotation == null) ? PieceRotation.Rotation.NONE : rotation;
	}

	/**
	 * @return the current rotation of this feature
	 */
	public PieceRotation.Rotation getRotation() {
		return this.rotation;
	}

	/**
	 * Merges this feature's structure with another,
	 * applying this feature's rotation to the incoming wrapper before merging.
	 */
	protected void mergeStructure(StructureWrapper other) {
		StructureWrapper rotatedOther = PieceRotation.rotate(other, this.rotation);
		this.baseStructure = this.baseStructure.merge(rotatedOther);
	}

	/**
	 * Checks if this feature's structure overlaps another.
	 */
	public boolean overlaps(WorldFeatureGenerationExtended other) {
		return this.getStructure().overlaps(other.getStructure());
	}
}
