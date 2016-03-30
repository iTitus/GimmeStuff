package io.github.ititus.gimmestuff.block;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.init.ModCreativeTab;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends BlockContainer {

	protected final String name;

	public BlockBase(String name) {
		this(name, Material.iron);
	}

	public BlockBase(String name, Material material) {
		super(material);
		this.name = name;
		setUnlocalizedName(GimmeStuff.MOD_ID + ":" + name);
		setRegistryName(GimmeStuff.MOD_ID, name);
		setCreativeTab(ModCreativeTab.MAIN_TAB);
		setHardness(5);
		setResistance(5);
	}

	public String getName() {
		return name;
	}
}
