package com.mrhid6.zonusv2.api;

public interface ICablePowerObject {
	
	public int getAmountICanAccept(int amount);
	public int getStoredPowerBuffer();
	public boolean hasPower();
	public void transferPower(int amount);
	
	public void setStoredPowerBuffer(int power);
	public int getMaxPowerBuffer();
}
