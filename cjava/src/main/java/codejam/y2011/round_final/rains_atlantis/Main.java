package codejam.y2011.round_final.rains_atlantis;

import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codejam.utils.main.DefaultInputFiles;
import codejam.utils.main.Runner.TestCaseInputScanner;
import codejam.utils.multithread.Consumer.TestCaseHandler;
import codejam.utils.utils.Direction;
import codejam.utils.utils.Grid;

import com.google.common.base.Preconditions;

public class Main implements TestCaseHandler<InputData>, TestCaseInputScanner<InputData>, DefaultInputFiles {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    @Override
    public String[] getDefaultInputFiles() {
        return new String[] { "sample.in" };
     //    return new String[] { "B-small-practice.in" };
       //  return new String[] { "B-small-practice.in", "B-large-practice.in" };
    }

    @Override
    public InputData readInput(Scanner scanner, int testCase) {

        InputData in = new InputData(testCase);

        in.H = scanner.nextInt();
        in.W = scanner.nextInt();
        in.M = scanner.nextLong();
        
        in.grid =  Grid.buildFromScanner(scanner, in.H, in.W, Grid.fromScannerLong, 0l);
        
        
        return in;
    }

    void determineWaterLevel(Grid<Long> grid, int gridIndex)
    {
        //Find cheapest path to edge
        
        //level location
        PriorityQueue< Pair<Long, Integer> > toVisit = new PriorityQueue<>();
        
        boolean[] seen = new boolean[grid.getSize()];
        
        toVisit.add(new ImmutablePair<>(grid.getEntry(gridIndex), gridIndex));
        
        while(!toVisit.isEmpty()) {
            Pair<Long, Integer> levelIndex = toVisit.poll();
            
            if (seen[levelIndex.getRight()])
                continue;
            
            
            seen[levelIndex.getRight()] = true;
            
            if (grid.minDistanceToEdge(levelIndex.getRight()) == 0) 
            {
                grid.setEntry(gridIndex, Math.max(grid.getEntry(gridIndex), levelIndex.getLeft()));
                return;
            }
            
            for(int dir = 0; dir <= 3; ++dir) {
                Integer index = grid.getIndex(levelIndex.getRight(), Direction.NORTH.turn(2*dir));
                if (index == null)
                    continue;
                
                //Max because the water 
                toVisit.add(new ImmutablePair<>( Math.max( levelIndex.getLeft(), grid.getEntry(index)), index));
            }
            
        }
        
        
        log.error("ERror");
        
    }
    
    void doErosion(Grid<Long> land, Grid<Long> waterLevel, Grid<Long> nextGrid, long maxErosion) {
        for(int index = 0; index < waterLevel.getSize(); ++index) {
            
            long minWaterLevel = Long.MAX_VALUE;
            
            //Find minimum adjacent square
            for(int dir = 0; dir <= 3; ++dir) {
                // Integer adjIndex = waterLevel.getIndex(index, Direction.NORTH.turn(2*dir));
                
                Long adjWaterLevel = waterLevel.getEntry(index, Direction.NORTH.turn(2*dir));
                
                //if (adjIndex == null)
                  //  continue;
                
                minWaterLevel = Math.min(minWaterLevel, adjWaterLevel);
            }
            
            Long curWaterLevel = waterLevel.getEntry(index);            
            Preconditions.checkState(curWaterLevel >= minWaterLevel);
            
            Long curLandLevel = land.getEntry(index);
            
            long erosion = curWaterLevel - minWaterLevel; 
            erosion = Math.min(maxErosion, erosion);
            
            nextGrid.setEntry(index, Math.max(0, curLandLevel - erosion));
        
        }
    }
    public String bruteForce(InputData in) {
        
        Grid<Long> land = new Grid<Long>(in.grid);
        
        for(int iter = 0; iter < 600; ++iter) {
            
            log.debug("Grid land {}", land);
            
            Set<Integer> zeros = land.getIndexesOf(0l);
            
            if (zeros.size() == land.getSize()) {
                return String.format("Case #%d: %d", in.testCase, iter);
            }
            
            Grid<Long> waterLevel = new Grid<>(land);
            
            for(int index = 0; index < waterLevel.getSize(); ++index) {
                determineWaterLevel(waterLevel, index);
            }
            
            /*
            int maxDistEdge = Math.max( waterLevel.getRows() / 2, waterLevel.getCols() / 2); 
            
            //go outside in
            for(int distEdge = 0; distEdge < maxDistEdge; ++distEdge )
            {
                int startCol = distEdge;
                int stopCol = waterLevel.getCols() - 1 - distEdge;
                int startRow = distEdge;
                int stopRow = waterLevel.getRows() - 1 - distEdge;
                
                for( int col = startCol; col <= stopCol; ++col) {
                    determineWaterLevel(waterLevel, startRow, col);
                    determineWaterLevel(waterLevel, stopRow, col);
                }
                
                for( int row = startRow; row <= stopRow; ++row) {
                    determineWaterLevel(waterLevel, row, startCol);
                    determineWaterLevel(waterLevel, row, stopCol);
                }
            }*/
            
            log.debug("Water level {}", waterLevel);
            
            Grid<Long> nextLand = new Grid<Long>(land);
            
            doErosion(land,waterLevel,nextLand, in.M);
            
            log.debug("After erosion {}", nextLand);
            
            land = nextLand;
            
            
            
        }
        
        return "g";
    }
    
    public String handleCase(InputData in) {

        //        return String.format("Case #%d: %d", in.testCase, minSize == Integer.MAX_VALUE ? 0 : minSize);
        
        return bruteForce(in);
    }

}
