package batchCheksAnalyser;

import cheksAnalyse.AbstractCheksAnalyser;
import cheksAnalyse.CheksAnalyserBooleans;
import cheksAnalyse.CheksAnalyserBytes;
import cheksAnalyse.CheksAnalyserBytesPerBytes;
import com.archosResearch.jCHEKS.chaoticSystem.ChaoticSystem;
import com.archosResearch.jCHEKS.chaoticSystem.exception.KeyLenghtException;
import com.archosResearch.jCHEKS.concept.chaoticSystem.AbstractChaoticSystem;
import keyStorage.keyStorage;

/**
 *
 * @author Michael Roussel <rousselm4@gmail.com>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //brent(100000);
        //for (int i = 0; i < 1000; i++) {
        brent();
        //AbstractChaoticSystem CS = new ChaoticSystem(128);
        //AbstractCheksAnalyser booleanAnalyser = new CheksAnalyserBooleans(false, CS);
        //booleanAnalyser.getEvolutionCount();
        //AbstractCheksAnalyser bytesAnalyser = new CheksAnalyserBytes(true, CS);
        //AbstractCheksAnalyser bytesPerBytesAnalyser = new CheksAnalyserBytesPerBytes(false, CS);
        //bytesPerBytesAnalyser = new CheksAnalyserBytesPerBytes(false, CS);
        //bytesPerBytesAnalyser = new CheksAnalyserBytesPerBytes(false, CS);
        //bytesPerBytesAnalyser = new CheksAnalyserBytesPerBytes(false, CS);
        //int min = booleanAnalyser.getEvolutionCount();
        //int middle = bytesAnalyser.getEvolutionCount();
        //int max = bytesPerBytesAnalyser.getEvolutionCount();
        //}
        //keyStorage.main(new String[0]);
    }

    private static int brent() throws KeyLenghtException, CloneNotSupportedException {
        ChaoticSystem turtle = new ChaoticSystem(128);
        ChaoticSystem rabbit = turtle.clone();
        int rabbitState = 0;
        int steps_taken = 0;
        int step_limit = 2;
        while (true) {
            rabbit.evolveSystem();
            rabbitState++;
            steps_taken++;
            if(rabbit.equals(turtle)){
                return  rabbitState;
            }
            if (steps_taken == step_limit) {
                steps_taken = 0;
                step_limit *= 2;
                turtle = rabbit.clone();
                System.out.println(rabbitState);
            }
        }
    }/*
     # main phase: search successive powers of two
     power = lam = 1
     tortoise = x0
     hare = f(x0)  # f(x0) is the element/node next to x0.

     # Next, the hare and tortoise move at same speed till they agree
     while tortoise != hare:
     tortoise = f(tortoise)
     hare = f(hare)
     mu += 1
 
     return lam, mu
     */

}
