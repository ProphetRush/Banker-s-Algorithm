/**
 * Created by 63160 on 6/2/2017.
 */
public class Controller {

    private static UserInterface userInterface;

    int[][] Max;
    int[][] Need;
    int[][] Allocation;
    int[] Available;

    public Controller(UserInterface ui){
        Controller.userInterface = ui;
        this.Max = userInterface.getMax();
        this.Need = userInterface.getNeed();
        this.Allocation = userInterface.getAllocation();
        this.Available = userInterface.getAvailable();
        System.out.println(SafetyCheck());
    }

    public Boolean SafetyCheck(){
        int[] Work = this.Available;
        Boolean[] Finish = new Boolean[5];

        for (int i = 0; i < 5; i++) {
            Finish[i] = false;
        }
        while(true){
            boolean EnoughInstanceExist = false;
            for (int i = 0; i < 5; i++) {
                if(Finish[i] == false){
                    if(isAvailableEnough(Work, i)){
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

    public Boolean isAvailableEnough(int[] Work, int i){
        for (int j = 0; j < 3; j++) {
            if(Need[i][j]>Work[j]) return false;
        }
        return true;
    }

    public Boolean allFinished(Boolean[] Finished){
        for (int i = 0; i < Finished.length; i++) {
            if(Finished[i] == false) return false;
        }
        return true;
    }

}
