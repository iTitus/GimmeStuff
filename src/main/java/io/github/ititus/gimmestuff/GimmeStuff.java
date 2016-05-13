package io.github.ititus.gimmestuff;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import io.github.ititus.gimmestuff.proxy.CommonProxy;
import io.github.ititus.gimmestuff.util.Logger;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GimmeStuff.MOD_ID, name = GimmeStuff.MOD_NAME, version = GimmeStuff.MOD_VERSION)
public class GimmeStuff {

	public static final boolean DEBUG = false;

	public static final String MOD_ID = "gimmestuff";
	public static final String MOD_NAME = "GimmeStuff - Creative Edition";
	public static final String MOD_VERSION = "@MODVERSION@";
	public static final String CLIENT_PROXY = "io.github.ititus.gimmestuff.proxy.ClientProxy";
	public static final String SERVER_PROXY = "io.github.ititus.gimmestuff.proxy.ServerProxy";

	@Mod.Instance
	public static GimmeStuff instance;

	@SidedProxy(clientSide = GimmeStuff.CLIENT_PROXY, serverSide = GimmeStuff.SERVER_PROXY)
	public static CommonProxy proxy;

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Stopwatch watch = Stopwatch.createStarted();
		Logger.info("Starting pre-initialization of " + MOD_NAME);
		proxy.preInit(event);
		Logger.info("Finished pre-initialization of " + MOD_NAME + " after " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	}

	@Mod.EventHandler
	public void preInit(FMLInitializationEvent event) {
		Stopwatch watch = Stopwatch.createStarted();
		Logger.info("Starting initialization of " + MOD_NAME);
		proxy.init(event);
		Logger.info("Finished initialization of " + MOD_NAME + " after " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	}

	@Mod.EventHandler
	public void preInit(FMLPostInitializationEvent event) {
		Stopwatch watch = Stopwatch.createStarted();
		Logger.info("Starting post-initialization of " + MOD_NAME);
		proxy.postInit(event);
		Logger.info("Finished post-initialization of " + MOD_NAME + " after " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	}

}
