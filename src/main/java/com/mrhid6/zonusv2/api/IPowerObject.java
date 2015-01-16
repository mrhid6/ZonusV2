package com.mrhid6.zonusv2.api;

public interface IPowerObject {
	
	public void setPowerStored(int amount);
	public int getStoredPower();
	
	public int getMaxPower();
	
	public boolean hasPower();
	
}
