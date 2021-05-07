package graphs;

import java.util.ArrayList;

public class NonTouchingLoops {
    public void printLoops(ArrayList<int[]> arr){
        for(int i = 0; i < arr.size(); i++){
            int[] temp = arr.get(i);
            for (int j=0;j< temp.length;j++){
                System.out.print(temp[j] +"  ");
            }
            System.out.println("");
        }
    }
    public ArrayList<int[]> twoNonTouchingLoops(ArrayList<ArrayList<Edge>> loop){
        ArrayList<int[]> nonTouchingloops = new ArrayList<>();
        //compare each loop with others
        for (int i = 0 ; i< loop.size() ; i++){
            for (int j = i+1;j<loop.size();j++){
                //if the end of first loop is the start of the second loop
                if(loop.get(i).get(0).getDestination().getId()==(loop.get(j).get(0).getSource().getId())){
                    continue;
                }
                //if they have the same start
                else if(loop.get(i).get(0).getSource().getId()==(loop.get(j).get(0).getSource().getId())){
                    continue;
                }
                //else
                int count = 0;
                //compare all nodes in two loops
                for (int x=0 ; x<loop.get(i).size();x++) {
                    if(count!=0)
                        break;
                    for (int z = 0; z < loop.get(j).size();z++) {
                        if (loop.get(i).get(x).getSource().getId() == loop.get(j).get(z).getSource().getId()) {
                            count++;
                            break;
                        }
                    }
                }
                //if they are non touching
                if(count==0){
                    int[] pair =new int[2];
                    pair[0]=i;
                    pair[1]=j;
                    nonTouchingloops.add(pair);
                }
            }
        }
        return nonTouchingloops;
    }

    public boolean isNonTouching(int loop1,int loop2,ArrayList<int[]> twoNonTouchingLoops){
        boolean flag = false;
        //check if these two loops is non touching or not
        for (int j = 0; j < twoNonTouchingLoops.size(); j++) {
            //if they are non touching
            if(loop1==twoNonTouchingLoops.get(j)[0] && loop2==twoNonTouchingLoops.get(j)[1]) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public ArrayList<ArrayList<int[]>> nonTouchingloops(ArrayList<ArrayList<Edge>> loop){
        ArrayList<ArrayList<int[]>> nonTouching = new ArrayList<>();

        ArrayList<int[]> twoNonTouching = twoNonTouchingLoops(loop);
        if (twoNonTouching.size()!=0){
            nonTouching.add(twoNonTouching);
            ArrayList<int[]> nNonTouching ;
            int counter = 0;
            while (true){
                nNonTouching = new ArrayList<>();
                nNonTouching = nNonTouchingLoops(twoNonTouching,nonTouching.get(counter),counter+3);
                if (nNonTouching.isEmpty()){
                    break;
                }
                nonTouching.add(nNonTouching);
                counter++;
            }
        }
        return nonTouching;
    }


    public ArrayList<int[]> nNonTouchingLoops(ArrayList<int[]> twoNonTouchingLoops,ArrayList<int[]> nLopp,int n){
        ArrayList<int[]> loop = new  ArrayList<int[]>();
        for (int i = 0 ; i< nLopp.size() ; i++) {
            for (int j = i + 1; j < twoNonTouchingLoops.size(); j++) {
                if(nLopp.get(i)[n-2] == twoNonTouchingLoops.get(j)[0]){
                    int[] temp = new int[n];
                    for(int z=0;z < n-1;z++){
                        temp[z]=nLopp.get(i)[z];
                    }
                    temp[n-1]=twoNonTouchingLoops.get(j)[1];
                    loop.add(temp);
                }
            }
        }
        //check if all loops are non touching
        for (int i = 0 ; i< loop.size() ; i++) {
            boolean[] flags = new boolean[n-2];
            for(int z=0;z < n-2;z++){
                flags[z] =  isNonTouching(loop.get(i)[z],loop.get(i)[n-1],twoNonTouchingLoops);
                //if there are at least two touching loops remove sequence
                if (flags [z] == false){
                    loop.remove(i);
                    i--;
                    break;
                }
            }
        }
        return loop;
    }
    public String nonTouchingLoopsToStrings(ArrayList<ArrayList<int[]>> nonTouching){
        String total = "Non Touching Loops:\n";
        total +="------------------------------------\n";
        if (nonTouching.size()!=0) {
            for (int i = 0; i < nonTouching.size(); i++) {
                total += "\n"+(i + 2) + " Non Touching Loops:\n";
                total += "------------------------------------\n";
                ArrayList<int[]> nNonTouchingLoop = nonTouching.get(i);
                for (int j = 0; j < nNonTouchingLoop.size(); j++) {
                    int[] temp = nNonTouchingLoop.get(j);
                    for (int k = 0; k < temp.length; k++) {
                        total += ("Loop " + temp[k]);
                        if (k != temp.length - 1) {
                            total += " , ";
                        }
                    }
                    total += "\n";
                }
            }
        }
        else{
            total+="No non touching loops\n";
        }
        total+="-----------------------------------------------------------------------\n";
        return total;
    }
}
