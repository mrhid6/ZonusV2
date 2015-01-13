package com.mrhid6.zonusv2.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.ZonusV2;
import com.mrhid6.zonusv2.reference.GUIs;
import com.mrhid6.zonusv2.tileentity.TileEntityZonus;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;
import com.mrhid6.zonusv2.utility.LogHelper;

public class BlockZoroFurnace extends BlockMachine implements ITileEntityProvider{

	private IIcon[] icons = new IIcon[3];

	public BlockZoroFurnace() {
		super();
		this.setBlockName("zorofurnace");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityZoroFurnace();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon( getTextureName() + "_top");
		icons[1] = iconRegister.registerIcon( getTextureName() + "_front_on");
		icons[2] = iconRegister.registerIcon( getTextureName() + "_side_on");
	}

	@Override
	public IIcon getIcon(int side, int metaData) {
		if (side == 1) {
			return icons[0];
		} else if (side == 0) {
			return icons[0];
		} else if (side == 4) {
			return icons[2];
		} else {
			return icons[1];
		}
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int x, int y, int z, int blockSide) {
		TileEntity tileentity = par1IBlockAccess.getTileEntity(x, y, z);

		int orientation = ForgeDirection.SOUTH.ordinal();

		if(tileentity instanceof TileEntityZonus){
			orientation = ((TileEntityZonus)tileentity).getOrientation().ordinal();
		}

		if (blockSide == 1) {
			return icons[0];
		} else if (blockSide == 0) {
			return icons[0];
		} else if(blockSide == orientation){
			return icons[1];
		} else {
			return icons[2];
		}
	}
	
	@Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventData)
    {
        super.onBlockEventReceived(world, x, y, z, eventId, eventData);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(eventId, eventData);
    }

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int faceHit, float par7, float par8, float par9)
	{
		if (player.isSneaking())
		{
			return false;
		}
		else
		{
			if (!world.isRemote)
			{
				if (world.getTileEntity(x, y, z) instanceof TileEntityZoroFurnace)
				{
					player.openGui(ZonusV2.instance, GUIs.ZOROFURNACE.ordinal(), world, x, y, z);
				}
			}
			return true;
		}
	}



}
