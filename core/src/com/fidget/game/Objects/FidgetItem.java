package com.fidget.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.fidget.game.DataStructures.Assets;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.Settings;

/**
 * Created by j.lee on 9/23/17.
 */

public class FidgetItem {
    // stats
    public int id;
    public int cost;
    public int level;
    public int upgradeCount;
    public boolean isPurchased;
    public int currentPointsPerSpin;
    public int pointsPerLevelIncrement;
    public String textureRegionFileName;
    public int currentCost;

    // fidget model
    public TextureRegion fidgetModel;
    public int x;
    public int y;

    // offset to add to the x/y
    public int offsetX;
    public int offsetY;

    // button buy
    public Button btnBuy;
    public int btnBuyXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(20);
    public int btnBuyYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(2f);

    // button upgrade
    public UpgradeButton btnUpgrade;
    public int btnUpgradeXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(20);
    public int btnUpgradeYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(4f);

    // text level
    public Text text_level;
    public int textLevelXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(-5f);
    public int textLevelYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(2f);

    // text cost
    public Text text_cost;
    public int textCostXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(40f);
    public int textCostYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(6.5f);

    // text upgrade
    public Text text_upgrade;
    public int textUpgradeXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(47f);
    public int textUpgradeYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(7f);

    // text upgrade
    public Text text_upgrade_cost;
    public int textUpgradeCostXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(20);
    public int textUpgradeCostYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(3);

    // button select
    public Button btnSelect;
    public int btnSelectXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(-10f);
    public int btnSelectYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(0f);

    public Button selectLabel;
    public int selectLabelXOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(-8f);
    public int selectLabelYOffset = (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(0f);


    public FidgetItem() {
        this(0, 0, 1, 0, false, 1, 1, "fidget/on_1.png");
    }

    public FidgetItem(int id, int cost, int level, int upgradeCount,
                      boolean isPurchased, int currentPointsPerSpin,
                      int pointsPerLevelIncrement, String textureRegionFileName) {
        this.id = id;
        this.cost = cost;
        this.level = level;
        this.upgradeCount = upgradeCount;
        this.isPurchased = isPurchased;
        this.currentPointsPerSpin = currentPointsPerSpin;
        this.pointsPerLevelIncrement = pointsPerLevelIncrement;
        this.textureRegionFileName = textureRegionFileName;
        this.currentCost = this.cost;

        this.fidgetModel = new TextureRegion(new Texture(textureRegionFileName));

        this.x = 0;
        this.y = 0;

        this.btnUpgrade = new UpgradeButton("storesettings/upgrade_off.png",
                "storesettings/upgrade_on.png",
                this.x,
                this.y);
        float scaleOfButtonUpgrade = 2;
        this.btnUpgrade.setScale(scaleOfButtonUpgrade, scaleOfButtonUpgrade);
        this.btnUpgrade.setAssignedID(id);

        this.btnBuy = new Button("storesettings/buy.png",
                this.x,
                this.y);
        this.btnBuy.setScaleByScreenPercentage(15f, 5f);
        this.btnBuy.setAssignedID(this.id);

        this.btnSelect = new Button("storesettings/transparent.png");
        this.btnSelect.setAssignedID(id);
        this.btnSelect.setScaleByScreenPercentage(85f, 12f);

        this.selectLabel = new Button("storesettings/selectedArrow.png");
        this.selectLabel.setAssignedID(id);
        this.selectLabel.setScaleByScreenPercentage(10f, 12f);

        // level
        text_level = new Text(3f, this.x, this.y);

        // cost
        this.text_cost = new Text(2f, this.x, this.y);

        // cost
        this.text_upgrade = new Text(2f, this.x, this.y);

        // cost
        this.text_upgrade_cost = new Text(1f, this.x, this.y);
    }

    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        text_cost.setOffset(textCostXOffset + offsetX, textCostYOffset + offsetY);
        btnBuy.setOffset(btnBuyXOffset + offsetX, btnBuyYOffset + offsetY);
        text_level.setOffset(textLevelXOffset + offsetX, textLevelYOffset + offsetY);
        btnUpgrade.setOffset(btnUpgradeXOffset + offsetX, btnUpgradeYOffset + offsetY);
        text_upgrade.setOffset(textUpgradeXOffset + offsetX, textUpgradeYOffset + offsetY);
        text_upgrade_cost.setOffset(textUpgradeCostXOffset + offsetX, textUpgradeCostYOffset + offsetY);
        btnSelect.setOffset(btnSelectXOffset + offsetX, btnSelectYOffset + offsetY);
        selectLabel.setOffset(selectLabelXOffset + offsetX, selectLabelYOffset + offsetY);
    }

    public void draw() {

        // draw select button before fidget
        btnSelect.draw();

        if (id == Settings.fidgetModel) {
            selectLabel.draw();
        }
//            System.out.println("taco textureRegionFileName: " + textureRegionFileName);
//        System.out.println("taco fidgetModel.draw("+fidgetModel.getTexture()+") = " + id + " " + fidgetModel.getRegionHeight() + " " + fidgetModel.getTexture().getWidth());


        Assets.batch.draw(
                fidgetModel,                          // texture region
                x + offsetX,                    // x-pos
                y + offsetY,                    // y-pos
                0,                              // x-origin
                0,                              // y-origin
                fidgetModel.getRegionWidth(),   // width
                fidgetModel.getRegionHeight(),  // height
                ScreenUtilities.getScaleByPercentOfScreenLowestDimension(.15f, fidgetModel), // x-scale
                ScreenUtilities.getScaleByPercentOfScreenLowestDimension(.15f, fidgetModel), // y-scale
                0);                             // rotation angle

        // draw buttons
        if (isPurchased) {
            // draw other buttons
            if (Settings.points >= (cost + (level * pointsPerLevelIncrement * 10))) {
                btnUpgrade.setCheckBox(true);
            } else {
                btnUpgrade.setCheckBox(false);
            }
            btnUpgrade.draw();
            text_upgrade.draw(
                    "+" + pointsPerLevelIncrement + " / spin\n(current:"
                            + pointsPerLevelIncrement * level + ")");
            // draw level
            this.currentCost = this.cost + (this.level * this.pointsPerLevelIncrement * 10);
            text_upgrade_cost.draw("cost: " + this.currentCost);

            btnBuy.setOffset(-999, -999);
        } else {
            btnBuy.draw();
            text_cost.draw("Cost:\n" + cost + (level * pointsPerLevelIncrement * 10));
        }

        // draw level
        text_level.draw("Lvl " + level);

    }

}
