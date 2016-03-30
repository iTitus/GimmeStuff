package io.github.ititus.gimmestuff;

import io.github.ititus.gimmestuff.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = GimmeStuff.MOD_ID, name = GimmeStuff.MOD_NAME, version = GimmeStuff.MOD_VERSION)
public class GimmeStuff {

	public static final String MOD_ID = "gimmestuff";
	public static final String MOD_NAME = "GimmeStuff";
	public static final String MOD_VERSION = "@MODVERSION@";
	public static final String CLIENT_PROXY = "io.github.ititus.gimmestuff.proxy.ClientProxy";
	public static final String SERVER_PROXY = "io.github.ititus.gimmestuff.proxy.ServerProxy";

	@Mod.Instance
	public static GimmeStuff instance;

	@SidedProxy(clientSide = GimmeStuff.CLIENT_PROXY, serverSide = GimmeStuff.SERVER_PROXY)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void preInit(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void preInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

}