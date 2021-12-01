package com.fidget.game.DataStructures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import com.fidget.game.Settings;

import java.util.Random;

/**
 * This class is design to keep track of the finger motion and place
 * dots on the screen over time and removing the dots over time.
 * <p>
 * Created by leej431 on 6/16/17.
 */

public class Dragger {
    public int pointAndTimeCounter;     // counts how many pointAndTime objects are in the linklist
    public PointAndTime averagePointAndTime;
    public int currentTextureIndex;
    public TextureRegion textureRegion;
    public int MAX_POINT_AND_TIME_LIFE_SPAN;
    public float MAX_POINT_AND_TIME_RADIUS = 10;
    public float SCALE_OF_DRAGGER_FOR_TEXTURE = .1f;
    public com.fidget.game.DataStructures.CircularLinkedList<PointAndTime> linkedList;

    public Dragger() {
        currentTextureIndex = 0;
        this.pointAndTimeCounter = 0;
        this.averagePointAndTime = new PointAndTime(0, 0, TimeUtils.millis());
        this.MAX_POINT_AND_TIME_LIFE_SPAN = 1000;
        this.textureRegion = new TextureRegion(new Texture("dragger/dragger_" + currentTextureIndex + ".png"));
        this.linkedList = new com.fidget.game.DataStructures.CircularLinkedList<PointAndTime>();
    }

    public void addPointAndTime(PointAndTime pointAndTime) {
        pointAndTimeCounter += 1;
        pointAndTime.draggerColorIndex = currentTextureIndex;
        averagePointAndTime.x += pointAndTime.x;
        averagePointAndTime.y += pointAndTime.y;
        linkedList.addNodeAtEnd(pointAndTime);
    }

    private void cleanDeadPointAndTimeInLinkedList(long currentTime) {
        while (linkedList.getSize() > 0
                && currentTime - ((PointAndTime) linkedList.head.data).timeCreated > MAX_POINT_AND_TIME_LIFE_SPAN) {
            CircularLinkedList.Node node = linkedList.popNodeFromStart();
            if (node != null) {
                PointAndTime pointAndTime = (PointAndTime) node.data;
                averagePointAndTime.x -= pointAndTime.x;
                averagePointAndTime.y -= pointAndTime.y;
                pointAndTimeCounter -= 1;
            }
        }
    }

    public void draw(long currentTime) {

        // Remove all PointAndTime from list that are older then MAX_POINT_AND_TIME_LIFE_SPAN
        cleanDeadPointAndTimeInLinkedList(currentTime);

        // Draw first PointAndTime in the list
        if (linkedList.getSize() > 0) {
            com.fidget.game.DataStructures.CircularLinkedList<PointAndTime>.Node<PointAndTime> currentNode = linkedList.head;

            float deltaTime = (float) (currentTime - currentNode.data.timeCreated) / (float) MAX_POINT_AND_TIME_LIFE_SPAN;

            if (Settings.swipeEnabled) {
                Assets.batch.draw(
                        Assets.textureRegionArrayList.get(currentNode.data.draggerColorIndex),                                          // texture
                        // region
                        currentNode.data.x - textureRegion.getRegionWidth() / 2,    // x-pos
                        currentNode.data.y - textureRegion.getRegionHeight() / 2,   // y-pos
                        textureRegion.getRegionWidth() / 2,   // x-origin
                        textureRegion.getRegionHeight() / 2,  // y-origin
                        textureRegion.getRegionWidth(),       // width
                        textureRegion.getRegionHeight(),      // height
                        getScaleBasedOnLifeSpan(deltaTime),   // x-scale
                        getScaleBasedOnLifeSpan(deltaTime),   // y-scale
                        0);                                   // rotation angle
            }
            currentNode = currentNode.next;

            while (currentNode != linkedList.head) {
                deltaTime = (float) (currentTime - currentNode.data.timeCreated) / (float) MAX_POINT_AND_TIME_LIFE_SPAN;
                if (Settings.swipeEnabled) {
                    Assets.batch.draw(
                            Assets.textureRegionArrayList.get(currentNode.data.draggerColorIndex),
                            currentNode.data.x - textureRegion.getRegionWidth() / 2,    // x-pos
                            currentNode.data.y - textureRegion.getRegionHeight() / 2,   // y-pos
                            textureRegion.getRegionWidth() / 2,   // x-origin
                            textureRegion.getRegionHeight() / 2,  // y-origin
                            textureRegion.getRegionWidth(),       // width
                            textureRegion.getRegionHeight(),      // height
                            getScaleBasedOnLifeSpan(deltaTime),   // x-scale
                            getScaleBasedOnLifeSpan(deltaTime),   // y-scale
                            0);                                   // rotation angle
                }
                currentNode = currentNode.next;
            }
        }
    }

    private float getScaleBasedOnLifeSpan(float deltaTime) {
        return SCALE_OF_DRAGGER_FOR_TEXTURE * (MAX_POINT_AND_TIME_RADIUS - (deltaTime * MAX_POINT_AND_TIME_RADIUS));
    }

    public void changeSwipeToRandomDifferent() {
        Random r = new Random();
        int newTextureIndex = r.nextInt(Assets.textureTypeCount);
        while (newTextureIndex == currentTextureIndex) {
            newTextureIndex = r.nextInt(Assets.textureTypeCount);
        }
        currentTextureIndex = newTextureIndex;
        textureRegion = Assets.textureRegionArrayList.get(currentTextureIndex);
    }
}
