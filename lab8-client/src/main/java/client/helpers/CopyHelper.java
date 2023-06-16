package client.helpers;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class CopyHelper {
    public static GridPane gridCopy(GridPane original) {
        GridPane copy = new GridPane();
        copyProperties(original, copy);
        copySize(original, copy);
        for (Node child : original.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            Integer colIndex = GridPane.getColumnIndex(child);

            // Если индексы не заданы, считаем их нулями
            rowIndex = (rowIndex == null) ? 0 : rowIndex;
            colIndex = (colIndex == null) ? 0 : colIndex;

            if (child instanceof GridPane) {
                GridPane childCopy = gridCopy((GridPane) child);
                copySize(child, childCopy); // copy size
                copy.add(childCopy, colIndex, rowIndex);
            } else if (child instanceof Label) {
                Label labelCopy = copyLabel((Label) child);
                copy.add(labelCopy, colIndex, rowIndex);
            } else {
                // Если появятся другие типы узлов, сделайте для них копии.
            }
        }

        return copy;
    }

    public static void copySize(Node original, Node copy) {
        if (original instanceof Region && copy instanceof Region) {
            Region originalRegion = (Region) original;
            Region copyRegion = (Region) copy;

            copyRegion.setPrefWidth(originalRegion.getPrefWidth());
            copyRegion.setPrefHeight(originalRegion.getPrefHeight());
            copyRegion.setMinWidth(originalRegion.getMinWidth());
            copyRegion.setMinHeight(originalRegion.getMinHeight());
            copyRegion.setMaxWidth(originalRegion.getMaxWidth());
            copyRegion.setMaxHeight(originalRegion.getMaxHeight());
        }
    }

    public static void copyProperties(GridPane original, GridPane copy) {
        copy.setAlignment(original.getAlignment());
        copy.setHgap(original.getHgap());
        copy.setVgap(original.getVgap());
        copy.setPadding(original.getPadding());
        copy.setPrefWidth(original.getPrefWidth());
        copy.setPrefHeight(original.getPrefHeight());
        copy.setLayoutX(original.getLayoutX());
        copy.setLayoutY(original.getLayoutY());
        copy.setId(original.getId());
        copy.setBackground(original.getBackground());
        copy.setGridLinesVisible(original.isGridLinesVisible());

        copy.setBorder(original.getBorder());

        // и так далее...
    }

    public static Label copyLabel(Label original) {
        Label copy = new Label();
        copySize(original, copy);
        copy.setFont(original.getFont());
        copy.setTextFill(original.getTextFill());
        copy.setStyle(original.getStyle());
        copy.setAlignment(original.getAlignment());
        copy.setGraphic(original.getGraphic());
        copy.setBorder(original.getBorder());
        copy.setText(original.getText()); // добавили копирование текста
        // и так далее...
        return copy;
    }
}
