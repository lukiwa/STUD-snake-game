package com.snake.game.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Class responsible for saving results to file
 */
public class ResultToFileSaver {

    public static void SaveResultsToFile(String filename, int playerPoints, int aiPoints) {
        try {

            FileWriter myWriter = new FileWriter(filename, true);
            myWriter.write("SAVED DATA:\n");
            myWriter.write("PlayerPoints: " + playerPoints + "\n");
            myWriter.write("AiPoints: " + aiPoints + "\n");
            myWriter.write("\n");
            myWriter.close();

            System.out.println("WRITTEN TO FILE");
        } catch (IOException e) {
            System.out.println("FAILED TO WRITE TO FILE");
            e.printStackTrace();
        }
    }
}

