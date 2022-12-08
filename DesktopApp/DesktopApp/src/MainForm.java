import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm {
    private JPanel mainPanel;
    private JPanel textPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton collapseButton;
    private JPanel buttonPanel;
    private JTextField textField4;
    private JButton expandButton;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextArea errorArea;

    public MainForm() {
        collapseButton.addActionListener(new Action() {
            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String firstName = textField2.getText();
                String lastName = textField1.getText();
                String patronymic = textField3.getText();
                if(firstName.equals("") || lastName.equals("")){
                    errorArea.setText("Ошибка: введите фамилию и имя");
                }else {
                    errorArea.setText("");
                    label4.setVisible(true);
                    textField4.setVisible(true);
                    expandButton.setVisible(true);
                    textField4.setText(lastName + " " + firstName + " " + patronymic);
                    label1.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    textField1.setVisible(false);
                    textField2.setVisible(false);
                    textField3.setVisible(false);
                    collapseButton.setVisible(false);
                }
            }
        });
        expandButton.addActionListener(new Action() {
            @Override
            public Object getValue(String s) {
                return null;
            }

            @Override
            public void putValue(String s, Object o) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

            }

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] names = textField4.getText().split("\\s");
                String firstName = names[1];
                String lastName = names[0];
                String patronymic = names.length == 3 ? names[2] : "";
                if(firstName.equals("") || lastName.equals("")){
                    errorArea.setText("Ошибка: введите фамилию и имя");
                }else {
                    errorArea.setText("");
                    textField1.setVisible(true);
                    textField2.setVisible(true);
                    textField3.setVisible(true);
                    label1.setVisible(true);
                    label2.setVisible(true);
                    label3.setVisible(true);
                    collapseButton.setVisible(true);
                    textField1.setText(lastName);
                    textField2.setText(firstName);
                    textField3.setText(patronymic);
                    textField4.setVisible(false);
                    label4.setVisible(false);
                    expandButton.setVisible(false);
                }
            }
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

}
