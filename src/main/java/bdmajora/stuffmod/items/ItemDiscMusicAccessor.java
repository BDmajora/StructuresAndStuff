package bdmajora.stuffmod.items;

import net.minecraft.core.item.ItemDiscMusic;

public class ItemDiscMusicAccessor extends ItemDiscMusic {
	private final String texture;

	// Constructor now includes namespaceId as the 2nd argument
	protected ItemDiscMusicAccessor(String name, int namespaceId, int id, String recordName, String recordAuthor, String texture) {
		// Pass the namespaceId to the super constructor, converting to String if needed
		super(name, String.valueOf(namespaceId), id, recordName, recordAuthor);
		this.texture = texture;
	}


	public String getTexture() {
		return texture;
	}
}
