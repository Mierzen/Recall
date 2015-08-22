package com.mierzen.recall;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

/**
 * Big thanks to sham1, who was a tremendous help with this!
 */
public class MessageTeleport implements IMessage
{
    public MessageTeleport()
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    public static class PacketHandler implements IMessageHandler<MessageTeleport, IMessage>
    {


        @Override
        public IMessage onMessage(MessageTeleport message, MessageContext ctx)
        {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            World world = player.worldObj;

            System.out.println("message called");
            
            Teleport tp = new Teleport();
            tp.tryTeleport(world, (EntityPlayerMP) player);

            return null;
        }
    }
}