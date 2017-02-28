import com.sun.xml.internal.bind.v2.model.runtime.RuntimeElementPropertyInfo;

import java.io.*;
import java.util.ArrayList;

/**
 * Mandelbrot
 * Created by tyler on 2/27/17.
 */
public class ResultsDriver {
    // test 1 - serial implementation of mandelbrot
        // loop 100 times getting the exection time and divide by 100 to get average serial execution time

    // test 2 - line by line threading
        // loop from 1 to 9 cores
            // loop 10 times getting the execution time and divide by 100 to get average execution time

    // test 3 - every 4 pixels threading
        // loop from 1 to 9 cores
            // loop 100 times getting the execution time and divide by 100 to get average execution time

    // test 4 - every 4 pixels recursive thread creation
        // loop from 1 to 9 cores
            // loop 100 times getting the execution time and divide by 100 to get average execution time

    // test 5 - if i can get a load bearing implementation that is good do it here similar to above

    // write to a file the average serial execution time

    // write to a file 1 - 9 core averages for rest of test cases
    public static void main(String[] args){
        // necessary setup for testings
        final int WIDTH = 800;
        final int HEIGHT = 800;
        final int THRESH = 10000;
        final int REPETITIONS = 20;
        int[] pixels = new int[WIDTH * HEIGHT];

        // setup file writing


         //test serial implementation of mandelbrot
        System.out.println("Test for serial implementation of the mandelbrot plot");
        long serialTime = 0;
        for(int i = 0 ; i < REPETITIONS; i++){
            long start = System.currentTimeMillis();
            SerialMandelbrot serialMandelbrot = new SerialMandelbrot(WIDTH, HEIGHT, THRESH, pixels);
            serialMandelbrot.start();
            try{
                serialMandelbrot.join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            serialTime+= end - start;
        }
        long averageTimeSerial = serialTime / 1;
        System.out.println("The average time for the serial implementation: " + averageTimeSerial);


        // test alternating row implementation
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        System.out.println("Test for parallel implementation that alternates pixel rows");
        for(int cores = 1 ; cores <= 9 ; cores++){
            long totalElapsedTime = 0;
            for (int j = 0 ; j < REPETITIONS ; j++){
                // perform a number of repititions
                ArrayList<AlternatingRowMandelbrot> threadList = new ArrayList<AlternatingRowMandelbrot>();
                // start timer
                long start = System.currentTimeMillis();
                // add a thread for each core
                for(int i = 0 ; i < cores ; i++){
                    threadList.add(new AlternatingRowMandelbrot(i , cores ,WIDTH, HEIGHT, THRESH, pixels));
                }
                // start each thread
                for(AlternatingRowMandelbrot p : threadList){
                    p.start();
                }

                // join all threads
                try{
                    for(AlternatingRowMandelbrot p : threadList){
                        p.join();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }


                // end timer
                long end = System.currentTimeMillis();
                totalElapsedTime+= end - start;
            }
            long averageRuntime = totalElapsedTime / REPETITIONS;
            System.out.println("The average time for : " + cores + " cores is: " + averageRuntime);

        }


        // test alternating pixel implementation
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        System.out.println("Test for parallel implementation that alternates pixels rather than rows");
        // for one to 9 cores
        for(int cores = 1 ; cores <= 9 ; cores++){
            long totalElapsedTime = 0;
            for (int j = 0 ; j < REPETITIONS ; j++){
                // perform a number of repititions
                ArrayList<ParallelMandelbrotEvery4> threadList = new ArrayList<ParallelMandelbrotEvery4>();
                // start timer
                long start = System.currentTimeMillis();
                // add a thread for each core
                for(int i = 0 ; i < cores ; i++){
                    threadList.add(new ParallelMandelbrotEvery4(i , cores ,WIDTH, HEIGHT, THRESH, pixels));
                }
                // start each thread
                for(ParallelMandelbrotEvery4 p : threadList){
                    p.start();
                }

                // join all threads
                try{
                    for(ParallelMandelbrotEvery4 p : threadList){
                        p.join();
                    }
                }catch(InterruptedException e){
                    e.printStackTrace();
                }


                // end timer
                long end = System.currentTimeMillis();
                totalElapsedTime+= end - start;
            }
            long averageRuntime = totalElapsedTime / REPETITIONS;
            System.out.println("The average time for : " + cores + " cores is: " + averageRuntime);

        }


        // write results to a file
//        try{
//            FileWriter fileWriter = new FileWriter("./results.txt");
//            BufferedWriter writer = new BufferedWriter(fileWriter);
//            writer.write("" + averageTimeSerial);
//            writer.close();
//        }catch(IOException err){
//            e.printStackTrace();
//        }

    }
}
