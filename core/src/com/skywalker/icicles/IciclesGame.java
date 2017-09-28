package com.skywalker.icicles;

import com.badlogic.gdx.Game;

public class IciclesGame extends Game {

	@Override
	public void create() {
		showDifficultyScreen();
	}

	public void showDifficultyScreen() {
		setScreen(new DifficultyScreen(this));
	}

	public void showIciclesScreen(Constants.Difficulty difficulty) {
		setScreen(new IciclesScreen(this, difficulty));
	}
}
