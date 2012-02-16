package communication;

import controls.Pijlkes;


public class Main {

	public static void main(String[] args) {
		Communicator communicator = new Communicator();		
		Pijlkes pijlkes = new Pijlkes(communicator);
		System.out.println("finished");
	}
}
