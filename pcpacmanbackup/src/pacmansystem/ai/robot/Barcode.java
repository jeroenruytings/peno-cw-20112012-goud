package pacmansystem.ai.robot;

public class Barcode
{
	
	int barcode;
	
	public Barcode (int barcode){
		if (!correctBarcode(barcode))
			throw new IllegalArgumentException("Foute barcode gemaakt:" + barcode);
		this.barcode = barcode;
	}
	
	private boolean correctBarcode(int barcode){
		return (barcode != 0);
	}
	public Barcode(int... code) 
	{
		String s = "";
		if (code.length != 8)
			throw new IllegalArgumentException("Deze constructor mag enkel met 8 argumenten worden opgeroepen.");
		for(int i = 1; i < (code.length - 1); i++)
			s+=code[i];
		this.barcode = (Integer.parseInt(s,2));
	}
	
	/**
	 * @return This barcode read in the other direction.
	 */
	public int getReverse() throws BarcodeException{
		char[] result = new char[6];
		char[] binairy = this.toString().toCharArray();//Integer.toBinaryString(getValue()).toCharArray();
		if (binairy.length != 6)
			throw new BarcodeException("The given barcode-value does not exist!");
		result[0] = binairy[5];
		result[1] = binairy[4];
		result[2] = binairy[3];
		result[3] = binairy[2];
		result[4] = binairy[1];
		result[5] = binairy[0];
		return Integer.parseInt(new String(result), 2);
	}
	/**
	 * 
	 * @return 
	 * the 
	 */
	public char[] getBitString(){
		return  Integer.toBinaryString(getValue()).toCharArray();
//		if (binairy.length != 8)
//			throw new BarcodeException("The given barcode-value does not exist!");
//		return Integer.parseInt((new String(binairy)));
	}
	
	public int getValue(){
		return barcode;
	}
	
	public boolean equals(Object o){
		if (o instanceof Barcode){
			return (((Barcode) o).getValue() == this.getValue()) || (((Barcode) o).getReverse() == this.getValue());
		}
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return barcode;
	}
	/**
	 * TEST MAIN!!!
	 * @param args
	 */
	public static void main(String[] args){
		Barcode test = new Barcode(24);
		test.getReverse();
		test = new Barcode(Integer.parseInt("00010000", 2));
		//System.out.println(new Barcode(test.getReverse()).getBitString());
		try {
			System.out.println(new Barcode(0,1,0,1,0,0,0,0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String toString()
	{
		char[] rv =getBitString();
		String rev = new String(rv);
		for(int i = 6;i>rv.length;i--)
			rev="0"+rev;
		return rev;
	}
	/**
	 * Don't use this method.
	 * 
	 * @return an array of booleans where true is white and black is false. Length 8
	 */
	@Deprecated
	public boolean[] getBinaryValues()
	{
		boolean[] rv = new boolean[8];
		rv[0]=false;
		rv[7]=false;
		for (int i = 1; i < rv.length-1; i++) {
			rv[i]=fromchar(toString().toCharArray()[i-1]);
		}
		
		return rv;
	}
	/**
	 * Dont even consider using this
	 * returns true if c=='1' and false if c=='0' throws error otherwise
	 */
	@Deprecated
	private boolean fromchar(char c)
	{
		if(c=='0')
			return false;
		if(c=='1')
			return true;
		throw new Error("Fatal conversion error");
	}
	
}
