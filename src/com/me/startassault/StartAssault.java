package com.me.startassault;

import com.badlogic.gdx.Game;
import com.me.startassault.screens.GameScreen;

public class StartAssault extends Game  {

	@Override
	public void create() {
		setScreen(new GameScreen());
		
	}


}
