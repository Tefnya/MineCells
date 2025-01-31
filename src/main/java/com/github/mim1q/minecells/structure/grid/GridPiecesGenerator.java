package com.github.mim1q.minecells.structure.grid;

import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class GridPiecesGenerator {
  public static List<GridPiece> generatePieces(BlockPos startPos, Optional<Heightmap.Type> projectStartToHeightmap, Structure.Context context, int size, RoomGridGenerator generator) {
    List<RoomData> roomDataList = generator.generate(context);
    List<GridPiece> pieces = new ArrayList<>();
    for (RoomData data : roomDataList) {
      if (projectStartToHeightmap.isPresent() && data.terrainFit) {
        pieces.add(getTerrainFitPiece(data, startPos, projectStartToHeightmap, context, size));
      } else {
        pieces.add(new GridPiece(context, data.poolId(), startPos.add(data.pos().multiply(size)).add(data.offset()), data.rotation(), size));
      }
    }
    return pieces;
  }

  public static GridPiece getTerrainFitPiece(RoomData data, BlockPos startPos, Optional<Heightmap.Type> projectStartToHeightmap, Structure.Context context, int size) {
    BlockPos pos = startPos.add(data.pos().multiply(size));
    int heightmapY = projectStartToHeightmap.map(
      type -> context.chunkGenerator().getHeightOnGround(pos.getX() + size / 2, pos.getZ() + size / 2, type, context.world(), context.noiseConfig())
    ).orElse(0);
    int heightDiff = heightmapY - startPos.getY();
    return new GridPiece(context, data.poolId(), startPos.add(data.pos().multiply(size)).add(data.offset()).add(0, heightDiff, 0), data.rotation(), size);
  }

  public record RoomData(
    Vec3i pos,
    BlockRotation rotation,
    Identifier poolId,
    Vec3i offset,
    boolean terrainFit
  ) { }

  public static abstract class RoomGridGenerator {
    private final List<RoomData> rooms = new ArrayList<>();
    protected abstract void addRooms(Random random);

    public final List<RoomData> generate(Structure.Context context) {
      rooms.clear();
      addRooms(context.random());
      return rooms;
    }
    protected void addRoom(Vec3i pos, BlockRotation rotation, Identifier poolId, Vec3i offset) {
      rooms.add(new RoomData(pos, rotation, poolId, offset, false));
    }

    protected final void addRoom(Vec3i pos, BlockRotation rotation, Identifier poolId) {
      addRoom(pos, rotation, poolId, Vec3i.ZERO);
    }

    protected void addTerrainFitRoom(Vec3i pos, BlockRotation rotation, Identifier poolId, Vec3i offset) {
      rooms.add(new RoomData(pos, rotation, poolId, offset, true));
    }

    protected final void addTerrainFitRoom(Vec3i pos, BlockRotation rotation, Identifier poolId) {
      addTerrainFitRoom(pos, rotation, poolId, Vec3i.ZERO);
    }

    public static RoomGridGenerator single(Identifier roomId) {
      return single(roomId, Vec3i.ZERO);
    }

    public static RoomGridGenerator single(Identifier roomId, Vec3i offset) {
      return new Single(roomId, offset);
    }

    public static final class Single extends RoomGridGenerator {
      private final Identifier roomId;
      private final Vec3i offset;

      Single(Identifier roomId, Vec3i offset) {
        this.roomId = roomId;
        this.offset = offset;
      }

      @Override
      protected void addRooms(Random random) {
        addRoom(Vec3i.ZERO, BlockRotation.random(random), roomId, offset);
      }
    }
  }
}
