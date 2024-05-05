package core;

import utils.PositionCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameScreen extends Screen{
    private InputMap inputMap;
    private ActionMap actionMap;

    private LetterGrid grid;

    private InputAction inputAction;

    private WordPicker wordListFile;
    private JLabel warningLabel;

    private Timer warningTimer;

    private Image gameScreenBackground;

    public GameScreen(Window window){
        super(window);
        initialize();
        update();
    }

    @Override
    public void initialize() {
        //background for game screen
        gameScreenBackground = new ImageIcon("./src/resources/screen_game.png").getImage();

        // offset panel for gap from top
        JPanel gridOffset1 = new JPanel();
        //height changed by RapiBuoy to look better
        gridOffset1.setPreferredSize(new Dimension(550, 140));
        gridOffset1.setOpaque(false);

        // offset panel for gap between grid and warning
        JPanel gridOffset2 = new JPanel();
        gridOffset2.setPreferredSize(new Dimension(550, 40));
        gridOffset2.setOpaque(false);

        // create grid panel
        grid = new LetterGrid();
        grid.setPreferredSize(new Dimension(320, 320));
        grid.setOpaque(false);

        warningLabel = new JLabel("Word is too short!", SwingConstants.CENTER);
        warningLabel.setPreferredSize(new Dimension(550, 130));
        warningLabel.setFont(new Font("Serif", Font.BOLD, 30));
        warningLabel.setForeground(Color.BLACK);
        warningLabel.setVisible(false); // warningLabel initially invisible

        // init input map and action map
        inputAction = new InputAction(window, this);
        inputMap = this.grid.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = this.grid.getActionMap();

        // warning timeout timer
        warningTimer = new Timer(2500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                warningLabel.setVisible(false);
            }
        });

        // init word picker and pick a random word
        this.wordListFile = new WordPicker("./src/resources/wordlist.txt");
        this.wordListFile.generateWord();

        // add components
        this.add(gridOffset1);
        this.add(grid);
        this.add(gridOffset2);
        this.add(warningLabel);
        this.setBackground(Color.darkGray);
    }

    public LetterGrid getGrid() {
        return grid;
    }

    public WordPicker getWordListFile() {
        return wordListFile;
    }


    public LetterBox getCurrentBox(){
        return this.grid.getBox(PositionCounter.getRow(),
                PositionCounter.getColumn());
    }

    public LetterBox[] getCurrentRow(){
        return this.grid.getRow(PositionCounter.getRow());
    }


    @Override
    public void update() {
        // handle letter key press
        for (char c = 'a'; c <= 'z'; c++) {
            final char key = c;
            this.inputMap.put(KeyStroke.getKeyStroke(key), Character.toString(key));
            this.actionMap.put(Character.toString(key), new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    inputAction.typeLetter(Character.toUpperCase(key));
                }
            });
        }

        // handle enter key
        this.inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        actionMap.put("Enter", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                inputAction.pressEnter();
            }
        });

        // handle backspace key
        this.inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Backspace");
        actionMap.put("Backspace", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                inputAction.pressBackspace();
            }
        });
    }


    public void reset(LetterGrid grid){
        // clear the grid
        grid.reset();

        // reset position pointer
        PositionCounter.setRow(0);
        PositionCounter.setColumn(0);
    }

    // show the warning
    public void showWarning() {
        warningLabel.setVisible(true);
        warningTimer.restart(); // Start or restart the timer
    }

    //draw the background image
    @Override
    protected void paintComponent( Graphics g ) {
        super.paintComponent( g );
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(gameScreenBackground, 0, 0, null);
    }
}
