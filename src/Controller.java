import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by 63160 on 6/2/2017.
 */
public class Controller {

    private static UserInterface userInterface;

    private static Controller controller;
//
//    public static Controller getInstance()
//    {
//        return controller;
//    }
//
//    private Controller()
//    {
//        this(new UserInterface());
//    }

    int[][] Max;
    int[][] Need;
    int[][] Allocation;
    int[] Available;
    int[][] Need_ORG;
    int[][] Allocation_ORG;
    int[] Available_ORG;
    int[] Request = {0,2,0};

    private Controller(UserInterface ui){

    }
    static void init(){
        UserInterface ui = new UserInterface();
        Controller.controller = new Controller(userInterface);
        Controller.userInterface = ui;
        controller.Max = userInterface.getMax();
        controller.Need = userInterface.getNeed();
        controller.Allocation = userInterface.getAllocation();
        controller.Available = userInterface.getAvailable();
        controller.Need_ORG = userInterface.getNeed();
        controller.Allocation_ORG = userInterface.getAllocation();
        controller.Available_ORG = userInterface.getAvailable();
        ApplyButtonListener applyButtonListener = new ApplyButtonListener();
        ResetButtonListener resetButtonListener = new ResetButtonListener();
        applyButtonListener.userInterface = ui;
        resetButtonListener.userInterface = ui;
        userInterface.applyButton.addActionListener(applyButtonListener);
        userInterface.resetButton.addActionListener(resetButtonListener);
    }


    public static Controller getController(){
        return controller;
    }


    public Boolean SafetyCheck(){
        int[] Work = new int[Available.length];
        for (int i = 0; i < Available.length; i++) {
            Work[i] = Available[i];
        }
        Boolean[] Finish = new Boolean[5];

        for (int i = 0; i < 5; i++) {
            Finish[i] = false;
        }
        while(true){
            boolean EnoughInstanceExist = false;
            for (int i = 0; i < 5; i++) {
                if(Finish[i] == false){
                    if(VectorCompare(Need[i],Work)){
                        for (int j = 0; j < Work.length; j++) {
                            Work[j] += Allocation[i][j];
                        }
                        EnoughInstanceExist = true;
                        Finish[i] = true;
                    }
                }
            }
            if(allFinished(Finish)){
                return true;
            }
            if(!EnoughInstanceExist){
                return false;
            }
        }

    }

    public boolean ResourceRequest(int[] request, int processID){
        if(VectorCompare(request, Need[processID])){
            if (VectorCompare(request, Available)){
                for (int i = 0; i < Available.length ; i++) {
                    Available[i] = Available[i] - request[i];
                    Allocation[processID][i] = Allocation[processID][i] + request[i];
                    Need[processID][i] = Need[processID][i] - request[i];
                }
                if(SafetyCheck()){
                    JOptionPane.showMessageDialog(null, "Request success! System is at safe state! New Resource Status Updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    DefaultTableModel NeedModel = (DefaultTableModel) userInterface.NeedTable.getModel();
                    for (int i = 0; i < Need[processID].length ; i++) {
                        NeedModel.setValueAt(Need[processID][i], processID, i+1);
                    }
                    DefaultTableModel AllocationModel = (DefaultTableModel) userInterface.AllocatedTable.getModel();
                    for (int i = 0; i < Allocation[processID].length ; i++) {
                        AllocationModel.setValueAt(Allocation[processID][i], processID, i+1);
                    }
                    DefaultTableModel AvailableModel = (DefaultTableModel) userInterface.AvailableTable.getModel();
                    for (int i = 0; i < Available.length ; i++) {
                        AvailableModel.setValueAt(Available[i], 0, i);
                    }

                }
                else{
                    JOptionPane.showMessageDialog(null, "Request failed! Though available resources is enough but it will cause the unsafe state of the system!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Request failed! Available resources is not enough! Process must wait!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Request resources is greater than it needed, please check your input!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }


    public Boolean allFinished(Boolean[] Finished){
        for (int i = 0; i < Finished.length; i++) {
            if(Finished[i] == false) return false;
        }
        return true;
    }

    public Boolean VectorCompare(int[] a, int[] b){
        for (int i = 0; i < a.length; i++) {
            if (a[i]>b[i]) return false;
        }
        return true;
    }



}

class ApplyButtonListener implements ActionListener{
    public UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        int processID = -1;
        try{
            processID = Integer.parseInt(userInterface.textField1.getText());
        }catch (NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "Please input the ProcessID!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        int[] request = new int[3];
        try{
            for (int i = 0; i < request.length; i++) {
                if(Integer.parseInt(userInterface.requests[i].getText())<0 ){
                    JOptionPane.showMessageDialog(null, "Request not valid!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                request[i] = Integer.parseInt(userInterface.requests[i].getText());
            }
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Please input the request value!!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        if((processID>=0) && (processID<=4)){
            Controller controller = Controller.getController();
            controller.ResourceRequest(request, processID);

        }
        else if (processID == -1){
            return;
        }
        else{
            JOptionPane.showMessageDialog(null, "ProcessID not valid!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}

class ResetButtonListener implements ActionListener{
    public UserInterface userInterface;
    @Override
    public void actionPerformed(ActionEvent e){
        DefaultTableModel NeedModel = (DefaultTableModel) userInterface.NeedTable.getModel();
        DefaultTableModel AllocationModel = (DefaultTableModel) userInterface.AllocatedTable.getModel();
        DefaultTableModel AvailableModel = (DefaultTableModel) userInterface.AvailableTable.getModel();
        Controller controller = Controller.getController();
        for (int i = 0; i < controller.Need_ORG.length; i++) {
            for (int j = 0; j < controller.Need_ORG[i].length; j++) {
                NeedModel.setValueAt(controller.Need_ORG[i][j], i, j+1);
            }
        }
        for (int i = 0; i < controller.Allocation_ORG.length; i++) {
            for (int j = 0; j < controller.Allocation_ORG[i].length; j++) {
                AllocationModel.setValueAt(controller.Allocation_ORG[i][j], i, j+1);
            }
        }
        for (int i = 0; i < controller.Available_ORG.length; i++) {
            AvailableModel.setValueAt(controller.Available_ORG[i], 0, i);

        }
        JOptionPane.showMessageDialog(null,"Reset Success!", "Success", JOptionPane.INFORMATION_MESSAGE);

    }
}
