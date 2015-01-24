package com.mrhid6.zonusv2.tileentity.cable;

public class TileEntityZoroCable extends TileEntityCableBase {

	@Override
	public float getCableThickness() {
		return 4f / 16.0f;
	}

	@Override
	public boolean canConnectWithOtherCable(TileEntityCableBase cable) {
		boolean out = true;

		if (cable instanceof TileEntityCableBase) {
			out = false;
		}

		if (cable instanceof TileEntityZoroCable) {
			out = true;
		}

		return out;
	}
}
