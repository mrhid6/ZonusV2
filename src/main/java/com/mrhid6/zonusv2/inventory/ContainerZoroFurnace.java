package com.mrhid6.zonusv2.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.mrhid6.zonusv2.init.Recipes;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerZoroFurnace extends ContainerZonus{

	private TileEntityZoroFurnace tileEntityZoroFurnace;
	private int lastprocessCurrent;
	private int lastPower;
	private int lastprocessFinal;

	public ContainerZoroFurnace(InventoryPlayer inventoryPlayer, TileEntityZoroFurnace tileEntityZoroFurnace) {

		this.tileEntityZoroFurnace = tileEntityZoroFurnace;

		addSlotToContainer(new Slot(tileEntityZoroFurnace, TileEntityZoroFurnace.INPUT_INVENTORY_INDEX, 56, 24));
		addSlotToContainer(new Slot(tileEntityZoroFurnace, TileEntityZoroFurnace.OUTPUT_INVENTORY_INDEX, 116, 33)
		{
			@Override
			public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack)
			{
				super.onPickupFromSlot(entityPlayer, itemStack);
				FMLCommonHandler.instance().firePlayerCraftingEvent(entityPlayer, itemStack, inventory);
			}

			@Override
			public boolean isItemValid(ItemStack itemStack)
			{
				return false;
			}
		});

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting iCrafting)
	{
		super.addCraftingToCrafters(iCrafting);
		iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityZoroFurnace.getStoredPower());
		iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityZoroFurnace.getProcessCurrent());
		iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityZoroFurnace.getProcessFinal());
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (Object crafter : this.crafters)
		{
			ICrafting icrafting = (ICrafting) crafter;

			if (this.lastPower != this.tileEntityZoroFurnace.getStoredPower())
			{
				icrafting.sendProgressBarUpdate(this, 0, this.tileEntityZoroFurnace.getStoredPower());
			}

			if (this.lastprocessCurrent != this.tileEntityZoroFurnace.getProcessCurrent())
			{
				icrafting.sendProgressBarUpdate(this, 1, this.tileEntityZoroFurnace.getProcessCurrent());
			}

			if (this.lastprocessFinal != this.tileEntityZoroFurnace.getProcessFinal())
			{
				icrafting.sendProgressBarUpdate(this, 2, this.tileEntityZoroFurnace.getProcessFinal());
			}
		}

		this.lastPower = this.tileEntityZoroFurnace.getStoredPower();
		this.lastprocessCurrent = this.tileEntityZoroFurnace.getProcessCurrent();
		this.lastprocessFinal = this.tileEntityZoroFurnace.getProcessFinal();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int valueType, int updatedValue)
	{
		if (valueType == 0)
		{
			this.tileEntityZoroFurnace.setPowerStored(updatedValue);
		}

		if (valueType == 1)
		{
			this.tileEntityZoroFurnace.setProcessCurrent(updatedValue);
		}

		if (valueType == 2)
		{
			this.tileEntityZoroFurnace.setProcessFinal(updatedValue);
		}
	}

	@Override
	public ItemStack transferStackInSlot( EntityPlayer player, int i ) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);

		int invTile = tileEntityZoroFurnace.INVENTORY_SIZE;
		int invPlayer = invTile + 27;
		int invFull = invTile + 36;

		if ((slot != null) && (slot.getHasStack())) {
			ItemStack stackInSlot = slot.getStack();
			itemstack = stackInSlot.copy();

			if (i == 1) {
				if (!mergeItemStack(stackInSlot, invTile, invFull, true)) {
					return null;
				}
			} else if (i != 0) {
				if (Recipes.ZoroFurnaceRecipes.getResultFor(stackInSlot) != null) {
					if (!mergeItemStack(stackInSlot, 0, 1, false)) {
						return null;
					}
				} else if ((i >= invTile) && (i < invPlayer)) {
					if (!mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
						return null;
					}
				} else if ((i >= invPlayer) && (i < invFull) && (!mergeItemStack(stackInSlot, invTile, invPlayer, false))) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, invTile, invFull, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stackInSlot.stackSize == itemstack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, stackInSlot);
		}
		return itemstack;
	}

}
