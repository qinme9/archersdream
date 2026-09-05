package com.qinme.archersdream.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class DoorWindowShapes {
    private DoorWindowShapes() {
    }

    private static final Map<String, List<int[]>> WINDOW_RECTS = createWindowRects();

    private static Map<String, List<int[]>> createWindowRects() {
        Map<String, List<int[]>> windowRects = new HashMap<>();
        windowRects.put("acacia_door_bottom", List.of(new int[]{3,5,1,13}, new int[]{7,9,1,13}, new int[]{11,13,1,13}));
        windowRects.put("acacia_door_top", List.of(new int[]{3,5,3,15}, new int[]{7,9,3,15}, new int[]{11,13,3,15}));
        windowRects.put("acacia_trapdoor", List.of(new int[]{2,4,3,13}, new int[]{7,9,3,13}, new int[]{12,14,3,13}));
        windowRects.put("bamboo_door_top", List.of(new int[]{4,5,4,5}, new int[]{6,10,4,5}, new int[]{11,12,4,5}, new int[]{4,5,6,7}, new int[]{11,12,6,7}, new int[]{4,5,8,9}, new int[]{11,12,8,9}, new int[]{4,5,10,11}, new int[]{11,12,10,11}, new int[]{6,10,6,13}, new int[]{4,5,12,13}, new int[]{11,12,12,13}, new int[]{4,5,14,15}, new int[]{6,10,14,15}, new int[]{11,12,14,15}));
        windowRects.put("bamboo_trapdoor", List.of(new int[]{3,4,3,4}, new int[]{5,6,3,4}, new int[]{7,9,3,4}, new int[]{10,11,3,4}, new int[]{12,13,3,4}, new int[]{3,4,5,6}, new int[]{5,6,5,6}, new int[]{10,11,5,6}, new int[]{12,13,5,6}, new int[]{7,9,5,7}, new int[]{3,4,7,9}, new int[]{5,11,7,9}, new int[]{12,13,7,9}, new int[]{7,9,9,11}, new int[]{3,4,10,11}, new int[]{5,6,10,11}, new int[]{10,11,10,11}, new int[]{12,13,10,11}, new int[]{3,4,12,13}, new int[]{5,6,12,13}, new int[]{7,9,12,13}, new int[]{10,11,12,13}, new int[]{12,13,12,13}));
        windowRects.put("cherry_door_bottom", List.of(new int[]{5,6,0,1}, new int[]{10,11,0,1}));
        windowRects.put("cherry_door_top", List.of(new int[]{5,6,4,5}, new int[]{10,11,4,5}, new int[]{4,7,5,6}, new int[]{9,12,5,6}, new int[]{5,6,6,7}, new int[]{10,11,6,7}, new int[]{7,9,7,9}, new int[]{5,6,9,10}, new int[]{10,11,9,10}, new int[]{4,7,10,11}, new int[]{9,12,10,11}, new int[]{5,6,11,12}, new int[]{10,11,11,12}, new int[]{7,9,12,14}, new int[]{5,6,14,15}, new int[]{10,11,14,15}, new int[]{4,7,15,16}, new int[]{9,12,15,16}));
        windowRects.put("cherry_trapdoor", List.of(new int[]{4,6,4,6}, new int[]{7,9,4,6}, new int[]{10,12,4,6}, new int[]{4,6,7,9}, new int[]{7,9,7,9}, new int[]{10,12,7,9}, new int[]{4,6,10,12}, new int[]{7,9,10,12}, new int[]{10,12,10,12}));
        windowRects.put("copper_door_top", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("copper_trapdoor", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{7,9,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("crimson_trapdoor", List.of(new int[]{3,13,3,4}, new int[]{3,13,7,9}, new int[]{3,13,12,13}));
        windowRects.put("exposed_copper_door_top", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("exposed_copper_trapdoor", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{7,9,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("iron_door_top", List.of(new int[]{3,7,3,6}, new int[]{9,13,3,6}, new int[]{3,7,8,11}, new int[]{9,13,8,11}));
        windowRects.put("iron_trapdoor", List.of(new int[]{3,6,3,6}, new int[]{10,13,3,6}, new int[]{3,6,10,13}, new int[]{10,13,10,13}));
        windowRects.put("jungle_door_top", List.of(new int[]{5,6,4,5}, new int[]{10,11,4,5}, new int[]{7,9,3,8}, new int[]{4,6,5,8}, new int[]{10,12,5,8}, new int[]{6,10,10,11}, new int[]{7,9,11,12}));
        windowRects.put("jungle_trapdoor", List.of(new int[]{5,6,3,4}, new int[]{10,11,3,4}, new int[]{4,6,4,5}, new int[]{10,12,4,5}, new int[]{7,9,3,7}, new int[]{3,6,5,7}, new int[]{10,13,5,7}, new int[]{4,6,9,11}, new int[]{10,12,9,11}, new int[]{7,9,9,12}, new int[]{5,6,11,12}, new int[]{10,11,11,12}));
        windowRects.put("mangrove_trapdoor", List.of(new int[]{6,10,5,6}, new int[]{5,11,6,10}, new int[]{6,10,10,11}));
        windowRects.put("oak_door_top", List.of(new int[]{3,7,3,6}, new int[]{9,13,3,6}, new int[]{3,7,8,11}, new int[]{9,13,8,11}));
        windowRects.put("oak_trapdoor", List.of(new int[]{3,6,3,6}, new int[]{10,13,3,6}, new int[]{3,6,10,13}, new int[]{10,13,10,13}));
        windowRects.put("oxidized_copper_door_top", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("oxidized_copper_trapdoor", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{7,9,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("warped_trapdoor", List.of(new int[]{6,8,3,4}, new int[]{11,13,3,4}, new int[]{3,4,3,5}, new int[]{10,13,4,5}, new int[]{3,5,5,6}, new int[]{7,9,4,7}, new int[]{4,6,6,8}, new int[]{3,5,8,9}, new int[]{11,12,5,10}, new int[]{7,10,7,10}, new int[]{6,9,10,12}, new int[]{11,13,10,12}, new int[]{3,4,9,13}, new int[]{7,9,12,13}, new int[]{10,11,12,13}, new int[]{12,13,12,13}));
        windowRects.put("weathered_copper_door_top", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        windowRects.put("weathered_copper_trapdoor", List.of(new int[]{5,11,3,4}, new int[]{6,10,4,5}, new int[]{3,4,5,6}, new int[]{7,9,5,6}, new int[]{12,13,5,6}, new int[]{3,5,6,7}, new int[]{11,13,6,7}, new int[]{3,6,7,9}, new int[]{7,9,7,9}, new int[]{10,13,7,9}, new int[]{3,5,9,10}, new int[]{11,13,9,10}, new int[]{3,4,10,11}, new int[]{7,9,10,11}, new int[]{12,13,10,11}, new int[]{6,10,11,12}, new int[]{5,11,12,13}));
        return windowRects;
    }

    public static VoxelShape apply(BlockState state, VoxelShape base) {
        List<int[]> windows = getWindowRects(state);
        if (windows == null || windows.isEmpty()) {
            return base;
        }

        VoxelShape holes = Shapes.empty();
        AABB bounds = base.bounds();
        Direction.Axis thinAxis = thinAxis(bounds);

        for (int[] rect : windows) {
            double u0 = rect[0];
            double u1 = rect[1];
            double v0 = rect[2];
            double v1 = rect[3];
            double topY0 = 16.0 - v1;
            double topY1 = 16.0 - v0;

            double x0;
            double y0;
            double z0;
            double x1;
            double y1;
            double z1;

            if (thinAxis == Direction.Axis.X) {
                x0 = bounds.minX * 16.0;
                x1 = bounds.maxX * 16.0;
                y0 = topY0;
                y1 = topY1;
                z0 = u0;
                z1 = u1;
            } else if (thinAxis == Direction.Axis.Z) {
                z0 = bounds.minZ * 16.0;
                z1 = bounds.maxZ * 16.0;
                y0 = topY0;
                y1 = topY1;
                x0 = u0;
                x1 = u1;
            } else {
                y0 = bounds.minY * 16.0;
                y1 = bounds.maxY * 16.0;
                x0 = u0;
                x1 = u1;
                z0 = v0;
                z1 = v1;
            }

            holes = Shapes.or(holes, Block.box(x0, y0, z0, x1, y1, z1));
        }

        return Shapes.join(base, holes, BooleanOp.ONLY_FIRST);
    }

    private static Direction.Axis thinAxis(AABB bounds) {
        double x = bounds.getXsize();
        double y = bounds.getYsize();
        double z = bounds.getZsize();
        if (x < y && x < z) {
            return Direction.Axis.X;
        } else if (z < x && z < y) {
            return Direction.Axis.Z;
        } else {
            return Direction.Axis.Y;
        }
    }

    private static List<int[]> getWindowRects(BlockState state) {
        ResourceLocation key = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (key == null) {
            return null;
        }

        String path = key.getPath();
        if (state.getBlock() instanceof DoorBlock) {
            return state.getValue(DoorBlock.HALF) == DoubleBlockHalf.UPPER
                ? WINDOW_RECTS.get(path + "_top")
                : WINDOW_RECTS.get(path + "_bottom");
        }

        if (state.getBlock() instanceof TrapDoorBlock) {
            return WINDOW_RECTS.get(path);
        }

        return null;
    }
}
