public class StateVectorImpl implements StateVector{
    private int [] vec;

    public StateVectorImpl(int size){
        vec=new int[size];
    }

    @Override
    public int getVal(int loc) {
        return vec[loc];
    }

    @Override
    public int getSize() {
        return vec.length;
    }

    @Override
    public int getOutputbit() {
        return 0;
    }

    @Override
    public void setVal(int loc, int val) {
        vec[loc]=val;
    }

    @Override
    public int scalarMult(StateVector other) {
        int scalar=0;
        for  (int i=0;i<vec.length; i++){
            scalar+=vec[i]*other.getVal(i);
        }
        return scalar;
    }

    public StateVector bitWiseMult(StateVector other){
        StateVector temp=new StateVectorImpl(vec.length);
        for (int i=0; i<vec.length;i++){
            temp.setVal(i,vec[i]*other.getVal(i));
        }
        return temp;
    }

    @Override
    public StateVector matrixMult(Matrix m) {
        StateVector res=new StateVectorImpl(vec.length);
        for (int i=0; i<vec.length;i++){
            int val=0;
            for (int j=0;j<vec.length;j++){
                val+=m.getVal(i,j)*vec[j];
            }
            res.setVal(i,val);
        }
        return res;
    }
}
