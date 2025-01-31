package com.github.mim1q.minecells.registry;

import com.github.mim1q.minecells.world.processor.BetterRuleStructureProcessor;
import com.mojang.serialization.Codec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public class MineCellsStructureProcessorTypes {
  public static final StructureProcessorType<BetterRuleStructureProcessor> BETTER_RULE = register(
    "better_rule",
    BetterRuleStructureProcessor.CODEC
  );

  public static void init() {}

  static <P extends StructureProcessor> StructureProcessorType<P> register(String id, Codec<P> codec) {
    return Registry.register(Registries.STRUCTURE_PROCESSOR, "minecells:" + id, () -> codec);
  }
}
