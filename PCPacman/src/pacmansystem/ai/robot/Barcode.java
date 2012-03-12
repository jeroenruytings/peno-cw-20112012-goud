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
		if(code.length!=8)
			throw new Exception("barcode length wrong");
		for(int i : code)
			s+=i;
		this.barcode = (Integer.parseInt(s,2));
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
	
	public int getBitString(){
		char[] binairy = Integer.toBinaryString(barcode).toCharArray();
	//	if (binairy.length != 8)
		///	throw new BarcodeException("The given barcode-value does not exist!");
		return Integer.parseInt((new String(binairy)));
	}
	public char[] getBitString2(){
		return  Integer.toBinaryString(barcode).toCharArray();
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
		Barcode test = new Barcode(Integer.parseInt("10010001", 2));
		System.out.println(new Barcode(test.getReverse()).getBitString());
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
		char[] rv =getBitString2();
		String rev = new String(rv);
		for(int i = 8;i>rv.length;i--)
			rev="0"+rev;
		return rev;
	}
	
}
