package com.mrhid6.zonusv2.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.mrhid6.zonusv2.client.gui.inventory.GuiZoroFurnace;
import com.mrhid6.zonusv2.inventory.ContainerZoroFurnace;
import com.mrhid6.zonusv2.reference.GUIs;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;
import com.mrhid6.zonusv2.utility.LogHelper;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        if (id == GUIs.ZOROFURNACE.ordinal())
        {
        	TileEntityZoroFurnace tileEntityZoroFurnace = (TileEntityZoroFurnace) world.getTileEntity(x, y, z);
            return new ContainerZoroFurnace(entityPlayer.inventory, tileEntityZoroFurnace);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer entityPlayer, World world, int x, int y, int z)
    {
        if (id == GUIs.ZOROFURNACE.ordinal())
        {
            TileEntityZoroFurnace tileEntityZoroFurnace = (TileEntityZoroFurnace) world.getTileEntity(x, y, z);
            return new GuiZoroFurnace(entityPlayer.inventory, tileEntityZoroFurnace);
        }
        return null;
    }
}
