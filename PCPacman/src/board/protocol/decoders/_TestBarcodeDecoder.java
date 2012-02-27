package board.protocol.decoders;

import static org.junit.Assert.*;

import org.junit.Test;

import board.protocol.Decoder;

public class _TestBarcodeDecoder {
	@Test
	public void trueTest()
	{
		Decoder reader = new BARCODE();
		assertTrue(reader.canDecode("jenny BARCODE something\n"));
	}
	@Test
	public void failTest()
	{
		Decoder reader = new BARCODE();
		assertFalse(reader.canDecode("jenny something something\n"));
	}
}
