package com.mrhid6.zonusv2.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.api.IMachineSidedConnectable;
import com.mrhid6.zonusv2.api.IPowerAcceptor;
import com.mrhid6.zonusv2.api.IPowerObject;
import com.mrhid6.zonusv2.init.ModBlocks;
import com.mrhid6.zonusv2.init.Recipes;
import com.mrhid6.zonusv2.network.PacketHandler;
import com.mrhid6.zonusv2.network.message.MessageTileEntityZoroFurnace;
import com.mrhid6.zonusv2.reference.Names;
import com.mrhid6.zonusv2.utility.ItemHelper;

import cpw.mods.fml.common.network.NetworkRegistry;

public class TileEntityZoroFurnace extends TileEntityZonus implements ISidedInventory, IPowerAcceptor, IPowerObject, IMachineSidedConnectable{

	public static final int INPUT_INVENTORY_INDEX = 0;
	public static final int INVENTORY_SIZE = 2;
	public static final int OUTPUT_INVENTORY_INDEX = 1;

	private ItemStack[] inventory;
	private boolean isActive = false;

	private final int maxpower = 5000;
	private int power = 0;
	private int processCurrent = 0;
	private int processFinal = 100;

	private ItemStack[] processInv;
	private boolean wasActive = false;

	//public ItemStack outputItemStack;

	public TileEntityZoroFurnace() {
		inventory = new ItemStack[INVENTORY_SIZE];
		processInv = new ItemStack[1];
	}

	@Override
	public int acceptPowerFromSide(ForgeDirection side, int amount) {
		
		int currentPower = getStoredPower();
		
		setPowerStored(getStoredPower() + amount);
		return getStoredPower() - currentPower;
	}

	@Override
	public boolean canAcceptPowerFromSide(ForgeDirection side) {
		return (getOrientation() != side);
	}

	@Override
	public boolean canExtractItem(int slotIndex, ItemStack itemStack, int side) {
		return slotIndex == OUTPUT_INVENTORY_INDEX;
	}

	private boolean canFinish() {
		if (processCurrent < processFinal) {
			return false;
		}

		ItemStack output = Recipes.ZoroFurnaceRecipes.getResultFor(processInv[0]);

		if (output == null) {
			processCurrent = 0;
			isActive = false;
			return true;
		}

		if (inventory[OUTPUT_INVENTORY_INDEX] == null) {
			return true;
		}
		if (!inventory[OUTPUT_INVENTORY_INDEX].isItemEqual(output)) {
			return false;
		}
		int result = Integer.valueOf(inventory[OUTPUT_INVENTORY_INDEX].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= getInventoryStackLimit()) && (result <= output.getMaxStackSize());
	}

	@Override
	public boolean canInsertItem(int slotIndex, ItemStack itemStack, int side) {
		return isItemValidForSlot(slotIndex, itemStack);
	}

	public boolean canStart() {

		if (!hasPower() || power<processFinal) {
			return false;
		}

		ItemStack output = Recipes.ZoroFurnaceRecipes.getResultFor(inventory[INPUT_INVENTORY_INDEX]);

		if (output == null) {
			return false;
		}

		if (inventory[OUTPUT_INVENTORY_INDEX] == null) {
			return true;
		}

		if (!inventory[OUTPUT_INVENTORY_INDEX].isItemEqual(output)) {
			return false;
		}

		int result = Integer.valueOf(inventory[OUTPUT_INVENTORY_INDEX].stackSize) + Integer.valueOf(output.stackSize);
		return (result <= output.getMaxStackSize());
	}
	
	@Override
	public boolean checkConectivityForSide(ForgeDirection side) {
		if(side == orientation) return false;
		return true;
	}

	@Override
	public void closeInventory() {}

	@Override
	public ItemStack decrStackSize(int slotIndex, int decrementAmount) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			if (itemStack.stackSize <= decrementAmount)
			{
				setInventorySlotContents(slotIndex, null);
			}
			else
			{
				itemStack = itemStack.splitStack(decrementAmount);
				if (itemStack.stackSize == 0)
				{
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return itemStack;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == ForgeDirection.DOWN.ordinal() ? new int[]{OUTPUT_INVENTORY_INDEX} : new int[]{INPUT_INVENTORY_INDEX, OUTPUT_INVENTORY_INDEX};
	}

	@Override
	public Packet getDescriptionPacket()
	{
		return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityZoroFurnace(this));
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomName() ? this.getCustomName() : Names.Containers.ZOROFURNACE;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public int getMaxPower() {
		return maxpower;
	}

	public int getProcessCurrent() {
		return processCurrent;
	}

	public int getProcessFinal() {
		return processFinal;
	}

	public int getScaledEnergyStored(int scale) {
		
		return (getStoredPower() * scale) / getMaxPower();
	}

	public int getScaledProgress(int scale) {
		if( getProcessCurrent() == 0 || getProcessFinal() == 0 ) return 0;
		
		return ( getProcessCurrent() *scale) / getProcessFinal();
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null)
		{
			setInventorySlotContents(slotIndex, null);
		}
		return itemStack;
	}

