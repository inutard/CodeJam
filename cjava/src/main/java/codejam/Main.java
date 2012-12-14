package codejam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codejam.utils.main.DefaultInputFiles;
import codejam.utils.main.Runner;

public class Main  {

    final static Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String args[]) throws Exception {


        //2008 amer semis
        
        //codejam.y2008.round_amer.mixing.Main m = new codejam.y2008.round_amer.mixing.Main();
        //codejam.y2008.round_amer.code_sequence.Main m = new codejam.y2008.round_amer.code_sequence.Main();
        codejam.y2008.round_amer.test_passing.Main m = new codejam.y2008.round_amer.test_passing.Main();
        
        //2008 apac semis
        //A and B are in c++        
        //C++ for millionaire exists that is very fast 
        //codejam.y2008.round_apac.millionaire.Main m = new codejam.y2008.round_apac.millionaire.Main();
        //codejam.y2008.round_apac.modern_art.Main m = new codejam.y2008.round_apac.modern_art.Main();
        
        //Round 1A -- 2009
        //In C++
        
         //Round 1B -- 2009
        //A in c++
         //codejam.y2009.round_1B.next_number.Main m = new codejam.y2009.round_1B.next_number.Main();
         //codejam.y2009.round_1B.square_math.Main m = new codejam.y2009.round_1B.square_math.Main();
         
         //Round 1C -- 2009 
         //codejam.y2009.round_1C.all_your_base.Main m = new codejam.y2009.round_1C.all_your_base.Main();
         //codejam.y2009.round_1C.center_mass.Main m = new codejam.y2009.round_1C.center_mass.Main();
         //codejam.y2009.round_1C.bribe_prisoner.Main m = new codejam.y2009.round_1C.bribe_prisoner.Main();
         
         //Round 2 -- 2009
        
         //codejam.y2009.crazy_rows.Main m = new codejam.y2009.crazy_rows.Main();
         //codejam.y2009.digging_problem.Main m = new codejam.y2009.digging_problem.Main();
         //codejam.y2009.round_2.stock_charts.Main m = new codejam.y2009.round_2.stock_charts.Main();
         //codejam.y2009.round_2.watering_plants.Main m = new codejam.y2009.round_2.watering_plants.Main();
         
         //Round 3 -- 2009
         //codejam.y2009.sokoban.Main m = new codejam.y2009.sokoban.Main();
         //codejam.y2009.alphabetomials.Main m = new codejam.y2009.alphabetomials.Main();
        // codejam.y2009.round_3.football_team.Main m = new codejam.y2009.round_3.football_team.Main();
         //codejam.y2009.interesting_ranges.Main m = new codejam.y2009.interesting_ranges.Main();
         
         //Round 4 -- 2009
         //codejam.y2009.year_more.Main m = new codejam.y2009.year_more.Main();
         
         //Both of these still a bit slow, especially min perimeter
         //codejam.y2009.min_perimeter.Main m = new codejam.y2009.min_perimeter.Main();      
        // codejam.y2009.double_sort_grid.Main m = new codejam.y2009.double_sort_grid.Main();
      
         //2010 qual
         //codejam.y2010.fair_warning.Main m = new codejam.y2010.fair_warning.Main();
         //codejam.y2010.theme_park.Main m = new codejam.y2010.theme_park.Main();
         
         //2010 1A
         //codejam.y2010.rotate.Main m = new codejam.y2010.rotate.Main();
         //codejam.y2010.smooth.Main m = new codejam.y2010.smooth.Main();
         //codejam.y2010.number_game.Main m = new codejam.y2010.number_game.Main();
         
         //2010 1B
         
         //codejam.y2010.file_fix.Main m = new codejam.y2010.file_fix.Main();
         //codejam.y2010.chicks.Main m = new codejam.y2010.chicks.Main();
         //codejam.y2010.rank_pure.Main m = new codejam.y2010.rank_pure.Main();
         
         //2010 1C
         //large a bit slow
         //codejam.y2010.chess_boards.Main m = new codejam.y2010.chess_boards.Main();
         //codejam.y2010.load_testing.Main m = new codejam.y2010.load_testing.Main();         
         //codejam.y2010.rope_intranet.Main m = new codejam.y2010.rope_intranet.Main();

         //2010 2         
         //TODO large slow
         //codejam.y2010.round_2.diamond.Main m = new codejam.y2010.round_2.diamond.Main();
         //codejam.y2010.round_2.world_cup.Main m = new codejam.y2010.round_2.world_cup.Main();         
         //codejam.y2010.round_2.bacteria.Main m = new codejam.y2010.round_2.bacteria.Main(); 
        //TODO corrections small
         //codejam.y2010.round_2.goats.Main m = new codejam.y2010.round_2.goats.Main();
                  
         //2010 3
         
         //codejam.y2010.round_3.rng.Main m = new codejam.y2010.round_3.rng.Main();         
        //codejam.y2010.round_3.boards.Main m = new codejam.y2010.round_3.boards.Main();
        //codejam.y2010.round_3.hotdog.Main m = new codejam.y2010.round_3.hotdog.Main();       
        //TODO large is slow
        //codejam.y2010.round_3.different_sum.Main m = new codejam.y2010.round_3.different_sum.Main();
       
                 
         //2011 1A
        

         //A in C++
         //TODO large is quite slow 14 secs -- 30 secs.  Can improve the algorithm
        // codejam.y2011.round_1A.killer_word.Main m = new codejam.y2011.round_1A.killer_word.Main();
         //codejam.y2011.round_1A.dominion.Main m = new codejam.y2011.round_1A.dominion.Main();
         
         //2011 1B
         //A in C++
         //codejam.y2011.round_1B.hotdog_revenge.Main m = new codejam.y2011.round_1B.hotdog_revenge.Main();
         //codejam.y2011.round_1B.house_kittens.Main m = new codejam.y2011.round_1B.house_kittens.Main();
        
        String[] files = {};
        if (m instanceof DefaultInputFiles) {
            files = ((DefaultInputFiles) m).getDefaultInputFiles();
        }
        
        if (args.length >= 1) {            
            files = args;
        }
        for (String file : files) {
            log.info("Input file {}", file);

             Runner.goSingleThread(file, m, m);
            //Runner.go(file, m, m, 3);
        }       
    }

    
}