package io.github.ititus.gimmestuff.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileBase extends TileEntity {

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		NBTTagCompound compound = pkt.getNbtCompound();
		if (compound != null) {
			readFromCustomNBT(compound);
		}
	}

	@Override
	public Packet<?> getDescriptionPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		writeToCustomNBT(compound);
		return new SPacketUpdateTileEntity(pos, -1, compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		readFromCustomNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		writeToCustomNBT(compound);
	}

	public void readFromCustomNBT(NBTTagCompound compound) {

	}

	public void writeToCustomNBT(NBTTagCompound compound) {

	}

}
