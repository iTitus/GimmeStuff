package io.github.ititus.gimmestuff.proxy;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.block.BlockInfinitePower;
import io.github.ititus.gimmestuff.client.gui.GuiInfiniteStuff;
import io.github.ititus.gimmestuff.client.handler.ClientEventHandler;
import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.init.ModItems;
import io.github.ititus.gimmestuff.inventory.container.ContainerInfiniteStuff;
import io.github.ititus.gimmestuff.lib.GuiIDs;
import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.ColorUtils;
import io.github.ititus.gimmestuff.util.INameable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

		for (BlockInfiniteItem.InfiniteItemType type : BlockInfiniteItem.InfiniteItemType.VALUES) {
			registerItemModel(ModBlocks.blockInfiniteItem, type.getMeta(), getModelResLoc(ModBlocks.blockInfiniteItem.getName(), "inventory_" + type.getName()));
		}

		registerItemModel(ModBlocks.blockInfiniteFluid);

		for (BlockInfinitePower.PowerType type : BlockInfinitePower.PowerType.VALUES) {
			registerItemModel(ModBlocks.blockInfinitePower, type.getMeta(), getModelResLoc(ModBlocks.blockInfinitePower.getName(), "inventory_" + type.getName()));
		}

		registerItemModel(ModBlocks.blockInfiniteStuff);

		registerItemModel(ModItems.itemGodFood);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		blocks.stream().filter(block -> block instanceof ColorUtils.IBlockWithColor).forEach(block -> {
			Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(ColorUtils.DefaultBlockColor.INSTANCE, block);
		});
		items.stream().filter(item -> item instanceof ColorUtils.IItemWithColor).forEach(item -> {
			Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ColorUtils.DefaultItemColor.INSTANCE, item);
		});
	}

	private ModelResourceLocation getModelResLoc(String path, String variant) {
		return new ModelResourceLocation(GimmeStuff.MOD_ID + ":" + path, variant);
	}

	private <T extends Block & INameable> void registerItemModel(T block) {
		registerItemModel(block, 0, getModelResLoc(block.getName(), "inventory"));
	}

	private <T extends Item & INameable> void registerItemModel(T item) {
		registerItemModel(item, 0, getModelResLoc(item.getName(), "inventory"));
	}

	private void registerItemModel(Block block, int meta, ModelResourceLocation modelResourceLocation) {
		registerItemModel(Item.getItemFromBlock(block), meta, modelResourceLocation);
	}

	private void registerItemModel(Item item, int meta, ModelResourceLocation modelResourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState state = world.getBlockState(pos);
		TileEntity tile = world.getTileEntity(pos);
		switch (id) {
			case GuiIDs.INFINITE_STUFF:
				if (!(tile instanceof TileInfiniteStuff)) {
					break;
				}
				return new GuiInfiniteStuff(new ContainerInfiniteStuff(player.inventory, (TileInfiniteStuff) tile));
			default:
				return null;
		}
		return null;
	}
}
