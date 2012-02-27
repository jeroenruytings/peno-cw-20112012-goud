package connector;

public abstract class LeoMonitor {
	private LeoMonitor next;
	public LeoMonitor(LeoMonitor next)
	{
			this.next=next;
	}
	public abstract void accept(byte[] message);
	public LeoMonitor next(){
		return this.next;
	}
}

