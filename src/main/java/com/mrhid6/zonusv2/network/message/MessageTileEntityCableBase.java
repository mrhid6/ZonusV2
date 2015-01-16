package com.mrhid6.zonusv2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

import com.mrhid6.zonusv2.tileentity.TileEntityZonus;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;
import com.mrhid6.zonusv2.utility.LogHelper;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageTileEntityCableBase implements IMessage, IMessageHandler<MessageTileEntityCableBase, IMessage>
{
    public int x, y, z;
    public int down,up,north,south,west,east;

    public MessageTileEntityCableBase()
    {
    }

    public MessageTileEntityCableBase(TileEntityCableBase tileEntityCableBase, boolean[] connections)
    {
        this.x = tileEntityCableBase.xCoord;
        this.y = tileEntityCableBase.yCoord;
        this.z = tileEntityCableBase.zCoord;
        
        this.down = (connections[0])?1:0;
        this.up = (connections[1])?1:0;
        this.north = (connections[2])?1:0;
        this.south = (connections[3])?1:0;
        this.west = (connections[4])?1:0;
        this.east = (connections[5])?1:0;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.down = buf.readInt();
        this.up = buf.readInt();
        this.north = buf.readInt();
        this.south = buf.readInt();
        this.west = buf.readInt();
        this.east = buf.readInt();
        LogHelper.info(toString());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(down);
        buf.writeInt(up);
        buf.writeInt(north);
        buf.writeInt(south);
        buf.writeInt(west);
        buf.writeInt(east);
    }

    @Override
    public IMessage onMessage(MessageTileEntityCableBase message, MessageContext ctx)
    {
        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (tileEntity instanceof TileEntityCableBase)
        {
        	TileEntityCableBase cable = (TileEntityCableBase)tileEntity;
        	System.out.println(message.west);
        	cable.setConnections(message.down, message.up, message.north, message.south, message.west, message.east);
        }

        return null;
    }

	@Override
	public String toString() {
		return "MessageTileEntityCableBase [x=" + x + ", y=" + y + ", z=" + z
				+ ", down=" + down + ", up=" + up + ", north=" + north
				+ ", south=" + south + ", west=" + west + ", east=" + east
				+ "]";
	}

    
}
