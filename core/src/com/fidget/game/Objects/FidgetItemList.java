package com.fidget.game.Objects;

import com.fidget.game.DataStructures.Assets;
import com.fidget.game.ScreenUtilities;
import com.fidget.game.Settings;
import com.fidget.game.StoreSettings;

import java.util.ArrayList;

/**
 * Created by j.lee on 9/25/17.
 */

public class FidgetItemList {
    public ArrayList<FidgetItem> fidgetItemArrayList;

    public FidgetItemList(ArrayList<FidgetItem> fidgetItemArrayList) {
        this.fidgetItemArrayList = fidgetItemArrayList;
        updatePointsPerSpinBonus();
        initializeOffset();

    }

    public void initializeOffset() {
        // TODO call offset methods on fidgetItems
        for (int i = 0; i < fidgetItemArrayList.size(); i++) {
            fidgetItemArrayList.get(i).setOffset(
                    (int) ScreenUtilities.getScreenPositionBasedOnPercentageX(15),
                    (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(80)
                            - (i * (int) ScreenUtilities.getScreenPositionBasedOnPercentageY(12)));
        }
    }

    public void draw() {
        for (int i = 0; i < fidgetItemArrayList.size(); i++) {
            fidgetItemArrayList.get(i).draw();
        }
    }

    public void updateBuyPressed(int assignedID) {
        FidgetItem fidgetItem = fidgetItemArrayList.get(assignedID);
        if (fidgetItem.cost <= Settings.points) {
            Settings.points -= fidgetItem.cost;
            fidgetItem.isPurchased = true;
            updatePointsPerSpinBonus();
        }
    }

    public void upgradePressed(int assignedID) {
        FidgetItem fidgetItem = fidgetItemArrayList.get(assignedID);
        if (fidgetItem.currentCost <= Settings.points) {
            Settings.points -= fidgetItem.currentCost;
            fidgetItem.level += 1;
            updatePointsPerSpinBonus();
        }
    }

    public int getTotalPointsPerSpin() {
        int retInt = 0;
        for (FidgetItem fidgetItem : fidgetItemArrayList) {
            if (fidgetItem.isPurchased) {
                retInt += fidgetItem.currentPointsPerSpin * fidgetItem.level;
            }
        }
        return retInt;
    }

    public void updatePointsPerSpinBonus() {
        StoreSettings.pointsPerSpinBonus = getTotalPointsPerSpin();
    }

    public void isPressed(float x, float y) {
        for (int i = 0; i < fidgetItemArrayList.size(); i++) {
            // check if an item was purchased
            if (fidgetItemArrayList.get(i).btnUpgrade.isPressed(x, y)) {
                upgradePressed(i);
            }

            // check if an item was purchased
            else if (fidgetItemArrayList.get(i).btnBuy.isPressed(x, y)) {
                updateBuyPressed(i);
            }

            // check if a fidget was selected put select mark next to the fidget
            else if (fidgetItemArrayList.get(i).isPurchased && fidgetItemArrayList.get(i).btnSelect.isPressed(x, y)) {
                Settings.fidgetModel = i;
                Assets.fidget.setFidgetModel(i);
            }



        }
    }
}
