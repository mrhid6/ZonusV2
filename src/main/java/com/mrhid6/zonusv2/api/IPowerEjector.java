package com.mrhid6.zonusv2.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IPowerEjector {

	public void ejectPower();

	public void ejectOnSide(ForgeDirection side, int amount);

	public boolean canEjectOnSide(ForgeDirection side);

	public int getMaxEjectAmount();

}
