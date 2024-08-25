package com.fabien_gigante.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpongeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Precipitation;
import net.minecraft.world.event.GameEvent;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpongeBlock.class)
public abstract class SpongeBlockMixin {
	private static boolean canBeWet(World world, Biome.Precipitation precipitation) {
		return precipitation == Precipitation.RAIN && world.getRandom().nextFloat() < 0.05F;
	}

	public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
		if (canBeWet(world, precipitation)) {
			world.setBlockState(pos, Blocks.WET_SPONGE.getDefaultState());
			world.emitGameEvent((Entity) null, GameEvent.BLOCK_CHANGE, pos);
		}
	}
}
