# Archer's Dream

**English** | [中文](README_zh.md)

Archer's Dream is a Minecraft mod for NeoForge 1.21.1.

It makes arrow collision follow the visible solid parts of blocks, so arrows can pass through gaps in fences, windows in doors, and the hollow interior of bells—while still being stopped by actual wood, iron bars, door panels, or bell walls.

## Features

- All arrows (excluding tridents) can pass through gaps in fences and fence gates
- Can pass through the bar gaps in iron bars
- Can pass through small windows in door and trapdoor textures
- Can pass through visual gaps in walls
- Can pass through the hollow interior of bells
- Glass panes and stained glass panes keep their normal thin solid collision
- Caches common block-state shapes to reduce repeated calculation while arrows are flying

## Supported Blocks

| Block | Effect |
| --- | --- |
| Fence | Uses centre posts plus upper/lower rails, so arrows can pass between rails |
| Fence Gate | Uses gate posts and rails; arrows can pass through gaps even when closed |
| Iron Bars | Uses vertical/horizontal bar strips from the texture, leaving gaps for arrows |
| Wall | Uses visual shape, allowing arrows through upper/side visual gaps |
| Door | Removes the small transparent window regions from the door texture |
| Trapdoor | Removes the small transparent window regions from the trapdoor texture |
| Bell | Uses a hollow bell body plus attachment collision |

## How It Works

Vanilla arrows use the normal block collision shape when checking whether they hit a block. Blocks like fences, iron bars, walls, and bells often have collision shapes that are more filled-in than their actual models, so arrows aimed at a gap can hit an invisible box.

This mod uses a Mixin to intercept block collision shape queries:

- The refined arrow shape is only used when the entity is an arrow-type projectile (not a trident)
- Players, mobs, and other projectiles keep the vanilla behaviour

Doors and trapdoors are handled slightly differently. Their models are solid thin slabs, but their textures have transparent windows. This mod includes the vanilla window data and subtracts those window areas from the collision shape.

## Project Structure

```text
src/main/java/com/qinme/archersdream/
├── ArchersDream.java                 # Main mod class
├── mixin/
│   └── ArrowProjectileCollisionMixin.java
└── util/
    ├── ArrowBlockShapes.java         # Arrow-specific block collision shapes
    └── DoorWindowShapes.java         # Door/trapdoor texture window data
```

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).
