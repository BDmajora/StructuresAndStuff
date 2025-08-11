package bdmajora.stuffmod.Utils;

import turniplabs.halplibe.util.ConfigHandler;

public class ConfigHandlerExtended extends ConfigHandler {
	public ConfigHandlerExtended(String modID, java.util.Properties defaultProperties) {
		super(modID, defaultProperties);
	}

	public Double getDouble(String key) {
		return Double.parseDouble(getProperty(key));
	}
}
