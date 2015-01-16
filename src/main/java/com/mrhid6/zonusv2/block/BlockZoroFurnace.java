package com.mrhid6.zonusv2.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
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
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

public class BlockZoroFurnace extends BlockMachine implements ITileEntityProvider{

	private IIcon[] icons = new IIcon[6];

	public BlockZoroFurnace() {
		super();

		this.setHardness(3.5F);
		this.setResistance(5.5F);
		this.setBlockName("zorofurnace");
	}

	@Override
	public int getRenderType() {
		return RenderIds.ZOROFURNACE;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityZoroFurnace();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons[0] = iconRegister.registerIcon( getTextureName() + "_off_0");
		icons[1] = iconRegister.registerIcon( getTextureName() + "_off_1");
		icons[2] = iconRegister.registerIcon( getTextureName() + "_off_2");
		icons[3] = iconRegister.registerIcon( getTextureName() + "_on_0");
		icons[4] = iconRegister.registerIcon( getTextureName() + "_on_1");
		icons[5] = iconRegister.registerIcon( getTextureName() + "_on_2");
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public IIcon getIcon(int side, int metaData) {
		if (side == 1) {
			return icons[2];
		} else if (side == 0) {
			return icons[2];
		} else if (side == 4) {
			return icons[0];
		} else {
			return icons[1];
		}
	}
	@Override
	public void breakBlock(World world, int x, int y,int z, Block block, int par6) {
		TileEntityZoroFurnace tile = (TileEntityZoroFurnace) world.getTileEntity(x, y, z);

		if (tile != null) {
			tile.dropContent(0, tile);
		}

		super.breakBlock(world, x, y, z, block, par6);
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int x, int y, int z, int blockSide) {
		TileEntity tileentity = par1IBlockAccess.getTileEntity(x, y, z);

		int orientation = ForgeDirection.SOUTH.ordinal();
		short state = 1;
		if(tileentity instanceof TileEntityZoroFurnace){
			orientation = ((TileEntityZoroFurnace)tileentity).getOrientation().ordinal();
			state = ((TileEntityZoroFurnace)tileentity).getState();
		}

		if (blockSide == 1 || blockSide == 0) {
			return (state==0)?icons[2]:icons[5];
		}else if(blockSide == orientation){
			return (state==0)?icons[0]:icons[3];
		} else {
			return (state==0)?icons[1]:icons[4];
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
