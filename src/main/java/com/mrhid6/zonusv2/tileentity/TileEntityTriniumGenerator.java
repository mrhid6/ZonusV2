package com.mrhid6.zonusv2.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.api.IMachineConnectable;
import com.mrhid6.zonusv2.api.IPowerEjector;
import com.mrhid6.zonusv2.api.IPowerObject;

public class TileEntityTriniumGenerator extends TileEntityZonus implements IPowerObject, IPowerEjector, IMachineConnectable{

	private int ejectAmount = 80;
	private int power = 0;
	private int maxPower = 10000;
	
	public static final int INVENTORY_SIZE = 2;
	public static final int INPUT_INVENTORY_INDEX = 0;
	public static final int UPGRADE_INVENTORY_INDEX = 1;

	@Override
	public void ejectPower() {
		
		if(hasPower()){
			
		}
	}

	
	@Override
	public void ejectOnSide(ForgeDirection side, int amount) {
		
	}

	@Override
	public void canEjectOnSide(ForgeDirection side) {
		
	}

	@Override
	public int maxEjectAmount() {
		return ejectAmount;
	}

	@Override
	public void setPowerStored(int power) {
		if(power > getMaxPower()){
			power = getMaxPower();
		}
		
		this.power = power;
		
		if(this.power < 0){
			this.power = 0;
		}
	}

	@Override
	public int getStoredPower() {
		return power;
	}

	@Override
	public int getMaxPower() {
		return maxPower;
	}

	@Override
	public boolean hasPower() {
		return getStoredPower() > 0;
	}
}
