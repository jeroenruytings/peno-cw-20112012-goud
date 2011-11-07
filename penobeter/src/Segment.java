
abstract class Segment {

        public static final Segment DEFAULT = new Segment(){

                @Override
                public void driveWith(ImprovedDifferentialPilot t) {
                        t.forward();
                        
                }
                
        };

        public abstract void driveWith(ImprovedDifferentialPilot t) ;

}