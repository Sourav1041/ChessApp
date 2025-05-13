import java.awt.Color;
import java.awt.GridLayout;

/*
public class xyz{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Multiple Templates Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // CardLayout to hold multiple templates
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // First template
        JPanel template1 = new JPanel();
        template1.add(new JLabel("This is Template 1"));
        template1.setBackground(Color.CYAN);

        // Second template
        JPanel template2 = new JPanel();
        template2.add(new JLabel("This is Template 2"));
        template2.setBackground(Color.PINK);

        // Add templates to the main panel
        mainPanel.add(template1, "Template1");
        mainPanel.add(template2, "Template2");

        // Buttons to switch between templates
        JPanel buttonPanel = new JPanel();
        JButton showTemplate1 = new JButton("Show Template 1");
        JButton showTemplate2 = new JButton("Show Template 2");

        buttonPanel.add(showTemplate1);
        buttonPanel.add(showTemplate2);

        showTemplate1.addActionListener(e -> cardLayout.show(mainPanel, "Template1"));
        showTemplate2.addActionListener(e -> cardLayout.show(mainPanel, "Template2"));

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
*/
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class xyz {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Main panel with GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(2, 2)); // 2x2 grid

        // Template 1
        JPanel template1 = new JPanel();
        template1.setBackground(Color.RED);
        template1.add(new JLabel("Template 1"));

        // Template 2
        JPanel template2 = new JPanel();
        template2.setBackground(Color.GREEN);
        template2.add(new JLabel("Template 2"));

        // Template 3
        JPanel template3 = new JPanel();
        template3.setBackground(Color.BLUE);
        template3.add(new JLabel("Template 3"));

        // Template 4
        JPanel template4 = new JPanel();
        template4.setBackground(Color.YELLOW);
        template4.add(new JLabel("Template 4"));

        // Add templates to the main panel
        mainPanel.add(template1);
        mainPanel.add(template2);
        mainPanel.add(template3);
        mainPanel.add(template4);

        // Add main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
