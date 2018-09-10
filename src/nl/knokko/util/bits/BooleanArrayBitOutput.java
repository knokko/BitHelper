package nl.knokko.util.bits;

import java.util.Arrays;

public class BooleanArrayBitOutput extends BitOutput {
	
	private boolean[] booleans;
	
	private int writeIndex;
	
	public BooleanArrayBitOutput(){
		this(800);
	}

	public BooleanArrayBitOutput(int startCapacity) {
		booleans = new boolean[startCapacity];
	}

	@Override
	public void addDirectBoolean(boolean value) {
		booleans[writeIndex++] = value;
	}

	@Override
	public void addDirectByte(byte value) {
		BitHelper.byteToBinary(value, booleans, writeIndex);
		writeIndex += 8;
	}
	
	@Override
	public void addDirectBooleans(boolean... bools){
		System.arraycopy(bools, 0, booleans, writeIndex, bools.length);
		writeIndex += bools.length;
	}

	@Override
	public void ensureExtraCapacityCapacity(int booleans) {
		int newLength = writeIndex + booleans;
		if(newLength > this.booleans.length)
			this.booleans = Arrays.copyOf(this.booleans, newLength);
	}
	
	@Override
	public void terminate(){}
	
	public boolean[] getRawBooleans(){
		return booleans;
	}
	
	public boolean[] getBooleans(){
		return Arrays.copyOf(booleans, writeIndex);
	}
	
	public byte[] getBytes(){
		int size = booleans.length / 8;
		int safeSize = size;
		boolean addHalfByte = 8 * size < booleans.length;
		if(addHalfByte)
			size++;//rounding upwards
		byte[] bytes = new byte[size];
		for(int i = 0; i < safeSize; i++)
			bytes[i] = BitHelper.byteFromBinary(booleans, i * 8);
		if(addHalfByte){
			boolean[] last = new boolean[8];
			int index = safeSize * 8;
			int lastIndex = 0;
			for(; index < booleans.length; index++)
				last[lastIndex++] = booleans[index];
			bytes[safeSize] = BitHelper.byteFromBinary(last);
		}
		return bytes;
	}
	
	public void setWriteIndex(int newIndex){
		writeIndex = newIndex;
	}
}
