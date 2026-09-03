package worldStage.loading;

import spriteData.backgroundSprite.StaticSprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StaticSpriteGen {

    public static StaticSprite genStaticSprite(String pathToData) {
        try { // Get the file set up.
            FileInputStream inFile = new FileInputStream(pathToData);
            Scanner scn = new Scanner(inFile);
            scn.useDelimiter("[|]|\\n");

            StaticSprite sprite = null;
            if (scn.hasNext()) {
                String field = scn.next();
                String pathToFile =  scn.next();
                sprite = new StaticSprite(pathToFile);
            } else throw new IllegalArgumentException("Given file is empty.");
            scn.close();
            return sprite;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Given file path does not exist.");
        }
    }

    private static void setBGSpriteData() {

    }

    private static void setPickableSpriteDate() {

    }
}
