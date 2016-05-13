package io.github.ititus.gimmestuff.compat.waila;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.block.BlockInfinitePower;
import io.github.ititus.gimmestuff.util.Logger;

import mcp.mobius.waila.api.IWailaRegistrar;

public class CompatWaila {

	public static void callbackRegister(IWailaRegistrar registrar) {
		Stopwatch watch = Stopwatch.createStarted();
		Logger.info("Starting initialization of Waila compat");

		DataProviderInfiniteItem dataProviderInfiniteItem = new DataProviderInfiniteItem();
		registrar.registerStackProvider(dataProviderInfiniteItem, BlockInfiniteItem.class);
		registrar.registerBodyProvider(dataProviderInfiniteItem, BlockInfiniteItem.class);

		registrar.registerBodyProvider(new DataProviderInfiniteFluid(), BlockInfiniteFluid.class);

		DataProviderInfinitePower dataProviderInfinitePower = new DataProviderInfinitePower();
		registrar.registerStackProvider(dataProviderInfinitePower, BlockInfinitePower.class);
		registrar.registerBodyProvider(dataProviderInfinitePower, BlockInfinitePower.class);

		Logger.info("Finished initialization of Waila compat after " + watch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	}

}
