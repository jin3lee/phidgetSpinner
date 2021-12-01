package com.fidget.game;

import com.fidget.game.Objects.FidgetItem;
import com.fidget.game.Objects.FidgetItemList;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by j.lee on 9/23/17.
 */

public class StoreSettings {
    public static final String TAG = "StoreSettings";

    public static void save() {
        saveFidgetItems();
    }

    public static void load() {
        initializeFidgetItems();
    }

    public static final String FIDGET_ITEM_INITIALIZATION_REGEX = "FIDGET_ITEM_INITIALIZATION_REGEX";

    // data structure
    public static FidgetItemList sFidgetItemList;
    public static int pointsPerSpinBonus = 0;

    private static void initializeFidgetItems() {
        boolean hasInitializeOnce = Settings.prefs.getBoolean("hasInitiailizeOnce", false);
        ArrayList<FidgetItem> fidgetItemArrayList = new ArrayList<FidgetItem>();
        ArrayList<FidgetItem> defaultFidgetItemList = getDefaultFidgetItems();
        for (int i = 0; i < defaultFidgetItemList.size(); i++) {
            String regex = Settings.prefs.getString(FIDGET_ITEM_INITIALIZATION_REGEX + i, "");
            FidgetItem fidgetItem = fidgetItemBuilder(regex);
            fidgetItem.id = i;
            
            if (hasInitializeOnce) {
                fidgetItemArrayList.add(fidgetItem);
            } else {
                fidgetItemArrayList.add(defaultFidgetItemList.get(i));
            }
        }
        if (!hasInitializeOnce) {
            Settings.prefs.putBoolean("hasInitiailizeOnce", true);
        }
        sFidgetItemList = new FidgetItemList(fidgetItemArrayList);
    }

    private static void saveFidgetItems() {
        for (int i = 0; i < sFidgetItemList.fidgetItemArrayList.size(); i++) {
            String fidgetItemRegex = "";

            FidgetItem fidgetItem = sFidgetItemList.fidgetItemArrayList.get(i);

            // generate regex
            fidgetItemRegex += fidgetItem.id + "-";
            fidgetItemRegex += fidgetItem.cost + "-";
            fidgetItemRegex += fidgetItem.level + "-";
            fidgetItemRegex += fidgetItem.upgradeCount + "-";
            fidgetItemRegex += "" + ((fidgetItem.isPurchased) ? "true" : "false") + "-";
            fidgetItemRegex += fidgetItem.currentPointsPerSpin + "-";
            fidgetItemRegex += fidgetItem.pointsPerLevelIncrement + "-";
            fidgetItemRegex += fidgetItem.textureRegionFileName;

            Settings.prefs.putString(FIDGET_ITEM_INITIALIZATION_REGEX + i, fidgetItemRegex);
        }
    }

    private static FidgetItem fidgetItemBuilder(String fidgetItemRegex) {
        FidgetItem fidgetItem;

        if (!fidgetItemRegex.equals("")) {
            String[] parts = fidgetItemRegex.split("-");
            fidgetItem = new FidgetItem(Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3]),
                    Boolean.parseBoolean(parts[4]),
                    Integer.parseInt(parts[5]),
                    Integer.parseInt(parts[6]),
                    parts[7]);
        } else {
            fidgetItem = new FidgetItem();
        }

        return fidgetItem;
    }

    private static ArrayList<FidgetItem> getDefaultFidgetItems() {
        ArrayList<FidgetItem> fidgetItemArrayList = new ArrayList<FidgetItem>();
        fidgetItemArrayList.add(new FidgetItem(0, 100, 1, 0, true, 1, 1, "fidget/on_0.png"));
        fidgetItemArrayList.add(new FidgetItem(1, 750, 1, 0, false, 2, 2, "fidget/on_1.png"));
        fidgetItemArrayList.add(new FidgetItem(2, 2500, 1, 0, false, 3, 3, "fidget/on_2.png"));
        fidgetItemArrayList.add(new FidgetItem(3, 6250, 1, 0, false, 5, 5, "fidget/on_3.png"));
        fidgetItemArrayList.add(new FidgetItem(4, 10000, 1, 0, false, 7, 7, "fidget/on_4.png"));
        fidgetItemArrayList.add(new FidgetItem(5, 20000, 1, 0, false, 20, 20, "fidget/on_5.png"));

        return fidgetItemArrayList;
    }
}
