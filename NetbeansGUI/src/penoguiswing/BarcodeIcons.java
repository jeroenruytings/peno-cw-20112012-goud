/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penoguiswing;

/**
 *
 */
public enum BarcodeIcons {
  
    RIGHT("rechtsaf"),LEFT("linksaf"),STRAIGHT("rechtdoor"),RISING("stijging"),LOWERING("daling"),ERROR("voorrang"),
    NARROWING("versmalling"),BRIDGE("brug"),UTURN("omkeren"),LEFTLINE("linkerlijn"),RIGHTLINE("rechterlijn");
    
    private BarcodeIcons(String fileName){
        this.fileName = fileName;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public static BarcodeIcons getBarcode(int numberOfBarcode){
        switch(numberOfBarcode){
            case 6:
                return RIGHT;
            case 3:
                return LEFT;
            case 1:
                return STRAIGHT;
            case 2:
                return RISING;
            case 4:
                return LOWERING;
            case 8:
                return NARROWING;
            case 7:
                return BRIDGE;
            case 13:
                return UTURN;
            case 0:
                return RIGHTLINE;
            case 15:
                return LEFTLINE;
            default :
                return ERROR;
        }
    }
            
    
    private final String fileName;
    
    
}
