package com.mrhid6.zonusv2.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.api.ICablePowerObject;
import com.mrhid6.zonusv2.api.IMachineSidedConnectable;
import com.mrhid6.zonusv2.api.IPowerAcceptor;
import com.mrhid6.zonusv2.api.IPowerEjector;
import com.mrhid6.zonusv2.api.IPowerObject;
import com.mrhid6.zonusv2.init.ModBlocks;
import com.mrhid6.zonusv2.network.PacketHandler;
import com.mrhid6.zonusv2.network.message.MessageTileEntityTriniumGenerator;
import com.mrhid6.zonusv2.reference.Names;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityTriniumGenerator extends TileEntityZonus implements
		ISidedInventory, IPowerObject, IPowerEjector, IMachineSidedConnectable {

	private int ejectAmount = 80;
	private int power = 5000;
	private int maxPower = 10000;

	public static final int INVENTORY_SIZE = 2;
	public static final int INPUT_INVENTORY_INDEX = 0;
	public static final int UPGRADE_INVENTORY_INDEX = 1;

	private ItemStack[] inventory;

	private int totalFuelTime = 0;
	private int deviceCookTime = 0;

	public TileEntityTriniumGenerator() {
		inventory = new ItemStack[INVENTORY_SIZE];
	}

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {

			boolean isBurning = this.deviceCookTime > 0;
			boolean sendUpdate = false;

			if (this.deviceCookTime > 0) {
				this.deviceCookTime--;
			}

			if (deviceCookTime == 0) {
				this.totalFuelTime = this.deviceCookTime = TileEntityFurnace
						.getItemBurnTime(this.inventory[INPUT_INVENTORY_INDEX]);

				if (this.deviceCookTime > 0) {
					sendUpdate = true;

					if (this.inventory[INPUT_INVENTORY_INDEX] != null) {
						--this.inventory[INPUT_INVENTORY_INDEX].stackSize;

						if (this.inventory[INPUT_INVENTORY_INDEX].stackSize == 0) {
							this.inventory[INPUT_INVENTORY_INDEX] = this.inventory[INPUT_INVENTORY_INDEX]
									.getItem().getContainerItem(
											inventory[INPUT_INVENTORY_INDEX]);
						}
					}
				}
			}

			if (this.deviceCookTime > 0) {
				setPowerStored(getStoredPower() + 10);
			}

			if (isBurning != this.deviceCookTime > 0) {
				sendUpdate = true;
			}

			sendPacket(sendUpdate);

			ejectPower();
		}
	}

	public void sendPacket(boolean sendUpdate) {
		if (sendUpdate) {
			this.markDirty();
			this.state = this.deviceCookTime > 0 ? (byte) 1 : (byte) 0;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord,
					ModBlocks.trinGenerator, 1, this.state);
			PacketHandler.INSTANCE.sendToAllAround(
					new MessageTileEntityTriniumGenerator(this),
					new NetworkRegistry.TargetPoint(
							this.worldObj.provider.dimensionId,
							(double) this.xCoord, (double) this.yCoord,
							(double) this.zCoord, 128d));
			this.worldObj.notifyBlockChange(this.xCoord, this.yCoord,
					this.zCoord, this.getBlockType());
		}
	}

	@Override
	public void ejectPower() {
		int currentPower = getStoredPower();

		if (hasPower()) {
			int outputCount = 0;
			for (int i = 0; i < 6; i++) {
				ForgeDirection side = ForgeDirection.getOrientation(i);
				if (canEjectOnSide(side)) {
					outputCount++;
				}
			}

			if (outputCount == 0) {
				return;
			}

			for (int i = 0; i < 6; i++) {
				ForgeDirection side = ForgeDirection.getOrientation(i);
				if (canEjectOnSide(side)) {

					int outputAmount = 0;

					if (getStoredPower() < getMaxEjectAmount()) {
						outputAmount = (int) Math.ceil((float) getStoredPower()
								/ (float) outputCount);
					} else {
						outputAmount = getMaxEjectAmount() / outputCount;
					}

					if (hasPower()) {
						ejectOnSide(side, outputAmount);
					}
				}
			}
		}
	}

	@Override
	public void ejectOnSide(ForgeDirection side, int amount) {
		int x = this.xCoord + side.offsetX;
		int y = this.yCoord + side.offsetY;
		int z = this.zCoord + side.offsetZ;

		TileEntity tile = this.worldObj.getTileEntity(x, y, z);

		if (tile instanceof IPowerAcceptor) {

			if (amount > getStoredPower()) {
				amount = getStoredPower();
			}

			IPowerAcceptor acceptor = (IPowerAcceptor) tile;

			int taken = acceptor.acceptPowerFromSide(side.getOpposite(), amount);
			
			setPowerStored(getStoredPower() - taken);
		}else if(tile instanceof ICablePowerObject){
			
			if (amount > getStoredPower()) {
				amount = getStoredPower();
			}
			
			ICablePowerObject cable = (ICablePowerObject) tile;
			int taken = cable.getAmountICanAccept(amount);
			cable.transferPower(taken);
			setPowerStored(getStoredPower() - taken);
		}
	}

	@Override
	public boolean canEjectOnSide(ForgeDirection side) {

		int x = this.xCoord + side.offsetX;
		int y = this.yCoord + side.offsetY;
		int z = this.zCoord + side.offsetZ;

		TileEntity tile = this.worldObj.getTileEntity(x, y, z);

		if (tile instanceof IPowerAcceptor) {
			IPowerAcceptor acceptor = (IPowerAcceptor) tile;

			return acceptor.canAcceptPowerFromSide(side.getOpposite());
		}
		
		if (tile instanceof ICablePowerObject) {
			return true;
		}

		return false;
	}

	@Override
	public int getMaxEjectAmount() {
		return ejectAmount;
	}

	@Override
	public void setPowerStored(int power) {
		if (power > getMaxPower()) {
			power = getMaxPower();
		}

		this.power = power;

		if (this.power < 0) {
			this.power = 0;
		}
	}

	@Override
	public int getStoredPower() {
		return power;
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}

	@Override
	public boolean hasPower() {
		return getStoredPower() > 0;
	}

	@Override
	public boolean checkConectivityForSide(ForgeDirection side) {
		return (getOrientation() != side);
	}

	@Override
	public boolean receiveClientEvent(int eventId, int eventData) {
		if (eventId == 1) {
			this.state = (byte) eventData;
			this.worldObj.func_147451_t(this.xCoord, this.yCoord, this.zCoord);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			return true;
		} else {
			return super.receiveClientEvent(eventId, eventData);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
		inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < inventory.length) {
				inventory[slotIndex] = ItemStack
						.loadItemStackFromNBT(tagCompound);
			}
		}

		deviceCookTime = nbtTagCompound.getInteger("deviceCookTime");
		power = nbtTagCompound.getInteger("power");
		totalFuelTime = nbtTagCompound.getInteger("totalFuelTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);

		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
			if (inventory[currentIndex] != null) {
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
		nbtTagCompound.setInteger("deviceCookTime", deviceCookTime);
		nbtTagCompound.setInteger("power", power);
		nbtTagCompound.setInteger("totalFuelTime", totalFuelTime);
	}

	@Override
	public int getSizeInventory() {
		return INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int decrementAmount) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) {
			if (itemStack.stackSize <= decrementAmount) {
				setInventorySlotContents(slotIndex, null);
			} else {
				itemStack = itemStack.splitStack(decrementAmount);
				if (itemStack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) {
			setInventorySlotContents(slotIndex, null);
		}
		return itemStack;
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomName() ? this.getCustomName()
				: Names.Containers.ZOROFURNACE;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.hasCustomName();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
		inventory[slotIndex] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
		switch (slotIndex) {
		case INPUT_INVENTORY_INDEX: {
			return true;
		}
		default: {
			return false;
		}
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { INPUT_INVENTORY_INDEX, INPUT_INVENTORY_INDEX + 1 };
	}

	@Override
	public boolean canInsertItem(int slotIndex, ItemStack itemStack, int side) {
		return isItemValidForSlot(slotIndex, itemStack);
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		return false;
	}

	public int getDeviceCookTime() {
		return deviceCookTime;
	}

	public int getTotalFuelTime() {
		return totalFuelTime;
	}

	public void setTotalFuelTime(int totalFuelTime) {
		this.totalFuelTime = totalFuelTime;
	}

	public void setDeviceCookTime(int deviceCookTime) {
		this.deviceCookTime = deviceCookTime;
	}

	@SideOnly(Side.CLIENT)
	public int getScaledEnergyStored(int scale) {

		return (getStoredPower() * scale) / getMaxPower();
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int scale) {
		if (this.totalFuelTime > 0) {
			return this.deviceCookTime * scale / this.totalFuelTime;
		}
		return 0;
	}
}
