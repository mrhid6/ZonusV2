package com.mrhid6.zonusv2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import com.mrhid6.zonusv2.tileentity.TileEntityZonus;
import com.mrhid6.zonusv2.utility.LogHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEntityZonus implements IMessage,
		IMessageHandler<MessageTileEntityZonus, IMessage> {
	public int x, y, z;
	public byte orientation, state;
	public String customName, owner;

	public MessageTileEntityZonus() {
	}

	public MessageTileEntityZonus(TileEntityZonus tileEntityZonus) {
		this.x = tileEntityZonus.xCoord;
		this.y = tileEntityZonus.yCoord;
		this.z = tileEntityZonus.zCoord;
		this.orientation = (byte) tileEntityZonus.getOrientation().ordinal();
		this.state = (byte) tileEntityZonus.getState();
		this.customName = tileEntityZonus.getCustomName();
		this.owner = tileEntityZonus.getOwner();
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
	}

	@Override
	public IMessage onMessage(MessageTileEntityZonus message, MessageContext ctx) {
		TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld
				.getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityZonus) {
			((TileEntityZonus) tileEntity).setOrientation(message.orientation);
			((TileEntityZonus) tileEntity).setState(message.state);
			((TileEntityZonus) tileEntity).setCustomName(message.customName);
			((TileEntityZonus) tileEntity).setOwner(message.owner);
		}

		return null;
	}

	@Override
	public String toString() {
		return String
				.format("MessageTileEntityZonus - x:%s, y:%s, z:%s, orientation:%s, state:%s, customName:%s, owner:%s",
						x, y, z, orientation, state, customName, owner);
	}
}
