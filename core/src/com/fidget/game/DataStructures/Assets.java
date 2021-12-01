package com.fidget.game.DataStructures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fidget.game.Objects.Button;
import com.fidget.game.Objects.CheckBoxButton;
import com.fidget.game.Objects.Fidget;
import com.fidget.game.Objects.FidgetItem;
import com.fidget.game.Objects.SpinCounter;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.Settings;

import java.util.ArrayList;

import static com.fidget.game.Screens.BaseScreen.getScreenHeightHalf;
import static com.fidget.game.Screens.BaseScreen.getScreenWidthHalf;

/**
 * Created by leej431 on 8/1/17.
 */

public class Assets {

    public static SpriteBatch batch;   // renders sprites - used for all screens

    public static void load() {
        batch = new SpriteBatch();
        initializePlayAssets();
        initializeSettingsAssets();
        initializeFidgetSettingsAssets();
        initializeDraggerAssets();
    }

    // Play Assets
    public static Sprite background;

    public static Fidget fidget;
    public static Dragger dragger;
    public static SpinCounter spinCounter;

    public static Pan currentPan;
    public static Pan lastPan;

    public static Button settingsButton;
    public static Button fidgetSettingsButton;

    private static void initializePlayAssets() {
        background = new Sprite(new Texture("background/splash_title.png"));
        dragger = new Dragger();

        fidget = new Fidget(Settings.fidgetModel, dragger);
        fidget.x = (int) getScreenWidthHalf();
        fidget.y = (int) getScreenHeightHalf();

        spinCounter = new SpinCounter();

        lastPan = new Pan(-99.0f, -99.0f, -99.0f, -99.0f);
        currentPan = new Pan(-98.0f, -99.0f, -99.0f, -99.0f);

        settingsButton = new Button("settings/settings_icon-gray.png",
                ScreenUtilities.getScreenPositionBasedOnPercentageX(85),
                ScreenUtilities.getScreenPositionBasedOnPercentageY(90));
        settingsButton.setScale(2f, 2f);

        fidgetSettingsButton = new Button("fidget/fidget_settings_icon.png",
                ScreenUtilities.getScreenPositionBasedOnPercentageX(5),
                ScreenUtilities.getScreenPositionBasedOnPercentageY(90));
        fidgetSettingsButton.setScale(1, 1);
    }

    /*
     * FidgetSettingsScreen's Fields
     */
    public static ArrayList<CheckBoxButton> checkBoxButtonArrayList;
    public static Button backButton;
    public static int fidgetModelAmount = 3;

    private static void initializeFidgetSettingsAssets() {
        checkBoxButtonArrayList = new ArrayList<CheckBoxButton>();
        for (int i = 0; i < fidgetModelAmount; i++) {
            checkBoxButtonArrayList.add(new CheckBoxButton("fidget/off_" + i + ".png", "fidget/on_" + i + ".png", 0, 0));
            checkBoxButtonArrayList.get(i).setScale(3f, 3f);
        }

        checkBoxButtonArrayList.get(0).setPosition(getScreenWidthHalf() - checkBoxButtonArrayList.get(0).getWidth() / 2,
                getScreenHeightHalf() + (checkBoxButtonArrayList.get(0).getHeight() + 10));
        checkBoxButtonArrayList.get(1).setPosition(getScreenWidthHalf() - checkBoxButtonArrayList.get(1).getWidth() / 2,
                getScreenHeightHalf());
        checkBoxButtonArrayList.get(2).setPosition(getScreenWidthHalf() - checkBoxButtonArrayList.get(2).getWidth() / 2,
                getScreenHeightHalf() - (checkBoxButtonArrayList.get(2).getHeight() + 80));

        backButton = new Button("settings/back_button.png", 0, 0);
        backButton.setScale(1f, 1f);
        backButton.setPosition(getScreenWidthHalf() - backButton.getWidth() / 2,
                ScreenUtilities.getScreenPositionBasedOnPercentageY(10));
    }

    private static void initializeSettingsAssets() {

    }

    /*
     * FidgetSettingsScreen's Fields
     */
    public static final int textureTypeCount = 5;
    public static ArrayList<TextureRegion> textureRegionArrayList;

    private static void initializeDraggerAssets() {
        textureRegionArrayList = new ArrayList<TextureRegion>();
        for (int i = 0; i < textureTypeCount; i++) {
            textureRegionArrayList.add(new TextureRegion(new Texture("dragger/dragger_" + i + ".png")));
        }
    }
}
