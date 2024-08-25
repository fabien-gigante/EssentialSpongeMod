package com.fabien_gigante.mixin;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.PointedDripstoneBlock.DrippingFluid;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {

	@Redirect(method = "method_33279", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	private static boolean canDryBlock_getFluid(BlockState blockState, Block block) {
		return blockState.isOf(Blocks.MUD) || blockState.isOf(Blocks.WET_SPONGE);
	}
	@Redirect(method = "dripTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
	private static boolean canDryBlock_dripTick(BlockState blockState, Block block) {
		return blockState.isOf(Blocks.MUD) || blockState.isOf(Blocks.WET_SPONGE);
	}

	@Redirect(method = "dripTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z"))
	private static boolean dryBlock_preventDefault(ServerWorld world, BlockPos pos, BlockState state) { return false; }

	@Inject(method = "dripTick",at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void dryBlock(BlockState state, ServerWorld world, BlockPos pos, float dripChance, CallbackInfo ci , Optional<DrippingFluid> optional) {
		BlockState sourceState = optional.get().sourceState(); BlockPos sourcePos = optional.get().pos();
		Block targetBlock = sourceState.isOf(Blocks.WET_SPONGE) ? Blocks.SPONGE : Blocks.CLAY;
		world.setBlockState(sourcePos, targetBlock.getDefaultState());
	}
}
