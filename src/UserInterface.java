import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by 63160 on 6/2/2017.
 */
public class UserInterface {
    public JTable MaxTable;
    public JTable NeedTable;
    public JTable AllocatedTable;
    public JTable AvailableTable;
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField3;
    public JTextField textField4;
    public JButton applyButton;
    public JButton resetButton;
    public JPanel MainPanel;
    private JScrollPane MaxScrollPane;
    private JScrollPane AllocatedScrollPane;
    private JScrollPane NeedScrollPane;
    private JScrollPane AvailableScrollPane;

    public JTextField[] requests = {textField2, textField3, textField4};


    public UserInterface(){
        JFrame frame = new JFrame("UserInterface");
        frame.setContentPane(this.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        int[] a = getAvailable();
    }






    public void createUIComponents() {
        // TODO: place custom component creation code here
        String[] columnNames = {"ProcessID", "Resource A", "Resource B", "Resource C"};
        String[] ResourceColumnNames = { "Resource A", "Resource B", "Resource C"};
        Object[][] MaxMatrix = {{0,7,5,3},{1,3,2,2},{2,9,0,2},{3,2,2,2},{4,4,3,3}};
        Object[][] NeedMatrix = {{0,7,4,3},{1,1,2,2},{2,6,0,0},{3,0,1,1},{4,4,3,1}};
        Object[][] AllocationMatrix = {{0,0,1,0},{1,2,0,0},{2,3,0,2},{3,2,1,1},{4,0,0,2}};
        Object[][] AvailableVector = {{3,3,2}};

//        Object[][] MaxMatrix = {{0,7,5,3},{1,3,2,2},{2,9,0,2},{3,2,2,2},{4,4,3,3}};
//        Object[][] NeedMatrix = {{0,7,2,3},{1,0,2,0},{2,6,0,0},{3,0,1,1},{4,4,3,1}};
//        Object[][] AllocationMatrix = {{0,0,3,0},{1,3,0,2},{2,3,0,2},{3,2,1,1},{4,0,0,2}};
//        Object[][] AvailableVector = {{2,1,0}};

//        Object[][] MaxMatrix = {{0,7,5,3},{1,3,2,2},{2,9,0,2},{3,2,2,2},{4,4,3,3}};
//        Object[][] NeedMatrix = {{0,7,4,3},{1,0,2,0},{2,6,0,0},{3,0,1,1},{4,4,3,1}};
//        Object[][] AllocationMatrix = {{0,0,1,0},{1,3,0,2},{2,3,0,2},{3,2,1,1},{4,0,0,2}};
//        Object[][] AvailableVector = {{2,3,0}};


        DefaultTableModel MaxModel = new DefaultTableModel(MaxMatrix, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        MaxTable = new JTable(MaxModel);


        DefaultTableModel NeedModel = new DefaultTableModel(NeedMatrix, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        NeedTable = new JTable(NeedModel);

        DefaultTableModel AllocationModel = new DefaultTableModel(AllocationMatrix, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        AllocatedTable = new JTable(AllocationModel);

        DefaultTableModel AvailableModel = new DefaultTableModel(AvailableVector, ResourceColumnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        AvailableTable = new JTable(AvailableModel);

        DefaultTableCellRenderer r   =   new   DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        MaxTable.setDefaultRenderer(Object.class,   r);
        NeedTable.setDefaultRenderer(Object.class,   r);
        AllocatedTable.setDefaultRenderer(Object.class,   r);
        AvailableTable.setDefaultRenderer(Object.class,   r);
    }


    public int[][] getMax(){
        int[][] maxMatrix = new int[5][3];
        DefaultTableModel maxModel = ((DefaultTableModel) MaxTable.getModel());
        Vector v = maxModel.getDataVector();
        for(int i=0; i<5; i++){
            Vector v_ = (Vector)v.get(i);
            for(int j=0; j<3; j++){
                maxMatrix[i][j] = (int)v_.get(j+1);
            }
        }
        return maxMatrix;
    }

    public int[][] getNeed(){
        int[][] NeedMatrix = new int[5][3];
        DefaultTableModel NeedModel = ((DefaultTableModel) NeedTable.getModel());
        Vector v = NeedModel.getDataVector();
        for(int i=0; i<5; i++){
            Vector v_ = (Vector)v.get(i);
            for(int j=0; j<3; j++){
                NeedMatrix[i][j] = (int)v_.get(j+1);
            }
        }
        return NeedMatrix;
    }

    public int[][] getAllocation(){
        int[][] AllocationMatrix = new int[5][3];
        DefaultTableModel AllocationModel = ((DefaultTableModel) AllocatedTable.getModel());
        Vector v = AllocationModel.getDataVector();
        for(int i=0; i<5; i++){
            Vector v_ = (Vector)v.get(i);
            for(int j=0; j<3; j++){
                AllocationMatrix[i][j] = (int)v_.get(j+1);
            }
        }
        return AllocationMatrix;
    }

    public int[] getAvailable(){
        int[] AvailableVector = new int[3];
        DefaultTableModel AvailableModel = ((DefaultTableModel) AvailableTable.getModel());
        Vector v = (Vector) AvailableModel.getDataVector().get(0);
        for (int i = 0; i < 3; i++) {
            AvailableVector[i] = (int) v.get(i);
        }
        return AvailableVector;
    }




}
