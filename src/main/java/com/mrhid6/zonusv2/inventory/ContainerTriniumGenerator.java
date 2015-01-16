package com.mrhid6.zonusv2.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import com.mrhid6.zonusv2.tileentity.TileEntityTriniumGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerTriniumGenerator extends ContainerZonus{
	
	private TileEntityTriniumGenerator tileEntityTrinGen;
	private int lastDeviceCookTime;
	private int lastPower;
	private int lastTotalFuelBurn;
	
	public ContainerTriniumGenerator(InventoryPlayer inventoryPlayer, TileEntityTriniumGenerator tileEntityTrinGen) {
		
		this.tileEntityTrinGen = tileEntityTrinGen;
		
		addSlotToContainer(new Slot(tileEntityTrinGen, TileEntityTriniumGenerator.INPUT_INVENTORY_INDEX, 56, 24));
		
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
        iCrafting.sendProgressBarUpdate(this, 0, this.tileEntityTrinGen.getStoredPower());
        iCrafting.sendProgressBarUpdate(this, 1, this.tileEntityTrinGen.getDeviceCookTime());
        iCrafting.sendProgressBarUpdate(this, 2, this.tileEntityTrinGen.getTotalFuelTime());
    }
	
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters)
        {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastPower != this.tileEntityTrinGen.getStoredPower())
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntityTrinGen.getStoredPower());
            }

            if (this.lastDeviceCookTime != this.tileEntityTrinGen.getDeviceCookTime())
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntityTrinGen.getDeviceCookTime());
            }

            if (this.lastTotalFuelBurn != this.tileEntityTrinGen.getTotalFuelTime())
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntityTrinGen.getTotalFuelTime());
            }
        }

        this.lastPower = this.tileEntityTrinGen.getStoredPower();
        this.lastDeviceCookTime = this.tileEntityTrinGen.getDeviceCookTime();
        this.lastTotalFuelBurn = this.tileEntityTrinGen.getTotalFuelTime();
    }
	
	 @SideOnly(Side.CLIENT)
	    public void updateProgressBar(int valueType, int updatedValue)
	    {
	        if (valueType == 0)
	        {
	            this.tileEntityTrinGen.setPowerStored(updatedValue);
	        }

	        if (valueType == 1)
	        {
	            this.tileEntityTrinGen.setDeviceCookTime(updatedValue);
	        }

	        if (valueType == 2)
	        {
	            this.tileEntityTrinGen.setTotalFuelTime(updatedValue);
	        }
	    }
	
	 @Override
		public ItemStack transferStackInSlot( EntityPlayer player, int i ) {
			ItemStack itemstack = null;
			Slot slot = (Slot) inventorySlots.get(i);

			int invTile = tileEntityTrinGen.INVENTORY_SIZE;
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
					if (TileEntityFurnace.isItemFuel(stackInSlot)) {
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
