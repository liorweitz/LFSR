import expression.Polynom;

public class Main {
    public static void main(String[] args) throws Exception {

        Polynom pol=new Polynom(new double[] {0,1,1,1});
        System.out.println(pol);

        Polynom sub=new Polynom(new double[] {1});
        System.out.println(sub);
        System.out.println(pol.divide(sub));
//        System.out.println(pol.subtract(sub));


//        Matrix m=new MatrixImpl(3,3);
//        m.setVal(0,0,1);
//        m.setVal(0,1,2);
//        m.setVal(0,2,3);
//        m.setVal(1,0,0);
//        m.setVal(1,1,1);
//        m.setVal(1,2,5);
//        m.setVal(2,0,5);
//        m.setVal(2,1,6);
//        m.setVal(2,2,0);
//
//        System.out.println(m);
//        System.out.println(m.minor(0,2));
//        System.out.println(m.determinant());
//        System.out.println(m.inverse());
//        Matrix minor=m.minor(4,4);
//        System.out.println(minor);
    }
}
