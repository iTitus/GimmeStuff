package io.github.ititus.gimmestuff.block;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.init.ModCreativeTab;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

	protected final String name;

	public BlockBase(String name) {
		this(name, Material.IRON);
	}

	public BlockBase(String name, Material material) {
		this(name, material, material.getMaterialMapColor());
	}

	public BlockBase(String name, Material blockMaterial, MapColor mapColor) {
		super(blockMaterial, mapColor);
		this.name = name;
		setUnlocalizedName(GimmeStuff.MOD_ID + ":" + name);
		setRegistryName(GimmeStuff.MOD_ID, name);
		setCreativeTab(ModCreativeTab.MAIN_TAB);
		setHardness(5);
		setResistance(5);
		if (blockMaterial == Material.IRON) {
			setSoundType(SoundType.METAL);
		}
	}


	public String getName() {
		return name;
	}

}
