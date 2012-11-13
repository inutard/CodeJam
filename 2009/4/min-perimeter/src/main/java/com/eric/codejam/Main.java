package com.eric.codejam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eric.codejam.geometry.PointInt;
import com.eric.codejam.geometry.PointLong;

public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static String handleCase(int caseNumber, InputData input) {

        
        
        log.info("Starting case {}", caseNumber);
        
        double ans = DivideConq.findMinPerimTriangle(input.points);

        log.info("Done calculating answer case {}", caseNumber);
        
        DecimalFormat decim = new DecimalFormat("0.00000000000");
        decim.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        
        return ("Case #" + caseNumber + ": " + decim.format(ans));
                

    }
    
    static class InputData {
        List<PointInt> points;
    }
    static InputData readInput(Scanner scanner) {
        List<PointInt> points = new ArrayList<>();
        
    
        int n = scanner.nextInt();
        log.info("Reading data...scanner");
        
        for (int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new PointInt(x, y));
        }
        
        InputData  i = new InputData();
        i.points = points;
        return i;
        
    }
    
    static InputData readInput(BufferedReader br) throws IOException {
        
    
        String line = br.readLine();
        int n = Integer.parseInt(line);
        
        List<PointInt> points = new ArrayList<>(n);
        
        log.info("Reading data...BR");
        Pattern split = Pattern.compile(" ");
        for (int i = 0; i < n; ++i) {
            String[] strArray = split.split(br.readLine());
            
            int x = Integer.parseInt(strArray[0]);
            int y = Integer.parseInt(strArray[1]);
            points.add(new PointInt(x,y));
        }
        log.info("Done Reading data...BR");
        
        InputData  i = new InputData();
        i.points = points;
        return i;
        
    }

    


    public Main() {

        // TODO Add test case vars
        super();
    }

    static int test = 0;
    public static void main(String args[]) throws Exception {

        if (args.length < 1) {
           //args = new String[] { "sample.txt" };
           //args = new String[] { "smallInput.txt" };
           args = new String[] { "largeInput.txt" };
        }
        log.info("Input file {}", args[0]);

        //
        
        InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(args[0])));
        final BufferedReader br = new BufferedReader(isr);
       // final Scanner scanner = new Scanner(br);

        String line = br.readLine();
        
        final int t = Integer.parseInt(line);
        
        OutputStream os = new FileOutputStream(args[0] + ".out");
        PrintStream pos = new PrintStream(os);

        final String[] answers = new String[t];
        final InputData[] input = new InputData[t];
                
        
        final int THREADS = 2;
        test = 0;
        Thread[] threads = new Thread[THREADS];
        
        long overAllStart = System.currentTimeMillis();
        
        for (int i = 0; i < threads.length; i++) {
            // final B inst = new B();
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        int ltest = 0;
                        synchronized (answers) {
                            ltest = test;
                            test++;
                            if (ltest >= t) {
                                return;
                            }
                            try {
                                input[ltest] = readInput(br);
                            } catch (IOException ex) {

                            }
                        }
                        long t = System.currentTimeMillis();
                        String ans = handleCase(ltest + 1, input[ltest]);
                        input[ltest] = null;

                        log.info("Test {} calc finished in {}", ltest,
                                + (System.currentTimeMillis() - t));
                        answers[ltest] = ans;

                    }
                }
            });
            threads[i].setPriority(Thread.MIN_PRIORITY);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++)
        {
            threads[i].join();
        }
        for (int test = 1; test <= t; test++)
        {
            pos.println(answers[test - 1]);
        }

        log.info("Finished");
        
        log.info("Total time {}", 
                + (System.currentTimeMillis() - overAllStart));
        
        os.close();
        //scanner.close();
        br.close();
    }
}