package net.xalcon.ecotec;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class EcotecConfigHandler
{
	private Configuration config;

	public EcotecConfigHandler(File configFile)
	{
		this.config = new Configuration(configFile);
		this.loadConfigs();
	}

	private void loadConfigs()
	{
		try
		{
			int energyUsage = this.config.getInt("energy_usage", "machines", 100, 0, Integer.MAX_VALUE, "Bla");
		}
		catch (Exception e)
		{
			Ecotec.Log.error("Error loading config", e);
		}
		finally
		{
			if (this.config.hasChanged())
				this.config.save();
		}
	}
}
