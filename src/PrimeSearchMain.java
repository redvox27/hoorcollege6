import java.util.concurrent.TimeUnit;

public class PrimeSearchMain {

    /**
     * @throws InterruptedException
     */
    volatile static long totalNumberOfPrimes = 0;

    //course 6 material
    public static long gettotalNumberofPrimes () { return totalNumberOfPrimes;};

    //course 6 material
    public static void settotalNumberofPrimes (long value) {totalNumberOfPrimes = value;};

    //course 6 material
    //okay.. this is really short... but is it rock solid?
    public static void addtotalNumberofPrimes (long value) {totalNumberOfPrimes += value;};

    public static void main(String[] args) throws InterruptedException {
        long minPrime = 1;
        long maxPrime = 10000;
        int nrThreads = 8;

        // bit of math here...	integral of sqrt (x) is 2/3 x ^3/2
        long nrDivs = Math.round((Math.pow(maxPrime,1.5)- Math.pow(minPrime,1.5))/3*2);
        long nrDivsPerT= nrDivs/ nrThreads;

        long lowerEdge = minPrime;
        long upperEdge = 0;
        long[] splits;
        splits = new long[nrThreads+2];

        // set the lower and upper boundary
        splits [1] = minPrime;
        splits [nrThreads+1] = maxPrime;

        // split the work in equally large pieces. Again, a bit of math involved here...
        for (int counter=2; counter <= nrThreads;counter++) {
            upperEdge =  (long) Math.pow((Math.pow(lowerEdge,1.5) * 2.0/3.0 + nrDivsPerT) *1.5,2.0/3.0);
            splits[counter] = upperEdge;
            lowerEdge = upperEdge;
        }

        //System.out.println("Giving you 10 seconds......"); so the user can e.g. open procesexplorer
        //TimeUnit.MILLISECONDS.sleep(10000);

        for (int i = 1 ; i <= nrThreads; i++){
            Thread t = new Thread(new PrimeSearchTask(i, splits[i]+1, splits[i+1]));
            t.start();
            t.join();
            //TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}