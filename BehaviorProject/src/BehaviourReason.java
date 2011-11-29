
public enum BehaviourReason implements Value {
	LIGHTSENSORNOTBROWN(0),LEFTTOUCH(1),RIGHTTOUCH(2),TWOTOUCH(3),NOREASON(4),LEFTWALL(5),RIGHTWALL(6),FRONTWALL(7);
private byte mask;
private BehaviourReason(int mask)
{
	this.mask=(byte) mask;
}
	@Override
	public byte getMask() {
		// TODO Auto-generated method stub
		return mask;
	}

}
