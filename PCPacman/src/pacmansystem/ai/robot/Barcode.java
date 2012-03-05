package pacmansystem.ai.robot;

public class Barcode
{
	
	int barcode;
	
	public Barcode (int barcode){
		this.barcode = barcode;
	}
	public Barcode(int... code) throws Exception
	{
		String s = "";
		if(code.length!=7)
			throw new Exception("barcode length wrong");
		for(int i : code)
			s+=i;
		this.barcode = (Integer.parseInt(s));
	}
	
	/**
	 * @return This barcode read in the other direction.
	 */
	public int getReverse(){
		char[] binairy = Integer.toBinaryString(barcode).toCharArray();
		if (binairy.length != 8)
			throw new BarcodeException("The given barcode-value does not exist!");
		char[] result = new char[8];
		result[0] = binairy[0];
		result[7] = binairy[7];
		result[1] = binairy[6];
		result[2] = binairy[5];
		result[3] = binairy[4];
		result[4] = binairy[3];
		result[5] = binairy[2];
		result[6] = binairy[1];
		return Integer.parseInt(new String(result), 2);
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
	
	
	/**
	 * TEST MAIN!!!
	 * @param args
	 */
	public static void main(String[] args){
		
		Barcode test = new Barcode(Integer.parseInt("10011011", 2));
		System.out.println(test.equals(new Barcode(test.getReverse())));
	}
}
