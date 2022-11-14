package wash.control;

import wash.io.WashingIO;
import wash.simulation.WashingSimulator;

public class Wash {

    public static void main(String[] args) throws InterruptedException {
        WashingSimulator sim = new WashingSimulator(Settings.SPEEDUP);
        
        WashingIO io = sim.startSimulation();

        TemperatureController temp = new TemperatureController(io);
        WaterController water = new WaterController(io);
        SpinController spin = new SpinController(io);

        WashingProgram1 wash1 = new WashingProgram1(io, temp, water, spin); ;
        
        temp.start();
        water.start();
        spin.start();

        while (true) {
            int n = io.awaitButton();
            System.out.println("user selected program " + n);

            switch(n){
            	case(0):
            		 System.out.println("EMERGENCY SHUTDOWN... " + n);
            	
            	wash1.interrupt();
            	
            	break;
            	case(1):
            		 wash1 = new WashingProgram1(io, temp, water, spin); 
            		wash1.start();

            	break;
            	case(2):
            		WashingProgram2 wash2 = new WashingProgram2(io, temp, water, spin); 
            	wash2.start();

                break;
            	case(3):
            		WashingProgram3 wash3 = new WashingProgram3(io, temp, water, spin); 
            		wash3.start();

                break;
                default:
                    System.out.println("oops " + n);

            }
            // TODO:
            // if the user presses buttons 1-3, start a washing program
            // if the user presses button 0, and a program has been started, stop it
        }
    }
};
