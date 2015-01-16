package com.mrhid6.zonusv2.tileentity.cable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.api.ICable;
import com.mrhid6.zonusv2.api.IMachineConnectable;
import com.mrhid6.zonusv2.api.IMachineSidedConnectable;
import com.mrhid6.zonusv2.network.PacketHandler;
import com.mrhid6.zonusv2.network.message.MessageTileEntityCableBase;

import cpw.mods.fml.common.network.NetworkRegistry;

public class TileEntityCableBase extends TileEntity implements ICable{

	private boolean[] connections = new boolean[6];
	private boolean initiated = false;
	public TileEntityCableBase() {
	}
	

	protected void sendPacket(){
		this.markDirty();
		PacketHandler.INSTANCE.sendToAllAround(new MessageTileEntityCableBase(this, connections), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, (double) this.xCoord, (double) this.yCoord, (double) this.zCoord, 128d));
		this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		System.out.println("sent packet");
	}

	@Override
	public boolean canIConnectWithTileEntity(TileEntity tileEntity, ForgeDirection side) {

		if(tileEntity == null) return false;

		if(tileEntity instanceof TileEntityCableBase){
			return canConnectWithOtherCable((TileEntityCableBase)tileEntity);
		}
		
		if(tileEntity instanceof IMachineConnectable){
			return true;
		}
		
		if(tileEntity instanceof IMachineSidedConnectable){
			IMachineSidedConnectable machine = (IMachineSidedConnectable)tileEntity;
			
			
			return machine.checkConectivityForSide(side);
		}

		return false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound data) {
		super.writeToNBT(data);
		
		for(int i=0;i<6;i++){
			data.setBoolean("connected_"+i, connections[i]);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data) {
		super.readFromNBT(data);
		
		for(int i=0;i<6;i++){
			connections[i] = data.getBoolean("connected_"+i);
		}
	}

	@Override
	public void setConnections(int down, int up, int north, int south, int west, int east) {
		connections[0] = (down==1)?true:false;
		connections[1] = (up==1)?true:false;
		connections[2] = (north==1)?true:false;
		connections[3] = (south==1)?true:false;
		connections[4] = (west==1)?true:false;
		connections[5] = (east==1)?true:false;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote){
			
			
			if(!initiated){
				initiated = true;
				updateConnections(true);
			}
		}
	}

	@Override
	public void updateConnections(boolean forceUpdate) {
		
		if(!this.worldObj.isRemote){
			
			boolean[] oldconnections = new boolean[6];
			
			for(int i=0;i<6;i++){
				
				oldconnections[i] = Boolean.valueOf(connections[i]);
			}
			
			boolean hasChanged = false;
			
			for(int i=0;i<6;i++){

				int xoffset = ForgeDirection.getOrientation(i).offsetX;
				int yoffset = ForgeDirection.getOrientation(i).offsetY;
				int zoffset = ForgeDirection.getOrientation(i).offsetZ;

				TileEntity tile = this.getWorldObj().getTileEntity(xCoord + xoffset, yCoord + yoffset, zCoord + zoffset);

				connections[i] = canIConnectWithTileEntity(tile, ForgeDirection.getOrientation(i).getOpposite());
			}
			
			for(int i=0;i<6;i++){
				
				if(oldconnections[i]!=connections[i]){
					hasChanged = true;
				}
			}
			if(hasChanged || forceUpdate){
				this.markDirty();
				sendPacket();
			}
		}
	}

	@Override
	public boolean[] getConnections() {
		return connections;
	}

	@Override
	public float getCableThickness() {
		return 2.8F / 16.0F;
	}


	@Override
	public boolean canConnectWithOtherCable(TileEntityCableBase cable) {
		boolean out = true;
		
		
		if(cable instanceof TileEntityZoroCable){
			out = false;
		}
		
		return out;
	}


}
