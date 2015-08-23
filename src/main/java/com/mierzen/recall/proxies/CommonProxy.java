package com.mierzen.recall.proxies;

import com.mierzen.recall.PlayerEnterDimension;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {

    }

    public void init(FMLInitializationEvent e)
    {
        FMLCommonHandler.instance().bus().register(new PlayerEnterDimension());
    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }
}
