import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.BasicStroke;


public class GridPanel extends JPanel // class used to build the grid
{
    private double a = 1;
    private double b = 0;
    private double c = 0;
    private double d = 1;

    public GridPanel()
    {
        setBackground(Color.WHITE); // Initial background
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        // reset grid
        super.paintComponent(g);

        // position initializations
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int spacing = 60;

        // Draw transformed grid lines
        g.setColor(Color.LIGHT_GRAY);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        int gridRange = 40;

        // Transform vertical grid lines
        for (int x = -gridRange; x <= gridRange; x++) {
            for (int y = -gridRange; y < gridRange; y++) {
                double x1 = a * x + b * y;
                double y1 = c * x + d * y;

                double x2 = a * x + b * (y + 1);
                double y2 = c * x + d * (y + 1);

                g2.drawLine(
                        centerX + (int) (x1 * spacing),
                        centerY - (int) (y1 * spacing),
                        centerX + (int) (x2 * spacing),
                        centerY - (int) (y2 * spacing)
                );
            }
        }

        // Transform horizontal grid lines
        for (int y = -gridRange; y <= gridRange; y++) {
            for (int x = -gridRange; x < gridRange; x++) {
                double x1 = a * x + b * y;
                double y1 = c * x + d * y;

                double x2 = a * (x + 1) + b * y;
                double y2 = c * (x + 1) + d * y;

                g2.drawLine(
                        centerX + (int) (x1 * spacing),
                        centerY - (int) (y1 * spacing),
                        centerX + (int) (x2 * spacing),
                        centerY - (int) (y2 * spacing)
                );
            }
        }

        // Draw coordinate axes
        g.setColor(Color.BLACK);
        g.drawLine(centerX, 0, centerX, height);
        g.drawLine(0, centerY, width, centerY);

        // Scale matrix coordinates to pixels
        int transformedX1 = centerX + (int)(a*spacing);
        int transformedY1 = centerY - (int)(c*spacing);

        int transformedX2 = centerX + (int)(b*spacing);
        int transformedY2 = centerY - (int)(d*spacing);

        // Draw transformed first basis vector in green

        g2.setStroke(new BasicStroke(5)); // Thickness of 5

        if (transformedX1 != centerX || transformedY1 != centerY) {
            g2.setColor(Color.GREEN.darker());
            drawArrow(g2, centerX, centerY, transformedX1, transformedY1);
        }



        // Draw transformed second basis vector in orange
        if (transformedX2 != centerX || transformedY2 != centerY) {
            g2.setColor(Color.ORANGE);
            drawArrow(g2, centerX, centerY, transformedX2, transformedY2);
        }

        // Draw standard basis vectors
        g2.setColor(Color.RED);
        drawArrow(g2, centerX, centerY, centerX + spacing, centerY);

        g2.setColor(Color.BLUE);
        drawArrow(g2, centerX, centerY, centerX, centerY - spacing);

        g2.setStroke(new BasicStroke(1));// Back to thickness of 1
    }

    private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) // method used for the vectors to have arrowheads
    {
        g2.drawLine(x1, y1, x2, y2);
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowLength = 12;
        double spread = Math.PI / 6;
        int x3 = (int) (x2 - arrowLength*Math.cos(angle - spread));
        int y3 = (int) (y2 - arrowLength*Math.sin(angle - spread));
        int x4 = (int) (x2 - arrowLength*Math.cos(angle + spread));
        int y4 = (int) (y2 - arrowLength*Math.sin(angle + spread));
        g2.drawLine(x2, y2, x3, y3);
        g2.drawLine(x2, y2, x4, y4);
    }

    public void setMatrix(double a, double b, double c, double d)
    { // updates matrix entries and tells Swing to redraw grid panel
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        repaint();
    }
}
