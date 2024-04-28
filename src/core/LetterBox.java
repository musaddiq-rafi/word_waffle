package core;

import utils.FontManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LetterBox extends JLabel {
    // color - not in word
    private final static Color gray = new Color(121, 124, 126);

    // color - in word, wrong position
    private final static Color yellow = new Color(198, 180, 102);

    // color - in word, in correct position
    private final static Color green = new Color(121, 167, 107);

    private final static Color chocolate = new Color(96, 56, 20);

    private final static Color defaultBoxColor = new Color(255, 216, 129);
    private final static Color defaultBorder = new Color(96, 56, 20);

    public enum BoxType {
        GREEN,
        YELLOW,
        GRAY,
        DEFAULT
    }


    public LetterBox(){
        // init box
        this.setSize(64, 64);
        this.setBackground(defaultBoxColor);
        this.setForeground(chocolate);
        this.setOpaque(true);

        // box text properties
        this.setText(" ");
        this.setHorizontalAlignment(JLabel.CENTER);

        // box border properties
        Border border = BorderFactory.createLineBorder(defaultBorder, 2);
        this.setBorder(border);

        // load font file
        // Todo for Rapi,
        //  example usage given:
        Font KGPrimary = FontManager.loadFont(
                "./src/resources/KGPrimaryPenmanship.ttf",
                Font.BOLD, 52f
        );

        //change the font
        this.setFont(KGPrimary);
    }


    public void setColor(BoxType type) {
        this.setForeground(Color.WHITE);
        switch (type){
            //incorrect: GRAY
            case GRAY:
                this.setBackground(gray);
                break;

            //not in right position: YELLOW
            case YELLOW:
                this.setBackground(yellow);
                break;

            //correct: GREEN
            case GREEN:
                this.setBackground(green);
                break;

            // set default box color
            case DEFAULT:
                this.setBackground(defaultBoxColor);
                this.setForeground(chocolate);
                break;

            default:
                System.err.println("Invalid Box Type");
        }
    }

    public void updateBoxColor(BoxType type){
        this.setColor(type);
    }

    public void clear(BoxType type, String inputLetter){
        this.setText(inputLetter);
        this.setColor(type);
    }
}

