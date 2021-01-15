package com.feri.hungryshark2d.config;

public class GameConfig {

  //shark
  public static final float SHARK_WIDTH = 200;
  public static final float SHARK_HEIGHT = 140;
  public static final float SHARK_SPEED = 500;

  //mine
  public static final float MINE_WIDTH = 100;
  public static final float MINE_HEIGHT = 100;
  public static float MINE_SPEED = 500;
  public static final long MINE_SPAWN_TIME = 2000000000;

  //fish
  public static final float FISH_SPEED = 500;
  public static final float FISH_WIDTH = 100;
  public static final float FISH_HEIGHT = 100;
  public static final long FISH_SPAWN_TIME = 2000000000;

  //screen
  public static float WIDTH = 800;
  public static float HEIGHT = 350;

  public static boolean debug = false;

  private GameConfig() {
  }
}
