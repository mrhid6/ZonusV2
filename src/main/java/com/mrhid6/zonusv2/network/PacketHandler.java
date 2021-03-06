package com.mrhid6.zonusv2.network;

import com.mrhid6.zonusv2.network.message.MessageTileEntityCableBase;
import com.mrhid6.zonusv2.network.message.MessageTileEntityTriniumGenerator;
import com.mrhid6.zonusv2.network.message.MessageTileEntityZonus;
import com.mrhid6.zonusv2.network.message.MessageTileEntityZoroFurnace;
import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE
			.newSimpleChannel(Reference.MOD_ID.toLowerCase());

	public static void init() {
		INSTANCE.registerMessage(MessageTileEntityZonus.class,
				MessageTileEntityZonus.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityZoroFurnace.class,
				MessageTileEntityZoroFurnace.class, 1, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityCableBase.class,
				MessageTileEntityCableBase.class, 2, Side.CLIENT);
		INSTANCE.registerMessage(MessageTileEntityTriniumGenerator.class,
				MessageTileEntityTriniumGenerator.class, 3, Side.CLIENT);
	}
}
