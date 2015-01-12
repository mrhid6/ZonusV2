package com.mrhid6.zonusv2.creativetab;

import com.mrhid6.zonusv2.init.ModItems;
import com.mrhid6.zonusv2.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabZonus {
	public static final CreativeTabs ZONUS_TAB = new CreativeTabs(Reference.MOD_ID.toLowerCase()) {
		
		@Override
		public Item getTabIconItem() {
			return ModItems.zoroIngot;
		}
	};
}
