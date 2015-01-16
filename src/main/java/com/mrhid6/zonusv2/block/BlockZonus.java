package com.mrhid6.zonusv2.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.creativetab.CreativeTabZonus;
import com.mrhid6.zonusv2.reference.Reference;
import com.mrhid6.zonusv2.tileentity.TileEntityZonus;
import com.mrhid6.zonusv2.utility.LogHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZonus extends Block{
	
	public BlockZonus(Material material) {
		super(material);

		this.setHardness(89.3F);
		this.setResistance(89.5F);
		this.setCreativeTab(CreativeTabZonus.ZONUS_TAB);
	}
	
	
	public BlockZonus(){
		this(Material.rock);
	}
	
	@Override
	public String getUnlocalizedName() {
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	public String getUnwrappedUnlocalizedName(String unlocalizedName){
		return unlocalizedName.substring(unlocalizedName.indexOf(".")+1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(getTextureName());
	}
	
	protected String getTextureName(){
		return this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1);
	}
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
		
        if (world.getTileEntity(x, y, z) instanceof TileEntityZonus)
        {
            int direction = 0;
            int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

            if (facing == 0)
            {
                direction = ForgeDirection.NORTH.ordinal();
            }
            else if (facing == 1)
            {
                direction = ForgeDirection.EAST.ordinal();
            }
            else if (facing == 2)
            {
                direction = ForgeDirection.SOUTH.ordinal();
            }
            else if (facing == 3)
            {
                direction = ForgeDirection.WEST.ordinal();
            }

            if (itemStack.hasDisplayName())
            {
                ((TileEntityZonus) world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
            }

            ((TileEntityZonus) world.getTileEntity(x, y, z)).setOrientation(direction);
        }
    }
	
}
