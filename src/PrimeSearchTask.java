
public class PrimeSearchTask implements Runnable {

    private long from, upTo;
    private int t_number;
    private static int totalNumbersOfPrimes = 0;

    public PrimeSearchTask(int t_number, long from, long upTo ) {
        if ( upTo < from ) throw new RuntimeException( "\"From\"-number should be less than \"upTo\"-number.");
        this.from = from;
        this.upTo = upTo;
        this.t_number = t_number;
    }

    public synchronized void run(){

        System.out.println( "Thread[" + t_number + "]: Starting prime search from " + from + " up to " + upTo);

        long startTime = System.currentTimeMillis();
        long numberofPrimes = searchPrimes();
        long stopTime = System.currentTimeMillis();

        //course 6 material
        //live in the fast lane here...
        totalNumbersOfPrimes += numberofPrimes;
        //PrimeSearchMain.addtotalNumberofPrimes(numberofPrimes);


        //course 6 material
        // the more reliable stuff... or....?
        // PrimeSearchMain.addtotalNumberofPrimes(numberofPrimes);

        System.out.println( "Thread[" + t_number + "]: The number of primes found is " + numberofPrimes + ".");
        System.out.println( "Thread[" + t_number + "]: This took " + (stopTime - startTime)/1000.0 + " seconds.");
        System.out.println( "Thread[" + t_number + "]: In total " + totalNumbersOfPrimes +  " primes were found (so far)");
    }

    /**
     * Checks all the numbers in the interval [from, upTo]
     * too see if they are prime and returns the last one.
     * (This isn't really efficient, but useful for this program.
     * @return Returns the number of primes found.
     */
    public long searchPrimes( ){
        long numberofPrimes = 0;
        for ( long i = from; i<= upTo; i++){
            if ( isPrime( i ) ) numberofPrimes++;
        }
        return numberofPrimes;
    }

    /**
     * Determine if a given number is prime. There's no need to go further than
     * the floor of the root of the primeCandidate. (Because if primeCandidate = x*y,
     * with x > root, then y < root and we would have found y earlier.)
     * No further optimization done
     * @param primeCandidate
     * @return Whether primeCandidate is prime or not.
     */
    private boolean isPrime( long primeCandidate ){
        long temp= (long) Math.sqrt(primeCandidate);
        boolean Prime = true;
        for (int factor=2; factor<=temp; factor++){
            if (primeCandidate%factor == 0) Prime = false;
            //if (Prime == false) return Prime;
        }
        return Prime;
    }
}