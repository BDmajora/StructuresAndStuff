//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package bdmajora.stuffmod.Utils;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.util.HardIllegalArgumentException;
import net.minecraft.core.util.collection.NamespaceID;
import org.jetbrains.annotations.NotNull;

public abstract class Util {
	public Util() {
	}

	public static Block<?> try_retrieve_key_or_err(@NotNull String namespaceID) {
		try {
			Block<?> b = (Block)Blocks.blockMap.get(NamespaceID.getTemp(namespaceID));
			if (b == null) {
				throw new RuntimeException("ERROR: can't find block with namespaceID '" + namespaceID + "' (from Structure Backports Config)");
			} else {
				return b;
			}
		} catch (HardIllegalArgumentException var3) {
			HardIllegalArgumentException e = var3;
			throw new RuntimeException(e);
		}
	}
}
