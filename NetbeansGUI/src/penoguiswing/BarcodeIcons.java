/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package penoguiswing;

/**
 *
 */
public enum BarcodeIcons {
  
    RIGHT("rechtsaf"),LEFT("linksaf"),STRAIGHT("rechtdoor"),RISING("stijging"),LOWERING("daling"),ERROR("voorrang"),NARROWING("versmalling"),BRIDGE("brug");
    
    private BarcodeIcons(String fileName){
        this.fileName = fileName;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public static BarcodeIcons getBarcode(int numberOfBarcode){
        switch(numberOfBarcode){
            case 0:
                return RIGHT;
            case 1:
                return LEFT;
            case 3:
                return STRAIGHT;
            case 4:
                return RISING;
            case 5:
                return LOWERING;
            case 6:
                return NARROWING;
            case 7:
                return BRIDGE;
            default :
                return ERROR;
        }
    }
            
    
    private final String fileName;
    
    
}
