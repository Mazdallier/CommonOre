package com.genuineflix.metal.generator.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.genuineflix.metal.util.Utility;
import com.google.common.base.Predicate;

public class CommonGenMinable extends WorldGenMinable {

	public final Block ore;
	public final int size;
	public final Predicate<Block> generateOver;

	public CommonGenMinable(final Block ore, final int size) {
		this(ore, size, Utility.IS_REPLACEABLE_BLOCK);
	}

	public CommonGenMinable(final Block ore, final int size, final Predicate<Block> generateOver) {
		super(ore, size, Blocks.stone);
		this.ore = ore;
		this.size = size;
		this.generateOver = generateOver;
	}

	@Override
	public boolean generate(final World world, final Random random, final int x, final int y, final int z) {
		boolean generated = false;
		final float angle = random.nextFloat() * (float) Math.PI;
		final double d0 = x + 8 + MathHelper.sin(angle) * size / 8.0F;
		final double d1 = x + 8 - MathHelper.sin(angle) * size / 8.0F;
		final double d2 = z + 8 + MathHelper.cos(angle) * size / 8.0F;
		final double d3 = z + 8 - MathHelper.cos(angle) * size / 8.0F;
		final double d4 = y + random.nextInt(3) - 2;
		final double d5 = y + random.nextInt(3) - 2;
		for (int count = 0; count <= size; ++count) {
			final double d6 = d0 + (d1 - d0) * count / size;
			final double d7 = d4 + (d5 - d4) * count / size;
			final double d8 = d2 + (d3 - d2) * count / size;
			final double d9 = random.nextDouble() * size / 16.0D;
			final double d10 = (MathHelper.sin(count * (float) Math.PI / size) + 1.0F) * d9 + 1.0D;
			final double d11 = (MathHelper.sin(count * (float) Math.PI / size) + 1.0F) * d9 + 1.0D;
			final int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			final int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			final int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			final int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			final int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			final int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);
			for (int genX = i1; genX <= l1; ++genX) {
				final double d12 = (genX + 0.5D - d6) / (d10 / 2.0D);
				if (d12 * d12 < 1.0D)
					for (int genY = j1; genY <= i2; ++genY) {
						final double d13 = (genY + 0.5D - d7) / (d11 / 2.0D);
						if (d12 * d12 + d13 * d13 < 1.0D)
							loop: for (int genZ = k1; genZ <= j2; ++genZ) {
								final double d14 = (genZ + 0.5D - d8) / (d10 / 2.0D);
								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && generateOver.apply(world.getBlock(genX, genY, genZ))) {
									world.setBlock(genX, genY, genZ, ore, 0, 2);
									generated = true;
									continue loop;
								}
							}
					}
			}
		}
		return generated;
	}
}
