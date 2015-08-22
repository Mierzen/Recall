package com.mierzen.recall;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Random;

public class MessageParticleFX extends MessageBase<MessageParticleFX>
{
    double x, y, z;

    public MessageParticleFX()
    {
    }

    public MessageParticleFX(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }

    @Override
    public void handleClientSide(MessageParticleFX message, EntityPlayer dontUse)
    {
        Random rand = new Random();
        for(int countparticles = 0; countparticles <= 100; ++countparticles)
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            player.worldObj.spawnParticle("largesmoke", message.x + (rand.nextDouble() - 0.5D) * (double)player.width, message.y + rand.nextDouble() * (double)player.height - (double) player.yOffset, message.z + (rand.nextDouble() - 0.5D) * (double) player.width, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void handleServerSide(MessageParticleFX message, EntityPlayer player)
    {

    }
}
