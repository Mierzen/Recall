package com.mierzen.recall;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class MessageParticleFX extends MessageBase<MessageParticleFX>
{
    public MessageParticleFX()
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
    public void handleClientSide(MessageParticleFX message, EntityPlayer dontUse)
    {
        Random rand = new Random();
        for(int countparticles = 0; countparticles <= 100; ++countparticles)
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            player.worldObj.spawnParticle("largesmoke", player.posX + (rand.nextDouble() - 0.5D) * (double)player.width, player.posY + rand.nextDouble() * (double)player.height - (double) player.yOffset, player.posZ + (rand.nextDouble() - 0.5D) * (double) player.width, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void handleServerSide(MessageParticleFX message, EntityPlayer player)
    {

    }
}
