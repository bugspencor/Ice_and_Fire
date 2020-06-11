package com.github.alexthe666.iceandfire.world;import com.mojang.datafixers.Dynamic;import net.minecraft.block.Block;import net.minecraft.block.BlockState;import net.minecraft.block.Blocks;import net.minecraft.util.SharedSeedRandom;import net.minecraft.util.math.BlockPos;import net.minecraft.world.biome.Biome;import net.minecraft.world.chunk.IChunk;import net.minecraft.world.gen.PerlinNoiseGenerator;import net.minecraft.world.gen.surfacebuilders.BadlandsSurfaceBuilder;import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;import java.util.Arrays;import java.util.Random;import java.util.function.Function;public class SurfaceBuilderGlacier extends SurfaceBuilder<SurfaceBuilderConfig> {    private static final BlockState ICE = Blocks.ICE.getDefaultState();    private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();    private static final BlockState BLUE_ICE = Blocks.BLUE_ICE.getDefaultState();    private static final BlockState SNOW_PACKED = Blocks.SNOW_BLOCK.getDefaultState();    private static final BlockState SNOW_LAYER = Blocks.SNOW.getDefaultState();    protected BlockState[] field_215432_a;    protected long field_215433_b;    protected PerlinNoiseGenerator field_215435_c;    protected PerlinNoiseGenerator field_215437_d;    protected PerlinNoiseGenerator field_215439_e;    public SurfaceBuilderGlacier(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51314_1_) {        super(p_i51314_1_);    }    public void setSeed(long seed) {        if (this.field_215433_b != seed || this.field_215432_a == null) {            this.func_215430_b(seed);        }        if (this.field_215433_b != seed || this.field_215435_c == null || this.field_215437_d == null) {            SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);            this.field_215435_c = new PerlinNoiseGenerator(sharedseedrandom, 3, 0);            this.field_215437_d = new PerlinNoiseGenerator(sharedseedrandom, 0, 0);        }        this.field_215433_b = seed;    }    protected void func_215430_b(long p_215430_1_) {        this.field_215432_a = new BlockState[64];        Arrays.fill(this.field_215432_a, ICE);        SharedSeedRandom sharedseedrandom = new SharedSeedRandom(p_215430_1_);        this.field_215439_e = new PerlinNoiseGenerator(sharedseedrandom, 0, 0);        for(int l1 = 0; l1 < 64; ++l1) {            l1 += sharedseedrandom.nextInt(5) + 1;            if (l1 < 64) {                this.field_215432_a[l1] = BLUE_ICE;            }        }        int i2 = sharedseedrandom.nextInt(4) + 2;        for(int i = 0; i < i2; ++i) {            int j = sharedseedrandom.nextInt(3) + 1;            int k = sharedseedrandom.nextInt(64);            for(int l = 0; k + l < 64 && l < j; ++l) {                this.field_215432_a[k + l] = PACKED_ICE;            }        }        int j2 = sharedseedrandom.nextInt(4) + 2;        for(int k2 = 0; k2 < j2; ++k2) {            int i3 = sharedseedrandom.nextInt(3) + 2;            int l3 = sharedseedrandom.nextInt(64);            for(int i1 = 0; l3 + i1 < 64 && i1 < i3; ++i1) {                this.field_215432_a[l3 + i1] = BLUE_ICE;            }        }        int l2 = sharedseedrandom.nextInt(4) + 2;        for(int j3 = 0; j3 < l2; ++j3) {            int i4 = sharedseedrandom.nextInt(3) + 1;            int k4 = sharedseedrandom.nextInt(64);            for(int j1 = 0; k4 + j1 < 64 && j1 < i4; ++j1) {                this.field_215432_a[k4 + j1] = PACKED_ICE;            }        }        int k3 = sharedseedrandom.nextInt(3) + 3;        int j4 = 0;        for(int l4 = 0; l4 < k3; ++l4) {            int i5 = 1;            j4 += sharedseedrandom.nextInt(16) + 4;            for(int k1 = 0; j4 + k1 < 64 && k1 < 1; ++k1) {                this.field_215432_a[j4 + k1] = BLUE_ICE;                if (j4 + k1 > 1 && sharedseedrandom.nextBoolean()) {                    this.field_215432_a[j4 + k1 - 1] = ICE;                }                if (j4 + k1 < 63 && sharedseedrandom.nextBoolean()) {                    this.field_215432_a[j4 + k1 + 1] = PACKED_ICE;                }            }        }    }    protected BlockState func_215431_a(int p_215431_1_, int p_215431_2_, int p_215431_3_) {        int i = (int)Math.round(this.field_215439_e.noiseAt((double)p_215431_1_ / 512.0D, (double)p_215431_3_ / 512.0D, false) * 2.0D);        return this.field_215432_a[(p_215431_2_ + i + 64) % 64];    }    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {        double d0 = 0.0D;        double d1 = Math.min(Math.abs(noise), this.field_215435_c.noiseAt((double)x * 0.25D, (double)z * 0.25D, false) * 15.0D);        if (d1 > 0.0D) {            double d2 = 0.001953125D;            double d3 = Math.abs(this.field_215437_d.noiseAt((double)x * 0.001953125D, (double)z * 0.001953125D, false));            d0 = d1 * d1 * 2.5D;            double d4 = Math.ceil(d3 * 50.0D) + 14.0D;            if (d0 > d4) {                d0 = d4;            }            d0 = d0 + 64.0D;        }        int l = x & 15;        int i = z & 15;        BlockState blockstate2 = WHITE_TERRACOTTA;        BlockState blockstate = biomeIn.getSurfaceBuilderConfig().getUnder();        int i1 = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);        boolean flag = Math.cos(noise / 3.0D * Math.PI) > 0.0D;        int j = -1;        boolean flag1 = false;        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();        for(int k = Math.max(startHeight, (int)d0 + 1); k >= 0; --k) {            blockpos$mutable.setPos(l, k, i);            if (chunkIn.getBlockState(blockpos$mutable).isAir() && k < (int)d0) {                chunkIn.setBlockState(blockpos$mutable, defaultBlock, false);            }            BlockState blockstate1 = chunkIn.getBlockState(blockpos$mutable);            if (blockstate1.isAir()) {                j = -1;            } else if (blockstate1.getBlock() == defaultBlock.getBlock()) {                if (j == -1) {                    flag1 = false;                    if (i1 <= 0) {                        blockstate2 = Blocks.AIR.getDefaultState();                        blockstate = defaultBlock;                    } else if (k >= seaLevel - 4 && k <= seaLevel + 1) {                        blockstate2 = WHITE_TERRACOTTA;                        blockstate = biomeIn.getSurfaceBuilderConfig().getUnder();                    }                    if (k < seaLevel && (blockstate2 == null || blockstate2.isAir())) {                        blockstate2 = defaultFluid;                    }                    j = i1 + Math.max(0, k - seaLevel);                    if (k >= seaLevel - 1) {                        if (k <= seaLevel + 3 + i1) {                            chunkIn.setBlockState(blockpos$mutable, biomeIn.getSurfaceBuilderConfig().getTop(), false);                            flag1 = true;                        } else {                            BlockState blockstate3;                            if (k >= 64 && k <= 127) {                                if (flag) {                                    blockstate3 = ICE;                                } else {                                    blockstate3 = this.func_215431_a(x, k, z);                                }                            } else {                                blockstate3 = BLUE_ICE;                            }                            chunkIn.setBlockState(blockpos$mutable, blockstate3, false);                        }                    } else {                        chunkIn.setBlockState(blockpos$mutable, blockstate, false);                        Block block = blockstate.getBlock();                        if (block == Blocks.PACKED_ICE || block == Blocks.ICE) {                            chunkIn.setBlockState(blockpos$mutable, BLUE_ICE, false);                        }                    }                } else if (j > 0) {                    --j;                    if (flag1) {                        chunkIn.setBlockState(blockpos$mutable, PACKED_ICE, false);                    } else {                        chunkIn.setBlockState(blockpos$mutable, this.func_215431_a(x, k, z), false);                    }                }            }        }    }}