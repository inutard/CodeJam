package com.eric.codejam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eric.codejam.InputData;
import com.eric.codejam.Main;
import com.eric.codejam.geometry.PointInt;
import com.eric.codejam.main.Runner;
import com.eric.codejam.multithread.Consumer.TestCaseHandler;
import com.eric.codejam.multithread.Producer.TestCaseInputReader;
import com.eric.codejam.utils.GridChar;

public class Main implements TestCaseHandler<InputData>, TestCaseInputReader<InputData> {

    final static Logger log = LoggerFactory.getLogger(Main.class);
    
    @Override
    public String handleCase(int caseNumber, InputData input) {

        log.info("Starting calculating case {}", caseNumber);
        
        log.debug("Grid {}", input.grid);
        //double ans = DivideConq.findMinPerimTriangle(input.points);

        for(int newSize = input.k; newSize <= 3 * input.k; ++newSize) {
            for(int offsetRow = 0; offsetRow <= newSize-input.k; ++offsetRow) {
                for(int offsetCol = 0; offsetCol <= newSize - input.k; ++offsetCol) {
                    GridChar grid = GridChar.buildEmptyGrid(newSize, newSize, '.');
                    
                    //copy input.grid
                    for(int r = 0; r < input.grid.getRows(); ++r) {
                        for(int c = 0; c < input.grid.getCols(); ++c) {
                            grid.setEntry(r+offsetRow, c+offsetCol, input.grid.getEntry(r,c));
                        }
                    }
                    
                    log.debug("Grid {}", grid);
                    if (isPerfect(grid)) {
                        int cost = newSize * newSize - input.k * input.k;
                        return ("Case #" + caseNumber + ": " + cost);
                    }
                }
            }
        }
        
        log.debug("Is perfect {}", isPerfect(input.grid));
        log.info("Done calculating answer case {}", caseNumber);
        
        //DecimalFormat decim = new DecimalFormat("0.00000000000");
        //decim.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
        
        return ("Case #" + caseNumber + ": " + "ERROR");
    }
    
    boolean isPerfect(GridChar grid) {
        //r, c == c, r
        //r, c == (k-1) - c, (k-1) - r
        
        for(int r = 0; r < grid.getRows(); ++r) {
            for(int c = 0; c < grid.getCols(); ++c) {
                char ch = grid.getEntry(r, c);
                if (ch == '.')
                    continue;
                
                //Via 'horizontal' in the diamond symmetry 0,0 to r,c
                char chHorizontal =  grid.getEntry(c, r);
                char chVertial = grid.getEntry(grid.getCols() - 1- c, grid.getRows() - 1 - r);
                if ( (chHorizontal != '.' && ch != chHorizontal ) ||
                        (chVertial != '.' && ch != chVertial) ) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    
    
    @Override
    public InputData readInput(BufferedReader br, int testCase) throws IOException {
        
    
        InputData  input = new InputData(testCase);
        
        input.k = Integer.parseInt(br.readLine());
        
        //map to a grid.  
        GridChar grid = GridChar.buildEmptyGrid(input.k, input.k, '.');
        
        int startingRow = 0;
        for(int startingCol = input.k - 1; startingCol >= 0; --startingCol) {
            String[] line = br.readLine().trim().split("\\s+");
            for(int i = 0; i < line.length; ++i) {
                int row = startingRow + i;
                int col = startingCol + i;
                grid.setEntry(row, col, line[i].charAt(0));
            }
        }
        
        int startingCol = 0;
        for(startingRow = 1; startingRow < input.k; ++startingRow) {
            String[] line = br.readLine().trim().split("\\s+");
            for(int i = 0; i < line.length; ++i) {
                int row = startingRow + i;
                int col = startingCol + i;
                grid.setEntry(row, col, line[i].charAt(0));
            }
        }
        
        input.grid = grid;
        
        return input;
        
    }

    


    public Main() {
        super();
    }
    
    
    public static void main(String args[]) throws Exception {

        if (args.length < 1) {
            args = new String[] { "sample.txt" };
           // args = new String[] { "B-small-practice.in" };
//            args = new String[] { "B-large-practice.in" };
         }
         log.info("Input file {}", args[0]);

         Main m = new Main();
         Runner.goSingleThread(args[0], m, m);
         //Runner.go(args[0], m, m, new InputData(-1));

        
       
    }

    
}