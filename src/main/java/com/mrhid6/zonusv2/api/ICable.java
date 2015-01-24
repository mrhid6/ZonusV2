package com.mrhid6.zonusv2.api;

import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * ICable API Class. Allows cables to determine whether or not to connect to it.
 * 
 * @author Mrhid6
 */
public interface ICable {

	/**
	 * 
	 * @param down
	 * @param up
	 * @param north
	 * @param south
	 * @param west
	 * @param east
	 */
	public void setConnections(int down, int up, int north, int south,
			int west, int east);

	/**
	 * 
	 * @param tileEntity
	 *            - The TileEntity your testing to connect to.
	 * @param side
	 *            - The side of the TileEntity your connecting to.
	 * @return boolean
	 */
	public boolean canIConnectWithTileEntity(TileEntity tileEntity,
			ForgeDirection side);

	/**
	 * 
	 * @param cable
	 *            - The cable your testing to connect to.
	 * @return
	 */
	public boolean canConnectWithOtherCable(TileEntityCableBase cable);

	/**
	 * Get the active Connection Array
	 * 
	 * @return boolean[]
	 */
	public boolean[] getConnections();

	/**
	 * 
	 * @return Cable Thickness
	 */
	public float getCableThickness();

	/**
	 * 
	 * @param forceUpdate
	 *            - Force the cable to update its connections.
	 */
	void updateConnections(boolean forceUpdate);

}
