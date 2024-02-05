package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

public class DealOrNoDeal {
    private static JPanel buttonPanel;
    private static int numOfButtonsSelected;
    private static int finalIndex;
    private static String[] buttonLabels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
    private static ArrayList<JButton> buttonList = new ArrayList<>();
    private static ArrayList<JLabel> moneyLabels;
    private static ArrayList<Integer> selectedButtons;
    private static ArrayList<JLabel> shuffledMoneyLabels;
    private static HashMap<JButton, Integer> buttonIndexMap = new HashMap<>();
    private static HashMap<Integer, JLabel> buttonToMoneyValueMap = new HashMap<>();
    private static ArrayList<String> moneyValues = new ArrayList<>(Arrays.asList("$100", "$500","$1,000", "$5,000", "$10,000", "$25,000", "$50,000", "$75,000", "$100,000", "$200,000", "$300,000", "$400,000", "$500,000", "$750,000", "$1,000,000"));
    private static final ArrayList<String> REMAINING_MONEY_VALUES = new ArrayList<>(moneyValues);
    private static final String[] BUTTON_IMAGE_PATHS = {
            "src/suitcase1.png",
            "src/suitcase2.png",
            "src/suitcase3.png",
            "src/suitcase4.png",
            "src/suitcase5.png",
            "src/suitcase6.png",
            "src/suitcase7.png",
            "src/suitcase8.png",
            "src/suitcase9.png",
            "src/suitcase10.png",
            "src/suitcase11.png",
            "src/suitcase12.png",
            "src/suitcase13.png",
            "src/suitcase14.png",
            "src/suitcase15.png"
    };
    private static final int NUM_SELECTIONS = 3;

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
        MyLabel label = new MyLabel();
        MyLabel subtitle = new MyLabel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel moneyPanel = new JPanel(new BorderLayout());
        JLabel moneyLabel;
        Font subtitleHeader = new Font("Serif", Font.BOLD, 20);
        Font moneyHeader = new Font("Serif", Font.BOLD, 30);

        label.setText("Deal Or No Deal");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);

        subtitle.setText("Open three (3) briefcases!");
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setVerticalAlignment(JLabel.BOTTOM);
        subtitle.setFont(subtitleHeader);

        mainPanel.setBackground(Color.black);
        mainPanel.setBorder(new EmptyBorder(20,0,0,200));
        mainPanel.add(label, BorderLayout.NORTH);
        mainPanel.add(subtitle, BorderLayout.SOUTH);
        moneyPanel.setLayout(new BoxLayout(moneyPanel, BoxLayout.Y_AXIS));
        moneyLabels = new ArrayList<>();
        selectedButtons = new ArrayList<>();

        for (String value : moneyValues) {
            moneyLabel = new JLabel(value);
            moneyLabel.setForeground(Color.white);
            moneyLabel.setFont(moneyHeader);
            moneyLabel.setHorizontalAlignment(JLabel.CENTER);
            moneyPanel.add(moneyLabel);
            moneyLabels.add(moneyLabel);
        }
        moneyPanel.setBackground(Color.black);
        moneyPanel.setBorder(new EmptyBorder(150,100,0,100));
        mainPanel.add(moneyPanel, BorderLayout.WEST);
        buttonPanel = new JPanel(new GridLayout(5, 2));
        buttonPanel.setBackground(Color.black);

        shuffledMoneyLabels = new ArrayList<>(moneyLabels);
        Collections.shuffle(shuffledMoneyLabels);

        chooseButtons();

        buttonPanel.setBorder(new EmptyBorder(0,0,0,0));
        mainPanel.add(buttonPanel, BorderLayout.CENTER);


        myFrame.add(moneyPanel, BorderLayout.WEST);
        myFrame.add(mainPanel, BorderLayout.CENTER);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(1300,1000);
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);

    }

    private static void dealOrNoDealDialog() {
        if (REMAINING_MONEY_VALUES.size() <= NUM_SELECTIONS) {
            // Last round: Display a pop-up message for not winning anything
            int lastSelectedButtonIndex = selectedButtons.get(selectedButtons.size() - 1);
            JLabel lastSelectedLabel = buttonToMoneyValueMap.get(lastSelectedButtonIndex);
            String amountWon = lastSelectedLabel.getText();
            JOptionPane.showMessageDialog(null, "You won " + amountWon + "!");
        }
        else {
            double bankOffer = calculateOffer();
            String formattedOffer = formatOffer(bankOffer);

            String[] options = {"Deal", "No Deal"};
            int choice = JOptionPane.showOptionDialog(null, "You have opened 3 briefcases!\n\nOffer: $" + formattedOffer + "\n\nDeal or No Deal?", "Deal or No Deal", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (choice == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Congratulations! You accepted the offer of $" + formattedOffer + "!");
                disableButtons();
            }
            else {
                numOfButtonsSelected = 0;
                JOptionPane.showMessageDialog(null, "You declined the offer of $" + formattedOffer + ".\nPlease select another 3 briefcases!");
            }
        }

    }

    private static void performSelectionAction(JButton button){
        // If the user hasn't chosen 3 selections yet
        if (numOfButtonsSelected < NUM_SELECTIONS) {
            finalIndex = buttonIndexMap.get(button);
            selectedButtons.add(finalIndex);
            button.setEnabled(false);
            buttonToMoneyValueMap.get(finalIndex).setForeground(Color.GRAY);
        }

        if (numOfButtonsSelected == NUM_SELECTIONS) {
            finalIndex = buttonIndexMap.get(button);
            selectedButtons.add(finalIndex);
            button.setEnabled(false);
            buttonToMoneyValueMap.get(finalIndex).setForeground(Color.GRAY);
            dealOrNoDealDialog();
        }

    }

    private static void chooseButtons(){
        ActionListener selectionActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                finalIndex = buttonIndexMap.get(button);
                numOfButtonsSelected += 1;
                performSelectionAction(button);
            }
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton();
            buttonList.add(button);
            button.setBackground(Color.BLACK);
            button.setIcon(new ImageIcon(BUTTON_IMAGE_PATHS[i]));
            int finalIndex = i;
            buttonToMoneyValueMap.put(finalIndex, shuffledMoneyLabels.get(i));
            buttonIndexMap.put(button, finalIndex);

            button.addActionListener(selectionActionListener);
            buttonPanel.add(button);
        }

    }

    private static double calculateOffer() {
        for (int selectedIndex : selectedButtons) {
            JLabel selectedLabel = buttonToMoneyValueMap.get(selectedIndex);
            String moneyValue = selectedLabel.getText();
            REMAINING_MONEY_VALUES.remove(moneyValue);
        }


        double avgRemainingValue = 0.0;
        for (String values : REMAINING_MONEY_VALUES) {
            avgRemainingValue += Double.parseDouble(values.substring(1).replaceAll(",", ""));
        }
        avgRemainingValue /= REMAINING_MONEY_VALUES.size();
        double offer = (avgRemainingValue * .6);
        return offer;
    }
    private static String formatOffer(double offer) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(offer);
    }

    private static void disableButtons(){
        for (JButton button : buttonList) {
            button.setEnabled(false);
        }
    }

}
