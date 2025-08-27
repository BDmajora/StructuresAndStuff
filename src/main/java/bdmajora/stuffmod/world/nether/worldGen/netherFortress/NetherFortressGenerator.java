package bdmajora.stuffmod.world.nether.worldGen.netherFortress;

import bdmajora.stuffmod.world.GeneralStructureGenerator;
import bdmajora.stuffmod.world.nether.blockPicking.netherFortress.FortressBlocks;
import bdmajora.stuffmod.world.nether.worldGen.netherFortress.steps.*;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkPosition;
import net.minecraft.core.world.chunk.provider.IChunkProvider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Fortress generator that chains steps by passing forward the exact
 * coordinates produced by the previous step—without introducing any
 * new shared base types in your codebase.
 *
 * Also adds a persistent coordMap that:
 *  - Stores fortress instances keyed by origin chunk (Long key)
 *  - Supports re-generation on later chunk loads (once per fortress)
 *  - Provides collision checks, containment checks, and nearest queries
 */
public class NetherFortressGenerator extends GeneralStructureGenerator {

	private final FortressBlocks fortressBlocks = FortressBlocks.DEFAULT;

	private final NetherFortressStart startPlacer;
	private final NetherFortressGenerateBridgeNorth bridgePlacerNorth;
	private final NetherFortressGenerateBridgeEndSouth bridgeEndPlacerSouth;
	private final NetherFortressGenerateBridgeCrossing crossingPlacerNorth;
	private final NetherFortressGenerateBridgeWest bridgePlacerWest;
	private final NetherFortressGenerateBridgeEast bridgePlacerEast;
	private final NetherFortressGenerateBridgeThrone throneRoomPlacer;

	// -----------------------------
	// Persistence & Utilities
	// -----------------------------

	/** Tracks all fortresses by their origin chunk key. */
	private final Map<Long, TrackedStructure> coordMap = new HashMap<>();

	/** AABB-style bounds helper. */
	private static final class Bounds {
		final int minX, minY, minZ, maxX, maxY, maxZ;

		Bounds(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
			this.minX = Math.min(minX, maxX);
			this.minY = Math.min(minY, maxY);
			this.minZ = Math.min(minZ, maxZ);
			this.maxX = Math.max(minX, maxX);
			this.maxY = Math.max(minY, maxY);
			this.maxZ = Math.max(minZ, maxZ);
		}

		boolean containsPoint(int x, int y, int z) {
			return x >= minX && x <= maxX
				&& y >= minY && y <= maxY
				&& z >= minZ && z <= maxZ;
		}

		boolean collidesWith(Bounds other) {
			return this.maxX >= other.minX && this.minX <= other.maxX
				&& this.maxY >= other.minY && this.minY <= other.maxY
				&& this.maxZ >= other.minZ && this.minZ <= other.maxZ;
		}

		ChunkPosition center() {
			return new ChunkPosition(
				(minX + maxX) >> 1,
				(minY + maxY) >> 1,
				(minZ + maxZ) >> 1
			);
		}
	}

	/** Stored fortress instance. Generated exactly once when any intersecting chunk is processed. */
	private final class TrackedStructure {
		final int anchorX, anchorY, anchorZ;   // start piece anchor
		final Bounds bounds;
		boolean generated = false;
		// Optionally track which chunks have triggered us (not strictly required with 'generated' flag)
		final Set<Long> touchedChunks = new HashSet<>();

		TrackedStructure(int anchorX, int anchorY, int anchorZ, Bounds bounds) {
			this.anchorX = anchorX;
			this.anchorY = anchorY;
			this.anchorZ = anchorZ;
			this.bounds = bounds;
		}

		void markTouched(int chunkX, int chunkZ) {
			touchedChunks.add(chunkKey(chunkX, chunkZ));
		}

		boolean intersectsChunk(int chunkX, int chunkZ) {
			int bx0 = (chunkX << 4);
			int bz0 = (chunkZ << 4);
			Bounds chunkBounds = new Bounds(bx0, 0, bz0, bx0 + 15, 255, bz0 + 15);
			return bounds.collidesWith(chunkBounds);
		}

		/** Generate the full fortress once from the anchor. */
		void generateAll(IChunkProvider chunkProvider, World world, Random rand) {
			if (generated) return;

			// Run the same step chain from the saved anchor
			boolean startPlaced = startPlacer.placeStart(world, rand, anchorX, anchorY, anchorZ);
			if (!startPlaced) {
				generated = true; // avoid retry loops—mark as done even if failed
				return;
			}

			bridgeEndPlacerSouth.placeBridgeEnd(world, rand, anchorX, anchorY, anchorZ);

			int endZOfNorthBridge = bridgePlacerNorth.generateBridge(world, rand, anchorX, anchorY, anchorZ);
			int cx = anchorX, cy = anchorY, cz = endZOfNorthBridge;

			crossingPlacerNorth.placeCrossing(world, rand, cx, cy, cz);
			bridgePlacerWest.generateBridge(world, rand, cx, cy, cz);
			bridgePlacerEast.generateBridge(world, rand, cx, cy, cz);
			throneRoomPlacer.placeThrone(world, rand, cx, cy, cz);

			generated = true;
		}
	}

	private static long chunkKey(int chunkX, int chunkZ) {
		return ((long) chunkX & 0xffffffffL) << 32 | ((long) chunkZ & 0xffffffffL);
	}

	// Local-only coord holder to pass precise positions between steps.
	private static final class Coord {
		final int x, y, z;
		Coord(int x, int y, int z) { this.x = x; this.y = y; this.z = z; }
	}

