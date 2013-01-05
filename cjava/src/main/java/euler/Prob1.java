package euler;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.fraction.Fraction;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codejam.utils.utils.Direction;
import codejam.utils.utils.Grid;
import codejam.utils.utils.PermutationWithRepetition;
import codejam.utils.utils.Permutations;
import codejam.utils.utils.Prime;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.math.BigIntegerMath;
import com.google.common.math.IntMath;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;

public class Prob1 {

    final static Logger log = LoggerFactory.getLogger(Prob1.class);
    
    public static void main(String args[]) throws Exception {
        long start = System.currentTimeMillis();
        problem48();
        long end = System.currentTimeMillis();
        
        log.info("Elapsed time {} ms", end - start);
        
    }
    
    public static void problem48() {
        BigInteger sum  = BigInteger.ZERO;
        for(int i = 1; i <= 1000; ++i) {
            sum  = sum.add(BigInteger.valueOf(i).pow(i));
        }
        
        sum = sum.mod(BigInteger.valueOf(10000000000L));
        
        log.debug("Sum {} ", sum);
    }
    
    public static void problem43() {
        Integer[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        
        Integer[] out = new Integer[digits.length];
        
        Permutations<Integer> p = Permutations.create(digits,out);
        
        List<Integer> primes = Prime.generatePrimes(17);
        
        long sum = 0;
        
        while(p.next()) {
            boolean special = true;
            
            for(int i = 0; i < 7; ++i) {
                int start = i + 1;
                
                int num = 0;
                
                for(int d = start; d < start + 3; ++d) {
                    num *= 10;
                    num += out[d];
                }
            
                if (num % primes.get(i) != 0) {
                    special = false;
                    break;
                }
                
            }
            
            if (special) {
                
                long num = 0;
                
                for(int d = 0; d < out.length; ++d) {
                    num *= 10;
                    num += out[d];
                }
                
                sum += num;
                log.debug("Num {} or {} has the property", (Object)out, num);
            }
        }
        
        log.debug("Sum is {}", sum);
    }
    
    public static void problem42() {
        Set<Integer> triangleNums = Sets.newHashSet();
        
        int tNum = 0;
        
        int max = 26 * 26;
        
        int next = 1;
        
        while(tNum < max) {
            tNum += next;
            triangleNums.add(tNum);
            next++;
        }
        
        Scanner scanner = new Scanner(Prob1.class.getResourceAsStream("prob42.txt"));
        
        String[] s = scanner.next().replace("\"", "").split(",");
        
        int count = 0;
        
        for (String str : s) {
            //log.debug(str);
            int num = 0;
            for(int c = 0; c < str.length(); ++c) {
                num += str.charAt(c) - 'A' + 1;
            }
            
            if (triangleNums.contains(num)) {
                log.debug("Found triangle word {}", str);
                ++count;
            }
        }
        
        log.debug("Count is {}", count);
        
    }
    
    public static void problem41() {
        List<Integer> knownPrimes = Prime.generatePrimes(1000000);
        
        for(int digits = 2; digits <= 9; ++digits) {
            Integer[] digitsArray = new Integer[digits];
            
            for(int d = 1; d <= digits; ++d) {
                digitsArray[d-1] = d;
            }
            
            Integer[] out = new Integer[digits];
            
            Permutations<Integer> p = Permutations.create(digitsArray, out);
            
            while(p.next()) {
                
                int num = 0;
                
                for(int d = 0; d < digits; ++d) {
                    num *= 10;
                    num += out[d];
                }
        
                if (Prime.isPrime(num, knownPrimes)) {
                    log.debug("pandigital prime {}", num);
                }
                
            }
        }
    }
    
    public static void problem40() {
        StringBuffer sb = new StringBuffer();
        
        int i = 1;
        
        while(sb.length() < 1000000) {
            sb.append(Integer.toString(i));
            ++i;
        }
        
        int digit = 1;
        int product = 1;
        for(i = 0; i < 7; ++i) {
            log.debug("Taking {} digit", digit);
            product *= Character.digit(sb.charAt(digit - 1), 10);
            digit *= 10;
            
        }
        
        log.debug("Product p {}", product);
    }
    
    public static void problem39() {
        int max = 0;
        for(int p = 3; p <= 1000; ++p) {
            int num = 0;
            
            for(int a = 1; a < p; ++a) {
                for(int b = a ; b < p; ++b) {
                    int c = p - a - b;
                    int ab = a*a + b*b;
                    int cc = c*c;
                    if (ab == cc) {
                        //log.debug("Found {} {} {} with p {}", a,b,c,p);
                        num ++;
                    }
                
                }
            }
            
            if (num > max) {
                max = num;
                log.debug("New max {} for p {}", max, p);
            }
        }
    }
    
    public static void problem38() {
      
        int max = 0;
        
        outer:
        for(int num = 1; num < 10000; ++num) {
            int i = 1;
            String s = "";
            
            while (s.length() < 9) {
                s = s + i * num;
                ++i;
            }
            
            if (s.length() > 9)
                continue;
            
            for(int d = 1; d <= 9; ++d) {
                if (!s.contains(Integer.toString(d))) {
                    continue outer;
                }
            }
            
            int pan = Integer.parseInt(s);
            
            if (pan > max) {
                max = pan;
            log.debug("Found {} makes {}", num, s);
            }
        }
        /*
        Integer[] digits = new Integer[9];
        for(int i = 1; i <= 9; ++i) {
            digits[i-1] = i;
        }
        
        Integer[] perm = new Integer[9];
        
        int max = 0;
        Permutations<Integer> p = Permutations.create(digits, perm);
        
        while(p.next()) {
            int panNum  = 0;
            for(int i = 0; i < 9; ++i) {
                panNum *= 10;
                panNum += perm[i];                
            }
            
            for(int n = 12; n >= 2; --n) {
                int trial = panNum;
                boolean ok = true;
                for(int i = n; i >= 1; --i) {
                    if (trial % i != 0) {
                        ok = false;
                        break;
                    }
                    
                    trial /= i;
                        
                }
                
                if (ok && panNum > max) {
                    max = panNum;
                    log.debug("Found pan {} from {} multiplying 1 to {}", panNum, trial, n);
                }
            }
        }*/
    }
    
    public static void problem37() {
        List<Integer> primes = Prime.generatePrimes(1000000);

        Set<Integer> primeSet = Sets.newHashSet(primes);
        
        int[] powTen = new int[8];
        
        powTen[0] = 10;
        
        for(int i = 1; i < powTen.length; ++i) {
            powTen[i] = 10 * powTen[i-1];
        }
        
        int sum = 0;
        for(int p : primes) {
            if (p < 10)
                continue;
            
            boolean isTruncable = true;
            int truncP = p;
            
            for(int lastDigit = powTen.length - 1; lastDigit >= 0; --lastDigit) {
                truncP %= powTen[lastDigit];
                
                if (!primeSet.contains(truncP)) {
                    isTruncable = false;
                    break;
                }
            }
            
            truncP = p;
            
            while(truncP > 0 && isTruncable) {
                if (!primeSet.contains(truncP)) {
                    isTruncable = false;
                    break;
                }
                
                truncP /= 10;
            }
        
            
            if (isTruncable) {
                log.debug("{} is truncable", p);
                sum += p;
            }
        }
        
        log.debug("Sum is {}", sum);
    }
    
    public static void main36() {
        int sum = 0;
        for(int i = 1; i < 1000000; ++i) {
            String s10 = Integer.toString(i, 10);
            String s2 = Integer.toString(i, 2);
            
            String chk = s2.substring(s2.length() - s2.length() / 2);
            //s10 len 4
            //0 1 2 3  with beg
            //len 5
            //4 5
            if ((s10.startsWith(StringUtils.reverse(s10.substring(s10.length() - s10.length() / 2)))) &&
            s2.startsWith(StringUtils.reverse(s2.substring(s2.length() - s2.length() / 2)))) {
                    log.debug("Found {} {} ", s10, s2);
                    sum += i;
            }
        }
        
        log.debug("Sum {}", sum);
    }
    
    public static void main35(String args[]) throws Exception {
        //10987654321

        long[] powTen;

        int powers = 8;
        powTen = new long[powers];
        long pt = 1;
        for (int i = 0; i < powers; ++i) {
            powTen[i] = pt;
            pt *= 10;
        }

        List<Integer> primes = Prime.generatePrimes(1000000);

        Set<Integer> primeSet = Sets.newHashSet();
        
        primeSet.addAll(primes);
        
        int count = 0;
        
        int numDigits = 1;
        
        for(int prime : primes) {
            if (prime >= powTen[numDigits]) {
                ++numDigits;
            }
            
            boolean ok = true;
            
            for(int shift = 0; shift < numDigits; ++shift) {
                int digit = prime % 10;
                prime /= 10;
                prime += digit * powTen[numDigits - 1];
                if (!primeSet.contains(prime)) {
                    ok = false;
                    break;
                }
            }
            
            if (ok) {
                log.debug("Circular prime {}  count {}", prime, count);
                ++count;
            }
            
        }
                        

        log.debug("Count is {}", count);
    }
    
    public static void main34_faster(String args[]) throws Exception {
        // 10987654321

        int[] factorials = new int[10];

        factorials[0] = 1;
        for (int i = 1; i <= 9; ++i) {
            factorials[i] = i * factorials[i-1];
        }

        int sum = 0;
        for (int num = 10; num < 10000000; ++num) {

            int digits = num;
            int fac = 0;
            
            while(digits > 0) {
                int digit = digits % 10;
                fac += factorials[digit];
                digits /= 10;
            }                

            if (fac == num) {
                log.debug("Found {}", num);
                sum += num;
            }
        

        }

        log.debug("Sum is {}", sum);
    }
    
    public static void main34(String args[]) throws Exception {
                 //10987654321
        
        long[] powTen;
        
        int powers = 15;
        powTen = new long[powers];
        long pt = 1;
        for(int i = 0; i < powers; ++i) {
            powTen[i] = pt;
            pt *= 10;
        }
    
        
        int[] factorials = new int[10];
        
        for(int i = 0; i <= 9; ++i) {
            factorials[i] = IntMath.factorial(i);
        }
        
        long sum = 0;
        for (int numDigits = 2; numDigits <= 7; ++numDigits) {
            
            for(long num = powTen[numDigits-1]; num < powTen[numDigits]; ++num) {
                long fac = 0;
                for(int d = 1; d <= numDigits; ++d) {
                    fac += factorials[getDigit(num, d, powTen)];
                }
                
                if (fac == num) {
                    log.debug("Found {}", num);
                    sum += num;
                }
            }
            
        }
        
        log.debug("Sum is {}", sum);
    }
    
    
    
    static int getDigit(long num, int digit, long[] powTen) {
    
        num %= powTen[digit];
        num /= powTen[digit - 1];
        
        return Ints.checkedCast(num);
    }
    
    public static void main33(String args[]) throws Exception {
        
        Fraction product = new Fraction(1);
        
        for (int num = 10; num <= 99; ++num) {
            for(int denom = 11; denom <= 99; ++denom) {
                int numSimp = num / 10;
                int denomSimp = denom % 10;
                
                int numElim = num % 10;
                int denomElim = denom / 10;

                if (denomSimp == 0)
                    continue;

                if (numElim != denomElim)
                    continue;

                Fraction f = new Fraction(num, denom);
                Fraction f2 = new Fraction(numSimp, denomSimp);
                
                if (f2.getDenominator() == 1)
                    continue;
                
                if (f.equals(f2)) {
                    log.debug("Fractions {} / {} == {}", num, denom, f2);
                    product = product.multiply(f);
                }
            }
        }
        
        log.debug("Product {}", product);
    }
    
    public static void main32(String args[]) throws Exception {
        //Iterator<Long> it = new CombinationIterator(n, k);
        
        int[] powTen = new int[] { 1, 10, 100, 1000, 
            10000, 
            100000, 
            1000000, 
            10000000, 
            100000000 };
        
        Set<Integer> panDigital = Sets.newHashSet();
        
        Integer choice[] = new Integer[] { 0, 1, 2 };
        Integer output[] = new Integer[9];
        
        PermutationWithRepetition<Integer> perm = PermutationWithRepetition.create(choice, output);
        
        while(perm.hasNext()) {
            perm.next();
            
            List<Integer> aDigits = Lists.newArrayList();
            List<Integer> bDigits = Lists.newArrayList();
            List<Integer> pDigits = Lists.newArrayList();
            
            for(int d = 1; d <= 9; ++d) {
                if (output[d-1] == 0) {
                    aDigits.add(d);
                } else if (output[d-1] == 1) {
                    bDigits.add(d);
                } else if (output[d-1] == 2) {
                    pDigits.add(d);
                } else {
                    Preconditions.checkState(false);
                }
            }
            
            if (pDigits.size() < 3 || pDigits.size() > 6)
                continue;
            
            if (aDigits.isEmpty() || bDigits.isEmpty())
                continue;
            
            Integer[] aDigitsArray = new Integer[aDigits.size()];
            aDigitsArray = aDigits.toArray(aDigitsArray);
            Integer[] bDigitsArray = new Integer[bDigits.size()];
            bDigitsArray = bDigits.toArray(bDigitsArray);
            
            Integer[] aDigitsOut = new Integer[aDigitsArray.length];
            Integer[] bDigitsOut = new Integer[bDigitsArray.length];
            
            Permutations<Integer> permA = Permutations.create(aDigitsArray, aDigitsOut);
            
            while(permA.next()) {
                
                
                int a = 0;
                for(int i = 0; i < aDigitsOut.length; ++i) {
                    a+=aDigitsOut[i] * powTen[i];
                }
                
                Permutations<Integer> permB = Permutations.create(bDigitsArray, bDigitsOut);
                
                while(permB.next()) {
                int b = 0;
                for(int i = 0; i < bDigitsOut.length; ++i) {
                    b+=bDigitsOut[i] * powTen[i];
                }
                
                int p = a * b;
                
                String pStr = Integer.toString(p);
                
                if (pStr.length() != pDigits.size())
                    continue;
                
                boolean match = true;
                for(int pDigit : pDigits) {
                    if (!pStr.contains(Integer.toString(pDigit))) {
                        match = false;
                        break;
                    }
                }
                
                if (match) {
                    log.debug("Unusual number {} * {} = {}", a, b, p);
                    panDigital.add(p);
                }
                
                }
            }
            
            
            
            //log.debug("output {}", (Object) output);
        }
        
        int sum = 0;
        for(int pan : panDigital) {
            sum += pan;
        }
        
        log.debug("Sum is {}", sum);
    }
    
    
    public static void main31(String args[]) throws Exception
    {
      List<Integer> values = Arrays.asList(1, 2, 5, 10, 20, 50, 100, 200);
        //List<Integer> values = Arrays.asList(1, 5, 10, 25, 50, 100);
        int total = 200;
        /*
         * 1  -- (1) 1
         * 2  -- (2) 1,1 ; 2
         * 3  -- (2) 1,1,1 ; 2,1 
         * 4  -- (3) 1x4 ; 2,1,1 ; 2,2
         * 5  -- (4)  ; 5
         * 6  -- (5)  ; 2,2,2
         * 7          ; 5,2
         * 8          ; 2,2,2,2
         * A new way every time the sum % coin == 0
         */
                
        log.debug("Ways {}", countWays(values, values.size() - 1, total));
        
        int[] ways = new int[total+1];
        ways[0] = 1;
        for(int coinIndex = 0; coinIndex < values.size(); ++coinIndex) {
            int coin = values.get(coinIndex);
            for(int j = coin; j <= total; ++j) {
                ways[j] = ways[j] + ways[j - coin];
            }
        }
        
        log.debug("Ways {}", ways[total]);
        
    }
    
    static int countWays(List<Integer> values, int maxCoin, int sum) {
        
        int value = values.get(maxCoin);
        
        if (maxCoin == 0) {
            if (sum % value == 0) 
                return 1;
            else 
                return 0;
        }
        
        int max = sum / value;
        
        int ways = 0;
        for(int coinsUsed = 0; coinsUsed <= max; ++coinsUsed) {
            ways += countWays(values, maxCoin - 1, sum - coinsUsed * value);
        }
        
        return ways;
    }
    
    
    
    public static void main30(String args[]) throws Exception {
        int pow = 5;
                
        int total = 0;
        
        for(int i = 2; i < 2000000; ++i) {
            String s = Integer.toString(i);
            
            int sum = 0;
            
            for(int c = 0; c < s.length(); ++c) {
                sum += IntMath.pow(Character.digit(s.charAt(c), 10), pow);
            }
            
            if (sum == i) {
                log.debug("Num {}", i);
                total += i;
            }
        }
        
        log.debug("Sum {}", total);
    }
    
    public static void main29(String args[]) throws Exception {
        Set<BigInteger> set = Sets.newHashSet();
        
        int max = 100;
        for(int a = 2; a <= max; ++a) {
            for(int b = 2; b <= max; ++b) {
                set.add(BigInteger.valueOf(a).pow(b));
            }
        }
        
        log.debug("Set size {}", set.size());
    }
    public static void main28(String args[]) throws Exception {
        int size = 1001;
        
        Grid<Integer> grid = Grid.buildEmptyGrid(size, size, 0);
        
        Integer index = grid.getIndex(size / 2, size / 2);
        
        Direction dir = Direction.EAST;
        
        int max = size * size;
        
        int movements = 1;
        int movementsLeft = 1;
        
        for(int i = 1; i <= max; ++i) {
            grid.setEntry(index, i);
            
            if (i == max)
                break;
            
            index = grid.getIndex(index, dir);
            
            --movementsLeft;
            
            if (movementsLeft == 0) {
                dir = dir.turn(2);
                
                if (dir == Direction.WEST || dir == Direction.EAST) {
                    ++movements;
                }
                
                movementsLeft = movements;
            }
        }
        
        index = 0;
        
        int sum = 0;
        for(int i = 0; i < size; ++i) {
            sum += grid.getEntry(index);
            
            index = grid.getIndex(index, Direction.SOUTH_EAST);
        }
        
        index = grid.getIndex(0, size - 1);
        for(int i = 0; i < size; ++i) {
            sum += grid.getEntry(index);
            
            index = grid.getIndex(index, Direction.SOUTH_WEST);
        }
        
        sum -= grid.getEntry(size / 2, size / 2);
        
        log.debug("Sum {}", sum);
    }
    public static void main27(String args[]) throws Exception {
        List<Integer> primes = Prime.generatePrimes(1000*1000 * 2);
        
        int max = 0;
        
        List<Integer> bPrimes = Prime.generatePrimes(1000);
        
        
        for(int a = -100; a <= 100; ++a) {
            //log.debug("A is {}", a);
            for(int b : bPrimes) {
                Preconditions.checkState(primes.contains(Math.abs(b)));
                
                int n = 0;
                for(n = 0; n < 100; ++n) {
                    int val = n * n + a * n  + b;
                    if (!primes.contains(val)) {
                        break;
                    }
                }
                if (n > max) {
                    max = n;
                    log.debug("a {} b {} num primes {}.  product {}", a,b, n, a*b);
                }
            }
        }
    }
    
    /*
     * dividend / divisor
     */
    static int getRepetitionLength(final int divisor, int dividend) {
        double ans = 0;

        int origDividend = dividend;
        int factor = 1;

        List<Integer> remainders = Lists.newArrayList();
        while (true) {

            while (dividend < divisor) {
                factor *= 10;
                dividend *= 10;
            }
            
            int index = remainders.indexOf(dividend);
            
            if (index != -1) {
                
                /*
                log.debug("Ans {} rep len {} for {} / {}", ans, 
                        remainders.size() - index,
                        origDividend, divisor);
                        */
                        
                return remainders.size() - index;
            }
            
            remainders.add(dividend);
            
            int d = dividend / divisor;
            
            //Too inaccurate
            ans += (double) d / factor;
            int remainer = dividend - d * divisor;
            
            dividend = remainer;

            if (dividend == 0) {
                return 0;
            }
        }
    }
    
    public static void main26(String args[]) throws Exception {
        
        int max = 0;
        
        for(int i = 2; i < 1000; ++i) {
            int rl = getRepetitionLength(i, 1);
            if (rl > max) {
                max = rl;
                log.debug("New max {} for num {}", max, i);
            }
        }
        
        
        
        
    }
    public static void main25(String args[]) throws Exception {
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ONE;
        
        int digits = 1000;
        
        BigInteger min = BigInteger.TEN.pow(digits-1);
        
        int term = 2;
        while(y.compareTo(min) < 0) {
            ++term;
            BigInteger next = x.add(y);
            x = y;
            y = next;
        }
        
        log.debug("Prob 25.  {}", term);
    }
    public static void main24(String args[]) throws Exception {
        Integer[] arr = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        
        Integer[] out = new Integer[10];
        
        Permutations<Integer> p = Permutations.create(arr, out);
        
        int i = 0;
        while(p.next()) {
            ++i;
            if (i==1000000) {
            log.debug("out {}", (Object)out);
            break;
            }
        }
    }
    
    public static void main23(String args[]) throws Exception {
        
        long l = System.currentTimeMillis();
        
        int maxNum = 28123;
        
        boolean[] abundant = new boolean[1+maxNum];
        
        for(int num = 1; num <= maxNum; ++num) {
            
            int sum = 0;
            
            int upperLimit = IntMath.sqrt(num, RoundingMode.DOWN);
            
            for(int factor = 1; factor <= upperLimit; ++factor) {
                if (num % factor == 0) {
                    sum += factor;
                    int otherFactor = num / factor;
                    
                    if (otherFactor != factor && otherFactor != num)
                        sum += otherFactor;
                }
            }
            
            
            if (sum > num) {
                abundant[num] = true;
            }
        }
        
        int sum = 0;
        
        num :
        for(int num = 1; num <= maxNum; ++num) {
            
            for(int smaller = 1; smaller <= num / 2; ++smaller) {
                int larger = num - smaller;
                if (abundant[smaller] && abundant[larger]) {
                    continue num;
                }
            }
            
            //log.debug("Num {} cannot be expressed as abundant", num);
            sum += num;
        }
        
        long elapsed = System.currentTimeMillis() - l ;
        log.debug("Prob 23.  Elapsed ms {} Sum {}", elapsed, sum);
    }
    
    
    public static void main1_22(String args[]) throws Exception {
        ///////////////////////////////////////
        //1
        int sum = 0;
        for(int i = 1; i < 1000; ++i ) {
            if (i%3 ==0 || i % 5 == 0)
                sum +=i;
        }
        log.info("Prob 1 Sum {}", sum);
        
        ///////////////////////////////////////
        //2
        
        sum = 2;
        int fibBack2 = 1;
        int fibBack1 = 2;
        
        while(fibBack1 <= 4000000) {
            int fib = fibBack1 + fibBack2;
            fibBack2 = fibBack1;
            fibBack1 = fib;
            if (fib % 2 == 0) {
                sum += fib;
            }
        }
        
        log.info("Prob 2 sum {}", sum);
        
        //////////////////////////////////////
        //3
        long num = 600851475143L;
        
        int sqRootNum = Ints.checkedCast( LongMath.sqrt(num, RoundingMode.UP) );
        
        List<Integer> primes = Prime.generatePrimes(sqRootNum);
        
        for(int i = primes.size() - 1; i >= 0; --i) {
            if (num % primes.get(i) == 0) {
                log.info("Prob 3 Prime is {}", primes.get(i));
                break;
            }
        }
        ///////////////////////////////////////
        //4
        
        int largest = 0;
        
        for(int i = 100; i <= 999; ++i) {
            for(int j = 100; j <= 999; ++j) {
                sum = i * j;
                
                int toRev = sum;
                int rev = 0;
                
                while(toRev != 0) {
                    rev *= 10;
                    rev += toRev % 10;
                    toRev /= 10;
                }
                if (rev == sum && largest < rev) {
                    largest = sum;
                }
            }
        }
        log.info("Prob 4.  Largest palin {}", largest);
        
        primes = Prime.generatePrimes(20);
        
        Multiset<Integer> primeFactorizationOfN = HashMultiset.create();
        
        for(int prime : primes) {
            primeFactorizationOfN.add(prime);
        }
        
        for(int i = 2; i <= 20; ++i) {
            Multiset<Integer> primeFactorizationOfDivisor = HashMultiset.create();
            //get prime factorization
            int divisor = i;
            
            while(divisor > 1) {
                for(int prime : primes) {
                    if (divisor % prime == 0) {
                        divisor /= prime;
                        primeFactorizationOfDivisor.add(prime);
                    }
                }
            }
            
            for(Integer pf : primeFactorizationOfDivisor) {
                if (primeFactorizationOfDivisor.count(pf) > primeFactorizationOfN.count(pf)) {
                    primeFactorizationOfN.setCount(pf, primeFactorizationOfDivisor.count(pf));
                }
            }
            
        }
        
        long n = 1;
        
        for(Integer pf : primeFactorizationOfN) {
            n *= pf;
        }
        
        log.info("Prob 5.  Divisible by 1-20 : {}", n);
        
//        for(long i = 1; i < 1000000000; ++i) {
//            boolean found = true;
//            
//            for(int j = 2; j <= 20; ++j) {
//                if (i % j != 0) {
//                    found = false;
//                    break;
//                }                    
//            }
//            if (found) {
//            log.info("Prob 5.  Divisible by 1-20 : {}", i);
//            break;
//            }
//        }
        
        
        n = 100;
        
        long sumSq = n * (n+1) * (2*n+1) /  6;
        long sqOfSum = n * (n+1) / 2;
        sqOfSum *= sqOfSum;
        
        log.info("Prob 6.   (1+2+...)^2 - (1^2 + 2^2 + ..)  {}", sqOfSum - sumSq);
        
        
        primes = Prime.generatePrimes(120000);
        log.info("Prob 7. primes 1st {} 10001 - {}", primes.get(0), primes.get(10000));

        String s = 
        "73167176531330624919225119674426574742355349194934" +
        "96983520312774506326239578318016984801869478851843" +
        "85861560789112949495459501737958331952853208805511" +
        "12540698747158523863050715693290963295227443043557" +
        "66896648950445244523161731856403098711121722383113" +
        "62229893423380308135336276614282806444486645238749" +
        "30358907296290491560440772390713810515859307960866" +
        "70172427121883998797908792274921901699720888093776" +
        "65727333001053367881220235421809751254540594752243" +
        "52584907711670556013604839586446706324415722155397" +
        "53697817977846174064955149290862569321978468622482" +
        "83972241375657056057490261407972968652414535100474" +
        "82166370484403199890008895243450658541227588666881" +
        "16427171479924442928230863465674813919123162824586" +
        "17866458359124566529476545682848912883142607690042" +
        "24219022671055626321111109370544217506941658960408" +
        "07198403850962455444362981230987879927244284909188" +
        "84580156166097919133875499200524063689912560717606" +
        "05886116467109405077541002256983155200055935729725" +
        "71636269561882670428252483600823257530420752963450";
        
        int max = 0;
        for(int i = 0; i < s.length() - 5; ++i) {
            String sub = s.substring(i, i+5);
            int prod = 1;
            for(int j=0; j<5; ++j) {
                prod *= Character.digit( sub.charAt(j), 10 );
            }
            max = Math.max(max, prod);
        }
        
        log.info("Prob 8 {}", max);
        
        outer:
        for(int i = 1; i <= 1000; ++i) {
            for(int j = i + 1; j <= 1000; ++j) {
                int k = 1000 - i - j;
                if (k <= j)
                    continue;
                
                if (k*k == i*i + j*j) {
                    log.info("Prob 9 {} {} {} --- prod {}", i,j,k,i*j*k);
                    break outer;
                }
            
            }
        }
        
        primes = Prime.generatePrimes(2000000);
        
        long sumL = 0;
        for(int p : primes) {
            sumL += p;
        }
        
        log.info("Prob 10 : {}", sumL);
        
        int[][] gridNum = new int[][] { 
              { 8, 02, 22, 97, 38, 15, 00, 40, 00, 75, 04, 05, 07, 78, 52, 12, 50, 77, 91, 8 },     
              { 49, 49, 99, 40, 17, 81, 18, 57, 60, 87, 17, 40, 98, 43, 69, 48, 04, 56, 62, 00 },
              { 81, 49, 31, 73, 55, 79, 14, 29, 93, 71, 40, 67, 53, 88, 30, 03, 49, 13, 36, 65 },
              { 52, 70, 95, 23, 04, 60, 11, 42, 69, 24, 68, 56, 01, 32, 56, 71, 37, 02, 36, 91 },
              { 22, 31, 16, 71, 51, 67, 63, 89, 41, 92, 36, 54, 22, 40, 40, 28, 66, 33, 13, 80 },
              { 24, 47, 32, 60, 99, 03, 45, 02, 44, 75, 33, 53, 78, 36, 84, 20, 35, 17, 12, 50 },
              { 32, 98, 81, 28, 64, 23, 67, 10, 26, 38, 40, 67, 59, 54, 70, 66, 18, 38, 64, 70 },
              { 67, 26, 20, 68, 02, 62, 12, 20, 95, 63, 94, 39, 63, 8, 40, 91, 66, 49, 94, 21 },
              { 24, 55, 58, 05, 66, 73, 99, 26, 97, 17, 78, 78, 96, 83, 14, 88, 34, 89, 63, 72 },
              { 21, 36, 23, 9, 75, 00, 76, 44, 20, 45, 35, 14, 00, 61, 33, 97, 34, 31, 33, 95 },
              { 78, 17, 53, 28, 22, 75, 31, 67, 15, 94, 03, 80, 04, 62, 16, 14, 9, 53, 56, 92 },
              { 16, 39, 05, 42, 96, 35, 31, 47, 55, 58, 88, 24, 00, 17, 54, 24, 36, 29, 85, 57 },
              { 86, 56, 00, 48, 35, 71, 89, 07, 05, 44, 44, 37, 44, 60, 21, 58, 51, 54, 17, 58 },
              { 19, 80, 81, 68, 05, 94, 47, 69, 28, 73, 92, 13, 86, 52, 17, 77, 04, 89, 55, 40 },
              { 04, 52, 8, 83, 97, 35, 99, 16, 07, 97, 57, 32, 16, 26, 26, 79, 33, 27, 98, 66 },
              { 88, 36, 68, 87, 57, 62, 20, 72, 03, 46, 33, 67, 46, 55, 12, 32, 63, 93, 53, 69 },
              { 04, 42, 16, 73, 38, 25, 39, 11, 24, 94, 72, 18, 8, 46, 29, 32, 40, 62, 76, 36 },
              { 20, 69, 36, 41, 72, 30, 23, 88, 34, 62, 99, 69, 82, 67, 59, 85, 74, 04, 36, 16 },
              { 20, 73, 35, 29, 78, 31, 90, 01, 74, 31, 49, 71, 48, 86, 81, 16, 23, 57, 05, 54 },
              { 01, 70, 54, 71, 83, 51, 54, 69, 16, 92, 33, 48, 61, 43, 52, 01, 89, 19, 67, 48 }
        };
        
        max = 0;
        for(int r = 0; r < 20; ++r) {
            for(int c = 0; c < 20; ++c) {
                //Vertical
                if (r <= 16) {
                    int prod = 1;
                    for(int deltaR = 0; deltaR < 4; ++deltaR) {
                        prod *= gridNum[r+deltaR][c];
                    }
                    max = Math.max(max, prod);
                }
                
                //Horizontal
                if (c <= 16) {
                    int prod = 1;
                    for(int deltaC = 0; deltaC < 4; ++deltaC) {
                        prod *= gridNum[r][c+deltaC];
                    }
                    max = Math.max(max, prod);
                }
                
                //Diag
                if (r <= 16 && c <= 16) {
                    int prod = 1;
                    for(int delta = 0; delta < 4; ++delta) {
                        prod *= gridNum[r+delta][c+delta];
                    }
                    max = Math.max(max, prod);
                }
                
                //Other diag
                if (r <= 16 && c >= 3) {
                    int prod = 1;
                    for(int delta = 0; delta < 4; ++delta) {
                        prod *= gridNum[r+delta][c-delta];
                    }
                    max = Math.max(max, prod);
                }
            }
        }
        
        log.info("Prob 11.  Sum {}", max);
        
        /*
        
        for(n = 1; n < 10000000; ++n) {
            long triangle = n * (n+1) / 2;
            
            Set<Long> factors = Sets.newHashSet();
            
            long upperLimit = LongMath.sqrt(triangle,RoundingMode.UP);
            
            for(long factor = 1; factor <= upperLimit; ++factor) {
                if (triangle % factor == 0) {
                    factors.add(factor);
                    factors.add(triangle / factor);
                }
            }
            
            if (factors.size() > 500 ) {
                log.info("Prob 12.  Triangle num {}", triangle);
                break;
            }
            
        }*/
    
        
        ///
        Scanner scanner = new Scanner(Prob1.class.getResourceAsStream("prob12.txt"));
        
        BigInteger sumBI = BigInteger.ZERO;
        
        for(int i = 0; i < 100; ++i) {
            BigInteger next = scanner.nextBigInteger();
            sumBI = sumBI.add(next);
        }
        
        log.info("Prob 13. Sum {}", sumBI.toString().substring(0, 10));
        scanner.close();
        
        /*
        int maxCount = 0;
        
        for(long start = 1; start < 1000000; ++start ) {
            int count = 0;
            long seq = start;
            while(seq != 1) {
                seq = seq % 2 == 0 ? seq / 2 : 3 * seq + 1;
                ++count;
            }
            if (count > maxCount) {
                maxCount = count;
               // log.info("Prob 14.  Seq length {}  start {}", count, start);
            }
        }*/
        
        s = BigInteger.valueOf(2).pow(1000).toString();
        
        sum = 0;
        for(int i = 0; i < s.length(); ++i) {
            sum += Character.digit(s.charAt(i),10);
        }
        
        log.info("Prob 16  sum {}", sum);

        //1 to 10
        int wordCounts[] = {3, 3, 5, 4, 4, 3, 5, 5, 4, 3, 6, "twelve".length(), "thirteen".length(),
                8, 7, 7, 9, "eighteen".length(), 8};
        int tenCounts[] = {"twenty".length(), "thirty".length(), "forty".length(), "fifty".length(),
                "sixty".length(), "seventy".length(), "eighty".length(), "ninety".length() };
        int hundred = "hundred".length();
        int thousand = "thousand".length();
        
        sum = 0;
        for(int i = 1; i <= 1000; ++i) {            
            if (i >= 1000) {
                sum += wordCounts[0] + thousand;
                continue;
            }
            
            int rest = i;
            if (i >= 100) {
                int hundredsDigit = wordCounts[i / 100 - 1] + hundred;
                sum += hundredsDigit;
                rest = i % 100;
            }
            
            if (rest == 0)
                continue;
            
            if (i >= 100) {
                sum += 3; //and
            }
            
            if (rest < 20) {
                sum += wordCounts[rest-1];
                continue;
            }
            
            int tensDigit = rest / 10;
            sum += tenCounts[tensDigit - 2];
            
            int onesDigit = rest % 10;
            
            if (onesDigit > 0) {
                sum += wordCounts[onesDigit-1];
            }
            
            
        }
        
        log.info("Prob 17 : sum {}", sum);
        
        //scanner = new Scanner(Prob1.class.getResourceAsStream("prob18.txt"));
        scanner = new Scanner(Prob1.class.getResourceAsStream("prob67.txt"));
        
        int nodeNum = 1;
        
        List<Integer> maximumPath  = Lists.newArrayList();
        maximumPath.add(scanner.nextInt());
        final int maxRow = 100;
        int globalMaxVal = 0;
        for(int r = 2; r <= maxRow; ++r) {
            for(int c = 1; c <= r; ++c) {
                ++nodeNum;
                int val = scanner.nextInt();
                
                int maxValue = 0;
                //subtract length of previous row
                if (c > 1) {
                    maxValue = Math.max(maxValue, val+maximumPath.get( nodeNum-r-1));
                }
                if (c < r) {
                    maxValue = Math.max(maxValue, val+maximumPath.get( nodeNum-r));
                }
                maximumPath.add(maxValue);
                globalMaxVal = Math.max(globalMaxVal,maxValue);
            }
        }
        
        log.info("Prob 18.  max sum {}", globalMaxVal);
        
        sum = 0;
        for (int y = 1901; y <= 2000; ++y) {
            for(int m = 1; m <= 12; ++m) {
                LocalDate dt = new LocalDate(y, m, 1);
                if (dt.getDayOfWeek() == DateTimeConstants.SUNDAY) {
                    sum++;
                }
                //log.info("Date time {}", dt.getDayOfWeek());
            }
        }
        
        log.info("Prob 19 {}", sum);
        
        
        String hugeFactorial = BigIntegerMath.factorial(100).toString();
        
        sum = 0;
        
        for(int i = 0; i < hugeFactorial.length(); ++i) {
            sum += Character.digit(hugeFactorial.charAt(i),10);
        }
        
        log.info("Prob 20 {}", sum);
        
        int amiable = 0;
        int[] sums = new int[10000];
        for(int i = 1; i < 10000; ++i) {
            
            int upperLimit = IntMath.sqrt(i,RoundingMode.UP);
            
            sum = 0;
            for(int factor = 1; factor <= upperLimit; ++factor) {
                if (i % factor == 0) {
                    sum += factor;
                    sum += i / factor;
                }
            }
            
            //Only proper factors
            sum -= i;
            
            sums[i-1] = sum;
            if (sum < i && sum > 0 && sums[sum-1] == i ) {
                log.info("Found amiable pair n {} : {} and {} : {}", i, sum, sum, sums[sum-1]);
                amiable+=i;
                amiable += sum;
            }
            
        }
        
        log.info("prob 21 : {}", amiable);
        
        
        scanner = new Scanner(Prob1.class.getResourceAsStream("prob22.txt"));
        
        Iterable<String> names = Splitter.on(",").split(scanner.next().replace("\"", ""));
        
        List<String> nameList = Lists.newArrayList(names);
        Collections.sort(nameList);
        
        sum = 0;
        for(int i = 0; i < nameList.size(); ++i) {
            String name = nameList.get(i);
            int alpha = 0;
           for(int c = 0; c < name.length(); ++c) {
               alpha += 1 + name.charAt(c) - 'A';
           }
           sum += (i+1) * alpha;
        }
        
        log.info("Prob 22 sum {}", sum);
        
        
    }

}
