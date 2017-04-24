package net.xalcon.ecotec;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;

import java.io.File;

public class EcotecConfigHandler
{
	private final static String CATEGORY_RANCHER = "rancher";

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

		}
		catch (Exception e)
		{
			EcotecMod.Log.error("Error loading config", e);
		}
		finally
		{
			if (this.config.hasChanged())
				this.config.save();
		}
	}
}
