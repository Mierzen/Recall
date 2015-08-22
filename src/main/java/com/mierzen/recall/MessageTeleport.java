package com.mierzen.recall;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

/**
 * Big thanks to sham1, who was a tremendous help with this!
 */
public class MessageTeleport extends MessageBase<MessageTeleport>
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

    @Override
    public void handleClientSide(MessageTeleport message, EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(MessageTeleport message, EntityPlayer player)
    {
        World world = player.worldObj;

        System.out.println("message called");

        Teleport tp = new Teleport();
        tp.tryTeleport(world, (EntityPlayerMP) player);
    }
}