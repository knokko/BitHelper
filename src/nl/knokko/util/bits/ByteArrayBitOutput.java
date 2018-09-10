package nl.knokko.util.bits;

import java.util.Arrays;

public class ByteArrayBitOutput extends BitOutput {
	
	private byte[] bytes;
	
	private int byteIndex;
	private int boolIndex;
	
	public ByteArrayBitOutput(){
		this(100);
	}

	public ByteArrayBitOutput(int startCapacity) {
		bytes = new byte[startCapacity];
	}

	@Override
	public void addDirectBoolean(boolean value) {
		boolean[] bools = BitHelper.byteToBinary(bytes[byteIndex]);
		bools[boolIndex++] = value;
		bytes[byteIndex] = BitHelper.byteFromBinary(bools);
		if(boolIndex == 8){
			boolIndex = 0;
			byteIndex++;
		}//10010101
	}

	@Override
	public void addDirectByte(byte value) {
		if(boolIndex == 0)
			bytes[byteIndex++] = value;
		else {
			boolean[] values = BitHelper.byteToBinary(value);
			int valueIndex = 0;
			boolean[] current = BitHelper.byteToBinary(bytes[byteIndex]);
			boolean[] next = BitHelper.byteToBinary(bytes[byteIndex + 1]);
			for(; boolIndex < 8; boolIndex++)
				current[boolIndex] = values[valueIndex++];
			boolIndex = 0;
			for(; valueIndex < 8; valueIndex++)
				next[boolIndex++] = values[valueIndex];
			bytes[byteIndex++] = BitHelper.byteFromBinary(current);
			bytes[byteIndex] = BitHelper.byteFromBinary(next);
		}
	}

	@Override
	public void ensureExtraCapacityCapacity(int booleans) {
		int extra = booleans / 8;
		if(booleans - extra * 8 + boolIndex < 8)//recently changed this from ... boolIndex < 8 to ... boolIndex >= 8
			extra++;
		int newLength = byteIndex + extra;
		if(newLength > bytes.length)
			bytes = Arrays.copyOf(bytes, newLength);
	}

	@Override
	public void terminate() {}
	
	public byte[] getRawBytes(){
		return bytes;
	}
	
	public byte[] getBytes(){
		int length = byteIndex;
		if(boolIndex != 0)
			length++;
		return Arrays.copyOf(bytes, length);
	}
	
	public boolean[] getBooleans(){
		int length = byteIndex * 8 + boolIndex;
		boolean[] bools = new boolean[length];
		int index = 0;
		for(int i = 0; i < byteIndex; i++){
			boolean[] current = BitHelper.byteToBinary(bytes[i]);
			for(boolean b : current)
				bools[index++] = b;
		}
		if(boolIndex > 0){
			boolean[] last = BitHelper.byteToBinary(bytes[byteIndex]);
			int lastIndex = 0;
			for(; index < length; index++)
				bools[index] = last[lastIndex++];
		}
		return bools;
	}
}