	public int getStoredPower() {
		return power;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.hasCustomName();
	}

	@Override
	public boolean hasPower(){
		return getStoredPower() > 0;
	}

	@Override
	public boolean isItemValidForSlot(int slotIndex, ItemStack itemStack) {
		switch (slotIndex)
		{
		case INPUT_INVENTORY_INDEX:
		{
			return true;
		}
		default:
		{
			return false;
		}
		}
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {}

	private void processFinish() {
		ItemStack output = Recipes.ZoroFurnaceRecipes.getResultFor(processInv[0]);

		if (inventory[OUTPUT_INVENTORY_INDEX] == null) {
			inventory[OUTPUT_INVENTORY_INDEX] = output;
		} else {
			inventory[OUTPUT_INVENTORY_INDEX].stackSize += output.stackSize;
		}

	}

	private void processStart() {
		processInv[INPUT_INVENTORY_INDEX] = ItemHelper.cloneItemStack(inventory[INPUT_INVENTORY_INDEX],1);

		processFinal = 5;

		inventory[INPUT_INVENTORY_INDEX].stackSize -= 1;
		if (inventory[INPUT_INVENTORY_INDEX].stackSize <= 0) {
			inventory[INPUT_INVENTORY_INDEX] = null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound)
	{
		super.readFromNBT(nbtTagCompound);

		// Read in the ItemStacks in the inventory from NBT
		NBTTagList tagList = nbtTagCompound.getTagList(Names.NBT.ITEMS, 10);
		inventory = new ItemStack[this.getSizeInventory()];
		for (int i = 0; i < tagList.tagCount(); ++i)
		{
			NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
			byte slotIndex = tagCompound.getByte("Slot");
			if (slotIndex >= 0 && slotIndex < inventory.length)
			{
				inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
			}
		}

		processCurrent = nbtTagCompound.getInteger("processCurrent");
		power = nbtTagCompound.getInteger("power");
		processFinal = nbtTagCompound.getInteger("processFinal");
	}

	@Override
    public boolean receiveClientEvent(int eventId, int eventData)
    {
        if (eventId == 1)
        {
            this.state = (byte) eventData;
            this.worldObj.func_147451_t(this.xCoord, this.yCoord, this.zCoord);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return true;
        }
        else
        {
            return super.receiveClientEvent(eventId, eventData);
        }
    }
	
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
		inventory[slotIndex] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	
	public void setPowerStored(int power) {
		
		if(power > getMaxPower()){
			power = getMaxPower();
		}
		
		this.power = power;
		
		if(this.power < 0){
			this.power = 0;
		}
	}

	public void setProcessCurrent(int processCurrent) {
		this.processCurrent = processCurrent;
	}

	public void setProcessFinal(int processFinal) {
		this.processFinal = processFinal;
	}

	@Override
	public void updateEntity() {

		boolean sendUpdate = false;

		if(!this.worldObj.isRemote){

			boolean curActive = isActive;

			if(isActive){
				if(processCurrent < processFinal){
					processCurrent++;
					power-=5;
				}
				if(canFinish()){
					processFinish();
					processCurrent = 0;

					if ((canStart())) {
						processStart();
						processCurrent += 1;
					} else {
						processCurrent = 0;
						isActive = false;
						wasActive = true;
					}
				}
			}else{
				if (canStart()) {
					processStart();
					processCurrent += 1;
					isActive = true;
				}
			}

			if ((curActive != isActive) && (isActive == true)) {
				sendUpdate = true;
			} else if ((wasActive)) {
				wasActive = false;
				sendUpdate = true;
			}
		}

		if (sendUpdate)
		{
			this.markDirty();
			this.state = this.isActive ? (byte) 1 : (byte) 0;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, ModBlocks.zoroFurnace, 1, this.state);
			PacketHandler.INSTANCE.sendToAllAround(new MessageTileEntityZoroFurnace(this), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, (double) this.xCoord, (double) this.yCoord, (double) this.zCoord, 128d));
			this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound)
	{
		super.writeToNBT(nbtTagCompound);

		// Write the ItemStacks in the inventory to NBT
		NBTTagList tagList = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex)
		{
			if (inventory[currentIndex] != null)
			{
				NBTTagCompound tagCompound = new NBTTagCompound();
				tagCompound.setByte("Slot", (byte) currentIndex);
				inventory[currentIndex].writeToNBT(tagCompound);
				tagList.appendTag(tagCompound);
			}
		}
		nbtTagCompound.setTag(Names.NBT.ITEMS, tagList);
		nbtTagCompound.setInteger("processFinal", processFinal);
		nbtTagCompound.setInteger("power", power);
		nbtTagCompound.setInteger("processCurrent", processCurrent);
	}

}
