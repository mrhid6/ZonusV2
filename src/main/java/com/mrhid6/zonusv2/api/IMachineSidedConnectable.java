package com.mrhid6.zonusv2.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IMachineSidedConnectable {

	public boolean checkConectivityForSide(ForgeDirection side);

}
