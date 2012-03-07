package communicator.parser.decoder;

import static org.junit.Assert.*;

import org.junit.Test;

import communicator.parser.Decoder;


public class _TestBarcodeDecoder
{

	@Test
	public void trueTest()
	{
		Decoder reader = new BARCODE(null);
		assertTrue(reader.canDecode("jenny BARCODE something\n"));
	}

	@Test
	public void failTest()
	{
		Decoder reader = new BARCODE(null);
		assertFalse(reader.canDecode("jenny something something\n"));
	}
}
