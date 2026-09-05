# Archer's Dream

Archer's Dream 是一个 NeoForge 1.21.1 模组，目标是让箭矢能穿过方块上的真实空隙，而不是被粗略的碰撞盒挡住。

## 功能

- 普通箭和光灵箭可以穿过栅栏、栅栏门、铁栏杆、墙的空隙
- 可以穿过门和活版门上的小窗
- 钟（Bell）使用空心碰撞，箭矢可以穿过内部
- 只会影响普通箭和光灵箭，不影响玩家、生物、物品、其它投射物和原版机制

## 安装

1. 安装 NeoForge 1.21.1
2. 将 `archersdream-1.0.0.jar` 放入 `mods` 文件夹
3. 启动游戏

## 构建

需要 JDK 21：

```bash
./gradlew build
```

构建产物位于 `build/libs/archersdream-1.0.0.jar`。

## 许可

MIT License
