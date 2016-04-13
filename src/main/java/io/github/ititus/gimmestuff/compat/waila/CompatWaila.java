package io.github.ititus.gimmestuff.compat.waila;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.block.BlockInfiniteRF;
import io.github.ititus.gimmestuff.util.Logger;

import mcp.mobius.waila.api.IWailaRegistrar;

public class CompatWaila {

	public static void callbackRegister(IWailaRegistrar registrar) {
		Stopwatch watch = Stopwatch.createStarted();
		Logger.info("Starting initialization of Waila compat");

		registrar.registerBodyProvider(new DataProviderInfiniteItem(), BlockInfiniteItem.class);
		registrar.registerBodyProvider(new DataProviderInfiniteFluid(), BlockInfiniteFluid.class);
		registrar.registerBodyProvider(new DataProviderInfiniteRF(), BlockInfiniteRF.class);

		Logger.info("Finished initialization of Waila compat after " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	}

}
