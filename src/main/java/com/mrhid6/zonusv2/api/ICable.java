package com.mrhid6.zonusv2.api;

import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public interface ICable {
	
	public void setConnections(int down, int up, int north, int south, int west, int east);
	
	public boolean canIConnectWithTileEntity(TileEntity tileEntity, ForgeDirection side);
	
	public boolean canConnectWithOtherCable(TileEntityCableBase cable);
	
	public boolean[] getConnections();
	
	public float getCableThickness();

	void updateConnections(boolean forceUpdate);
	
}
