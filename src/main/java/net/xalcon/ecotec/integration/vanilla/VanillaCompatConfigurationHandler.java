package net.xalcon.ecotec.integration.vanilla;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.xalcon.ecotec.Ecotec;

import java.io.File;

public class VanillaCompatConfigurationHandler
{
	private final static String CATEGORY_RANCHER = "rancher";

	private Configuration config;

	private RancherConfig rancherConfig;

	public VanillaCompatConfigurationHandler(File configFile)
	{
		this.config = new Configuration(configFile);
		this.loadConfigs();
	}

	public RancherConfig getRancherConfig()
	{
		return this.rancherConfig;
	}

	private void loadConfigs()
	{
		try
		{
			this.rancherConfig = new RancherConfig(this.config);
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

	public static class RancherConfig
	{
		private boolean ranchCowsEnabled;
		private int ranchCowsAmount;
		private int ranchCowsCooldown;
		private boolean ranchMooshroomsEnabled;
		private int ranchMooshroomsAmount;
		private int ranchMooshroomsCooldown;
		private boolean ranchSquidsEnabled;
		private int ranchSquidsAmount;
		private int ranchSquidsCooldown;
		private boolean ranchSheepsEnabled;

		public boolean isRanchCowsEnabled() { return this.ranchCowsEnabled; }
		public int getRanchCowsAmount() { return this.ranchCowsAmount; }
		public int getRanchCowsCooldown() { return this.ranchCowsCooldown; }
		public boolean isRanchMooshroomsEnabled() { return this.ranchMooshroomsEnabled; }
		public int getRanchMooshroomsAmount() { return this.ranchMooshroomsAmount; }
		public int getRanchMooshroomsCooldown() { return this.ranchMooshroomsCooldown; }
		public boolean isRanchSquidsEnabled() { return this.ranchSquidsEnabled; }
		public int getRanchSquidsAmount() { return this.ranchSquidsAmount; }
		public int getRanchSquidsCooldown() { return this.ranchSquidsCooldown; }
		public boolean isRanchSheepsEnabled() { return this.ranchSheepsEnabled; }

		RancherConfig(Configuration config)
		{
			this.ranchCowsEnabled = config.get(CATEGORY_RANCHER, "ranch_cows_enabled", true, "Controls if cows can be ranched for milk").getBoolean();
			this.ranchCowsAmount = config.get(CATEGORY_RANCHER, "ranch_cows_amount", Fluid.BUCKET_VOLUME, "The amount of fluid per ranching process", 0, 4000).getInt();
			this.ranchCowsCooldown = config.get(CATEGORY_RANCHER, "ranch_cows_cooldown", 30 * 20, "The cooldown between ranching in ticks", 0, Integer.MAX_VALUE).getInt();

			this.ranchMooshroomsEnabled = config.get(CATEGORY_RANCHER, "ranch_mooshrooms_enabled", true, "Controls if mooshrooms can be ranched for mushroom soup").getBoolean();
			this.ranchMooshroomsAmount = config.get(CATEGORY_RANCHER, "ranch_mooshrooms_amount", Fluid.BUCKET_VOLUME, "The amount of fluid per ranching process", 0, 4000).getInt();
			this.ranchMooshroomsCooldown = config.get(CATEGORY_RANCHER, "ranch_mooshrooms_cooldown", 30 * 20, "The cooldown between ranching in ticks", 0, Integer.MAX_VALUE).getInt();

			this.ranchSquidsEnabled = config.get(CATEGORY_RANCHER, "ranch_squids_enabled", true, "Controls if squids can be ranched for ink sacs").getBoolean();
			this.ranchSquidsAmount = config.get(CATEGORY_RANCHER, "ranch_squids_amount", 1, "The amount of ink sacs per ranching process", 0, 64).getInt();
			this.ranchSquidsCooldown = config.get(CATEGORY_RANCHER, "ranch_squids_cooldown", 30 * 20, "The cooldown between ranching in ticks", 0, Integer.MAX_VALUE).getInt();

			this.ranchSheepsEnabled = config.get(CATEGORY_RANCHER, "ranch_sheeps_enabled", true, "Controls if sheeps can be sheared for wool").getBoolean();
		}
	}
}
