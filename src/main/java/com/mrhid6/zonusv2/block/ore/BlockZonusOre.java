package com.mrhid6.zonusv2.block.ore;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.mrhid6.zonusv2.block.BlockZonus;
import com.mrhid6.zonusv2.reference.Materials;
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.utility.ItemHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZonusOre extends BlockZonus {

	public BlockZonusOre() {
		this.setHardness(3.5F);
		this.setResistance(5.5F);
		this.setBlockName("multiOre");

		setHarvestLevel("pickaxe", 2);
	}

	IIcon[] icons = new IIcon[4];

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

	@Override
	public int getRenderType() {
		return RenderIds.ZONUSORES;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

		/*
		 * if (metadata == Materials.NoxiteOre.getItemDamage()) { ItemStack a =
		 * InventoryUtils.copyStack(Materials.NoxiteCystal, 1); a.stackSize = (1
		 * + Utils.getRandomInt(3) % (2 + fortune)); drops.add(a); int dustCount
		 * = Utils.getRandomInt(3) % (3 + fortune); if (dustCount > 0) { a =
		 * InventoryUtils.copyStack(Materials.NoxiteDust, 1); a.stackSize =
		 * dustCount; drops.add(a); } } else if (metadata ==
		 * Materials.StearilliumOreBlock.getItemDamage()) { ItemStack a =
		 * InventoryUtils.copyStack(Materials.StearilliumOre, 1); a.stackSize =
		 * (1 + Utils.getRandomInt(3) % (2 + fortune)); drops.add(a); } else {
		 */
		ItemStack a = null;
		if (metadata == 0) {
			a = ItemHelper.cloneItemStack(Materials.ZoroOre, 1);
		}
		/*
		 * if (metadata == 1) { a =
		 * InventoryUtils.copyStack(Materials.TriniumOre, 1); }
		 */

		drops.add(a);
		// }
		return drops;

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess par1iBlockAccess, int x, int y, int z,
			int side) {
		return getIcon(side, par1iBlockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		if (par2 < icons.length) {
			return icons[par2];
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item par1, CreativeTabs tab, List subItems) {
		subItems.add(new ItemStack(this, 1, 0));
		// subItems.add(new ItemStack(this, 1, 1));
		// subItems.add(new ItemStack(this, 1, 2));
		// subItems.add(new ItemStack(this, 1, 3));
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {

		String prefix = getTextureName();
		icons[0] = iconRegister.registerIcon(prefix + ".zoroore");
		/*
		 * icons[1] = iconRegister.registerIcon(Zonus.Modname + "triniumore");
		 * icons[2] = iconRegister.registerIcon(Zonus.Modname + "noxiteore");
		 * icons[3] = iconRegister.registerIcon(Zonus.Modname +
		 * "stearilliumore");
		 */

	}

}
