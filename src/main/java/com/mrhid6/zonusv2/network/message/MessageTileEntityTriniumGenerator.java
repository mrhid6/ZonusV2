package com.mrhid6.zonusv2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import com.mrhid6.zonusv2.tileentity.TileEntityTriniumGenerator;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;
import com.mrhid6.zonusv2.utility.LogHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEntityTriniumGenerator implements IMessage,
		IMessageHandler<MessageTileEntityTriniumGenerator, IMessage> {

	public int x, y, z;
	public byte orientation, state;
	public String customName, owner;
	public int power;

	public MessageTileEntityTriniumGenerator() {
	}

	public MessageTileEntityTriniumGenerator(
			TileEntityTriniumGenerator tileEntityZoroFurnace) {
		this.x = tileEntityZoroFurnace.xCoord;
		this.y = tileEntityZoroFurnace.yCoord;
		this.z = tileEntityZoroFurnace.zCoord;
		this.orientation = (byte) tileEntityZoroFurnace.getOrientation()
				.ordinal();
		this.state = (byte) tileEntityZoroFurnace.getState();
		this.customName = tileEntityZoroFurnace.getCustomName();
		this.owner = tileEntityZoroFurnace.getOwner();
		// this.power = tileEntityZoroFurnace.getPower();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.x = buf.readInt();
		this.y = buf.readInt();
		this.z = buf.readInt();
		this.orientation = buf.readByte();
		this.state = buf.readByte();
		int customNameLength = buf.readInt();
		this.customName = new String(buf.readBytes(customNameLength).array());
		int ownerLength = buf.readInt();
		this.owner = new String(buf.readBytes(ownerLength).array());
		// this.power = buf.readInt();
		LogHelper.info(toString());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(orientation);
		buf.writeByte(state);
		buf.writeInt(customName.length());
		buf.writeBytes(customName.getBytes());
		buf.writeInt(owner.length());
		buf.writeBytes(owner.getBytes());
		// buf.writeInt(power);
	}

	@Override
	public IMessage onMessage(MessageTileEntityTriniumGenerator message,
			MessageContext ctx) {
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld
				.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityTriniumGenerator) {
			((TileEntityTriniumGenerator) tileEntity)
					.setOrientation(message.orientation);
			((TileEntityTriniumGenerator) tileEntity).setState(message.state);
			((TileEntityTriniumGenerator) tileEntity)
					.setCustomName(message.customName);
			((TileEntityTriniumGenerator) tileEntity).setOwner(message.owner);
			// ((TileEntityZoroFurnace) tileEntity).setPower(message.power);
		}

		return null;
	}

	@Override
	public String toString() {
		return String
				.format("MessageTileEntityTriniumGenerator - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s, power:%s",
						x, y, z, orientation, state, customName, owner, power);
	}
}
