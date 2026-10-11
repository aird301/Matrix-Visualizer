import javax.swing.*;
import java.awt.*;


public class MatrixWindow extends JFrame
{
    private JTextField aField = new JTextField("1", 3);
    private JTextField bField = new JTextField("0", 3);
    private JTextField cField = new JTextField("0", 3);
    private JTextField dField = new JTextField("1", 3);
    private GridPanel gridpanel = new GridPanel();
    private JLabel determinantLabel = new JLabel("Determinant: 1.0");
    private JLabel areaScaleFactorLabel = new JLabel("Area Scale Factor: 1.0");
    private JLabel orientationLabel = new JLabel("Orientation: Preserved");

    public MatrixWindow() {
        setTitle("MatrixVisualizer"); // Initializing everything
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel controls = new JPanel();
        JPanel resultsPanel = new JPanel();
        controls.setLayout(new FlowLayout());
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 0));
        controls.add(new JLabel("Matrix: "));

        controls.add(aField);
        controls.add(bField);

        controls.add(new JLabel(" "));
        controls.add(cField);
        controls.add(dField);

        JButton applyButton = new JButton("Apply Matrix"); // Matrix will be able to be applied or not applied
        JButton resetButton = new JButton("Reset Matrix"); // Matrix will be reset with this button
        JButton rotationButton = new JButton("Rotate 90°"); // Button which applies a 90 degree rotation trasnformation to e1 and e2
        JButton reflectionYButton = new JButton("Reflect Y");
        JButton reflectionXButton = new JButton("Reflect X");
        JButton scaleXButton = new JButton("Scale X by 2x");
        JButton scaleYButton = new JButton("Scale Y by 2x");
        JButton scaleXandYButton = new JButton("Scale X and Y by 2x");
        JButton horizontalShearButton = new JButton("Horizontal Shear");
        JButton verticalShearButton = new JButton("Vertical Shear");
        JButton zeroMatrixButton = new JButton("Zero Matrix");


        applyButton.addActionListener(e -> {
            try {
                double a = Double.parseDouble(aField.getText());
                double b = Double.parseDouble(bField.getText());
                double c = Double.parseDouble(cField.getText());
                double d = Double.parseDouble(dField.getText());

                // Calculates determinant, area scale factor, and determines orientation
                gridpanel.setMatrix(a, b, c, d);
                double det = a * d - b * c;

                // Adjusts the fonts for the buttons and labels

                Font labelFont = new Font("Arial", Font.PLAIN, 16);
                determinantLabel.setFont(labelFont);
                areaScaleFactorLabel.setFont(labelFont);
                orientationLabel.setFont(labelFont);

                Font buttonFont = new Font("Arial", Font.PLAIN, 14);
                applyButton.setFont(buttonFont);
                resetButton.setFont(buttonFont);
                rotationButton.setFont(buttonFont);
                reflectionYButton.setFont(buttonFont);
                reflectionXButton.setFont(buttonFont);
                scaleXButton.setFont(buttonFont);
                scaleYButton.setFont(buttonFont);
                scaleXandYButton.setFont(buttonFont);
                horizontalShearButton.setFont(buttonFont);
                verticalShearButton.setFont(buttonFont);
                zeroMatrixButton.setFont(buttonFont);


                determinantLabel.setText("Determinant: " + det);
                double areaScaleFactor = Math.abs(det);
                areaScaleFactorLabel.setText("Area Scale Factor: " + areaScaleFactor);
                if (det > 0)
                {
                    orientationLabel.setText("Orientation: Preserved");
                }
                else if (det < 0)
                {
                    orientationLabel.setText("Orientation: Reversed");
                }
                else {
                    orientationLabel.setText("Orientation: Collapsed");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter valid numbers in all four fields."
                );
            }
        });

        resetButton.addActionListener(e -> {
            aField.setText("1");
            bField.setText("0");
            cField.setText("0");
            dField.setText("1");

            gridpanel.setMatrix(1, 0, 0, 1);

            double det = 1.0;
            determinantLabel.setText("Determinant: " + det);
            double areaScaleFactor = 1.0;
            areaScaleFactorLabel.setText("Area Scale Factor: " + areaScaleFactor);
            orientationLabel.setText("Orientation: Preserved");
        });

        rotationButton.addActionListener(e ->
                applyPreset(0, -1, 1, 0));
        reflectionYButton.addActionListener(e ->
                applyPreset(-1, 0, 0, 1));
        reflectionXButton.addActionListener(e ->
                applyPreset(1, 0, 0, -1));
        scaleXButton.addActionListener(e ->
                applyPreset(2, 0, 0, 1));
        scaleYButton.addActionListener(e ->
                applyPreset(1, 0, 0, 2));
        scaleXandYButton.addActionListener(e ->
                applyPreset(2, 0, 0, 2));
        horizontalShearButton.addActionListener(e ->
                applyPreset(1, 1, 0, 1));
        verticalShearButton.addActionListener(e ->
                applyPreset(1, 0, 1, 1));
        zeroMatrixButton.addActionListener(e ->
                applyPreset(0, 0, 0, 0));



        // Displays determinant, area scale factor, and orientation. Before that are the buttons
        controls.add(applyButton);
        controls.add(resetButton);
        controls.add(rotationButton);
        controls.add(reflectionYButton);
        controls.add(reflectionXButton);
        controls.add(scaleXButton);
        controls.add(scaleYButton);
        controls.add(scaleXandYButton);
        controls.add(horizontalShearButton);
        controls.add(verticalShearButton);
        controls.add(zeroMatrixButton);
        resultsPanel.add(determinantLabel);
        resultsPanel.add(areaScaleFactorLabel);
        resultsPanel.add(orientationLabel);

        JPanel legend = new JPanel(); // Used to distinguish between new and transformed vectors

        legend.add(new JLabel("Original e₁: RED;"));
        legend.add(new JLabel("Original e₂: BLUE;"));
        legend.add(new JLabel("Transformed e₁: GREEN;"));
        legend.add(new JLabel("Transformed e₂: YELLOW;"));
        legend.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(controls, BorderLayout.NORTH);
        topPanel.add(legend, BorderLayout.CENTER);
        topPanel.add(resultsPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(gridpanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void applyPreset(double a, double b, double c, double d) // applies preset transformations
    {
        aField.setText(String.valueOf(a));
        bField.setText(String.valueOf(b));
        cField.setText(String.valueOf(c));
        dField.setText(String.valueOf(d));

        gridpanel.setMatrix(a, b, c, d);

        double det = a*d - b*c;
        determinantLabel.setText("Determinant: " + det);
        double areaScaleFactor = Math.abs(det);
        areaScaleFactorLabel.setText("Area Scale factor: " + areaScaleFactor);

        if (det > 0)
        {
            orientationLabel.setText("Orientation: Preserved");
        }
        else if (det < 0)
        {
            orientationLabel.setText("Orientation: Reversed");
        }
        else
        {
            orientationLabel.setText("Orientation: Collapsed");
        }
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MatrixWindow::new);
    }
}