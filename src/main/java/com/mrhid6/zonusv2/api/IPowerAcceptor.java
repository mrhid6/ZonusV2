package com.mrhid6.zonusv2.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerAcceptor {
	
	public int acceptPowerFromSide(ForgeDirection side, int amount);
	
	public boolean canAcceptPowerFromSide(ForgeDirection side);
	
}
