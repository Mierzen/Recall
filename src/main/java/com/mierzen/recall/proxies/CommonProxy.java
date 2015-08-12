package com.mierzen.recall.proxies;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy
{
    public EntityPlayer getPlayerEntity(MessageContext ctx) {
        //TutorialMain.logger.info("Retrieving player from CommonProxy for message on side " + ctx.side);
        return ctx.getServerHandler().playerEntity;
    }

    public void preInit(FMLPreInitializationEvent e)
    {

    }

    public void init(FMLInitializationEvent e)
    {

    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }
}
