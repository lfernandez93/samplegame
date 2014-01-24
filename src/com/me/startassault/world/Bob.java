package com.me.startassault.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.startassault.world.Bob.State;

public class Bob {

    public enum State {

        IDLE, WALKING, JUMPING, DYING
    }

    public static final float SPEED = 4f;	// unit per second
    static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f; // half a unit

    Vector2 position = new Vector2();
    Vector2 acceleration = new Vector2();
    Vector2 velocity = new Vector2();
    Rectangle bounds = new Rectangle();
    State state = State.IDLE;
    boolean facingLeft = true;

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    float stateTime = 0;

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }

    public Vector2 getAcceleration() {
        return this.acceleration;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public void update(float delta) {
        stateTime += delta;
        //position.add(velocity.cpy().scl(delta));
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Bob(Vector2 position) {
        this.position = position;
        this.bounds.x = position.x;
        this.bounds.y = position.y;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }

    public void setFacingLeft(boolean b) {
        this.facingLeft = b;

    }

    public State getState() {
        return state;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public void setState(State state) {
        this.state = state;

    }
}
