package algorithms.search;

import java.util.Comparator;

public class AStateComperator implements Comparator<AState>{

    /**
     * @param o1 AState 1 with cost
     * @param o2 AState 2 with cost
     * @return  calls the compare cost function of AState
     */
        @Override
        public int compare(AState o1, AState o2) {
            return o1.CompareCost(o2);
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
        protected void NullArgCheck(Object my_obj) throws Exception{
        if (my_obj==null){
            throw new Exception("Null argument received");
        }
    }

}