	public NetherFortressGenerator(int range) {
		setRange(range);

		startPlacer = new NetherFortressStart(fortressBlocks);

		// The rotations you already use:
		bridgePlacerNorth = new NetherFortressGenerateBridgeNorth(fortressBlocks, 0);
		bridgeEndPlacerSouth = new NetherFortressGenerateBridgeEndSouth(fortressBlocks, 180);
		crossingPlacerNorth = new NetherFortressGenerateBridgeCrossing(fortressBlocks, 0);
		bridgePlacerWest = new NetherFortressGenerateBridgeWest(fortressBlocks, 90);
		bridgePlacerEast = new NetherFortressGenerateBridgeEast(fortressBlocks, 90);

		// Throne room — facing north (rotation 0)
		throneRoomPlacer = new NetherFortressGenerateBridgeThrone(fortressBlocks, 0);
	}

	/**
	 * Generates the fortress immediately when the placement chunk is hit,
	 * and stores a persistent record so future chunks can recognize it.
	 */
	@Override
	public void generate(IChunkProvider chunkProvider, World world, int originChunkX, int originChunkZ) {
		NetherFortressPlacementLogic.PlacementInfo info =
			NetherFortressPlacementLogic.getPlacementInfo(world, originChunkX, originChunkZ);
		if (info == null) return;

		Random rand = info.rand;

		// Anchor: the start piece is your origin for attachments.
		Coord start = new Coord(info.x, info.y, info.z);

		// 1) Place START
		boolean startPlaced = startPlacer.placeStart(world, rand, start.x, start.y, start.z);
		if (!startPlaced) return;

		// 2) SOUTH — entrance capping/end
		bridgeEndPlacerSouth.placeBridgeEnd(world, rand, start.x, start.y, start.z);

		// 3) NORTH — main bridge from START
		int endZOfNorthBridge = bridgePlacerNorth.generateBridge(world, rand, start.x, start.y, start.z);
		Coord northBridgeEnd = new Coord(start.x, start.y, endZOfNorthBridge);

		// 4) CROSSING at end of north bridge
		crossingPlacerNorth.placeCrossing(world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z);

		// 5a) WEST bridge from crossing
		bridgePlacerWest.generateBridge(world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z);

		// 5b) EAST bridge from crossing
		bridgePlacerEast.generateBridge(world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z);

		// 6) THRONE ROOM directly north of crossing
		throneRoomPlacer.placeThrone(
			world, rand, northBridgeEnd.x, northBridgeEnd.y, northBridgeEnd.z
		);

		// After building, compute a conservative bounds and persist.
		// We know Z extends from start.z (south cap) to endZOfNorthBridge (+ a safety margin).
		// X spans both east & west bridges off the crossing; we use a safe fixed half-width.
		final int halfWidthX = 64;     // tweak if your side bridges are longer
		final int marginZ   = 64;      // extra safety past the north end
		final int minX = start.x - halfWidthX;
		final int maxX = start.x + halfWidthX;
		final int minZ = Math.min(start.z - 16, northBridgeEnd.z - 16);
		final int maxZ = Math.max(start.z + 16, northBridgeEnd.z + marginZ);
		final int minY = Math.max(0, start.y - 16);
		final int maxY = Math.min(255, start.y + 64);

		Bounds bounds = new Bounds(minX, minY, minZ, maxX, maxY, maxZ);

		long key = chunkKey(originChunkX, originChunkZ);
		TrackedStructure ts = new TrackedStructure(start.x, start.y, start.z, bounds);
		ts.generated = true; // we just generated it here
		ts.markTouched(originChunkX, originChunkZ);
		coordMap.put(key, ts);
	}

	// --------------------------------------------------------------------
	// Persistence API (callable from your mixin or elsewhere if you want)
	// --------------------------------------------------------------------

	/**
	 * For chunks that weren’t the initial placement chunk: if a chunk intersects
	 * a saved fortress that hasn’t been generated yet, generate it now (once).
	 * This keeps structures persistent across chunk-gen order.
	 */
	public boolean generateStructuresInChunk(IChunkProvider chunkProvider, World world, Random random, int chunkX, int chunkZ) {
		boolean any = false;
		for (TrackedStructure ts : coordMap.values()) {
			if (!ts.generated && ts.intersectsChunk(chunkX, chunkZ)) {
				ts.generateAll(chunkProvider, world, random);
				ts.markTouched(chunkX, chunkZ);
				any = true;
			}
		}
		return any;
	}

	/** True if (x,y,z) is inside any tracked fortress bounds. */
	public boolean isInsideStructure(int x, int y, int z) {
		for (TrackedStructure ts : coordMap.values()) {
			if (ts.bounds.containsPoint(x, y, z)) return true;
		}
		return false;
	}

	/** Naive collision test of a prospective AABB (world coords) against tracked fortresses. */
	public boolean collidesWith(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		Bounds test = new Bounds(minX, minY, minZ, maxX, maxY, maxZ);
		for (TrackedStructure ts : coordMap.values()) {
			if (ts.bounds.collidesWith(test)) return true;
		}
		return false;
	}

	/** Returns the center of the nearest tracked fortress to (x,y,z), or null. */
	public ChunkPosition getNearestInstance(int x, int y, int z) {
		double best = Double.MAX_VALUE;
		ChunkPosition bestPos = null;
		for (TrackedStructure ts : coordMap.values()) {
			ChunkPosition c = ts.bounds.center();
			double dx = c.x - x;
			double dy = c.y - y;
			double dz = c.z - z;
			double d2 = dx*dx + dy*dy + dz*dz;
			if (d2 < best) {
				best = d2;
				bestPos = c;
			}
		}
		return bestPos;
	}

	/** Clear all cached fortress records (e.g., on world unload). */
	public void reset() {
		coordMap.clear();
	}
}
