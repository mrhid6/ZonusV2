package com.mrhid6.zonusv2.block.cable;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.ZonusV2;
import com.mrhid6.zonusv2.block.BlockZonus;
import com.mrhid6.zonusv2.creativetab.CreativeTabZonus;
import com.mrhid6.zonusv2.init.ModItems;
import com.mrhid6.zonusv2.reference.Reference;
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCableBase extends BlockZonus implements ITileEntityProvider{
	
	public BlockCableBase(Material material) {
		super(material);
		
		this.setResistance(0.5F);
		this.setHardness(0.5F);
		this.setStepSound(Block.soundTypeCloth);
		
		this.setBlockName("cablebase");
		this.setCreativeTab(null);
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.cablebase;
	}
	
	@Override
	public void addCollisionBoxesToList( World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity ) {
		setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);

		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

		TileEntityCableBase cable = (TileEntityCableBase) world.getTileEntity(x, y, z);

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y - 1, z), ForgeDirection.DOWN.getOpposite())) {
			setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.75F, 0.75F);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y + 1, z), ForgeDirection.UP.getOpposite())) {
			setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 1.0F, 0.75F);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y, z - 1), ForgeDirection.NORTH.getOpposite())) {
			setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.75F);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y, z + 1), ForgeDirection.SOUTH.getOpposite())) {
			setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 1.0F);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x - 1, y, z), ForgeDirection.WEST.getOpposite())) {
			setBlockBounds(0.0F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x + 1, y, z), ForgeDirection.EAST.getOpposite())) {
			setBlockBounds(0.25F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
			super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		}

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, int x, int y, int z ) {

		TileEntityCableBase cable = (TileEntityCableBase) world.getTileEntity(x, y, z);
		double halfThickness = cable.getCableThickness() / 2.0D;

		double minX = x + 0.5D - halfThickness;
		double minY = y + 0.5D - halfThickness;
		double minZ = z + 0.5D - halfThickness;
		double maxX = x + 0.5D + halfThickness;
		double maxY = y + 0.5D + halfThickness;
		double maxZ = z + 0.5D + halfThickness;

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x - 1, y, z), ForgeDirection.WEST.getOpposite())) {
			minX = x;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y - 1, z), ForgeDirection.DOWN.getOpposite())) {
			minY = y;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y, z - 1), ForgeDirection.NORTH.getOpposite())) {
			minZ = z;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x + 1, y, z), ForgeDirection.EAST.getOpposite())) {
			maxX = x + 1;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y + 1, z), ForgeDirection.UP.getOpposite())) {
			maxY = y + 1;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y, z + 1), ForgeDirection.SOUTH.getOpposite())) {
			maxZ = z + 1;
		}

		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z ) {

		TileEntityCableBase cable = (TileEntityCableBase) world.getTileEntity(x, y, z);

		double halfThickness = cable.getCableThickness() / 2.0D;

		float minX = (float) (0.5F - halfThickness);
		float minY = (float) (0.5F - halfThickness);
		float minZ = (float) (0.5F - halfThickness);
		float maxX = (float) (0.5F + halfThickness);
		float maxY = (float) (0.5F + halfThickness);
		float maxZ = (float) (0.5F + halfThickness);

		if (cable.canIConnectWithTileEntity(world.getTileEntity(x - 1, y, z), ForgeDirection.WEST.getOpposite())) {
			minX = 0;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y - 1, z), ForgeDirection.DOWN.getOpposite())) {
			minY = 0;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y, z - 1), ForgeDirection.NORTH.getOpposite())) {
			minZ = 0;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x + 1, y, z), ForgeDirection.EAST.getOpposite())) {
			maxX = 1;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y + 1, z), ForgeDirection.UP.getOpposite())) {
			maxY = 1;
		}
		if (cable.canIConnectWithTileEntity(world.getTileEntity(x, y, z + 1), ForgeDirection.SOUTH.getOpposite())) {
			maxZ = 1;
		}

		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);

	}
	
	public BlockCableBase(){
		this(Material.cloth);
	}
	
	public boolean isBlockNormalCube() {
		return false;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if(tileEntity instanceof TileEntityCableBase){
			TileEntityCableBase cable = (TileEntityCableBase)tileEntity;
			
			cable.updateConnections(false);
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.CABLEBASE;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityCableBase();
	}
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return ModItems.cablebase;
	}
}
