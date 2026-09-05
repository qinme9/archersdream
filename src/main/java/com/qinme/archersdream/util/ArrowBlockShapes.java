package com.qinme.archersdream.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ArrowBlockShapes {
    private static final Map<BlockState, VoxelShape> FENCE_SHAPES = new ConcurrentHashMap<>();
    private static final Map<BlockState, VoxelShape> FENCE_GATE_SHAPES = new ConcurrentHashMap<>();
    private static final Map<BlockState, VoxelShape> IRON_BAR_SHAPES = new ConcurrentHashMap<>();
    private static final Map<BlockState, VoxelShape> DOOR_WINDOW_SHAPES = new ConcurrentHashMap<>();
    private static final Map<BlockState, VoxelShape> BELL_SHAPES = new ConcurrentHashMap<>();

    private ArrowBlockShapes() {
    }

    @Nullable
    public static VoxelShape getArrowShape(BlockState state, BlockGetter level, BlockPos pos) {
        Block block = state.getBlock();
        if (block instanceof DoorBlock || block instanceof TrapDoorBlock) {
            return DOOR_WINDOW_SHAPES.computeIfAbsent(state, s -> {
                VoxelShape base = s.getShape(level, pos);
                return DoorWindowShapes.apply(s, base);
            });
        }
        if (block instanceof BellBlock) {
            return BELL_SHAPES.computeIfAbsent(state, ArrowBlockShapes::bellShape);
        }
        if (block instanceof FenceBlock) {
            return cachedFenceShape(state);
        }
        if (block instanceof IronBarsBlock) {
            return block == Blocks.IRON_BARS ? cachedIronBarsShape(state) : state.getShape(level, pos);
        }
        if (block instanceof CrossCollisionBlock) {
            return state.getShape(level, pos);
        }
        if (block instanceof FenceGateBlock) {
            return cachedFenceGateShape(state);
        }
        if (block instanceof WallBlock) {
            return state.getShape(level, pos);
        }
        return null;
    }

    private static VoxelShape cachedFenceShape(BlockState state) {
        return FENCE_SHAPES.computeIfAbsent(state, ArrowBlockShapes::fenceShape);
    }

    private static VoxelShape cachedFenceGateShape(BlockState state) {
        return FENCE_GATE_SHAPES.computeIfAbsent(state, ArrowBlockShapes::fenceGateShape);
    }

    private static VoxelShape cachedIronBarsShape(BlockState state) {
        return IRON_BAR_SHAPES.computeIfAbsent(state, ArrowBlockShapes::ironBarsShape);
    }

    private static VoxelShape bellShape(BlockState state) {
        VoxelShape bellBody = Shapes.or(
            Block.box(4.0, 4.0, 4.0, 12.0, 6.0, 12.0),
            Block.box(5.0, 6.0, 5.0, 11.0, 13.0, 11.0)
        );
        VoxelShape cavity = Block.box(6.0, 4.0, 6.0, 10.0, 13.0, 10.0);
        VoxelShape shell = Shapes.join(bellBody, cavity, BooleanOp.ONLY_FIRST);
        return Shapes.or(shell, bellAttachmentShape(state));
    }

    private static VoxelShape bellAttachmentShape(BlockState state) {
        Direction facing = state.getValue(BellBlock.FACING);
        BellAttachType attachment = state.getValue(BellBlock.ATTACHMENT);
        boolean zAxis = facing.getAxis() == Direction.Axis.Z;
        return switch (attachment) {
            case CEILING -> Block.box(7.0, 13.0, 7.0, 9.0, 16.0, 9.0);
            case DOUBLE_WALL -> zAxis
                ? Block.box(7.0, 13.0, 0.0, 9.0, 15.0, 16.0)
                : Block.box(0.0, 13.0, 7.0, 16.0, 15.0, 9.0);
            case SINGLE_WALL -> switch (facing) {
                case NORTH -> Block.box(7.0, 13.0, 0.0, 9.0, 15.0, 13.0);
                case SOUTH -> Block.box(7.0, 13.0, 3.0, 9.0, 15.0, 16.0);
                case EAST -> Block.box(3.0, 13.0, 7.0, 16.0, 15.0, 9.0);
                case WEST -> Block.box(0.0, 13.0, 7.0, 13.0, 15.0, 9.0);
                default -> Shapes.empty();
            };
            case FLOOR -> zAxis
                ? Shapes.or(
                    Block.box(0.0, 0.0, 6.0, 2.0, 16.0, 10.0),
                    Block.box(14.0, 0.0, 6.0, 16.0, 16.0, 10.0),
                    Block.box(2.0, 13.0, 7.0, 14.0, 15.0, 9.0)
                )
                : Shapes.or(
                    Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 2.0),
                    Block.box(6.0, 0.0, 14.0, 10.0, 16.0, 16.0),
                    Block.box(7.0, 13.0, 2.0, 9.0, 15.0, 14.0)
                );
        };
    }

    private static VoxelShape ironBarsShape(BlockState state) {
        VoxelShape shape = Shapes.or(
            Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
        );
        if (state.getValue(CrossCollisionBlock.NORTH)) {
            shape = addIronBarsNorth(shape);
        }
        if (state.getValue(CrossCollisionBlock.SOUTH)) {
            shape = addIronBarsSouth(shape);
        }
        if (state.getValue(CrossCollisionBlock.WEST)) {
            shape = addIronBarsWest(shape);
        }
        if (state.getValue(CrossCollisionBlock.EAST)) {
            shape = addIronBarsEast(shape);
        }
        return shape;
    }

    private static VoxelShape addIronBarsNorth(VoxelShape shape) {
        return Shapes.or(
            shape,
            Block.box(7.0, 0.0, 2.0, 9.0, 16.0, 4.0),
            Block.box(7.0, 2.0, 2.0, 9.0, 4.0, 9.0),
            Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 4.0)
        );
    }

    private static VoxelShape addIronBarsSouth(VoxelShape shape) {
        return Shapes.or(
            shape,
            Block.box(7.0, 0.0, 12.0, 9.0, 16.0, 14.0),
            Block.box(7.0, 7.0, 12.0, 9.0, 9.0, 16.0),
            Block.box(7.0, 12.0, 7.0, 9.0, 14.0, 14.0)
        );
    }

    private static VoxelShape addIronBarsWest(VoxelShape shape) {
        return Shapes.or(
            shape,
            Block.box(2.0, 0.0, 7.0, 4.0, 16.0, 9.0),
            Block.box(2.0, 2.0, 7.0, 9.0, 4.0, 9.0),
            Block.box(0.0, 7.0, 7.0, 4.0, 9.0, 9.0)
        );
    }

    private static VoxelShape addIronBarsEast(VoxelShape shape) {
        return Shapes.or(
            shape,
            Block.box(12.0, 0.0, 7.0, 14.0, 16.0, 9.0),
            Block.box(12.0, 7.0, 7.0, 16.0, 9.0, 9.0),
            Block.box(7.0, 12.0, 7.0, 14.0, 14.0, 9.0)
        );
    }

    private static VoxelShape fenceShape(BlockState state) {
        VoxelShape shape = Shapes.or(
            Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0)
        );
        if (state.getValue(CrossCollisionBlock.NORTH)) {
            shape = Shapes.or(
                shape,
                Block.box(7.0, 6.0, 0.0, 9.0, 9.0, 9.0),
                Block.box(7.0, 12.0, 0.0, 9.0, 15.0, 9.0)
            );
        }
        if (state.getValue(CrossCollisionBlock.SOUTH)) {
            shape = Shapes.or(
                shape,
                Block.box(7.0, 6.0, 7.0, 9.0, 9.0, 16.0),
                Block.box(7.0, 12.0, 7.0, 9.0, 15.0, 16.0)
            );
        }
        if (state.getValue(CrossCollisionBlock.WEST)) {
            shape = Shapes.or(
                shape,
                Block.box(0.0, 6.0, 7.0, 9.0, 9.0, 9.0),
                Block.box(0.0, 12.0, 7.0, 9.0, 15.0, 9.0)
            );
        }
        if (state.getValue(CrossCollisionBlock.EAST)) {
            shape = Shapes.or(
                shape,
                Block.box(7.0, 6.0, 7.0, 16.0, 9.0, 9.0),
                Block.box(7.0, 12.0, 7.0, 16.0, 15.0, 9.0)
            );
        }
        return shape;
    }

    private static VoxelShape fenceGateShape(BlockState state) {
        if (state.getValue(FenceGateBlock.OPEN)) {
            return Shapes.empty();
        }
        boolean inWall = state.getValue(FenceGateBlock.IN_WALL);
        Direction.Axis axis = state.getValue(FenceGateBlock.FACING).getAxis();
        return axis == Direction.Axis.X ? fenceGateShapeAlongX(inWall) : fenceGateShapeAlongZ(inWall);
    }

    private static VoxelShape fenceGateShapeAlongZ(boolean inWall) {
        VoxelShape shape = Shapes.empty();
        if (inWall) {
            shape = addGateZParts(
                shape,
                2.0, 13.0,
                3.0, 12.0,
                3.0, 6.0,
                9.0, 12.0
            );
        } else {
            shape = addGateZParts(
                shape,
                5.0, 16.0,
                6.0, 15.0,
                6.0, 9.0,
                12.0, 15.0
            );
        }
        return shape;
    }

    private static VoxelShape fenceGateShapeAlongX(boolean inWall) {
        VoxelShape shape = Shapes.empty();
        if (inWall) {
            shape = addGateXParts(
                shape,
                2.0, 13.0,
                3.0, 12.0,
                3.0, 6.0,
                9.0, 12.0
            );
        } else {
            shape = addGateXParts(
                shape,
                5.0, 16.0,
                6.0, 15.0,
                6.0, 9.0,
                12.0, 15.0
            );
        }
        return shape;
    }

    private static VoxelShape addGateZParts(
        VoxelShape shape,
        double outerY0,
        double outerY1,
        double innerY0,
        double innerY1,
        double lowerY0,
        double lowerY1,
        double upperY0,
        double upperY1
    ) {
        shape = Shapes.or(shape,
            Block.box(0.0, outerY0, 7.0, 2.0, outerY1, 9.0),
            Block.box(14.0, outerY0, 7.0, 16.0, outerY1, 9.0),
            Block.box(6.0, innerY0, 7.0, 8.0, innerY1, 9.0),
            Block.box(8.0, innerY0, 7.0, 10.0, innerY1, 9.0),
            Block.box(2.0, lowerY0, 7.0, 6.0, lowerY1, 9.0),
            Block.box(10.0, lowerY0, 7.0, 14.0, lowerY1, 9.0),
            Block.box(2.0, upperY0, 7.0, 6.0, upperY1, 9.0),
            Block.box(10.0, upperY0, 7.0, 14.0, upperY1, 9.0)
        );
        return shape;
    }

    private static VoxelShape addGateXParts(
        VoxelShape shape,
        double outerY0,
        double outerY1,
        double innerY0,
        double innerY1,
        double lowerY0,
        double lowerY1,
        double upperY0,
        double upperY1
    ) {
        shape = Shapes.or(shape,
            Block.box(7.0, outerY0, 0.0, 9.0, outerY1, 2.0),
            Block.box(7.0, outerY0, 14.0, 9.0, outerY1, 16.0),
            Block.box(7.0, innerY0, 6.0, 9.0, innerY1, 8.0),
            Block.box(7.0, innerY0, 8.0, 9.0, innerY1, 10.0),
            Block.box(7.0, lowerY0, 2.0, 9.0, lowerY1, 6.0),
            Block.box(7.0, lowerY0, 10.0, 9.0, lowerY1, 14.0),
            Block.box(7.0, upperY0, 2.0, 9.0, upperY1, 6.0),
            Block.box(7.0, upperY0, 10.0, 9.0, upperY1, 14.0)
        );
        return shape;
    }
}
