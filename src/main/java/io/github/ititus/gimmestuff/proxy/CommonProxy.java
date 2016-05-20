package io.github.ititus.gimmestuff.proxy;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.handler.EventHandler;
import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.init.ModItems;
import io.github.ititus.gimmestuff.init.ModStuffTypes;
import io.github.ititus.gimmestuff.inventory.container.ContainerInfiniteStuff;
import io.github.ititus.gimmestuff.lib.GuiIDs;
import io.github.ititus.gimmestuff.recipe.RecipeInfiniteItemContentChanger;
import io.github.ititus.gimmestuff.tile.TileBase;
import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.INameable;
import io.github.ititus.gimmestuff.util.stuff.StuffTypeRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public class CommonProxy implements IGuiHandler {

	protected final List<Item> items = Lists.newArrayList();
	protected final List<Block> blocks = Lists.newArrayList();

	public void preInit(FMLPreInitializationEvent event) {
		StuffTypeRegistry.getStuffTypeRegistry();
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(GimmeStuff.instance, GimmeStuff.proxy);
		ModBlocks.preInit();
		ModItems.preInit();
		FMLInterModComms.sendMessage("Waila", "register", "io.github.ititus.gimmestuff.compat.waila.CompatWaila.callbackRegister");
	}

	public void init(FMLInitializationEvent event) {
		for (BlockInfiniteItem.InfiniteItemType type : BlockInfiniteItem.InfiniteItemType.VALUES) {
			GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockInfiniteItem, 1, type.getMeta()), new ItemStack(ModBlocks.blockInfiniteItem, 1, type.getMeta()));
		}

		GameRegistry.addRecipe(new RecipeInfiniteItemContentChanger());
		RecipeSorter.register(GimmeStuff.MOD_ID + ":infiniteItemContentChanger", RecipeInfiniteItemContentChanger.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");

		ModStuffTypes.init();
	}

	public void postInit(FMLPostInitializationEvent event) {
	}

	public <T extends Block & INameable> T registerWithDefaultItemBlock(T block) {
		GameRegistry.register(block);
		blocks.add(block);
		Item itemBlock = new ItemBlock(block).setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
		items.add(itemBlock);
		return block;
	}

	public <T extends Block & INameable> T registerWithCustomItemBlock(T block, Item itemBlock) {
		GameRegistry.register(block);
		blocks.add(block);
		itemBlock.setRegistryName(block.getRegistryName());
		GameRegistry.register(itemBlock);
		items.add(itemBlock);
		return block;
	}

	public void registerTileEntity(Class<? extends TileBase> tileClass, String name) {
		GameRegistry.registerTileEntity(tileClass, "tileentity." + GimmeStuff.MOD_ID + ":" + name);
	}

	public <T extends Item & INameable> T registerItem(T item) {
		GameRegistry.register(item);
		items.add(item);
		return item;
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
		TileEntity tile = world.getTileEntity(pos);
		switch (id) {
			case GuiIDs.INFINITE_STUFF:
				if (!(tile instanceof TileInfiniteStuff)) {
					break;
				}
				return new ContainerInfiniteStuff(player.inventory, (TileInfiniteStuff) tile);
			default:
				return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
