public class Main {
    public static void main(String[] args) throws Exception {
        StateVector seed=new StateVectorImpl(4);
        seed.setVal(0,1);
        StateVector taps=new StateVectorImpl(new int[]{1,1,1,0});
        BitGenerator bitGenerator=new XorBitGenerator();
        CoreLfsr lfsr=new CoreLfsrImpl(seed,taps,bitGenerator);

        System.out.println(lfsr.findPeriodicityByBruteForce());

        System.out.println(lfsr.findMaxPeriodicity());


        System.out.println(lfsr.getState());
        for (int i=1; i<=40;i++){
            lfsr.step();
            System.out.println(lfsr.getState());
        }
        System.out.println(lfsr.getStream());

    }
}
