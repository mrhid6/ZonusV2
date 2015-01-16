package com.mrhid6.zonusv2.item;

import java.util.Iterator;
import java.util.List;

import com.mrhid6.zonusv2.tileentity.TileEntityZonus;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemZonusDebug extends ItemZonus{
	
	
	public ItemZonusDebug() {
		super();
		this.setUnlocalizedName("zonusdebug");
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		
		if(world.isRemote){
			MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
			
			if(mop == null){
				this.onItemUseFirst(itemStack, player, world, 0, 0, 0, -1, 0, 0, 0);
			}else{
				
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				
				if(world.getBlock(x, y, z).isAir(world, x, y, z))
					this.onItemUseFirst(itemStack, player, world, 0, 0, 0, -1, 0, 0, 0);
				
			}
		}
		
		return itemStack;
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		
		
		if(world.isRemote){
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile != null && tile instanceof TileEntityCableBase){
				
				TileEntityCableBase cable = (TileEntityCableBase)tile;
				
				player.addChatMessage(new ChatComponentText(tile.getClass().getName()));
				
				for(int i=0;i<6;i++){
					player.addChatMessage(new ChatComponentText(ForgeDirection.getOrientation(i)+""+cable.getConnections()[i]));
				}
			}
		}
		return false;
	}
	
	
}
