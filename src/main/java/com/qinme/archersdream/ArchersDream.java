package com.qinme.archersdream;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(ArchersDream.MODID)
public class ArchersDream {
    public static final String MODID = "archersdream";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ArchersDream(IEventBus modEventBus, ModContainer modContainer) {
    }
}
