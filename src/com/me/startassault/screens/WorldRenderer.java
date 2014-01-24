package com.me.startassault.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.me.startassault.world.Block;
import com.me.startassault.world.Bob;
import com.me.startassault.world.Bob.State;
import com.me.startassault.world.World;

public class WorldRenderer {

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;

    private World world;
    private OrthographicCamera cam;

    /**
     * for debug rendering *
     */
    ShapeRenderer debugRenderer = new ShapeRenderer();
    private static final float RUNNING_FRAME_DURATION = 0.06f;

    /**
     * Textures *
     */
    private Texture bobTexture;
    private TextureRegion bobIdleLeft;
    private TextureRegion bobIdleRight;
    private TextureRegion blockTexture;
    private TextureRegion bobFrame;
    private SpriteBatch spriteBatch;
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;
    private boolean debug = false;
    private int width;
    private int height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis

    public void setSize(int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float) width / CAMERA_WIDTH;
        ppuY = (float) height / CAMERA_HEIGHT;
    }

    public WorldRenderer(World world, boolean debug) {
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
        loadTextures();
    }

    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("com/me/startassault/image/sprite/entities.atlas"));
        bobIdleRight = atlas.findRegion("character1");
        bobIdleLeft = new TextureRegion(bobIdleRight);
        bobIdleLeft.flip(true, false);
        blockTexture = atlas.findRegion("block");
        TextureRegion[] walkLeftFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            walkLeftFrames[i] = atlas.findRegion("character" + (i + 1));
        }
        walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

        TextureRegion[] walkRightFrames = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {

            walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
            walkRightFrames[i].flip(true, false);
        }
        walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
    }

    public void render() {
        spriteBatch.begin();
        drawBlocks();
        drawBob();
        spriteBatch.end();
        drawCollisionBlocks();
        //System.out.println(debug);
        if (debug) {
            drawDebug();
        }
    }

    private void drawCollisionBlocks() {
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        debugRenderer.setColor(new Color(1, 1, 1, 1));
        for (Rectangle rect : world.getCollisionRects()) {
            debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        debugRenderer.end();

    }

    private void drawBlocks() {
        for (Block block : world.getDrawableBlocks((int) CAMERA_WIDTH, (int) CAMERA_HEIGHT)) {
            spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
        }
    }

    private void drawBob() {
        Bob bob = world.getBob();
        bobFrame = bob.isFacingLeft() ? bobIdleLeft : bobIdleRight;
        if (bob.getState().equals(State.WALKING)) {
            bobFrame = bob.isFacingLeft() ? walkRightAnimation.getKeyFrame(bob.getStateTime(), true) : walkLeftAnimation.getKeyFrame(bob.getStateTime(), true);
        }
        spriteBatch.draw(bobFrame, bob.getPosition().x * ppuX, bob.getPosition().y * ppuY, Bob.SIZE * ppuX, Bob.SIZE * ppuY);
    }

    private void drawDebug() {
        // render blocks
        // render blocks
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        for (Block block : world.getDrawableBlocks((int) CAMERA_WIDTH, (int) CAMERA_HEIGHT)) {
            Rectangle rect = block.getBounds();
            //System.out.println(rect.x+""+rect.y);
   //spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }
        // render Bob
        Bob bob = world.getBob();
        Rectangle rect = bob.getBounds();
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        debugRenderer.end();
    }
}
